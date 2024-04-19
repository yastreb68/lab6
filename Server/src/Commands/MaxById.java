package Commands;

import Collection.Movie;
import Utilities.Response;

import java.util.Comparator;

public class MaxById implements Command{
    @Override
    public Response execute(String args, Movie objArgs) {
        Movie maxMovie = movies.stream()
                .max(Comparator.comparingInt(Movie::getId))
                .orElse(null);

        if (maxMovie != null) {
            return new Response("Movie с максимальным id:" + "\n" + maxMovie);
        }

        return new Response("Список фильмов пуст");
    }

    @Override
    public String getName() {
        return "max_by_id";
    }
}
