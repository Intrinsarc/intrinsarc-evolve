package ar.dc.uba.parser;

import java.util.*;

import ar.dc.uba.model.language.*;

/**
 * Parses a word from a String. The word is a collection of Symbol separated by
 * |. for example: a|b|c or turnOn|turnOff|goUp|returnHome|goDown
 * 
 * @author gsibay
 * 
 */
public class WordParser {

	private static WordParser instance = new WordParser();

	public static WordParser getInstance() {
		return instance;
	}

	public Word parseWord(String wordStr) {
		List<Symbol> wordSymbols = new LinkedList<Symbol>();

		StringTokenizer tokenizer = new StringTokenizer(wordStr, "|");
		Symbol symbol;
		while (tokenizer.hasMoreTokens()) {
			String symbolStr = (String) tokenizer.nextToken();
			symbol = new SingleSymbol(symbolStr);
			wordSymbols.add(symbol);
		}
		return new Word(wordSymbols);
	}
}
