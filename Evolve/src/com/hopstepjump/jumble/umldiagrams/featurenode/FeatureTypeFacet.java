package com.hopstepjump.jumble.umldiagrams.featurenode;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 01-Aug-02
 *
 */
public interface FeatureTypeFacet extends Facet
{
	public int getFeatureType();
	public String getFigureName();
	
  /**
   * the short name is the elided form of the entire operation name
   */
  public String makeShortName(String name);
  public String makeNameFromSubject();
  
  public String setText(String text, Object listSelection);
  public void performPostContainerDropTransaction();
  public Command generateDeleteDelta(ToolCoordinatorFacet coordinator, Classifier owner);
  public JMenuItem getReplaceItem(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);
  
  public boolean isSubjectReadOnlyInDiagramContext(boolean kill);
	public JList formSelectionList(String textSoFar);
}
