package ar.dc.uba.model.language;

import java.util.*;

import org.apache.commons.collections15.*;
import org.apache.commons.lang.*;
import org.apache.commons.lang.builder.*;

/**
 * A language is a set of Word. The language is unmodifiable
 * 
 * @author gsibay
 * 
 */
public class Language {

	private Set<Word> words = new HashSet<Word>();

	public Language(Set<Word> words) {
		Validate.notEmpty(words);
		this.words.addAll(words);
	}

	/**
	 * Returns an unmodifiable view of the words in the language
	 * 
	 * @return
	 */
	public Set<Word> getWords() {
		return Collections.unmodifiableSet(this.words);
		/*
		 * // returns a copy. The language is unmodifiable Set<Word> copy = new
		 * HashSet<Word>(); copy.addAll(this.words); return copy;
		 */
	}

	public Alphabet getAlphabet() {
		Set<Symbol> symbols = new HashSet<Symbol>();
		Collection<List<Symbol>> wordsAsSymbols = CollectionUtils.collect(this
				.getWords(), Word.TO_SYMBOLS_TRANSFORMER);
		for (List<Symbol> wordAsSymbols : wordsAsSymbols) {
			symbols.addAll(wordAsSymbols);
		}
		return new Alphabet(symbols);
	}

	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Language) {
			Language language = (Language) anObject;
			return this.getWords().equals(language.getWords());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return new HashCodeBuilder(19, 37).append(this.getWords()).toHashCode();
	}

	public String toString() {
		return this.getWords().toString();
	}
}
