package com.hopstepjump.jumble.umldiagrams.notenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.Package;

import com.hexidec.ekit.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.selectionbase.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;
import com.l2fprod.common.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;


public final class NoteNodeGem implements Gem
{
  static final String FIGURE_NAME = "note";
  private Comment subject;
  private Font font;
  private String text = "";
  private boolean hideNote;
  private Color lineColor = Color.black;
  private Color fillColor = new Color(255, 255, 204);

  private final static int dogEarOffset = 15;
  private final static int offset = 3; // on all sides, x & y excepting x dog
                                        // ear side
  private BasicNodeFigureFacet figureFacet;
  private BasicNodeAppearanceFacetImpl appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private ResizeVetterFacetImpl resizeVetterFacet = new ResizeVetterFacetImpl();
  private TextableFacetImpl textableFacet = new TextableFacetImpl();
  private LocationFacet locationFacet = new LocationFacetImpl();

  /** handle container stuff */
  private SimpleContainerFacet contents;
  private FigureFacet primitiveContents;
  private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
  private NoteNodeFacet noteNodeFacet = new NoteNodeFacetImpl();
  private boolean useHTML;
  private boolean wordWrap = true;


  public BasicNodeContainerFacet getBasicNodeContainerFacet()
  {
    return containerFacet;
  }

  private class NoteNodeFacetImpl implements NoteNodeFacet
  {
    /*
     * @see com.hopstepjump.jumble.umldiagrams.notenode.NoteNodeFacet#getBoundsAfterExistingContainablesAlter(com.hopstepjump.idraw.foundation.PreviewCacheFacet)
     */
    public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews)
    {
      SimpleContainerPreviewFacet contentsPreviewFacet = (SimpleContainerPreviewFacet) previews.getCachedPreview(
          contents.getFigureFacet()).getDynamicFacet(SimpleContainerPreviewFacet.class);
      // we should always find this
      assert contentsPreviewFacet != null;

      UBounds bounds = figureFacet.getFullBounds();
      UBounds minContentBounds = contentsPreviewFacet.getMinimumBoundsFromPreviews(previews);
      if (minContentBounds == null)
        return bounds;

      return bounds.union(minContentBounds);
    }

