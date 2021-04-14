// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Parser {

    public static String toJson(Map<String, String> response) {
        JSONObject json = new JSONObject();
        Set<String> keys = response.keySet();

        for (String key : keys) {
            try {
                json.put(key, response.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    public static String toJson(ArrayList<HashMap<String, String>> list) {
        JSONObject json = new JSONObject();

        try {
            json.put("book", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    //@TODO
    public static HashMap<String, String> parse(String msg) {
        HashMap<String, String> response = new HashMap<>();
        try {
            JSONObject json = new JSONObject(msg);
            String method = json.getString("method");
            response.put("method", method);
            switch (method) {
                case "login":
                    response.put("login", json.getString("login"));
                    response.put("password", json.getString("password"));
                    break;
                case "outputLogin":
                    break;
                case "checkLogin":
                    break;
                case "register":
                    response.put("login", json.getString("login"));
                    response.put("password", json.getString("password"));
                    response.put("name", json.getString("name"));
                    response.put("lastName", json.getString("lastName"));
                    response.put("address", json.getString("address"));
                    break;
                case "search":
                    response.put("bookName", json.getString("bookName"));
                    break;
                case "checkSearch":
                    break;
                case "searchOutput":
                    break;
                case "booking":
                    break;
                case "reservations":
                    break;
                case "getBooks":
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
