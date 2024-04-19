package Utilities;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 5252L;
    private String commandName;
    private String commandStrArg;
    private Serializable commandObjArg;

    public Request(String commandName, String commandStrArg, Serializable commandObjArg) {
        this.commandName = commandName;
        this.commandStrArg = commandStrArg;
        this.commandObjArg = commandObjArg;
    }

    public Request(String commandName, String commandStrArg) {
        this(commandName, commandStrArg, null);
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandStrArg() {
        return commandStrArg;
    }

    public Serializable getCommandObjArg() {
        return commandObjArg;
    }

    @Override
    public String toString() {
        return "Request{" +
                "commandName='" + commandName + '\'' +
                ", commandStrArg='" + commandStrArg + '\'' +
                ", commandObjArg=" + commandObjArg +
                '}';
    }
}
