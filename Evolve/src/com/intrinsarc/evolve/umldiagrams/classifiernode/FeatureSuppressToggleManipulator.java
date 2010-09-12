/**
 * FeatureSuppressToggleManipulator.java
 * 
 * @author Andrew McVeigh
 */

package com.intrinsarc.evolve.umldiagrams.classifiernode;

import org.eclipse.uml2.*;

import com.intrinsarc.evolve.umldiagrams.featurenode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public class FeatureSuppressToggleManipulator extends ManipulatorAdapter
{
  private ZGroup diagramLayer;
  private ZGroup group;
  private int featureType;
  private boolean featureSuppressed;
  private ManipulatorListenerFacet listener;
  private ManipulatorListenerFacet appListener;
  private UPoint location;
  private int size;

  public FeatureSuppressToggleManipulator(FigureReference reference, String name, UPoint location, int size,
      int featureType, String featureName, boolean featureSuppressed)
  {
    this.featureType = featureType;
    this.featureSuppressed = featureSuppressed;
    this.location = location;
    this.size = size;
  }

  /** add the view to the display */
  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
  {
    this.diagramLayer = diagramLayer;

    ZNode icon = FeatureNodeGem.makeIcon(
        featureSuppressed ? VisibilityKind.PUBLIC_LITERAL : VisibilityKind.PRIVATE_LITERAL,
        false,
        location,
        size,
        null,
        featureType);
    group = new ZGroup();
    group.addChild(icon);

    group.putClientProperty("manipulator", this);
    group.setChildrenPickable(false);
    group.setChildrenFindable(false);

    diagramLayer.addChild(group);
  }

  /**
   * Method cleanUp
   *  
   */
  public void cleanUp()
  {
    diagramLayer.removeChild(group);
  }

  public int getType()
  {
    return TYPE2;
  }

  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
  {
    this.listener = listener;
  }

  public void setAppListener(ManipulatorListenerFacet listener)
  {
    this.appListener = listener;
  }

  /** Invoked when a mouse button has been released on this figure */
  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
  {
    listener.haveFinished();
    if (appListener != null)
    	appListener.haveFinished();
  }

  public void setLayoutOnly()
  {
  }

	public boolean handlesReadonly()
	{
		return false;
	}
}