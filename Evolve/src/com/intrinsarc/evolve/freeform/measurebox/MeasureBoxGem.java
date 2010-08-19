package com.intrinsarc.evolve.freeform.measurebox;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulation.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.nodefacilities.resize.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class MeasureBoxGem implements Gem
{
	private Color lineColor = Color.magenta;
	private Font font = ScreenProperties.getPrimaryFont();
	static final String FIGURE_NAME = "measure box";
	private String text = "Measure box";
	private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private ResizeVetterFacet resizeVetterFacet = new ResizeVetterFacetImpl();
	private TextableFacet textableFacet = new TextableFacetImpl();
	private BasicNodeFigureFacet figureFacet;

	public MeasureBoxGem()
  {
  }
  
	public MeasureBoxGem(PersistentFigure pfig)
  {
		interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
		text = properties.retrieve("text").asString();
	}

	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  	figureFacet.registerDynamicFacet(textableFacet, TextableFacet.class);
	}

	private class TextableFacetImpl implements TextableFacet
	{
		/**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#getTextBounds(String)
		 */
		public UBounds getTextBounds(String text)
		{
			UBounds bounds = figureFacet.getFullBounds();
			ZText nameText = new ZText(text, font);
			ZText boundsText = new ZText(bounds.toString(), font);
			ZText brText = new ZText(bounds.getBottomRightPoint().toString(), font);
			UPoint start = bounds.getTopLeftPoint().subtract(getTextHeightAsDimension(brText));
			brText.setTranslation(start);
			brText.setPenColor(lineColor);
			start = start.subtract(getTextHeightAsDimension(boundsText));
			boundsText.setTranslation(start);
			boundsText.setPenColor(lineColor);
			start = start.subtract(getTextHeightAsDimension(nameText));
			nameText.setTranslation(start);
			nameText.setPenColor(lineColor);
	
			return new UBounds(nameText.getBounds()).addToPoint(new UDimension(0, -1));
		}
	
		/**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#vetTextResizedExtent(String)
		 */
		public UBounds vetTextResizedExtent(String text)
		{
			return figureFacet.getFullBounds();
		}
	
	  public void setText(String newText, Object listSelection, boolean unsuppress)
	  {
	    // need to resize this also, as the change in text may have affected the size
	    text = newText;	    
	    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(newText));
	  }
	
		/**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextableFacet#getFigureFacet()
		 */
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}

    public JList formSelectionList(String textSoFar)
    {
      return null;
    }

	}
	
	private class ResizeVetterFacetImpl implements ResizeVetterFacet
	{
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.resizebase.ResizeVetter#startResizeVet()
		 */
		public void startResizeVet()
		{
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedBounds(DiagramView, int, UBounds, boolean)
		 */
		public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
		{
			return bounds;
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedExtent(UBounds)
		 */
		public UDimension vetResizedExtent(UBounds bounds)
		{
			if (figureFacet.isAutoSized())
				return figureFacet.getFullBounds().getDimension();
			return bounds.getDimension();
		}
	}
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
		 */
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), new UDimension(100, 100));
		}

		public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}
		
		/**
		 * @see com.intrinsarc.jumble.foundation.interfaces.Figure#formView()
		 */
		public ZNode formView()
		{
			UBounds bounds = figureFacet.getFullBounds();
			ZRectangle rect = new ZRectangle(bounds);
			rect.setPenPaint(lineColor);
			rect.setPenWidth(2);
			rect.setFillPaint(null);
			
			// place some text indicating the dimensions
			ZText nameText = new ZText(text, font);
			ZText boundsText = new ZText(bounds.toString(), font);
			ZText brText = new ZText(bounds.getBottomRightPoint().toString(), font);
			UPoint start = bounds.getTopLeftPoint().subtract(getTextHeightAsDimension(brText));
			brText.setTranslation(start);
			brText.setPenColor(lineColor);
			start = start.subtract(getTextHeightAsDimension(boundsText));
			boundsText.setTranslation(start);
			boundsText.setPenColor(lineColor);
			start = start.subtract(getTextHeightAsDimension(nameText));
			nameText.setTranslation(start);
			nameText.setPenColor(lineColor);
			
			ZGroup group = new ZGroup();
			group.addChild(new ZVisualLeaf(nameText));
			group.addChild(new ZVisualLeaf(boundsText));
			group.addChild(new ZVisualLeaf(brText));
			group.addChild(new ZVisualLeaf(rect));
					
	/*
			// make an svg figure
			ZSVG zsvg = new ZSVG(new File("c:/program files/mayura/artwork.svg"));
			ZGroup gp = zsvg.getGroup();
			UBounds bds = new UBounds(gp.getBounds());
			UPoint svgTopLeft = bds.getTopLeftPoint();
	
			ZTransformGroup offsetGp = new ZTransformGroup();
			offsetGp.translate(-svgTopLeft.getX(), -svgTopLeft.getY());
			offsetGp.addChild(gp);
	
			ZTransformGroup scaleTransform = new ZTransformGroup(offsetGp);
			AffineTransform affine = new AffineTransform();
			affine.scale(bounds.getWidth() / bds.getWidth(), bounds.getHeight() / bds.getHeight());
	
			scaleTransform.setTransform(affine);
			ZTransformGroup translateTransform = new ZTransformGroup(scaleTransform);
			translateTransform.translate(pt.getX(), pt.getY());
			group.addChild(translateTransform);
			*/
			
			// indicate that these visual items are linked to this figure
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("figure", figureFacet);
			
			return group;
		}
		
		/**
		 * @see com.intrinsarc.jumble.foundation.interfaces.Figure#getFigureName()
		 */
		public String getFigureName()
		{
			return FIGURE_NAME;
		}
	
		/**
		 * @see com.intrinsarc.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
		 */
		public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
	    ManipulatorFacet keyFocus = null;
	    if (favoured)
	    {
	      TextManipulatorGem textGem = new TextManipulatorGem(coordinator, diagramView, "changed measure box text", "restored measure box text", text, font, lineColor, Color.white, TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
	      textGem.connectTextableFacet(textableFacet);
	      keyFocus = textGem.getManipulatorFacet();
	    }
	    return new Manipulators(
	        keyFocus,
	        new ResizingManipulatorGem(
	        		coordinator,
	            figureFacet,
	            diagramView,
	            figureFacet.getFullBounds(),
	            resizeVetterFacet,
	            firstSelected).getManipulatorFacet());
		}

		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getContextMenu(IDiagramView, IToolCoordinator)
		 */
		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
		  if (diagramView.getDiagram().isReadOnly())
		    return null;
			JPopupMenu popup = new JPopupMenu();
			popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
			return popup;
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return new UDimension(100, 100);
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#makeNodePreviewFigure(PreviewCacheFacet, IDiagram, UPoint, boolean)
		 */
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			return basicGem.getPreviewFacet();
		}

		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
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
			return figureFacet.getFullBounds();
		}
		
		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			return figureFacet.getFullBounds();
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("text", text));
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

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }
  
	private UDimension getTextHeightAsDimension(ZText text)
	{
		return new UDimension(0, text.getBounds().getHeight());
	}
}
