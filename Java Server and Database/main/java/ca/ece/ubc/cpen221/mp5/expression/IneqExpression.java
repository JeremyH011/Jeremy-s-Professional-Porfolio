package ca.ece.ubc.cpen221.mp5.expression;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;

public class IneqExpression implements Expression {

	private String operator;
	private int num;
	private String operand;
	// Rep invariant:
	// Operator must be a valid operation and operand must be a valid expression
	// while 1 < num < 5

	// Abstraction Function:
	// represents an inequality expression where operand is the value being
	// compared against num using the operator

	/**
	 * Creates a new Inequality Expression
	 * 
	 * @param operator
	 *            the inequality expression you want to perform, must be either >,
	 *            >=, =, <=, or <
	 * @param operand
	 *            the value that you want to compare inside the data, the value
	 *            associated with the operand must be convertible to an integer
	 * @param num
	 *            the integer number that you want to check against
	 */
	public IneqExpression(String operator, String operand, int num) {
		this.operator = operator;
		this.operand = operand;
		this.num = num;
	}

	@Override
	public Set<RevieweeData> eval(YelpDB data) {
		Set<RevieweeData> returnSet = new HashSet<>();
		Stream<RevieweeData> getRes = data.getRes().stream();

		// Uses streams to check each of the conditions
		if (operand.equals("price")) {
			if (operator.equals(">")) {
				returnSet.addAll(getRes.filter(s -> s.getPrice() > num).collect(Collectors.toSet()));
			} else if (operator.equals(">=")) {
				returnSet.addAll(getRes.filter(s -> s.getPrice() >= num).collect(Collectors.toSet()));
			} else if (operator.equals("<")) {
				returnSet.addAll(getRes.filter(s -> s.getPrice() < num).collect(Collectors.toSet()));
			} else if (operator.equals("<=")) {
				returnSet.addAll(getRes.filter(s -> s.getPrice() <= num).collect(Collectors.toSet()));
			} else if (operator.equals("=")) {
				returnSet.addAll(getRes.filter(s -> s.getPrice() == num).collect(Collectors.toSet()));
			}
		} else if (operand.equals("rating")) {
			if (operator.equals(">")) {
				returnSet.addAll(getRes.filter(s -> s.getRating() > num).collect(Collectors.toSet()));
			} else if (operator.equals(">=")) {
				returnSet.addAll(getRes.filter(s -> s.getRating() >= num).collect(Collectors.toSet()));
			} else if (operator.equals("<")) {
				returnSet.addAll(getRes.filter(s -> s.getRating() < num).collect(Collectors.toSet()));
			} else if (operator.equals("<=")) {
				returnSet.addAll(getRes.filter(s -> s.getRating() <= num).collect(Collectors.toSet()));
			} else if (operator.equals("=")) {
				returnSet.addAll(getRes.filter(s -> s.getRating() == num).collect(Collectors.toSet()));
			}
		}

		return returnSet;
	}

	@Override
	public String toString() {
		return operand + operator + num;
	}

}
