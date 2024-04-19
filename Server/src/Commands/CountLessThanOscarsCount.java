package Commands;

import Collection.Movie;
import Utilities.Response;

public class CountLessThanOscarsCount implements Command{


    public Response execute(String args, Movie objArgs) {
        if (args.isEmpty()) {
            return new Response("Команда count_less_than_oscars_count требует аргументы");
        } else {
            try {
                int oscars = Integer.parseInt(args);

                long counter = movies.stream()
                        .filter(movie -> movie.getOscarsCount() < oscars)
                        .count();

                return new Response("Количество фильмов, у которых больше " + oscars + " оскара(ов): " + counter);
            } catch (Exception e) {
                return new Response("Убедитесь в правильности введенных данных и повторите ввод. Ошибка: " + e.getMessage());
            }

        }
    }

    @Override
    public String getName() {
        return "count_less_than_oscars_count";
    }
}
