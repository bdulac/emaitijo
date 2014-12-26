package emaitijo.grammar;

import java.io.IOException;
import java.util.logging.Logger;

import emaitijo.grammar.ANTLRGrammarGenerator;
import junit.framework.TestCase;

public class ANTLRGrammarGeneratorTest extends TestCase {
	
	private static Logger logger = Logger.getAnonymousLogger();

	public void testWriteGrammarFromFile() throws IOException {
		ANTLRGrammarGenerator gen = new ANTLRGrammarGenerator();
		String res = gen.transformBnf2Grammar("jpql.bnf");
		assertNotNull(res);
		logger.info(res);
	}
	
}
