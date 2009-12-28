package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

class PortProximity
{
  private double x;
  private double y;
  private Port port;
  
  public PortProximity(FigureFacet portFigure, Port port, UBounds partBounds)
  {
    this.port = port;
    UBounds portBounds = portFigure.getFullBounds();
    UDimension middle = portBounds.getMiddlePoint().subtract(partBounds.getTopLeftPoint());
    x = middle.getWidth() / partBounds.getWidth();
    y = middle.getHeight() / partBounds.getHeight();
  }
  
  public Port getPort()
  {
    return port;
  }
  
  public double computeDistance(PortProximity replacedProx)
  {
    return square(x - replacedProx.x) + square(y - replacedProx.y);
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
    for (PortProximity replacedProx : replacedPorts)
    {
      // look for a close match with the replacedports
      PortProximity match = null;
      double dist = 1e7;
      
      // find the minimum distance
      for (PortProximity otherProx : otherPorts)
      {
        double apart = otherProx.computeDistance(replacedProx);
        if (apart < dist)
        {
          match = otherProx;
          dist = apart;
        }
      }
      
      // create a remap for this match, and remove both ports from the lists
      if (match != null)
      {
        otherPorts.remove(match);
        
        // only need to create a remap if these are different
        if (match.getPort() != replacedProx.getPort())
        {
          PortRemap remap = spec.createPortRemap();
          GlobalSubjectRepository.repository.incrementPersistentDelete(remap);
          remap.setOriginalPort(replacedProx.getPort());
          remap.setNewPort(match.getPort());
          remaps.add(remap);
        }
      }
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
              port, (Port) pair.getOriginal().getRepositoryObject(),
              partBounds);
          proxes.add(prox);
          break;
        }
      }
    }
    
    return proxes;
  }
}
