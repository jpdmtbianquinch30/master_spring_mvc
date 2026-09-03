package master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Ressource introuvable
    @ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class})
    @ResponseBody
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Ressource introuvable";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    // 400 - Erreurs de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 500 - Filet de securite pour toute autre erreur non prevue
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur interne : " + ex.getMessage());
    }

    // 409Produit existant deja
    @ExceptionHandler(ProduitAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<String> handleAlreadyExists(ProduitAlreadyExistsException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Produit déjà existant";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}