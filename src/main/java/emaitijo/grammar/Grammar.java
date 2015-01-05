package emaitijo.grammar;

import java.util.LinkedHashMap;
import java.util.Set;

import emaitijo.bnf.impl.Expression;
import emaitijo.bnf.impl.Rule;

public class Grammar //extends TreeMap<Rule, Expression> {
extends LinkedHashMap<Rule, Expression> {

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
	
	public String toAntlr() {
		String result = "grammar " + toAntlrName() + ";";
		for(Rule rule : keySet()) {
			String instruction = rule.toAntlr();
			if(instruction != null)result += "\n" + instruction;
		}
		return result;
	}

	private String toAntlrName() {
		/*
		return 
				name.substring(0, 1).toUpperCase() 
				+ name.substring(1).toLowerCase();
				*/
		return name;
	}

	public Expression getExpression(String ruleName) {
		Set<Rule> rules = keySet();
		for(Rule rule : rules) {
			if(rule.getName().equals(ruleName)) {
				return get(rule);
			}
		}
		return null;
	}
	
}