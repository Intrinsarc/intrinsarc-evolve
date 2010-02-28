package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.parser.*;

public class TokenizerTests
{
	public static String BASE_DIR = "./tests/com/hopstepjump/backbonetests/parser";
	
	@Test
	public void testExpectString() throws IOException
	{
		StringReader reader = new StringReader("\"hello\"");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hello"), t);
	}

	@Test(expected=ParseException.class)
	public void testExpectStringNotClosed() throws IOException
	{
		StringReader reader = new StringReader("\"hello");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
	}

	@Test
	public void testExpectStringMultiLine() throws IOException
	{
		StringReader reader = new StringReader("\"hello\ngoodbye\" name");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hello\ngoodbye"), t);
	}

	@Test
	public void testExpectStringAfterWhite() throws IOException
	{
		StringReader reader = new StringReader("  \t\t  \"hello\"");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hello"), t);
	}

	@Test
	public void testExpectStringTwo() throws IOException
	{
		StringReader reader = new StringReader("  \t\t  \"hello\"  \t\"goodbye\"\t  ");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hello"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.STRING, "goodbye"), t);
	}
	
	@Test
	public void testExpectStringEscapes() throws IOException
	{
		String inp = "\"hello\\\"\\n\\t\\r\\\\there\"";
		StringReader reader = new StringReader(inp);
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hello\"\n\t\r\\there"), t);
	}
	
	@Test
	public void testExpectStringEscapes2() throws IOException
	{
		StringReader reader = new StringReader("  \"hel\\\"lo'\"");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.STRING, "hel\"lo'"), t);
	}
	
	@Test
	public void testExpectChar() throws IOException
	{
		StringReader reader = new StringReader("  'a'");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.CHAR, "a"), t);
	}

	@Test
	public void testExpectCharEscape() throws IOException
	{
		StringReader reader = new StringReader("  '\\\''");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.CHAR, "\'"), t);
	}

	@Test
	public void testExpectCharEscape2() throws IOException
	{
		StringReader reader = new StringReader("  '\\t'");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.CHAR, "\t"), t);
	}

	@Test
	public void testExpectCharQuote() throws IOException
	{
		StringReader reader = new StringReader("  '\"'");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.CHAR, "\""), t);
	}

	@Test(expected=ParseException.class)
	public void testExpectCharFailure() throws IOException
	{
		StringReader reader = new StringReader("  ''");
		new Tokenizer(reader).next();
	}

	@Test
	public void testExpectLineComment() throws IOException
	{
		StringReader reader = new StringReader("  hello\nthere//comment\nandrew");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "hello"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "there"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "andrew"), t);
	}	

	@Test
	public void testExpectGeneralComment() throws IOException
	{
		StringReader reader = new StringReader("  hello\nthere/* comment \n\n*/\nandrew");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "hello"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "there"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "andrew"), t);
	}	
		
	@Test
	public void testExpectInteger() throws IOException
	{
		StringReader reader = new StringReader("12hello 1.23e-8 178.2 /* hello */ 57");
		Tokenizer tok = new Tokenizer(reader);
		Token t = tok.next();
		assertEquals(new Token(TokenType.INTEGER, "12"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.LITERAL, "hello"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.DOUBLE, "1.23e-8"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.DOUBLE, "178.2"), t);
		t = tok.next();
		assertEquals(new Token(TokenType.INTEGER, "57"), t);
	}
	
	@Test
	public void testPosition() throws IOException
	{
		assessLineAndPosition(
				"  hello\t\n\r  1.2.3",
				1, 1, 7);
	}
	
	@Test
	public void testPosition2() throws IOException
	{
		assessLineAndPosition(
				"  hello\t\n\r  \n /* hello */ 1.2.3",
				1, 2, 18);
	}
	
	public void assessLineAndPosition(String text, int nexts, int line, int pos) throws IOException
	{
		StringReader reader = new StringReader(text);
		Tokenizer tok = new Tokenizer(reader);
		for (int lp = 0; lp < nexts; lp++)
			tok.next();
		boolean caught = false;
		try
		{
			tok.next();
		}
		catch (ParseException ex)
		{
			caught = true;
			assertEquals(line, ex.getLine());
			assertEquals(pos, ex.getPos());
		}
		assertTrue(caught);
	}	
}
