package com.hopstepjump.jumble.umldiagrams.portnode;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.figurefacilities.containment.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creation.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.repositorybase.*;

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

		public Set<String>[] getAddedAndDeleted()
		{
			return (Set<String>[]) new Set[]{new HashSet<String>(addedUuids), new HashSet<String>(deletedUuids)};
		}

		public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid)
		{
			if (addedUuids.contains(uuid))
				return false;
			return visuallySuppressedUuids.contains(uuid) || deletedUuids.contains(uuid);
		}

		public void resetToDefaults()
		{
			addedUuids.clear();
			deletedUuids.clear();
		}

		public void setAddedAndDeleted(Set<String>[] addedAndDeletedUuids)
		{
			if (addedAndDeletedUuids == null)
				resetToDefaults();
			else
			{
				addedUuids = addedAndDeletedUuids[0];
				deletedUuids = addedAndDeletedUuids[1];
			}
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
  }

  private class PortAddFacetImpl implements PortAddFacet
  {
    public Object addPort(Object memento, FigureReference reference, NodeCreateFacet factory,
        PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location)
    {
      // if we have created the cmd before, just reexecute
      if (memento != null)
      {
        ((Command) memento).execute(false);
        return memento;
      }
      
      // make the composite and execute it
      CompositeCommand comp = new CompositeCommand("", "");
      // adjust resizings to accommodate operation preview
      NodeCreateFigureTransaction.create(
      		figureFacet.getDiagram(),
          useSubject,
          reference,
          figureFacet.getFigureReference(),
          factory,
          new UPoint(0,0),
          null,
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
  
      ContainerAddCommand addCmd =
        new ContainerAddCommand(
            figureFacet.getFigureReference(),
            new FigureReference[]{reference},
            "added containables to container",
            "removed containables from container");

      resizings.resizeToAddContainables(new ContainedPreviewFacet[]{movings.getCachedPreview(figure).getContainedPreviewFacet()}, location);
      resizings.end();
  
      return comp;
    }

    public void unAddPort(Object memento)
    {
      ((Command) memento).unExecute();
    }

    public Object replacePort(Object memento, FigureReference reference, FigureReference toReplace,
        NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject)
    {
      // if we have created the cmd before, just reexecute
      if (memento != null)
      {
        ((Command) memento).execute(false);
        return memento;
      }
      
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
  
      ContainerAddCommand addCmd =
        new ContainerAddCommand(
            figureFacet.getFigureReference(),
            new FigureReference[]{reference},
            "added containables to container",
            "removed containables from container");
      addCmd.execute(true);

      resizings.resizeToAddContainables(new ContainedPreviewFacet[]{movings.getCachedPreview(figure).getContainedPreviewFacet()}, location);
      resizings.end();
  
      return null;
    }

    public void unReplacePort(Object memento)
    {
      ((Command) memento).unExecute();
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

    public void unAddContents(Object memento)
    {
      ContainedFacet[] containable = makeContained((FigureReference[]) memento);
      removeContents(containable);
    }

    public Object removeContents(ContainedFacet[] containable)
    {
      // tell each containable that they are now contained by this
      for (int lp = 0; lp < containable.length; lp++)
      {
        contents.remove(containable[lp].getFigureFacet());
        containable[lp].setContainer(null);
      }
      figureFacet.adjusted();

      return makeFigureReferences(containable);
    }

    public void unRemoveContents(Object memento)
    {
      ContainedFacet[] containable = makeContained((FigureReference[]) memento);
      addContents(containable);
    }

    public Object addContents(ContainedFacet[] containable)
    {
      // tell each containable that they are now contained by this
      for (int lp = 0; lp < containable.length; lp++)
      {
        contents.add(containable[lp].getFigureFacet());
        containable[lp].setContainer(this);
      }
      figureFacet.adjusted();

      return makeFigureReferences(containable);
    }

    private FigureReference[] makeFigureReferences(ContainedFacet[] containable)
    {
      int length = containable.length;
      FigureReference[] references = new FigureReference[length];
      for (int lp = 0; lp < length; lp++)
        references[lp] = containable[lp].getFigureFacet().getFigureReference();
      return references;
    }

    private ContainedFacet[] makeContained(FigureReference[] figures)
    {
      int length = figures.length;
      ContainedFacet[] containable = new ContainedFacet[length];
      for (int lp = 0; lp < length; lp++)
        containable[lp] = GlobalDiagramRegistry.registry.retrieveFigure(figures[lp]).getContainedFacet();
      return containable;
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

        PortNodeFacet typeFacet = (PortNodeFacet) containables[lp].getFigureFacet()
            .getDynamicFacet(PortNodeFacet.class);
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
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
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
     * @see com.hopstepjump.idraw.foundation.ContainerFacet#persistence_addContained(
     *      FigureFacet)
     */
    public void persistence_addContained(FigureFacet contained)
    {
      // add this to the list, and set the container
      contents.add(contained);
      contained.getContainedFacet().persistence_setContainer(containerFacet);
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
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
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
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#setPersistentProperties(MDiagramDefinitionsPackage,
     *      MFigure)
     */
    public void addToPersistentProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("classScope", classScope, false));
      properties.add(new PersistentProperty("addedUuids", addedUuids));
      properties.add(new PersistentProperty("deletedUuids", deletedUuids));
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
     */
    public Command formViewUpdateCommandAfterSubjectChanged(boolean isTop, ViewUpdatePassEnum pass)
    {
      return null;
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
     */
    public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
    {
      return null;
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
     */
    public Object getSubject()
    {
      return null;
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
     */
    public boolean hasSubjectBeenDeleted()
    {
      return false;
    }

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
    }

    public Command getPostContainerDropCommand()
    {
      return null;
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

  public PortCompartmentGem(PersistentProperties properties)
  {
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
