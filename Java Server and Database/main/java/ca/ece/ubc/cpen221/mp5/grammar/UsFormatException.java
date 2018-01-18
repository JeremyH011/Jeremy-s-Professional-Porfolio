package ca.ece.ubc.cpen221.mp5.grammar;

public class UsFormatException extends Exception{
	// Parameterless Constructor
    public UsFormatException() {}

    // Constructor that accepts a message
    public UsFormatException(String message)
    {
       super(message);
    }
}
