package ca.ece.ubc.cpen221.mp5;

public interface Database {
	/**
	 * Gets the data that is associated with the ID found in the database
	 * 
	 * @param ID
	 *            the unique ID of the data, data must be in the database and ID
	 *            cannot be null
	 * @return the Data that is associated with ID
	 */
	public Data getEntry(String ID);
}
