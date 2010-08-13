package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.evolve.deltaview.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;

public class InterfaceDelegatedAdornerGem
{
  private DelegatedDeltaAdornerFacet adorner = new DelegatedDeltaAdornerFacetImpl();
  private FigureFacet iface;
  private FigureFacet attributes;
  private FigureFacet operations;
  
  public InterfaceDelegatedAdornerGem(FigureFacet iface, FigureFacet attributes, FigureFacet operations)
  {
    this.iface = iface;
    this.attributes = attributes;
    this.operations = operations;
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
      Interface subject = (Interface) iface.getSubject();
      
      determineAdornments(
          displays,
          iface,
          attributes.getContainerFacet().getContents(),
          subject.undeleted_getOwnedAttributes(),
          subject.undeleted_getDeltaDeletedAttributes(),
          subject.undeleted_getDeltaReplacedAttributes());
          
      determineAdornments(
          displays,
          iface,
          operations.getContainerFacet().getContents(),
          subject.undeleted_getOwnedOperations(),
          subject.undeleted_getDeltaDeletedOperations(),
          subject.undeleted_getDeltaReplacedOperations());
                
      return displays;
    }
  }
  
  public static void determineAdornments(
      Map<FigureFacet, Integer> displays,
      FigureFacet classifier,
      Iterator<FigureFacet> contents,
      ArrayList<Element> adds,
      ArrayList<DeltaDeletedConstituent> deletes,
      ArrayList<DeltaReplacedConstituent> replacements)
  {
    Set<Element> replaced = new HashSet<Element>();
    for (DeltaReplacedConstituent constituent : replacements)
      replaced.add(constituent.getReplacement());
    
    while (contents.hasNext())
    {
      FigureFacet content = contents.next();
      Object subject = content.getSubject();
      
      // if this has been added, then indicate so...
      if (adds.contains(subject))    
        displays.put(content, DeltaTypeEnum.DELTA_ADD.ordinal());
      
      // if this has been replaced, indicate so...
      if (replaced.contains(subject))
        displays.put(content, DeltaTypeEnum.DELTA_REPLACE.ordinal());
    }
    
    // if some deletes exist, add this with a delete
    if (!deletes.isEmpty())
      displays.put(classifier, DeltaTypeEnum.DELTA_DELETE.ordinal());
  }
}
