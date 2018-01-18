// Code adapted from https://stackoverflow.com/questions/18132078/handling-errors-in-antlr4
package ca.ece.ubc.cpen221.mp5.grammar;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ErrorThrowListener extends BaseErrorListener {
	public static final ErrorThrowListener INSTANCE = new ErrorThrowListener();

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) throws ParseCancellationException {
		throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
	}
}