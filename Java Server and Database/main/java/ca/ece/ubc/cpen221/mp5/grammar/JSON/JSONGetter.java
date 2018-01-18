package ca.ece.ubc.cpen221.mp5.grammar.JSON;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser.ArrayContext;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser.JsonContext;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser.ObjContext;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser.PairContext;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser.ValueContext;

public class JSONGetter implements JSONListener {
	String json;

	/**
	 * Gets the string representation of the JSON string
	 * 
	 * @return the json file as a string
	 */
	public String getJson() {
		return json;
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
	public void enterJson(JsonContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitJson(JsonContext ctx) {
		// Grabs the json string
		json = ctx.getText();

	}

	@Override
	public void enterObj(ObjContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitObj(ObjContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterPair(PairContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitPair(PairContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterArray(ArrayContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitArray(ArrayContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterValue(ValueContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitValue(ValueContext ctx) {
		// TODO Auto-generated method stub

	}

}
