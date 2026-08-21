package master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class})
    @ResponseBody
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Ressource introuvable";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}