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

        scriptScanner = new Scanner(args);
        Map<String, Command> commands = Invoker.getCommands();
        StringBuilder message = new StringBuilder();
        while (scriptScanner.hasNextLine()) {

            String[] input = scriptScanner.nextLine().trim().split(" ");
            String userCommand = input[0];
            String userArgs = "";
            if (input.length == 2) userArgs = input[1];
            if (commands.containsKey(userCommand)) {
                if (userCommand.equals("add") || userCommand.equals("add_if_min") || userCommand.equals("update")) {
                    message.append(commands.get(userCommand).executeInScript(userArgs, objArgs).getMessage()).append("\n");
                } else message.append(commands.get(userCommand).execute(userArgs, objArgs).getMessage()).append("\n");
            }
        }

        return new Response(message.toString());
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
