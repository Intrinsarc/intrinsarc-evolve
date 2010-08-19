package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.intrinsarc.evolve.guibase.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulation.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.nodefacilities.resize.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author andrew
 */
public class LinkedTextGem implements Gem
{
	static final String FIGURE_NAME = "Linked text";
	private Color lineColor = Color.black;
  private boolean suppress = false;
	private Font font = ScreenProperties.getPrimaryFont();
	private String text;
	private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private BasicNodeFigureFacet figureFacet;
	private LinkedTextFacet linkedTextFacet = new LinkedTextFacetImpl();
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();
  private int majorPointType;
  private String prefix;

	public LinkedTextGem(String text, boolean suppress, int majorPointType)
  {
	  this.text = text;
	  this.suppress = suppress;
	  this.majorPointType = majorPointType;
  }
  
	public LinkedTextGem(PersistentFigure pfig)
  {
		interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
		text = properties.retrieve("text", "").asString();
    suppress = properties.retrieve("suppress", false).asBoolean();
    majorPointType = properties.retrieve("majorPt", CalculatedArcPoints.MAJOR_POINT_MIDDLE).asInteger();
		prefix = properties.retrieve("prefix", (String) null).asString();
	}

	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
  public LinkedTextFacet getLinkedTextFacet()
  {
    return linkedTextFacet;
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  	figureFacet.registerDynamicFacet(linkedTextFacet, TextableFacet.class);
  	figureFacet.registerDynamicFacet(linkedTextFacet, LinkedTextFacet.class);
    figureFacet.setShowing(!suppress);
	}
  
