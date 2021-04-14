// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(6000);
            new Thread(() -> {
                while (true) {
                    try {
                        new Client(serverSocket.accept());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new DataBase();
    }
}
