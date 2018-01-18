// Generated from Query.g4 by ANTLR 4.7
package ca.ece.ubc.cpen221.mp5.grammar.query;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, WS=6, OR=7, AND=8, GT=9, GTE=10, 
		LT=11, LTE=12, EQ=13, NUM=14, STRING=15, LPAREN=16, RPAREN=17;
	public static final int
		RULE_query = 0, RULE_orexpr = 1, RULE_andexpr = 2, RULE_atom = 3, RULE_ineq = 4, 
		RULE_in = 5, RULE_category = 6, RULE_name = 7, RULE_rating = 8, RULE_price = 9;
	public static final String[] ruleNames = {
		"query", "orexpr", "andexpr", "atom", "ineq", "in", "category", "name", 
		"rating", "price"
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

	@Override
	public String getGrammarFileName() { return "Query.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class QueryContext extends ParserRuleContext {
		public OrexprContext orexpr() {
			return getRuleContext(OrexprContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			orexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrexprContext extends ParserRuleContext {
		public List<AndexprContext> andexpr() {
			return getRuleContexts(AndexprContext.class);
		}
		public AndexprContext andexpr(int i) {
			return getRuleContext(AndexprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(QueryParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(QueryParser.OR, i);
		}
		public OrexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterOrexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitOrexpr(this);
		}
	}

	public final OrexprContext orexpr() throws RecognitionException {
		OrexprContext _localctx = new OrexprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			andexpr();
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(23);
				match(OR);
				setState(24);
				andexpr();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndexprContext extends ParserRuleContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(QueryParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(QueryParser.AND, i);
		}
		public AndexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAndexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAndexpr(this);
		}
	}

	public final AndexprContext andexpr() throws RecognitionException {
		AndexprContext _localctx = new AndexprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_andexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			atom();
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(31);
				match(AND);
				setState(32);
				atom();
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public InContext in() {
			return getRuleContext(InContext.class,0);
		}
		public CategoryContext category() {
			return getRuleContext(CategoryContext.class,0);
		}
		public RatingContext rating() {
			return getRuleContext(RatingContext.class,0);
		}
		public PriceContext price() {
			return getRuleContext(PriceContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(QueryParser.LPAREN, 0); }
		public OrexprContext orexpr() {
			return getRuleContext(OrexprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(QueryParser.RPAREN, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_atom);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				in();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				category();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(40);
				rating();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(41);
				price();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 5);
				{
				setState(42);
				name();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(43);
				match(LPAREN);
				setState(44);
				orexpr();
				setState(45);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IneqContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(QueryParser.GT, 0); }
		public TerminalNode GTE() { return getToken(QueryParser.GTE, 0); }
		public TerminalNode LT() { return getToken(QueryParser.LT, 0); }
		public TerminalNode LTE() { return getToken(QueryParser.LTE, 0); }
		public TerminalNode EQ() { return getToken(QueryParser.EQ, 0); }
		public IneqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ineq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIneq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIneq(this);
		}
	}

	public final IneqContext ineq() throws RecognitionException {
		IneqContext _localctx = new IneqContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ineq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE) | (1L << EQ))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QueryParser.LPAREN, 0); }
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public TerminalNode RPAREN() { return getToken(QueryParser.RPAREN, 0); }
		public InContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_in; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIn(this);
		}
	}

	public final InContext in() throws RecognitionException {
		InContext _localctx = new InContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_in);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__0);
			setState(52);
			match(LPAREN);
			setState(53);
			match(STRING);
			setState(54);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CategoryContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QueryParser.LPAREN, 0); }
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public TerminalNode RPAREN() { return getToken(QueryParser.RPAREN, 0); }
		public CategoryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCategory(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCategory(this);
		}
	}

	public final CategoryContext category() throws RecognitionException {
		CategoryContext _localctx = new CategoryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_category);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(T__1);
			setState(57);
			match(LPAREN);
			setState(58);
			match(STRING);
			setState(59);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QueryParser.LPAREN, 0); }
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public TerminalNode RPAREN() { return getToken(QueryParser.RPAREN, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(T__2);
			setState(62);
			match(LPAREN);
			setState(63);
			match(STRING);
			setState(64);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RatingContext extends ParserRuleContext {
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(QueryParser.NUM, 0); }
		public RatingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rating; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterRating(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitRating(this);
		}
	}

	public final RatingContext rating() throws RecognitionException {
		RatingContext _localctx = new RatingContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rating);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__3);
			setState(67);
			ineq();
			setState(68);
			match(NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PriceContext extends ParserRuleContext {
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(QueryParser.NUM, 0); }
		public PriceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_price; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterPrice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitPrice(this);
		}
	}

	public final PriceContext price() throws RecognitionException {
		PriceContext _localctx = new PriceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_price);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__4);
			setState(71);
			ineq();
			setState(72);
			match(NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23M\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\3\3\3\3\3\3\7\3\34\n\3\f\3\16\3\37\13\3\3\4\3\4\3\4\7\4$\n\4\f"+
		"\4\16\4\'\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\62\n\5\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\3\3\2"+
		"\13\17\2I\2\26\3\2\2\2\4\30\3\2\2\2\6 \3\2\2\2\b\61\3\2\2\2\n\63\3\2\2"+
		"\2\f\65\3\2\2\2\16:\3\2\2\2\20?\3\2\2\2\22D\3\2\2\2\24H\3\2\2\2\26\27"+
		"\5\4\3\2\27\3\3\2\2\2\30\35\5\6\4\2\31\32\7\t\2\2\32\34\5\6\4\2\33\31"+
		"\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36\5\3\2\2\2\37\35"+
		"\3\2\2\2 %\5\b\5\2!\"\7\n\2\2\"$\5\b\5\2#!\3\2\2\2$\'\3\2\2\2%#\3\2\2"+
		"\2%&\3\2\2\2&\7\3\2\2\2\'%\3\2\2\2(\62\5\f\7\2)\62\5\16\b\2*\62\5\22\n"+
		"\2+\62\5\24\13\2,\62\5\20\t\2-.\7\22\2\2./\5\4\3\2/\60\7\23\2\2\60\62"+
		"\3\2\2\2\61(\3\2\2\2\61)\3\2\2\2\61*\3\2\2\2\61+\3\2\2\2\61,\3\2\2\2\61"+
		"-\3\2\2\2\62\t\3\2\2\2\63\64\t\2\2\2\64\13\3\2\2\2\65\66\7\3\2\2\66\67"+
		"\7\22\2\2\678\7\21\2\289\7\23\2\29\r\3\2\2\2:;\7\4\2\2;<\7\22\2\2<=\7"+
		"\21\2\2=>\7\23\2\2>\17\3\2\2\2?@\7\5\2\2@A\7\22\2\2AB\7\21\2\2BC\7\23"+
		"\2\2C\21\3\2\2\2DE\7\6\2\2EF\5\n\6\2FG\7\20\2\2G\23\3\2\2\2HI\7\7\2\2"+
		"IJ\5\n\6\2JK\7\20\2\2K\25\3\2\2\2\5\35%\61";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}