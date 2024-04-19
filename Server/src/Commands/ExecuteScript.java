package Commands;

import Collection.Movie;
import Utilities.Invoker;
import Utilities.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import static Utilities.Server.scriptScanner;
public class ExecuteScript implements Command{



    @Override
    public Response execute(String args, Movie objArgs) {


        try {
            scriptScanner = new Scanner(new File(args));
            Map<String, Command> commands = Invoker.getCommands();

            while (scriptScanner.hasNextLine()) {

                String[] input = scriptScanner.nextLine().trim().split(" ");
                String userCommand = input[0];
                String userArgs = "";
                if (input.length == 2) userArgs = input[1];
                if (userCommand.equals("execute_script")) {
                    if (userArgs.equals(args)) {
                        return new Response("Не возможно выполнить рекурсию");
                    } else try {
                        scriptScanner = new Scanner(new File(userArgs));

                        while (scriptScanner.hasNextLine()) {

                            String[] input1 = scriptScanner.nextLine().trim().split(" ");
                            String userCommand1 = input1[0];
                            String userArgs1 = "";
                            if (input1.length == 2) userArgs1 = input1[1];
                            if (userCommand1.equals("execute_script")) {
                                if (userArgs1.equals(args) || userArgs1.equals(userArgs)) {
                                    return new Response("Не возможно выполнить рекурсию");
                                } else new ExecuteScript().execute(userArgs1, objArgs);
                                return new Response("");

                            }
                            if (commands.containsKey(userCommand1)) {
                                if (userCommand1.equals("add") || userCommand1.equals("add_if_min") || userCommand1.equals("update")) {
                                    return commands.get(userCommand1).executeInScript(userArgs1, objArgs);
                                } else return commands.get(userCommand1).execute(userArgs1, objArgs);
                            }
                        }

                    } catch (FileNotFoundException e) {
                        return new Response("Невозможно выполнить рекурсию или файла не существует");
                    }
                }
                if (commands.containsKey(userCommand)) {
                    if (userCommand.equals("add") || userCommand.equals("add_if_min") || userCommand.equals("update")) {
                        return commands.get(userCommand).executeInScript(userArgs, objArgs);
                    } else return commands.get(userCommand).execute(userArgs, objArgs);
                }
            }

        } catch (FileNotFoundException e) {
            return new Response("Невозможно выполнить рекурсию или файла не существует");
        }
    return new Response("");
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
