package ltsa.lts;

import java.util.*;

/* -----------------------------------------------------------------------*/

class Range extends Declaration {
	static Hashtable<String, Range> ranges;
	Stack<Symbol> low;
	Stack<Symbol> high;
}