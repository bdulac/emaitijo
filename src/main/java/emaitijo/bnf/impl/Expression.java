package emaitijo.bnf.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import emaitijo.bnf.ExpressionTerm;
import emaitijo.grammar.Grammar;

public class Expression implements ExpressionTerm {
	
	enum CARDINALITY {
		// Default
		ONCE, 
		// [...]
		ZERO_OR_ONCE, 
		// {...}*
		ZERO_OR_MORE,
		// {...}+
		ONCE_OR_MORE
	}
	
	private Rule rule;

	private Expression parent;
	
	private String expression;
	
	private CARDINALITY cardinality;
	
	private List<ExpressionTerm> expressionTerms;
	
	private boolean instruction;
	
	public Expression(String expr, Rule r) {
		if(expr == null)throw new NullPointerException();
		if(r == null)throw new NullPointerException();
		expression = expr.trim();
		rule  = r;
		instruction = true;
	}
	
	public Expression(String expr, Expression p) {
		if(expr == null)throw new NullPointerException();
		if(p == null)throw new NullPointerException();
		expression = expr;
		parent  = p;
		rule = p.getRule();
		instruction = false;
	}
	
	public Rule getRule() {
		return rule;
	}
	
	private boolean isZeroOrOnce(String expr) {
		return 
				(expr.startsWith("["))
				&&(expr.endsWith("]"));
	}
	
	private boolean isZeroOrMore(String expr) {
		return 
				(expr.startsWith("{"))
				&&(expr.endsWith("}*"));
	}
	
	private boolean isOnceOrMore(String expr) {
		return 
				(expr.startsWith("{"))
				&&(expr.endsWith("}+"));
	}
	
	private boolean isGroup(String str) {
		return 
				(str.startsWith("{"))
				&&(str.endsWith("}"));
	}
	
	private boolean isKeyword(String str) {
		boolean delimiter =
				(str.startsWith("\""))
				&&(str.endsWith("\""));
		if(delimiter)return false;
		/*
		return 
				(!isGroup(str)) 
				&& (!isZeroOrMore(str)) 
				&& (!isZeroOrOnce(str)) 
				&& (!isRuleName(str));
		*/
		else if(("|").equals(str))return true;
		return false;
	}
	
	public void notifyGrammarComplete() {
		String expr = expression;
		expr = expr.trim();
		// []
		if(isZeroOrOnce(expr)) {
			cardinality = CARDINALITY.ZERO_OR_ONCE;
			expr = expr.substring(1, expr.length() - 1);
		}
		// {}*
		if(isZeroOrMore(expr)) {
			cardinality = CARDINALITY.ZERO_OR_MORE;
			expr = expr.substring(1, expr.length() - 2);
		}
		// {}+
		if(isOnceOrMore(expr)) {
			cardinality = CARDINALITY.ONCE_OR_MORE;
			expr = expr.substring(1, expr.length() - 2);
		}
		else {
			cardinality = CARDINALITY.ONCE;
		}
		// {}
		if(isGroup(expr)) {
			expr = expr.substring(1, expr.length() - 1);
		}
		// TODO ?
		// ..* +
		expressionTerms = new ArrayList<ExpressionTerm>();
		// Sub-expressions
		String remainderExpr = processStartingExpression(expr);
		while(!remainderExpr.equals(expr)) {
			remainderExpr = remainderExpr.trim();
			expr = remainderExpr;
			remainderExpr = processStartingExpression(remainderExpr);
		}
		expr = remainderExpr;
		String[] alternations = expr.split(Pattern.quote("|"));
		if(alternations.length > 1) {
			Alternation alternation = new Alternation();
			int index = 0;
			for(String altExpr : alternations) {
				alternation.addExpression(
						new Expression(
								altExpr.trim(),
								this
						)
				);
				if(index > 0) {
					CharSequence target = "|" + altExpr;
					expr = expr.replace(target, "");
				}
				else expr = expr.replace(altExpr, "");
				index++;
			}
			expressionTerms.add(alternation);
		}
		// Expression terms
		expr = expr.trim();
		String[] tokens = expr.split(" ");
		if(tokens.length > 0) {
			for(String token : tokens) {
				processToken(token);
			}
		}
		for(ExpressionTerm argument : expressionTerms) {
			argument.notifyGrammarComplete();
		}
	}

