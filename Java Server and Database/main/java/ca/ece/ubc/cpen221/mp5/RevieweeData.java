package ca.ece.ubc.cpen221.mp5;

public class RevieweeData extends Data {

	/**
	 * The constructor
	 */
	public RevieweeData() {
		super("reviewee");
	}

	/**
	 * Gets the price rating of the reviewee
	 * 
	 * @return the price rating as an integer between 1 and 5
	 */
	public double getPrice() {
		return Double.valueOf(super.getInfo("price").toString());
	}
	
	/**
	 * Gets the price rating of the reviewee
	 * 
	 * @return the price rating as an integer between 1 and 5
	 */
	public double getRating() {
		return Double.valueOf(super.getInfo("stars").toString());
	}
}
