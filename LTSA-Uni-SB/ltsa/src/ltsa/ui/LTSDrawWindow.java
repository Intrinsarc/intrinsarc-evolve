package ltsa.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import ltsa.dclap.*;
import ltsa.lts.*;

public class LTSDrawWindow extends JSplitPane implements EventClient {

	private static final long serialVersionUID = -719490984791363789L;

	private static boolean isDotAvailable = checkForDotAvailable();

	private final EventManager eman;
	protected int[] lastEvent, prevEvent;
	protected String lastName;
	protected int Nmach = 0; // the number of machines
	private int hasC = 0; // 1 if last machine is composition
	protected CompactState[] sm; // an array of machines
	protected boolean[] machineHasAction;
	protected boolean[] machineToDrawSet;

	public static boolean fontFlag = false;
	public static boolean singleMode = false;

	protected final JList list;
	private final JScrollPane left, right;

	protected GraphVisualization visualization;

	/*
	 * private final Font f1 = new Font("Monospaced", Font.PLAIN, 12); private
	 * final Font f2 = new Font("Monospaced", Font.BOLD, 16);
	 */
	protected final Font f1 = new Font("SansSerif", Font.PLAIN, 12);
	protected final Font f2 = new Font("SansSerif", Font.BOLD, 16);

	protected final ImageIcon drawIcon = new ImageIcon(getClass().getResource(
			"/icon/draw.gif"));

	// Toolbar buttons
	private final AbstractButton zoomOutButton;
	private final AbstractButton zoomInButton;
	private final AbstractButton zoomToFitButton;
	private final AbstractButton simpleLayoutButton;
	private final AbstractButton dotLayoutLRButton;
	private final AbstractButton dotLayoutTBButton;
	private final AbstractButton stretchHorizontalButton;
	private final AbstractButton compressHorizontalButton;
	private final AbstractButton stretchVerticalButton;
	private final AbstractButton compressVerticalButton;

	protected boolean isScaleToFit = true;
	private boolean isUsingDot = false;
	private boolean isDotTopToBottom = false;

