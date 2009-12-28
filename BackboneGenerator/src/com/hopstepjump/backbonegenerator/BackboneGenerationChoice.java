package com.hopstepjump.backbonegenerator;

import java.io.*;
import java.util.*;
import java.util.regex.*;

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
  private Pattern topLevelPattern = Pattern.compile("\\s*((?:\\w+\\:\\:)+)(\\w+)\\.(\\w+)\\s*");
  private FigureFacet runComment;
  private List<FigureFacet> selected;
  private DiagramViewFacet diagramView;
  private Package singleStratum;
  private Class singleComponent;
  private List<String> profile;
  
  public BackboneGenerationChoice(ToolCoordinatorFacet toolCoordinator)
  {
    diagramView = toolCoordinator.getCurrentDiagramView();
    locateChosenFigures();
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
  
  public Class getSingleComponent()
  {
  	return singleComponent;
  }
  
  /**
   * get any directly linked packages
   * @return
   */
  public List<DEStratum> extractDirectlyLinkedStrata()
  {
  	IDeltaEngine engine = GlobalDeltaEngine.engine;
    List<DEStratum> chosen = new ArrayList<DEStratum>();
  	
    // if a single stratum is selected, use this
  	if (singleStratum != null)
  	{
  		chosen.add(engine.locateObject(singleStratum).asStratum());
  		return chosen;  		
  	}
    
    // if this is a comment then get the links and follow from there
    // otherwise just take the previously selected elements
    if (runComment != null)
    {
      for (Iterator<LinkingFacet> iter = runComment.getAnchorFacet().getLinks(); iter.hasNext();)
      {
        LinkingFacet linking = iter.next();
        AnchorFacet other =
          linking.getAnchor1() == runComment.getAnchorFacet() ? linking.getAnchor2() : linking.getAnchor1();
        DEObject pkg = engine.locateObject(other.getFigureFacet().getSubject());
        if (pkg != null && pkg.asStratum() != null)
        	chosen.add(pkg.asStratum());
      }
    }
    else
    {
    	for (FigureFacet figure : selected)
    	{
        DEObject pkg = engine.locateObject(figure);
        if (pkg.asStratum() != null)
        	chosen.add(pkg.asStratum());
    	}
    }
    return chosen;
  }
  
  public List<DEStratum> extractStrata(String failureMessage) throws BackboneGenerationException
  {
  	IDeltaEngine engine = GlobalDeltaEngine.engine;
  	
    // if a single stratum is selected, use this
  	if (singleStratum != null)
  	{
  		DEStratum single = engine.locateObject(singleStratum).asStratum(); 
  		return single.determineOrderedPackages(false);
  	}
  	
    List<FigureFacet> chosen = new ArrayList<FigureFacet>();
    
    // if this is a comment then get the links and follow from there
    // otherwise just take the previously selected elements
    if (runComment != null)
    {
      for (Iterator<LinkingFacet> iter = runComment.getAnchorFacet().getLinks(); iter.hasNext();)
      {
        LinkingFacet linking = iter.next();
        AnchorFacet other =
          linking.getAnchor1() == runComment.getAnchorFacet() ? linking.getAnchor2() : linking.getAnchor1();
        chosen.add(other.getFigureFacet());
      }
    }
    else
      chosen.addAll(selected);

    // use the figures to find the set of stratum to generate for
    return DEStratum.determineOrderedPackages(visuallyDiveForStrata(chosen, failureMessage), false);
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
  	
    if (runComment == null)
      throw new BackboneGenerationException("A comment with run parameters must be tagged before running", null);
    
    Comment comment = (Comment) runComment.getSubject(); 
    String body = comment.getBody();
    BufferedReader reader = new BufferedReader(new StringReader(body));
    try
    {    
      String runLine = reader.readLine();
      if (!runLine.equals("run:"))
        throw new BackboneGenerationException("First line of run text must be 'run:'", comment);
      
      // get the toplevel component and the port
      Matcher matcher = topLevelPattern.matcher(reader.readLine());
      if (!matcher.matches())
        throw new BackboneGenerationException("Second line of run text must be in the form '(stratum::)+topLevelComponent.portName'", comment);
      
      String stratum = matcher.group(1);
      if (stratum.endsWith("::"))
      	stratum = stratum.substring(0, stratum.length() - 2);
      String topLevelComponent = matcher.group(2);
      String port = matcher.group(3);
      return new String[]{stratum, topLevelComponent, port};
    }
    catch (IOException e)
    {
      try
      {
        reader.close();
      }
      catch (IOException e1)
      {
      }
      throw new BackboneGenerationException("IOException while reading run body...", comment);
    }
  }

  public DEStratum extractSingleStratum() throws BackboneGenerationException
  {
		if (extractDirectlyLinkedStrata().size() != 1)
			throw new BackboneGenerationException("A single top stratum muse be tagged for hardcoded generation", null);
		return extractDirectlyLinkedStrata().get(0);
  }
  
  public List<String> getGenerationProfile() throws BackboneGenerationException
  {
  	if (profile == null)
  	{
  		profile = new ArrayList<String>();
  		if (extractDirectlyLinkedStrata().size() != 1)
  			throw new BackboneGenerationException("A single top stratum muse be tagged for hardcoded generation", null);
  	
  		// get the generation profile
  		String raw = StereotypeUtilities.extractStringProperty((Element) extractDirectlyLinkedStrata().get(0).getRepositoryObject(), CommonRepositoryFunctions.GENERATION_PROFILE);
  		if (raw == null)
  			raw = "";
  		StringTokenizer tok = new StringTokenizer(raw);
  		while (tok.hasMoreTokens())
  			profile.add(tok.nextToken().trim().toLowerCase());
  	}
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
    FigureFacet figure = selection.getFirstSelectedFigure();
    boolean haveComment = figure != null && figure.getSubject() instanceof Comment;
    
    // save either the run comment or the selection
    if (haveComment)
      runComment = figure;
    else
    {
      selected = new ArrayList<FigureFacet>();
      for (FigureFacet sel : selection.getSelectedFigures())
        selected.add(sel);
    }
  }
  
  private List<DEStratum> visuallyDiveForStrata(List<FigureFacet> chosen, String failureMessage) throws BackboneGenerationException
  {
    // should have one or more groupers selected
    List<DEStratum> selectedStrata = new ArrayList<DEStratum>();
    for (FigureFacet figure : chosen)
      visuallyDiveDownToFindStrata(selectedStrata, figure);
   
    // strata shouldn't be empty at this stage
    if (selectedStrata.isEmpty())
      throw new BackboneGenerationException(failureMessage, null);
  
    return selectedStrata;
  }
  
  private void visuallyDiveDownToFindStrata(List<DEStratum> selectedStrata, FigureFacet figure) throws BackboneGenerationException
  {
    Package stratum = extractStratum(figure.getSubject());
    if (stratum != null)
      selectedStrata.add(GlobalDeltaEngine.engine.locateObject(stratum).asStratum());
    
    // get the contents of the figure
    if (figure.getContainerFacet() != null)
      for (Iterator<FigureFacet> cIter = figure.getContainerFacet().getContents(); cIter.hasNext();)
      {
        FigureFacet possible = cIter.next();
        visuallyDiveDownToFindStrata(selectedStrata, possible);
      }   
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
}
