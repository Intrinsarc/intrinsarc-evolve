package ltsa.ui;

import java.util.*;

import ltsa.lts.*;

public class MinimalManager implements LTSManager {

	LTSInput input;
	LTSOutput output;
	LTSError error;
	String currentDirectory;

	Hashtable<String, LabelSet> labelSetConstants = null;

	public MinimalManager(LTSInput input, LTSOutput output, LTSError error,
			String currentDirectory) {
		this.input = input;
		this.output = output;
		this.error = error;
		this.currentDirectory = currentDirectory;
		parse();
	}

	public boolean parse() {
		input.resetMarker();
		Hashtable<String, CompositionExpression> cs;
		final LTSCompiler comp = new LTSCompiler(input, output,
				currentDirectory);
		try {
			comp.parse(cs = new Hashtable<String, CompositionExpression>(),
					new Hashtable<String, ProcessSpec>());

		} catch (final LTSException x) {
			error.displayError(x);
			cs = null;
		}

		labelSetConstants = LabelSet.getConstants();
		return cs != null;
	}

	public CompositeState compile(String name) {
		input.resetMarker();
		CompositeState cs = null;
		final LTSCompiler comp = new LTSCompiler(input, output,
				currentDirectory);
		try {
			cs = comp.compile(name);
		} catch (final LTSException x) {
			error.displayError(x);
		}
		return cs;
	}

	public Set<String> getLabelSet(String name) {
		if (labelSetConstants == null)
			return null;

		final Set<String> s = new HashSet<String>();
		final LabelSet ls = labelSetConstants.get(name);

		if (ls == null)
			return null;

		for (final String a : ls.getActions(null))
			s.add(a);

		return s;
	}

	public String getTargetChoice() {
		return null;
	}

	public void newMachines(List<CompactState> machines) {
		// null operation (AMES)
	}

	public void performAction(Runnable r, boolean showOutputPane) {
		r.run();
	}

}
