package emaitijo.bnf.impl;

import emaitijo.bnf.ExpressionTerm;

public class RuleReference implements ExpressionTerm {
	
	private Rule rule;
	
	public RuleReference(Rule r) {
		if(r == null)throw new NullPointerException();
		rule = r;
	}
	
	public Rule getRule() {
		return rule;
	}

	@Override
	public String toAntlr() {
		return rule.getName();
	}
	
	@Override
	public String toString() {
		return rule.getName();
	}

	@Override
	public void notifyGrammarComplete() {
		// TODO Auto-generated method stub
		
	}

}
