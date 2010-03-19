package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.utility.*;

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
  private HideLinkedTextFacetImpl hideLinkedTextFacet = new HideLinkedTextFacetImpl();
  private ClipboardCommandsFacet clipboardCommandsFacet = new ClipboardCommandsFacetImpl();
  private int majorPointType;

	public LinkedTextGem(String text, boolean suppress, int majorPointType)
  {
	  this.text = text;
	  this.suppress = suppress;
	  this.majorPointType = majorPointType;
  }
  
	public LinkedTextGem(PersistentProperties properties)
  {
		text = properties.retrieve("text", "").asString();
    suppress = properties.retrieve("suppress", false).asBoolean();
    majorPointType = properties.retrieve("majorPt", CalculatedArcPoints.MAJOR_POINT_MIDDLE).asInteger();
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
    figureFacet.registerDynamicFacet(hideLinkedTextFacet, HideLinkedTextFacet.class);
    figureFacet.setShowing(!suppress);
	}
  
  public ClipboardCommandsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }

  private class ClipboardCommandsFacetImpl implements ClipboardCommandsFacet
  {
    public boolean hasSpecificDeleteCommand()
    {
      return true;
    }
    
    public Command makeSpecificDeleteCommand()
    {
      return new HideLinkedTextCommand(figureFacet.getFigureReference(), true, "", "");
    }
    
    public Command performPostDeleteTransaction()
    {
      return null;
    }

    public boolean hasSpecificKillCommand()
    {
      return false;
    }

    public Command makeSpecificKillCommand(ToolCoordinatorFacet coordinator)
    {
      return null;
    }
  }
  
  private class HideLinkedTextFacetImpl implements HideLinkedTextFacet
  {
    public Object hideLinkedText(boolean hide)
    {
      // make the change
      suppress = hide;
      figureFacet.setShowing(!suppress);
      figureFacet.performResizingTransaction(figureFacet.getFullBounds());      
      return null;
    }
    
    /**
     * @see com.giroway.jumble.nodefacilities.resizebase.CmdAutoSizeable#unAutoSize(Object)
     */
    public void unHideLinkedText(Object memento)
    {
    }
  }
  
  private class LinkedTextFacetImpl implements LinkedTextFacet
  {
    public ManipulatorFacet getTextEntryManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
    {
      TextManipulatorGem textGem = new TextManipulatorGem(
      		coordinator,
      		"changed port text", "restored port text",
      		text, 
      		font,
      		lineColor,
      		Color.white,
      		TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
      textGem.connectTextableFacet(linkedTextFacet);

	    OriginLineManipulatorGem decorator =
	      new OriginLineManipulatorGem(figureFacet, getMajorPoint());
	    decorator.setDecoratedManipulatorFacet(textGem.getManipulatorFacet());

	    if (text.length() == 0)
	    {
		    EmptyTextManipulatorGem decorator2 =
	    		new EmptyTextManipulatorGem(diagramView, figureFacet, figureFacet.getFullBounds().getTopLeftPoint(), "(?)");
	    	decorator2.setDecoratedManipulatorFacet(decorator.getManipulatorFacet());
	    	return decorator2.getManipulatorFacet();
	    }
	    else
	    	return decorator.getManipulatorFacet();
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
          Command hideCommand =
            new HideLinkedTextCommand(
                figureFacet.getFigureReference(),
                !suppress,
                suppress ? "show " + name + " label" : "hide " + name + " label",
                suppress ? "hide " + name + " label" : "show " + name + " label");
          coordinator.executeCommandAndUpdateViews(hideCommand);
        }
      });
      
      return viewLabelItem;
    }

    public boolean isHidden()
    {
      return suppress;
    }

    /**
		 * @see com.hopstepjump.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#getTextBounds(String)
		 */
		public UBounds getTextBounds(String text)
		{
		  return vetTextResizedExtent(text).addToPoint(new UDimension(0, -1));
		}
	
		/**
		 * @see com.hopstepjump.jumble.figurefacilities.textmanipulationbase.TextResizeVetter#vetTextResizedExtent(String)
		 */
		public UBounds vetTextResizedExtent(String text)
		{
			ZText nameText = new ZText(text, font);	
			return new UBounds(
			    figureFacet.getFullBounds().getTopLeftPoint(),
			    new UBounds(nameText.getBounds()).getDimension());
		}
	
	  public void setText(String newText, Object listSelection, boolean unsuppress)
	  {
	    // need to resize this also, as the change in text may have affected the size
	    text = extractOriginFacet().textChanged(newText, majorPointType);
	    
	    // change the visibility
	    if (unsuppress)
	    {
	    	suppress = false;
	    	figureFacet.setShowing(true);
	    }
	    figureFacet.performResizingTransaction(linkedTextFacet.vetTextResizedExtent(newText));
	  }
	
		/**
		 * @see com.hopstepjump.jumble.figurefacilities.textmanipulationbase.TextableFacet#getFigureFacet()
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
	}
	
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
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
		 * @see com.hopstepjump.jumble.foundation.interfaces.Figure#formView()
		 */
		public ZNode formView()
		{
			UBounds bounds = figureFacet.getFullBounds();

			// place some text indicating the dimensions
			ZText nameText = new ZText(text, font);
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
		 * @see com.hopstepjump.jumble.foundation.interfaces.Figure#getFigureName()
		 */
		public String getFigureName()
		{
			return FIGURE_NAME;
		}
	
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
		 */
		public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
		  ManipulatorFacet primaryFocus = null;
	    if (favoured)
	    {
		    ManipulatorFacet keyFocus = null;
	      TextManipulatorGem textGem = new TextManipulatorGem(coordinator, "changed text", "restored text", text, font, lineColor, Color.white, TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
	      textGem.connectTextableFacet(linkedTextFacet);
	      keyFocus = textGem.getManipulatorFacet();

	      OriginLineManipulatorGem decorator =
		      new OriginLineManipulatorGem(figureFacet, getMajorPoint());
		    decorator.setDecoratedManipulatorFacet(keyFocus);
		    primaryFocus = decorator.getManipulatorFacet();
	    }
	    
	    Manipulators manipulators =
	      new Manipulators(
	          primaryFocus,
	          new LinkedTextSelectionManipulatorGem(
	              figureFacet.getFullBounds(),
	              firstSelected,
	              new UDimension(4, 8)).getManipulatorFacet());
	    
	    return manipulators;
		}

    /**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getContextMenu(IDiagramView, IToolCoordinator)
		 */
		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
		  return null;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			ZText nameText = new ZText(text, font);	
			return new UBounds(nameText.getBounds()).getDimension();
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#makeNodePreviewFigure(PreviewCacheFacet, IDiagram, UPoint, boolean)
		 */
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
      basicGem.connectBasicNodePreviewUnusualSizingFacet(
          new LinkedTextPreviewUnusualSizingGem(
              basicGem.getPreviewFacet()).getBasicNodePreviewUnusualSizingFacet());
			return basicGem.getPreviewFacet();
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
			return false;
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
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
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("text", text));
      properties.add(new PersistentProperty("suppress", suppress, false));
      properties.add(new PersistentProperty("majorPt", majorPointType, CalculatedArcPoints.MAJOR_POINT_MIDDLE));
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
  }
  
  private LinkedTextOriginFacet extractOriginFacet()
  {
    FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
    LinkedTextOriginFacet originFacet =
      (LinkedTextOriginFacet) parent.getDynamicFacet(LinkedTextOriginFacet.class);
    return originFacet;
  }
  
	/**
   * @return
   */
  private UPoint getMajorPoint()
  {
    return extractOriginFacet().getMajorPoint(majorPointType);
  }

	private UDimension getTextHeightAsDimension(ZText text)
	{
		return new UDimension(0, text.getBounds().getHeight());
	}
}
