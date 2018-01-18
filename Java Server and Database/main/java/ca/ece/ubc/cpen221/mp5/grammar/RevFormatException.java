package ca.ece.ubc.cpen221.mp5.grammar;

public class RevFormatException extends Exception{
	// Parameterless Constructor
    public RevFormatException() {}

    // Constructor that accepts a message
    public RevFormatException(String message)
    {
       super(message);
    }
}
