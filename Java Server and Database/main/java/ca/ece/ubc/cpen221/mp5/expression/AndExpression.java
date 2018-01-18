package ca.ece.ubc.cpen221.mp5.expression;

import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;

public class AndExpression implements Expression {

	private Expression value1;
	private Expression value2;
	// Rep Invariant:
	// value1 and value2 cannot be null

	// Abstraction Function:
	// Represents value1 anded with value2

	/**
	 * Creates a new and Expression between value1 and value2
	 * 
	 * @param value1
	 *            the first expression of the and
	 * @param value2
	 *            the second expression of the and
	 */
	public AndExpression(Expression value1, Expression value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	@Override
	public Set<RevieweeData> eval(YelpDB data) {
		Set<RevieweeData> set1 = value1.eval(data);
		Set<RevieweeData> set2 = value2.eval(data);
		Set<RevieweeData> returnSet = new HashSet<>();
		// Only add it if its in both sets
		for (RevieweeData d : set1) {
			if (set2.contains(d)) {
				returnSet.add(d);
			}
		}
		return returnSet;
	}

	@Override
	public String toString() {
		return "(" + value1.toString() + "&&" + value2.toString() + ")";
	}

}
