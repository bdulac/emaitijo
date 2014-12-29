package emaitijo.grammar;

import java.io.IOException;
import java.util.logging.Logger;

import emaitijo.grammar.ANTLRGrammarGenerator;
import junit.framework.TestCase;

public class ANTLRGrammarGeneratorTest extends TestCase {
	
	private static Logger logger = Logger.getAnonymousLogger();
	
	public void testTransformBnf2Grammar_coma() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("coma.bnf");
		assertNotNull(res);
		logger.info(res);
	}
	
	public void testTransformBnf2Grammar_option() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("option.bnf");
		assertNotNull(res);
		logger.info(res);
	}
	
	public void testTransformBnf2Grammar_alternation() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("alternation.bnf");
		assertNotNull(res);
		logger.info(res);
	}
	
	public void testTransformBnf2Grammar_jpql() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("jpql.bnf");
		assertNotNull(res);
		logger.info(res);
	}
}
