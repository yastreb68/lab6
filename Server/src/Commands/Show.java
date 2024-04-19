package Commands;

import Collection.Movie;
import Utilities.Response;

import java.util.stream.Collectors;


public class Show implements Command{
    @Override
    public Response execute(String args, Movie objArgs) {
        String movieList = movies.stream()
                .map(Movie::toString)
                .collect(Collectors.joining("\n"));

        return new Response(movieList);
    }

    @Override
    public String getName() {
        return "show";
    }
}
