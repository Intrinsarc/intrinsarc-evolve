/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import edu.umd.cs.jazz.*;

/**
 * <b>ZDebug</b> provides
 * static methods for maintaining/setting/retrieving global debugging state.
 * It is not inteneded to be instantiated.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @author Britt McAlister
 * @see ZNode
 */
public class ZDebug implements Serializable {

	/**
	 * Flag denoting whether debugging in general should be enabled.
	 * Settig thisto false will cause all debugging code will be optimized out
	 * of Jazz during compilation.
	 */
	static final public boolean debug = false;

	/**
	 * Flag denoting whether the bounds of each object are being rendered
	 * Don't set this directly - rather call {@link #setShowBounds}.
	 */
	static public boolean showBounds = false;

	/**
	 * Camera that is associated with bounds.  It is used show that when
	 * damaging nodes and displaying bounds, we know what magnification
	 * we are at so we can compute appropriate thin bounds.
	 */
	static private ZCamera boundsCamera = null;

	/**
	 * Flag denoting whether there is a display to help debug region management
	 */
	static public boolean debugRegionMgmt = false;

	/**
	 * Flag denoting whether to print debugging info related to render operations
	 */
	static public boolean debugRender = false;

	/**
	 * Flag denoting whether to print debugging info related to repaint operations
	 */
	static public boolean debugRepaint = false;

	/**
	 * Flag denoting whether to print debugging info related to timing
	 */
	static public boolean debugTiming = false;

	/**
	 * Flag denoting whether to print debugging info related to picking
	 */
	static public boolean debugPick = false;

	/**
	 * Flag denoting whether to print debugging info related to spatial indexing.
	 */
	static public boolean debugSpatialIndexing = false;

	/**
	 * Flag denoting whether to print debugging info related to the frame rate.
	 */
	public static boolean debugPrintFrameRate = false;

	/**
	 * Flag denoting whether to print debugging info related to the memory usage.
	 */
	public static boolean debugPrintUsedMemory = false;

	/**
	 * Specifies the rate that totals should be calculated and printed to the console when
	 * debugPrintFrameRate or debugPrintUsedMemory are true.
	 */
	public static int printResultsFrameRate = 10;

	/**
	 * Tally of the number of ZNodes (that contain a visual) that
	 * have been painted
	 */
	static private int paintCount;

	private static long framesProcessed;
	private static long startProcessingOutputTime;
	private static long startProcessingInputTime;
	private static long processOutputTime;
	private static long processInputTime;

	/**
	 * Controls whether the bounds of each object should be drawn as a debugging aid.
	 * Caller must also specify the camera that should be used in computing how to display bounds.
	 * @param showBounds true to show bounds, or false to hide bounds
	 * @param camera The camera the bounds should be scaled for.
	 */
	static public void setShowBounds(boolean showBounds, ZCamera camera) {
		if (boundsCamera != null) {
			boundsCamera.repaint();
		}
		ZDebug.showBounds = showBounds;
		boundsCamera = camera;
		if (camera != null) {
			camera.repaint();
		}
	}

	/** Clears the paint count. The paint count is the number of nodes that painted
	 * themselves during the last render. This method should be called at the begining
	 * of each render.
	 *
	 * @see #incPaintCount
	 * @see #getPaintCount
	 */
	static public void clearPaintCount() {
		paintCount = 0;
	}

	/** Call this method whenever a node paints itself.
	 *
	 * @see #clearPaintCount
	 * @see #getPaintCount
	 */
	static public void incPaintCount() {
		paintCount++;
	}

	/**
	 * In general, both Swing and Jazz are not thread safe.  Generally,
	 * modifications to both the Swing component hierarchy and the Jazz
	 * scenegraph should not occur outside of the Java event dispatch
	 * thread.  Practically speaking, this means that if any thread other
	 * than the event dispatch thread needs to modify anything in a Swing
	 * component hierarchy or a Jazz scenegraph, they must do so in the
	 * event thread.  Swing provides two utilites to make calls on
	 * the event thread, called <code>invokeLater</code> and
	 * <code>invokeAndWait</code> (see javax.swing.SwingUtilities).
	 *
	 * Some common Jazz programming violations of this policy are modifying
	 * Jazz objects in an animation thread or modifying Jazz objects from the
	 * main thread (ie. public static void main(String[] main)).
	 * Modifications to the Jazz scenegraph <b>CAN</b> be made in a
	 * thread <b>BEFORE</b> the Window containing a ZCanvas is shown.
	 * Not coincidently, this is when events start being generated for a
	 * Jazz ZCanvas.
	 *
	 * For code that will modify a Jazz scenegraph, the following function
	 * reports whether the code has been called from the event thread.  This
	 * function is provided as a convenience to verify that Jazz scenegraph
	 * modifications are occuring on the appropriate thread.
	 *
	 * @see javax.swing.SwingUtilities#invokeLater
	 * @see javax.swing.SwingUtilities#invokeAndWait
	 * @see javax.swing.SwingUtilities#isEventDispatchThread
	 * @return Was this function called from the event dispatch thread?
	 */
	static public boolean isEventThread() {
		return SwingUtilities.isEventDispatchThread();
	}

