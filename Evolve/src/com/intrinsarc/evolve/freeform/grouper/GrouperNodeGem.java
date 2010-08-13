/*
 * Created on Dec 31, 2003 by Andrew McVeigh
 */
package com.intrinsarc.evolve.freeform.grouper;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.evolve.clipboardactions.*;
import com.intrinsarc.evolve.packageview.actions.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulation.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.nodefacilities.resize.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;
import com.intrinsarc.idraw.nodefacilities.style.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * @author Andrew
 */
public class GrouperNodeGem
{
	private Font font = ScreenProperties.getPrimaryFont();
	private Color fillColor;
  public static final Color INITIAL_FILL_COLOR = ScreenProperties.getTransparentColor();

	private String name = "";
	private boolean textAtTop;
	private UBounds minVetBounds;
	private SimpleContainerFacet contents;
	private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
	private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private GrouperNodeFacet grouperFacet = new GrouperNodeFacetImpl();
	private BasicNodeFigureFacet figureFacet;
	private TextableFacetImpl textableFacet = new TextableFacetImpl();
	private ResizeVetterFacetImpl resizeVetterFacet = new ResizeVetterFacetImpl();
  private Comment subject = null;

	private class GroupBoundsDisplayer extends ManipulatorAdapter
	{
		private ZGroup diagramLayer;
		private ZGroup corners;
		
		public void addToView(ZGroup diagramLayer, ZCanvas canvas)
		{
			this.diagramLayer = diagramLayer;
			
			// add some nice corners
			corners = new ZGroup();
			addCorners(corners, getFigureFacet().getFullBounds(), ScreenProperties.getDashedStroke());
			diagramLayer.addChild(corners);
		}

		public void cleanUp()
		{
      if (corners != null)
        diagramLayer.removeChild(corners);
			diagramLayer = null;
			corners = null;
		}

    public void setLayoutOnly()
    {
    }
	}

	/**
	 * @param properties
	 */
	public GrouperNodeGem(PersistentFigure pfig)
	{
		interpretPersistentFigure(pfig);
  }

	private void interpretPersistentFigure(PersistentFigure pfig)
	{
    subject = (Comment) pfig.getSubject();
    PersistentProperties properties = pfig.getProperties();
		name = properties.retrieve("name", "").asString();
		textAtTop = properties.retrieve("textAtTop", false).asBoolean();
    fillColor = properties.retrieve("fill", INITIAL_FILL_COLOR).asColor();
	}

	/**
	 * @param diagram
	 * @param figureId
	 */
	public GrouperNodeGem(Comment subject, DiagramFacet diagram, String figureId, boolean textAtTop, Color fillColor)
	{
    this.subject = subject;
		SimpleContainerGem simpleGem =
			SimpleContainerGem.createAndWireUp(diagram, figureId + "_C",
																				 containerFacet,
                                         new UDimension(10, 10),
                                         new UDimension(0,0),
                                         null,
                                         false);
		contents = simpleGem.getSimpleContainerFacet();
		this.textAtTop = textAtTop;
		this.fillColor = fillColor;
	}

	public static final String FIGURE_NAME = "Grouper";

	/**
	 * @return
	 */
	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
	{
		return appearanceFacet;
	}

	/**
	 * @return
	 */
	public BasicNodeContainerFacet getBasicNodeContainerFacet()
	{
		return containerFacet;
	}

	/**
	 * @param facet
	 */
	public void connectBasicNodeFigureFacet(BasicNodeFigureFacet facet)
	{
		this.figureFacet = facet;
		figureFacet.registerDynamicFacet(textableFacet, TextableFacet.class);
	}

	private class GrouperNodeFacetImpl implements GrouperNodeFacet
	{
		public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews)
		{
	
			GrouperSizeInfo info = makeCurrentSizeInfo();  	
			SimpleContainerPreviewFacet contentsPreviewFacet = (SimpleContainerPreviewFacet) previews.getCachedPreview(contents.getFigureFacet()).getDynamicFacet(SimpleContainerPreviewFacet.class);
	
			// we will always find a contents preview facet
			assert contentsPreviewFacet != null;
			UBounds minContentsBounds = contentsPreviewFacet.getMinimumBoundsFromPreviews(previews);
			info.setMinContentBounds(minContentsBounds);
	
			GrouperSizes sizes = info.makeActualSizes(false);
			return sizes.getOuter();		
		}

