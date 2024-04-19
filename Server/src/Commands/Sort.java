package Commands;

import Collection.Movie;
import Utilities.Response;

import java.util.Collections;

public class Sort implements Command{
    @Override
    public Response execute(String args, Movie objArgs) {
        Collections.sort(movies);
        return new Response("Коллекция успешно отсортирована");
    }

    @Override
    public String getName() {
        return "sort";
    }
}
