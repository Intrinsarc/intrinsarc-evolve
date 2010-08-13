package com.intrinsarc.evolve.umldiagrams.portnode;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.figurefacilities.containment.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creation.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.repositorybase.*;

import edu.umd.cs.jazz.*;

/**
 * @author andrew
 */
public class PortCompartmentGem implements Gem
{
  static final String FIGURE_NAME = "port container";
  private Set<FigureFacet> contents = new HashSet<FigureFacet>(); // as figures
  private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private boolean classScope;
  private PortAddFacet portAddFacet = new PortAddFacetImpl();
  private PortCompartmentFacetImpl portCompartmentFacet = new PortCompartmentFacetImpl();
  private Set<String> addedUuids = new HashSet<String>();
  private Set<String> deletedUuids = new HashSet<String>();
  
  public PortCompartmentGem()
  {
  }
  
  public PortCompartmentFacet getPortCompartmentFacet()
  {
    return portCompartmentFacet;
  }
  
  private class PortCompartmentFacetImpl implements PortCompartmentFacet
  {
		public void addDeleted(String uuid)
		{
			if (!addedUuids.remove(uuid))
				deletedUuids.add(uuid);
		}

		public void removeDeleted(String uuid)
		{
			if (!deletedUuids.remove(uuid))
				addedUuids.add(uuid);
		}

		public void clean(Set<String> visuallySuppressedUuids, Set<String> uuids)
		{
			addedUuids.retainAll(visuallySuppressedUuids);
			deletedUuids.removeAll(visuallySuppressedUuids);
			deletedUuids.retainAll(uuids);
		}

		public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid)
		{
			if (addedUuids.contains(uuid))
				return false;
			return visuallySuppressedUuids.contains(uuid) || deletedUuids.contains(uuid);
		}

		public void setToShowAll(Set<String> visuallySuppressedUuids)
		{
			addedUuids = visuallySuppressedUuids;
			deletedUuids.clear();
		}

    public FigureFacet getFigureFacet()
    {
      return figureFacet;
    }
    
    public String toString()
    {
    	return
    		"[added uuids = " + asString(addedUuids) +
    		", deleted uuids = " + asString(deletedUuids) + "]";
    }

