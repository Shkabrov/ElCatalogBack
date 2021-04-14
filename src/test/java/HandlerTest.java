// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerTest {
    @Test
    public void response() {
        new DataBase();
        assertEquals("{\"status\":\"ok\"}",
                Handler.response("{method:register,login:test,password:test,name:test," +
                        "lastName:test,address:test}"));
        assertEquals("{\"error_type\":\"login_already_used\",\"status\":\"error\"}",
                Handler.response("{method:register,login:test,password:test,name:test," +
                        "lastName:test,address:test}"));
        assertEquals("{\"book\":[{\"statusReservation\":\"0\",\"author\":\"Ray Bradbury\"," +
                        "\"bookYear\":\"1957\",\"bookName\":\"Dandelion Wine\"},{\"statusReservation\":\"0\"," +
                        "\"author\":\"Daniel Keyes\",\"bookYear\":\"1959\"," +
                        "\"bookName\":\"Flowers for Algernon\"},{\"statusReservation\":\"0\"," +
                        "\"author\":\"Daniel Defoe\",\"bookYear\":\"1719\",\"bookName\":\"Robinson Crusoe\"}," +
                        "{\"statusReservation\":\"0\",\"author\":\"Mark Twain\",\"bookYear\":\"1876\"," +
                        "\"bookName\":\"The Adventures of Tom Sawyer\"},{\"statusReservation\":\"0\"," +
                        "\"author\":\"Salinger\",\"bookYear\":\"1951\",\"bookName\":\"The Catcher in the Rye\"}," +
                        "{\"statusReservation\":\"0\",\"author\":\"Antoine de Saint-Exupery\"," +
                        "\"bookYear\":\"1942\",\"bookName\":\"The Little Prince\"},{\"statusReservation\":\"0\"," +
                        "\"author\":\"Oscar Wilde\",\"bookYear\":\"1890\"," +
                        "\"bookName\":\"The Picture of Dorian Gray\"},{\"statusReservation\":\"0\"," +
                        "\"author\":\"Erich Maria Remarque\",\"bookYear\":\"1936\",\"bookName\":\"Three Comrades\"}]}",
                Handler.response("{method:getBooks}"));
        assertEquals("{\"status\":\"ok\"}",
                Handler.response("{method:login,login:test,password:test}"));
        assertEquals("{\"error_type\":\"wrong_password\",\"status\":\"error\"}",
                Handler.response("{method:login,login:test,password:1}"));
        assertEquals("{\"idClients\":\"3\"}",
                Handler.response("{method:checkLogin}"));
        assertEquals("{\"statusReservation\":\"0\",\"author\":\"Daniel Defoe\",\"bookYear\":\"1719\"," +
                        "\"bookName\":\"Robinson Crusoe\"}",
                Handler.response("{method:search,bookName:Robinson Crusoe}"));
        assertEquals("{\"idBooks\":\"1\"}",
                Handler.response("{method:checkSearch}"));
        assertEquals("{\"statusReservation\":\"0\",\"author\":\"Daniel Defoe\",\"bookYear\":\"1719\"," +
                        "\"bookName\":\"Robinson Crusoe\"}",
                Handler.response("{method:searchOutput}"));
        assertEquals("{}",
                Handler.response("{method:booking}"));
        assertEquals("{\"book\":[{\"date\":\"29.5.2020\",\"author\":\"Daniel Defoe\",\"bookYear\":\"1719\"," +
                        "\"bookName\":\"Robinson Crusoe\"}]}",
                Handler.response("{method:reservations}"));
        assertEquals("{}",
                Handler.response("{method:outputLogin}"));
    }
}