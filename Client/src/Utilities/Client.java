package Utilities;

import Collection.Movie;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Client {

    private String hostname;
    private int port;

    public Client() {
        hostname = null;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите имя хоста: ");
            hostname = scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Такого хоста не существует. Повторите ввод");
            new Client().run();
        }
        try {
            System.out.print("Введите порт: ");
            port = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Такого порта не существует. Повторите ввод");
            new Client().run();
        }
        int counter = 0;
        while (true) {
            try {
                if (Client.connect(scanner, hostname, port)) System.out.println("Соединение с сервером восстановлено");
            } catch (IOException e) {
                System.out.println("Сервер в данный момент не доступен");
                System.out.println("Хотите изменить имя хоста и порт? Введите \"да\" или \"нет\"");
                System.out.print(">>> ");
                String in = scanner.nextLine().trim();
                while (!in.equals("да") && !in.equals("нет")) {
                    if (in.equals("exit")) System.exit(0);
                    System.out.println("Введите \"да\" или \"нет\"");
                    System.out.print(">>> ");
                    in = scanner.nextLine().trim();
                }
                if (in.equals("да")) new Client().run();
                if (in.equals("нет") && counter < 1) {

                    System.out.println("Сервер в данный момент не доступен. Повторное подключение");
                    counter++;

                }

            }
        }
    }

    private static boolean connect(Scanner scanner, String hostname, int port) throws IOException {
        SocketChannel clientSocketChannel = SocketChannel.open();
        clientSocketChannel.connect(new InetSocketAddress(hostname, port));

        System.out.println("Добро пожаловать! Введите \"help\" для получения информации по всем доступным командам");

        try (ObjectOutputStream out = new ObjectOutputStream(clientSocketChannel.socket().getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocketChannel.socket().getInputStream())) {
            while (true) {
                System.out.print(">>> ");
                String[] input = null;
                try {
                    input = scanner.nextLine().trim().split(" ");
                } catch (Exception e) {
                    String m = e.getMessage();
                    if (m.equals("No line found")) {
                        System.out.println(e.getMessage());
                        new Client().run();
                    }
                }
                String commandName = input[0];
                String commandArgs = "";
                if (input.length == 2) commandArgs = input[1];

                Request request = null;
                if (commandName.equals("add") || commandName.equals("update") || commandName.equals("add_if_min")) {
                    Movie objArgument = UserInputGetter.getMovieInput();
                    request = new Request(commandName, commandArgs, objArgument);
                } else if (commandName.equals("exit")) {
                    System.out.println("До свидания!");
                    request = new Request(commandName, commandArgs);
                    out.writeObject(request);
                    out.flush();
                    System.exit(0);
                } else if (commandName.equals("execute_script")) {
                    commandArgs = scriptHandler(commandArgs);
                    request = new Request(commandName, commandArgs);
                } else {
                    request = new Request(commandName, commandArgs);
                }

                out.writeObject(request);
                out.flush();

                try {
                    Response response = (Response) in.readObject();
                    System.out.println(response.getMessage());
                } catch (ClassNotFoundException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }
    private static String scriptHandler(String commandArgs) {
        List<String> scriptLines = null;
        try {
            scriptLines = Files.readAllLines(Paths.get(commandArgs));
        } catch (IOException e) {
            return "Ошибка: Файла с таким названием не существует";
        }
        StringBuilder script = new StringBuilder();
        script.append(commandArgs).append("\n");
        if (scriptLines == null) {
            return "Ошибка: Вы не передали название файла";
        }
        for (String line: scriptLines) {
            if (line.contains("execute_script")) {
                String[] scriptLine = line.trim().split(" ");
                if (scriptLine.length < 2) return "Ошибка: Вы не передали название файла";
                if (scriptLine[1].equals(commandArgs)) return "Ошибка: Невозможно выполнить рекурсию";
                else {
                    List<String> scriptLines2 = null;
                    try {
                        scriptLines2 = Files.readAllLines(Paths.get(scriptLine[1]));
                    } catch (IOException e) {
                        return "Ошибка: Файла с таким названием не существует";
                    }
                    StringBuilder script2 = new StringBuilder();
                    script2.append(scriptLine[1]).append("\n");
                    if (scriptLines2 == null) {
                        return "Ошибка: Вы не передали название файла";
                    }
                    for (String line2 : scriptLines2) {
                        if (line2.contains("execute_script")) {
                            String[] scriptLine2 = line2.trim().split(" ");
                            if (scriptLine2.length < 2) return "Ошибка: Вы не передали название файла";
                            if (scriptLine2[1].equals(commandArgs) || scriptLine2[1].equals(scriptLine[1])) return "Ошибка: Невозможно выполнить рекурсию";
                            else scriptHandler(scriptLine2[1]);
                        }
                        script2.append(line2).append("\n");
                    }
                    return script2.toString();
                }
            }
            script.append(line).append("\n");
        }
        return script.toString();
    }
}