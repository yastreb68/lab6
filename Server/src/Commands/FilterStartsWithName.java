package Commands;

import Collection.Movie;
import Utilities.Response;

import java.util.stream.Collectors;

public class FilterStartsWithName implements Command{


    @Override
    public Response execute(String args, Movie objArgs) {
        if (args.isEmpty()) {
            return new Response("Команда filter_starts_with_name требует аргументы");
        } else {
            long counter = movies.stream()
                    .filter(movie -> movie.getName().startsWith(args))
                    .count();

            if (counter == 0) return new Response("Movie с полями name, начинающимися на " + args + ", нет");
        }

        return new Response(movies.stream()
                .map(Movie::getName)
                .filter(name -> name.startsWith(args))
                .collect(Collectors.joining(", ")));

    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }
}
