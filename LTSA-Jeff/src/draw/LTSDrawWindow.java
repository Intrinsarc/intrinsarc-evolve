package draw;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import lts.*;
import dclap.*;

public class LTSDrawWindow
{
// start generated code
// attributes
// required ports
	private javax.swing.JCheckBoxMenuItem bigFont;
	private javax.swing.JCheckBoxMenuItem v2Labels;
	private javax.swing.JCheckBoxMenuItem multipleLTS;
	private javax.swing.JCheckBoxMenuItem displayName;
// provided ports
	private IWindowWindowImpl window_IWindowProvided = new IWindowWindowImpl();
	private EventClientEventsImpl events_EventClientProvided = new EventClientEventsImpl();
	private ActionListenerBigFontImpl bigFont_ActionListenerProvided = new ActionListenerBigFontImpl();
	private ActionListenerV2LabelsImpl v2Labels_ActionListenerProvided = new ActionListenerV2LabelsImpl();
	private ActionListenerMultipleLTSImpl multipleLTS_ActionListenerProvided = new ActionListenerMultipleLTSImpl();
	private ActionListenerDisplayNameImpl displayName_ActionListenerProvided = new ActionListenerDisplayNameImpl();
// setters and getters
	public void setBigFont_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem bigFont) { this.bigFont = bigFont; }
	public void setV2Labels_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem v2Labels) { this.v2Labels = v2Labels; }
	public void setMultipleLTS_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem multipleLTS) { this.multipleLTS = multipleLTS; }
	public void setDisplayName_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem displayName) { this.displayName = displayName; }
	public ui.IWindow getWindow_IWindow(Class<?> required) { return window_IWindowProvided; }
	public lts.EventClient getEvents_EventClient(Class<?> required) { return events_EventClientProvided; }
	public java.awt.event.ActionListener getBigFont_ActionListener(Class<?> required) { return bigFont_ActionListenerProvided; }
	public java.awt.event.ActionListener getV2Labels_ActionListener(Class<?> required) { return v2Labels_ActionListenerProvided; }
	public java.awt.event.ActionListener getMultipleLTS_ActionListener(Class<?> required) { return multipleLTS_ActionListenerProvided; }
	public java.awt.event.ActionListener getDisplayName_ActionListener(Class<?> required) { return displayName_ActionListenerProvided; }
