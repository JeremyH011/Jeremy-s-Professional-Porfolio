// Generated from Query.g4 by ANTLR 4.7
package ca.ece.ubc.cpen221.mp5.grammar.query;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, WS=6, OR=7, AND=8, GT=9, GTE=10, 
		LT=11, LTE=12, EQ=13, NUM=14, STRING=15, LPAREN=16, RPAREN=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "WS", "OR", "AND", "GT", "GTE", 
		"LT", "LTE", "EQ", "NUM", "STRING", "LPAREN", "RPAREN"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'in'", "'category'", "'name'", "'rating'", "'price'", null, "'||'", 
		"'&&'", "'>'", "'>='", "'<'", "'<='", "'='", null, null, "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "WS", "OR", "AND", "GT", "GTE", "LT", 
		"LTE", "EQ", "NUM", "STRING", "LPAREN", "RPAREN"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public QueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Query.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23v\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\6\7E\n\7\r\7\16"+
		"\7F\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\6\20`\n\20\r\20\16\20a\3\20\6\20e\n"+
		"\20\r\20\16\20f\3\20\6\20j\n\20\r\20\16\20k\7\20n\n\20\f\20\16\20q\13"+
		"\20\3\21\3\21\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23\3\2\5\5\2\13\f\17\17\"\"\3"+
		"\2\63\67\7\2//\62;C\\aac|\2z\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5(\3\2\2\2\7\61\3\2\2\2\t\66"+
		"\3\2\2\2\13=\3\2\2\2\rD\3\2\2\2\17J\3\2\2\2\21M\3\2\2\2\23P\3\2\2\2\25"+
		"R\3\2\2\2\27U\3\2\2\2\31W\3\2\2\2\33Z\3\2\2\2\35\\\3\2\2\2\37_\3\2\2\2"+
		"!r\3\2\2\2#t\3\2\2\2%&\7k\2\2&\'\7p\2\2\'\4\3\2\2\2()\7e\2\2)*\7c\2\2"+
		"*+\7v\2\2+,\7g\2\2,-\7i\2\2-.\7q\2\2./\7t\2\2/\60\7{\2\2\60\6\3\2\2\2"+
		"\61\62\7p\2\2\62\63\7c\2\2\63\64\7o\2\2\64\65\7g\2\2\65\b\3\2\2\2\66\67"+
		"\7t\2\2\678\7c\2\289\7v\2\29:\7k\2\2:;\7p\2\2;<\7i\2\2<\n\3\2\2\2=>\7"+
		"r\2\2>?\7t\2\2?@\7k\2\2@A\7e\2\2AB\7g\2\2B\f\3\2\2\2CE\t\2\2\2DC\3\2\2"+
		"\2EF\3\2\2\2FD\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\b\7\2\2I\16\3\2\2\2JK\7~"+
		"\2\2KL\7~\2\2L\20\3\2\2\2MN\7(\2\2NO\7(\2\2O\22\3\2\2\2PQ\7@\2\2Q\24\3"+
		"\2\2\2RS\7@\2\2ST\7?\2\2T\26\3\2\2\2UV\7>\2\2V\30\3\2\2\2WX\7>\2\2XY\7"+
		"?\2\2Y\32\3\2\2\2Z[\7?\2\2[\34\3\2\2\2\\]\t\3\2\2]\36\3\2\2\2^`\t\4\2"+
		"\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2bo\3\2\2\2ce\7\"\2\2dc\3\2\2"+
		"\2ef\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hj\t\4\2\2ih\3\2\2\2jk\3\2\2"+
		"\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2md\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2"+
		"\2p \3\2\2\2qo\3\2\2\2rs\7*\2\2s\"\3\2\2\2tu\7+\2\2u$\3\2\2\2\b\2Fafk"+
		"o\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}