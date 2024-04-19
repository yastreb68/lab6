package Utilities;

import Collection.Movie;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    private final InetAddress hostname;
    private final int port;

    public Client(String hostname, int port) throws UnknownHostException {
        this.hostname = InetAddress.getByName(hostname);
        this.port = port;
    }
    public Client(InetAddress hostname, int port) throws UnknownHostException {
        this.hostname = hostname;
        this.port = port;
    }
    public void run() {
        try (SocketChannel clientSocketChannel = SocketChannel.open()) {
            clientSocketChannel.connect(new InetSocketAddress(hostname, port));

            Scanner scanner = new Scanner(System.in);

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
                            new Client(hostname, port).run();
                        }
                    }
                    String commandName = input[0];
                    String commandArgs = "";
                    if (input.length == 2) commandArgs = input[1];

                    Request request = null;
                    if (commandName.equals("add") || commandName.equals("update") || commandName.equals("add_if_min")){
                        Movie objArgument = UserInputGetter.getMovieInput();
                        request = new Request(commandName, commandArgs, objArgument);
                    }
                    else if (commandName.equals("exit")){
                        System.out.println("До свидания!");
                        request = new Request(commandName, commandArgs);
                        out.writeObject(request);
                        out.flush();
                        System.exit(0);
                    }
                    else {
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
            } catch (IOException e) {
                System.err.println("Сервер в данный момент недоступен. Повторите попытку позже: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при подключении к серверу. Повторите попытку позже: " + e.getMessage());
        }
    }
}







