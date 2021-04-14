// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import java.util.HashMap;
import java.util.Map;

public class Handler {
    //@TODO
    public static String response(String msg) {
        Map<String, String> response = new HashMap<>();
        HashMap<String, String> request = Parser.parse(msg);
        String method = request.get("method");
        response.put("method", method);
        switch (method) {
            case "login":
                response = DataBase.login(request);
                break;
            case "outputLogin":
                response = DataBase.outputLogin();
                break;
            case "checkLogin":
                response = DataBase.checkLogin();
                break;
            case "register":
                response = DataBase.register(request);
                break;
            case "search":
                response = DataBase.search(request);
                break;
            case "checkSearch":
                response = DataBase.checkSearch();
                break;
            case "booking":
                response = DataBase.booking();
                break;
            case "reservations":
                return Parser.toJson(DataBase.reservations());
            case "getBooks":
                return Parser.toJson(DataBase.getBooks());
            case "searchOutput":
                response = DataBase.searchOutput();
                break;
        }
        return Parser.toJson(response);
    }
}
