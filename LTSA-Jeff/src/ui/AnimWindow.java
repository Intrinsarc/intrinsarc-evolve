package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.hopstepjump.backbone.runtime.api.*;

import lts.*;

/**
 * Window to run the simple animator
 */
public class AnimWindow extends JFrame implements ILifecycle
{
// start generated code
// attributes
// required ports
	private lts.IAnalyser animator;
// provided ports
	private IAnimWindowMainImpl main_IAnimWindowProvided = new IAnimWindowMainImpl();
// setters and getters
	public void setAnimator_IAnalyser(lts.IAnalyser animator) { this.animator = animator; }
	public ui.IAnimWindow getMain_IAnimWindow(Class<?> required) { return main_IAnimWindowProvided; }
// end generated code
	
	class AnimAction implements ActionListener {
		int choice;

		AnimAction(int id) {
			choice = id;
		}

		public void actionPerformed(ActionEvent e) {
			dostep(choice);
		}
	}

	public static boolean fontFlag = false;
	JTextArea output;
	JCheckBox[] choices;
	Font f1;
	BitSet actions;
	Color priority;
	BitSet pactions;
	String[] modelAlphabet;
	JButton step, run;
	public boolean autoRun = false;
	private final static int STEPLIMIT = 64;

	protected boolean traceMode = false;

	private class IAnimWindowMainImpl implements IAnimWindow
	{

		@Override
		public void activate(RunMenu r, boolean auto, boolean traceM, Point point)
		{
			autoRun = auto;
			traceMode = traceM;
			// setFont
			if (fontFlag) {
				f1 = new Font("SansSerif", Font.BOLD, 16); // should be bold - bug
			} else {
				f1 = new Font("SansSerif", Font.BOLD, 12);
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setBackground(Color.white);
			getContentPane().setLayout(
					new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
			// trace output
			output = new JTextArea("", 15, 15);
			output.setEditable(false);
			output.setFont(f1);
			output.setBackground(Color.white);
			output.setBorder(new EmptyBorder(0, 5, 0, 0));
			JScrollPane outp = new JScrollPane(output,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			getContentPane().add(outp);
			if (r == null)
				actions = animator.getAnimator().initialise(null);
			else
				actions = animator.getAnimator().initialise(r.alphabet);
			if (traceMode)
				setTitle("Replay Animator");
			else
				setTitle("Animator");
			// buttons
			Box buttons;
			step = new JButton("Step");
			step.setFont(f1);
			step.addActionListener(new AnimAction(traceMode ? -3 : -1));
			run = new JButton("Run");
			run.addActionListener(new AnimAction(traceMode ? -4 : -2));
			run.setFont(f1);
			buttons = Box.createHorizontalBox();
			buttons.add(run);
			buttons.add(step);
			// controls
			String alphabet[] = animator.getAnimator().getMenuNames();
			modelAlphabet = animator.getAnimator().getAllNames();
			if (animator.getAnimator().getPriority())
				priority = Color.cyan;
			else
				priority = Color.pink;
			pactions = animator.getAnimator().getPriorityActions();
			choices = new JCheckBox[alphabet.length];
			Box p = Box.createVerticalBox();
			for (int i = 1; i < alphabet.length; i++) {
				p
						.add(choices[i] = new JCheckBox(alphabet[i], null, actions
								.get(i)));
				choices[i].setFont(f1);
				choices[i].addActionListener(new AnimAction(i));
				if (traceMode)
					choices[i].setEnabled(false);
				if (pactions != null && pactions.get(i))
					choices[i].setBackground(priority);
			}
			p.add(Box.createHorizontalStrut(10));
			boolean enable = (animator.getAnimator().nonMenuChoice() || traceMode);
			step.setEnabled(enable);
			run.setEnabled(enable);
			if (empty(actions) && !enable)
				outln("STOP");
			JScrollPane boxes = new JScrollPane(p,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			boxes.setBorder(new EmptyBorder(0, 0, 0, 0));
			Box side = Box.createVerticalBox();
			side.add(buttons);
			side.add(boxes);
			getContentPane().add(side);
			p.setBackground(Color.white);
			validate();
			if (autoRun)
				dostep(-2);
			pack();
			point.translate(10, 100);
			setLocation(point);
			setVisible(true);
		}
	}

	public void clearOutput() {
		output.setText("");
	}

	private void dostep(int choice) {
		if (animator.getAnimator().isError())
			return;
		if (choice == -1) {
			actions = animator.getAnimator().singleStep();
			outAction();
		} else if (choice == -2) {
			actions = multiStep(null);
		} else if (choice == -3) {
			actions = animator.getAnimator().traceStep();
			outAction();
		} else if (choice == -4) {
			actions = multiTraceStep(null);
		} else if (!choices[choice].isSelected()) { // must have been toggled
			// from true
			actions = animator.getAnimator().menuStep(choice);
			outAction();
		}
		if (actions == null)
			return;
		if (autoRun && !traceMode)
			actions = multiStep(actions);
		for (int j = 1; j < choices.length; j++) {
			choices[j].setSelected(actions.get(j));
		}
		if (!traceMode) {
			boolean enable = animator.getAnimator().nonMenuChoice();
			step.setEnabled(enable);
			run.setEnabled(enable);
			if (empty(actions) && !enable && !animator.getAnimator().isError()) {
				if (animator.getAnimator().isEnd())
					outln("END");
				else
					outln("STOP");
			}
		} else {
			boolean enable = animator.getAnimator().traceChoice();
			step.setEnabled(enable);
			run.setEnabled(enable);
			if (!enable && !animator.getAnimator().isError()) {
				if (empty(actions)) {
					if (animator.getAnimator().isEnd())
						outln("END");
					else
						outln("STOP");
				} else {
					outln("DIVERGED FROM TRACE");
				}
			}
		}
		repaint();
	}

	// -----MuCSPOutput-------------------------------------------------------------------

	private boolean empty(BitSet b) {
		for (int i = 0; i < b.size(); ++i)
			if (b.get(i))
				return false;
		return true;
	}

	private BitSet multiStep(BitSet b) {
		int step = 0;
		while (animator.getAnimator().nonMenuChoice()) {
			b = animator.getAnimator().singleStep();
			outAction();
			if (++step > STEPLIMIT) {
				outln("LOOP");
				return b;
			}
		}
		return b;
	}

	private BitSet multiTraceStep(BitSet b) {
		while (animator.getAnimator().traceChoice()) {
			b = animator.getAnimator().traceStep();
			outAction();
		}
		return b;
	}

	// ------------------------------------------------------------------------

	public void out(String str) {
		output.append(str);
	}

	private void outAction() {
		outln(" " + modelAlphabet[animator.getAnimator().actionChosen()]);
		if (animator.getAnimator().isError())
			outln("ERROR");
	}

	public void outln(String str) {
		output.append(str + "\n");
	}

	@Override
	public void afterInit()
	{
	}

	@Override
	public void beforeDelete()
	{
		dispose();
	}
}
