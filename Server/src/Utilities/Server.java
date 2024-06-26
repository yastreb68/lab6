package Utilities;
import Collection.Movie;
import Commands.Command;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static Scanner scriptScanner = null;
    private static ArrayList<Movie> movies = new ArrayList<>();
    private static LocalDateTime initializationDate = LocalDateTime.now();
    private static String csvPath = System.getenv("CSV");

    private int port;

    public static String getCsvPath() {
        return csvPath;
    }

    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public static LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public Server() throws IOException {
        Invoker.invoke();
        try {
            CSVConverter.CSVToArrayList(movies, csvPath);
        } catch (Exception e) {
            System.out.println("Недостаточно прав доступа или файла не существует");
            System.exit(0);
        }
    }

    private static final Logger logger = Logger.getLogger("Server");

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите порт: ");
            port = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Такого порта не существует. Повторите ввод");
            new Server().run();
        }
        Thread inputThread = new Thread(() -> {
            Scanner inputThreadScanner = new Scanner(System.in);
            while (true) {
                String input = inputThreadScanner.nextLine().trim();
                if (input.equals("exit")) {
                    save();
                    System.exit(0);
                } else if (input.equals("save")) {
                    save();
                }
            }
        });
        inputThread.start();
        Map<String, Command> commands = Invoker.getCommands();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.log(Level.INFO, "Сервер запущен на порте " + port);
            while (true) {
                Socket clientSocket = null;
                ObjectInputStream objectInputStream = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    clientSocket = serverSocket.accept();
                    logger.log(Level.INFO, "Подключился новый клиент: " + clientSocket.getInetAddress());
                    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    Response response;
                    while (true) {
                        Request request = (Request) objectInputStream.readObject();
                        logger.log(Level.INFO, "Получен запрос от клиента: " + request);
                        String commandName = request.getCommandName();
                        String commandStrArg = request.getCommandStrArg();
                        Movie commandObjArg = (Movie) request.getCommandObjArg();

                        if (commands.containsKey(commandName) && !commandStrArg.contains("Ошибка")) {
                            response = commands.get(commandName).execute(commandStrArg, commandObjArg);
                        } else if (commandStrArg.contains("Ошибка")) {
                            response = new Response(commandStrArg);
                        } else {
                            response = new Response("Команды " + commandName + " не существует.");
                        }

                        objectOutputStream.writeObject(response);
                        objectOutputStream.flush();
                        logger.log(Level.INFO, "Отправлен ответ клиенту: " + response.getMessage());
                        if (commandName.equals("exit")) {
                            logger.log(Level.INFO, "Клиент отключился");
                            break;
                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Ошибка: " + e.getMessage());
                } finally {
                    try {
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (clientSocket != null) {
                            clientSocket.close();
                        }
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, "Ошибка: " + ex.getMessage());
                    }
                }
            }
        } catch (BindException e) {
            logger.log(Level.SEVERE, "Невозможно запустить сервер на данном порту, так как он занят: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка: " + e.getMessage());
        }
    }

    private static void save() {
        try {
            CSVConverter.ArrayListToCSV(movies, Server.getCsvPath());
            logger.log(Level.INFO, "Коллекция успешно сохранена");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }
}