package com.javarush.task.task30.task3008.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.javarush.task.task30.task3008.ConsoleHelper.*;

public class BotClient extends Client {
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_"+ (int) (Math.random()*100);
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }

    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды"+
                    ": дата, день, месяц, год, время, час, минуты, секунды.");

            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            writeMessage(message);
            if (message.contains(": ")) {
                String[] strings = message.split(": ");

                String name = strings[0];
                String text = strings[1];

                String pattern = null;
                if (text.equals("дата")) {
                    pattern = "d.MM.YYYY";
                } else if (text.equals("день")) {
                    pattern = "d";
                } else if (text.equals("месяц")) {
                    pattern = "MMMM";
                } else if (text.equals("год")) {
                    pattern = "YYYY";
                } else if (text.equals("время")) {
                    pattern = "H:mm:ss";
                } else if (text.equals("час")) {
                    pattern = "H";
                } else if (text.equals("минуты")) {
                    pattern = "m";
                } else if (text.equals("секунды")) {
                    pattern = "s";
                }

                if (pattern != null) {
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    Calendar calendar = new GregorianCalendar();

                    Date date = calendar.getTime();
                    sendTextMessage(String.format("Информация для %s: %s", name, format.format(date)));
                }
            }
        }
    }
}
