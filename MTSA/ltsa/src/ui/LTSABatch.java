package ui;

import java.io.*;
import java.util.*;

import lts.*;

public class LTSABatch implements LTSManager, LTSInput, LTSOutput, LTSError {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileTxt = "";
		LTSABatch batch = null;
		try {
			// the command line args should be:
			// <file> [monolithic | recurse <numberOfModules> |
			// <numberOfModules> [memoize [project]] refine <heuristic>]
			// if any option is left out
			// it is assumed to be false
			// the memoize option refers to extra memoization during refinement
			// some memoization is done by default in learning
			// the memoize option may be chosen only when refine is also chosen
			if (args.length == 0) {
				System.out
						.println("Need at least the input model in a file *.lts.\n Run by:\n\t"
								+ "<file> [monolithic | recurse <numberOfModules> | <numberOfModules> [memoize [project]] refine <heuristic>]\n"
								+ "Do NOT use 'memoize [project]' with recursive refinement. Won't work correctly. Later."
								+ "Available refinement heuristics: fwd, bwd, allDiff, bwdPluspropAlpha, prevAlpha "
								+ "(for recursive only and it does bwd without initializing by property alphabet)\n");
				return;
			}
			BufferedReader file = new BufferedReader(new FileReader(args[0]));
			String thisLine;
			StringBuffer buff = new StringBuffer();
			while ((thisLine = file.readLine()) != null) {
				buff.append(thisLine + "\n");
			}
			file.close();
			fileTxt = buff.toString();
		} catch (Exception e) {
			System.out.println("Error reading file: " + e);
		}
		// parse arguments
		if (args.length == 1) // assumes <file>
			batch = new LTSABatch(fileTxt, 2, false, false, "none", false);
		if (args.length == 2) {// assumes <file> monolithic
			batch = new LTSABatch(fileTxt, 2, false, false, "none", false);
			batch.safety();
			System.exit(0);
		} else if (args.length == 3) // assumes <file> recurse <number of
			// modules>
			batch = new LTSABatch(fileTxt, Integer.parseInt(args[2]), false,
					false, "none", false);
		else if (args.length == 4) // assumes <file> <number of modules> refine
			// <heuristic> ("refine" is "true")
			batch = new LTSABatch(fileTxt, Integer.parseInt(args[1]), false,
					true, args[3], false);
		else if (args.length == 5) // assume <file> <number of modules> memoize
			// refine <heuristic>
			batch = new LTSABatch(fileTxt, Integer.parseInt(args[1]), true,
					true, args[4], false);
		else if (args.length > 5) // assume <file> <number of modules> memoize
			// project refine <heuristic> ...rest
			// irrelevant
			batch = new LTSABatch(fileTxt, Integer.parseInt(args[1]), true,
					true, args[5], true);

		// parse model
		batch.parse();

		// do compositional learning
		// batch.compositionBatch.SET();
		System.exit(0);
	}
	CompositeState current = null;
	Set<String> compositeNames = new HashSet<String>();
	String currentDirectory = System.getProperty("user.dir");
	Hashtable<String, LabelSet> labelSetConstants = null;
	// SETCompositionalBatch compositionBatch = null;
	String model = "";
	int fPos = -1;

	String fSrc = "\n";

	public LTSABatch(String fileTxt, int modulesCount, boolean memo,
			boolean ref, String heur, boolean proj) {
		SymbolTable.init();
		// compositionBatch =
		// new SETCompositionalBatch(this,this,this,this,true,
		// memo, ref, heur,proj,modulesCount);
		model = fileTxt;
	}

	public char backChar() {
		fPos = fPos - 1;
		if (fPos < 0) {
			fPos = 0;
			return '\u0000';
		} else
			return fSrc.charAt(fPos);
	}

	public void clearOutput() {
		// not needed
	}

	private void compile() {
		if (!parse())
			return;
		current = docompile();
	}

	public CompositeState compile(String name) {
		fPos = -1;
		fSrc = model;
		CompositeState cs = null;
		LTSCompiler comp = new LTSCompiler(this, this, currentDirectory);
		try {
			cs = comp.compile(name);
		} catch (LTSException x) {
			displayError(x);
		}
		return cs;
	}

	public void compileIfChange() {
		// not needed
	}

	public void displayError(LTSException x) {
		outln("ERROR - " + x.getMessage());
	}

	private CompositeState docompile() {
		fPos = -1;
		fSrc = model;
		CompositeState cs = null;
		LTSCompiler comp = new LTSCompiler(this, this, currentDirectory);
		try {
			cs = comp.compile("ALL");
		} catch (LTSException x) {
			displayError(x);
		}
		return cs;
	}

	private Hashtable doparse() {
		Hashtable cs = new Hashtable();
		Hashtable ps = new Hashtable();
		doparse(cs, ps);
		return cs;
	}

	private void doparse(Hashtable cs, Hashtable ps) {
		fPos = -1;
		fSrc = model;
		LTSCompiler comp = new LTSCompiler(this, this, currentDirectory);
		try {
			comp.parse(cs, ps);
		} catch (LTSException x) {
			displayError(x);
			cs = null;
		}
	}

	public Set<String> getCompositeNames() {
		return compositeNames;
	}

	/**
	 * Returns the current labeled transition system.
	 */
	public CompositeState getCurrentTarget() {
		return current;
	}

	/**
	 * Returns the set of actions which correspond to the label set definition
	 * with the given name.
	 */
	public Set<String> getLabelSet(String name) {
		if (labelSetConstants == null)
			return null;

		Set<String> s = new HashSet<String>();
		LabelSet ls = labelSetConstants.get(name);

		if (ls == null)
			return null;

		for (String a : (Vector<String>) ls.getActions(null))
			s.add(a);

		return s;
	}

	public int getMarker() {
		return fPos;
	}

	public String getTargetChoice() {
		// not needed
		return "";
	}

	public void newMachines(java.util.List<CompactState> machines) {
		// not needed
	}

	public char nextChar() {
		fPos = fPos + 1;
		if (fPos < fSrc.length()) {
			return fSrc.charAt(fPos);
		} else {
			// fPos = fPos - 1;
			return '\u0000';
		}
	}

	public void out(String str) {
		System.out.print(str);
	}

	public void outln(String str) {
		System.out.println(str);
	}

	public boolean parse() {
		// >>> AMES: Enhanced Modularity
		Hashtable cs = new Hashtable();
		Hashtable ps = new Hashtable();
		doparse(cs, ps);
		// <<< AMES

		if (cs == null)
			return false;
		if (cs.size() == 0) {
			compositeNames.add("DEFAULT");
		} else {
			Enumeration e = cs.keys();
			java.util.List forSort = new ArrayList();
			while (e.hasMoreElements()) {
				forSort.add(e.nextElement());
			}
			Collections.sort(forSort);
			for (Iterator i = forSort.iterator(); i.hasNext();) {
				compositeNames.add((String) i.next());
			}
		}
		current = null;

		return true;
	}

	public void performAction(final Runnable r, final boolean showOutputPane) {
		// not needed
	}

	public void resetMarker() {
		fPos = -1;
	}

	private void safety() {
		safety(true, false);
	}

	private void safety(boolean checkDeadlock, boolean multiCe) {
		compile();
		if (current != null) {
			// no sirve asi!
			current.analyse(checkDeadlock, this);
		}
	}

}