	private void processToken(String token) {
		ExpressionTerm argument = null;
		if(token.length() > 0) {
			Grammar grammar = rule.getGrammar();
			Expression ruleExpression = grammar.getExpression(token);
			if(ruleExpression != null) {
				argument = new RuleReference(ruleExpression.getRule());
			}
			else {
				String remainderExpr = processStartingExpression(token);
				while(!remainderExpr.equals(token)) {
					remainderExpr = remainderExpr.trim();
					token = remainderExpr;
					remainderExpr = processStartingExpression(remainderExpr);
				}
				if(token.trim().length() > 0) {
					int endIndex = -1;
					if(token.startsWith("\"")) {
						endIndex = token.substring(1).indexOf("\"");
					}
					else {
						endIndex = token.indexOf("[");
						if(endIndex < 0)endIndex = token.indexOf("{");
					}
					String literal = null;
					if(endIndex >= 0) {
						literal = token.substring(0, endIndex);
					}
					else literal = token;
					if(literal.length() > 0)argument = new Literal(literal);
					token = literal.substring(literal.length());
					processToken(token);
				}
			}
			if(argument != null)expressionTerms.add(argument);
		}
	}

	private String processStartingExpression(String remainderExpr) {
		if(expressionTerms == null)throw new IllegalStateException();
		remainderExpr = remainderExpr.trim();
		String subExpr = null;
		if(remainderExpr.startsWith("{")) {
			int subEndIndex = remainderExpr.indexOf("}");
			if(subEndIndex >=0)subExpr = remainderExpr.substring(0, subEndIndex + 1);
			
		}
		if(remainderExpr.startsWith("[")) {
			int subEndIndex = remainderExpr.indexOf("]");
			if(subEndIndex >=0)subExpr = remainderExpr.substring(0, subEndIndex + 1);
		}
		if(subExpr != null) {
			Expression startExpr = new Expression(subExpr, this);
			expressionTerms.add(startExpr);
			remainderExpr = remainderExpr.substring(subExpr.length());
		}
		return remainderExpr;
	}

	@Override
	public String toAntlr() {
		// String compareTo = writeInstruction(expression);
		String antlr = "";
		for(ExpressionTerm argument : expressionTerms) {
			antlr += argument.toAntlr();
		}
		if(instruction)antlr += "\n;";
		// TODO Write from arguments
		return antlr;
	}
	
	/*
	private static String writeInstruction(final String line) {
		String instruction = line;
		instruction = instruction.trim();
		if(instruction.length() == 0)return null;
		instruction = instruction.replace("::=", ":");
		instruction = replaceZeroOrOnce(instruction);
		instruction = replaceZeroOrMore(instruction);
		instruction = instruction.replaceAll(Pattern.quote("|"), "\n 	|");
		instruction += ";";
		return instruction;
	}

	private static String replaceZeroOrOnce(String instruction) {
		int begin = instruction.indexOf('[');
		int end = instruction.indexOf(']');
		while(begin < end) {
			instruction = instruction.replace("[", "(");
			instruction = instruction.replace("]", ")?");
			begin = instruction.indexOf('[');
			end = instruction.indexOf(']');
		}
		return instruction;
	}
	
	private static String replaceZeroOrMore(String instruction) {
		int begin = instruction.indexOf('{');
		int end = instruction.indexOf('}');
		while(begin < end) {
			instruction = instruction.replace("{", "(");
			instruction = instruction.replace("}", ")*");
			begin = instruction.indexOf('{');
			end = instruction.indexOf('}');
		}
		return instruction;
	}*/
	
	@Override
	public String toString() {
		if(expressionTerms != null)return expressionTerms.toString();
		else  return "" + super.toString() + " -> " + expression;
	}
}
