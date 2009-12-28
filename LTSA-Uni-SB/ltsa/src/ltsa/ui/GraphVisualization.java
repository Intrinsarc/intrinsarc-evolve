package ltsa.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.*;

import javax.swing.*;

import ltsa.lts.*;
import att.grappa.*;

public class GraphVisualization extends JPanel {

	private class GrappaDisplayMachine {

		protected CompactState cs;
		protected int lastselected;
		protected int selected;
		protected String lastaction;

		public GrappaDisplayMachine(CompactState compactState) {
			this.cs = compactState;
		}

	}

	static {
		Grappa.antiAliasText = true;
		Grappa.useAntiAliasing = true;
		Grappa.elementSelection = GrappaConstants.NODE | GrappaConstants.EDGE;
		Grappa.doDisplayException = false;
	}

	private static final long serialVersionUID = 1366718749405507754L;

	protected LTSCanvas simpleLTSCanvas;
	protected GrappaPanel grappaPanel;
	protected Graph graph;
	protected final LTSDrawWindow drawWindow;
	protected GrappaDisplayMachine[] grappaDisplayingMachines = null;
	private boolean useBigFont = false;
	protected boolean isDrawName = true;

	protected boolean singleMode;
	protected int singleDisplayedMachine;

	protected boolean isUsingDot = false;
	private boolean isDotLayoutTopToBottom = false;

	Thread layouterJob = null;

	private final Thread layoutStopperThread = new Thread("Layout Stopper") {
		@Override
		public void run() {
			final Thread layouter = layouterJob;
			if (layouter != null) {
				try {
					while (layouter.isAlive()) {
						layouter.interrupt();
						layouter.join(500);
					}
				} catch (final InterruptedException e) {
					// ok, then give up...
					Thread.currentThread().interrupt();
				}
			}
		}
	};

	public GraphVisualization(boolean singleMode, LTSDrawWindow drawWindow) {
		super();
		this.drawWindow = drawWindow;
		this.singleMode = singleMode;
		setLayout(new GridLayout(1, 1));

		setUseDot(false, false);

		Runtime.getRuntime().addShutdownHook(layoutStopperThread);
	}

	public void setUseDot(boolean useDot, boolean topToBottom) {
		final boolean changed = useDot != isUsingDot
				|| topToBottom != isDotLayoutTopToBottom;
		isDotLayoutTopToBottom = topToBottom;
		if (!useDot && (isUsingDot || simpleLTSCanvas == null)) {
			// no new thread! the code is executed in the current thread
			layoutStopperThread.run();
			if (simpleLTSCanvas == null) {
				simpleLTSCanvas = createLTSCanvas();
			}
			isUsingDot = false;
			removeAll();
			add(simpleLTSCanvas);
			validate();
			repaint();
		} else if (useDot && (changed || grappaPanel == null)) {
			if (grappaPanel == null)
				createGrappaPanel();
			isUsingDot = true;
			removeAll();
			add(grappaPanel);
			validate();
			updateGraph();
		}
	}

	private void createGrappaPanel() {
		graph = new Graph("LTS Graph");
		grappaPanel = new GrappaPanel(graph);
		grappaPanel.addGrappaListener(new GrappaAdapter());
		grappaPanel.setScaleToFit(true);
		grappaPanel.setToolTipText("");
		graph.setToolTipText("");
		graph.addPanel(grappaPanel);
	}

