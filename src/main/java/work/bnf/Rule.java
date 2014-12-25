package work.bnf;

import work.grammar.Grammar;

public class Rule implements BnfElement {
	
	private String name;
	
	private Expression expression;
	
	private Grammar grammar;
	
	public Rule(String nm, String expr, Grammar gmr) {
		if(nm == null)throw new NullPointerException();
		if(gmr == null)throw new NullPointerException();
		grammar = gmr;
		name = nm;
		expression = new Expression(expr, this);
	}
	
	public Grammar getGrammar() {
		return grammar;
	}

	public String getName() {
		return name;
	}
	
	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toAntlr() {
		return name;
	}
}
