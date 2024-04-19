package Commands;

import Collection.Movie;
import Utilities.Response;


public class RemoveById implements Command{


    public Response execute(String args, Movie objArgs) {
        if (args.isEmpty()) {
            return new Response("Команда remove_by_id требует аргументы");
        } else {
            int idToRemove = Integer.parseInt(args);
            long countBefore = movies.stream()
                    .filter(movie -> movie.getId() == idToRemove)
                    .count();

            movies.removeIf(movie -> movie.getId() == idToRemove);

            long countAfter = movies.stream()
                    .filter(movie -> movie.getId() == idToRemove)
                    .count();

            if (countBefore == 0) {
                return new Response("Movie с таким id не существует");
            } else if (countAfter == 0) {
                return new Response("Movie с таким id успешно удалён");
            } else {
                return new Response("Произошла ошибка при удалении Movie");
            }
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
}
