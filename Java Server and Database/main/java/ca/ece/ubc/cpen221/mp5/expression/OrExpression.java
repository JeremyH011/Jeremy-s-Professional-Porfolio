package ca.ece.ubc.cpen221.mp5.expression;

import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;

public class OrExpression implements Expression {

	private Expression value1;
	private Expression value2;
	// Rep Invariant:
	// value1 and value2 cannot be null
	
	// Abstraction Function:
	// Represents value1 ored with value2
	
	/**
	 * Creates a new or Expression between value1 and value2
	 * 
	 * @param value1
	 *            the first expression of the or
	 * @param value2
	 *            the second expression of the or
	 */
	public OrExpression(Expression value1, Expression value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	@Override
	public Set<RevieweeData> eval(YelpDB data) {
		Set<RevieweeData> set1 = value1.eval(data);
		Set<RevieweeData> set2 = value2.eval(data);
		Set<RevieweeData> returnSet = new HashSet<>();
		// Adds all data from both sets
		for (RevieweeData d : set1) {
			returnSet.add(d);
		}
		for (RevieweeData d : set2) {
			returnSet.add(d);
		}
		return returnSet;
	}

	@Override
	public String toString() {
		return "(" + value1.toString() + "||" + value2.toString() + ")";
	}

}
