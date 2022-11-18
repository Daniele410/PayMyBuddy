package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserEmailNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public UserEmailNotFoundException(String message) {
        super(message);

    }




}
