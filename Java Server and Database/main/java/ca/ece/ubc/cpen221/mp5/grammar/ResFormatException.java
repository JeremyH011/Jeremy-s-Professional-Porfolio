package ca.ece.ubc.cpen221.mp5.grammar;

public class ResFormatException extends Exception{
	// Parameterless Constructor
    public ResFormatException() {}

    // Constructor that accepts a message
    public ResFormatException(String message)
    {
       super(message);
    }
}
