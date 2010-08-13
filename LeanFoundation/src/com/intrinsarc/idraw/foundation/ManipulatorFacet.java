package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


/**
 * this is a figure which performs direct manipulation on a diagram.
 * It produces a command on mouse release.
 */
public interface ManipulatorFacet extends Facet
{
  public static final int TYPE0 = 0;  // appears on screen in editing mode straight away
  public static final int TYPE1 = 1;  // can appear on screen and then be manipulated in 1 go.  Used for arc adjust manipulator
  public static final int TYPE2 = 2;  // if it is on screen, will be used with no questions
  public static final int TYPE3 = 3;  // if it is on screen, will only be used if the mouse is pressed and released on it.

  /** returns true if the manipulator area is direct */
  public int getType();
  /** set layout only, for safe operations */
  public void setLayoutOnly();

  /** entry functions -- called to start manipulation */
  public boolean wantToEnterViaKeyPress(char keyPressed);
  /** invoked on key entry */
  public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed);
  /** invoked on button press entry */
  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers);
  /** invoked on button release entry */
  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers);
	/** invoked only on type 0 manipulators */
	public void enterImmediately(ManipulatorListenerFacet listener);

  /** event functions -- invoked when an event occurs */
  /** invoked when a key has been pressed */
  public void keyPressed(char keyPressed);
  /**Invoked when a mouse button has been pressed on this figure */
  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event);
  /** invoked when a mouse button has been dragged on this figure */
  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event);
  /** invoked when a mouse button has been moved on this figure */
  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event);
  /**Invoked when a mouse button has been released on this figure */
  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event);

  /** add the view to the display */
  public void addToView(ZGroup diagramLayer, ZCanvas canvas);
  public void cleanUp();
}