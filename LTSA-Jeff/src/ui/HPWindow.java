package ui;

// This is an experimental version with progress & LTL property check

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import lts.*;
import lts.ltl.*;
import actions.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.jtattoo.plaf.*;
import com.jtattoo.plaf.smart.*;

import editor.*;

public class HPWindow {
// start generated code
// attributes
	private Attribute<java.lang.String> title;
	private Attribute<java.lang.String> currentDirectory;
	private Attribute<lts.CompositeState> top;
// required ports
	private java.util.List<ui.IWindow> window = new java.util.ArrayList<ui.IWindow>();
	private lts.IEventManager events;
	private java.util.List<javax.swing.JCheckBoxMenuItem> options = new java.util.ArrayList<javax.swing.JCheckBoxMenuItem>();
	private javax.swing.JCheckBoxMenuItem bigFont;
	private java.util.List<actions.IAction> actions = new java.util.ArrayList<actions.IAction>();
	private actions.INameList target;
	private actions.IAction parser;
	private java.util.List<actions.IAction> checks = new java.util.ArrayList<actions.IAction>();
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
	private java.util.List<ActionListenerOptionsImpl>  options_ActionListenerProvided = new java.util.ArrayList<ActionListenerOptionsImpl>();
	private ActionListenerBigFontImpl bigFont_ActionListenerProvided = new ActionListenerBigFontImpl();
	private LTSOutputInoutImpl inout_LTSOutputProvided = new LTSOutputInoutImpl();
	private LTSInputInoutImpl inout_LTSInputProvided = new LTSInputInoutImpl();
	private INameListenerTargetImpl target_INameListenerProvided = new INameListenerTargetImpl();
	private ICoordinatorCoordinatorImpl coordinator_ICoordinatorProvided = new ICoordinatorCoordinatorImpl();
// setters and getters
	public Attribute<java.lang.String> getTitle() { return title; }
	public void setTitle(Attribute<java.lang.String> title) { this.title = title;}
	public void setRawTitle(java.lang.String title) { this.title.set(title);}
	public Attribute<java.lang.String> getCurrentDirectory() { return currentDirectory; }
	public void setCurrentDirectory(Attribute<java.lang.String> currentDirectory) { this.currentDirectory = currentDirectory;}
	public void setRawCurrentDirectory(java.lang.String currentDirectory) { this.currentDirectory.set(currentDirectory);}
	public Attribute<lts.CompositeState> getTop() { return top; }
	public void setTop(Attribute<lts.CompositeState> top) { this.top = top;}
	public void setRawTop(lts.CompositeState top) { this.top.set(top);}
	public void setWindow_IWindow(ui.IWindow window, int index) { PortHelper.fill(this.window, window, index); }
	public void removeWindow_IWindow(ui.IWindow window) { PortHelper.remove(this.window, window); }
	public void setEvents_IEventManager(lts.IEventManager events) { this.events = events; }
	public void setOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem options, int index) { PortHelper.fill(this.options, options, index); }
	public void removeOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem options) { PortHelper.remove(this.options, options); }
	public void setBigFont_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem bigFont) { this.bigFont = bigFont; }
	public void setActions_IAction(actions.IAction actions, int index) { PortHelper.fill(this.actions, actions, index); }
	public void removeActions_IAction(actions.IAction actions) { PortHelper.remove(this.actions, actions); }
	public void setTarget_INameList(actions.INameList target) { this.target = target; }
	public void setParser_IAction(actions.IAction parser) { this.parser = parser; }
	public void setChecks_IAction(actions.IAction checks, int index) { PortHelper.fill(this.checks, checks, index); }
	public void removeChecks_IAction(actions.IAction checks) { PortHelper.remove(this.checks, checks); }
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
	public java.awt.event.ActionListener getOptions_ActionListener(Class<?> required, int index) { return PortHelper.fill(options_ActionListenerProvided, new ActionListenerOptionsImpl(), index); }
	public java.awt.event.ActionListener getBigFont_ActionListener(Class<?> required) { return bigFont_ActionListenerProvided; }
	public lts.LTSOutput getInout_LTSOutput(Class<?> required) { return inout_LTSOutputProvided; }
	public lts.LTSInput getInout_LTSInput(Class<?> required) { return inout_LTSInputProvided; }
	public actions.INameListener getTarget_INameListener(Class<?> required) { return target_INameListenerProvided; }
	public actions.ICoordinator getCoordinator_ICoordinator(Class<?> required) { return coordinator_ICoordinatorProvided; }
