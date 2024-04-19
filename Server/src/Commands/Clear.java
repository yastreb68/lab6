package Commands;


import Collection.Movie;
import Utilities.Response;

public class Clear implements Command{
    public Response execute(String args, Movie objArgs) {
        movies.clear();
        return new Response("Коллекция успешно очищена");
    }

    @Override
    public String getName() {
        return "clear";
    }
}
