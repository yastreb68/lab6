package Commands;

import Collection.Movie;
import Utilities.Response;

public class RemoveFirst implements Command{
    public Response execute(String args, Movie objArgs) {

        if (!movies.isEmpty()) {
            movies.remove(0);
            return new Response("Первый элемент коллекции успешно удалён");
        } else return new Response("Невозможно удалить первый элемент коллекции, так ка она пустая");
    }

    @Override
    public String getName() {
        return "remove_first";
    }
}