	public LTSDrawWindow(CompositeState cs, EventManager eman) {
		super(JSplitPane.HORIZONTAL_SPLIT, true);
		this.eman = eman;
		// output canvas
		visualization = new GraphVisualization(singleMode, this);
		right = new JScrollPane(visualization,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scrollable list pane
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new PrintAction());
		list.setCellRenderer(new MyCellRenderer());
		left = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		final JPanel fortools = new JPanel(new BorderLayout());
		fortools.add("Center", right);
		// tool bar
		final JToolBar tools = new JToolBar();
		tools.setOrientation(SwingConstants.VERTICAL);
		fortools.add("West", tools);

		simpleLayoutButton = createTool("/icon/simpleLayout.gif",
				"Use a simple layouter", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						switchLayout(false, false);
					}
				}, true);
		tools.add(simpleLayoutButton);
		stretchHorizontalButton = createTool("/icon/stretchHorizontal.gif",
				"Stretch Horizontal", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						visualization.stretchHorizontal(10);
					}
				}, false);
		tools.add(stretchHorizontalButton);
		compressHorizontalButton = createTool("/icon/compressHorizontal.gif",
				"Compress Horizontal", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						visualization.stretchHorizontal(-10);
					}
				}, false);
		tools.add(compressHorizontalButton);
		stretchVerticalButton = createTool("/icon/stretchVertical.gif",
				"Stretch Vertical", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						visualization.stretchVertical(10);
					}
				}, false);
		tools.add(stretchVerticalButton);
		compressVerticalButton = createTool("/icon/compressVertical.gif",
				"Compress Vertical", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						visualization.stretchVertical(-10);
					}
				}, false);
		tools.add(compressVerticalButton);
		tools.addSeparator();
		dotLayoutLRButton = createTool("/icon/dotLayoutLR.gif",
				"Use dot layouter (left to right)", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						switchLayout(true, false);
					}
				}, true);
		if (!isDotAvailable) {
			dotLayoutLRButton.setEnabled(false);
			dotLayoutLRButton
					.setToolTipText("The \"dot\" tool is not available!");
		}
		tools.add(dotLayoutLRButton);
		dotLayoutTBButton = createTool("/icon/dotLayoutTB.gif",
				"Use dot layouter (top to bottom)", new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						switchLayout(true, true);
					}
				}, true);
		if (!isDotAvailable) {
			dotLayoutTBButton.setEnabled(false);
			dotLayoutTBButton
					.setToolTipText("The \"dot\" tool is not available!");
		}
		tools.add(dotLayoutTBButton);
		zoomInButton = createTool("/icon/zoomin.gif", "Zoom In",
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						zoom(1.25);
					}
				}, false);
		tools.add(zoomInButton);
		zoomOutButton = createTool("/icon/zoomout.gif", "Zoom Out",
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						zoom(0.8);
					}
				}, false);
		tools.add(zoomOutButton);
		zoomToFitButton = createTool("/icon/zoomfit.gif", "Scale to fit",
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isScaleToFit = !isScaleToFit;
						visualization.scaleToFit(isScaleToFit);
						updateButtonStates();
					}
				}, true);
		tools.add(zoomToFitButton);
		switchLayout(false, false);

		if (eman != null)
			eman.addClient(this);
		new_machines(cs);
		setLeftComponent(left);
		setRightComponent(fortools);
		setDividerLocation(200);
		setBigFont(fontFlag);
		validate();
	}

	protected void zoom(double factor) {
		isScaleToFit = false;
		visualization.zoom(factor);
		updateButtonStates();
	}

	protected void switchLayout(boolean useDot, boolean topToBottom) {
		isUsingDot = useDot;
		isDotTopToBottom = topToBottom;
		visualization.setUseDot(useDot, topToBottom);
		updateButtonStates();
	}

	protected void updateButtonStates() {
		simpleLayoutButton.setSelected(!isUsingDot);
		dotLayoutLRButton.setSelected(isUsingDot && !isDotTopToBottom);
		dotLayoutTBButton.setSelected(isUsingDot && isDotTopToBottom);
		stretchHorizontalButton.setEnabled(!isUsingDot);
		stretchVerticalButton.setEnabled(!isUsingDot);
		compressHorizontalButton.setEnabled(!isUsingDot);
		compressVerticalButton.setEnabled(!isUsingDot);
		zoomInButton.setEnabled(isUsingDot);
		zoomOutButton.setEnabled(isUsingDot);
		zoomToFitButton.setEnabled(isUsingDot);
		zoomToFitButton.setSelected(isScaleToFit);
	}

	// ------------------------------------------------------------------------

	class PrintAction implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() && !singleMode)
				return;
			final int machine = list.getSelectedIndex();
			if (machine < 0 || machine >= Nmach)
				return;
			if (singleMode) {
				visualization.draw(machine, sm[machine], validMachine(machine,
						prevEvent), validMachine(machine, lastEvent), lastName);
			} else {
				if (!machineToDrawSet[machine]) {
					visualization.draw(machine, sm[machine], validMachine(
							machine, prevEvent), validMachine(machine,
							lastEvent), lastName);
					machineToDrawSet[machine] = true;
				} else {
					visualization.clear(machine);
					machineToDrawSet[machine] = false;
				}
				list.clearSelection();
			}
		}
	}

	int validMachine(int machine, int[] event) {
		if (event != null && machine < (Nmach - hasC))
			return event[machine];
		else
			return 0;
	}

	/*---------LTS event broadcast action-----------------------------*/

	public void ltsAction(LTSEvent e) {
		switch (e.kind) {
		case LTSEvent.NEWSTATE:
			prevEvent = lastEvent;
			lastEvent = (int[]) e.info;
			lastName = e.name;
			visualization.select(Nmach - hasC, prevEvent, lastEvent, e.name);
			buttonHighlight(e.name);
			break;
		case LTSEvent.INVALID:
			prevEvent = null;
			lastEvent = null;
			new_machines((CompositeState) e.info);
			break;
		case LTSEvent.KILL:
			break;
		}
	}

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

	private void new_machines(CompositeState cs) {
		hasC = (cs != null && cs.composition != null) ? 1 : 0;
		if (cs != null && cs.machines != null && cs.machines.size() > 0) {
			// get set of machines
			sm = new CompactState[cs.machines.size() + hasC];
			final Enumeration<CompactState> e = cs.machines.elements();
			for (int i = 0; e.hasMoreElements(); i++)
				sm[i] = e.nextElement();
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
		final DefaultListModel lm = new DefaultListModel();
		for (int i = 0; i < Nmach; i++) {
			if (hasC == 1 && i == (Nmach - 1))
				lm.addElement("||" + sm[i].name);
			else
				lm.addElement(sm[i].name);
		}
		list.setModel(lm);
		visualization.setMachines(Nmach);
	}

	// ------------------------------------------------------------------------

	protected AbstractButton createTool(String icon, String tip,
			ActionListener act, boolean toggle) {
		final ImageIcon imageIcon = new ImageIcon(getClass().getResource(icon));
		final AbstractButton b = toggle ? new JToggleButton(imageIcon)
				: new JButton(imageIcon);
		b.setAlignmentY(0.5f);
		b.setRequestFocusEnabled(false);
		b.setMargin(new Insets(0, 0, 0, 0));
		b.setToolTipText(tip);
		b.addActionListener(act);
		return b;
	}

	// --------------------------------------------------------------------
	public void setBigFont(boolean b) {
		fontFlag = b;
		visualization.setBigFont(b);
	}

	public void setDrawName(boolean b) {
		visualization.setDrawName(b);
	}

	public void setNewLabelFormat(boolean b) {
		visualization.setNewLabelFormat(b);
	}

	public void setMode(boolean b) {
		singleMode = b;
		visualization.setMode(b);
		list.clearSelection();
		if (Nmach > 0)
			machineToDrawSet = new boolean[Nmach];
		list.repaint();
	}

	// --------------------------------------------------------------------
	public void removeClient() {
		if (eman != null)
			eman.removeClient(this);
	}

	// ------------------------------------------------------------------------

	class MyCellRenderer extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = -2973191343946062819L;

		public MyCellRenderer() {
			setOpaque(true);
			setHorizontalTextPosition(SwingConstants.LEFT);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setFont(fontFlag ? f2 : f1);
			setText(value.toString());
			setBackground(isSelected ? Color.blue : Color.white);
			setForeground(isSelected ? Color.white : Color.black);
			if (machineHasAction != null && machineHasAction[index]) {
				setBackground(Color.red);
				setForeground(Color.white);
			}
			setForeground(isSelected ? Color.white : Color.black);
			setIcon(machineToDrawSet[index] && !singleMode ? drawIcon : null);
			return this;
		}
	}

	/* -------------------------------------------- */

	public void saveFile() {
		// TODO handle saving of grappa panels
		final DrawMachine dm = visualization.getDrawing();
		if (dm == null) {
			JOptionPane.showMessageDialog(this,
					"No LTS picture selected to save");
			return;
		}
		final FileDialog fd = new FileDialog((Frame) getTopLevelAncestor(),
				"Save file in:", FileDialog.SAVE);
		if (Nmach > 0) {
			String fname = dm.getMachine().name;
			final int colon = fname.indexOf(':', 0);
			if (colon > 0)
				fname = fname.substring(0, colon);
			fd.setFile(fname + ".pct");
		}
		fd.setVisible(true);
		String file = fd.getFile();
		if (file == null)
			return;

		try {
			final int i = file.indexOf('.', 0);
			file = file.substring(0, i) + "." + "pct";
			final FileOutputStream fout = new FileOutputStream(fd
					.getDirectory()
					+ file);
			// get picture
			final ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
			final Rectangle r = new Rectangle(0, 0, dm.getSize().width, dm
					.getSize().height);
			final Gr2PICT pict = new Gr2PICT(baos, visualization.getGraphics(),
					r);
			dm.fileDraw(pict);
			pict.finalize(); // make sure pict end is written
			fout.write(baos.toByteArray());
			fout.flush();
			fout.close();
			// outln("Saved in: "+ fd.getDirectory()+file);
		} catch (final IOException e) {
			System.out.println("Error saving file: " + e);
		}
	}

	public void setMachineToDrawSet(int m, boolean state) {
		machineToDrawSet[m] = state;
		list.repaint();
	}

	private static boolean checkForDotAvailable() {
		Process dotProcess = null;
		try {
			dotProcess = Runtime.getRuntime().exec("dot");
			return true;
		} catch (final IOException e) {
			return false;
		} finally {
			if (dotProcess != null) {
				dotProcess.destroy();
				try {
					dotProcess.waitFor();
				} catch (final InterruptedException e) {
					// ignore
				}
			}
		}
	}

}