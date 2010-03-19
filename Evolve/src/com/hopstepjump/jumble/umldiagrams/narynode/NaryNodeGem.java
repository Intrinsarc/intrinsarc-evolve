package com.hopstepjump.jumble.umldiagrams.narynode;

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

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class NaryNodeGem implements Gem
{
  private static final double SIZE = 32;
  static final String FIGURE_NAME = "n-ary association";
	private Color lineColor = Color.black;
	private Color fillColor = Color.white;
  private BasicNodeFigureFacet figureFacet;
  private BasicNodeAppearanceFacetImpl appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private ResizeVetterFacetImpl resizeVetterFacet = new ResizeVetterFacetImpl();

  public NaryNodeGem()
  {
  }
  
  public NaryNodeGem(PersistentProperties properties)
  {
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
	  	NaryNodeGem.this.figureFacet = figureFacet;
	  }
	
	  public String getFigureName()
	  {
	    return FIGURE_NAME;
	  }

	  public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
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
	  	// make a basic node preview gem, and connect it to the nary preview gem
	  	BasicNodePreviewGem previewGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
	  	NaryPreviewGem naryGem = new NaryPreviewGem(figureFacet.getFullBounds());
			previewGem.connectBasicNodePreviewAppearanceFacet(naryGem.getBasicNodePreviewAppearanceFacet());
			previewGem.connectPreviewCacheFacet(previews);
			previewGem.connectFigureFacet(figureFacet);
			return previewGem.getPreviewFacet();
	  }
	
	
	  public ZNode formView()
	  {
	    // if the graphical structures aren't set up, then do it now
	    UBounds boxBounds = figureFacet.getFullBounds();
	
	    double width = boxBounds.getWidth();
	    double height = boxBounds.getHeight();
	    UPoint start = boxBounds.getTopLeftPoint().add(new UDimension(width/2, 0));
	    ZPolygon diamondLine = new ZPolygon(start);
	    diamondLine.setPenPaint(lineColor);
	    diamondLine.setClosed(true);
	    diamondLine.setFillPaint(fillColor);
	
	    // add the points for the note
	    diamondLine.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
	    diamondLine.add(start.add(new UDimension(-width/2, height/2)));
	    diamondLine.add(start.add(new UDimension(0, height)));
	    diamondLine.add(start.add(new UDimension(width/2, height/2)));
	
	    // group them
	    ZGroup group = new ZGroup();
	    group.addChild(new ZVisualLeaf(diamondLine));
	
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
		  if (diagramView.getDiagram().isReadOnly())
		    return null;
			JPopupMenu popup = new JPopupMenu();
			popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
			return popup;
		}
		
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), getCreationExtent());
		}
		
		public UDimension getCreationExtent()
		{
			return new UDimension(SIZE, SIZE);
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

    public Command getPostContainerDropCommand()
    {
      return null;
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
	    if (figureFacet.isAutoSized())
	      return minExtent;
	
	    double vettedWidth = Math.max(extent.getWidth(), minExtent.getWidth());
	    return new UDimension(vettedWidth, vettedWidth);  // preserve "squareness"
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