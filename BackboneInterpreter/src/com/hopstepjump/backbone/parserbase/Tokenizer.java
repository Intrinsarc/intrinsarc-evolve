package com.hopstepjump.backbone.parserbase;

import java.io.*;

/**
 * 1-token look ahead tokeniser
 * token types: literal, string, int, float, name
 * @author Andrew
 */

public class Tokenizer
{
	private Reader reader;
	private int lastChar = -1;
	private Token lastToken;
	private int currentLine = 1;
	private int currentPos;
	private int previousLine;
	private int previousPos;
	
	public Tokenizer(Reader reader)
	{
		this.reader = reader;
	}
	
	public Token peek()
	{
		if (lastToken != null)
			return lastToken;
		lastToken = next();
		return lastToken;
	}
	
	public Token next()
	{
		if (lastToken != null)
		{
			Token last = lastToken;
			lastToken = null;
			return last;
		}
		
		// read past whitespace
		skipWhite();
		
		// start reading
		int first = nextChar();
		if (first == -1)
			return null;  // end of stream
		
		char ch = (char) first;
		if (ch == '/' && peekChar() == '/')
		{
			readLineComment();
			return next();
		}
		if (ch == '/' && peekChar() == '*')
		{
			readGeneralComment();
			return next();
		}
		if (ch == '/' && peekChar() != '/')
			return readDescriptiveName();			
		if (Character.isDigit(first))
			return readNumber(ch);
		if (Character.isJavaIdentifierStart(ch))
			return readLiteral(ch);
		if (ch == '"')
			return readString();
		if (ch == '\'')
			return readChar();
		// otherwise, just read the character and return as a literal token
		return new Token(TokenType.LITERAL, "" + ch);
	}
	
	private Token readChar()
	{
		int first = translateChar(false);
		if (first == -'"')
			first = '"';
		if (first == -'\'')
			throwParseException("Character ends too soon", false);
		int next = nextChar();
		if (first == -1 || next == -1)
			throwParseException("Reached end of document without character close", false);
		if (next != '\'')
			throwParseException("Found character " + (char) next + " where character close should be", true);
		return new Token(TokenType.CHAR, "" + (char) first);
	}
		
	private Token readDescriptiveName()
	{
		// read until another ".  handle escaped characters and escaped " accordingly
		StringBuilder b = new StringBuilder();
		int next;
		while ((next = translateChar(false)) != -1)
		{
			if (next == '/')
				return new Token(TokenType.DESCRIPTIVE_NAME, b.toString());
			else
			if (next == -'\'')
				b.append("'");
			else
			if (next == -'\'')
				b.append("'");
			else
				b.append((char) next);
		}
		throwParseException("Reached end of document without descriptive name close", false);
		return null;  // will never happen
	}

	private Token readString()
	{
		// read until another ".  handle escaped characters and escaped " accordingly
		StringBuilder b = new StringBuilder();
		int next;
		while ((next = translateChar(false)) != -1)
		{
			if (next == -'"')
				return new Token(TokenType.STRING, b.toString());
			else
			if (next == -'\'')
				b.append("'");
			else
				b.append((char) next);
		}
		throwParseException("Reached end of document without string close", false);
		return null;  // will never happen
	}

	private int translateChar(boolean escaped)
	{
		int next = nextChar();
		if (next == -1)
			return -1;
		
		// handle escaped
		if (escaped)
		{
			switch (next)
			{
				case '\'':
					return '\'';
				case '"':
					return '"';
				case 't':
					return '\t';
				case 'n':
					return '\n';
				case 'r':
					return '\r';
				case '\\':
					return '\\';
				default:
					throwParseException("Unknown escape sequence: \\" + next, true);
			}
		}
		else
		if (next == '\\')
			return translateChar(true);
		if (next == '"')
			return -'"';
		if (next == '\'')
			return -'\'';
		return (char) next;
	}

	private Token readLiteral(char ch)
	{
		StringBuilder b = new StringBuilder();
		b.append(ch);
		int next;
		while ((next = peekChar()) != -1)
		{
			if (!Character.isJavaIdentifierPart(next) && next != '-' && next != '.')
				break;
			b.append((char) next);
			nextChar();
		}
		return new Token(TokenType.LITERAL, b.toString());
	}

	private Token readNumber(char ch)
	{
		// read until we have no more number-like symbols
		StringBuilder b = new StringBuilder();
		b.append(ch);
		int next;
		while ((next = peekChar()) != -1)
		{
			if (Character.isDigit(next) || next == '.' || next == 'e' || next == 'E' || next == '-' || next == '+')
				b.append((char) next);
			else
				break;
			nextChar();
		}
		// parse
		String n = b.toString();
		if (n.contains("e") || n.contains("E") || n.contains("."))
		{
			try
			{
				Double.parseDouble(n);
			}
			catch (NumberFormatException ex)
			{
				throwParseException("Problem parsing double", false);
			}
			return new Token(TokenType.DOUBLE, n);
		}
		
		try
		{
			Integer.parseInt(n);
		}
		catch (NumberFormatException ex)
		{
			throwParseException("Problem parsing integer: " + n, false);
		}
		return new Token(TokenType.INTEGER, n);
	}

	private void readGeneralComment()
	{
		// read past the *
		nextChar();
		int next;
		while ((next = nextChar()) != -1)
		{
			if (next == '*' && peekChar() == '/')
			{
				nextChar();  // move past the /
				return;
			}
		}
		throwParseException("Reached end of file before comment end", false);
	}

	private void readLineComment()
	{
		// read past the /
		nextChar();
		int next;
		while ((next = peekChar()) != -1)
		{
			if (next == '\n')
				return;
			nextChar();
		}
	}

	private void skipWhite()
	{
		int next;
		while ((next = peekChar()) != -1)
		{
			if (!Character.isWhitespace(next))
				return;
			nextChar();
		}
	}
	
	private int peekChar()
	{
		if (lastChar != -1)
			return lastChar;
		lastChar = nextChar();
		return lastChar;
	}
	
	private int nextChar()
	{
		if (lastChar != -1)
		{
			int last = lastChar;
			lastChar = -1;
			return last;
		}
		
		try
		{
			int next = reader.read();
			previousLine = currentLine;
			previousPos = currentPos;
			if (next == '\n')
			{
				currentLine++;
				currentPos = 0;
			}
			else
			if (next == '\r')
				;  // ignore, \n is used as line counter instead
			else
				currentPos++;
			return next;
		}
		catch (IOException ex)
		{
			throwParseException("Problem reading stream", ex, false);
			return -1; // will never happen
		}
	}
	
	public void throwParseException(String message, boolean current)
	{
		throw new ParseException(
				message,
				current ? currentLine : previousLine, current ? currentPos : previousPos);
	}

	public void throwParseException(String message, Exception ex, boolean current)
	{
		throw new ParseException(
				message,
				current ? currentLine : previousLine, current ? currentPos : previousPos, ex);
	}
}
