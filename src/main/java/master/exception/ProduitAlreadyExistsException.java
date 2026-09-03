package master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProduitAlreadyExistsException extends RuntimeException {

    public ProduitAlreadyExistsException(String message) {
        super(message);
    }
}