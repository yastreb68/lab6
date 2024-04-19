package Commands;

import Collection.*;
import Utilities.Response;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;

import static Utilities.Server.scriptScanner;

public class AddIfMin implements Command{

    @Override
    public Response execute(String args, Movie objArgs) {
        Movie minMovie = movies.stream()
                .min(Comparator.naturalOrder())
                .orElse(null);

        if (minMovie != null && objArgs.compareTo(minMovie) < 0) {
            movies.add(objArgs);
            return new Response("Movie успешно добавлен");
        } else {
            return new Response("Movie не добавлен, так как его значение больше или равно наименьшему элементу коллекции");
        }
    }
    public Response executeInScript(String args, Movie objArgs) {
        Movie minMovie = Collections.min(movies);
        String name = null;
        Float x = null;
        float y = 210;
        int oscarsCount = -1;
        double budget = -1;
        Long length = null;
        MpaaRating mpaaRating = null;
        String personName = null;
        String passportID = null;
        Color hairColor = null;
        try {
            name = scriptScanner.nextLine();
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации имени");
        }
        try {
            x = Float.parseFloat(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации X");
        }
        try {
            y = Float.parseFloat(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации Y");
        }
        try {
            oscarsCount = Integer.parseInt(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации количества оскаров");
        }
        try {
            budget = Double.parseDouble(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации бюджета");
        }
        try {
            length = Long.valueOf(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации длины");
        }
        try {
            mpaaRating = MpaaRating.valueOf(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации mpaa rating");
        }
        try {
            personName = scriptScanner.nextLine();
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации имени оператора");
        }
        try {
            passportID = scriptScanner.nextLine();
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации паспорта");
        }
        try {
            hairColor = Color.valueOf(scriptScanner.nextLine());
        } catch (RuntimeException e) {
            return new Response("Ошибка при инициализации цвета волос");
        }

        int errors = 0;
        if (name.isEmpty()) {
            return new Response("Имя не может быть пустым");
        }
        if (x <= -625) {
            return new Response("X должен быть больше -625");
        }
        if (y >= 209) {
            return new Response("Y должен быть меньше 209");
        }
        if (oscarsCount <= 0) {
            return new Response("Количество оскаров должен быть больше 0");
        }
        if (budget <= 0) {
            return new Response("Бюджет должен быть больше 0");
        }
        if (length <= 0) {
            return new Response("Длина должна быть больше 0");
        }
        if (personName.isEmpty()) {
            return new Response("Имя оператора не может быть пустым");
        }
        if (passportID.isEmpty()) {
            return new Response("ИД паспорта не может быть пустым");
        }
        Movie movie = null;
        if (errors == 0) {
            movie = objArgs;
            Field id = null;
            try {
                id = movie.getClass().getDeclaredField("id");
                id.setAccessible(true);
                id.set(movie, Movie.generateUniqueId());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (movie != null && movie.compareTo(minMovie) < 0) {
            movies.add(movie);
            return new Response("Movie успешно добавлен");
        } else {
            return new Response("Movie не добавлен, так как его значение больше или равно наименьшему элементу коллекции");
        }
    }

    @Override
    public String getName() {
        return "add_if_min";
    }
}
