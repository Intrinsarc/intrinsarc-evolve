package ac.ic.doc.mtsa;

import java.io.*;

import lts.*;
import ui.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.util.fsp.*;
import dispatcher.*;

public class MTSCompiler {

	private static MTSCompiler instance = new MTSCompiler();

	public static MTSCompiler getInstance() {
		return instance;
	}

	private MTSCompiler() {
	}

	public MTS<Long, String> compileMTS(String inputString, String modelName)
			throws Exception {
		CompositeState compositeState = compileCompositeState(inputString,
				modelName);
		return AutomataToMTSConverter.getInstance().convert(
				compositeState.getComposition());
	}

	public CompositeState compileCompositeState(String modelName, File inputFile)
			throws IOException {
		LTSInput input = new FileInput(inputFile);

		return compileComposite(modelName, input);
	}

	public CompositeState compileCompositeState(String inputString,
			String modelName) throws IOException {
		return compileComposite(modelName, new LTSInputString(inputString));
	}

	private CompositeState compileComposite(String modelName, LTSInput input)
			throws IOException {
		LTSOutput output = new StandardOutput();
		String currentDirectory = (new File(".")).getCanonicalPath();
		LTSCompiler compiler = new LTSCompiler(input, output, currentDirectory);
		lts.SymbolTable.init();
		CompositeState c = compiler.compile(modelName);
		TransitionSystemDispatcher.applyComposition(c, output);
		return c;
	}
}
