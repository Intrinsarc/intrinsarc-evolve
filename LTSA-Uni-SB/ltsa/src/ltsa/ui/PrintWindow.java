package ltsa.ui;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import ltsa.lts.*;

public class PrintWindow extends JSplitPane implements LTSOutput, EventClient {

	private static final long serialVersionUID = 4498895114859924357L;

	public static boolean fontFlag = false;

	JTextArea output;
	JList list;
	JScrollPane left, right;
	EventManager eman;
	int Nmach;
	int selectedMachine = 0;
	CompactState[] sm; // an array of machines
	Font f1 = new Font("Monospaced", Font.PLAIN, 12);
	Font f2 = new Font("Monospaced", Font.BOLD, 20);
	Font f3 = new Font("SansSerif", Font.PLAIN, 12);
	Font f4 = new Font("SansSerif", Font.BOLD, 18);
	PrintWindow thisWindow;
	private final static int MAXPRINT = 400;

	public PrintWindow(CompositeState cs, EventManager eman) {
		super();
		this.eman = eman;
		thisWindow = this;
		// scrollable output pane
		output = new JTextArea(23, 50);
		output.setEditable(false);
		right = new JScrollPane(output,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		output.setBackground(Color.white);
		output.setBorder(new EmptyBorder(0, 5, 0, 0));
		// scrollable list pane
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new PrintAction());
		left = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		if (eman != null)
			eman.addClient(this);
		new_machines(cs);
		setLeftComponent(left);
		setRightComponent(right);
		setDividerLocation(200);
		setBigFont(fontFlag);
		validate();
	}

	// ------------------------------------------------------------------------

	class PrintAction implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			final int machine = list.getSelectedIndex();
			if (machine < 0 || machine >= Nmach)
				return;
			selectedMachine = machine;
			clearOutput();
			final PrintTransitions p = new PrintTransitions(sm[selectedMachine]);
			p.print(thisWindow, MAXPRINT);
		}
	}

	/*---------LTS event broadcast action-----------------------------*/
	public void ltsAction(LTSEvent e) {
		switch (e.kind) {
		case LTSEvent.NEWSTATE:
			break;
		case LTSEvent.INVALID:
			new_machines((CompositeState) e.info);
			break;
		case LTSEvent.KILL:
			// this.dispose();
			break;
		}
	}

	// ------------------------------------------------------------------------

	public void out(String str) {
		output.append(str);
	}

	public void outln(String str) {
		output.append(str + "\n");
	}

	public void clearOutput() {
		output.setText("");
	}

	private void new_machines(CompositeState cs) {
		final int hasC = (cs != null && cs.composition != null) ? 1 : 0;
		if (cs != null && cs.machines != null && cs.machines.size() > 0) { // get
			// set
			// of
			// machines
			sm = new CompactState[cs.machines.size() + hasC];
			final Enumeration<CompactState> e = cs.machines.elements();
			for (int i = 0; e.hasMoreElements(); i++)
				sm[i] = e.nextElement();
			Nmach = sm.length;
			if (hasC == 1)
				sm[Nmach - 1] = cs.composition;
		} else
			Nmach = 0;
		final DefaultListModel lm = new DefaultListModel();
		for (int i = 0; i < Nmach; i++) {
			if (hasC == 1 && i == (Nmach - 1))
				lm.addElement("||" + sm[i].name);
			else
				lm.addElement(sm[i].name);
		}
		list.setModel(lm);
		if (selectedMachine >= Nmach)
			selectedMachine = 0;
		clearOutput();
	}

	// --------------------------------------------------------------------
	public void setBigFont(boolean b) {
		fontFlag = b;
		if (fontFlag) {
			output.setFont(f2);
			list.setFont(f4);
		} else {
			output.setFont(f1);
			list.setFont(f3);
		}
	}

	// --------------------------------------------------------------------
	public void removeClient() {
		if (eman != null)
			eman.removeClient(this);
	}

	public void copy() {
		output.copy();
	}

	// ------------------------------------------------------------------------

	public void saveFile(String currentDirectory, String extension) {
		String message;
		if (extension.equals(".txt"))
			message = "Save text in:";
		else
			message = "Save as Aldebaran format (.aut) in:";
		final FileDialog fd = new FileDialog((Frame) getTopLevelAncestor(),
				message, FileDialog.SAVE);
		if (Nmach > 0) {
			String fname = sm[selectedMachine].name;
			final int colon = fname.indexOf(':', 0);
			if (colon > 0)
				fname = fname.substring(0, colon);
			fd.setFile(fname + extension);
			fd.setDirectory(currentDirectory);
		}
		fd.setVisible(true);
		String sn = fd.getFile();
		if (sn == null)
			return;

		try {
			final int i = sn.indexOf('.', 0);
			sn = sn.substring(0, i) + extension;
			final File file = new File(fd.getDirectory(), sn);
			final FileOutputStream fout = new FileOutputStream(file);
			// now convert the FileOutputStream into a PrintStream
			final PrintStream myOutput = new PrintStream(fout);
			if (extension.equals(".txt")) {
				final String text = output.getText();
				myOutput.print(text);
			} else {
				if (Nmach > 0)
					sm[selectedMachine].printAUT(myOutput);
			}
			myOutput.close();
			fout.close();
			// outln("Saved in: "+ fd.getDirectory()+file);
		} catch (final IOException e) {
			outln("Error saving file: " + e);
		}
	}

}