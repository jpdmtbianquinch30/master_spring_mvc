package master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Leve quand on tente de supprimer un Type auquel des Products sont encore rattaches
@ResponseStatus(HttpStatus.CONFLICT)
public class TypeHasProductsException extends RuntimeException {

    public TypeHasProductsException(String message) {
        super(message);
    }
}