	/** Returns the number of nodes that painted themselves during the last render.
	 *
	 * @see #clearPaintCount
	 * @see #incPaintCount
	 */
	static public int getPaintCount() {
		return paintCount;
	}

	public static void startProcessingOutput() {
		startProcessingOutputTime = System.currentTimeMillis();
	}

	public static void endProcessingOutput() {
		processOutputTime += (System.currentTimeMillis() - startProcessingOutputTime);
		framesProcessed++;

		if (ZDebug.debugPrintFrameRate) {
			if (framesProcessed % printResultsFrameRate == 0) {
				System.out.println(
					"Process output frame rate: " + getOutputFPS() + " fps");
				System.out.println(
					"Process input frame rate: " + getInputFPS() + " fps");
				System.out.println(
					"Total frame rate: " + getTotalFPS() + " fps");
				System.out.println();
				resetFPSTiming();
			}
		}

		if (ZDebug.debugPrintUsedMemory) {
			if (framesProcessed % printResultsFrameRate == 0) {
				System.out.println("Approximate used memory: " + getApproximateUsedMemory() / 1024 + " k");
			}
		}
	}

	public static void startProcessingInput() {
		startProcessingInputTime = System.currentTimeMillis();
	}

	public static void endProcessingInput() {
		processInputTime += (System.currentTimeMillis() - startProcessingInputTime);
	}

	/**
	 * Return how many frames are processed and painted per second. 
	 * Note that since piccolo doesn’t paint continuously this rate
	 * will be slow unless you are interacting with the system or have
	 * activities scheduled.
	 */
	public static double getTotalFPS() {
		if ((framesProcessed > 0)) {
			return 1000.0 / ((processInputTime + processOutputTime) / (double) framesProcessed);
		} else {
			return 0;
		}				
	}

	/**
	 * Return the frames per second used to process 
	 * input events and activities.
	 */
	public static double getInputFPS() {
		if ((processInputTime > 0) && (framesProcessed > 0)) {
			return 1000.0 / (processInputTime / framesProcessed);
		} else {
			return 0;
		}
	}

	/**
	 * Return the frames per seconds used to paint
	 * graphics to the screen.
	 */
	public static double getOutputFPS() {
		if ((processOutputTime > 0) && (framesProcessed > 0)) {
			return 1000.0 / (processOutputTime / framesProcessed);
		} else {
			return 0;
		}
	}

	/**
	 * Return the number of frames that have been processed since the last
	 * time resetFPSTiming was called.
	 */
	public long getFramesProcessed() {
		return framesProcessed;
	}

	/**
	 * Reset the variables used to track FPS. If you reset seldom they you will
	 * get good average FPS values, if you reset more often only the frames recorded
	 * after the last reset will be taken into consideration.
	 */
	public static void resetFPSTiming() {
		framesProcessed = 0;
		processInputTime = 0;
		processOutputTime = 0;
	}

	public static long getApproximateUsedMemory() {
		System.gc();
		System.runFinalization();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return totalMemory - free;
	}

	/**
	 * Used for dumping to a string buffer
	 */
	static private StringBuffer dumpBuffer = null;

	/**
	 * Debugging function to dump the scenegraph rooted at the specified node to stdout.
	 * It uses {@link ZSceneGraphObject#dump} to display each object, and descends the hierarchy.
	 * @param node The root of the subtree to display.
	 */
	static public void dump(ZNode node) {
		dumpBuffer = null; // use System.out instead
		dump(node, 0, true);
	}

	/**
	 * Debugging function to dump the scenegraph rooted at the specified node to a String.
	 * It uses {@link ZSceneGraphObject#dump} to display each object, and descends the hierarchy.
	 * @param obj The scenegraph object being dumped.
	 * @param includeChildren true if children nodes should be dumped.
	 */
	static public String dumpString(
		ZSceneGraphObject obj,
		boolean includeChildren) {
		dumpBuffer = new StringBuffer();
		dump(obj, 0, includeChildren);
		return dumpBuffer.toString();
	}

