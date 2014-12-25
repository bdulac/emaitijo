package work.bnf;

import java.util.List;

import work.grammar.Grammar;

public class Expression implements ExpressionArgument {
	
	enum CARDINALITY {
		// Default
		ONCE, 
		// [...]
		ZERO_OR_ONCE, 
		// {...}
		ZERO_OR_MORE
	}
	
	private Rule rule;
	
	private String expression;
	
	private CARDINALITY cardinality;
	
	List<ExpressionArgument> arguments;
	
	public Expression(String expr, Rule r) {
		if(expr == null)throw new NullPointerException();
		if(r == null)throw new NullPointerException();
		expression = expr;
		rule  = r;
	}
	
	private boolean isZeroOrOnce(String expr) {
		return 
				(expr.startsWith("["))
				&&(expr.endsWith("]"));
	}
	
	private boolean isZeroOrMore(String expr) {
		return 
				(expr.startsWith("{"))
				&&(expr.endsWith("}"));
	}
	
	private boolean isGroup(String str) {
		return 
				(str.startsWith("("))
				&&(str.endsWith(")"));
	}
	
	private boolean isRuleName(String str) {
		Grammar gr = rule.getGrammar();
		return (gr.get(str) != null);
	}
	
	private boolean isKeyword(String str) {
		boolean delimiter =
				(str.startsWith("\""))
				&&(str.endsWith("\""));
		if(delimiter)return false;
		return 
				(!isGroup(str)) 
				&& (!isZeroOrMore(str)) 
				&& (!isZeroOrOnce(str)) 
				&& (!isRuleName(str));
		
	}
	
	public void notifyGrammarComplete() {
		String expr = expression;
		if(isZeroOrOnce(expr)) {
			cardinality = CARDINALITY.ZERO_OR_ONCE;
			expr = expr.substring(1, expression.length() - 1);
		}
		if(
				isZeroOrMore(expr)
		) {
			cardinality = CARDINALITY.ZERO_OR_MORE;
			expr = expr.substring(1, expression.length() - 1);
		}
		else {
			cardinality = CARDINALITY.ONCE;
		}
		// ..* +
		if(isGroup(expr)) {
			expr = expr.substring(1, expression.length() - 1);
		}
		String[] tokens = expr.split(" ");
		
		Grammar grammar = rule.getGrammar();
		
	}

	@Override
	public String toAntlr() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
