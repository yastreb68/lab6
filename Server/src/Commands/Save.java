package Commands;

import Collection.Movie;
import Utilities.CSVConverter;
import Utilities.Response;
import Utilities.Server;

import java.io.IOException;

public class Save implements Command{
    public Response execute(String args, Movie objArgs) {
        try {
            CSVConverter.ArrayListToCSV(movies, Server.getCsvPath());
            return new Response("Коллекция успешно сохранена");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "save";
    }
}
