package com.hopstepjump.backbone.generator;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

/**
 * encodes the choice of either a run comment, and linked stratum,
 * or a selection of stratum only for generation
 * @author andrew
 */
public class BackboneGenerationChoice
{
  private List<FigureFacet> selected;
  private DiagramViewFacet diagramView;
  private Package singleStratum;
  private Class singleComponent;
  
  public BackboneGenerationChoice(ToolCoordinatorFacet toolCoordinator)
  {
    diagramView = toolCoordinator.getCurrentDiagramView();
    locateChosenFigures();
  }
  
  public DEStratum getSingleStratum() throws BackboneGenerationException
  {
  	if (singleStratum == null)
  		throw new BackboneGenerationException("A single stratum must be tagged first", null);
  	IDeltaEngine engine = GlobalDeltaEngine.engine;
  	return engine.locateObject(singleStratum).asStratum();
  }
  
  /**
   * find all related strata
   * @return
   * @throws BackboneGenerationException 
   */
  public List<DEStratum> extractRelatedStrata() throws BackboneGenerationException
  {
		List<DEStratum> st = new ArrayList<DEStratum>();
		st.add(getSingleStratum());
		st = DEStratum.determineOrderedPackages(st, false);
		Collections.reverse(st);
		return st;
  }
  
  /**
   * this is used to select a single component for generation.
   * the insides are adjusted so that the run parameters are set to the selected component
   * and the 
   * @return
   * @throws BackboneGenerationException
   */
  public void adjustSelectionForProtocolAnalysis() throws BackboneGenerationException
  {
  	// should have a single component selected
  	FigureFacet figure = diagramView.getSelection().getSingleSelection();
  	if (figure == null || figure.getSubject().getClass() != ClassImpl.class)
  		throw new BackboneGenerationException("A single component must be selected", null);
  	
  	// now, traverse up until we find the first owning stratum
  	for (Element element = (Element) figure.getSubject(); element != null; element = element.getOwner())
  	{
  		Package possible = extractStratum(element);
  		if (possible != null)
  		{
  			selected.clear();
  			singleStratum = possible;
  			singleComponent = (Class) figure.getSubject();
  			return;
  		}
  	}
  	
  	// if we got here, no stratum have been found
  	throw new BackboneGenerationException("Component is not contained within a stratum", null);
  }
  
  public DEComponent getSingleComponent()
  {
  	IDeltaEngine engine = GlobalDeltaEngine.engine;
  	return engine.locateObject(singleComponent).asComponent();
  }
  
	/**
   * extract the stratum, component and port to run
   * @return
   * @throws BackboneGenerationException 
   */
  public String[] extractRunParameters() throws BackboneGenerationException
  {
  	// if we have a component, return all the parameters
  	if (singleComponent != null)
  		return new String[] {singleStratum.getName(), singleComponent.getName(), "run"};
  	
    if (singleStratum == null)
      throw new BackboneGenerationException("A single stratum must be tagged before running", null);
    
    String stratum = StereotypeUtilities.extractStringProperty(singleStratum, CommonRepositoryFunctions.BACKBONE_RUN_STRATUM);
    if (stratum == null || stratum.isEmpty())
    	stratum = singleStratum.getName();
    String component = StereotypeUtilities.extractStringProperty(singleStratum, CommonRepositoryFunctions.BACKBONE_RUN_COMPONENT);
    if (component == null || component.isEmpty())
      throw new BackboneGenerationException(CommonRepositoryFunctions.BACKBONE_RUN_COMPONENT + " must be set to an existing component", null);
    String port = StereotypeUtilities.extractStringProperty(singleStratum, CommonRepositoryFunctions.BACKBONE_RUN_PORT);
    return new String[]{stratum, component, port};
  }

  public List<String> getGenerationProfile() throws BackboneGenerationException
  {
		List<String> profile = new ArrayList<String>();
		if (singleStratum == null)
			throw new BackboneGenerationException("A single top stratum muse be tagged for hardcoded generation", null);
	
		// get the generation profile
		String raw = StereotypeUtilities.extractStringProperty(singleStratum, CommonRepositoryFunctions.GENERATION_PROFILE);
		if (raw == null)
			raw = "";
		StringTokenizer tok = new StringTokenizer(raw);
		while (tok.hasMoreTokens())
			profile.add(tok.nextToken().trim().toLowerCase());
  	return profile;
  }

  
  ///////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////
  
  private void locateChosenFigures()
  {
    // find a possible single comment selection, and look for links to contained elements
    SelectionFacet selection = diagramView.getSelection();
    
    selected = new ArrayList<FigureFacet>();
    for (FigureFacet sel : selection.getSelectedFigures())
      selected.add(sel);
  }
  
  private Package extractStratum(Object subject)
  {
    if (!(subject instanceof Package))
      return null;
    Package pkg = (Package) subject;
    if (StereotypeUtilities.isStereotypeApplied(pkg, CommonRepositoryFunctions.STRATUM))
      return pkg;
    return null;
  }

	public void selectSingleStratum(Package pkg) throws BackboneGenerationException
	{
  	// should have a single stratum selected
  	if (pkg == null || extractStratum(pkg) == null)
  		throw new BackboneGenerationException("A single stratum must be selected", null);
  	singleStratum = pkg;
	}
}
