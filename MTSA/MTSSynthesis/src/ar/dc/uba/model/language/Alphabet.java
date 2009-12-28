package ar.dc.uba.model.language;

import java.util.*;

import org.apache.commons.lang.*;
import org.apache.commons.lang.builder.*;

/**
 * An alphabet of symbols
 * 
 * @author gsibay
 * 
 */
public class Alphabet {

	private Set<Symbol> symbols = new HashSet<Symbol>();
	public static SingleSymbol TAU = new SingleSymbol("tau");

	public Alphabet(Set<Symbol> symbols) {
		Validate.notEmpty(symbols);
		Validate.isTrue(!symbols.contains(Alphabet.TAU), TAU.toString()
				+ " is a reserved symbols. You should not use it");
		this.symbols.addAll(symbols);
		this.symbols = Collections.unmodifiableSet(this.symbols);
	}

	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Alphabet) {
			Alphabet anAlphabet = (Alphabet) anObject;
			return this.getSymbols().equals(anAlphabet.getSymbols());
		} else {
			return false;
		}
	}

	public Set<Symbol> getSymbols() {
		return this.symbols;
	}

	public int hashCode() {
		return new HashCodeBuilder(19, 37).append(this.getSymbols())
				.toHashCode();
	}

	public String toString() {
		return this.getSymbols().toString();
	}
}
