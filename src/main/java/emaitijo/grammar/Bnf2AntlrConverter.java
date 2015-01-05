package emaitijo.grammar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import org.antlr.v4.Tool;
import org.antlr.v4.tool.ErrorType;

public class Bnf2AntlrConverter {
	
	public String convert2Antlr(String bnfLocation, boolean gen) 
			throws IOException {
		URL url = getClass().getClassLoader().getResource(bnfLocation);
		File f = new File(url.getPath());
		String fName = f.getName();
		int extIndex = fName.lastIndexOf(".");
		String grammarName = null;
		if(extIndex > 0) {
			grammarName = fName.substring(0, extIndex);
		}
		else {
			grammarName = fName;
		}
		FileReader fReader = new FileReader(f);
		BufferedReader bReader = new BufferedReader(fReader);
		String line = null;
		Grammar grammar = new Grammar(grammarName);
		while((line = bReader.readLine()) != null) {
			if(isRule(line)) {
				grammar.addRule(line);
			}
		}
		grammar.notifyGrammarComplete();
		bReader.close();
		fReader.close();
		String targetUrl = url.getPath().replace(fName, grammarName + ".g4");
		File targetFile = new File(targetUrl);
		targetFile.createNewFile();
		FileWriter fWriter = new FileWriter(targetFile);
		BufferedWriter bWriter = new BufferedWriter(fWriter);
		// bWriter.write(result);
		String antlr = grammar.toAntlr();
		bWriter.write(antlr);
		bWriter.close();
		fWriter.close();
		if(gen)executeAntlr(targetUrl);
		return antlr;
	}

	private void executeAntlr(String targetUrl) {
		String[] args = new String[] { targetUrl };
		Tool antlr = new Tool(args);
		if (args.length == 0) {
			antlr.help();
			antlr.exit(0);
		}
		try {
			antlr.processGrammarsOnCommandLine();
		} finally {
			if (antlr.log) {
				try {
					String logname = antlr.logMgr.save();
					System.out.println("wrote " + logname);
				} catch (IOException ioe) {
					antlr.errMgr.toolError(ErrorType.INTERNAL_ERROR, ioe);
				}
			}
		}
		if (antlr.errMgr.getNumErrors() > 0) {
			antlr.exit(1);
		}
		antlr.exit(0);
	}

	private boolean isRule(String line) {
		return line.contains("::=")||line.contains("=");
	}

	/*
	private String writeInstruction(final String line) {
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

	private String replaceZeroOrOnce(String instruction) {
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
	
	private String replaceZeroOrMore(String instruction) {
		int begin = instruction.indexOf('{');
		int end = instruction.indexOf('}');
		while(begin < end) {
			instruction = instruction.replace("{", "(");
			instruction = instruction.replace("}", ")*");
			begin = instruction.indexOf('{');
			end = instruction.indexOf('}');
		}
		return instruction;
	}
	*/
}
