package ar.dc.uba.model.condition;

import java.util.*;

import ar.dc.uba.model.language.*;

/**
 * Fluent definition
 * 
 * @author gsibay
 * 
 */
public interface Fluent {

	/**
	 * Returns the Fluent's name
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Returns the Fluent's initial value
	 * 
	 * @return
	 */
	public boolean initialValue();

	/**
	 * Returns the Fluent's initiating actions
	 * 
	 * @return
	 */
	public Set<Symbol> getInitiatingActions();

	/**
	 * Returns the Fluent's terminating actions
	 * 
	 * @return
	 */
	public Set<Symbol> getTerminatingActions();
}
