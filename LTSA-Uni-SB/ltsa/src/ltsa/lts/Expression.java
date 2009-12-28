package ltsa.lts;

import java.util.*;

/* -----------------------------------------------------------------------*/

public class Expression {

	static Hashtable<String, Value> constants;

	private static String labelVar(Stack<Symbol> s,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		if (s == null)
			return null;
		if (s.empty())
			return null;
		Symbol token = s.peek();
		if (token.kind == Symbol.IDENTIFIER) {
			if (locals != null) {
				Value vr = locals.get(token.toString());
				if (vr != null && vr.isLabel()) {
					s.pop();
					return vr.toString();
				}
			}
		} else if (token.kind == Symbol.UPPERIDENT) {
			Value vr = null;
			if (globals != null)
				vr = globals.get(token.toString());
			if (vr == null)
				vr = constants.get(token.toString());
			if (vr != null && vr.isLabel()) {
				s.pop();
				return vr.toString();
			}
		} else if (token.kind == Symbol.LABELCONST) { // this is a label
			// constant
			ActionLabels el = (ActionLabels) token.getAny();
			if (el.hasMultipleValues())
				Diagnostics.fatal("label constants cannot be sets", token);
			el.initContext(locals, globals);
			s.pop();
			return el.nextName();
		} else if (token.kind == Symbol.AT) { // this is a set index selection
			return indexSet(s, locals, globals);
		}
		return null;
	}

	protected static int countSet(Symbol token,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		if (token.kind != Symbol.LABELCONST)
			Diagnostics.fatal("label set expected", token);
		ActionLabels el = (ActionLabels) token.getAny();
		el.initContext(locals, globals);
		int count = 0;
		while (el.hasMoreNames()) {
			++count;
			el.nextName();
		}
		el.clearContext();
		return count;
	}

	protected static String indexSet(Stack<Symbol> s,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		s.pop();
		int index = eval(s, locals, globals);
		Symbol token = s.pop();
		if (token.kind != Symbol.LABELCONST)
			Diagnostics.fatal("label set expected", token);
		ActionLabels el = (ActionLabels) token.getAny();
		el.initContext(locals, globals);
		int count = 0;
		String label = null;
		while (el.hasMoreNames()) {
			label = el.nextName();
			if (count == index)
				break;
			++count;
		}
		el.clearContext();
		if (count != index)
			Diagnostics.fatal("label set index expression out of range", token);
		return label;
	}

	@SuppressWarnings("unchecked")
	public static int evaluate(Stack<Symbol> s,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		Stack<Symbol> mine = (Stack<Symbol>) s.clone();
		return eval(mine, locals, globals);
	}

	@SuppressWarnings("unchecked")
	public static Value getValue(Stack<Symbol> s,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		Stack<Symbol> mine = (Stack<Symbol>) s.clone();
		return getVal(mine, locals, globals);
	}

	private static Value getVal(Stack<Symbol> s,
			Hashtable<String, Value> locals, Hashtable<String, Value> globals) {
		String str = labelVar(s, locals, globals);
		if (str != null)
			return new Value(str);
		return new Value(eval(s, locals, globals));
	}

	private static int eval(Stack<Symbol> s, Hashtable<String, Value> locals,
			Hashtable<String, Value> globals) {
		Symbol token = s.pop();
		switch (token.kind) {
		case Symbol.INT_VALUE:
			return token.intValue();
		case Symbol.IDENTIFIER:
			if (locals == null)
				Diagnostics.fatal("no variables defined", token);
			Value variable = locals.get(token.toString());
			if (variable == null)
				Diagnostics.fatal("variable not defined - " + token, token);
			if (variable.isLabel())
				Diagnostics.fatal("no integer variable - " + token, token);
			return variable.intValue();
		case Symbol.UPPERIDENT:
			Value constant = null;
			if (globals != null)
				constant = globals.get(token.toString());
			if (constant == null)
				constant = constants.get(token.toString());
			if (constant == null)
				Diagnostics.fatal("constant or parameter not defined - "
						+ token, token);
			if (constant.isLabel())
				Diagnostics.fatal(
						"no integer constant or parameter - " + token, token);
			return constant.intValue();
		case Symbol.HASH:
			return countSet(s.pop(), locals, globals);
		case Symbol.PLUS:
		case Symbol.MINUS:
		case Symbol.STAR:
		case Symbol.DIVIDE:
		case Symbol.MODULUS:
		case Symbol.CIRCUMFLEX:
		case Symbol.BITWISE_AND:
		case Symbol.BITWISE_OR:
		case Symbol.SHIFT_LEFT:
		case Symbol.SHIFT_RIGHT:
		case Symbol.LESS_THAN:
		case Symbol.LESS_THAN_EQUAL:
		case Symbol.GREATER_THAN:
		case Symbol.GREATER_THAN_EQUAL:
		case Symbol.EQUALS:
		case Symbol.NOT_EQUAL:
		case Symbol.AND:
		case Symbol.OR:
			Value right = getVal(s, locals, globals);
			Value left = getVal(s, locals, globals);
			if (right.isInt() && left.isInt()) {
				return exec_op(token.kind, left.intValue(), right.intValue());
			} else if (token.kind == Symbol.EQUALS
					|| token.kind == Symbol.NOT_EQUAL) {
				if (token.kind == Symbol.EQUALS)
					return left.toString().equals(right.toString()) ? 1 : 0;
				else
					return left.toString().equals(right.toString()) ? 0 : 1;
			} else
				Diagnostics.fatal("invalid expression", token);
			// this break is never reached... but eclipse doesn't know ;)
			break;
		case Symbol.UNARY_PLUS:
			return eval(s, locals, globals);
		case Symbol.UNARY_MINUS:
			return -eval(s, locals, globals);
		case Symbol.PLING:
			return eval(s, locals, globals) > 0 ? 0 : 1;
		default:
			Diagnostics.fatal("invalid expression", token);
		}
		return 0;
	}

	private static int exec_op(int op, int left, int right) {
		switch (op) {
		case Symbol.PLUS:
			return left + right;
		case Symbol.MINUS:
			return left - right;
		case Symbol.STAR:
			return left * right;
		case Symbol.DIVIDE:
			return left / right;
		case Symbol.MODULUS:
			return left % right;
		case Symbol.CIRCUMFLEX:
			return left ^ right;
		case Symbol.BITWISE_AND:
			return left & right;
		case Symbol.BITWISE_OR:
			return left | right;
		case Symbol.SHIFT_LEFT:
			return left << right;
		case Symbol.SHIFT_RIGHT:
			return left >> right;
		case Symbol.LESS_THAN:
			return left < right ? 1 : 0;
		case Symbol.LESS_THAN_EQUAL:
			return left <= right ? 1 : 0;
		case Symbol.GREATER_THAN:
			return left > right ? 1 : 0;
		case Symbol.GREATER_THAN_EQUAL:
			return left >= right ? 1 : 0;
		case Symbol.EQUALS:
			return left == right ? 1 : 0;
		case Symbol.NOT_EQUAL:
			return left != right ? 1 : 0;
		case Symbol.AND:
			return (left != 0) && (right != 0) ? 1 : 0;
		case Symbol.OR:
			return (left != 0) || (right != 0) ? 1 : 0;
		}
		return 0;
	}

}