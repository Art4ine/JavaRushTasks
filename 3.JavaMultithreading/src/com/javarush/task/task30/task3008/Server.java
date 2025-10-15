package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.javarush.task.task30.task3008.ConsoleHelper.*;

public class Server {
    public static void main(String[] args) {
        writeMessage("Введите порт сервера:");
        int port = readInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            writeMessage("Сервер запущен.");

            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);

                handler.start();
            }
        } catch (IOException e) {
            writeMessage("Произошла ошибка при запуске или работы сервера.");
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }
    }
}
