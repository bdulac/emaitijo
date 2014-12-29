package emaitijo.bnf.impl;

import emaitijo.bnf.ExpressionTerm;

public class Literal implements ExpressionTerm {

	private String literal;
	
	public Literal(String l) {
		if(l == null)throw new NullPointerException();
		literal = l;
	}
	
	@Override
	public String toAntlr() {
		return '\'' + literal + '\'';
	}
	
	public String toString() {
		return literal;
	}

	@Override
	public void notifyGrammarComplete() {
		// TODO Auto-generated method stub
		
	}

}
