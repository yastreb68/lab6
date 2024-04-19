package Utilities;

import Commands.*;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public static Map<String, Command> commands = new HashMap<>();
    public static void invoke() {

        Command addCommand = new Add();
        commands.put(addCommand.getName(), addCommand);

        Command helpCommand = new Help();
        commands.put(helpCommand.getName(), helpCommand);

        Command infoCommand = new Info();
        commands.put(infoCommand.getName(), infoCommand);

        Command clearCommand = new Clear();
        commands.put(clearCommand.getName(), clearCommand);

        Command showCommand = new Show();
        commands.put(showCommand.getName(), showCommand);

        Command updateCommand = new Update();
        commands.put(updateCommand.getName(), updateCommand);

        Command removeById = new RemoveById();
        commands.put(removeById.getName(), removeById);

        Command removeFirstCommand = new RemoveFirst();
        commands.put(removeFirstCommand.getName(), removeFirstCommand);

        Command addIfMinCommand = new AddIfMin();
        commands.put(addIfMinCommand.getName(), addIfMinCommand);

        Command sortCommand = new Sort();
        commands.put(sortCommand.getName(), sortCommand);

        Command maxByIdCommand = new MaxById();
        commands.put(maxByIdCommand.getName(), maxByIdCommand);

        Command countLessThanOscarsCount = new CountLessThanOscarsCount();
        commands.put(countLessThanOscarsCount.getName(), countLessThanOscarsCount);

        Command filterStartsWithNameCommand = new FilterStartsWithName();
        commands.put(filterStartsWithNameCommand.getName(), filterStartsWithNameCommand);

        Command executeScriptCommand = new ExecuteScript();
        commands.put(executeScriptCommand.getName(), executeScriptCommand);

    }
    public static Map<String, Command> getCommands() {
        return commands;
    }

}
