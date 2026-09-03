package master.exception;

public class ProduitAlreadyExistsException extends RuntimeException {

    public ProduitAlreadyExistsException(String message) {
        super(message);
    }

}