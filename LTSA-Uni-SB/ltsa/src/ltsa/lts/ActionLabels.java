package ltsa.lts;

import java.util.*;

public abstract class ActionLabels implements Cloneable {

	protected ActionLabels follower; // next part of compound label
	protected Hashtable<String, Value> locals;
	protected Hashtable<String, Value> globals;

	public void addFollower(ActionLabels f) {
		follower = f;
	}

	public ActionLabels getFollower() {
		return follower;
	}

	/**
	 * - initialises context for label generation
	 */
	public void initContext(Hashtable<String, Value> locals,
			Hashtable<String, Value> globals) {
		this.locals = locals;
		this.globals = globals;
		initialise();
		checkDuplicateVarDefn();
		if (follower != null)
			follower.initContext(locals, globals);
	}

	public void clearContext() {
		removeVarDefn();
		if (follower != null)
			follower.clearContext();
	}

	/**
	 * - returns string for this label and moves counter
	 */
	public String nextName() {
		String s = computeName();
		if (follower != null) {
			s = s + "." + follower.nextName();
			if (!follower.hasMoreNames()) {
				follower.initialise();
				next();
			}
		} else {
			next();
		}
		return s;
	}

	/**
	 * - returns false if no more names
	 */
	public abstract boolean hasMoreNames();

	/**
	 * default implementations for ActionLabels with no variables
	 */

	public Vector<String> getActions(Hashtable<String, Value> locals,
			Hashtable<String, Value> constants) {
		final Vector<String> v = new Vector<String>();
		initContext(locals, constants);
		while (hasMoreNames()) {
			final String s = nextName();
			v.addElement(s);
		}
		clearContext();
		return v;
	}

	public boolean hasMultipleValues() {
		if (this instanceof ActionRange || this instanceof ActionSet
				|| this instanceof ActionVarRange
				|| this instanceof ActionVarSet)
			return true;
		else if (follower != null)
			return follower.hasMultipleValues();
		return false;
	}

	/**
	 * default implementations for ActionLabels with no variables
	 */

	protected void checkDuplicateVarDefn() { /* nothing */
	}

	protected void removeVarDefn() { /* nothing */
	}

	protected abstract String computeName();

	protected abstract void next();

	protected abstract void initialise();

	@Override
	public ActionLabels clone() {
		ActionLabels an;
		try {
			an = (ActionLabels) super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new RuntimeException("Internal error: Cannot clone object: "
					+ e.getMessage(), e);
		}
		an.locals = null;
		an.globals = null;
		an.follower = follower == null ? null : follower.clone();
		return an;
	}

}

/**
 * -- evaluate lowerident labels
 */
class ActionName extends ActionLabels {

	protected Symbol name;

	public ActionName(Symbol name) {
		this.name = name;
	}

	@Override
	protected String computeName() {
		return name.toString();
	}

	protected boolean consumed;

	@Override
	protected void initialise() {
		consumed = false;
	}

	@Override
	protected void next() {
		consumed = true;
	}

	@Override
	public boolean hasMoreNames() {
		return !consumed;
	}

}

/**
 * -- evaluate [expr] labels
 */
class ActionExpr extends ActionLabels {

	protected Stack<Symbol> expr;

	public ActionExpr(Stack<Symbol> expr) {
		this.expr = expr;
	}

	@Override
	protected String computeName() {
		final Value v = Expression.getValue(expr, locals, globals);
		return v.toString();
	}

	protected boolean consumed;

	@Override
	protected void initialise() {
		consumed = false;
	}

	@Override
	protected void next() {
		consumed = true;
	}

	@Override
	public boolean hasMoreNames() {
		return !consumed;
	}

}

/**
 * -- evaluate {a,b,c,d,e} labels
 */
class ActionSet extends ActionLabels {

	protected LabelSet set;
	protected Vector<String> actions;

	public ActionSet(LabelSet set) {
		this.set = set;
	}

	@Override
	protected String computeName() {
		return actions.elementAt(current);
	}

	protected int current, high, low;

	@Override
	protected void initialise() {
		actions = set.getActions(locals, globals);
		current = low = 0;
		high = actions.size() - 1;
	}

