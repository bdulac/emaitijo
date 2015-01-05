package emaitijo.grammar;

import java.io.IOException;
import java.util.logging.Logger;

import emaitijo.grammar.Bnf2AntlrConverter;
import junit.framework.TestCase;

public class ANTLRGrammarGeneratorTest extends TestCase {
	
	private static Logger logger = Logger.getAnonymousLogger();
	/*
	public void testTransformBnf2Grammar_coma() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("coma.bnf", false);
		assertNotNull(res);
		logger.info(res);
	}
	
	public void testTransformBnf2Grammar_option() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("option.bnf", false);
		assertNotNull(res);
		logger.info(res);
	}
	
	public void testTransformBnf2Grammar_alternation() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("alternation.bnf", false);
		assertNotNull(res);
		logger.info(res);
	}*/
	
	public void testTransformBnf2Grammar_jpql() throws IOException {
		Bnf2AntlrConverter gen = new Bnf2AntlrConverter();
		String res = gen.convert2Antlr("jpql.bnf", false);
		assertNotNull(res);
		logger.info(res);
	}
}
