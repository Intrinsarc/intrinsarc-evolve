package jsyntaxpane;

import java.io.*;

/**
 * a simple lexer for backbone
 * @author andrew
 *
 */
public final class TALLexer implements Lexer
{
	private Reader reader;
	private static final String[] TOP_KEYWORDS = {
		"stratum", "component", "interface"};
	private static final String[] MODIFIERS = {
		"is-relaxed", "is-destructive", "depends-on", "nests",
		"is-composite", "is-leaf", "is-primitivetype", "is-factory", "is-placeholder",
		"resembles", "replaces", "implementation-class", "is-factory-interface", "is-bean",
		"is-create-port", "is-hyperport-start", "is-hyperport-end", "read-only", "write-only", "becomes"};
	private static final String[] INSIDE_KEYWORDS = {
		"delete-operations:", "operations:", "replace-operations:", 
		"delete-attributes:", "attributes:", "replace-attributes:", 
		"delete-ports:", "ports:", "replace-ports:", 
		"delete-parts:", "parts:", "replace-parts:", 
		"delete-connectors:", "connectors:", "replace-connectors:",
		"delete-port-links:", "port-links:", "replace-port-links:",
		"joins", "to", "delegates-from"};
	private static final char GUILLEMET_START = '\u00ab';
	private static final char GUILLEMET_END   = '\u00bb';
	private int offset;
	
	public void yyreset(Reader reader)
	{
		this.reader = reader;
		offset = 0;
	}
	
	public Token yylex() throws IOException
	{
		// read up until whitespace
		StringBuilder b = new StringBuilder();
		int ch;
		boolean guillemetMode = false;
		
		int start = offset;
		int length = 0;
		while ((ch = reader.read()) != -1)
		{
			offset++;
			length++;
			if (ch == GUILLEMET_START)
				guillemetMode = true;
			else
			if (ch == GUILLEMET_END)
				return new Token(TokenType.TYPE, start, length);
			else
			if (!guillemetMode && Character.isWhitespace(ch))
			{
				if (isKeyword(b.toString(), TOP_KEYWORDS))
					return new Token(TokenType.KEYWORD, start, length);
				else
				if (isKeyword(b.toString(), MODIFIERS))
					return new Token(TokenType.IDENT, start, length);					
				else
				if (isKeyword(b.toString(), INSIDE_KEYWORDS))
					return new Token(TokenType.REGEX, start, length);					
				else
					return new Token(TokenType.OPER, start, length);
			}
			b.append((char) ch);
		}
		if (start == offset)
			return null;
		return new Token(TokenType.OPER, start, offset);		
	}

	private boolean isKeyword(String str, String[] list)
	{
		for (String k : list)
		{
			if (k.equals(str))
				return true;
		}
		return false;
	}

}
