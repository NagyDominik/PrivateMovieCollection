package privatemoviecollection.dal;

/**
 * Represents an exception thrown in the DAL. Most often thrown when a database operation fails
 * @author sebok
 */
public class DAException extends Exception {

    /**
     * Construct a new exception using the parameter as the message
     * @param message The message of the exception
     */
    public DAException(String message) {
        super(message);
    }
    
    /**
     * Construct a new DAException using a message from another exception
     * @param ex 
     */
    public DAException(Exception ex)
    {
        super(ex.getMessage());
    }
    
    /**
     * Get the message of the exception
     * @return The message of the exception
     */
    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }
}
