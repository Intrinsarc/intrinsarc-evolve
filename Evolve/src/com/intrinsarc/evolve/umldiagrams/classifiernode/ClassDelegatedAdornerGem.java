package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.deltaview.*;
import com.intrinsarc.evolve.umldiagrams.constituenthelpers.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class ClassDelegatedAdornerGem
{
  private DelegatedDeltaAdornerFacet adorner = new DelegatedDeltaAdornerFacetImpl();
  private FigureFacet cls;
  private FigureFacet attributes;
  private FigureFacet operations;
  private FigureFacet ports;
  private FigureFacet parts;
  
  public ClassDelegatedAdornerGem(FigureFacet cls, FigureFacet attributes, FigureFacet operations, FigureFacet ports, FigureFacet parts)
  {
    this.cls = cls;
    this.attributes = attributes;
    this.operations = operations;
    this.ports = ports;
    this.parts = parts;
  }
  
  public Facet getDelegatedDeltaAdornerFacet()
  {
    return adorner;
  }

  private class DelegatedDeltaAdornerFacetImpl implements DelegatedDeltaAdornerFacet
  {
    public Map<FigureFacet, Integer> getDeltaDisplaysAtHome()
    {
      Map<FigureFacet, Integer> displays = new HashMap<FigureFacet, Integer>();
      Class subject = (Class) cls.getSubject();
      
      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          attributes.getContainerFacet().getContents(),
          subject.undeleted_getOwnedAttributes(),
          subject.undeleted_getDeltaDeletedAttributes(),
          subject.undeleted_getDeltaReplacedAttributes());
          
      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          parts.getContainerFacet().getContents(),
          subject.undeleted_getOwnedAttributes(),
          subject.undeleted_getDeltaDeletedAttributes(),
          subject.undeleted_getDeltaReplacedAttributes());
          
      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          operations.getContainerFacet().getContents(),
          subject.undeleted_getOwnedOperations(),
          subject.undeleted_getDeltaDeletedOperations(),
          subject.undeleted_getDeltaReplacedOperations());

      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          ports.getContainerFacet().getContents(),
          subject.undeleted_getOwnedPorts(),
          subject.undeleted_getDeltaDeletedPorts(),
          subject.undeleted_getDeltaReplacedPorts());
      
      // find all the connectors for this class
      Set<FigureFacet> connectorFigures = ClassConnectorHelper.findConnectors(ports, parts, false);
      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          connectorFigures.iterator(),
          subject.undeleted_getOwnedConnectors(),
          subject.undeleted_getDeltaDeletedConnectors(),
          subject.undeleted_getDeltaReplacedConnectors());
      
      // find all the port links for this class
      Set<FigureFacet> portLinkFigures = ClassConnectorHelper.findConnectors(ports, parts, true);
      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          portLinkFigures.iterator(),
          subject.undeleted_getOwnedConnectors(),
          subject.undeleted_getDeltaDeletedConnectors(),
          subject.undeleted_getDeltaReplacedConnectors());
      
      // find all the trace links for this class
      List<FigureFacet> figures = new ArrayList<FigureFacet>();
      for (Iterator<LinkingFacet> iter = cls.getAnchorFacet().getLinks(); iter.hasNext();)
      {
      	LinkingFacet link = iter.next();
      	Object dep = link.getFigureFacet().getSubject();
      	if (link.getAnchor1().getFigureFacet() == cls && dep instanceof Dependency && ((Dependency) dep).isTrace())
      		figures.add(link.getFigureFacet());
      }

	    InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          figures.iterator(),
          subject.undeleted_getOwnedAnonymousDependencies(),
          subject.undeleted_getDeltaDeletedTraces(),
          subject.undeleted_getDeltaReplacedTraces());
      
      return displays;
    }
  }
  
  public static Set<FigureFacet> findPartPorts(FigureFacet part)
  {
    // look 2 deep
    Set<FigureFacet> figures = new HashSet<FigureFacet>();
    ClassConnectorHelper.collectAtDepth(figures, part, 2);    

    Set<FigureFacet> ports = new HashSet<FigureFacet>();
    for (FigureFacet figure : figures)
      if (figure.getSubject() instanceof Port)
        ports.add(figure);

    return ports;
  }
}