		private String asString(Set<String> uuids)
		{
			String str = "";
			for (String uuid : uuids)
			{
				str += uuid + " ";
			}
			return str;
		}
  }

  private class PortAddFacetImpl implements PortAddFacet
  {
    public void addPort(FigureReference reference, NodeCreateFacet factory,
        PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location)
    {
      // adjust resizings to accommodate operation preview
      NodeCreateFigureTransaction.create(
      		figureFacet.getDiagram(),
          useSubject,
          reference,
          figureFacet.getFigureReference(),
          factory,
          new UPoint(0,0),
          properties,
          relatedSubject);
      
      // get hold of the figure
      DiagramFacet diagram = figureFacet.getDiagram();
      FigureFacet figure = diagram.retrieveFigure(reference.getId());
      
      // make resizings for port compartment of class, and a moving figures for the moving shape
      MovingFiguresFacet movings = new MovingFiguresGem(diagram, new UPoint(0,0)).getMovingFiguresFacet();
      ResizingFiguresFacet resizings = new ResizingFiguresGem(movings, diagram).getResizingFiguresFacet();
      resizings.markForResizing(figureFacet);
  
      List<FigureFacet> movingFigure = new ArrayList<FigureFacet>();
      movingFigure.add(figure);
      movings.indicateMovingFigures(movingFigure);
      movings.move(location);      
  
	    ContainerAddTransaction.add(
	        figureFacet.getContainerFacet(),
	        new FigureReference[]{reference});

      resizings.resizeToAddContainables(new ContainedPreviewFacet[]{movings.getCachedPreview(figure).getContainedPreviewFacet()}, location);
      resizings.end();
    }

    public void replacePort(FigureReference reference, FigureReference toReplace,
        NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject)
    {
      // use the location of the previous port
      DiagramFacet diagram = figureFacet.getDiagram();
      FigureFacet replacedFigure = diagram.retrieveFigure(toReplace.getId());
      UPoint location = replacedFigure.getFullBounds().getTopLeftPoint();

      // adjust resizings to accommodate operation preview
      NodeCreateFigureTransaction.create(
      		diagram,
          useSubject,
          reference,
          figureFacet.getFigureReference(),
          factory,
          new UPoint(0,0),
          null,
          relatedSubject);
      
      // get hold of the figure
      FigureFacet figure = diagram.retrieveFigure(reference.getId());
      
      // make resizings for operation compartment of class, and a moving figures for the moving shape
      MovingFiguresFacet movings = new MovingFiguresGem(diagram, new UPoint(0,0)).getMovingFiguresFacet();
      ResizingFiguresFacet resizings = new ResizingFiguresGem(movings, diagram).getResizingFiguresFacet();
      resizings.markForResizing(figureFacet);
  
      List<FigureFacet> movingFigure = new ArrayList<FigureFacet>();
      movingFigure.add(figure);
      movings.indicateMovingFigures(movingFigure);
      movings.move(location);
  
      ContainerAddTransaction.add(
	      figureFacet.getContainerFacet(),
	      new FigureReference[]{reference});

      resizings.resizeToAddContainables(new ContainedPreviewFacet[]{movings.getCachedPreview(figure).getContainedPreviewFacet()}, location);
      resizings.end();
    }
  }

  private class ContainerFacetImpl implements BasicNodeContainerFacet
  {
    public boolean insideContainer(UPoint point)
    {
      return figureFacet.getFullBounds().contains(point);
    }

    public Iterator<FigureFacet> getContents()
    {
      return getContentsList().iterator();
    }

    public void removeContents(ContainedFacet[] containable)
    {
      // tell each containable that they are now contained by this
      for (int lp = 0; lp < containable.length; lp++)
      {
        contents.remove(containable[lp].getFigureFacet());
        containable[lp].setContainer(null);
      }
    }
    public void addContents(ContainedFacet[] containable)
    {
      // tell each containable that they are now contained by this
      for (int lp = 0; lp < containable.length; lp++)
      {
        contents.add(containable[lp].getFigureFacet());
        containable[lp].setContainer(this);
      }
    }

    public boolean isWillingToActAsBackdrop()
    {
      return true;
    }

    public boolean directlyAcceptsItems()
    {
      return false;
    }

    /**
     * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
     */
    public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
    {
      for (int lp = 0; lp < containables.length; lp++)
      {
        // only accept port types
        if (!containables[lp].getFigureFacet().hasDynamicFacet(PortNodeFacet.class))
        {
          return null;
        }

        PortNodeFacet typeFacet = containables[lp].getFigureFacet().getDynamicFacet(PortNodeFacet.class);
        if (typeFacet.isClassScope() != classScope)
        {
          return null;
        }
      }
      return this;
    }

    public FigureFacet getFigureFacet()
    {
      return figureFacet;
    }

    public ContainedFacet getContainedFacet()
    {
      return figureFacet.getContainedFacet();
    }

    /**
     * @see com.hopstepjump.jumble.foundation.ContainerFacet#addChildPreviewsToCache(PreviewCacheFacet)
     */
    public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
    {
      Iterator iter = getContents();
      {
        while (iter.hasNext())
        {
          FigureFacet contained = (FigureFacet) iter.next();
          previewCache.getCachedPreviewOrMakeOne(contained);
        }
      }
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
     */
    public void setShowingForChildren(boolean showing)
    {
      for (Iterator iter = getContents(); iter.hasNext();)
      {
        FigureFacet figure = (FigureFacet) iter.next();
        figure.setShowing(showing);
      }
    }

    /**
     * @see com.intrinsarc.idraw.foundation.ContainerFacet#persistence_addContained(
     *      FigureFacet)
     */
    public void persistence_addContained(FigureFacet contained)
    {
      // add this to the list, and set the container
      contents.add(contained);
      contained.getContainedFacet().persistence_setContainer(containerFacet);
    }

		public void cleanUp()
		{
			contents.clear();
		}
  }

  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {
    public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured,
        boolean firstSelected, boolean allowTYPE0Manipulators)
    {
      return null; // not needed -- this is not directly selectable
    }

    public ZNode formView()
    {
      // don't draw, as this doesn't show up as a visual representation
      return new ZGroup();
    }
    
    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}

    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
     */
    public FigureFacet getActualFigureForSelection()
    {
      // eek! we want this container's actual figure for selection
      return figureFacet.getContainedFacet().getContainer().getFigureFacet().getActualFigureForSelection();
    }

    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getContextMenu(DiagramView,
     *      ToolCoordinator)
     */
    public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      return null;
    }

    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.Figure#getFigureName()
     */
    public String getFigureName()
    {
      ContainerFacet container = figureFacet.getContainedFacet().getContainer();
      if (container == null)
        return "port container";
      return "port container for " + container.getFigureFacet().getFigureName();
    }

    public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
	  	PortCompartmentPreviewGem containerPreviewGem = new PortCompartmentPreviewGem();
	  	BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, false);
      basicGem.connectBasicNodePreviewUnusualSizingFacet(containerPreviewGem.getBasicNodePreviewUnusualSizingFacet());
      basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			basicGem.connectContainerPreviewFacet(containerPreviewGem.getContainerPreviewFacet());
			containerPreviewGem.connectPreviewFacet(basicGem.getPreviewFacet());
			containerPreviewGem.connectPreviewCacheFacet(previews);

			return basicGem.getPreviewFacet();
	  }    /**
           * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getAutoSizedBounds(IDiagram)
           */
    public UBounds getAutoSizedBounds(boolean autoSized)
    {
      return null;
    }

    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
     */
    public UDimension getCreationExtent()
    {
      return new UDimension(0, 0);
    }

    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
     */
    public boolean acceptsContainer(ContainerFacet container)
    {
      return true;
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
     */
    public UBounds getFullBoundsForContainment()
    {
      UBounds containmentBounds = figureFacet.getFullBounds();
      for (FigureFacet port : contents)
        containmentBounds = containmentBounds.union(port.getFullBoundsForContainment());

      return containmentBounds;
    }

    public UBounds getRecalculatedFullBounds(boolean diagramResize)
    {
      return figureFacet.getFullBounds();
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#setPersistentProperties(MDiagramDefinitionsPackage,
     *      MFigure)
     */
    public void addToPersistentProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("classScope", classScope, false));
      properties.add(new PersistentProperty("addedUuids", addedUuids));
      properties.add(new PersistentProperty("deletedUuids", deletedUuids));
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
     */
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
     */
    public void middleButtonPressed(ToolCoordinatorFacet coordinator)
    {
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
     */
    public Object getSubject()
    {
      return null;
    }

    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
     */
    public boolean hasSubjectBeenDeleted()
    {
      return false;
    }

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
    }

    public void performPostContainerDropTransaction()
    {
    }

		public boolean canMoveContainers()
		{
			return false;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }

  public static PortCompartmentGem createAndWireUp(
      DiagramFacet diagram,
      String figureId,
      ContainerFacet owner,
      String containedName,
      boolean classScope)
  {
    // need to find a better way to do this...
    BasicNodeGem basicGem = new BasicNodeGem(
        PortCompartmentCreatorGem.RECREATOR_NAME, diagram,
        figureId,
        new UPoint(0, 0),
        true,
        containedName,
        false);
    PortCompartmentGem portGem = new PortCompartmentGem(classScope);
    basicGem.connectBasicNodeAppearanceFacet(portGem.getBasicNodeAppearanceFacet());
    basicGem.getBasicNodeFigureFacet().getContainedFacet().setContainer(owner);

    // a port container cannot act as an anchor
    basicGem.connectAnchorFacet(null);
    portGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
    basicGem.connectBasicNodeContainerFacet(portGem.getBasicNodeContainerFacet());
    return portGem;
  }

  public PortCompartmentGem(boolean classScope)
  {
    this.classScope = classScope;
  }

  public PortCompartmentGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }

  private void interpretPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
    classScope = properties.retrieve("classScope", false).asBoolean();
    addedUuids = new HashSet<String>(properties.retrieve("addedUuids").asStringCollection());
    deletedUuids = new HashSet<String>(properties.retrieve("deletedUuids").asStringCollection());
	}

	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
    return appearanceFacet;
  }

  private List<FigureFacet> getContentsList()
  {
    List<FigureFacet> list = new ArrayList<FigureFacet>();
    list.addAll(contents);
    return list;
  }

  /**
   * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#setBasicNodeFigureFacet(BasicNodeFigureFacet)
   */
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
    figureFacet.registerDynamicFacet(portCompartmentFacet, PortCompartmentFacet.class);
    figureFacet.registerDynamicFacet(portCompartmentFacet, SimpleDeletedUuidsFacet.class);
    figureFacet.registerDynamicFacet(portAddFacet, PortAddFacet.class);
  }

  public BasicNodeContainerFacet getBasicNodeContainerFacet()
  {
    return containerFacet;
  }
}
