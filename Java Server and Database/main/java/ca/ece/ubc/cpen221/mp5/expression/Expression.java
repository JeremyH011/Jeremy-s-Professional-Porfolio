package ca.ece.ubc.cpen221.mp5.expression;

import java.util.Set;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;

/**
 * Expression - A logic expression.
 *
 */
public interface Expression {

	/**
	 * Evaluates a logic expression inside the database.
	 * 
	 * @return the Set of RevieweeData that this evaluates to according the the
	 *         constraints and database
	 */
	Set<RevieweeData> eval(YelpDB data);

}
