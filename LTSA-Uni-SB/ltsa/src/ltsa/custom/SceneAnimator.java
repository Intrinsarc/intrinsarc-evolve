package ltsa.custom;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import ltsa.lts.*;
import uk.ac.ic.doc.scenebeans.animation.*;
import uk.ac.ic.doc.scenebeans.animation.parse.*;
import uk.ac.ic.doc.scenebeans.input.*;

public class SceneAnimator extends CustomAnimator implements AnimationControl {

	private static final long serialVersionUID = 6963108078768999304L;

	SceneAnimationController tac;
	MenuBar mb;
	Menu run;
	MenuItem pause;
	MenuItem resume;
	Menu trace;
	CheckboxMenuItem setTrace;
	CheckboxMenuItem setDebug;
	Scrollbar bar;
	Animator animator;
	Relation<String, String> buttonControls;

	AnimationCanvas _canvas; // Scene Canvas
	MouseDispatcher _dispatcher;

	/*
	 * -- called by LTSA when it start animation
	 */

	public SceneAnimator() {
		setTitle("SceneBean Animator");
		this.addWindowListener(new MyWindow());
		setLayout(new BorderLayout());
		// the SceneGraph Canvas
		_canvas = new AnimationCanvas();
		_canvas.setBackground(java.awt.Color.white);
		_canvas.setAnimationStretched(true);
		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// hints.put(RenderingHints.KEY_RENDERING,
		// RenderingHints.VALUE_RENDER_QUALITY);
		_canvas.setRenderingHints(hints);
		_canvas.setFont(new java.awt.Font("SansSerif", Font.BOLD, 14));
		add("Center", _canvas);
		// frame rate control
		bar = new Scrollbar(Scrollbar.VERTICAL, 25, 1, 1, 32);
		_canvas.setFrameDelay(40); // twenty five frames per second
		add("East", bar);
		bar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				_canvas.setTimeWarp((33 - e.getValue()) * 0.125);
			}
		});
		// Build the menu bar.
		MenuBar mb = new MenuBar();
		setMenuBar(mb);
		// run menu
		run = new Menu("Run");
		mb.add(run);
		pause = new MenuItem("Pause");
		run.add(pause);
		resume = new MenuItem("Resume");
		run.add(resume);
		pause.setEnabled(true);
		resume.setEnabled(false);
		RunMenu m = new RunMenu();
		pause.addActionListener(m);
		resume.addActionListener(m);
		// trace menu
		trace = new Menu("Trace");
		mb.add(trace);
		setTrace = new CheckboxMenuItem("Trace");
		trace.add(setTrace);
		setTrace.setState(false);
		setDebug = new CheckboxMenuItem("Debug");
		trace.add(setDebug);
		setDebug.setState(false);
		CheckItem ca = new CheckItem();
		setDebug.addItemListener(ca);
		setTrace.addItemListener(ca);
		_dispatcher = new MouseDispatcher(_canvas.getSceneGraph(), _canvas);
		_dispatcher.attachTo(_canvas);

	}

	@Override
	public void init(Animator a, File xml, Relation<String, String> actions,
			Relation<String, String> controls, boolean replay) {
		if (replay)
			setTitle("Custom Animator - Replay Mode");
		setTrace.setState(replay);
		animator = a;
		if (actions == null || controls == null) {
			animator.message("Animator - must have 'controls' and 'actions'");
			dispose();
			return;
		}
		try {
			// create sprite
			XMLAnimationParser parser = new XMLAnimationParser(xml, _canvas);
			Animation sprite = parser.parseAnimation();
			_canvas.setAnimation(sprite);
			// find out which controls need buttons
			buttonControls = controls.inverse();
			// real type is string
			Iterator<?> i = sprite.getEventNames().iterator();
			while (i.hasNext()) {
				String control = (String) i.next();
				buttonControls.remove(control);
			}
			// add button clear actions to action relation
			Relation<String, String> all = new Relation<String, String>();
			all.union(actions);
			all.union(buttonControls.inverse());
			tac = new SceneAnimationController(a, all, controls, replay,
					_canvas);
			// register sprite actions
			i = sprite.getCommandNames().iterator();
			while (i.hasNext()) {
				registerAction((String) i.next());
			}
			// register button clear actions
			if (buttonControls.size() > 0)
				createButtons(buttonControls);
			// add tac to listen for sprite events
			sprite.addAnimationListener(tac);
			invalidate();
			pack();
			tac.start();
			clearButtons(buttonControls);
			tac.restart();
		} catch (Exception ex) {
			animator.message("XML-" + ex);
			ex.printStackTrace();
			dispose();
		}
	}

	protected void createButtons(Relation<String, String> r) {
		Panel p = new Panel();
		add("South", p);
		Enumeration<String> k = r.keys();
		while (k.hasMoreElements()) {
			String name = k.nextElement();
			Button b = new Button(name);
			b.setBackground(Color.green);
			b.addActionListener(new ButtonAction(name));
			registerButtonClearAction(name, b);
			p.add(b);
		}
	}

	protected void clearButtons(Relation<String, String> r) {
		Enumeration<String> k = r.keys();
		while (k.hasMoreElements()) {
			String name = k.nextElement();
			clearControl(name);
		}
	}

	@Override
	public void stop() {
		if (tac != null)
			tac.stop();
		if (_canvas != null)
			_canvas.stop();
	}

	public void registerAction(String name) {
		tac.registerAction(name, new CommandAction(name));
	}

	class CommandAction implements AnimationAction {
		String name;

		CommandAction(String s) {
			name = s;
		}

		public void action() {
			try {
				synchronized (_canvas) {
					_canvas.getAnimation().invokeCommand(name);
				}
			} catch (CommandException e) {
				System.out.println("Animation" + e);
			}
		}
	}

	public void registerButtonClearAction(String name, Button b) {
		tac.registerAction(name, new ButtonClearAction(name, b));
	}

	class ButtonClearAction implements AnimationAction {
		String name;
		Button button;

		ButtonClearAction(String s, Button b) {
			name = s;
			button = b;
		}

		public void action() {
			button.setBackground(Color.green);
			clearControl(name);
		}
	}

	/*
	 * -- signalling of control action
	 */
	public void signalControl(String name) {
		tac.signalControl(name);
	}

	public void clearControl(String name) {
		tac.clearControl(name);
	}

	/*
	 * window handler
	 */
	class MyWindow extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			dispose();
		}
	}

	/*
	 * check box handler
	 */
	class CheckItem implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == setTrace) {
				tac.setTrace(setTrace.getState());
			} else if (e.getSource() == setDebug) {
				tac.setDebug(setDebug.getState());
			}
		}
	}

	/*
	 * run menu handler
	 */
	class RunMenu implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == pause) {
				if (tac != null)
					tac.stop();
				pause.setEnabled(false);
				resume.setEnabled(true);
			} else if (e.getSource() == resume) {
				if (tac != null)
					tac.restart();
				pause.setEnabled(true);
				resume.setEnabled(false);
			}
		}
	}

	/*
	 * button handler
	 */
	class ButtonAction implements ActionListener {
		String name;

		public ButtonAction(String s) {
			name = s;
		}

		public void actionPerformed(ActionEvent e) {
			Button b = (Button) e.getSource();
			b.setBackground(Color.red);
			signalControl(name);
		}
	}

}