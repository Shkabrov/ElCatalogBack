// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DataBase {

    private static Connection dbConnection;

    public DataBase() {
        String connectionString =
                "jdbc:mysql://127.0.0.1:3306/elcatalog?&useUnicode=true&serverTimezone=UTC&useSSL=false";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(connectionString, "root", "jvcr-456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> login(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from clients " +
                    "WHERE login='" + request.get("login") + "'");
            set.next();
            if (set.getString("password").equals(request.get("password"))) {
                response.put("status", "ok");
                statement.execute("INSERT into login (idClients)" +
                        " VALUES(" + set.getString("idClients") + ")");
            } else {
                response.put("status", "error");
                response.put("error_type", "wrong_password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> outputLogin() {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            statement.execute("DELETE FROM login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> checkLogin() {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from login");
            if (!set.next()) {
                response.put("idClients", "null");
                return response;
            } else {
                response.put("idClients", set.getString("idClients"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> register(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        if (inBase(request.get("login"))) {
            response.put("status", "error");
            response.put("error_type", "login_already_used");
        } else {
            try {
                String name = request.get("name");
                String lastName = request.get("lastName");
                String login = request.get("login");
                String password = request.get("password");
                String address = request.get("address");
                Statement statement = dbConnection.createStatement();
                statement.execute("INSERT into clients (idClients,name,lastName,login,password,address)" +
                        " VALUES('" + countClients() + "','" + name + "','" + lastName + "','" + login + "','"
                        + password + "','" + address + "')");
                response.put("status", "ok");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private static Boolean inBase(String login) {
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT login from clients");
            while (set.next()) {
                if (login.equals(set.getNString("login"))) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static HashMap<String, String> search(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from books " +
                    "WHERE bookName='" + request.get("bookName") + "'");
            set.next();
            response.put("bookName", set.getString("bookName"));
            response.put("author", set.getString("author"));
            response.put("bookYear", set.getString("bookYear"));
            response.put("statusReservation", set.getString("statusReservation"));
            statement.execute("INSERT searchbooks(idBooks, bookName, author, bookYear, statusReservation)" +
                    " VALUES ('" + set.getString("idBooks") + "','" + set.getString("bookName") +
                    "','" + set.getString("author") + "','" + set.getString("bookYear") +
                    "'," + set.getString("statusReservation") + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> checkSearch() {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from searchbooks");
            if (!set.next()) {
                response.put("idBooks", "null");
                return response;
            } else {
                response.put("idBooks", set.getString("idBooks"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> searchOutput() {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from searchbooks ");
            set.next();
            response.put("bookName", set.getString("bookName"));
            response.put("author", set.getString("author"));
            response.put("bookYear", set.getString("bookYear"));
            response.put("statusReservation", set.getString("statusReservation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, String> booking() {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();

            ResultSet set0 = statement.executeQuery("SELECT * from login ");
            set0.next();
            String idClient = set0.getString("idClients");

            ResultSet set1 = statement.executeQuery("SELECT * from searchbooks ");
            set1.next();
            if (set1.getInt("statusReservation") == 0) {
                String statusReservation = "1";
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH) + 14;
                String date = day + "." + month + "." + year + "";

                statement.execute("UPDATE books " + "SET statusReservation=" + statusReservation +
                        "" + " WHERE bookName='" + set1.getString("bookName") + "'");

                ResultSet set2 = statement.executeQuery("SELECT * from searchbooks ");
                set2.next();

                ResultSet setNew = statement.executeQuery("SELECT * from books " +
                        "WHERE bookName='" + set2.getString("bookName") + "'");
                setNew.next();

                statement.execute("INSERT rbooks(idBooks, idClients, bookName, author, bookYear, date)" +
                        " VALUES ('" + setNew.getString("idBooks") + "','" + idClient + "','" +
                        setNew.getString("bookName") + "', '" + setNew.getString("author") +
                        "','" + setNew.getString("bookYear") + "','" + date + "')");
                statement.execute("DELETE FROM searchbooks");
            } else {
                statement.execute("DELETE FROM searchbooks");
                response.put("status", "error");
                response.put("error_type", "book_already_booked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<HashMap<String, String>> reservations() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set0 = statement.executeQuery("SELECT * from login ");
            set0.next();

            ResultSet set = statement.executeQuery("SELECT * from rbooks " +
                    "WHERE idClients='" + set0.getString("idClients") + "'");
            while (set.next()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("bookName", set.getString("bookName"));
                map.put("author", set.getString("author"));
                map.put("bookYear", set.getString("bookYear"));
                map.put("date", set.getString("date"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static int countClients() {
        int count = 1;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT login from clients");
            while (set.next()) {
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static ArrayList<HashMap<String, String>> getBooks() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from books " +
                    "ORDER BY bookName");
            while (set.next()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("bookName", set.getString("bookName"));
                map.put("author", set.getString("author"));
                map.put("bookYear", set.getString("bookYear"));
                map.put("statusReservation", set.getString("statusReservation"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
