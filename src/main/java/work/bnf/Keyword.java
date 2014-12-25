package work.bnf;

public class Keyword implements ExpressionArgument {
	
	private String keyword;

	public Keyword(String key) {
		keyword = key;
	}
	
	public String getKeyword() {
		return keyword;
	}

	@Override
	public String toAntlr() {
		// TODO Auto-generated method stub
		return null;
	}
}
