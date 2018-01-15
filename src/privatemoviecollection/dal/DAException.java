package privatemoviecollection.dal;

/**
 * Represents an exception thrown in the DAL. Most often thrown when a database
 * operation fails
 *
 * @author sebok
 */
public class DAException extends Exception {

    /**
     * Constructs a new exception using the parameter as the message
     *
     * @param message The message of the exception
     */
    public DAException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAException using a message from another exception
     *
     * @param ex The exception from which the message will be retrieved
     */
    public DAException(Exception ex) {
        super(ex.getMessage());
    }

    /**
     * Gets the message of the exception
     *
     * @return The message of the exception
     */
    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }
}
