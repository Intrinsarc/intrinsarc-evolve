package ar.dc.uba.parser;

import java.util.*;

import ar.dc.uba.model.language.*;

/**
 * Parses a Chart from a String. The Chart is a set of words (a language). First
 * the set must be parsed, then the words
 * 
 * @author gsibay
 * 
 */
public class LanguageParser {

	private static LanguageParser instance = new LanguageParser();

	public static LanguageParser getInstance() {
		return instance;
	}

	public Language parseLanguage(String languageStr) {
		Set<Word> words = new HashSet<Word>();

		WordParser wordParser = WordParser.getInstance();

		// Parse the set to get the set of words (the words are strings)
		Set<String> wordsStr = SetParser.getInstance().parse(languageStr);

		// Parse each word from the string
		Iterator<String> wordsStrIt = wordsStr.iterator();
		while (wordsStrIt.hasNext()) {
			String wordStr = (String) wordsStrIt.next();
			// parse and add the word to the word�s set
			words.add(wordParser.parseWord(wordStr));
		}

		return new Language(words);
	}
}
