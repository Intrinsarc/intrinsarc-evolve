package ui;

// This is an experimental version with progress & LTL property check

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import lts.Analyser;
import lts.Animator;
import lts.CompositeState;
import lts.Diagnostics;
import lts.EventManager;
import lts.LTSCanvas;
import lts.LTSCompiler;
import lts.LTSEvent;
import lts.LTSException;
import lts.LTSInput;
import lts.LTSOutput;
import lts.MenuDefinition;
import lts.ProgressCheck;
import lts.Relation;
import lts.RunMenu;
import lts.SuperTrace;
import lts.SymbolTable;
import lts.ltl.AssertDefinition;
import lts.ltl.FormulaFactory;

import com.hopstepjump.backbone.api.ICreate;
import com.hopstepjump.backbone.api.IRun;
import com.jtattoo.plaf.BaseTheme;
import com.jtattoo.plaf.smart.SmartLookAndFeel;

import custom.CustomAnimator;
import custom.SceneAnimator;
import editor.ColoredEditorKit;

public class HPWindow
{
	private static final String VERSION = " j1.2 v14-10-99, amimation support";
	private static final String DEFAULT = "DEFAULT";

// start generated code
	private String title;
	private com.hopstepjump.backbone.api.IRun run_IRunProvided = new IRunRunImpl();

	private ui.IAlphabetWindow alpha_IAlphabetWindowRequired;

	private com.hopstepjump.backbone.api.ICreate createAlpha_ICreateRequired;
// end generated code

	JTextArea output;
	JEditorPane input;
	JEditorPane manual;
	PrintWindow prints;
	LTSDrawWindow draws;
	JTabbedPane textIO;
	JToolBar tools;
	JFrame main;

	JComboBox targetChoice;
	JPanel p;
	EventManager eman = new EventManager();
	Frame animator = null;
	CompositeState current = null;
	String run_menu = DEFAULT;
	String asserted = null;

	// Listener for the edits on the current document.
	protected UndoableEditListener undoHandler = new UndoHandler();
	// UndoManager that we add edits to.
	protected UndoManager undo = new UndoManager();

	JMenu file, edit, check, build, window, help, option;
	JMenuItem file_new, file_open, file_save, file_saveAs, file_export,
			file_exit, edit_cut, edit_copy, edit_paste, edit_undo, edit_redo,
			check_safe, check_progress, check_reachable, check_stop,
			build_parse, build_compile, build_compose, build_minimise,
			help_about, supertrace_options;
	JMenu check_run, file_example, check_liveness;
	JMenuItem default_run;
	JMenuItem[] run_items, assert_items;
	String[] run_names, assert_names;
	boolean[] run_enabled;
	JCheckBoxMenuItem setWarnings;
	JCheckBoxMenuItem setWarningsAreErrors;
	JCheckBoxMenuItem setFair;
	JCheckBoxMenuItem setAlphaLTL;
	JCheckBoxMenuItem setSynchLTL;
	JCheckBoxMenuItem setPartialOrder;
	JCheckBoxMenuItem setObsEquiv;
	JCheckBoxMenuItem setReduction;
	JCheckBoxMenuItem setBigFont;
	JCheckBoxMenuItem setDisplayName;
	JCheckBoxMenuItem setNewLabelFormat;
	JCheckBoxMenuItem setAutoRun;
	JCheckBoxMenuItem setMultipleLTS;
	JCheckBoxMenuItem help_manual;
	JCheckBoxMenuItem window_alpha;
	JCheckBoxMenuItem window_print;
	JCheckBoxMenuItem window_draw;
	// tool bar buttons - that need to be enabled and disabled
	JButton stopTool, parseTool, safetyTool, progressTool, cutTool, pasteTool,
			newFileTool, openFileTool, saveFileTool, compileTool, composeTool,
			minimizeTool, undoTool, redoTool;
	// used to implement muCSPInput
	int fPos = -1;
	String fSrc = "\n";
	Font fixed = new Font("Monospaced", Font.PLAIN, 12);
	Font big = new Font("Monospaced", Font.BOLD, 20);
	// Font title = new Font("SansSerif",Font.PLAIN,12);
	private RunnableImpl runnableImpl = new RunnableImpl();
	private InputImpl inputImpl = new InputImpl();
	private OutputImpl outputImpl = new OutputImpl();

	private AppletButton isApplet = null;

	public HPWindow()
	{
	}
	
	private class RunnableImpl implements Runnable
	{
		public void run() {
			try {
				switch (theAction) {
				case DO_safety:
					showOutput();
					safety();
					break;
				case DO_execute:
					execute();
					break;
				case DO_reachable:
					showOutput();
					reachable();
					break;
				case DO_compile:
					showOutput();
					compile();
					break;
				case DO_doComposition:
					showOutput();
					doComposition();
					break;
				case DO_minimiseComposition:
					showOutput();
					minimiseComposition();
					break;
				case DO_progress:
					showOutput();
					progress();
					break;
				case DO_liveness:
					showOutput();
					liveness();
					break;
				case DO_parse:
					parse();
					break;
				}
			} catch (Throwable e) {
				showOutput();
				outln("**** Runtime Exception: " + e);
				e.printStackTrace();
				current = null;
			}
			menuEnable(true);
			check_stop.setEnabled(false);
			stopTool.setEnabled(false);
		}
	}
	
	private class InputImpl implements LTSInput
	{
		// Implementation of the DarwinEnvironment Interface

		public char nextChar() {
			fPos = fPos + 1;
			if (fPos < fSrc.length()) {
				return fSrc.charAt(fPos);
			} else {
				// fPos = fPos - 1;
				return '\u0000';
			}
		}

		public char backChar() {
			char ch;
			fPos = fPos - 1;
			if (fPos < 0) {
				fPos = 0;
				return '\u0000';
			} else
				return fSrc.charAt(fPos);
		}

		public int getMarker() {
			return fPos;
		}
	}
	
	private class OutputImpl implements LTSOutput
	{
		public void out(String str)
		{
			SwingUtilities.invokeLater(new OutputAppend(str));
		}

		public void outln(String str)
		{
			SwingUtilities.invokeLater(new OutputAppend(str + "\n"));
		}