// end generated code

	LTSCanvas output;
	CompositeState cs;
	int[] lastEvent, prevEvent;

	String lastName;
	int Nmach = 0; // the number of machines

	int hasC = 0; // 1 if last machine is composition
	CompactState[] sm; // an array of machines

	boolean[] machineHasAction;
	boolean[] machineToDrawSet;

	JList list;

	JScrollPane left, right;
	JSplitPane me;

	// ------------------------------------------------------------------------

	Font f1 = new Font("Monospaced", Font.PLAIN, 12);

	Font f2 = new Font("Monospaced", Font.BOLD, 16);

	Font f3 = new Font("SansSerif", Font.PLAIN, 12);

	Font f4 = new Font("SansSerif", Font.BOLD, 16);

	ImageIcon drawIcon = new ImageIcon(ClassLoader.getSystemResource("icon/draw.gif"));

	private class ActionListenerBigFontImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setBigFont();
		}		
	}
	
	private class ActionListenerDisplayNameImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setDrawName();
		}		
	}

	private class ActionListenerV2LabelsImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setNewLabelFormat();
		}		
	}

	private class ActionListenerMultipleLTSImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setMode();
		}
	}
	
	private class EventClientEventsImpl implements EventClient
	{
		@Override
		public void ltsAction(LTSEvent e) {
			switch (e.kind) {
			case LTSEvent.NEWSTATE:
				prevEvent = lastEvent;
				lastEvent = (int[]) e.info;
				lastName = e.name;
				output.select(Nmach - hasC, prevEvent, lastEvent, e.name);
				buttonHighlight(e.name);
				break;
			case LTSEvent.INVALID:
				prevEvent = null;
				lastEvent = null;
				new_machines(cs = (CompositeState) e.info);
				break;
			case LTSEvent.KILL:
				break;
			default:
				;
			}
		}
	}

	private boolean singleMode()
	{
		return !multipleLTS.isSelected();
	}
	

	private class IWindowWindowImpl implements ui.IWindow
	{

		@Override
		public void activate(CompositeState cs)
		{
			// output canvas
			me = new JSplitPane();
			output = new LTSCanvas(singleMode());
			JPanel outPane = new JPanel();
			outPane.setLayout(new BorderLayout());
			outPane.add("Center", output);
			right = new JScrollPane(outPane,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			// right.getViewport().putClientProperty("EnableWindowBlit",
			// Boolean.TRUE);
			// scrollable list pane
			list = new JList();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.addListSelectionListener(new PrintAction());
			list.setCellRenderer(new MyCellRenderer());
			left = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JPanel fortools = new JPanel(new BorderLayout());
			fortools.add("Center", right);
			// tool bar
			JToolBar tools = new JToolBar();
			tools.setOrientation(JToolBar.VERTICAL);
			fortools.add("West", tools);
			tools.add(createTool("icon/stretchHorizontal.gif",
					"Stretch Horizontal", new HStretchAction(10)));
			tools.add(createTool("icon/compressHorizontal.gif",
					"Compress Horizontal", new HStretchAction(-10)));
			tools.add(createTool("icon/stretchVertical.gif", "Stretch Vertical",
					new VStretchAction(10)));
			tools.add(createTool("icon/compressVertical.gif", "Compress Vertical",
					new VStretchAction(-10)));
			new_machines(cs);
			me.setLeftComponent(left);
			me.setRightComponent(fortools);
			me.setDividerLocation(200);
			setBigFont();
			setDrawName();
			setMode();
			setNewLabelFormat();
			me.validate();
			output.addKeyListener(new KeyPress());
			output.addMouseListener(new MyMouse());
		}

		@Override
		public void copy()
		{
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
		public void saveFile(String directory)
		{
			DrawMachine dm = output.getDrawing();
			if (dm == null) {
				JOptionPane.showMessageDialog(me,
						"No LTS picture selected to save");
				return;
			}
			FileDialog fd = new FileDialog((Frame) me.getTopLevelAncestor(),
					"Save file in:", FileDialog.SAVE);
			if (Nmach > 0) {
				String fname = dm.getMachine().name;
				int colon = fname.indexOf(':', 0);
				if (colon > 0)
					fname = fname.substring(0, colon);
				fd.setFile(fname + ".pct");
			}
			fd.show();
			String file = fd.getFile();
			if (file != null)
				try {
					int i = file.indexOf('.', 0);
					file = file.substring(0, i) + "." + "pct";
					FileOutputStream fout = new FileOutputStream(fd.getDirectory()
							+ file);
					// get picture
					ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
					Rectangle r = new Rectangle(0, 0, dm.getSize().width, dm
							.getSize().height);
					Gr2PICT pict = new Gr2PICT(baos, output.getGraphics(), r);
					dm.fileDraw(pict);
					pict.finalize(); // make sure pict end is written
					fout.write(baos.toByteArray());
					fout.flush();
					fout.close();
					// outln("Saved in: "+ fd.getDirectory()+file);
				} catch (IOException e) {
					System.out.println("Error saving file: " + e);
				}
		}
	}
	
	class HStretchAction implements ActionListener {
		int increment;

		HStretchAction(int i) {
			increment = i;
		}

		public void actionPerformed(ActionEvent e) {
			if (output != null)
				output.stretchHorizontal(increment);
		}
	}
	class KeyPress extends KeyAdapter {
		public void keyPressed(KeyEvent k) {
			if (output == null)
				return;
			int code = k.getKeyCode();
			if (code == KeyEvent.VK_LEFT) {
				output.stretchHorizontal(-5);
			} else if (code == KeyEvent.VK_RIGHT) {
				output.stretchHorizontal(5);
			} else if (code == KeyEvent.VK_UP) {
				output.stretchVertical(-5);
			} else if (code == KeyEvent.VK_DOWN) {
				output.stretchVertical(5);
			} else if (code == KeyEvent.VK_BACK_SPACE) {
				int m = output.clearSelected();
				if (m >= 0) {
					machineToDrawSet[m] = false;
					list.repaint();
				}
			}
		}
	}
	class MyCellRenderer extends JLabel implements ListCellRenderer {
		public MyCellRenderer() {
			setOpaque(true);
			setHorizontalTextPosition(SwingConstants.LEFT);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setFont(bigFont.isSelected() ? f4 : f3);
			setText(value.toString());
			setBackground(isSelected ? Color.blue : Color.white);
			setForeground(isSelected ? Color.white : Color.black);
			if (machineHasAction != null && machineHasAction[index]) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			setForeground(isSelected ? Color.white : Color.black);
			setIcon(machineToDrawSet[index] && !singleMode() ? drawIcon : null);
			return this;
		}
	}
	class MyMouse extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			output.requestFocus();
		}
	}
	class PrintAction implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() && !singleMode())
				return;
			int machine = list.getSelectedIndex();
			if (machine < 0 || machine >= Nmach)
				return;
			if (singleMode()) {
				output.draw(machine, sm[machine], validMachine(machine,
						prevEvent), validMachine(machine, lastEvent), lastName);
			} else {
				if (!machineToDrawSet[machine]) {
					output.draw(machine, sm[machine], validMachine(machine,
							prevEvent), validMachine(machine, lastEvent),
							lastName);
					machineToDrawSet[machine] = true;
				} else {
					output.clear(machine);
					machineToDrawSet[machine] = false;
				}
				list.clearSelection();
			}
		}
	}
	class VStretchAction implements ActionListener {
		int increment;

		VStretchAction(int i) {
			increment = i;
		}

		public void actionPerformed(ActionEvent e) {
			if (output != null)
				output.stretchVertical(increment);
		}
	}
	/*---------LTS event broadcast action-----------------------------*/

	private void buttonHighlight(String label) {
		if (label == null && machineHasAction != null) {
			for (int i = 0; i < machineHasAction.length; i++)
				machineHasAction[i] = false;
		} else if (machineHasAction != null) {
			for (int i = 0; i < sm.length - hasC; i++)
				machineHasAction[i] = (!label.equals("tau") && sm[i]
						.hasLabel(label));
		}
		list.repaint();
		return;
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

	// ------------------------------------------------------------------------

	private void new_machines(CompositeState cs) {
		hasC = (cs != null && cs.composition != null) ? 1 : 0;
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
			machineHasAction = new boolean[Nmach];
			machineToDrawSet = new boolean[Nmach];
		} else {
			Nmach = 0;
			machineHasAction = null;
			machineToDrawSet = null;
		}
		DefaultListModel lm = new DefaultListModel();
		for (int i = 0; i < Nmach; i++) {
			if (hasC == 1 && i == (Nmach - 1))
				lm.addElement("||" + sm[i].name);
			else
				lm.addElement(sm[i].name);
		}
		list.setModel(lm);
		output.setMachines(Nmach);
	}

	// --------------------------------------------------------------------
	public void setDrawName() {
		boolean b = displayName.isSelected();
		output.setDrawName(b);
		LTSCanvas.displayName = b;
	}

	public void setMode() {
		boolean b = singleMode();
		output.setMode(b);
		list.clearSelection();
		if (Nmach > 0)
			machineToDrawSet = new boolean[Nmach];
		list.repaint();
	}

	// ------------------------------------------------------------------------

	public void setNewLabelFormat() {
		boolean b = v2Labels.isSelected();
		LTSCanvas.newLabelFormat = b;
		output.setNewLabelFormat(b);
	}

	/* -------------------------------------------- */

	private int validMachine(int machine, int[] event) {
		if (event != null && machine < (Nmach - hasC))
			return event[machine];
		else
			return 0;
	}

	public void setBigFont() {
		boolean big = bigFont.isSelected();
		LTSCanvas.fontFlag = big;
		output.setBigFont(big);
	}
}