	@Override
	protected void next() {
		++current;
	}

	@Override
	public boolean hasMoreNames() {
		return (current <= high);
	}

	@Override
	public ActionLabels clone() {
		final ActionSet clone = (ActionSet) super.clone();
		// reset actions
		clone.actions = null;
		return clone;
	}

}

/**
 * -- evaluates {a,b,c,d,e}\{d,e} labels
 */
class ActionSetExpr extends ActionLabels {

	protected LabelSet left;
	protected LabelSet right;
	protected Vector<String> actions;

	protected int current, high, low;

	public ActionSetExpr(LabelSet left, LabelSet right) {
		this.left = left;
		this.right = right;
	}

	@Override
	protected String computeName() {
		return actions.elementAt(current);
	}

	@Override
	protected void initialise() {
		final Vector<String> left_actions = left.getActions(locals, globals);
		final Vector<String> right_actions = right.getActions(locals, globals);
		actions = new Vector<String>();
		final Enumeration<String> e = left_actions.elements();
		while (e.hasMoreElements()) {
			final String s = e.nextElement();
			if (!right_actions.contains(s))
				actions.addElement(s);
		}
		current = low = 0;
		high = actions.size() - 1;
	}

	@Override
	protected void next() {
		++current;
	}

	@Override
	public boolean hasMoreNames() {
		return (current <= high);
	}

	@Override
	public ActionSetExpr clone() {
		final ActionSetExpr clone = (ActionSetExpr) super.clone();
		// reset all but left and right
		clone.actions = null;
		clone.current = clone.high = clone.low = 0;
		return clone;
	}

}

/**
 * -- evaluate [low..high] labels
 */
class ActionRange extends ActionLabels {

	Stack<Symbol> rlow;
	Stack<Symbol> rhigh;

	public ActionRange(Stack<Symbol> low, Stack<Symbol> high) {
		this.rlow = low;
		this.rhigh = high;
	}

	public ActionRange(Range r) {
		rlow = r.low;
		rhigh = r.high;
	}

	@Override
	protected String computeName() {
		return String.valueOf(current);
	}

	protected int current, high, low;

	@Override
	protected void initialise() {
		low = Expression.evaluate(rlow, locals, globals);
		high = Expression.evaluate(rhigh, locals, globals);
		if (low > high)
			Diagnostics.fatal("Range not defined", rlow.peek());
		current = low;
	}

	@Override
	protected void next() {
		++current;
	}

	@Override
	public boolean hasMoreNames() {
		return (current <= high);
	}

}

/**
 * -- evaluate [i:low..high] labels
 */
class ActionVarRange extends ActionRange {

	protected Symbol var;

	public ActionVarRange(Symbol var, Stack<Symbol> low, Stack<Symbol> high) {
		super(low, high);
		this.var = var;
	}

	public ActionVarRange(Symbol var, Range r) {
		super(r);
		this.var = var;
	}

	@Override
	protected String computeName() {
		if (locals != null)
			locals.put(var.toString(), new Value(current));
		return String.valueOf(current);
	}

	@Override
	protected void checkDuplicateVarDefn() {
		if (locals == null)
			return;
		if (locals.get(var.toString()) != null)
			Diagnostics.fatal("Duplicate variable definition: " + var, var);
	}

	@Override
	protected void removeVarDefn() {
		if (locals != null)
			locals.remove(var.toString());
	}

}

/**
 * -- evaluate [i:low..high] labels
 */
class ActionVarSet extends ActionSet {

	protected Symbol var;

	public ActionVarSet(Symbol var, LabelSet set) {
		super(set);
		this.var = var;
	}

	@Override
	protected String computeName() {
		final String s = actions.elementAt(current);
		if (locals != null)
			locals.put(var.toString(), new Value(s));
		return s;
	}

	@Override
	protected void checkDuplicateVarDefn() {
		if (locals == null)
			return;
		if (locals.get(var.toString()) != null)
			Diagnostics.fatal("Duplicate variable definition: " + var, var);
	}

	@Override
	protected void removeVarDefn() {
		if (locals != null)
			locals.remove(var.toString());
	}

}
