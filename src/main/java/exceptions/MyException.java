package exceptions;

import javax.ws.rs.WebApplicationException;

public class MyException extends WebApplicationException {
    public MyException() {
        super();
    }
    public MyException(String message) {
        super(message);
    }
}
