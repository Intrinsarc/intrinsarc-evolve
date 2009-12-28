package alpha;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import lts.*;
import ui.*;

public class AlphabetWindow
{
// start generated code
// attributes
// required ports
	private javax.swing.JCheckBoxMenuItem bigFont;
// provided ports
	private IWindowWindowImpl window_IWindowProvided = new IWindowWindowImpl();
	private EventClientEventsImpl events_EventClientProvided = new EventClientEventsImpl();
	private ActionListenerBigFontImpl bigFont_ActionListenerProvided = new ActionListenerBigFontImpl();
// setters and getters
	public void setBigFont_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem bigFont) { this.bigFont = bigFont; }
	public ui.IWindow getWindow_IWindow(Class<?> required) { return window_IWindowProvided; }
	public lts.EventClient getEvents_EventClient(Class<?> required) { return events_EventClientProvided; }
	public java.awt.event.ActionListener getBigFont_ActionListener(Class<?> required) { return bigFont_ActionListenerProvided; }
// end generated code
	private LTSOutput out = new LTSOutputImpl();
	private JSplitPane split;
	JTextArea output;
	JList list;
	JScrollPane left, right;
	int Nmach;
	int selectedMachine = 0;
	Alphabet current = null;
	int expandLevel = 0;
	CompactState[] sm; // an array of machines

	Font f1 = new Font("Monospaced", Font.PLAIN, 12);

	// ------------------------------------------------------------------------

	Font f2 = new Font("Monospaced", Font.BOLD, 20);

	Font f3 = new Font("SansSerif", Font.PLAIN, 12);

	// ------------------------------------------------------------------------

	Font f4 = new Font("SansSerif", Font.BOLD, 18);

	public AlphabetWindow()
	{
	}
	
	private class ActionListenerBigFontImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setBigFont();
		}		
	}
	
	class ExpandLeastAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current == null)
				return;
			expandLevel = 0;
			out.clearOutput();
			current.print(out, expandLevel);
		}
	}

	class ExpandLessAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current == null)
				return;
			if (expandLevel > 0)
				--expandLevel;
			out.clearOutput();
			current.print(out, expandLevel);
		}
	}

	class ExpandMoreAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current == null)
				return;
			if (expandLevel < current.maxLevel)
				++expandLevel;
			out.clearOutput();
			current.print(out, expandLevel);
		}
	}

	class ExpandMostAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (current == null)
				return;
			expandLevel = current.maxLevel;
			out.clearOutput();
			current.print(out, expandLevel);
		}
	}
	
	private class LTSOutputImpl implements LTSOutput
	{
		public void clearOutput() {
			output.setText("");
		}

		public void out(String str) {
			output.append(str);
		}

		public void outln(String str) {
			output.append(str + "\n");
		}
	}
	
	private class EventClientEventsImpl implements EventClient
	{
		@Override
		public void ltsAction(LTSEvent e)
		{
			switch (e.kind) {
			case LTSEvent.NEWSTATE:
				break;
			case LTSEvent.INVALID:
				new_machines((CompositeState) e.info);
				break;
			case LTSEvent.KILL:
				// this.dispose();
				break;
			default:
				;
			}
		}
	}
	
	private class IWindowWindowImpl implements IWindow {
		public void copy() {
			output.copy();
		}

		public JSplitPane getComponent() {
			return split;
		}

		// --------------------------------------------------------------------
		public void deactivate()
		{
		}

		public void saveFile(String fileName) {

			FileDialog fd = new FileDialog((Frame) split.getTopLevelAncestor(),
					"Save text in:", FileDialog.SAVE);
			if (Nmach > 0) {
				String fname = sm[selectedMachine].name;
				int colon = fname.indexOf(':', 0);
				if (colon > 0)
					fname = fname.substring(0, colon);
				fd.setFile(fname + ".txt");
			}
			fd.show();
			String file = fd.getFile();
			if (file != null)
				try {
					int i = file.indexOf('.', 0);
					file = file.substring(0, i) + "." + "txt";
					FileOutputStream fout = new FileOutputStream(fd
							.getDirectory()
							+ file);
					// now convert the FileOutputStream into a PrintStream
					PrintStream myOutput = new PrintStream(fout);
					String text = output.getText();
					myOutput.print(text);
					myOutput.close();
					fout.close();
					// outln("Saved in: "+ fd.getDirectory()+file);
				} catch (IOException e) {
					out.outln("Error saving file: " + e);
				}
		}

		// ------------------------------------------------------------------------

		public void activate(CompositeState cs) {
			// scrollable output pane
			output = new JTextArea(23, 50);
			output.setEditable(false);
			right = new JScrollPane(output,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			output.setBackground(Color.white);
			output.setBorder(new EmptyBorder(0, 5, 0, 0));
			// scrollable list pane
			list = new JList();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.addListSelectionListener(new PrintAction());
			left = new JScrollPane(list,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JPanel fortools = new JPanel(new BorderLayout());
			fortools.add("Center", right);
			// tool bar
			JToolBar tools = new JToolBar();
			tools.setOrientation(JToolBar.VERTICAL);
			fortools.add("West", tools);
			tools.add(createTool("icon/expanded.gif", "Expand Most",
					new ExpandMostAction()));
			tools.add(createTool("icon/expand.gif", "Expand",
					new ExpandMoreAction()));
			tools.add(createTool("icon/collapse.gif", "Collapse",
					new ExpandLessAction()));
			tools.add(createTool("icon/collapsed.gif", "Most Concise",
					new ExpandLeastAction()));

			new_machines(cs);
			split = new JSplitPane();
			split.setLeftComponent(left);
			split.setRightComponent(fortools);
			split.setDividerLocation(150);
			setBigFont();
			split.validate();
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public boolean isActive() {
			return true;
		}
	}
	
	class PrintAction implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			int machine = list.getSelectedIndex();
			if (machine < 0 || machine >= Nmach)
				return;
			selectedMachine = machine;
			out.clearOutput();
			current = new Alphabet(sm[machine]);
			if (expandLevel > current.maxLevel)
				expandLevel = current.maxLevel;
			current.print(out, expandLevel);
		}
	}
	protected JButton createTool(String icon, String tip, ActionListener act) {
		JButton b = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(icon))) {
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

	private void new_machines(CompositeState cs) {
		int hasC = (cs != null && cs.composition != null) ? 1 : 0;
		if (cs != null && cs.machines != null && cs.machines.size() > 0) { // get
			// set
			// of
			// machines
			sm = new CompactState[cs.machines.size() + hasC];
			Enumeration e = cs.machines.elements();
			for (int i = 0; e.hasMoreElements(); i++)
				sm[i] = (CompactState) e.nextElement();
			Nmach = sm.length;
			if (hasC == 1)
				sm[Nmach - 1] = cs.composition;
		} else
			Nmach = 0;
		DefaultListModel lm = new DefaultListModel();
		for (int i = 0; i < Nmach; i++) {
			if (hasC == 1 && i == (Nmach - 1))
				lm.addElement("||" + sm[i].name);
			else
				lm.addElement(sm[i].name);
		}
		list.setModel(lm);
		if (selectedMachine >= Nmach)
			selectedMachine = 0;
		current = null;
		out.clearOutput();
	}
	
	private void setBigFont()
	{
		if (bigFont.isSelected())
		{
			output.setFont(f2);
			list.setFont(f4);
		}
		else
		{
			output.setFont(f1);
			list.setFont(f3);
		}
	}
}