		public void clearOutput()
		{
			SwingUtilities.invokeLater(new OutputClear());
		}


	}
	
	// ----------------------------------------------------------------------
	static void centre(Component c) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		Dimension ltsa = c.getSize();
		double x = (screen.getWidth() - ltsa.getWidth()) / 2;
		double y = (screen.getHeight() - ltsa.getHeight()) / 2;
		c.setLocation((int) x, (int) y);
	}

	// ----------------------------------------------------------------------
	void left(Component c) {
		Point ltsa = main.getLocationOnScreen();
		ltsa.translate(10, 100);
		c.setLocation(ltsa);
	}

	// -----------------------------------------------------------------------
	protected JButton createTool(String icon, String tip, ActionListener act) {
		JButton b = new JButton(
				new ImageIcon(this.getClass().getResource(icon))) {
			public float getAlignmentY() {
				return 0.5f;
			}
		};
		b.setRequestFocusEnabled(false);
		b.setMargin(new Insets(0, 0, 0, 0));
		b.setToolTipText(tip);
		b.addActionListener(act);
		return b;
	}

	// ------------------------------------------------------------------------

	void menuEnable(boolean flag) {
		boolean application = (isApplet == null);
		file_new.setEnabled(flag && tabindex == 0);
		file_example.setEnabled(flag && tabindex == 0);
		file_open.setEnabled(application && flag && tabindex == 0);
		file_exit.setEnabled(flag);
		check_safe.setEnabled(flag);
		check_progress.setEnabled(flag);
		check_run.setEnabled(flag);
		check_reachable.setEnabled(flag);
		build_parse.setEnabled(flag);
		build_compile.setEnabled(flag);
		build_compose.setEnabled(flag);
		build_minimise.setEnabled(flag);
		parseTool.setEnabled(flag);
		safetyTool.setEnabled(flag);
		progressTool.setEnabled(flag);
		compileTool.setEnabled(flag);
		composeTool.setEnabled(flag);
		minimizeTool.setEnabled(flag);
	}

	// ------------------------------------------------------------------------
	private final static int DO_safety = 1;
	private final static int DO_execute = 3;
	private final static int DO_reachable = 4;
	private final static int DO_compile = 5;
	private final static int DO_doComposition = 6;
	private final static int DO_minimiseComposition = 7;
	private final static int DO_progress = 8;
	private final static int DO_liveness = 9;
	private final static int DO_parse = 10;

	private int theAction = 0;
	private Thread executer;

	private void do_action(int action) {
		menuEnable(false);
		check_stop.setEnabled(true);
		stopTool.setEnabled(true);
		theAction = action;
		executer = new Thread(runnableImpl);
		executer.setPriority(Thread.NORM_PRIORITY - 1);
		executer.start();
	}

	// ------------------------------------------------------------------------

	class CloseWindow extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			quitAll();
		}

		public void windowActivated(WindowEvent e) {
			if (animator != null)
				animator.toFront();
		}
	}

	// ------------------------------------------------------------------------
	private void invalidateState() {
		current = null;
		targetChoice.removeAllItems();
		targetChoice.addItem(DEFAULT);
		check_run.removeAll();
		check_run.add(default_run);
		run_items = null;
		assert_items = null;
		run_names = null;
		check_liveness.removeAll();
		main.validate();
		eman.post(new LTSEvent(LTSEvent.INVALID, null));
		if (animator != null) {
			animator.dispose();
			animator = null;
		}
	}

	private void postState(CompositeState m) {
		if (animator != null) {
			animator.dispose();
			animator = null;
		}
		eman.post(new LTSEvent(LTSEvent.INVALID, m));
	}

	// ------------------------------------------------------------------------
	// File handling
	// -----------------------------------------------------------------------

	private final static String fileType = "*.lts";
	private String openFile = fileType;
	String currentDirectory;
	private String savedText = "";

	private void newFile() {
		if (checkSave()) {
			main.setTitle("LTS Analyser");
			savedText = "";
			openFile = fileType;
			input.setText("");
			swapto(0);
			output.setText("");
			invalidateState();
		}
		main.repaint(); // hack to solve display problem
	}

	public void newExample(String dir, String ex) {
		undo.discardAllEdits();
		input.getDocument().removeUndoableEditListener(undoHandler);
		if (checkSave()) {
			invalidateState();
			clearOutput();
			doOpenFile(dir, ex, true);
		}
		input.getDocument().addUndoableEditListener(undoHandler);
		updateDoState();
		main.repaint();
	}

	// ------------------------------------------------------------------------
	private void openAFile() {
		if (checkSave()) {
			invalidateState();
			clearOutput();
			FileDialog fd = new FileDialog(main, "Select source file:");
			if (currentDirectory != null)
				fd.setDirectory(currentDirectory);
			fd.setFile(fileType);
			fd.setVisible(true);
			doOpenFile(currentDirectory = fd.getDirectory(), fd.getFile(),
					false);
		}
		main.repaint(); // hack to solve display problem
	}

	private void doOpenFile(String dir, String f, boolean resource) {
		if (f != null)
			try {
				openFile = f;
				main.setTitle("LTSA - " + openFile);
				InputStream fin;
				if (!resource)
					fin = new FileInputStream(dir + openFile);
				else
					fin = this.getClass().getResourceAsStream(dir + openFile);
				// now turn the FileInputStream into a DataInputStream
				try {
					BufferedReader myInput = new BufferedReader(
							new InputStreamReader(fin));
					try {
						String thisLine;
						StringBuffer buff = new StringBuffer();
						while ((thisLine = myInput.readLine()) != null) {
							buff.append(thisLine + "\n");
						}
						savedText = buff.toString();
						input.setText(savedText);
						parse();
					} catch (Exception e) {
						outln("Error reading file: " + e);
					}
				} // end try
				catch (Exception e) {
					outln("Error creating InputStream: " + e);
				}
			} // end try
			catch (Exception e) {
				outln("Error creating FileInputStream: " + e);
			}
	}

	// ------------------------------------------------------------------------

	private void saveAsFile() {
		FileDialog fd = new FileDialog(main, "Save file in:", FileDialog.SAVE);
		if (currentDirectory != null)
			fd.setDirectory(currentDirectory);
		fd.setFile(openFile);
		fd.setVisible(true);
		String tmp = fd.getFile();
		if (tmp != null) { // if not cancelled
			currentDirectory = fd.getDirectory();
			openFile = tmp;
			main.setTitle("LTSA - " + openFile);
			saveFile();
		}
	}

	private void saveFile() {
		if (openFile != null && openFile.equals("*.lts"))
			saveAsFile();
		else if (openFile != null)
			try {
				int i = openFile.indexOf('.', 0);
				if (i > 0)
					openFile = openFile.substring(0, i) + "." + "lts";
				else
					openFile = openFile + ".lts";
				String tempname = (currentDirectory == null) ? openFile
						: currentDirectory + openFile;
				FileOutputStream fout = new FileOutputStream(tempname);
				// now convert the FileOutputStream into a PrintStream
				PrintStream myOutput = new PrintStream(fout);
				savedText = input.getText();
				myOutput.print(savedText);
				myOutput.close();
				fout.close();
				outln("Saved in: " + tempname);
			} catch (IOException e) {
				outln("Error saving file: " + e);
			}
	}

	// -------------------------------------------------------------------------

	private void exportFile() {
		String message = "Export as Aldebaran format (.aut) to:";
		FileDialog fd = new FileDialog(main, message, FileDialog.SAVE);
		if (current == null || current.composition == null) {
			JOptionPane.showMessageDialog(main,
					"No target composition to export");
			return;
		}
		String fname = current.composition.name;
		fd.setFile(fname + ".aut");
		fd.setDirectory(currentDirectory);
		fd.setVisible(true);
		String sn;
		if ((sn = fd.getFile()) != null)
			try {
				int i = sn.indexOf('.', 0);
				sn = sn.substring(0, i) + ".aut";
				File file = new File(fd.getDirectory(), sn);
				FileOutputStream fout = new FileOutputStream(file);
				// now convert the FileOutputStream into a PrintStream
				PrintStream myOutput = new PrintStream(fout);
				current.composition.printAUT(myOutput);
				myOutput.close();
				fout.close();
				outln("Exported to: " + fd.getDirectory() + file);
			} catch (IOException e) {
				outln("Error exporting file: " + e);
			}
	}

	// ------------------------------------------------------------------------
	// return false if operation cancelled otherwise true
	private boolean checkSave() {
		if (isApplet != null)
			return true;
		if (!savedText.equals(input.getText())) {
			int result = JOptionPane.showConfirmDialog(main,
					"Do you want to save the contents of " + openFile);
			if (result == JOptionPane.YES_OPTION) {
				saveFile();
				return true;
			} else if (result == JOptionPane.NO_OPTION)
				return true;
			else if (result == JOptionPane.CANCEL_OPTION)
				return false;
		}
		return true;
	}

	// ------------------------------------------------------------------------
	private void doFont() {
		if (setBigFont.getState()) {
			input.setFont(big);
			output.setFont(big);
		} else {
			input.setFont(fixed);
			output.setFont(fixed);
		}
		main.pack();
		main.setVisible(true);
	}

	// ------------------------------------------------------------------------

	private void quitAll() {
		if (isApplet != null) {
			main.dispose();
//			isApplet.ended();
		} else {
			if (checkSave())
				System.exit(0);
		}
	}

	// ----Event
	// Handling-----------------------------------------------------------

	class NewFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			undo.discardAllEdits();
			input.getDocument().removeUndoableEditListener(undoHandler);
			newFile();
			input.getDocument().addUndoableEditListener(undoHandler);
			updateDoState();
		}
	}

	class OpenFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			undo.discardAllEdits();
			input.getDocument().removeUndoableEditListener(undoHandler);
			openAFile();
			input.getDocument().addUndoableEditListener(undoHandler);
			updateDoState();
		}
	}

	class SaveFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit") || pp.equals("Output"))
				saveFile();
			else if (pp.equals("Alphabet"))
				alpha_IAlphabetWindowRequired.saveFile();
			else if (pp.equals("Transitions"))
				prints.saveFile(currentDirectory, ".txt");
			else if (pp.equals("Draw"))
				draws.saveFile();
		}
	}

	class SaveAsFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit"))
				saveAsFile();
		}
	}

	class ExportFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit"))
				exportFile();
			else if (pp.equals("Transitions"))
				prints.saveFile(currentDirectory, ".aut");
		}
	}

	class ExitFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			quitAll();
		}
	}

	class DoAction implements ActionListener {
		int actionCode;

		DoAction(int a) {
			actionCode = a;
		}

		public void actionPerformed(ActionEvent e) {
			do_action(actionCode);
			;
		}
	}

	class OptionAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == setWarnings)
				Diagnostics.warningFlag = setWarnings.isSelected();
			else if (source == setWarningsAreErrors)
				Diagnostics.warningsAreErrors = setWarningsAreErrors
						.isSelected();
			else if (source == setFair)
				ProgressCheck.strongFairFlag = setFair.isSelected();
			else if (source == setAlphaLTL)
				AssertDefinition.addAsterisk = !setAlphaLTL.isSelected();
			else if (source == setSynchLTL)
				FormulaFactory.normalLTL = !setSynchLTL.isSelected();
			else if (source == setPartialOrder)
				Analyser.partialOrderReduction = setPartialOrder.isSelected();
			else if (source == setObsEquiv)
				Analyser.preserveObsEquiv = setObsEquiv.isSelected();
			else if (source == setReduction)
				CompositeState.reduceFlag = setReduction.isSelected();
			else if (source == setBigFont) {
				AnimWindow.fontFlag = setBigFont.isSelected();
				AlphabetWindow.fontFlag = setBigFont.isSelected();
				if (alpha_IAlphabetWindowRequired != null)
					alpha_IAlphabetWindowRequired.setBigFont(setBigFont.isSelected());
				PrintWindow.fontFlag = setBigFont.isSelected();
				if (prints != null)
					prints.setBigFont(setBigFont.isSelected());
				LTSDrawWindow.fontFlag = setBigFont.isSelected();
				if (draws != null)
					draws.setBigFont(setBigFont.isSelected());
				LTSCanvas.fontFlag = setBigFont.isSelected();
				doFont();
			} else if (source == setDisplayName) {
				if (draws != null)
					draws.setDrawName(setDisplayName.isSelected());
				LTSCanvas.displayName = setDisplayName.isSelected();
			} else if (source == setMultipleLTS) {
				LTSDrawWindow.singleMode = !setMultipleLTS.isSelected();
				if (draws != null)
					draws.setMode(LTSDrawWindow.singleMode);
			} else if (source == setNewLabelFormat) {
				if (draws != null)
					draws.setNewLabelFormat(setNewLabelFormat.isSelected());
				LTSCanvas.newLabelFormat = setNewLabelFormat.isSelected();
			}
		}
	}

	class SuperTraceOptionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSuperTraceOption();
		}
	}

	class WinAlphabetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newAlphabetWindow(window_alpha.isSelected());
		}
	}

	class WinPrintAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newPrintWindow(window_print.isSelected());
		}
	}

	class WinDrawAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newDrawWindow(window_draw.isSelected());
		}
	}

	class HelpAboutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			aboutDialog();
		}
	}

	class BlankAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			blankit();
		}
	}

	class HelpManualAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayManual(help_manual.isSelected());
		}
	}

	class StopAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (executer != null) {
				executer.stop();
				menuEnable(true);
				check_stop.setEnabled(false);
				stopTool.setEnabled(false);
				outln("\n\t-- stopped");
				executer = null;
			}
		}
	}

	class ExecuteAction implements ActionListener {
		String runtarget;

		ExecuteAction(String s) {
			runtarget = s;
		}

		public void actionPerformed(ActionEvent e) {
			run_menu = runtarget;
			do_action(DO_execute);
		}
	}

	class LivenessAction implements ActionListener {
		String asserttarget;

		LivenessAction(String s) {
			asserttarget = s;
		}

		public void actionPerformed(ActionEvent e) {
			asserted = asserttarget;
			do_action(DO_liveness);
		}
	}

	class EditCutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			input.cut();
		}
	}

	class EditCopyAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit"))
				input.copy();
			else if (pp.equals("Output"))
				output.copy();
			else if (pp.equals("Manual"))
				manual.copy();
			else if (pp.equals("Alphabet"))
				alpha_IAlphabetWindowRequired.copy();
			else if (pp.equals("Transitions"))
				prints.copy();
		}
	}

	class EditPasteAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			input.paste();
		}
	}

	class TargetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String choice = (String) targetChoice.getSelectedItem();
			if (choice == null)
				return;
			run_enabled = MenuDefinition.enabled(choice);
			if (run_items != null && run_enabled != null) {
				if (run_items.length == run_enabled.length)
					for (int i = 0; i < run_items.length; ++i)
						run_items[i].setEnabled(run_enabled[i]);
			}
		}
	}

	// --------------------------------------------------------------------
	// undo editor stuff

	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			updateDoState();
		}
	}

	class UndoAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
			}
			updateDoState();
		}
	}

	class RedoAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotUndoException ex) {
			}
			updateDoState();
		}
	}

	protected void updateDoState() {
		edit_undo.setEnabled(undo.canUndo());
		undoTool.setEnabled(undo.canUndo());
		edit_redo.setEnabled(undo.canRedo());
		redoTool.setEnabled(undo.canRedo());
	}

	// ------------------------------------------------------------------------
	private int tabindex = 0;

	private void swapto(int i) {
		if (i == tabindex)
			return;
		textIO.setBackgroundAt(i, Color.green);
		if (tabindex != i && tabindex < textIO.getTabCount())
			textIO.setBackgroundAt(tabindex, Color.lightGray);
		tabindex = i;
		setEditControls(tabindex);
		textIO.setSelectedIndex(i);
	}

	class TabChange implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int i = textIO.getSelectedIndex();
			if (i == tabindex)
				return;
			textIO.setBackgroundAt(i, Color.green);
			textIO.setBackgroundAt(tabindex, Color.lightGray);
			tabindex = i;
			setEditControls(tabindex);
		}
	}

	private void setEditControls(int tabindex) {
		boolean app = (isApplet == null);
		String pp = textIO.getTitleAt(tabindex);
		boolean b = (tabindex == 0);
		edit_cut.setEnabled(b);
		cutTool.setEnabled(b);
		edit_paste.setEnabled(b);
		pasteTool.setEnabled(b);
		file_new.setEnabled(b);
		file_example.setEnabled(b);
		file_open.setEnabled(app && b);
		file_saveAs.setEnabled(app && b);
		file_export.setEnabled(app && (b || pp.equals("Transitions")));
		newFileTool.setEnabled(b);
		openFileTool.setEnabled(app && b);
		edit_undo.setEnabled(b && undo.canUndo());
		undoTool.setEnabled(b && undo.canUndo());
		edit_redo.setEnabled(b && undo.canRedo());
		redoTool.setEnabled(b && undo.canRedo());
		if (b)
			SwingUtilities.invokeLater(new RequestFocus());
	}

	class RequestFocus implements Runnable {
		public void run() {
			input.requestFocusInWindow();
		}
	}

	// ------------------------------------------------------------------------

	public void out(String str)
	{
		SwingUtilities.invokeLater(new OutputAppend(str));
	}

	public void outln(String str)
	{
		SwingUtilities.invokeLater(new OutputAppend(str + "\n"));
	}

	public void clearOutput()
	{
		SwingUtilities.invokeLater(new OutputClear());
	}

	public void showOutput()
	{
		SwingUtilities.invokeLater(new OutputShow());
	}

	class OutputAppend implements Runnable {
		String text;

		OutputAppend(String text) {
			this.text = text;
		}

		public void run() {
			output.append(text);
		}
	}

	class OutputClear implements Runnable {
		public void run() {
			output.setText("");
		}
	}

	class OutputShow implements Runnable {
		public void run() {
			swapto(1);
		}
	}

	// ------------------------------------------------------------------------

	private void compile() {
		clearOutput();
		if (!parse())
			return;
		current = docompile();
		if (current != null) {
			postState(current);
		}
	}

	private void displayError(LTSException x) {
		if (x.marker != null) {
			int i = ((Integer) (x.marker)).intValue();
			int line = 1;
			for (int j = 0; j < i; ++j) {
				if (fSrc.charAt(j) == '\n')
					++line;
			}
			outln("ERROR line:" + line + " - " + x.getMessage());
			input.select(i, i + 1);
		} else
			outln("ERROR - " + x.getMessage());
	}

	private CompositeState docompile() {
		fPos = -1;
		fSrc = input.getText();
		CompositeState cs = null;
		LTSCompiler comp = new LTSCompiler(inputImpl, outputImpl, currentDirectory);
		try {
			cs = comp.compile((String) targetChoice.getSelectedItem());
		} catch (LTSException x) {
			displayError(x);
		}
		return cs;
	}

	private Hashtable doparse() {
		fPos = -1;
		fSrc = input.getText();
		LTSCompiler comp = new LTSCompiler(inputImpl, outputImpl, currentDirectory);
		Hashtable cs = new Hashtable();
		Hashtable ps = new Hashtable();
		try {
			comp.parse(cs, ps);
		} catch (LTSException x) {
			displayError(x);
			return null;
		}
		return cs;
	}

	private void compileIfChange() {
		String tmp = input.getText();
		if (current == null || !tmp.equals(fSrc)
				|| !current.name.equals(targetChoice.getSelectedItem()))
			compile();
	}

	// ------------------------------------------------------------------------

	private void safety() {
		clearOutput();
		compileIfChange();
		if (current != null) {
			current.analyse(outputImpl);
		}
	}

	// ------------------------------------------------------------------------

	private void progress() {
		clearOutput();
		compileIfChange();
		if (current != null) {
			current.checkProgress(outputImpl);
		}
	}

	// ------------------------------------------------------------------------

	private void liveness() {
		clearOutput();
		compileIfChange();
		CompositeState ltl_property = AssertDefinition.compile(outputImpl, asserted);
		if (current != null && ltl_property != null) {
			current.checkLTL(outputImpl, ltl_property);
			postState(current);
		}
	}

	// ------------------------------------------------------------------------

	private void minimiseComposition() {
		clearOutput();
		compileIfChange();
		if (current != null) {
			if (current.composition == null)
				current.compose(outputImpl);
			current.minimise(outputImpl);
			postState(current);
		}
	}

	// ------------------------------------------------------------------------

	private void doComposition() {
		clearOutput();
		compileIfChange();
		if (current != null) {
			current.compose(outputImpl);
			postState(current);
		}
	}

	// ------------------------------------------------------------------------
	private boolean checkReplay(Animator a) {
		if (a.hasErrorTrace()) {
			int result = JOptionPane.showConfirmDialog(main,
					"Do you want to replay the error trace?");
			if (result == JOptionPane.YES_OPTION) {
				return true;
			} else if (result == JOptionPane.NO_OPTION)
				return false;
			else if (result == JOptionPane.CANCEL_OPTION)
				return false;
		}
		return false;
	}

	private void execute() {
		clearOutput();
		compileIfChange();
		boolean replay;
		if (current != null) {
			Analyser a = new Analyser(current, outputImpl, eman);
			replay = checkReplay(a);
			if (animator != null) {
				animator.dispose();
				animator = null;
			}
			RunMenu r = null;
			if (RunMenu.menus != null)
				r = (RunMenu) RunMenu.menus.get(run_menu);
			if (r != null && r.isCustom()) {
				animator = createCustom(a, r.params, r.actions, r.controls,
						replay);
			} else {
				animator = new AnimWindow(a, r, setAutoRun.getState(), replay);
			}
			if (animator != null) {
				animator.pack();
				left(animator);
				animator.setVisible(true);
			}
		}
	}

	private Frame createCustom(Animator anim, String params, Relation actions,
			Relation controls, boolean replay) {
		CustomAnimator window = null;
		try {
			window = new SceneAnimator();
			File f;
			if (params != null)
				f = new File(currentDirectory, params);
			else
				f = null;
			window.init(anim, f, actions, controls, replay); // give it the
																// Animator
																// interface
			return window;
		} catch (Exception e) {
			outln("** Failed to create instance of Scene Animator" + e);
			return null;
		}
	}

	// ------------------------------------------------------------------------

	private void reachable() {
		clearOutput();
		compileIfChange();
		if (current != null && current.machines.size() > 0) {
			Analyser a = new Analyser(current, outputImpl, null);
			SuperTrace s = new SuperTrace(a, outputImpl);
			current.setErrorTrace(s.getErrorTrace());
		}
	}

	// ------------------------------------------------------------------------

	private void newDrawWindow(boolean disp) {
		if (disp && textIO.indexOfTab("Draw") < 0) {
			// create Text window
			draws = new LTSDrawWindow(current, eman);
			textIO.addTab("Draw", draws);
			swapto(textIO.indexOfTab("Draw"));
		} else if (!disp && textIO.indexOfTab("Draw") > 0) {
			swapto(0);
			textIO.removeTabAt(textIO.indexOfTab("Draw"));
			draws.removeClient();
			draws = null;
		}
	}

	// ------------------------------------------------------------------------

	private void newPrintWindow(boolean disp) {
		if (disp && textIO.indexOfTab("Transitions") < 0) {
			// create Text window
			prints = new PrintWindow(current, eman);
			textIO.addTab("Transitions", prints);
			swapto(textIO.indexOfTab("Transitions"));
		} else if (!disp && textIO.indexOfTab("Transitions") > 0) {
			swapto(0);
			textIO.removeTabAt(textIO.indexOfTab("Transitions"));
			prints.removeClient();
			prints = null;
		}
	}

	// ------------------------------------------------------------------------

	private void newAlphabetWindow(boolean disp) {
		if (disp) {
			// create Alphabet window
			createAlpha_ICreateRequired.create(null);
			alpha_IAlphabetWindowRequired.setup(current, eman);
			textIO.addTab("Alphabet", alpha_IAlphabetWindowRequired.getComponent());
			swapto(textIO.indexOfTab("Alphabet"));
		} else if (!disp) {
			swapto(0);
			alpha_IAlphabetWindowRequired.removeClient();
			textIO.removeTabAt(textIO.indexOfTab("Alphabet"));
			createAlpha_ICreateRequired.destroy(null);
		}
	}

	// ------------------------------------------------------------------------

	private void aboutDialog() {
		LTSASplash d = new LTSASplash(main);
		d.setVisible(true);
	}

	// ------------------------------------------------------------------------

	private void blankit() {
		LTSABlanker d = new LTSABlanker(main);
		d.setVisible(true);
	}

	// ------------------------------------------------------------------------

	private void setSuperTraceOption() {
		try {
			String o = (String) JOptionPane.showInputDialog(main,
					"Enter Hashtable size (Kilobytes):",
					"Supertrace parameters", JOptionPane.PLAIN_MESSAGE, null,
					null, "" + SuperTrace.getHashSize());
			if (o == null)
				return;
			SuperTrace.setHashSize(Integer.parseInt(o));
			o = (String) JOptionPane.showInputDialog(main,
					"Enter bound for search depth size:",
					"Supertrace parameters", JOptionPane.PLAIN_MESSAGE, null,
					null, "" + SuperTrace.getDepthBound());
			if (o == null)
				return;
			SuperTrace.setDepthBound(Integer.parseInt(o));
		} catch (NumberFormatException e) {
		}
	}

	// ------------------------------------------------------------------------

	private boolean parse() {
		String oldChoice = (String) targetChoice.getSelectedItem();
		Hashtable cs = doparse();
		if (cs == null)
			return false;
		targetChoice.removeAllItems();
		if (cs.size() == 0) {
			targetChoice.addItem(DEFAULT);
		} else {
			Enumeration e = cs.keys();
			java.util.List forSort = new ArrayList();
			while (e.hasMoreElements()) {
				forSort.add(e.nextElement());
			}
			Collections.sort(forSort);
			for (Iterator i = forSort.iterator(); i.hasNext();) {
				targetChoice.addItem((String) i.next());
			}
		}
		if (oldChoice != null) {
			if ((!oldChoice.equals(DEFAULT)) && cs.containsKey(oldChoice))
				targetChoice.setSelectedItem(oldChoice);
		}
		current = null;
		// deal with run menu
		check_run.removeAll();
		run_names = MenuDefinition.names();
		run_enabled = MenuDefinition.enabled((String) targetChoice
				.getSelectedItem());
		check_run.add(default_run);
		if (run_names != null) {
			run_items = new JMenuItem[run_names.length];
			for (int i = 0; i < run_names.length; ++i) {
				run_items[i] = new JMenuItem(run_names[i]);
				run_items[i].setEnabled(run_enabled[i]);
				run_items[i].addActionListener(new ExecuteAction(run_names[i]));
				check_run.add(run_items[i]);
			}
		}
		// deal with assert menu
		check_liveness.removeAll();
		assert_names = AssertDefinition.names();
		if (assert_names != null) {
			assert_items = new JMenuItem[assert_names.length];
			for (int i = 0; i < assert_names.length; ++i) {
				assert_items[i] = new JMenuItem(assert_names[i]);
				assert_items[i].addActionListener(new LivenessAction(
						assert_names[i]));
				check_liveness.add(assert_items[i]);
			}
		}
		main.validate();
		return true;
	}

	// ------------------------------------------------------------------------

	private void displayManual(boolean dispman) {
		if (dispman && textIO.indexOfTab("Manual") < 0) {
			// create Manual window
			manual = new JEditorPane();
			manual.setEditable(false);
			manual.addHyperlinkListener(new Hyperactive());
			JScrollPane mm = new JScrollPane(manual,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			textIO.addTab("Manual", mm);
			swapto(textIO.indexOfTab("Manual"));
			URL man = this.getClass().getResource("doc/User-manual.html");
			try {
				manual.setPage(man);
				// outln("URL: "+man);
			} catch (java.io.IOException e) {
				outln("" + e);
			}
		} else if (!dispman && textIO.indexOfTab("Manual") > 0) {
			swapto(0);
			textIO.removeTabAt(textIO.indexOfTab("Manual"));
			manual = null;
		}
	}

	class Hyperactive implements HyperlinkListener {

		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				JEditorPane pane = (JEditorPane) e.getSource();
				try {
					URL u = e.getURL();
					// outln("URL: "+u);
					pane.setPage(u);
				} catch (Throwable t) {
					outln("" + e);
				}
			}
		}
	}

	// ------------------------------------------------------------------------
	public static String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";

	public class IRunRunImpl implements IRun {
		@Override
		public int run(String[] args) {
			args = new String[0];
			try {
				// String lf = UIManager.getSystemLookAndFeelClassName();
				// UIManager.setLookAndFeel(lf);

				// see if we can get the font first
				Properties props = new Properties();
				Font font = new Font("Arial", 12, Font.PLAIN);

				// Change the size if 10 point does not fit your needs
				props.put("logoString", "LTSA");
				props.put("licenseKey", LICENSE_KEY);
				props.put("controlTextFont", fontString(font, 0));
				props.put("labelTextFont", fontString(font, 0));
				props.put("systemTextFont", fontString(font, 0));
				props.put("userTextFont", fontString(font, 0));
				props.put("menuTextFont", fontString(font, 0));
				props.put("windowTitleFont", fontString(font
						.deriveFont(Font.BOLD), 0));
				props.put("subTextFont", fontString(font, 2));
				SmartLookAndFeel.setTheme("Default");
				BaseTheme.setProperties(props);

				UIManager.setLookAndFeel(new SmartLookAndFeel());
				JFrame.setDefaultLookAndFeelDecorated(false);
				JDialog.setDefaultLookAndFeelDecorated(false);

				{
					main = new JFrame();
					SymbolTable.init();
					main.setLayout(new BorderLayout());

					textIO = new JTabbedPane();

					// edit window for specification source
					// input = new JTextArea("",24,80);
					input = new JEditorPane();
					input.setEditorKit(new ColoredEditorKit());
					input.setFont(fixed);
					input.setBackground(Color.white);
					input.getDocument().addUndoableEditListener(undoHandler);
					undo.setLimit(10); // set maximum undo edits
					// input.setLineWrap(true);
					// input.setWrapStyleWord(true);
					input.setBorder(new EmptyBorder(0, 5, 0, 0));
					JScrollPane inp = new JScrollPane(input,
							JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					textIO.addTab("Edit", inp);
					// results window
					output = new JTextArea("", 30, 100);
					output.setEditable(false);
					output.setFont(fixed);
					output.setBackground(Color.white);
					output.setLineWrap(true);
					output.setWrapStyleWord(true);
					output.setBorder(new EmptyBorder(0, 5, 0, 0));
					JScrollPane outp = new JScrollPane(output,
							JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					textIO.addTab("Output", outp);
					textIO.addChangeListener(new TabChange());
					textIO.setRequestFocusEnabled(false);
					main.add(textIO, BorderLayout.CENTER);
					
					// Build the menu bar.
					JMenuBar mb = new JMenuBar();
					main.setJMenuBar(mb);
					// file menu
					file = new JMenu("File");
					mb.add(file);
					file_new = new JMenuItem("New");
					file_new.addActionListener(new NewFileAction());
					file.add(file_new);
					file_open = new JMenuItem("Open...");
					file_open.addActionListener(new OpenFileAction());
					file.add(file_open);
					file_save = new JMenuItem("Save");
					file_save.addActionListener(new SaveFileAction());
					file.add(file_save);
					file_saveAs = new JMenuItem("Save as...");
					file_saveAs.addActionListener(new SaveAsFileAction());
					file.add(file_saveAs);
					file_export = new JMenuItem("Export...");
					file_export.addActionListener(new ExportFileAction());
					file.add(file_export);
					file_example = new JMenu("Examples");
//					new Examples(file_example, this).getExamples();
					file.add(file_example);
					file_exit = new JMenuItem("Quit");
					file_exit.addActionListener(new ExitFileAction());
					file.add(file_exit);
					// edit menu
					edit = new JMenu("Edit");
					mb.add(edit);
					edit_cut = new JMenuItem("Cut");
					edit_cut.addActionListener(new EditCutAction());
					edit.add(edit_cut);
					edit_copy = new JMenuItem("Copy");
					edit_copy.addActionListener(new EditCopyAction());
					edit.add(edit_copy);
					edit_paste = new JMenuItem("Paste");
					edit_paste.addActionListener(new EditPasteAction());
					edit.add(edit_paste);
					edit.addSeparator();
					edit_undo = new JMenuItem("Undo");
					edit_undo.addActionListener(new UndoAction());
					edit.add(edit_undo);
					edit_redo = new JMenuItem("Redo");
					edit_redo.addActionListener(new RedoAction());
					edit.add(edit_redo);
					// check menu
					check = new JMenu("Check");
					mb.add(check);
					check_safe = new JMenuItem("Safety");
					check_safe.addActionListener(new DoAction(DO_safety));
					check.add(check_safe);
					check_progress = new JMenuItem("Progress");
					check_progress.addActionListener(new DoAction(DO_progress));
					check.add(check_progress);
					check_liveness = new JMenu("LTL property");
					if (hasLTL2BuchiJar())
						check.add(check_liveness);
					check_run = new JMenu("Run");
					check.add(check_run);
					default_run = new JMenuItem(DEFAULT);
					default_run.addActionListener(new ExecuteAction(DEFAULT));
					check_run.add(default_run);
					check_reachable = new JMenuItem("Supertrace");
					check_reachable.addActionListener(new DoAction(DO_reachable));
					check.add(check_reachable);
					check_stop = new JMenuItem("Stop");
					check_stop.addActionListener(new StopAction());
					check_stop.setEnabled(false);
					check.add(check_stop);
					// build menu
					build = new JMenu("Build");
					mb.add(build);
					build_parse = new JMenuItem("Parse");
					build_parse.addActionListener(new DoAction(DO_parse));
					build.add(build_parse);
					build_compile = new JMenuItem("Compile");
					build_compile.addActionListener(new DoAction(DO_compile));
					build.add(build_compile);
					build_compose = new JMenuItem("Compose");
					build_compose.addActionListener(new DoAction(DO_doComposition));
					build.add(build_compose);
					build_minimise = new JMenuItem("Minimise");
					build_minimise.addActionListener(new DoAction(DO_minimiseComposition));
					build.add(build_minimise);
					// window menu
					window = new JMenu("Window");
					mb.add(window);
					// only add if we have a creator for the alphabet
					if (createAlpha_ICreateRequired != null)
					{
						window_alpha = new JCheckBoxMenuItem("Alphabet");
						window_alpha.setSelected(false);
						window_alpha.addActionListener(new WinAlphabetAction());
						window.add(window_alpha);
					}
					window_print = new JCheckBoxMenuItem("Transitions");
					window_print.setSelected(false);
					window_print.addActionListener(new WinPrintAction());
					window.add(window_print);
					window_draw = new JCheckBoxMenuItem("Draw");
					window_draw.setSelected(true);
					window_draw.addActionListener(new WinDrawAction());
					window.add(window_draw);
					// help menu
					help = new JMenu("Help");
					mb.add(help);
					help_about = new JMenuItem("About");
					help_about.addActionListener(new HelpAboutAction());
					help.add(help_about);
					help_manual = new JCheckBoxMenuItem("Manual");
					help_manual.setSelected(false);
					help_manual.addActionListener(new HelpManualAction());
					help.add(help_manual);
					// option menu
					OptionAction opt = new OptionAction();
					option = new JMenu("Options");
					mb.add(option);
					setWarnings = new JCheckBoxMenuItem("Display warning messages");
					setWarnings.addActionListener(opt);
					option.add(setWarnings);
					setWarnings.setSelected(true);
					setWarningsAreErrors = new JCheckBoxMenuItem("Treat warnings as errors");
					setWarningsAreErrors.addActionListener(opt);
					option.add(setWarningsAreErrors);
					setWarningsAreErrors.setSelected(false);
					setFair = new JCheckBoxMenuItem("Fair Choice for LTL check");
					setFair.addActionListener(opt);
					option.add(setFair);
					setFair.setSelected(true);
					setAlphaLTL = new JCheckBoxMenuItem("Alphabet sensitive LTL");
					setAlphaLTL.addActionListener(opt);
					// option.add(setAlphaLTL);
					setAlphaLTL.setSelected(false);
					setSynchLTL = new JCheckBoxMenuItem("Timed LTL");
					setSynchLTL.addActionListener(opt);
					// option.add(setSynchLTL);
					setSynchLTL.setSelected(false);
					setPartialOrder = new JCheckBoxMenuItem("Partial Order Reduction");
					setPartialOrder.addActionListener(opt);
					option.add(setPartialOrder);
					setPartialOrder.setSelected(false);
					setObsEquiv = new JCheckBoxMenuItem("Preserve OE for POR composition");
					setObsEquiv.addActionListener(opt);
					option.add(setObsEquiv);
					setObsEquiv.setSelected(true);
					setReduction = new JCheckBoxMenuItem("Enable Tau Reduction");
					setReduction.addActionListener(opt);
					option.add(setReduction);
					setReduction.setSelected(true);
					supertrace_options = new JMenuItem("Set Supertrace parameters");
					supertrace_options.addActionListener(new SuperTraceOptionListener());
					option.add(supertrace_options);
					option.addSeparator();
					setBigFont = new JCheckBoxMenuItem("Use big font");
					setBigFont.addActionListener(opt);
					option.add(setBigFont);
					setBigFont.setSelected(false);
					setDisplayName = new JCheckBoxMenuItem("Display name when drawing LTS");
					setDisplayName.addActionListener(opt);
					option.add(setDisplayName);
					setDisplayName.setSelected(true);
					setNewLabelFormat = new JCheckBoxMenuItem(
							"Use V2.0 label format when drawing LTS");
					setNewLabelFormat.addActionListener(opt);
					option.add(setNewLabelFormat);
					setNewLabelFormat.setSelected(true);
					setMultipleLTS = new JCheckBoxMenuItem("Multiple LTS in Draw window");
					setMultipleLTS.addActionListener(opt);
					option.add(setMultipleLTS);
					setMultipleLTS.setSelected(false);
					option.addSeparator();
					setAutoRun = new JCheckBoxMenuItem("Auto run actions in Animator");
					setAutoRun.addActionListener(opt);
					option.add(setAutoRun);
					setAutoRun.setSelected(false);
					// toolbar
					tools = new JToolBar();
					tools.setFloatable(false);
					tools.add(newFileTool = createTool("icon/new.gif", "New file",
							new NewFileAction()));
					tools.add(openFileTool = createTool("icon/open.gif", "Open file",
							new OpenFileAction()));
					tools.add(saveFileTool = createTool("icon/save.gif", "Save File",
							new SaveFileAction()));
					tools.addSeparator();
					tools.add(cutTool = createTool("icon/cut.gif", "Cut",
							new EditCutAction()));
					tools.add(createTool("icon/copy.gif", "Copy", new EditCopyAction()));
					tools.add(pasteTool = createTool("icon/paste.gif", "Paste",
							new EditPasteAction()));
					tools.add(undoTool = createTool("icon/undo.gif", "Undo",
							new UndoAction()));
					tools.add(redoTool = createTool("icon/redo.gif", "Redo",
							new RedoAction()));
					tools.addSeparator();
					tools.add(parseTool = createTool("icon/parse.gif", "Parse",
							new DoAction(DO_parse)));
					tools.add(compileTool = createTool("icon/compile.gif", "Compile",
							new DoAction(DO_compile)));
					tools.add(composeTool = createTool("icon/compose.gif", "Compose",
							new DoAction(DO_doComposition)));
					tools.add(minimizeTool = createTool("icon/minimize.gif", "Minimize",
							new DoAction(DO_minimiseComposition)));
					// status field used to name the composition we are wrking on
					targetChoice = new JComboBox();
					targetChoice.setEditable(false);
					targetChoice.addItem(DEFAULT);
					targetChoice.setToolTipText("Target Composition");
					targetChoice.setRequestFocusEnabled(false);
					targetChoice.addActionListener(new TargetAction());
					tools.add(targetChoice);
					tools.addSeparator();
					tools.add(safetyTool = createTool("icon/safety.gif", "Check safety",
							new DoAction(DO_safety)));
					tools.add(progressTool = createTool("icon/progress.gif",
							"Check Progress", new DoAction(DO_progress)));
					tools.add(stopTool = createTool("icon/stop.gif", "Stop",
							new StopAction()));
					stopTool.setEnabled(false);
					tools.addSeparator();
					tools.add(createTool("icon/alphabet.gif", "Run DEFAULT Animation",
							new ExecuteAction(DEFAULT)));
					tools.add(createTool("icon/blanker.gif", "Blank Screen",
							new BlankAction()));
					tools.addSeparator();
					main.add(tools, BorderLayout.NORTH);
					// enable menus
					menuEnable(true);
					file_save.setEnabled(isApplet == null);
					file_saveAs.setEnabled(isApplet == null);
					file_export.setEnabled(isApplet == null);
					saveFileTool.setEnabled(isApplet == null);
					updateDoState();
					// switch to edit tab
					LTSCanvas.displayName = setDisplayName.isSelected();
					LTSCanvas.newLabelFormat = setNewLabelFormat.isSelected();
					LTSDrawWindow.singleMode = !setMultipleLTS.isSelected();
					newDrawWindow(window_draw.isSelected());
					// switch to edit tab
					swapto(0);
					// close window action
					main.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					main.addWindowListener(new CloseWindow());
				}

				
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			main.setTitle(title);
			main.pack();
			centre(main);
			main.setVisible(true);
			if (args.length > 0) {
				SwingUtilities.invokeLater(new ScheduleOpenFile(HPWindow.this, args[0]));
			} else {
				currentDirectory = System.getProperty("user.dir");
			}
			return 0;
		}

	}

	private static Object fontString(Font font, int subtractSize) {
		return font.getName() + " " + (font.isBold() ? "bold " : "")
				+ (font.isItalic() ? "italic " : "")
				+ (font.getSize() - subtractSize);
	}

	static class ScheduleOpenFile implements Runnable {
		HPWindow window;
		String arg;

		ScheduleOpenFile(HPWindow window, String arg) {
			this.window = window;
			this.arg = arg;
		}

		public void run() {
			window.doOpenFile("", arg, false);
		}
	}

	private boolean hasLTL2BuchiJar() {
		try {
			new gov.nasa.ltl.graph.Graph();
			return true;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}

}
