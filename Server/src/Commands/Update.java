package Commands;

import Collection.*;
import Utilities.Response;

import java.lang.reflect.Field;

import static Utilities.Server.scriptScanner;

public class Update implements Command{

    @Override
    public Response execute(String args, Movie objArgs) {
        int counter = 0;
        for (Movie movie : movies) {
            if (args.isEmpty()) {
                System.out.println("Команда update требует аргументы");
                break;
            } else {
                if (movie.getId() == Integer.parseInt(args)) {
                    try {
                        Field id = objArgs.getClass().getDeclaredField("id");
                        id.setAccessible(true);
                        id.set(objArgs, movie.getId());

                        movies.remove(movie);
                        movies.add(objArgs);
                        counter++;
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!args.isEmpty()) {
                    if (counter == 0) return new Response("Movie с таким id не существует");
                    else return new Response("Movie успешно обновлён");
                }
            }
        }
        return new Response("");
    }
    public Response executeInScript(String args, Movie objArgs) {
        if (args.isEmpty()) {
            return new Response("Команда update требует аргументы");
        } else {
            int counter = 0;
            for (Movie movie : movies) {
                if (movie.getId() == Integer.parseInt(args)) {
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
                    if (errors == 0) {
                        try {
                            Field fieldName = movie.getClass().getDeclaredField("name");
                            fieldName.setAccessible(true);
                            fieldName.set(movie, name);

                            Field fieldX = movie.getCoordinates().getClass().getDeclaredField("x");
                            fieldX.setAccessible(true);
                            fieldX.set(movie.getCoordinates(), x);

                            Field fieldY = movie.getCoordinates().getClass().getDeclaredField("y");
                            fieldY.setAccessible(true);
                            fieldY.set(movie.getCoordinates(), y);

                            Field fieldOscarsCount = movie.getClass().getDeclaredField("oscarsCount");
                            fieldOscarsCount.setAccessible(true);
                            fieldOscarsCount.set(movie, oscarsCount);

                            Field fieldBudget = movie.getClass().getDeclaredField("budget");
                            fieldBudget.setAccessible(true);
                            fieldBudget.set(movie, budget);

                            Field fieldLength = movie.getClass().getDeclaredField("length");
                            fieldLength.setAccessible(true);
                            fieldLength.set(movie, length);

                            Field fieldMpaaRating = movie.getClass().getDeclaredField("mpaaRating");
                            fieldMpaaRating.setAccessible(true);
                            fieldMpaaRating.set(movie, mpaaRating);

                            Field fieldPersonName = movie.getOperator().getClass().getDeclaredField("name");
                            fieldPersonName.setAccessible(true);
                            fieldPersonName.set(movie.getOperator(), personName);

                            Field fieldPassportID = movie.getOperator().getClass().getDeclaredField("passportID");
                            fieldPassportID.setAccessible(true);
                            fieldPassportID.set(movie.getOperator(), passportID);

                            Field fieldHairColor = movie.getOperator().getClass().getDeclaredField("hairColor");
                            fieldHairColor.setAccessible(true);
                            fieldHairColor.set(movie.getOperator(), hairColor);
                            counter++;
                            break;
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (counter == 0) return new Response("Movie с таким id не существует");
            else return new Response("Movie успешно обновлён");
        }
    }

    @Override
    public String getName() {
        return "update";
    }
}
