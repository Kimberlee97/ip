package lumi.exceptions;

/**
 * This class represents an exception specific to the Lumi application.
 * This exception is thrown when Lumi encounters an error related to
 * invalid input, storage issues, or other application specific problems.
 * It extends {@link Exception}.
 */
public class LumiException extends Exception {
    /**
     * Constructs a new LumiException with the specified message.
     * @param message The message that describes the nature of the exception.
     */
    public LumiException(String message) {
        super(message);
    }
}
