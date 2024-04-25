package Utilities;


import java.io.Serial;
import java.io.Serializable;


public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 52L;
    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
