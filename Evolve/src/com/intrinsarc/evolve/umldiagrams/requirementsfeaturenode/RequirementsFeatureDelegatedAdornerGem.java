package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.deltaview.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.evolve.umldiagrams.constituenthelpers.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class RequirementsFeatureDelegatedAdornerGem
{
  private DelegatedDeltaAdornerFacet adorner = new DelegatedDeltaAdornerFacetImpl();
  private FigureFacet cls;
  
  public RequirementsFeatureDelegatedAdornerGem(FigureFacet cls)
  {
    this.cls = cls;
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
      RequirementsFeature subject = (RequirementsFeature) cls.getSubject();
      
      // get all of the features linking to this
      List<FigureFacet> figures = new ArrayList<FigureFacet>();
      for (Iterator<LinkingFacet> iter = cls.getAnchorFacet().getLinks(); iter.hasNext();)
      {
      	LinkingFacet link = iter.next();
      	if (link.getAnchor1().getFigureFacet() == cls)
      		figures.add(link.getFigureFacet());
      }

      InterfaceDelegatedAdornerGem.determineAdornments(
          displays,
          cls,
          figures.iterator(),
          subject.undeleted_getSubfeatures(),
          subject.undeleted_getDeltaDeletedSubfeatures(),
          subject.undeleted_getDeltaReplacedSubfeatures());
      
      // possibly add a replacement adorner
      InterfaceDelegatedAdornerGem.addReplacementAdorner(cls, displays);      
      
      return displays;
    }
  }
}