	private LTSCanvas createLTSCanvas() {
		final LTSCanvas newLTSCanvas = new LTSCanvas(singleMode);
		newLTSCanvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent k) {
				final int code = k.getKeyCode();
				if (code == KeyEvent.VK_LEFT) {
					newLTSCanvas.stretchHorizontal(-5);
				} else if (code == KeyEvent.VK_RIGHT) {
					newLTSCanvas.stretchHorizontal(5);
				} else if (code == KeyEvent.VK_UP) {
					newLTSCanvas.stretchVertical(-5);
				} else if (code == KeyEvent.VK_DOWN) {
					newLTSCanvas.stretchVertical(5);
				} else if (code == KeyEvent.VK_BACK_SPACE) {
					final int m = newLTSCanvas.clearSelected();
					if (m >= 0)
						GraphVisualization.this.drawWindow.setMachineToDrawSet(
								m, false);
				}
			}
		});
		newLTSCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newLTSCanvas.requestFocus();
			}
		});
		return newLTSCanvas;
	}

	private synchronized void updateGraph() {
		if (!isUsingDot)
			return;
		// no new thread! the code is executed in the current thread
		layoutStopperThread.run();

		// graph.reset() sometimes returns without removing all elements (e.g.
		// while painting)
		do {
			graph.reset();
		} while (graph.nodeElements().hasMoreElements()
				|| graph.edgeElements().hasMoreElements()
				|| graph.subgraphElements().hasMoreElements());

		final Integer fontsize = useBigFont ? 26 : 14;

		graph.setAttribute(GrappaConstants.RANKDIR_ATTR,
				isDotLayoutTopToBottom ? "TB" : "LR");

		final Process dotProcess;
		try {
			dotProcess = Runtime.getRuntime().exec("dot");
		} catch (final IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Error starting the 'dot' layouter tool.\n"
									+ "Most probably it isn't installed on your system or is not "
									+ "in your PATH environment variable.\n\n"
									+ "Error Message: " + e, "dot error",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		// now layout the graph
		final FutureTask<Boolean> layouterTask = new FutureTask<Boolean>(
				new Callable<Boolean>() {
					public Boolean call() throws Exception {
						for (int machNr = 0; machNr < grappaDisplayingMachines.length; ++machNr) {
							GrappaDisplayMachine machine = grappaDisplayingMachines[machNr];
							if (machine == null
									|| (singleMode && machNr != singleDisplayedMachine))
								continue;
							Subgraph addTo;
							if (singleMode)
								addTo = graph;
							else {
								Subgraph subgraph = new Subgraph(graph,
										"cluster_" + machNr);
								if (isDrawName) {
									subgraph.setAttribute(
											GrappaConstants.LABEL_ATTR,
											machine.cs.name);
								}
								subgraph.setAttribute(
										GrappaConstants.STYLE_ATTR,
										"dotted, setlinewidth(0)");
								graph.addSubgraph(subgraph);
								addTo = subgraph;
							}
							final Node[] nodes = new Node[machine.cs.maxStates];
							Node errorNode = null;
							assert machine.cs.maxStates == machine.cs.states.length;
							for (int i = 0; i < machine.cs.maxStates; ++i) {
								if ((i & 255) == 255 && Thread.interrupted())
									throw new InterruptedException();
								nodes[i] = new Node(addTo, "node_" + machNr
										+ "_" + i);
								nodes[i].setAttribute(
										GrappaConstants.LABEL_ATTR, Integer
												.toString(i));
								nodes[i].setAttribute(GrappaConstants.TIP_ATTR,
										"Node " + i);
								nodes[i]
										.setAttribute(
												GrappaConstants.FONTSIZE_ATTR,
												fontsize);
								if (i == 0) {
									nodes[i].setAttribute(
											GrappaConstants.STYLE_ATTR,
											"filled");
									nodes[i].setAttribute(
											GrappaConstants.FILLCOLOR_ATTR,
											Color.LIGHT_GRAY);
								}
								addTo.addNode(nodes[i]);
							}

							// transitions
							for (int i = 0; i < machine.cs.maxStates; i++) {
								if ((i & 64) == 64 && Thread.interrupted())
									throw new InterruptedException();
								EventState p = machine.cs.states[i];
								final Node node = nodes[i];
								while (p != null) {
									EventState tr = p;
									final String label = machine.cs.alphabet[tr.event];
									if (label.charAt(0) != '@') {
										while (tr != null) {
											if (tr.next == -1
													&& errorNode == null) {
												errorNode = new Node(addTo);
												errorNode
														.setAttribute(
																GrappaConstants.LABEL_ATTR,
																"-1");
												errorNode
														.setAttribute(
																GrappaConstants.STYLE_ATTR,
																"filled");
												errorNode
														.setAttribute(
																GrappaConstants.FILLCOLOR_ATTR,
																Color.RED);
												errorNode
														.setAttribute(
																GrappaConstants.TIP_ATTR,
																"Error Node");
												errorNode
														.setAttribute(
																GrappaConstants.FONTSIZE_ATTR,
																fontsize);
												addTo.addNode(errorNode);
											}
											final Node endNode = tr.next == -1 ? errorNode
													: nodes[tr.next];
											final Edge newEdge = new Edge(
													graph, node, endNode);
											newEdge.setAttribute(
													GrappaConstants.LABEL_ATTR,
													label);
											newEdge.setAttribute(
													GrappaConstants.TIP_ATTR,
													"Transition: " + label);
											newEdge
													.setAttribute(
															GrappaConstants.FONTSIZE_ATTR,
															fontsize);
											if (i == machine.lastselected
													&& tr.next == machine.selected)
												newEdge.highlight |= GrappaConstants.SELECTION_MASK;
											graph.addEdge(newEdge);
											tr = tr.nondet;
										}
									}
									p = p.list;
								}
							}
						}
						return GrappaSupport.filterGraph(graph, dotProcess);
					}
				});
		new Thread(layouterTask, "Graph Layouter").start();
		boolean ready = false;
		try {
			ready = layouterTask.get(1, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			layouterTask.cancel(true);
			return;
		} catch (final ExecutionException e) {
			JOptionPane.showMessageDialog(GraphVisualization.this,
					"Graph could not be layout.\n\n" + "Error Message: "
							+ e.getCause(), "Error layouting",
					JOptionPane.ERROR_MESSAGE);
			return;
		} catch (final TimeoutException e1) {
			// ignore
		}
		if (ready) {
			graph.repaint();
			return;
		}

		final JPanel cancelButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setString("Laying out graph...");
		progressBar.setStringPainted(true);
		removeAll();
		cancelButtonPanel.add(progressBar);
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layouterJob.interrupt();
			}
		});
		cancelButtonPanel.add(cancelButton);
		add(cancelButtonPanel);
		validate();

		layouterJob = new Thread("Wait for Layouter") {
			@Override
			public void run() {
				boolean success = true;
				try {
					success &= layouterTask.get();
					dotProcess.waitFor();
					// only success if dot's return value is 0
					success &= dotProcess.exitValue() == 0;
					if (!success)
						JOptionPane.showMessageDialog(GraphVisualization.this,
								"Graph could not be layout.",
								"Error layouting", JOptionPane.ERROR_MESSAGE);
				} catch (final HeadlessException e) {
					JOptionPane.showMessageDialog(GraphVisualization.this,
							"Graph could not be layout.\n\n"
									+ "Error Message: " + e, "Error layouting",
							JOptionPane.ERROR_MESSAGE);
					return;
				} catch (final InterruptedException e) {
					layouterTask.cancel(true);
					dotProcess.destroy();
				} catch (final ExecutionException e) {
					JOptionPane.showMessageDialog(GraphVisualization.this,
							"Graph could not be layout.\n\n"
									+ "Error Message: " + e, "Error layouting",
							JOptionPane.ERROR_MESSAGE);
				}
				// on an error, we clear (reset) the graph
				// graph.reset() sometimes returns without removing all elements
				// (e.g. while painting)
				while (!success
						&& (graph.nodeElements().hasMoreElements()
								|| graph.edgeElements().hasMoreElements() || graph
								.subgraphElements().hasMoreElements()))
					graph.reset();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (!isUsingDot)
							return;
						removeAll();
						add(grappaPanel);
						validate();
						repaint();
					}
				});
				graph.repaint();
			}
		};
		layouterJob.start();
	}

	public void stretchHorizontal(int i) {
		if (!isUsingDot && simpleLTSCanvas != null)
			simpleLTSCanvas.stretchHorizontal(i);
	}

	public void stretchVertical(int i) {
		if (!isUsingDot && simpleLTSCanvas != null)
			simpleLTSCanvas.stretchVertical(i);
	}

	public void select(int i, int[] last, int[] current, String name) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.select(i, last, current, name);
		for (int m = 0; m < i; ++m) {
			if (m < grappaDisplayingMachines.length
					&& grappaDisplayingMachines[m] != null) {
				grappaDisplayingMachines[m].lastselected = last == null ? 0
						: last[m];
				grappaDisplayingMachines[m].selected = current == null ? 0
						: current[m];
				grappaDisplayingMachines[m].lastaction = name;
			}
			updateGraph();
		}
	}

	public void setMachines(int nmach) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.setMachines(nmach);
		grappaDisplayingMachines = new GrappaDisplayMachine[Math.max(0, nmach)];
		updateGraph();
	}

	public void setBigFont(boolean b) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.setBigFont(b);
		if (useBigFont != b) {
			useBigFont = b;
			updateGraph();
		}
	}

	public void setDrawName(boolean b) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.setDrawName(b);
		if (isDrawName != b) {
			isDrawName = b;
			updateGraph();
		}
	}

	public void setNewLabelFormat(boolean b) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.setNewLabelFormat(b);
	}

	public void setMode(boolean b) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.setMode(b);
		if (singleMode != b) {
			singleMode = b;
			updateGraph();
		}
	}

	public DrawMachine getDrawing() {
		if (!isUsingDot && simpleLTSCanvas != null)
			return simpleLTSCanvas.getDrawing();
		return null;
	}

	public void draw(int machine, CompactState compactState, int current,
			int last, String name) {
		if (simpleLTSCanvas != null) {
			simpleLTSCanvas.draw(machine, compactState, current, last, name);
		}
		if (machine >= 0 && machine < grappaDisplayingMachines.length) {
			if (singleMode)
				singleDisplayedMachine = machine;
			grappaDisplayingMachines[machine] = new GrappaDisplayMachine(
					compactState);
			grappaDisplayingMachines[machine].lastselected = last;
			grappaDisplayingMachines[machine].selected = current;
			grappaDisplayingMachines[machine].lastaction = name;
			updateGraph();
		}
	}

	public void clear(int machine) {
		if (simpleLTSCanvas != null)
			simpleLTSCanvas.clear(machine);
		if (machine >= 0 && machine < grappaDisplayingMachines.length) {
			grappaDisplayingMachines[machine] = null;
			updateGraph();
		}
	}

	public void zoom(double factor) {
		if (isUsingDot && grappaPanel != null) {
			scaleToFit(false);
			grappaPanel.multiplyScaleFactor(factor);
			grappaPanel.repaint();
		}
	}

	public void scaleToFit(boolean flag) {
		if (isUsingDot && grappaPanel != null) {
			if (!flag && grappaPanel.isScaleToFit()) {
				grappaPanel.setScaleFactor(grappaPanel.getCurrentScaleFactor());
			}
			grappaPanel.setScaleToFit(flag);
			grappaPanel.repaint();
		}
	}

}