// end generated code

	private static final String VERSION = " j1.2 v14-10-99, amimation support";
	private static final String DEFAULT = "DEFAULT";
	private int windowIndex = -1;
	JTextArea output;
	JEditorPane input;
	JEditorPane manual;
	JTabbedPane textIO;
	JToolBar tools;
	JFrame main;
	JComboBox targetChoice;
	JPanel p;
	String run_menu = DEFAULT;
	String asserted = null;
	// Listener for the edits on the current document.
	protected UndoableEditListener undoHandler = new UndoHandler();
	// UndoManager that we add edits to.
	protected UndoManager undo = new UndoManager();

	JMenu file, edit, check, build, windowm, help, option;

	JMenuItem file_new, file_open, file_save, file_saveAs, file_export,
			file_exit, edit_cut, edit_copy, edit_paste, edit_undo, edit_redo,
			check_stop,
			help_about, supertrace_options;

	private List<JMenuItem> backbone_menu_items = new ArrayList<JMenuItem>(); 
	private List<JButton> backbone_tool_items = new ArrayList<JButton>(); 

	JMenu file_example;

	JMenuItem[] run_items, assert_items;

	String[] run_names, assert_names;

	boolean[] run_enabled;

	JCheckBoxMenuItem setWarnings;

	// ------------------------------------------------------------------------

	JCheckBoxMenuItem setWarningsAreErrors;

	JCheckBoxMenuItem setAlphaLTL;
	JCheckBoxMenuItem setSynchLTL;
	JCheckBoxMenuItem setPartialOrder;
	JCheckBoxMenuItem setObsEquiv;
	JCheckBoxMenuItem setReduction;

	JCheckBoxMenuItem help_manual;

	// ------------------------------------------------------------------------
	// File handling
	// -----------------------------------------------------------------------

	// tool bar buttons - that need to be enabled and disabled
	JButton stopTool, cutTool, pasteTool,
			newFileTool, openFileTool, saveFileTool,
			undoTool, redoTool;
	// used to implement muCSPInput
	int fPos = -1;
	String fSrc = "\n";
	Font fixed = new Font("Monospaced", Font.PLAIN, 12);

	Font big = new Font("Monospaced", Font.BOLD, 20);

	// Font title = new Font("SansSerif",Font.PLAIN,12);
	private RunnableImpl runnableImpl = new RunnableImpl();

	// ------------------------------------------------------------------------

	private AppletButton isApplet = null;

	// -------------------------------------------------------------------------

	// ----Event
	// Handling-----------------------------------------------------------

	private final static String fileType = "*.lts";
	
	private class INameListenerTargetImpl implements INameListener
	{
		@Override
		public void haveNewNames()
		{
			String oldChoice = (String) targetChoice.getSelectedItem();
			Map<String, Boolean> names = target.getItems();
			targetChoice.removeAllItems();
			if (names.size() == 0)
			{
				targetChoice.addItem(DEFAULT);
			}
			else
			{
				List<String> forSort = new ArrayList<String>(names.keySet());
				Collections.sort(forSort);
				for (String n : forSort)
					targetChoice.addItem(n);
			}
			if (oldChoice != null) {
				if ((!oldChoice.equals(DEFAULT)) && names.containsKey(oldChoice))
					targetChoice.setSelectedItem(oldChoice);
			}
		}		
	}
	
	private class ICoordinatorCoordinatorImpl implements ICoordinator
	{
		@Override
		public void displayError(LTSException ex)
		{
			displayTheError(ex);
		}

		@Override
		public void primeText()
		{
			fPos = -1;
			fSrc = input.getText();
		}

		@Override
		public void clearAndShowOutput()
		{
			clearOutput();
			showOutput();
		}

		@Override
		public boolean needRecompile()
		{
			String tmp = input.getText();
			return (top.get() == null || !tmp.equals(fSrc) || !top.get().name.equals(targetChoice.getSelectedItem()));
		}

		@Override
		public Point getLocationOnScreen()
		{
			return main.getLocationOnScreen();
		}		
	}
	
	public void clearOutput()
	{
		SwingUtilities.invokeLater(new OutputClear());
	}

	private class ActionListenerBigFontImpl implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AnimWindow.fontFlag = bigFont.isSelected();
			doFont();
		}
	}

	private class ActionListenerOptionsImpl implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// not used
		}
	}

	class BlankAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			blankit();
		}
	}

	class CloseWindow extends WindowAdapter {
		public void windowActivated(WindowEvent e)
		{
//xxx			if (animator != null)
//				animator.toFront();
		}

		public void windowClosing(WindowEvent e)
		{
			quitAll();
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
			else if (windowIndex >= 0)
				window.get(windowIndex).copy();
		}
	}

	class EditCutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			input.cut();
		}
	}

	class EditPasteAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			input.paste();
		}
	}

	class ExitFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			quitAll();
		}
	}

	class ExportFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit"))
				exportFile();
			else if (windowIndex >= 0)
				window.get(windowIndex).saveFile(
						currentDirectory.get());
		}
	}

	class HelpAboutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			aboutDialog();
		}
	}

	class HelpManualAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			displayManual(help_manual.isSelected());
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

	private class LTSInputInoutImpl implements LTSInput {
		// Implementation of the DarwinEnvironment Interface

		public char backChar() {
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

		public char nextChar() {
			fPos = fPos + 1;
			if (fPos < fSrc.length()) {
				return fSrc.charAt(fPos);
			} else {
				// fPos = fPos - 1;
				return '\u0000';
			}
		}
	}

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
					// new Examples(file_example, this).getExamples();
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
					check = new JMenu("Check!");
					mb.add(check);
					
					for (final IAction ch : checks)
					{
						JMenuItem checkItem = new JMenuItem(ch.getName());
						checkItem.setIcon(getIcon(ch.getIcon()));
						checkItem.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								do_action(ch);
							}
						});
						check.add(checkItem);
						backbone_menu_items.add(checkItem);
					}
					
					check_stop = new JMenuItem("Stop");
					check_stop.addActionListener(new StopAction());
					check_stop.setEnabled(false);
					check.add(check_stop);
					// build menu
					build = new JMenu("Build!");
					mb.add(build);
					
					// add in the new actions
					for (final IAction action : actions)
					{
						JMenuItem actItem = new JMenuItem(action.getName());
						actItem.addActionListener(new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								do_action(action);
							}
						});
						actItem.setIcon(getIcon(action.getIcon()));
						build.add(actItem);
					}
					
					// window menu
					windowm = new JMenu("Window!");
					mb.add(windowm);
					int lp = 0;
					for (IWindow w : window) {
						if (w != null) {
							final int count = lp;
							JCheckBoxMenuItem item = new JCheckBoxMenuItem(w.getName());
							backbone_menu_items.add(item);
							item.setSelected(false);
							item.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e)
								{
									IWindow w = window.get(count);
									if (!w.isActive()) {
										// create Alphabet window
										w.activate(top.get());
										textIO.addTab(w.getName(), w.getComponent());
										swapto(textIO.getTabCount() - 1);
									}
									else
									{
										swapto(0);
										textIO.removeTabAt(textIO.indexOfTab(w.getName()));
										window.get(count).deactivate();
									}
								}
							});
							windowm.add(item);
						}
						lp++;
					}
					// make sure we know which is the current index
					textIO.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							String name = textIO.getTitleAt(textIO
									.getSelectedIndex());
							int lp = 0;
							windowIndex = -1;
							for (IWindow w : window) {
								if (w != null && w.getName().equals(name))
									windowIndex = lp;
								lp++;
							}
						}
					});

					// option menu
					OptionAction opt = new OptionAction();
					option = new JMenu("Options!");
					mb.add(option);
					setWarnings = new JCheckBoxMenuItem(
							"Display warning messages");
					setWarnings.addActionListener(opt);
					option.add(setWarnings);
					setWarnings.setSelected(true);
					setWarningsAreErrors = new JCheckBoxMenuItem(
							"Treat warnings as errors");
					setWarningsAreErrors.addActionListener(opt);
					option.add(setWarningsAreErrors);
					setWarningsAreErrors.setSelected(false);
					setAlphaLTL = new JCheckBoxMenuItem(
							"Alphabet sensitive LTL");
					setAlphaLTL.addActionListener(opt);
					// option.add(setAlphaLTL);
					setAlphaLTL.setSelected(false);
					setSynchLTL = new JCheckBoxMenuItem("Timed LTL");
					setSynchLTL.addActionListener(opt);
					// option.add(setSynchLTL);
					setSynchLTL.setSelected(false);
					setPartialOrder = new JCheckBoxMenuItem(
							"Partial Order Reduction");
					setPartialOrder.addActionListener(opt);
					option.add(setPartialOrder);
					setPartialOrder.setSelected(false);
					setObsEquiv = new JCheckBoxMenuItem(
							"Preserve OE for POR composition");
					setObsEquiv.addActionListener(opt);
					option.add(setObsEquiv);
					setObsEquiv.setSelected(true);
					setReduction = new JCheckBoxMenuItem("Enable Tau Reduction");
					setReduction.addActionListener(opt);
					option.add(setReduction);
					setReduction.setSelected(true);
					supertrace_options = new JMenuItem(
							"Set Supertrace parameters");
					supertrace_options
							.addActionListener(new SuperTraceOptionListener());
					option.add(supertrace_options);
					option.addSeparator();

					// add extra options
					option.add(bigFont);
					for (JCheckBoxMenuItem item : options)
						option.add(item);

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

					// toolbar
					tools = new JToolBar();
					tools.setFloatable(false);
					tools.add(newFileTool = createTool("icon/new.gif",
							"New file", new NewFileAction()));
					tools.add(openFileTool = createTool("icon/open.gif",
							"Open file", new OpenFileAction()));
					tools.add(saveFileTool = createTool("icon/save.gif",
							"Save File", new SaveFileAction()));
					tools.addSeparator();
					tools.add(cutTool = createTool("icon/cut.gif", "Cut",
							new EditCutAction()));
					tools.add(createTool("icon/copy.gif", "Copy",
							new EditCopyAction()));
					tools.add(pasteTool = createTool("icon/paste.gif", "Paste",
							new EditPasteAction()));
					tools.add(undoTool = createTool("icon/undo.gif", "Undo",
							new UndoAction()));
					tools.add(redoTool = createTool("icon/redo.gif", "Redo",
							new RedoAction()));
					tools.addSeparator();
					
					// add our actions
					for (final IAction action : actions)
					{
						if (action.getIcon() != null)
						{
							JButton actItem = new JButton("!", getIcon(action.getIcon()));
							actItem.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent e)
								{
									do_action(action);
								}
							});
							tools.add(actItem);
							actItem.setToolTipText(action.getName());
							backbone_tool_items.add(actItem);
						}
					}

					
					// status field used to name the composition we are wrking
					// on
					targetChoice = new JComboBox();
					targetChoice.setEditable(false);
					targetChoice.addItem(DEFAULT);
					targetChoice.setToolTipText("Target Composition");
					targetChoice.setRequestFocusEnabled(false);
					targetChoice.addActionListener(new TargetAction());
					tools.add(targetChoice);
					tools.addSeparator();
					for (final IAction ch : checks)
					{
						if (ch.getIcon() != null)
						{
							JButton actItem = new JButton("!", getIcon(ch.getIcon()));
							actItem.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent e)
								{
									do_action(ch);
								}
							});
							tools.add(actItem);
							actItem.setToolTipText(ch.getName());
							backbone_tool_items.add(actItem);
						}
					}
					tools.add(stopTool = createTool("icon/stop.gif", "Stop",
							new StopAction()));
					stopTool.setEnabled(false);
					tools.addSeparator();
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
					swapto(0);
					// close window action
					main
							.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					main.addWindowListener(new CloseWindow());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			main.setTitle(title.get());
			SwingUtilities.updateComponentTreeUI(main);
			main.pack();
			centre(main);
			main.setVisible(true);
			if (args.length > 0) {
				SwingUtilities.invokeLater(new ScheduleOpenFile(HPWindow.this,
						args[0]));
			} else {
				currentDirectory.set(System.getProperty("user.dir"));
			}
			return 0;
		}

	}

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

	class OptionAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == setWarnings)
				Diagnostics.warningFlag = setWarnings.isSelected();
			else if (source == setWarningsAreErrors)
				Diagnostics.warningsAreErrors = setWarningsAreErrors
						.isSelected();
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
		}
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

	private class LTSOutputInoutImpl implements LTSOutput {
		public void clearOutput() {
			SwingUtilities.invokeLater(new OutputClear());
		}

		public void out(String str) {
			SwingUtilities.invokeLater(new OutputAppend(str));
		}

		public void outln(String str) {
			SwingUtilities.invokeLater(new OutputAppend(str + "\n"));
		}

	}

	class OutputShow implements Runnable {
		public void run() {
			swapto(1);
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

	class RequestFocus implements Runnable {
		public void run() {
			input.requestFocusInWindow();
		}
	}

	private class RunnableImpl implements Runnable
	{
		public void run()
		{
			try
			{
				theAction.doAction();
			}
			catch (final Throwable e)
			{
				showOutput();
				outln("**** Runtime Exception: " + e);
				e.printStackTrace();
				top.set(null);
			}
		
		menuEnable(true);
			check_stop.setEnabled(false);
			stopTool.setEnabled(false);
		}
	}

	class SaveAsFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit"))
				saveAsFile();
		}
	}

	class SaveFileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pp = textIO.getTitleAt(textIO.getSelectedIndex());
			if (pp.equals("Edit") || pp.equals("Output"))
				saveFile();
			else if (windowIndex >= 0)
				window.get(windowIndex).saveFile(
						currentDirectory.get());
		}
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

	class StopAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
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

	class SuperTraceOptionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSuperTraceOption();
		}
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

	class TargetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String choice = (String) targetChoice.getSelectedItem();
			if (choice == null)
				return;
			target.setSelectedItem(choice);
			run_enabled = MenuDefinition.enabled(choice);
			if (run_items != null && run_enabled != null) {
				if (run_items.length == run_enabled.length)
					for (int i = 0; i < run_items.length; ++i)
						run_items[i].setEnabled(run_enabled[i]);
			}
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

	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			updateDoState();
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

	private IAction theAction;

	private Thread executer;

	private String openFile = fileType;

	private String savedText = "";

	// ------------------------------------------------------------------------
	private int tabindex = 0;

	// ------------------------------------------------------------------------
	public static String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";

	private static Object fontString(Font font, int subtractSize) {
		return font.getName() + " " + (font.isBold() ? "bold " : "")
				+ (font.isItalic() ? "italic " : "")
				+ (font.getSize() - subtractSize);
	}

	public HPWindow() {
	}

	private void aboutDialog() {
		LTSASplash d = new LTSASplash(main);
		d.setVisible(true);
	}

	private void blankit() {
		LTSABlanker d = new LTSABlanker(main);
		d.setVisible(true);
	}

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

	// --------------------------------------------------------------------
	// undo editor stuff

	private ImageIcon getIcon(String resource)
	{
		if (resource == null)
			return null;
		return new ImageIcon(ClassLoader.getSystemResource(resource));
	}
	
	// -----------------------------------------------------------------------
	protected JButton createTool(String icon, String tip, ActionListener act) {
		JButton b = new JButton(new ImageIcon(ClassLoader.getSystemResource(icon))) {
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

	private void displayTheError(LTSException x) {
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

	private void do_action(IAction action) {
		menuEnable(false);
		check_stop.setEnabled(true);
		stopTool.setEnabled(true);
		theAction = action;
		executer = new Thread(runnableImpl);
		executer.setPriority(Thread.NORM_PRIORITY - 1);
		executer.start();
	}

	// ------------------------------------------------------------------------
	private void doFont() {
		if (bigFont.getState()) {
			input.setFont(big);
			output.setFont(big);
		} else {
			input.setFont(fixed);
			output.setFont(fixed);
		}
		main.pack();
		main.setVisible(true);
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
						do_action(parser);
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

	private void exportFile() {
		String message = "Export as Aldebaran format (.aut) to:";
		FileDialog fd = new FileDialog(main, message, FileDialog.SAVE);
		if (top.get() == null || top.get().composition == null) {
			JOptionPane.showMessageDialog(main,
					"No target composition to export");
			return;
		}
		String fname = top.get().composition.name;
		fd.setFile(fname + ".aut");
		fd.setDirectory(currentDirectory.get());
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
				top.get().composition.printAUT(myOutput);
				myOutput.close();
				fout.close();
				outln("Exported to: " + fd.getDirectory() + file);
			} catch (IOException e) {
				outln("Error exporting file: " + e);
			}
	}

	// ------------------------------------------------------------------------
	private void invalidateState() {
		top.set(null);
		targetChoice.removeAllItems();
		targetChoice.addItem(DEFAULT);
		run_items = null;
		assert_items = null;
		run_names = null;
		main.validate();
		if (events != null)
			events.post(new LTSEvent(LTSEvent.INVALID, null));
	}

	// ------------------------------------------------------------------------

	// ----------------------------------------------------------------------
	void menuEnable(boolean flag) {
		boolean application = (isApplet == null);
		file_new.setEnabled(flag && tabindex == 0);
		file_example.setEnabled(flag && tabindex == 0);
		file_open.setEnabled(application && flag && tabindex == 0);
		file_exit.setEnabled(flag);
		
		for (JMenuItem item : backbone_menu_items)
			item.setEnabled(flag);
		for (JButton item : backbone_tool_items)
			item.setEnabled(flag);
	}

	// ------------------------------------------------------------------------

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

	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	private void openAFile() {
		if (checkSave()) {
			invalidateState();
			clearOutput();
			FileDialog fd = new FileDialog(main, "Select source file:");
			if (currentDirectory != null)
				fd.setDirectory(currentDirectory.get());
			fd.setFile(fileType);
			fd.setVisible(true);
			currentDirectory.set(fd.getDirectory());
			doOpenFile(currentDirectory.get(), fd.getFile(), false);
		}
		main.repaint(); // hack to solve display problem
	}

	public void out(String str) {
		SwingUtilities.invokeLater(new OutputAppend(str));
	}

	public void outln(String str) {
		SwingUtilities.invokeLater(new OutputAppend(str + "\n"));
	}


	// ------------------------------------------------------------------------

	private void postState(CompositeState m) {
		if (events != null)
			events.post(new LTSEvent(LTSEvent.INVALID, m));
	}

	// ------------------------------------------------------------------------

	private void quitAll() {
		if (isApplet != null) {
			main.dispose();
			// isApplet.ended();
		} else {
			if (checkSave())
				System.exit(0);
		}
	}

	// ------------------------------------------------------------------------

	private void saveAsFile() {
		FileDialog fd = new FileDialog(main, "Save file in:", FileDialog.SAVE);
		if (currentDirectory != null)
			fd.setDirectory(currentDirectory.get());
		fd.setFile(openFile);
		fd.setVisible(true);
		String tmp = fd.getFile();
		if (tmp != null) { // if not cancelled
			currentDirectory.set(fd.getDirectory());
			openFile = tmp;
			main.setTitle("LTSA - " + openFile);
			saveFile();
		}
	}

	// ------------------------------------------------------------------------

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

	// ------------------------------------------------------------------------
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

	public void showOutput() {
		SwingUtilities.invokeLater(new OutputShow());
	}

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

	protected void updateDoState() {
		edit_undo.setEnabled(undo.canUndo());
		undoTool.setEnabled(undo.canUndo());
		edit_redo.setEnabled(undo.canRedo());
		redoTool.setEnabled(undo.canRedo());
	}
}
