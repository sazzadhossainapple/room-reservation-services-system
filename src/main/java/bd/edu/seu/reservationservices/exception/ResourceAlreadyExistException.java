package bd.edu.seu.reservationservices.exception;

public class ResourceAlreadyExistException extends Exception {
    public ResourceAlreadyExistException(String resource) {
        super(resource + "already exists!");
    }
}
