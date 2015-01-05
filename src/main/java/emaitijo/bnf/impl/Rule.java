package emaitijo.bnf.impl;

import emaitijo.bnf.BnfElement;
import emaitijo.grammar.Grammar;

public class Rule implements BnfElement, Comparable<Rule> {
	
	private String name;
	
	private Expression expression;
	
	private Grammar grammar;
	
	public Rule(String nm, String expr, Grammar gmr) {
		if(nm == null)throw new NullPointerException();
		if(gmr == null)throw new NullPointerException();
		grammar = gmr;
		name = nm.trim();
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
	
	public String toAntlrName() {
		/*return name.toUpperCase().substring(0, 1) 
				+ name.toLowerCase().substring(1);
				*/
		return name.toLowerCase();
	}

	@Override
	public String toAntlr() {
		return toAntlrName() + "\n	:	" 
				+ expression.toAntlr();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Rule o) {
		if(o == null)return -1;
		String str = this.toString();
		String oStr = o.toString();
		return str.compareTo(oStr);
	}
}