    public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds)
    {
      PreviewFacet contentsPreview = previews.getCachedPreview(contents.getFigureFacet());
      contentsPreview.setFullBounds(bounds, true);
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
      List<FigureFacet> list = new ArrayList<FigureFacet>();
      list.add(contents.getFigureFacet());
      return list.iterator(); // this is not a reference, but is a copy, so we
                              // don't need to make it unmodifiable
    }

    public void unAddContents(Object memento)
    {
      // not used -- this has a static set of contents
    }

    public Object removeContents(ContainedFacet[] containable)
    {
      // not used -- this has a static set of contents
      return null;
    }

    public void unRemoveContents(Object memento)
    {
      // not used -- this has a static set of contents
    }

    public Object addContents(ContainedFacet[] containable)
    {
      // not used -- this has a static set of contents
      return null;
    }

    public boolean isWillingToActAsBackdrop()
    {
      return false;
    }

    public boolean directlyAcceptsItems()
    {
      return true;
    }

    /**
     * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
     */
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

    /**
     * @see com.hopstepjump.jumble.foundation.ContainerFacet#addChildPreviewsToCache(PreviewCacheFacet)
     */
    public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
    {
      previewCache.getCachedPreviewOrMakeOne(contents.getFigureFacet());
    }

    public void setShowingForChildren(boolean showing)
    {
      for (Iterator iter = getContents(); iter.hasNext();)
      {
        FigureFacet figure = (FigureFacet) iter.next();
        figure.setShowing(showing);
      }
    }

    public void persistence_addContained(FigureFacet contained)
    {
      // we should only get one of these
      contents = (SimpleContainerFacet) contained.getDynamicFacet(SimpleContainerFacet.class);
      primitiveContents = contained;
      contained.getContainedFacet().persistence_setContainer(this);
    }
  }

  private class LocationFacetImpl implements LocationFacet
  {
    /**
     * @see com.hopstepjump.idraw.figurefacilities.selectionbase.LocationFacet#setLocation(MPackage)
     */
    public Object setLocation()
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // locate to the diagram, or a possible nesting package
      // look upwards, until we find one that has a PackageFacet registered
      Namespace space = (Package) figureFacet.getDiagram().getLinkedObject();
      Namespace currentSpace = (Package) subject.getOwner();
      Namespace containerSpace = repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet
          .getContainedFacet().getContainer());
      if (containerSpace != null)
        space = containerSpace;

      // make sure that the package is not set to be owned by itself somehow
      for (Element owner = space; owner != null; owner = owner.getOwner())
        if (owner == subject)
          return null;

      currentSpace.getOwnedComments().remove(subject);
      space.getOwnedComments().add(subject);

      return new Namespace[]{currentSpace, space};
    }

    public void unSetLocation(Object memento)
    {
      // don't bother if the memento isn't set
      if (memento == null)
        return;

      Namespace[] spaces = (Namespace[]) memento;
      Namespace oldSpace = spaces[0];
      Namespace newSpace = spaces[1];
      newSpace.getOwnedComments().remove(subject);
      oldSpace.getOwnedComments().add(subject);
    }
  }

  private class TextableFacetImpl implements TextableFacet
  {
    public UBounds getTextBounds(String text)
    {
      if (useHTML)
        return vetTextResizedExtent(text).addToPoint(new UDimension(offset, offset)).addToExtent(
            new UDimension(-18, -offset));
      return vetTextResizedExtent(text).addToPoint(new UDimension(offset, offset)).addToExtent(
          new UDimension(-getDogEarOffset() - offset, -offset));
    }

    public UBounds vetTextResizedExtent(String text)
    {
      UBounds fullBounds = figureFacet.getFullBounds();
      UDimension newExtent = vetResizedExtent(text, fullBounds.getDimension(), figureFacet.isAutoSized());
      return new UBounds(fullBounds.getTopLeftPoint(), newExtent);
    }


    public void setText(String newText, Object listSelection, boolean unsuppress)
    {
      subject.setBody(newText);
    }

    public FigureFacet getFigureFacet()
    {
      return figureFacet;
    }

    String getText()
    {
      return text;
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
      return NoteNodeGem.this.vetResizedExtent(textableFacet.getText(), extent, figureFacet.isAutoSized());
    }

    public UBounds vetResizedBounds(DiagramViewFacet diagramView, int corner, UBounds bounds, boolean fromCentre)
    {
      return bounds;
    }

    public void startResizeVet()
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
    	UDimension offset = new UDimension(8, 8);
    	if (figureFacet.getFullBounds().addToPoint(offset).addToExtent(offset.multiply(2).negative()).contains(point))    		
    		return new ToolFigureClassification("top", null);
  		return new ToolFigureClassification(FIGURE_NAME, null);
		}

    public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured,
        boolean firstSelected, boolean allowTYPE0Manipulators)
    {
      ManipulatorFacet keyFocus = null;
      if (favoured)
      {
        TextManipulatorGem textGem = new TextManipulatorGem(
        		coordinator, 
        		"changed note text", "restored note text",
        		textableFacet.getText(),
            font, Color.black, fillColor, useHTML
                ? TextManipulatorGem.TEXT_HTML_TYPE
                : TextManipulatorGem.TEXT_AREA_TYPE);
        textGem.setWordWrap(wordWrap);
        textGem.connectTextableFacet(textableFacet);
        keyFocus = textGem.getManipulatorFacet();
      }

      return new Manipulators(keyFocus,
      		new ResizingManipulatorGem(
      				coordinator,
      				figureFacet,
      				diagramView,
      				figureFacet.getFullBounds(),
      				resizeVetterFacet,
      				firstSelected).getManipulatorFacet());
    }

    public ZNode formView()
    {
      // if the graphical structures aren't set up, then do it now
      UBounds boxBounds;
      boxBounds = figureFacet.getFullBounds();

      UPoint start = boxBounds.getTopRightPoint().add(new UDimension(-getDogEarOffset(), 0));
      ZPolygon noteLine = new ZPolygon(start);
      noteLine.setPenPaint(lineColor);
      noteLine.setClosed(true);
      if (hideNote)
        noteLine.setFillPaint(null);
      else
        noteLine.setFillPaint(fillColor);

      // add the points for the note
      noteLine.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      noteLine.add(boxBounds.getTopLeftPoint());
      noteLine.add(boxBounds.getBottomLeftPoint());
      noteLine.add(boxBounds.getBottomRightPoint());
      noteLine.add(boxBounds.getTopRightPoint().add(new UDimension(0, getDogEarOffset())));
      noteLine.add(start);

      UBounds fullBounds = figureFacet.getFullBounds();
      UBounds bounds = new UBounds(fullBounds.getTopLeftPoint().add(new UDimension(offset, offset)), fullBounds
          .getDimension().subtract(new UDimension(getDogEarX(), offset)));

      JTextComponent textField;
      if (useHTML)
      {
        EkitCore core = TextManipulatorGem.makeEkit(false, textableFacet.getText());
        textField = core.getTextPane();
      }
      else
      {
        JTextArea area = new JTextArea(textableFacet.getText());
        if (wordWrap)
        {
          area.setWrapStyleWord(true);
          area.setLineWrap(true);
        }
        textField = area;
      }

      textField.setBounds((int) bounds.getTopLeftPoint().getX(), (int) bounds.getTopLeftPoint().getY(), (int) bounds
          .getWidth(), (int) bounds.getHeight());
      textField.setMaximumSize(new Dimension((int) bounds.getWidth(), (int) bounds.getHeight()));
      textField.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) bounds.getHeight()));
      if (font != null)
        textField.setFont(font);
      textField.setForeground(Color.black);
      textField.setBackground(ScreenProperties.getTransparentColor());
      textField.setBorder(null);

      ZSwing zswing = new ZSwing(new ZCanvas(), textField);
      ZTransformGroup transform = new ZTransformGroup(new ZVisualLeaf(zswing));
      transform.setTranslation(bounds.getTopLeftPoint().getX(), bounds.getTopLeftPoint().getY());

      // add a transparent box over this so we can pick up correct mouse events
      ZRectangle rect = new ZRectangle(transform.getBounds());
      rect.setFillPaint(ScreenProperties.getTransparentColor());
      rect.setPenPaint(null);
      ZVisualLeaf transparentCover = new ZVisualLeaf(rect);

      // group them
      ZGroup group = new ZGroup();
      if (!hideNote)
      {
        group.addChild(new ZVisualLeaf(noteLine));
        // add the dog ear
        ZPolygon dogEar = new ZPolygon(start);
        dogEar.add(boxBounds.getTopRightPoint().add(new UDimension(-getDogEarOffset(), getDogEarOffset())));
        dogEar.add(boxBounds.getTopRightPoint().add(new UDimension(0, getDogEarOffset())));
        dogEar.setClosed(true);
        dogEar.setFillPaint(fillColor);
        group.addChild(new ZVisualLeaf(dogEar));
      }
      group.addChild(transform);
      group.addChild(transparentCover);

      // add the interpretable properties
      group.setChildrenPickable(false);
      group.setChildrenFindable(false);
      group.putClientProperty("figure", figureFacet);

      return group;
    }

    public UDimension getCreationExtent()
    {
      return vetResizedExtent("", new UDimension(0, 0), figureFacet.isAutoSized());
    }

    private JMenuItem getHideNoteItem(DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      JMenuItem item = new JCheckBoxMenuItem("Hide note");
      item.setSelected(hideNote);
      item.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          Command hideNoteCommand = new AbstractCommand(
              hideNote ? "Showed note" : "Hid note",
              !hideNote ? "Showed note" : "Hid note")
          {
            public void execute(boolean isTop)
            {
              toggle();
            }

            public void unExecute()
            {
              toggle();
            }

            private void toggle()
            {
              hideNote = !hideNote;
              figureFacet.adjusted();
            }
          };
          coordinator.executeCommandAndUpdateViews(hideNoteCommand);
        }
      });
      return item;
    }

    private JMenuItem getChangeHTMLItem(DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      JMenuItem item = new JCheckBoxMenuItem("HTML text");
      item.setSelected(useHTML);
      item.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          useHTML = !useHTML;
          figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(text));
        }
      });
      return item;
    }

    private JMenuItem getToggleWordWrapItem(DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      JMenuItem item = new JCheckBoxMenuItem("Wrap words");
      item.setSelected(wordWrap);
      item.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          wordWrap = !wordWrap;
          figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(text));
        }
      });
      return item;
    }

    private JMenuItem getChangeFontItem(DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      JMenuItem item = new JMenuItem("Change font");
      item.addActionListener(new ActionListener()
      {
        private Command resizeCommand;

        public void actionPerformed(ActionEvent e)
        {
          JFontChooser chooser = new JFontChooser();
          chooser.setSelectedFont(font);
          int chosen = coordinator.invokeAsDialog(null, "Select font", chooser, new JButton[]{new JButton("OK"), new JButton("Cancel")}, null);

          if (chosen == 0)
          {
            font = chooser.getSelectedFont();
            figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(text));
          }
        }
      });
      return item;
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
      popup.add(getHideNoteItem(diagramView, coordinator));
      popup.add(getChangeHTMLItem(diagramView, coordinator));
      popup.add(getChangeFontItem(diagramView, coordinator));      
      popup.add(getToggleWordWrapItem(diagramView, coordinator));
      return popup;
    }

    /**
     * @see com.giroway.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
     */
    public UBounds getAutoSizedBounds(boolean autoSized)
    {
      UBounds fullBounds = figureFacet.getFullBounds();
      UDimension newExtent = vetResizedExtent(textableFacet.getText(), fullBounds.getDimension(), true);
      return ResizingManipulatorGem.formCentrePreservingBoundsExactly(fullBounds, newExtent);
    }

    public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start,
        boolean isFocus)
    {
      // get some size information first
      UBounds bounds = figureFacet.getFullBounds();
      UBounds containerArea = bounds;

      BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
      NotePreviewGem notePreviewGem = new NotePreviewGem(bounds, containerArea);
      basicGem.connectPreviewCacheFacet(previews);
      basicGem.connectFigureFacet(figureFacet);
      basicGem.connectBasicNodePreviewAppearanceFacet(notePreviewGem.getBasicNodePreviewAppearanceFacet());
      basicGem.connectContainerPreviewFacet(notePreviewGem.getContainerPreviewFacet());
      notePreviewGem.connectPreviewFacet(basicGem.getPreviewFacet());
      notePreviewGem.setNoteNodeFacet(noteNodeFacet);
      notePreviewGem.connectPreviewCacheFacet(previews);

      return basicGem.getPreviewFacet();
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
      properties.add(new PersistentProperty("hideNote", hideNote, false));
      properties.add(new PersistentProperty("useHTML", useHTML, false));
      properties.add(new PersistentProperty("wordWrap", wordWrap, true));
      properties.add(new PersistentProperty("font", font, ScreenProperties.getPrimaryFont()));
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
     */
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      if (subject == null || pass != ViewUpdatePassEnum.LAST)
        return;

      // if neither the name or the namespace has changed suppress any command
      if (subject.getBody().equals(text))
        return;

      text = subject.getBody();
      UBounds bounds = textableFacet.vetTextResizedExtent(text);
      figureFacet.performResizingTransaction(bounds);
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
      return subject;
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
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
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }
  }

  public NoteNodeGem(Comment subject, DiagramFacet diagram, String figureId, boolean useHTML, boolean hideNote, boolean wordWrap)
  {
    this.subject = subject;
    this.useHTML = useHTML;
    this.hideNote = hideNote;
    this.wordWrap = wordWrap;
    font = ScreenProperties.getPrimaryFont();
    SimpleContainerGem simpleGem = SimpleContainerGem.createAndWireUp(
        diagram,
        figureId + "_C",
        containerFacet,
        new UDimension(0, 0),
        new UDimension(2, 2),
        null,
        false);
    contents = simpleGem.getSimpleContainerFacet();
    primitiveContents = contents.getFigureFacet();
  }

  public NoteNodeGem(PersistentFigure figure)
  {
    // reconstitute the subject
    subject = (Comment) figure.getSubject();
    text = subject.getBody();
    hideNote = figure.getProperties().retrieve("hideNote", false).asBoolean();
    useHTML = figure.getProperties().retrieve("useHTML", false).asBoolean();
    wordWrap = figure.getProperties().retrieve("wordWrap", true).asBoolean();
    font = figure.getProperties().retrieve("font", ScreenProperties.getPrimaryFont()).asFont();
  }

  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
    return appearanceFacet;
  }

  private UDimension getMinimumExtent()
  {
    return new UDimension(60, 40);
  }

  private UDimension vetResizedExtent(String text, UDimension extent, boolean autoSized)
  {
    double extentWidth = extent.getWidth() - getDogEarX();
    double preferredWidth = Math.max(extentWidth, getMinimumExtent().getWidth());

    // preferring the width, look for the auto-sized height
    JTextComponent textField;
    EkitCore core = null;
    if (useHTML)
    {
      core = TextManipulatorGem.makeEkit(false, text);
      textField = core.getTextPane();
      textField.setPreferredSize(new Dimension((int) preferredWidth, (int) textField.getPreferredSize().getHeight()));
    }
    else
    {
      JTextArea area = new JTextArea(text);
      if (wordWrap)
      {
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
      }
      textField = area;
    }

    // set some other bounds
    textField.setBounds(0, 0, (int) preferredWidth, (int) extent.getHeight());
    textField.setMaximumSize(new Dimension((int) preferredWidth, (int) extent.getHeight()));


    if (font != null)
      textField.setFont(font);
    textField.setForeground(Color.black);
    textField.setBackground(fillColor);
    textField.setBorder(null);

    // construct a zswing component to get this to set the bounds to fit
    // -- NOTE: this is necessary to get the text area to resize correctly --
    // without it this method won't work!!!
    ZCanvas canvas = new ZCanvas();
    ZSwing swing = new ZSwing(canvas, textField);
    // don't ask where the numbers like 18 come from!
    double maxWidth = Math.max(textField.getMinimumSize().getWidth() + getDogEarX(), swing.getBounds().getWidth()
        + (useHTML ? 18 : 0));

    double autoSizedHeight = Math.max(textField.getMinimumSize().getHeight(), getMinimumExtent().getHeight()) + offset;
    autoSizedHeight = Math.max(autoSizedHeight, swing.getBounds().getHeight());
    double wantedHeight = extent.getHeight();
    if (wantedHeight < autoSizedHeight)
      wantedHeight = autoSizedHeight;

    return new UDimension(maxWidth, (int) (autoSized ? autoSizedHeight : wantedHeight));
  }
  

  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
    figureFacet.registerDynamicFacet(textableFacet, TextableFacet.class);
    figureFacet.registerDynamicFacet(locationFacet, LocationFacet.class);
  }

  private int getDogEarX()
  {
    return getDogEarOffset() + offset;
  }

  private int getDogEarOffset()
  {
    return dogEarOffset;
  }
}