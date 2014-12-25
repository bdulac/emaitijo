package work.grammar;

import java.util.HashMap;

import work.bnf.Expression;
import work.bnf.Rule;

public class Grammar extends HashMap<Rule, Expression> {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -4639744150630285891L;
	
	private String name;
	
	public Grammar(String nm) {
		name = nm;
	}
	
	public String getName() {
		return name;
	}
	
	public void addRule(String rule) {
		int index = rule.indexOf("::=");
		if(index < 0)index = rule.indexOf("=");
		if(index < 0) {
			throw new IllegalArgumentException();
		}
		String ruleName = rule.substring(0, index);
		String ruleExpression = rule.substring(index + 3);
		Rule r = new Rule(ruleName, ruleExpression, this);
		put(r, r.getExpression());	
	}
	
	public void notifyGrammarComplete() {
		for(Rule r : keySet()) {
			Expression expr = get(r);
			expr.notifyGrammarComplete();
		}
	}
	
}