  public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }

  private class ClipboardActionsFacetImpl implements ClipboardActionsFacet
  {
    public boolean hasSpecificDeleteAction()
    {
      return true;
    }
    
    public void makeSpecificDeleteAction()
    {
    	hideLinkedText(true);
    }
    
    public void performPostDeleteAction()
    {
    }

    public boolean hasSpecificKillAction()
    {
      return false;
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
    }
  }
  
  private void hideLinkedText(boolean hide)
  {
    // make the change
    suppress = hide;
    figureFacet.setShowing(!suppress);
    figureFacet.performResizingTransaction(figureFacet.getFullBounds());      
  }
  
  private class LinkedTextFacetImpl implements LinkedTextFacet
  {
    public ManipulatorFacet getTextEntryManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
    {
    	if (!figureFacet.isShowing())
    		return null;
    	
	    ManipulatorFacet keyFocus = null;
      TextManipulatorGem textGem = new TextManipulatorGem(coordinator, "changed text", "restored text", getPrefixedText(text), font, lineColor, Color.white, TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
      textGem.connectTextableFacet(linkedTextFacet);
      keyFocus = textGem.getManipulatorFacet();
      OriginLineManipulatorGem decorator =
	      new OriginLineManipulatorGem(figureFacet, getMajorPoint());
	    decorator.setDecoratedManipulatorFacet(keyFocus);
	    
	    Manipulators manipulators =
	      new Manipulators(
	      		decorator.getManipulatorFacet(),
	          new LinkedTextSelectionManipulatorGem(
	              figureFacet.getFullBounds(),
	              false,
	              new UDimension(4, 4)).getManipulatorFacet());
	    
	    return manipulators;
    }
    
    public JMenuItem getViewLabelMenuItem(final ToolCoordinatorFacet coordinator, final String name)
    {
      JCheckBoxMenuItem viewLabelItem = new JCheckBoxMenuItem("View " + name + " label");
      viewLabelItem.setState(!suppress);
            
      viewLabelItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // toggle the label viewing flag (as a command)
          coordinator.startTransaction(
                suppress ? "show " + name + " label" : "hide " + name + " label",
                suppress ? "hide " + name + " label" : "show " + name + " label");
          hideLinkedText(!suppress);
          
          // force the parent to be adjusted so the manipulators get redone
          FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
          figureFacet.getDiagram().forceAdjust(parent);
          
          coordinator.commitTransaction();
        }
      });
      
      return viewLabelItem;
    }

    public boolean isHidden()
    {
      return suppress;
    }

    /**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#getTextBounds(String)
		 */
		public UBounds getTextBounds(String text)
		{
		  return vetTextResizedExtent(text).addToPoint(new UDimension(0, -1));
		}
	
		/**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#vetTextResizedExtent(String)
		 */
		public UBounds vetTextResizedExtent(String text)
		{
			ZText nameText = new ZText(getPrefixedText(text), font);
			UPoint point = figureFacet.getFullBounds().getTopLeftPoint();
			UDimension extent = new UBounds(nameText.getBounds()).getDimension();
			
			// see if the container is an arc
			ContainerFacet container = figureFacet.getContainedFacet().getContainer();
			if (container != null && container.getFigureFacet().getLinkingFacet() != null && majorPointType != CalculatedArcPoints.MAJOR_POINT_MIDDLE)
			{
				CalculatedArcPoints calc = container.getFigureFacet().getLinkingFacet().getCalculated();
				return new UBounds(
						getNewPoint(calc, point, UDimension.ZERO, extent),
						extent);
			}
			
			return new UBounds(point, extent);
		}
	
	  public void setText(String newText, Object listSelection, boolean unsuppress)
	  {
	  	// remove any prefix and any space
	  	if (newText != null && prefix != null && newText.startsWith(prefix))
	  	{
	  		newText = newText.substring(prefix.length());
	  		if (newText.startsWith(" "))
	  			newText = newText.substring(1);
	  	}
	  	
	    // need to resize this also, as the change in text may have affected the size
	  	text = extractOriginFacet().textChanged(newText, majorPointType);
	    
	    // change the visibility
	    if (unsuppress)
	    {
	    	suppress = false;
	    	figureFacet.setShowing(true);
	    }
	    figureFacet.performResizingTransaction(linkedTextFacet.vetTextResizedExtent(text));
	  }
	
		/**
		 * @see com.intrinsarc.jumble.figurefacilities.textmanipulationbase.TextableFacet#getFigureFacet()
		 */
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}

		public String getText()
		{
			return text;
		}
		
    public JList formSelectionList(String textSoFar)
    {
      return null;
    }

		public UPoint getNewPoint(CalculatedArcPoints calc, UPoint previewPoint, UDimension middleOffset, UDimension extent)
		{
			UDimension offset = calculateOffset(calc, majorPointType, extent);
			
			switch (majorPointType)
			{
				case CalculatedArcPoints.MAJOR_POINT_START:
					return calc.getStartPoint().add(offset);
				case CalculatedArcPoints.MAJOR_POINT_END:
					return calc.getEndPoint().add(offset);
				default:
					return previewPoint.add(middleOffset);
			}
		}

		private UDimension calculateOffset(CalculatedArcPoints calc, int point, UDimension extent)
		{
			if (point == CalculatedArcPoints.MAJOR_POINT_MIDDLE)
				return UDimension.ZERO;
			List<UPoint> all = calc.getAllPoints();
			
			// if we have a null extent, work out the extent ourselves
			if (extent == null)
				extent = new UBounds(new ZText(text).getBounds()).getDimension();
			
			UPoint first = point == CalculatedArcPoints.MAJOR_POINT_START ? calc.getStartPoint() : calc.getEndPoint();
			UPoint second = point == CalculatedArcPoints.MAJOR_POINT_START ? all.get(1) : all.get(all.size() - 2);
			
			boolean wider = first.xDistance(second) > first.yDistance(second);
			boolean higher = first.getY() <= second.getY();
			boolean left = first.getX() <= second.getX();
			if (wider)
			{
				// we are more horizontal
				UDimension offset = first.getTowardsOffsetUsingX(second, 2 + extent.getHeight());
				if (!higher)
					offset = offset.add(new UDimension(0, extent.getHeight()));
				
				offset = !left ? offset.subtract(new UDimension(extent.getWidth(), extent.getHeight())) : offset.subtract(new UDimension(0, extent.getHeight()));
				return higher ? offset.add(new UDimension(0, 0)) : offset.subtract(new UDimension(0, 2));
			}
			else
			{
				// we are more vertical
				UDimension offset = first.getTowardsOffsetUsingY(second, 6 + extent.getHeight());
				if (!left)
					offset = offset.subtract(new UDimension(extent.getWidth(), 0));
				else
					 offset = offset.add(new UDimension(1, 0));
				
				// odd tweak
				if (first.getIntX() == second.getIntX() && higher)
					offset = offset.add(new UDimension(0, 1));
				
				return higher ? offset.subtract(new UDimension(0, extent.getHeight())) : offset.subtract(new UDimension(0, 0));
			}
		}
	}
	
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
		 */
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), getCreationExtent());
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

			// place some text indicating the dimensions
			ZText nameText = new ZText(getPrefixedText(text), font);
			UPoint start = bounds.getTopLeftPoint();
			nameText.setTranslation(start);
			nameText.setPenColor(lineColor);
			
			ZGroup group = new ZGroup();
			group.addChild(new ZVisualLeaf(nameText));
					
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
			return figureFacet.getContainedFacet().getContainer().getFigureFacet().getSelectionManipulators(coordinator, diagramView, favoured, firstSelected, allowTYPE0Manipulators);
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
		  return null;
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			ZText nameText = new ZText(getPrefixedText(text), font);	
			return new UBounds(nameText.getBounds()).getDimension();
		}
	
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#makeNodePreviewFigure(PreviewCacheFacet, IDiagram, UPoint, boolean)
		 */
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			ReusableDiagramViewFacet view = GlobalPackageViewRegistry.activeRegistry.getFocussedView();
			// a start or end linked text cannot be moved by dragging it directly
			if (view != null && view.getCurrentDiagramView().getSelection().getFirstSelectedFigure() == figureFacet)
				if (majorPointType != CalculatedArcPoints.MAJOR_POINT_MIDDLE)
					basicGem.setAllowedToMove(false);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
      basicGem.connectBasicNodePreviewUnusualSizingFacet(
          new LinkedTextPreviewUnusualSizingGem(
              basicGem.getPreviewFacet()).getBasicNodePreviewUnusualSizingFacet());
			return basicGem.getPreviewFacet();
		}

		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
			return false;
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
      if (suppress)
        return null;
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
      properties.add(new PersistentProperty("suppress", suppress, false));
      properties.add(new PersistentProperty("majorPt", majorPointType, CalculatedArcPoints.MAJOR_POINT_MIDDLE));
			properties.add(new PersistentProperty("prefix", prefix, null));
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
      ContainerFacet container = figureFacet.getContainedFacet().getContainer();
      if (container == null)
        return true;
      return container.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
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
  
  private LinkedTextOriginFacet extractOriginFacet()
  {
    FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
    LinkedTextOriginFacet originFacet = parent.getDynamicFacet(LinkedTextOriginFacet.class);
    return originFacet;
  }
  
	/**
   * @return
   */
  private UPoint getMajorPoint()
  {
    return extractOriginFacet().getMajorPoint(majorPointType);
  }

	public void addPrefix(String prefix)
	{
		this.prefix = prefix;
	}
	
	private String getPrefixedText(String text)
	{
		if (prefix != null && text.length() > 0)
			return prefix + " " + text;
		if (prefix != null)
			return prefix;
		return text;
	}
}
