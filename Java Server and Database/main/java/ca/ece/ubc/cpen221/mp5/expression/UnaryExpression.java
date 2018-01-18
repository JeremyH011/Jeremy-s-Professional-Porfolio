package ca.ece.ubc.cpen221.mp5.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;

public class UnaryExpression implements Expression {

	private String operator;
	private String operand;
	// Rep invariant:
	// Operator must exist in the data

	// Abstraction Function:
	// represents a unary expression where it evaluates to any data that matches the
	// operand in the operator field

	/**
	 * Creates a new UnaryExpression
	 * 
	 * @param operation
	 *            the operation you want to perform, must be a valid expression
	 *            cannot be null
	 * @param value
	 *            the expression value that the operation performs on, cannot be
	 *            null
	 */
	public UnaryExpression(String operator, String operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public Set<RevieweeData> eval(YelpDB data) {
		Set<RevieweeData> returnSet = new HashSet<RevieweeData>();
		Set<RevieweeData> dataSet = data.getResMatches(operand);
		// System.out.println(operator);
		// System.out.println(operand);
		if (operator.equals("categories") || operator.equals("neighborhoods")) {
			// Checks if the data is matching the correct operator
			for (RevieweeData d : dataSet) {
				// System.out.println(d.getInfo("categories"));
				Object test = d.getInfo(operator);
				if(test instanceof ArrayList<?>) {
					for(String s : ((ArrayList<String>) test)) {
						if(s.equalsIgnoreCase(operand)) {
							returnSet.add(d);
						}
					}
				}
				
			}
		} else {
			// Checks if the data is matching the correct operator
			for (RevieweeData d : dataSet) {
				// System.out.println(d.getInfo("categories"));
				if (((String) d.getInfo(operator)).equalsIgnoreCase(operand)) {
					returnSet.add(d);
				}
			}
		}

		return returnSet;
	}

	@Override
	public String toString() {
		return operator + "(" + operand + ")";
	}

}
