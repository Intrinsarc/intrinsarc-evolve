package com.hopstepjump.jumble.umldiagrams.baseline;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class BaselineNodeGem implements Gem
{
  private static final double SIZE = 100;
  private BasicNodeFigureFacet figureFacet;
  private BasicNodeAppearanceFacetImpl appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private ResizeVetterFacetImpl resizeVetterFacet = new ResizeVetterFacetImpl();
  private boolean region;
  private String name;
  

  public BaselineNodeGem(String name, boolean region)
  {
    this.name = name;
    this.region = region;
  }
  
  public BaselineNodeGem(String name, boolean region, PersistentProperties properties)
  {
    this(name, region);
  	// do nothing at the moment
  }
  
  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {
	  public void setBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
	  {
	  	BaselineNodeGem.this.figureFacet = figureFacet;
	  }
	
	  public String getFigureName()
	  {
	    return name;
	  }
	
	  public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	  {
	    return new Manipulators(
	        null,
	        new ResizingManipulatorGem(
	        		coordinator,
	            figureFacet,
	            diagramView,
	            figureFacet.getFullBounds(),
	            resizeVetterFacet,
	            firstSelected).getManipulatorFacet());
	  }
	
	  public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
      BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
      basicGem.connectPreviewCacheFacet(previews);
      basicGem.connectFigureFacet(figureFacet);
      return basicGem.getPreviewFacet();
	  }
	
	
	  public ZNode formView()
	  {
	    // if the graphical structures aren't set up, then do it now
	    UBounds lineBounds = figureFacet.getFullBounds();
	
	    // group them
	    ZGroup group = new ZGroup();

      // add a very thin line
      ZLine thinLine = new ZLine(lineBounds.getTopLeftPoint(), lineBounds.getTopRightPoint());
      if (region)
      {
        thinLine.setStroke(
            new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));
      }
      else
        thinLine.setPenPaint(null);
      group.addChild(new ZVisualLeaf(thinLine));
      
      // add the thick line
      ZLine grabLine = new ZLine(lineBounds.getTopLeftPoint(), lineBounds.getTopRightPoint());
      grabLine.setPenWidth(7);
      grabLine.setPenPaint(ScreenProperties.getTransparentColor());
      ZVisualLeaf grabLeaf = new ZVisualLeaf(grabLine);
      grabLeaf.putClientProperty("figure", this);
      group.addChild(grabLeaf);
      
      
      // add the interpretable properties
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("figure", figureFacet);
	
	    return group;
	  }
	
	  /**
		 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#getContextMenu()
		 */
		public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			if (!diagramView.getDiagram().isReadOnly())
			  popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
			return popup;
		}
		
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), getCreationExtent());
		}
		
		public UDimension getCreationExtent()
		{
			return new UDimension(SIZE, 0);
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
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
			return figureFacet.getFullBounds();
		}

		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			UBounds bounds = figureFacet.getFullBounds();
			return new UBounds(bounds.getPoint(), resizeVetterFacet.vetResizedExtent(bounds));
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			// nothing to do at the moment
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
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

    public void performPostContainerDropTransaction()
    {
    }

		public boolean canMoveContainers()
		{
			return true;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return false;
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

		public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return null;
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
		}
  }
  
  private class ResizeVetterFacetImpl implements ResizeVetterFacet
  {
		public void startResizeVet()
		{
		}
	
	  public UDimension vetResizedExtent(UBounds bounds)
	  {
	  	UDimension extent = bounds.getDimension();

      // don't allow the extent to go less than the height or width
	    UDimension minExtent = appearanceFacet.getCreationExtent();
	    double vettedWidth = Math.max(extent.getWidth(), minExtent.getWidth());
	    return new UDimension(vettedWidth, 0);  // preserve "flatness"
	  }
	
	  public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
	  {
	  	return bounds;
	  }
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  }
}