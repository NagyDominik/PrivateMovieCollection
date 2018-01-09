/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

/**
 *
 * @author Dominik
 */
public class BLLException extends Exception {

    /**
     * Constructs a new exception using the parameter string as the message
     *
     * @param message The string that will be used as the message
     */
    public BLLException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception using the message from another exception
     *
     * @param ex The exception from which the message will be used
     */
    public BLLException(Exception ex) {
        super(ex.getMessage());
    }

    /**
     * Returns the message stored in this exception
     *
     * @return The message stored in this exception
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
