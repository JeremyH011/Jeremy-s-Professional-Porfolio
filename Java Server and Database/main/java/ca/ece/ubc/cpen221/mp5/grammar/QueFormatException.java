package ca.ece.ubc.cpen221.mp5.grammar;

public class QueFormatException extends Exception{
	// Parameterless Constructor
    public QueFormatException() {}

    // Constructor that accepts a message
    public QueFormatException(String message)
    {
       super(message);
    }
}
