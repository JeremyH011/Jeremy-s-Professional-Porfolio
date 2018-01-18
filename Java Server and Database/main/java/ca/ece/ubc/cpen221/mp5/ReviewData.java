package ca.ece.ubc.cpen221.mp5;

public class ReviewData extends Data {
	// Rep invariant: Must have fields inside the reviewData that represents the ID
	// of the review, reviewee, and reviewer
	// Abstraction Function: Represents a chunk of data grouped together where each
	// entry in the HashMap specifies a property of the object that the data
	// describes.

	public ReviewData() {
		super("review");
	}

	/**
	 * Gets the id of the reviewer
	 * 
	 * @return the string ID of the reviewer
	 */
	public String getReviewer() {
		return (String) super.getInfo("user_id");
	}

	/**
	 * Stores the id of the reviewer
	 * 
	 * @param user_id
	 *            the id of the reviewer
	 */
	public void putReviewer(String user_id) {
		super.storeInfo("user_id", user_id);
	}

	/**
	 * Gets the id of the reviewee
	 * 
	 * @return the string ID of the reviewee
	 */
	public String getReviewee() {
		return (String) super.getInfo("business_id");
	}

	/**
	 * Stores the id of the reviewee
	 * 
	 * @param business_id
	 *            the string ID of the reviewee
	 */
	public void putReviewee(String business_id) {
		super.storeInfo("business_id", business_id);
	}

	/**
	 * Gets the rating of the reviewee by the reviewer
	 * 
	 * @return the rating as an integer between 1 and 5
	 */
	public double getRating() {
		return Double.valueOf(super.getInfo("stars").toString());
	}
}
