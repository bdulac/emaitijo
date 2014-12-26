package emaitijo.bnf;

public class RuleReference implements ExpressionArgument {
	
	private Rule rule;
	
	public RuleReference(Rule r) {
		rule = r;
	}
	
	public Rule getRule() {
		return rule;
	}

	@Override
	public String toAntlr() {
		// TODO Auto-generated method stub
		return null;
	}

}
