package Commands;

import Collection.Movie;
import Utilities.Response;
import Utilities.Server;


public class Info implements Command{
    public Response execute(String args, Movie objArgs) {
        return new Response("Информация о коллекции:" + "\n" +
                "Тип коллекции: " + Server.getMovies().getClass().getSimpleName() + "\n" +
                "Дата инициализации: " + Server.getInitializationDate() + "\n" +
                "Количество элементов: " + Server.getMovies().size());

    }
    @Override
    public String getName() {
        return "info";
    }
}
