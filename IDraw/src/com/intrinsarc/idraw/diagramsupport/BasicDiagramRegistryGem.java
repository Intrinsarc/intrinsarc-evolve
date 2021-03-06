package com.intrinsarc.idraw.diagramsupport;

import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class BasicDiagramRegistryGem implements Gem
{
  private int maxUnmodifiedUnviewedDiagrams;
	private java.util.Map<DiagramReference, DiagramFacet> diagrams = new HashMap<DiagramReference, DiagramFacet>();
	private DiagramRegistryFacet registryFacet = new DiagramRegistryFacetImpl();
	private BasicDiagramRecreatorFacet recreatorFacet;
	
  public BasicDiagramRegistryGem(int maxUnmodifiedUnviewedDiagrams)
  {
    this.maxUnmodifiedUnviewedDiagrams = maxUnmodifiedUnviewedDiagrams;
  }

  public void connectBasicDiagramRecreatorFacet(BasicDiagramRecreatorFacet recreatorFacet)
  {
    this.recreatorFacet = recreatorFacet;
  }
  
	public DiagramRegistryFacet getDiagramRegistryFacet()
	{
		return registryFacet;
	}
	
	private class DiagramRegistryFacetImpl implements DiagramRegistryFacet
	{
	  public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference)
	  {
	    return retrieveOrMakeDiagram(reference, true);
	  }
	
    public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference, boolean forceToNotModified)
    {
      // see if we have this already
      DiagramFacet lruDiagram = null;
      for (DiagramFacet diagram : diagrams.values())
        if (diagram.getDiagramReference().equals(reference))
        {
          lruDiagram = diagram;
          break;
        }
      
      if (lruDiagram == null)
      {
        try
        {
          lruDiagram = recreatorFacet.recreateAndRegisterDiagram(reference, diagrams, null);
        }
        catch (DiagramRecreationException ex)
        {
          System.err.println("$$ got a recreation exception when regenerating diagram: " + ex);
        }
        update(lruDiagram, forceToNotModified);
      }
      
      lruDiagram.setLRUTime(System.currentTimeMillis());    

      return lruDiagram;
    }
    
    public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference, DiagramFacet source, Object perspective, DiagramPostProcessor postProcessor)
    {
      // see if we have this already
      DiagramFacet lruDiagram = null;
      for (DiagramFacet diagram : diagrams.values())
        if (diagram.getDiagramReference().equals(reference))
        {
          lruDiagram = diagram;
          break;
        }
      
      if (lruDiagram == null)
      {
        try
				{
					lruDiagram = recreatorFacet.recreateAndRegisterDiagram(reference, diagrams, source, perspective, postProcessor);
				}
        catch (DiagramRecreationException ex)
				{
          System.err.println("$$ got a recreation exception when regenerating diagram: " + ex);
				}
  
        // make sure that we handles any needed changes to this diagram
        for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
          lruDiagram.formViewUpdate(pass, true);

        // make sure this is shown as unmodified
        lruDiagram.resetModified();
      }      
      lruDiagram.setLRUTime(System.currentTimeMillis());
      
      return lruDiagram;
    }
    
    public DiagramFacet retrieveOrMakeClipboardDiagram(DiagramReference reference)
    {
      DiagramFacet diagram = diagrams.get(reference);
      if (diagram == null)
      {
        BasicDiagramGem gem = new BasicDiagramGem(reference, true, null, false);
        gem.connectBasicDiagramReadOnlyFacet(
            new BasicDiagramReadOnlyFacet()
            {
              public boolean isReadOnly()
              {
                return true;
              } 
            });
        diagram = gem.getDiagramFacet();
        diagrams.put(reference, diagram);
      }
      return diagram;
    }
    
	  public FigureFacet retrieveFigure(FigureReference figureReference)
	  {
	    DiagramFacet diagram = retrieveOrMakeDiagram(figureReference.getDiagramReference());
	    return diagram.retrieveFigure(figureReference.getId());
	  }
	  
		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramRegistryFacet#getDiagrams()
		 */
		public List<DiagramFacet> getDiagrams()
		{
			return new ArrayList<DiagramFacet>(diagrams.values());
		}

    public void reset()
    {
      diagrams = new HashMap<DiagramReference, DiagramFacet>();
    }

    /**
     * @see com.intrinsarc.jumble.repository.LRUDiagramCacheFacet#enforceMaxUnmodifiedUnviewedDiagramsLimit()
     */
    public void enforceMaxUnmodifiedUnviewedDiagramsLimit()
    {
    	// kill any unlistened to chained diagrams
      for (DiagramFacet possible : GlobalDiagramRegistry.registry.getDiagrams())
      {
        if (!possible.hasListeners() && possible.getPossiblePerspective() != null)
          removeDiagram(possible);
      }    
    	
      // loop until done
      boolean moreToKill = false;
      do
      {
        int unmodifiedUnviewed = 0;
        DiagramFacet oldestDiagram = null;
        long oldestTime = System.currentTimeMillis();
        
        for (DiagramFacet possible : GlobalDiagramRegistry.registry.getDiagrams())
        {
          if (!possible.hasListeners() && !possible.isModified() && !possible.isClipboard())
          {
            unmodifiedUnviewed++;
            if (possible.getLRUTime() <= oldestTime)
            {
              oldestDiagram = possible;
              oldestTime = possible.getLRUTime();
            }
          }
        }
  
        // if we have more than the required number, kill the oldest
        if (unmodifiedUnviewed > maxUnmodifiedUnviewedDiagrams && oldestDiagram != null)
        {
          removeDiagram(oldestDiagram);
          moreToKill = unmodifiedUnviewed-- > maxUnmodifiedUnviewedDiagrams;
        }
        else
          moreToKill = false;
      } while (moreToKill);
    }
    
    public List<DiagramFacet> refreshAllDiagrams(List<DiagramFacet> exceptThese)
    {
      List<DiagramFacet> unmodified = new ArrayList<DiagramFacet>();
      for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
      {
        if (!diagram.isClipboard())
        {
        	if (exceptThese.contains(diagram))
        	{
        		// ignore
        	}
        	else
          // either kill it or resync it
          {
            if (diagram.hasListeners())
            {
              refreshDiagram(diagram);
              unmodified.add(diagram);
            }
            else
            {
              removeDiagram(diagram);
            }
          }
        }
      }
      return unmodified;
    }
    
    private void refreshDiagram(DiagramFacet diagram)
    {
      try
      {
        diagram.regenerate(recreatorFacet.retrievePersistentDiagram(diagram.getDiagramReference()));
        recreatorFacet.setSaveDetails(diagram);
        update(diagram, true);
      }
      catch (DiagramRecreationException ex)
      {
        System.err.println("$$ got a recreation exception when regenerating diagram: " + ex);
      }
    }
	}
	
	private void removeDiagram(DiagramFacet diagram)
	{
		diagram.dispose();
	  diagrams.remove(diagram.getDiagramReference());
	}
	
	private void update(DiagramFacet diagram, boolean forceToNotModified)
	{
    // make sure that we handles any needed changes to this diagram
    for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
      diagram.formViewUpdate(pass, false);

    // make sure this is shown as unmodified
    if (forceToNotModified)
      diagram.resetModified();		
	}
}