package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.javarush.task.task30.task3008.ConsoleHelper.*;
import static com.javarush.task.task30.task3008.MessageType.*;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                writeMessage("Не смогли отправить сообщение для адреса: " +
                        connection.getRemoteSocketAddress());
            }
        }
    }

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

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(NAME_REQUEST, "Пожалуйста, введите ваше имя:"));
            Message message = connection.receive();

            String name = message.getData();
            if (message.getType() == USER_NAME && Objects.nonNull(name) &&
                    !name.isEmpty() && !connectionMap.containsKey(name)) {
                connectionMap.put(name, connection);
                connection.send(new Message(NAME_ACCEPTED, String.format("Добро пожаловать в чат, %s!", name)));
                
                return name;
            } else {
                writeMessage("Ошибка имени ввода пользователя.");
                return serverHandshake(connection);
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String name : connectionMap.keySet()) {
                if (!name.equals(userName)) {
                    connection.send(new Message(USER_ADDED, name));
                }
            }

//            connectionMap.keySet().stream()
//                    .filter(name -> !Objects.equals(name, userName))
//                    .map(name -> new Message(USER_ADDED, name))
//                    .forEach(connection::send);
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == TEXT) {
                    sendBroadcastMessage(new Message(TEXT, userName + ": " + message.getData()));
                } else {
                    writeMessage("Тип сообщения не соответсвует протоколу.");
                }
            }
        }

        @Override
        public void run() {
            String userName = null;

            writeMessage("установлено новое соединение с удаленным адресом "+socket.getRemoteSocketAddress());
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(USER_ADDED, userName));
                notifyUsers(connection, userName);

                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");
            }

            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(USER_REMOVED, userName));
            }

            writeMessage("Соединение с удаленным адресом "+socket.getRemoteSocketAddress()+" закрыто.");


        }
    }
}
