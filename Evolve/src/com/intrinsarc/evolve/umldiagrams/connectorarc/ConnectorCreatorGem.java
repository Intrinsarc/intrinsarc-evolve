package com.intrinsarc.evolve.umldiagrams.connectorarc;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;

/**
 * @author andrew
 */
public class ConnectorCreatorGem implements Gem
{
  public static final String NAME = "Connector";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private int end1Index = -1;
  private int end2Index = -1;
  private boolean delegate;
  private boolean portLink;
  private boolean directed;
	
	public ConnectorCreatorGem()
	{
	}
	
	public ArcCreateFacet getArcCreateFacet()
	{
		return arcCreateFacet;
	}
	
	private class ArcCreateFacetImpl implements ArcCreateFacet
	{
	  public String getFigureName()
	  {
	    return ConnectorArcAppearanceGem.figureName;
	  }
	
	  public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints), properties.retrieve(">curved", false).asBoolean());
	  	ConnectorArcAppearanceGem connectorGem = new ConnectorArcAppearanceGem(
	  			new PersistentFigure(figureId, null, subject, properties));
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
	    connectorGem.connectFigureFacet(gem.getFigureFacet(), properties);
	    gem.connectClipboardActionsFacet(connectorGem.getClipboardCommandsFacet());
	    																					 
	    diagram.add(gem.getFigureFacet());
	  }
  	  
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}
	
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figure);
	  	ConnectorArcAppearanceGem connectorGem = new ConnectorArcAppearanceGem(figure); 
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
      connectorGem.connectFigureFacet(gem.getFigureFacet(), figure.getProperties());
      gem.connectClipboardActionsFacet(connectorGem.getClipboardCommandsFacet());
	  	
	  	return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.addIfNotThere(new PersistentProperty(">end1Index", end1Index, -1));
      properties.addIfNotThere(new PersistentProperty(">end2Index", end2Index, -1));
      properties.addIfNotThere(new PersistentProperty(">portLink", portLink, false));
      properties.addIfNotThere(new PersistentProperty(">delegate", delegate, false));
      properties.addIfNotThere(new PersistentProperty(">directed", directed, false));
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
	    // add to the list of required interfaces, if it isn't there already
	    CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
	    
	    // find a common owner
	    StructuredClassifier commonOwner = findCommonStructuredClassifier(
	    		points.getNode1().getFigureFacet(),
	    		points.getNode2().getFigureFacet());
	    
	    Connector connector = commonOwner.createOwnedConnector();
	    
	    // handle the start connectable
      ConnectorEnd end1 = makeConnectorEnd(connector, points.getNode1().getFigureFacet());
	    connector.getEnds().add(end1);
      ConnectorEnd end2 = makeConnectorEnd(connector, points.getNode2().getFigureFacet());
	    connector.getEnds().add(end2);

      // set the end indices
      int end1Value = properties.retrieve(">end1Index", -1).asInteger();
      if (end1Value != -1)
      {
        LiteralInteger lower = (LiteralInteger) end1.createLowerValue(UML2Package.eINSTANCE.getLiteralInteger());
        lower.setValue(end1Value);
      }
      int end2Value = properties.retrieve(">end2Index", -1).asInteger();
      if (end2Value != -1)
      {
        LiteralInteger lower = (LiteralInteger) end2.createLowerValue(UML2Package.eINSTANCE.getLiteralInteger());
        lower.setValue(end2Value);
      }
      boolean delegate = properties.retrieve(">delegate", false).asBoolean();
      if (delegate)
        connector.setKind(ConnectorKind.DELEGATION_LITERAL);

      boolean portLink = properties.retrieve(">portLink", false).asBoolean();
      if (portLink)
        connector.setKind(ConnectorKind.PORT_LINK_LITERAL);
      
      boolean directed = properties.retrieve(">directed", false).asBoolean();
      if (directed)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getConnector(), CommonRepositoryFunctions.CONNECTOR);
        connector.getAppliedBasicStereotypes().add(stereo);
        StereotypeUtilities.setBooleanRawStereotypeAttributeTransaction(
            connector, CommonRepositoryFunctions.DIRECTED, true);
      }

      return connector;
	  }

		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
			return acceptsOneOrBothAnchors(start, end);
		}

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
	}
	
  public static boolean acceptsOneOrBothAnchors(AnchorFacet start, AnchorFacet end)
  {
    // the start must be a connectable element
  	// the end, if it is set, must also be a connectable element
    // also, the connector's owning classifier must be editable in the current visual context
  	boolean startOk = start.getFigureFacet().getSubject() instanceof ConnectableElement;
  	FigureFacet startCls = findVisualOwningStructuredClassifierFigure(start.getFigureFacet());
  	
  	boolean endOk = end == null || end.getFigureFacet().getSubject() instanceof ConnectableElement;
  	FigureFacet endCls = null;
    boolean common = true;
  	if (endOk && end != null)
  	{
  	  endCls = findVisualOwningStructuredClassifierFigure(end.getFigureFacet());  	
  	  common = endCls == startCls;
  	}

  	return 
  	  startOk && !startCls.isSubjectReadOnlyInDiagramContext(false) &&
  	  (end == null || endOk && common && !endCls.isSubjectReadOnlyInDiagramContext(false));
  }  

	/**
   * find a common structured classifier for each connectable, by looking upwards to the owners of the ports
   * @param startConnectableElement
   * @param endConnectableElement
   * @return
   */
  public static StructuredClassifier findCommonStructuredClassifier(FigureFacet startFigure, FigureFacet endFigure)
	{
  	// find the parent visual parent of both, and start from there
  	StructuredClassifier start = findVisualOwningStructuredClassifier(startFigure);
  	StructuredClassifier end = findVisualOwningStructuredClassifier(endFigure);
  	
  	return start == end ? start : start;
	}

  /**
   * we are anticipating this is a port, so we need to look 2 levels up to get to the part or
   * to the class that visually owns the port.
   * if we have a part, find the owning classifier
   * if we have a class, just return this...
   * @param figure
   * @return
   */
	public static StructuredClassifier findVisualOwningStructuredClassifier(FigureFacet figure)
	{
	  Namespace element = GlobalSubjectRepository.repository.findVisuallyOwningNamespace(figure.getDiagram(), figure.getContainerFacet());
		// only return if the element is structured
		return element instanceof StructuredClassifier ? (StructuredClassifier) element : null;
	}
	
	public static FigureFacet findVisualOwningStructuredClassifierFigure(FigureFacet figure)
	{
	  while (figure != null)
	  {
	    if (figure.getSubject() instanceof StructuredClassifier)
	      return figure;
	    if (figure.getContainedFacet() == null || figure.getContainedFacet().getContainer() == null)
	      return null;
	    figure = figure.getContainedFacet().getContainer().getFigureFacet();
	  }
	  return null;
	}
	
	public static Element findVisualOwnerOfPort(FigureFacet figure)
	{
		if (figure == null)
			return null;
		ContainerFacet container1 = figure.getContainedFacet().getContainer();
		if (container1 == null)
			return null;
		ContainerFacet container2 = container1.getContainedFacet().getContainer();
		if (container2 == null)
			return null;
		Element element = (Element) container2.getFigureFacet().getSubject();
		
		// could be a property or a class
		return element;
	}
	
	public static ConnectorEnd makeConnectorEnd(Connector connector, FigureFacet figureFacet)
	{
		ConnectorEnd end = UML2Factory.eINSTANCE.createConnectorEnd();
		
		// possibly connected to a part?
		Element part = findVisualOwnerOfPort(figureFacet);
		Element port = ClassifierConstituentHelper.getOriginalSubject(figureFacet.getSubject()); 
		if (part instanceof Property)
		{
			Element actualPart = ClassifierConstituentHelper.getOriginalSubject(part);
			end.setPartWithPort((Property) actualPart);
			// must take possible port remap into account		
			DEPart depart = GlobalDeltaEngine.engine.locateObject(part).asConstituent().asPart();
			DEPort deport = GlobalDeltaEngine.engine.locateObject(port).asConstituent().asPort();
			end.setRole((ConnectableElement) depart.unRemap(deport).getRepositoryObject());
		}
		else
			// just have a port
			end.setRole((ConnectableElement) port);

		
		return end;
	}

  public void setEnd1Index(int end1Index)
  {
    this.end1Index = end1Index;
  }

  public void setEnd2Index(int end2Index)
  {
    this.end2Index = end2Index;
  }
  
  public void setDelegate(boolean delegate)
  {
    this.delegate = delegate;
  }

  public void setPortLink(boolean portLink)
  {
    this.portLink = portLink;
  }
  
  public void setDirected(boolean directed)
  {
  	this.directed = directed;
  }
}
