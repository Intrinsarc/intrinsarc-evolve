package com.hopstepjump.idraw.foundation;

import java.awt.event.*;

import com.hopstepjump.gem.*;

import edu.umd.cs.jazz.*;


/**
 * Tool is an interactive device, which allows the user to generate a command interactively.
 * Needs access to the diagram view, for the selection and sweep layers
 */

public interface ToolFacet extends Facet
{
  public boolean isActive();
  public void activate(DiagramViewFacet diagramView, ZNode mouseNode, ToolCoordinatorFacet coordinator);
  public void deactivate();

  public boolean wantsKey(KeyEvent event);
  public void keyPressed(KeyEvent event);
  public void keyTyped(KeyEvent event);
  public void keyReleased(KeyEvent event);
  public ArcAcceptanceFacet getPossibleArcAcceptance();
}