package exceptions;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;

public class MyException extends BadRequestException {
    public MyException() {
        super();
    }
    public MyException(String message) {
        super(message);
    }
}