		public UBounds getContainmentBounds(UBounds newBounds)
		{
			GrouperSizeInfo info = makeCurrentSizeInfo();
			info.setTopLeft(newBounds.getTopLeftPoint());
			info.setEntire(newBounds.getDimension());
			info.setAutoSized(false);
			
			return info.makeActualSizes(false).getOuter();
		}

    public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds)
    {
      PreviewFacet contentsPreview = previews.getCachedPreview(contents.getFigureFacet());
      contentsPreview.setFullBounds(getContainmentBounds(bounds), true);
    }
	}

	private class ContainerFacetImpl implements BasicNodeContainerFacet
	{
		public boolean insideContainer(UPoint point)
		{
			GrouperSizes sizes = makeCurrentSizeInfo().makeActualSizes(false);
			return sizes.getContents().contains(point);
		}
	
		public Iterator<FigureFacet> getContents()
		{
			List<FigureFacet> list = new ArrayList<FigureFacet>();
			list.add(contents.getFigureFacet());
			return list.iterator();  // this is not a reference, but is a copy, so we don't need to make it unmodifiable
		}
	  
		public void removeContents(ContainedFacet[] containable)
		{
			// not used -- this has a static set of contents
		}
	
		public void addContents(ContainedFacet[] containable)
		{
			// not used -- this has a static set of contents
		}
	
		public boolean isWillingToActAsBackdrop()
		{
			return false;
		}
	  
		public boolean directlyAcceptsItems()
		{
			return true;
		}
		
		public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
		{
			return contents.getFigureFacet().getContainerFacet();
		}
		
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}
	  
		public ContainedFacet getContainedFacet()
		{
			return figureFacet.getContainedFacet();
		}
	  
		public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
		{
			previewCache.getCachedPreviewOrMakeOne(contents.getFigureFacet());
		}
		
		public void setShowingForChildren(boolean showing)
		{
		}
		
		public void persistence_addContained(FigureFacet contained)
		{
			// we should only get one of these
			contents = contained.getDynamicFacet(SimpleContainerFacet.class);
			contained.getContainedFacet().persistence_setContainer(this);
		}

		public void cleanUp()
		{
		}
	}

	private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
	{
		public String getFigureName()
		{
			return FIGURE_NAME;
		}
		
    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}
	
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			GrouperSizeInfo info = makeCurrentSizeInfo();
			GrouperSizes sizes = info.makeActualSizes(false);

			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			GrouperNodePreviewGem containerPreviewGem =
				new GrouperNodePreviewGem(sizes.getOuter(), sizes.getContents());
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			basicGem.connectContainerPreviewFacet(containerPreviewGem.getContainerPreviewFacet());
			containerPreviewGem.connectPreviewFacet(basicGem.getPreviewFacet());
			containerPreviewGem.connectGrouperNodeFacet(grouperFacet);
			containerPreviewGem.connectPreviewCacheFacet(previews);

			return basicGem.getPreviewFacet();
		}
	
		public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
			// make the manipulators
			GrouperSizes sizes = makeCurrentSizeInfo().makeActualSizes(false);
			ManipulatorFacet keyFocus = null;
			if (favoured)
			{
				TextManipulatorGem textGem =
					new TextManipulatorGem(
							coordinator, 
							"changed grouper text", "restored grouper text", name,
							font, Color.black,
							fillColor.equals(ScreenProperties.getTransparentColor()) ? Color.WHITE : fillColor, TextManipulatorGem.TEXT_PANE_CENTRED_TYPE);
				textGem.connectTextableFacet(textableFacet);
				keyFocus = textGem.getManipulatorFacet();
			}
		  
			return
				new Manipulators(
					keyFocus,
					new GroupBoundsDisplayer(),
					new ResizingManipulatorGem(
							coordinator,
							figureFacet,
							diagramView,
							sizes.getOuter(),
							resizeVetterFacet,
							firstSelected).getManipulatorFacet());
		}
	
		public ZNode formView()
		{
			// calculate the sizes
			GrouperSizes sizes = makeCurrentSizeInfo().makeActualSizes(false);

			// create a group			
			ZGroup group = new ZGroup();

			// draw the boundary
			UBounds outer = sizes.getOuter();
			ZRectangle rect = new ZRectangle(outer);
			rect.setFillPaint(fillColor);
			rect.setPenPaint(null);
			group.addChild(new ZVisualLeaf(rect));
			
			// draw possible text
			ZTransformGroup zname = sizes.getZName();
			if (zname != null)
			{
				group.addChild(zname);

				// place a transparent rectangle over "name", so we can get the events
				ZRectangle nameRect = new ZRectangle(sizes.getText());
				nameRect.setFillPaint(ScreenProperties.getTransparentColor());
				nameRect.setPenPaint(null);
				group.addChild(new ZVisualLeaf(nameRect));
			}
			if (fillColor.equals(INITIAL_FILL_COLOR))
				addCorners(group, outer.addToExtent(new UDimension(-1, -1)), ScreenProperties.getDashedStroke());
		
			// add the interpretable properties
			group.setChildrenPickable(false);
			group.setChildrenFindable(false);
			group.putClientProperty("figure", figureFacet);
	
			return group;
		}
	  
	  
		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			if (!diagramView.getDiagram().isReadOnly())
			{
  			popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
        Utilities.addSeparator(popup);
  			popup.add(getTextAtTopMenuItem(coordinator));
        popup.add(BasicNamespaceNodeGem.getChangeColorItem(diagramView, coordinator, figureFacet, fillColor,
        		new SetFillCallback()
						{							
							public void setFill(Color fill)
							{
								fillColor = fill;
							}
						}));
			}
			return popup;
		}
		
		private JMenuItem getTextAtTopMenuItem(final ToolCoordinatorFacet coordinator)
		{
			JCheckBoxMenuItem locateTextItem = new JCheckBoxMenuItem("Place text above");
			locateTextItem.setState(textAtTop);

			locateTextItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// toggle the suppress attributes flag
					coordinator.startTransaction(
								textAtTop  ? "Placed text underneath" : "Placed text above",
								!textAtTop ? "Placed text underneath" : "Placed text above");
					textAtTop = !textAtTop;
					figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(name));
					coordinator.commitTransaction();
				}
			});
			return locateTextItem;
		}
		
		
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			if (!contents.isEmpty())
			{
				resizeVetterFacet.startResizeVet();
				UBounds centredBounds = new UBounds(figureFacet.getFullBounds().getTopLeftPoint(), new UDimension(0,0));
				return resizeVetterFacet.vetFreeResizedBounds(null, ResizingManipulatorGem.BOTTOM_RIGHT_CORNER, centredBounds, false);
			}

			GrouperSizeInfo info = makeCurrentSizeInfo();
			info.setAutoSized(true);
			UBounds autoBounds =
				ResizingManipulatorGem.formCentrePreservingBoundsExactly(
					figureFacet.getFullBounds(),
					info.makeActualSizes(false).getOuter().getDimension());

			return autoBounds;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return GrouperSizeInfo.MIN_OUTER_DIMENSIONS;
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
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return makeCurrentSizeInfo().makeActualSizes(false).getOuter();
		}

		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			GrouperSizeInfo info = makeCurrentSizeInfo();
			GrouperSizes sizes = info.makeActualSizes(false);
      if (!diagramResize)
        return sizes.getOuter();
			return formCentredBounds(info, sizes.getContents()).getOuter();
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("name", name, ""));
			properties.add(new PersistentProperty("textAtTop", textAtTop, false));
      properties.add(new PersistentProperty("fill", fillColor, INITIAL_FILL_COLOR));
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
    /**
     * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
     */
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      if (subject == null || pass != ViewUpdatePassEnum.LAST)
        return;
      
      // if neither the name or the namespace has changed suppress any command
      if (subject.getBody().equals(name))
        return;
      
      name = subject.getBody();
      UBounds bounds = textableFacet.vetTextResizedExtent(name);
      figureFacet.performResizingTransaction(bounds);
    }

		public void middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
			// want to open the parent diagram
				new CursorWaitingAction(
					new GotoDiagramAction(
						figureFacet.getDiagram(),
						null,
						false,
						true,
						true),
					coordinator,
					0).actionPerformed(null);
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public Object getSubject()
		{
			return subject;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
		 */
		public boolean hasSubjectBeenDeleted()
		{
      // cannot delete something with no subject
      if (subject == null)
        return false;
      
      return subject.isThisDeleted();
		}

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
      if (effect.equals("exportGIFImage"))
        exportImage("gif", parameters, coordinator);
      else
      if (effect.equals("exportPNGImage"))
        exportImage("png", parameters, coordinator);
      else
      if (effect.equals("exportJPGImage"))
        exportImage("jpg", parameters, coordinator);
      else
      if (effect.equals("exportGroupedImage"))
      {
        String startTag = "export ";
        if (name.startsWith(startTag) && (name.endsWith(".eps") || name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".png")))
        {
          File directory = (File) parameters[0];
          String fileName = name.substring(startTag.length());
  
          DiagramViewFacet view = formDiagramViewOfInsides(coordinator);

          // copy to clipboard
          if (view != null)
          {
            if (name.endsWith(".eps"))
              ClipboardViewContextGem.saveAsEPS(
                  new File(directory, fileName),
                  view.getCanvas(),
                  view.getDrawnBounds());
            else
            {
              int length = fileName.length();
              ClipboardViewContextGem.saveAsImage(
                  new File(directory, fileName),
                  view.getCanvas(),
                  view.getDrawnBounds(),
                  fileName.substring(length - 3, length));
            }
          }
        }
      }
    }
    
    private void exportImage(String type, Object[] parameters, ToolCoordinatorFacet coordinator)
    {
      // the first parameter is the file path to save the image into
      DiagramViewFacet view = formDiagramViewOfInsides(coordinator);
      view.getCanvas().getDrawingSurface().setRenderQuality(ZDrawingSurface.RENDER_QUALITY_MEDIUM);
      File directory = (File) parameters[0];
      String fileName = (String) parameters[1];

      ClipboardViewContextGem.saveAsImage(
          new File(directory, fileName),
          view.getCanvas(),
          view.getDrawnBounds(),
          type);
    }

    private DiagramViewFacet formDiagramViewOfInsides(ToolCoordinatorFacet coordinator)
    {
      // get the figures to include
      Collection<String> includedFigureIds = new ArrayList<String>();
      final Set<FigureFacet> initialFigures = new HashSet<FigureFacet>();
      for (Iterator iter = contents.getFigureFacet().getContainerFacet().getContents(); iter.hasNext();)
        initialFigures.add((FigureFacet) iter.next());
      Set<FigureFacet> figuresToCopy = new HashSet<FigureFacet>();
      
      final ChosenFiguresFacet chosenFiguresFacet = new ChosenFiguresFacet()
      {
        public boolean isChosen(FigureFacet figure)
        {
          return initialFigures.contains(figure);
        }          
      };        
      // now, compute the contained closure
      for (FigureFacet figure : initialFigures)
      {
        ClipboardFacet clip = figure.getClipboardFacet();
        if (clip != null)
          clip.addIncludedInCopy(figuresToCopy, chosenFiguresFacet, false);
      }        
      // copy over the ids
      for (FigureFacet figure : figuresToCopy)
        includedFigureIds.add(figure.getId());
      // don't progress if this is empty
      if (includedFigureIds.isEmpty())
        return null;
  
      // copy the insides to the clipboard
      DiagramFacet clipboard = GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE);
      DiagramFacet here = figureFacet.getDiagram();
      CopyAction.CopyCommandGenerator.copyToClipboard(clipboard, here, includedFigureIds);
    //  coordinator.executeForPreview(command, true, false);
      
      // now, save the clipboard to the directory in EPS format
      // make a new diagram view, and copy it to the system clipboard as an image
      ZCanvas canvas = new ZCanvas();
  
      // diagram view -- we only need the canvas here
      canvas.setOpaque(false);
      DiagramViewFacet view = new BasicDiagramViewGem(
          clipboard,
          null,
          canvas,
          new UDimension(1, 1),
          new Color(0, 0, 0, 0),
          false).getDiagramViewFacet();
      return view;
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
	
	private class TextableFacetImpl implements TextableFacet
	{
    public void setText(String newText, Object listSelection, boolean unsuppress)
    {
      subject.setBody(newText);
      name = newText;
	    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(name));
    }
  
		/**
		 * vetting for the text and resizing manipulators
		 * 
		 */
		public UBounds vetTextResizedExtent(String name)
		{
			return vetTextChange(name).getOuter();
		}
	
		public UBounds getTextBounds(String name)
		{
			if (name.length() == 0)
				return figureFacet.getFullBounds();
			return vetTextChange(name).getText();
		}
	
		private GrouperSizes vetTextChange(String name)
		{
			// get the old sizes, the new sizes and then get the preferred topleft.  Then, get the sizes with this as the topleft point!!
			GrouperSizeInfo info = makeCurrentSizeInfo();
			info.setName(name);
			GrouperSizes newSizes = info.makeActualSizes(false);
	
			// centre this
			UBounds possibleBounds = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), newSizes.getOuter().getDimension());
			if (possibleBounds.getX() < newSizes.getOuter().getX())
			{
				info.setTopLeft(possibleBounds.getPoint());  
				return info.makeActualSizes(false);
			}
			else
				return newSizes;
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
	
	private class ResizeVetterFacetImpl implements ResizeVetterFacet
	{
		public UDimension vetResizedExtent(UBounds bounds)
		{
			UDimension extent = bounds.getDimension();
			if (!contents.isEmpty())
				return extent;

			GrouperSizeInfo info = makeCurrentSizeInfo();
			info.setAutoSized(true);
			GrouperSizes sizes = info.makeActualSizes(false);
			return sizes.getOuter().getDimension().maxOfEach(extent);
		}
	  
		public void startResizeVet()
		{
			minVetBounds = null;
		}
		
		public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
		{
			if (figureFacet.isAutoSized())
			{
				GrouperSizeInfo info = makeCurrentSizeInfo();
				return info.makeActualSizes(false).getOuter();
			}	
			return vetFreeResizedBounds(view, corner, bounds, fromCentre);
		}
	   
		private UBounds vetFreeResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
		{
			if (contents.isEmpty())
				return bounds;
	
			// NOTE: the hard code is now moved into SimpleContainer etc...
			if (minVetBounds == null)
			{
				// debug code -- display on the screen
				minVetBounds = contents.getMinimumResizeBounds(new ContainerSizeCalculator()
				{
					public ContainerSizeInfo makeInfo(UPoint topLeft, UDimension extent, boolean autoSized)
					{
						GrouperSizeInfo info = makeCurrentSizeInfo();
						info.setTopLeft(topLeft);
						info.setEntire(extent);
						info.setAutoSized(autoSized);
						return info;
					}
				}, corner, bounds, figureFacet.getFullBounds().getTopLeftPoint(), fromCentre);
			}
	
			// return the bounds that are bigger or equal to the auto bounds (on a per-dimension basis)
			UPoint topLeft = bounds.getTopLeftPoint().minOfEach(minVetBounds.getTopLeftPoint());
			UPoint bottomRight = bounds.getBottomRightPoint().maxOfEach(minVetBounds.getBottomRightPoint());
			
			// calculate the sizes, just to get the tab height
			return new UBounds(topLeft, bottomRight.subtract(topLeft));
		}
	}

	/**
	 * add corners to a ZGroup
	 * @param corners the group to add the corners to
	 * @param outer the outer bounds
	 */
	private void addCorners(ZGroup corners, UBounds outer, Stroke stroke)
	{
		UDimension horizOffset = new UDimension(20,0);
		UDimension vertOffset = new UDimension(0,20);
			
		corners.addChild(makeLine(outer.getPoint(), horizOffset, stroke));
		corners.addChild(makeLine(outer.getPoint(), vertOffset, stroke));
			
		corners.addChild(makeLine(outer.getTopRightPoint(), horizOffset.negative(), stroke));
		corners.addChild(makeLine(outer.getTopRightPoint(), vertOffset, stroke));
			
		corners.addChild(makeLine(outer.getBottomLeftPoint(), horizOffset, stroke));
		corners.addChild(makeLine(outer.getBottomLeftPoint(), vertOffset.negative(), stroke));
			
		corners.addChild(makeLine(outer.getBottomRightPoint(), horizOffset.negative(), stroke));
		corners.addChild(makeLine(outer.getBottomRightPoint(), vertOffset.negative(), stroke));
	}

	/**
	 * @param point
	 * @param horizOffset
	 * @return
	 */
	private ZNode makeLine(UPoint point, UDimension offset, Stroke stroke)
	{
		ZLine line = new ZLine(point, point.add(offset));
		line.setPenPaint(new Color(0, 0, 0, 50));
		if (stroke != null)
			line.setStroke(stroke);
		return new ZVisualLeaf(line);
	}

	/**
	 * geometry calculations
	 * 
	 */
	private GrouperSizeInfo makeCurrentSizeInfo()
	{
		UBounds fullBounds = figureFacet.getFullBounds();
		
		return new GrouperSizeInfo(fullBounds.getTopLeftPoint(), fullBounds.getDimension(),
													     figureFacet.isAutoSized(), font, name,
													     contents.isEmpty() ? null : contents.getMinimumBounds(),
													     textAtTop); 
	}
	
	// finish of geometry calculations
	  
	private GrouperSizeInfo makeCurrentInfoFromPreviews(PreviewCacheFacet previews)
	{
		SimpleContainerPreviewFacet contentsPreviewFacet = (SimpleContainerPreviewFacet) previews.getCachedPreview(contents.getFigureFacet()).getDynamicFacet(SimpleContainerPreviewFacet.class);
		// we should always have a contents preview facet as part of the previews
		assert contentsPreviewFacet != null;

		UBounds minContentBounds = contentsPreviewFacet.getMinimumBoundsFromPreviews(previews);
	
		GrouperSizeInfo info = makeCurrentSizeInfo();
		info.setMinContentBounds(minContentBounds);
		return info;
	}
	
	private GrouperSizes formCentredBounds(GrouperSizeInfo info, UBounds contentBoundsToPreserve)
	{
		GrouperSizes sizes = info.makeActualSizes(false);
		
		// ok to simply recentre and return, if the contents are empty
		if (contents.isEmpty())
		{
			UBounds centred =
				ResizingManipulatorGem.formCentrePreservingBoundsExactly(
					figureFacet.getFullBounds(), sizes.getOuter().getDimension());
					
			info.setTopLeft(centred.getTopLeftPoint());
			return info.makeActualSizes(false);
		}
			
		// if this is autosized, don't move in the top left
		if (figureFacet.isAutoSized())
			return sizes;
			
		// case 3: preserve the contents region if we can, by pushing the topleft up or down, and
		// trying to centre horizontally
		info.setMinContentBounds(contentBoundsToPreserve);
		UBounds newBounds = info.makeActualSizes(false).getOuter();
		
		// adjust the new top left to make the width centred
		double widthDifference = (newBounds.getWidth() - contentBoundsToPreserve.getWidth())/2;
		double heightDifference = sizes.getContents().getHeight() - contentBoundsToPreserve.getHeight();
		info.setTopLeft(sizes.getOuter().getPoint().subtract(new UDimension(widthDifference, -heightDifference)));
		info.setEntire(info.getEntire().subtract(new UDimension(0, heightDifference)));
		
		return info.makeActualSizes(false);
	}
	
	public ResizeVetterFacet getResizeVetterFacet()
	{
		return resizeVetterFacet;
	}
	
	public FigureFacet getFigureFacet()
	{
		return figureFacet;
	}
}
