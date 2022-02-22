package local.jrmmba.points.exceptions;

/**
 * A custom exception to be used when someone tries to spend more points then they have
 */
public class InsufficientFundsException
        extends RuntimeException
{
    public InsufficientFundsException(String message)
    {
        super("Error from the Points Application " + message);
    }
}
