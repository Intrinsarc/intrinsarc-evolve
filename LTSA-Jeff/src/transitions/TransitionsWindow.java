package transitions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import ui.*;

import lts.*;

import com.hopstepjump.backbone.runtime.api.*;

public class TransitionsWindow
{
// start generated code
// attributes
	private Attribute<java.lang.String> extension;
// required ports
	private javax.swing.JCheckBoxMenuItem bigFont;
// provided ports
	private IWindowWindowImpl window_IWindowProvided = new IWindowWindowImpl();
	private EventClientEventsImpl events_EventClientProvided = new EventClientEventsImpl();
	private ActionListenerBigFontImpl bigFont_ActionListenerProvided = new ActionListenerBigFontImpl();
// setters and getters
	public Attribute<java.lang.String> getExtension() { return extension; }
	public void setExtension(Attribute<java.lang.String> extension) { this.extension = extension;}
	public void setRawExtension(java.lang.String extension) { this.extension.set(extension);}
	public void setBigFont_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem bigFont) { this.bigFont = bigFont; }
	public ui.IWindow getWindow_IWindow(Class<?> required) { return window_IWindowProvided; }
	public lts.EventClient getEvents_EventClient(Class<?> required) { return events_EventClientProvided; }
	public java.awt.event.ActionListener getBigFont_ActionListener(Class<?> required) { return bigFont_ActionListenerProvided; }
// end generated code
	private LTSOutput out = new LTSOutputImpl();
	
	private class ActionListenerBigFontImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setBigFont();
		}		
	}
	
	class PrintAction implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			int machine = list.getSelectedIndex();
			if (machine < 0 || machine >= Nmach)
				return;
			selectedMachine = machine;
			out.clearOutput();
			PrintTransitions p = new PrintTransitions(sm[selectedMachine]);
			p.print(out, MAXPRINT);
		}
	}

	private static boolean fontFlag = false;
	JTextArea output;
	JList list;
	JScrollPane left, right;
	int Nmach;
	int selectedMachine = 0;
	CompactState[] sm; // an array of machines
	Font f1 = new Font("Monospaced", Font.PLAIN, 12);
	Font f2 = new Font("Monospaced", Font.BOLD, 20);
	Font f3 = new Font("SansSerif", Font.PLAIN, 12);
	Font f4 = new Font("SansSerif", Font.BOLD, 18);
	JSplitPane me;
	
	private final static int MAXPRINT = 400;

	// ------------------------------------------------------------------------

	private class EventClientEventsImpl implements EventClient
	{
		@Override
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
			default:
				;
			}
		}		
	}
	
	private class LTSOutputImpl implements LTSOutput
	{
		@Override
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
	
	private class IWindowWindowImpl implements IWindow
	{
		@Override
		public void activate(CompositeState cs) {
			// scrollable output pane
			me = new JSplitPane();
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
			left = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			new_machines(cs);
			me.setLeftComponent(left);
			me.setRightComponent(right);
			me.setDividerLocation(200);
			setBigFont();
			me.validate();
		}

		@Override
		public void copy() {
			output.copy();
		}

		@Override
		public void deactivate()
		{
		}

		@Override
		public JComponent getComponent() {
			return me;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public boolean isActive() {
			return true;
		}

		@Override
		public void saveFile(String directory) {
			String message;
			if (extension.equals(".txt"))
				message = "Save text in:";
			else
				message = "Save as Aldebaran format (.aut) in:";
			FileDialog fd = new FileDialog((Frame) me.getTopLevelAncestor(), message,
					FileDialog.SAVE);
			if (Nmach > 0) {
				String fname = sm[selectedMachine].name;
				int colon = fname.indexOf(':', 0);
				if (colon > 0)
					fname = fname.substring(0, colon);
				fd.setFile(fname + extension.get());
				fd.setDirectory(directory);
			}
			fd.show();
			String sn;
			if ((sn = fd.getFile()) != null)
				try {
					int i = sn.indexOf('.', 0);
					sn = sn.substring(0, i) + extension.get();
					File file = new File(fd.getDirectory(), sn);
					FileOutputStream fout = new FileOutputStream(file);
					// now convert the FileOutputStream into a PrintStream
					PrintStream myOutput = new PrintStream(fout);
					if (extension.get().equals(".txt")) {
						String text = output.getText();
						myOutput.print(text);
					} else {
						if (Nmach > 0)
							sm[selectedMachine].printAUT(myOutput);
					}
					myOutput.close();
					fout.close();
					// outln("Saved in: "+ fd.getDirectory()+file);
				} catch (IOException e) {
					out.outln("Error saving file: " + e);
				}
		}
	}
	
	// ------------------------------------------------------------------------

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
		out.clearOutput();
	}

	// ------------------------------------------------------------------------

	public void setBigFont() {
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
