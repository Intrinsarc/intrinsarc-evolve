package com.hopstepjump.jumble.umldiagrams.freetext;

import java.awt.*;
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
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class FreetextNodeGem implements Gem
{
  static final String FIGURE_NAME = "free text";
  private Color lineColor = Color.black;
  private Font font = ScreenProperties.getPrimaryFont();
  private String text;
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private TextableFacet textableFacet = new TextableFacetImpl();
  private BasicNodeFigureFacet figureFacet;  

  public FreetextNodeGem()
  {
    this.text = "";
  }
  
  public FreetextNodeGem(PersistentFigure pfig)
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
      text = newText;
	    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(text));
    }
  
    /**
     * @see com.hopstepjump.jumble.figurefacilities.textmanipulationbase.TextableFacet#getFigureFacet()
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
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  { 
    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
     */
    public UBounds getAutoSizedBounds(boolean autoSized)
    {
      return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), getCreationExtent());
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
        TextManipulatorGem textGem = new TextManipulatorGem(coordinator, "changed measure box text", "restored measure box text", text, font, lineColor, Color.white, TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
        textGem.connectTextableFacet(textableFacet);
        keyFocus = textGem.getManipulatorFacet();
        primaryFocus = keyFocus;
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
      String actualText = text.length() == 0 ? "a" : text;
      ZText nameText = new ZText(actualText, font); 
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
      return figureFacet.getFullBounds();
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
     */
    public void addToPersistentProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("text", text));
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
    public void middleButtonPressed(ToolCoordinatorFacet coordinator)
    {
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
			return new ToolFigureClassification(FIGURE_NAME, null);
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }
}