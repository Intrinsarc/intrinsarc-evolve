package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

class PortProximity
{
  private double x;
  private double y;
  private Port port;
  
  public PortProximity(FigureFacet portFigure, Port port, UBounds partBounds)
  {
    this.port = port;
    UBounds portBounds = portFigure.getFullBounds();
    UDimension middle = portBounds.getMiddlePoint().subtract(partBounds.getPoint());
    x = middle.getWidth() / partBounds.getWidth();
    y = middle.getHeight() / partBounds.getHeight();
  }
  
  public Port getPort()
  {
    return port;
  }
  
  public double computeDistance(PortProximity replacedProx)
  {
    return Math.sqrt(square(x - replacedProx.x) + square(y - replacedProx.y));
  }
  
  private double square(double val)
  {
    return val * val;
  }
}

public class PortRemapper
{
  private FigureFacet classifier;
  private FigureFacet otherPart;
  private FigureFacet replacedPart;

  public PortRemapper(FigureFacet classifier, FigureFacet otherPart, FigureFacet replacedPart)
  {
    this.classifier = classifier;
    this.otherPart = otherPart;
    this.replacedPart = replacedPart;
  }

  public List<PortRemap> remapPortsBasedOnProximity()
  {
    // find the perspective
    Package stratum = GlobalSubjectRepository.repository.findVisuallyOwningStratum(classifier.getDiagram(), classifier.getContainerFacet());
    DEStratum perspective = GlobalDeltaEngine.engine.locateObject(stratum).asStratum();
    
    // find the ports for each and give them a percentage rating
    List<PortProximity> otherPorts = formProximity(perspective, otherPart);
    List<PortProximity> replacedPorts = formProximity(perspective, replacedPart);
    InstanceSpecification spec = UMLTypes.extractInstanceOfPart((Property) otherPart.getSubject());
    
    List<PortRemap> remaps = new ArrayList<PortRemap>();
    for (;;)
    {
      // look for a close match with the replacedports
      PortProximity match = null;
      PortProximity bestReplaced = null;
      double dist = 1e7;
      
	    for (PortProximity replacedProx : replacedPorts)
	    {
	      // find the minimum distance
	      for (PortProximity otherProx : otherPorts)
	      {
	        double apart = otherProx.computeDistance(replacedProx);
	        if (apart < dist)
	        {
	        	bestReplaced = replacedProx;
	          match = otherProx;
	          dist = apart;
	        }
	      }	      
	    }
	    
      // create a remap for this match, and remove both ports from the lists
      if (match != null)
      {
        otherPorts.remove(match);
        replacedPorts.remove(bestReplaced);
        
        DEPart actual = GlobalDeltaEngine.engine.locateObject(replacedPart.getSubject()).asConstituent().asPart();
        Port next = match.getPort();
        // ouch...
        Port best = (Port) ClassifierConstituentHelper.getOriginalSubject(bestReplaced.getPort());
        Port orig = (Port) actual.unRemap(GlobalDeltaEngine.engine.locateObject(best).asConstituent().asPort()).getRepositoryObject();
        
        if (next != orig)
        {
	        // only need to create a remap if these are different
	        PortRemap remap = spec.createPortRemap();
	        remap.setOriginalPort(orig);
	        remap.setNewPort(next);
	        remaps.add(remap);
        }
      }
      else
      	break;
    }
    
    return remaps;
  }

  private List<PortProximity> formProximity(DEStratum perspective, FigureFacet part)
  {
    Set<FigureFacet> ports = ClassDelegatedAdornerGem.findPartPorts(part);
    DEPart actual = GlobalDeltaEngine.engine.locateObject(part.getSubject()).asConstituent().asPart();
    Set<DeltaPair> pairs = actual.getPortsAfterRemap(perspective); 

    List<PortProximity> proxes = new ArrayList<PortProximity>();
    UBounds partBounds = part.getFullBounds();
    for (FigureFacet port : ports)
    {
      // find the original
      for (DeltaPair pair : pairs)
      {
        if (pair.getConstituent().getRepositoryObject() == port.getSubject())
        {
          PortProximity prox = new PortProximity(
              port,
              (Port) pair.getConstituent().getRepositoryObject(),
              partBounds);
          proxes.add(prox);
          break;
        }
      }
    }
    
    return proxes;
  }
}