	static private void println(String line) {
		if (dumpBuffer != null) {
			dumpBuffer.append(line);
			dumpBuffer.append('\n');
		} else {
			System.out.println(line);
		}
	}

	static private void print(String str) {
		if (dumpBuffer != null) {
			dumpBuffer.append(str);
		} else {
			System.out.print(str);
		}
	}

	/**
	 * Internal method for dump(ZNode node).  This method handles pretty indenting
	 * of each level as it recurses down the tree.
	 * @param sgo the scenegraph object being dumped.
	 * @param level a count of recursion level for indenting.
	 * @param includeChildren true if children nodes should be dumped.
	 */
	static protected void dump(
		ZSceneGraphObject sgo,
		int level,
		boolean includeChildren) {
		int i;
		String space = "";

		for (i = 0; i < level; i++) {
			space = space.concat("    ");
		}
		dumpElement(space, "* ", sgo.dump());

		// Dump children of group nodes here so that we can indent them on display properly
		if (includeChildren && sgo instanceof ZGroup) {
			ZNode[] children = ((ZGroup) sgo).getChildren();
			if (children.length > 0) {
				println(space + "  - Children:      ");
				for (i = 0; i < children.length; i++) {
					ZNode child = children[i];
					if (child.getParent() != sgo) {
						println("");
						println(
							"WARNING: parent pointer of "
								+ child
								+ " not equal to "
								+ sgo);
						println(
							"WARNING: instead it is set to "
								+ child.getParent());
						println("");
					}
					dump(child, level + 1, includeChildren);
				}
			}
		}

		// Dump visual components of certain nodes here so that we can indent them on display properly
		ZVisualComponent vc = null;
		if (sgo instanceof ZVisualLeaf) {
			ZVisualLeaf vl = (ZVisualLeaf)sgo;
			if (vl.getNumVisualComponents() > 0) {
				ZVisualComponent[] vcs = vl.getVisualComponents();
				for (int j=0; i<vl.getNumVisualComponents(); i++) {
					vc = vcs[j];
					if (vc != null) {
						println(space + "  => Visual Component:      ");
						dump(vc, level + 1, includeChildren);
					}
				}
			}
		}
		if (sgo instanceof ZVisualGroup) {
			vc = ((ZVisualGroup) sgo).getFrontVisualComponent();
			if (vc != null) {
				println(space + "  => Front Visual Component:      ");
				dump(vc, level + 1, includeChildren);
			}
			vc = ((ZVisualGroup) sgo).getBackVisualComponent();
			if (vc != null) {
				println(space + "  => Back Visual Component:      ");
				dump(vc, level + 1, includeChildren);
			}
		}
	}

	/**
	 * Print the element for the scenegraph dump.
	 * Parse the element, and if there are any newlines, space out
	 * each line with the 'space' parameter.  Also, print the
	 * header for the first line, and a matching number of spaces for ensuing lines.
	 * @param space space to indent the header.
	 * @param origHeader text header for the first line.
	 */
	static protected void dumpElement(
		String space,
		String origHeader,
		String element) {
		boolean done = false;
		boolean newLine = true;
		String header = origHeader;
		StringReader reader = new StringReader(element);
		StreamTokenizer tokenizer = new StreamTokenizer(reader);

		tokenizer.eolIsSignificant(true);
		tokenizer.wordChars('.', '.');
		tokenizer.wordChars(',', ',');
		tokenizer.wordChars(':', ':');
		tokenizer.wordChars('=', '=');
		tokenizer.wordChars('@', '@');
		tokenizer.wordChars('(', '(');
		tokenizer.wordChars(')', ')');
		tokenizer.wordChars('[', '[');
		tokenizer.wordChars(']', ']');
		tokenizer.wordChars('\'', '\'');
		do {
			if (newLine) {
				print(space + header);
				// Replace header with a matching number spaces
				String temp = new String();
				for (int i = 0; i < origHeader.length(); i++) {
					temp = temp.concat(" ");
				}
				temp = temp.concat("- ");
				header = temp;
				newLine = false;
			}
			try {
				tokenizer.nextToken();
				switch (tokenizer.ttype) {
					case StreamTokenizer.TT_WORD :
						print(tokenizer.sval + " ");
						break;
					case StreamTokenizer.TT_NUMBER :
						print(tokenizer.nval + " ");
						break;
					case StreamTokenizer.TT_EOL :
						println("");
						newLine = true;
						break;
					case StreamTokenizer.TT_EOF :
						done = true;
						break;
				}
			} catch (IOException e) {
				println("");
				println(
					"Error parsing string while dumping scenegraph: "
						+ element);
				done = true;
			}
		} while (!done);
		println("");
	}
}
