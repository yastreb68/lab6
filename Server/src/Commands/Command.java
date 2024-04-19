package Commands;

import Collection.Movie;
import Utilities.Response;
import Utilities.Server;

import java.util.ArrayList;

public interface Command {
    Response execute(String args, Movie objArgs);
    String getName();
    ArrayList<Movie> movies = Server.getMovies();
    boolean inScript = false;
    
    default Response executeInScript(String userArgs, Movie objArgs) {
        return null;
    }
}
