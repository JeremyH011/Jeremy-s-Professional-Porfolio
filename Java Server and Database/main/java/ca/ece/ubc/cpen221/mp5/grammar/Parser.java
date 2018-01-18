package ca.ece.ubc.cpen221.mp5.grammar;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ca.ece.ubc.cpen221.mp5.expression.Expression;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONGetter;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONLexer;
import ca.ece.ubc.cpen221.mp5.grammar.JSON.JSONParser;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryGetter;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryLexer;
import ca.ece.ubc.cpen221.mp5.grammar.query.QueryParser;

public class Parser {
	String command;
	String rest;
	String json;
	Expression lang;
	// Rep Invariant:
	// command must be a valid command such as QUERY, GETRESTAURANT, ADDUSER,
	// ADDRESTAURANT, or ADDREVIEW. json must follow the json format.

	// Abstraction Function:
	// Represents a command where the command is the action
	// followed by rest, which is the raw arguments, or json or lang if it has a
	// specialized arguments such as structure or json

	/**
	 * Creates an object that translates what the string means into its constituent
	 * parts
	 * 
	 * @param input
	 *            the String that you want to translate, cannot be null
	 * @throws IllegalArgumentException
	 *             when the command is not correct
	 * @throws QueFormatException
	 *             when the query arguments aren't correct
	 * @throws ResFormatException
	 *             when the restaurant arguments aren't correct
	 * @throws RevFormatException
	 *             when the review arguments aren't correct
	 * @throws UsFormatException
	 *             when the user arguments aren't correct
	 */
	public Parser(String input) throws QueFormatException, ResFormatException, RevFormatException, UsFormatException {
		String temp = input.trim();
		command = temp.substring(0, temp.indexOf(' ')).toUpperCase();
		rest = temp.substring(temp.indexOf(' ') + 1).trim();

		// Decides on which grammar to use on the arguments
		if (command.equals("QUERY")) {
			CharStream stream = CharStreams.fromString(rest);
			QueryLexer lexer = new QueryLexer(stream);
			TokenStream tokens = new CommonTokenStream(lexer);
			QueryParser parser = new QueryParser(tokens);
			lexer.removeErrorListeners();
			parser.removeErrorListeners();
			lexer.addErrorListener(ErrorThrowListener.INSTANCE);
			parser.addErrorListener(ErrorThrowListener.INSTANCE);
			try {
				// Walking down the tree starting at the root
				ParseTree tree = parser.query();

				// The tree walker
				ParseTreeWalker walker = new ParseTreeWalker();
				// The query translator
				QueryGetter listener = new QueryGetter();
				// Start walking and listening
				walker.walk(listener, tree);

				lang = listener.getLang();
			} catch (ParseCancellationException e) {
				throw new QueFormatException();
			}

		} else if (command.equals("ADDUSER") || command.equals("ADDRESTAURANT") || command.equals("ADDREVIEW")) {
			CharStream stream = CharStreams.fromString(rest);
			JSONLexer lexer = new JSONLexer(stream);
			TokenStream tokens = new CommonTokenStream(lexer);
			JSONParser parser = new JSONParser(tokens);
			lexer.removeErrorListeners();
			parser.removeErrorListeners();
			lexer.addErrorListener(ErrorThrowListener.INSTANCE);
			parser.addErrorListener(ErrorThrowListener.INSTANCE);

			try {
				// Walking down the tree starting at the root
				ParseTree tree = parser.json();

				// The tree walker
				ParseTreeWalker walker = new ParseTreeWalker();
				// The command getter (tree translator)
				JSONGetter listener = new JSONGetter();
				// Start walking and listening
				walker.walk(listener, tree);

				json = listener.getJson();
			} catch (ParseCancellationException e) {
				if (command.equals("ADDUSER")) {
					throw new UsFormatException();
				} else if (command.equals("ADDRESTAURANT")) {
					throw new ResFormatException();
				} else {
					throw new RevFormatException();
				}
			}
		} else if (!command.equals("GETRESTAURANT")) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the current command that was inputed
	 * 
	 * @return the string representation of the command that was inputed
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Gets the argument of the command if it does not involve structured queries or
	 * json
	 * 
	 * @return the string representation of the arguments put after the command
	 */
	public String getArugments() {
		return rest;
	}

	/**
	 * Gets the json that is associated with the command
	 * 
	 * @return a string representation of the json file
	 */
	public String getJson() {
		return json;
	}

	/**
	 * Gets the expression that the query says
	 * 
	 * @return an expression tree based on the query
	 */
	public Expression getExpression() {
		return lang;
	}

}
