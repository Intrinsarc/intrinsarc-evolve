package ltsa.lts;

import java.util.*;

public class ProgressTest {

	String name;
	Vector<String> pactions; // vector of strings
	BitSet pset;
	Vector<String> cactions; // if P then C
	BitSet cset;

	static Vector<ProgressTest> tests; // vector of progress tests

	public static void init() {
		tests = new Vector<ProgressTest>();
	}

	public ProgressTest(String name, Vector<String> pactions,
			Vector<String> cactions) {
		this.name = name;
		this.cactions = cactions;
		this.pactions = pactions;
		tests.addElement(this);
	}

	// return convert sets of actions to bitset using alphabet
	public static void initTests(String alphabet[]) {
		if (tests == null || tests.size() == 0)
			return;
		// convert alphabet to hashtable
		Hashtable<String, Integer> stoi = new Hashtable<String, Integer>(
				alphabet.length);
		for (int i = 0; i < alphabet.length; ++i)
			stoi.put(alphabet[i], new Integer(i));
		// for each key
		Enumeration<ProgressTest> e = tests.elements();
		while (e.hasMoreElements()) {
			ProgressTest p = e.nextElement();
			p.pset = alphaToBit(p.pactions, stoi);
			p.cset = alphaToBit(p.cactions, stoi);
		}
	}

	public static boolean noTests() {
		return (tests == null || tests.size() == 0);
	}

	private static BitSet alphaToBit(Vector<String> actions,
			Hashtable<String, Integer> stoi) {
		if (actions == null)
			return null;
		BitSet b = new BitSet(stoi.size());
		Enumeration<String> en = actions.elements();
		while (en.hasMoreElements()) {
			String s = en.nextElement();
			Integer I = stoi.get(s);
			if (I != null)
				b.set(I.intValue());
		}
		return b;
	}

}
