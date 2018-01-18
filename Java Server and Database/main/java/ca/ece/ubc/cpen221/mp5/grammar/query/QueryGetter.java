package ca.ece.ubc.cpen221.mp5.grammar.query;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import ca.ece.ubc.cpen221.mp5.expression.AndExpression;
import ca.ece.ubc.cpen221.mp5.expression.Expression;
import ca.ece.ubc.cpen221.mp5.expression.IneqExpression;
import ca.ece.ubc.cpen221.mp5.expression.OrExpression;
import ca.ece.ubc.cpen221.mp5.expression.UnaryExpression;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.AndexprContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.AtomContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.CategoryContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.InContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.IneqContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.NameContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.OrexprContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.PriceContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.QueryContext;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser.RatingContext;

public class QueryGetter implements QueryListener {
	private Expression lang;
	private Stack<Expression> stack;
	private String ineq;

	/**
	 * Gets the expression representation of the input query
	 * 
	 * @return the input query as an expression
	 */
	public Expression getLang() {
		return lang;
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterQuery(QueryContext ctx) {
		// Starts the query
		stack = new Stack<Expression>();

	}

	@Override
	public void exitQuery(QueryContext ctx) {
		// Ends the query
		lang = stack.pop();

	}

	@Override
	public void enterOrexpr(OrexprContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitOrexpr(OrexprContext ctx) {
		// Checks if is compounded atoms and has items
		String test = ctx.getText().trim();

		String[] temp = test.split("\\|\\|");
		if (temp.length > 1 && stack.size() > 1) {
			// If so, or all the compounded stuff together
			for (int i = 1; i < temp.length; i++) {
				stack.push(new OrExpression(stack.pop(), stack.pop()));
			}
		}
	}

	@Override
	public void enterAndexpr(AndexprContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitAndexpr(AndexprContext ctx) {
		// Checks if it is compounded atoms and has items
		String test = ctx.getText().trim();

		String[] temp = test.split("\\&\\&");
		if (temp.length > 1 && stack.size() > 1) {
			// If so, and all the compounded stuff together
			for (int i = 1; i < temp.length; i++) {
				stack.push(new AndExpression(stack.pop(), stack.pop()));
			}
		}
	}

	@Override
	public void enterAtom(AtomContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitAtom(AtomContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterIneq(IneqContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitIneq(IneqContext ctx) {
		if (ctx.getText() != null) {
			ineq = ctx.getText();
		}

	}

	@Override
	public void enterIn(InContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitIn(InContext ctx) {
		String test = ctx.getText();

		// Gets the text inside the atom
		String[] temp = test.split("\\(");
		String text = temp[1].replaceAll("\\)", "").trim();
		stack.push(new UnaryExpression("neighborhoods", text));

	}

	@Override
	public void enterCategory(CategoryContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitCategory(CategoryContext ctx) {
		String test = ctx.getText();

		// Gets the text inside the atom
		String[] temp = test.split("\\(");
		String text = temp[1].replaceAll("\\)", "").trim();
		stack.push(new UnaryExpression("categories", text));

	}

	@Override
	public void enterName(NameContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitName(NameContext ctx) {
		String test = ctx.getText();

		// Gets the text inside the atom
		String[] temp = test.split("\\(");
		String text = temp[1].replaceAll("\\)", "").trim();
		stack.push(new UnaryExpression("name", text));
	}

	@Override
	public void enterRating(RatingContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitRating(RatingContext ctx) {
		String test = ctx.getText();

		// Gets the number in the inequality
		String[] temp = test.split(ineq);
		String num = temp[1].trim();
		int number = Integer.valueOf(num);
		stack.push(new IneqExpression(ineq, "rating", number));
	}

	@Override
	public void enterPrice(PriceContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitPrice(PriceContext ctx) {
		String test = ctx.getText();

		// Gets the number in the inequality
		String[] temp = test.split(ineq);
		String num = temp[1].trim();
		int number = Integer.valueOf(num);
		stack.push(new IneqExpression(ineq, "price", number));
	}

}
