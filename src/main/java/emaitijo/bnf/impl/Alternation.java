package emaitijo.bnf.impl;

import java.util.ArrayList;
import java.util.List;

import emaitijo.bnf.ExpressionTerm;

public class Alternation implements ExpressionTerm {

	private List<Expression> expressions;
	
	public Alternation() {
		expressions = new ArrayList<Expression>();
	}
	
	public void addExpression(Expression expr) {
		if(expr == null)throw new NullPointerException();
		expressions.add(expr);
	}
	
	@Override
	public String toAntlr() {
		String antlr = "";
		for(Expression expression : expressions) {
			if(antlr.length() > 0) {
				antlr += "\n	| " + expression.toAntlr()+ " ";
			}
			else antlr+= expression.toAntlr();
		}
		return antlr;
	}

	@Override
	public String toString() {
		return toAntlr();
	}

	@Override
	public void notifyGrammarComplete() {
		for(Expression expr  : expressions) {
			expr.notifyGrammarComplete();
		}
	}
}
