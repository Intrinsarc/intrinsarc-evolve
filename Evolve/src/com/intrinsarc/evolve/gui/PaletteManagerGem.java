package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.freeform.grouper.*;
import com.intrinsarc.evolve.freeform.image.*;
import com.intrinsarc.evolve.freeform.measurebox.*;
import com.intrinsarc.evolve.repositorybrowser.*;
import com.intrinsarc.evolve.umldiagrams.associationarc.*;
import com.intrinsarc.evolve.umldiagrams.baseline.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.evolve.umldiagrams.colors.*;
import com.intrinsarc.evolve.umldiagrams.connectorarc.*;
import com.intrinsarc.evolve.umldiagrams.containmentarc.*;
import com.intrinsarc.evolve.umldiagrams.dependencyarc.*;
import com.intrinsarc.evolve.umldiagrams.freetext.*;
import com.intrinsarc.evolve.umldiagrams.implementationarc.*;
import com.intrinsarc.evolve.umldiagrams.inheritancearc.*;
import com.intrinsarc.evolve.umldiagrams.lifeline.*;
import com.intrinsarc.evolve.umldiagrams.messagearc.*;
import com.intrinsarc.evolve.umldiagrams.nodenode.*;
import com.intrinsarc.evolve.umldiagrams.notelinkarc.*;
import com.intrinsarc.evolve.umldiagrams.notenode.*;
import com.intrinsarc.evolve.umldiagrams.packagenode.*;
import com.intrinsarc.evolve.umldiagrams.portnode.*;
import com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode.*;
import com.intrinsarc.evolve.umldiagrams.sequencesection.*;
import com.intrinsarc.evolve.umldiagrams.stereotypenode.*;
import com.intrinsarc.evolve.umldiagrams.substitutionarc.*;
import com.intrinsarc.evolve.umldiagrams.tracearc.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.creation.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.figurefacilities.selection.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.creation.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.palette.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public class PaletteManagerGem
{
  private static final ImageIcon FOLDER = IconLoader.loadIcon("folder.png");
  public static final String FEATURE_FOCUS = "Feature focus";
  public static final String COMPONENT_FOCUS = "Component focus";
  public static final String STATE_FOCUS = "State focus";
  public static final String BEHAVIOUR_FOCUS = "Behaviour focus";
  public static final String PROFILE_FOCUS = "Profile focus";
  public static final String DOCUMENTATION_FOCUS = "Documentation focus";
  public static final String CLASS_FOCUS = "Class focus";
  public static final String MISCELLANEOUS_FOCUS = "Miscellaneous focus";

  private RichPalette tools;
  private IRichPaletteEntry select;
  private IRichPaletteEntry lastSelect;
  private ToolFacet current;
  private boolean multiTool;
  private DiagramViewFacet attachedTo;
  private JComponent paletteComponent;
  private UPoint lastMouseLocation;
  private ZNode mouseNode;
  private ToolCoordinatorFacet coordinator;
  private KeyListener keyListener = new KeyListenerImpl();
  private ZMouseListener noToolsPopupListener = new NoToolsPopupListener();
  private ZMouseListener mouseEventsListener = new MouseEventsListener();
  private PaletteManagerFacet paletteManager = new PaletteManagerFacetImpl();
  private ToolClassificationFacet toolClassifier = new ToolClassificationFacetImpl();
  
  private ZCanvas canvas;
  private SmartMenuBarFacet smartMenuBar;
  
  public PaletteManagerGem()
  {
  }
  
  private class ToolClassificationFacetImpl implements ToolClassificationFacet
  {
		public List<ToolClassification> getToolClassifications()
		{
			return tools.getToolClassifications();
		}

		public void setMultiTool(boolean multi)
		{
			multiTool = multi;
		}  	
  }
  
  private class KeyListenerImpl implements KeyListener
  {
    public void keyPressed(KeyEvent event)
    {
      if (!event.isControlDown() && current != null)
        current.keyPressed(event);

      // if this is shift, go into spring-loaded mode (see the tidwell book "designing interfaces")
      if (event.isShiftDown())
      {
        if ((event.getKeyCode() & ~KeyEvent.VK_ALT) == 0)
          multiTool = true;
        else
          // have to do this to deal with the fact that pressing shift-fn key and releasing quickly doesn't give
          // a shift up event :-(
          multiTool = false;
      }
    }
  
    public void keyTyped(KeyEvent event)
    {
    	if (event.getKeyChar() == ' ')
    		return;
    	
      // delegate to the tool if no action matches
      if (!event.isControlDown())  // avoid ctrl, due to ctrl-D etc
      {
        if (current != null && current.wantsKey(event))
          current.keyTyped(event);
        else
        {
          // choose a palette entry, starting from the entry currently selected
          IRichPaletteEntry next = tools.getEntryByHotkey(event.getKeyChar());
          if (next != null)
          {
            tools.selectEntry(next);
            setCurrentCursor(next);
          }
          else
          {
            tools.selectEntry(lastSelect);
            setCurrentCursor(lastSelect);
          }
        }
      }
    }

    public void keyReleased(KeyEvent event)
    {
      if ((multiTool && !event.isShiftDown() && event.getKeyChar() == KeyEvent.CHAR_UNDEFINED) || event.getKeyCode() == KeyEvent.VK_ESCAPE)
        paletteManager.activateLatestSelectionTool();
      else
        if (current != null)
        	current.keyReleased(event);
      multiTool = false;
    }
  }
  
  private class NoToolsPopupListener extends ZMouseAdapter
  {
    public void mousePressed(ZMouseEvent e)
    {
      if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
      {
        JPopupMenu noToolsPopup = attachedTo.makeContextMenu(coordinator);
        if (noToolsPopup != null)
        {
          noToolsPopup.setLabel("Context menu");
          noToolsPopup.setBorder(new BevelBorder(BevelBorder.RAISED));
          noToolsPopup.show(canvas, e.getX(), e.getY());
        }
      }
    }
  }

  private class MouseEventsListener extends ZMouseAdapter
  {
    public void mousePressed(ZMouseEvent e)
    {
      lastMouseLocation = new UPoint(e.getLocalPoint());
    }
  }
  
  private class PaletteManagerFacetImpl implements PaletteManagerFacet
  {
    public void activateLatestSelectionTool()
    {
      IRichPaletteEntry latest = (lastSelect != null) ? lastSelect : select;
      tools.selectEntry(latest);
      setCurrentCursor(latest);
      multiTool = false;  // just in case
    }

    public void attachTo(DiagramViewFacet diagramView, ZCanvas newCanvas, ZNode newMouseNode)
    {
      // if we are already attached, do nothing
      if (attachedTo == diagramView)
        return;
      
      detach();

      attachedTo = diagramView;
      canvas = newCanvas;
      mouseNode = newMouseNode;
      
      boolean isUsingTools = diagramView.isUsingTools();
      if (isUsingTools)
        activateLatestSelectionTool();
      else
        mouseNode.addMouseListener(noToolsPopupListener);
      mouseNode.addMouseListener(mouseEventsListener);
      diagramView.addKeyListenerJustOnce(keyListener);
      
      // regenerate the menus
      smartMenuBar.removeSmartMenuContributorFacet("diagram");
      smartMenuBar.addSmartMenuContributorFacet("diagram", diagramView.getSmartMenuContributorFacet());
      smartMenuBar.rebuild();
      refreshEnabled();
    }
    
    private void detach()
    {
      // if we aren't using tools, remove the popup mouse listener as we installed it before
      if (attachedTo != null)
      {
        if (attachedTo.isUsingTools())
          mouseNode.removeMouseListener(noToolsPopupListener);
        mouseNode.removeMouseListener(mouseEventsListener);
        // deactivate the current tool
        attachedTo.setCurrentTool(null, coordinator);
      }

      attachedTo = null;
    }

    public UPoint getLastMouseLocation()
    {
      return lastMouseLocation;
    }

    public void reestablishCurrentTool()
    {
      if (attachedTo != null)
        attachedTo.setCurrentTool(current, coordinator);
    }

    public void toolFinished(ZMouseEvent e, boolean stopMultiTool)
    {
      // if shift is up, change back to the selection tool
      if (e == null || !multiTool || stopMultiTool)
       activateLatestSelectionTool();
    }

    public DiagramViewFacet getCurrentDiagramView()
    {
      return attachedTo;
    }

    public JComponent getPaletteComponent()
    {
      if (paletteComponent != null)
        return paletteComponent;
      paletteComponent = createPalette().getComponent();
      return paletteComponent;
    }

    public void refreshEnabled()
    {
      // get the current package, and see if it is readonly
      DiagramFacet diagram = attachedTo.getDiagram();
      
      boolean readOnly = attachedTo.getDiagram().isClipboard() || isCurrentDiagramReadOnly();
      
      if (tools.isEnabled() == readOnly)
      {
        tools.setEnabled(!readOnly);
        
        // if this is readonly, clear out the current tool
        if (diagram.isClipboard())
          current = null;
        else
          activateLatestSelectionTool();
      }
    }

		public void setFocus(String focus)
		{
			tools.setFocus(focus);
		}

		public String getFocus()
		{
			return tools.getFocus();
		}
  }
  
  private boolean isCurrentDiagramReadOnly()
  {
    return attachedTo.getDiagram().isReadOnly();
  }
  
  public RichPalette createPalette()
  {
    if (tools != null)
      return tools;
    
    GlobalPopupMenuFacet popupFacet = new GlobalPopupMenuFacet()
    {
      public void addToContextMenu(JPopupMenu popupMenu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator, FigureFacet figure)
      {
        boolean readonly = GlobalSubjectRepository.repository.isReadOnly((Element) figure.getSubject());
        Object subject = figure.getSubject();
        if (subject instanceof Element)
          new StereotypeChanger().createMenu(popupMenu, coordinator, (Element) subject, readonly);
      }       
    };

    // make the bespoke creators
    NodeCreateFacet compositeShortcutCreator = makeCompositeShortcutCreator();
    ClassCreatorGem compositeCreator = new ClassCreatorGem();
    compositeCreator.setSuppressOperations(true);
    compositeCreator.setAutoSized(false);
    compositeCreator.setComponentKind(ComponentKindEnum.NORMAL);
    compositeCreator.setStereotype(CommonRepositoryFunctions.COMPONENT);
    NodeCreateFacet placeholderCreator = makePlaceholderCreator(false);
    NodeCreateFacet factoryCreator = makeFactoryCreator(false);
    InterfaceCreatorGem interfaceShortcutCreator = new InterfaceCreatorGem();
    interfaceShortcutCreator.setStereotype(CommonRepositoryFunctions.INTERFACE);
    interfaceShortcutCreator.setSuppressOperations(true);
    interfaceShortcutCreator.setAutosized(true);
    InterfaceCreatorGem interfaceCreator = new InterfaceCreatorGem();
    interfaceCreator.setStereotype(CommonRepositoryFunctions.INTERFACE);
    ConnectorCreatorGem delegateConnectorCreator = new ConnectorCreatorGem();
    delegateConnectorCreator.setDelegate(true);
    ConnectorCreatorGem portLinkCreator = new ConnectorCreatorGem();
    portLinkCreator.setPortLink(true);

    tools = new RichPalette();
    {
      RichPaletteCategory palette = new RichPaletteCategory(null, "Selection", true, null);

      SelectionToolGem selectionToolGem = new SelectionToolGem(false);
      selectionToolGem.connectGlobalPopupMenuFacet(popupFacet);
      selectionToolGem.connectToolClassificationFacet(toolClassifier);
      palette.addEntry(select = makeEntry(false, "select.png", "Select", selectionToolGem.getToolFacet(), true, "", ""));
      tools.addCategory(palette);
    }

    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Notes", null);
      palette.addEntry(makeEntry(true, "note.png",        "Note",               makeNodeCreateTool(retrieveNodeRecreator(NoteCreatorGem.NAME)), "top"));
      NoteCreatorGem htmlCreator = new NoteCreatorGem();
      htmlCreator.setAutoSized(false);
      htmlCreator.setUseHTML(true);
      htmlCreator.setHideNote(true);
      palette.addEntry(makeEntry(false, "notelink.gif",    "Note link",         new ArcCreateToolGem(retrieveArcRecreator(NoteLinkCreatorGem.NAME)).getToolFacet(), "note"));
      palette.addEntry(makeEntry(true, "grouper.png",     "Grouper",            makeNodeCreateTool(retrieveNodeRecreator(GrouperCreatorGem.NAME)), "top"));
      palette.addEntry(makeEntry(true, "doc-image.png",   "Image",              makeNodeCreateTool(retrieveNodeRecreator(ImageCreatorGem.NAME)), "top"));
      tools.addCategory(palette);
    }
    
    if (Evolve.ADVANCED)
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Documentation", new String[]{DOCUMENTATION_FOCUS});

      PackageCreatorGem topCreator = new PackageCreatorGem(true);
      topCreator.setStereotype(CommonRepositoryFunctions.DOCUMENTATION_TOP);
      topCreator.setDisplayOnlyIcon(true);
      palette.addEntry(makeEntry(true, "doc-top.png",     "Top",        makeNodeCreateTool(topCreator.getNodeCreateFacet()), "top"));
      
      PackageCreatorGem includedCreator = new PackageCreatorGem(true);
      includedCreator.setDisplayOnlyIcon(true);
      includedCreator.setStereotype(CommonRepositoryFunctions.DOCUMENTATION_INCLUDED);
      palette.addEntry(makeEntry(true, "doc-included.png",     "Included",   makeNodeCreateTool(includedCreator.getNodeCreateFacet()), "top"));
      
      GrouperCreatorGem seeAlsoCreator = new GrouperCreatorGem();
      seeAlsoCreator.setStereotype(CommonRepositoryFunctions.DOCUMENTATION_SEE_ALSO);
      seeAlsoCreator.setTextAtTop(true);
      seeAlsoCreator.setText("See also:");
      palette.addEntry(makeEntry(true, "doc-see-also.png",     "See also",   makeNodeCreateTool(seeAlsoCreator.getNodeCreateFacet()), "top"));
      
      GrouperCreatorGem figureCreator = new GrouperCreatorGem();
      figureCreator.setStereotype(CommonRepositoryFunctions.DOCUMENTATION_FIGURE);
      palette.addEntry(makeEntry(true, "doc-figure.png",     "Figure",   makeNodeCreateTool(figureCreator.getNodeCreateFacet()), "top"));
      
      GrouperCreatorGem imageGalleryCreator = new GrouperCreatorGem();
      imageGalleryCreator.setStereotype(CommonRepositoryFunctions.DOCUMENTATION_IMAGE_GALLERY);
      imageGalleryCreator.setTextAtTop(true);
      imageGalleryCreator.setText("Images");
      
      NoteCreatorGem htmlCreator = new NoteCreatorGem();
      htmlCreator.setAutoSized(false);
      htmlCreator.setUseHTML(true);
      htmlCreator.setHideNote(true);
      palette.addEntry(makeEntry(true, "doc-image.png",   "Image",            makeNodeCreateTool(retrieveNodeRecreator(ImageCreatorGem.NAME)), "top"));
      palette.addEntry(makeEntry(true, "photos.png",      "Image gallery",    makeNodeCreateTool(imageGalleryCreator.getNodeCreateFacet()), "top"));
      palette.addEntry(makeEntry(true, "htmlnote.png",    "HTML note",        makeNodeCreateTool(htmlCreator.getNodeCreateFacet()), "top"));
      palette.addEntry(makeEntry(true, "measure.gif",    "Measure box",    			makeNodeCreateTool((retrieveNodeRecreator(MeasureBoxCreatorGem.NAME))), "top"));
      palette.addEntry(makeEntry(true, "text_smallcaps.png",    "Free text",    makeNodeCreateTool((retrieveNodeRecreator(FreetextCreatorGem.NAME))), "top"));
      tools.addCategory(palette);
    }
    
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Package", null);
      
		  palette.addEntry(makeEntry(true, "relaxed-stratum.png", "Stratum", makeNodeCreateTool(makeRelaxedStratumCreator()), "top", null));      
      palette.addEntry(makeEntry(true, "package.png",     "Package",              makeNodeCreateTool(retrieveNodeRecreator(PackageCreatorGem.NAME)), "top"));
      palette.addEntry(makeEntry(false, "dependency.png", "Dependency",           new ArcCreateToolGem(retrieveArcRecreator(DependencyCreatorGem.NAME)).getToolFacet(), "namespace,element"));
      tools.addCategory(palette);
    }
    
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Evolution", new String[]{COMPONENT_FOCUS, FEATURE_FOCUS, STATE_FOCUS, PROFILE_FOCUS});
      palette.addEntry(makeEntry(false, "resemblance.png",  "Resemble",         new ArcCreateToolGem(SubstitutionUtilities.makeResemblanceCreator().getArcCreateFacet()).getToolFacet(), "classifier"));
      palette.addEntry(makeEntry(false, "replacement.png",  "Replace",         new ArcCreateToolGem(SubstitutionUtilities.makeReplacementCreator().getArcCreateFacet()).getToolFacet(), "classifier"));
      palette.addEntry(makeEntry(false, "evolution.png",  "Evolve",             new ArcCreateToolGem(SubstitutionUtilities.makeEvolutionCreator().getArcCreateFacet()).getToolFacet(), "classifier"));
      tools.addCategory(palette);
    }

    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Feature", new String[]{FEATURE_FOCUS});
      
      palette.addEntry(makeEntry(true, "feature.png",     						"Feature",              makeNodeCreateTool(retrieveNodeRecreator(RequirementsFeatureCreatorGem.NAME)), "top"));
      palette.addEntry(makeEntry(false, "mandatory-subfeature.png", 	"Mandatory subfeature", new ArcCreateToolGem(new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.MANDATORY_LITERAL).getArcCreateFacet()).getToolFacet(), "requirementsfeature"));
      palette.addEntry(makeEntry(false, "optional-subfeature.png", 		"Optional subfeature",  new ArcCreateToolGem(new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.OPTIONAL_LITERAL).getArcCreateFacet()).getToolFacet(), "requirementsfeature"));
      palette.addEntry(makeEntry(false, "one-or-more-subfeature.png", "One or more",     			new ArcCreateToolGem(new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.ONE_OR_MORE_LITERAL).getArcCreateFacet()).getToolFacet(), "requirementsfeature"));
      palette.addEntry(makeEntry(false, "one-of-subfeature.png", 			"One of (Alternative)", new ArcCreateToolGem(new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.ONE_OF_LITERAL).getArcCreateFacet()).getToolFacet(), "requirementsfeature"));
      palette.addEntry(makeEntry(true, "component.png",    "Component",            						makeNodeCreateTool(compositeCreator.getNodeCreateFacet()), "top"));
      palette.addEntry(makeEntry(true, "component.png",    "Component (small)",    						makeNodeCreateTool(compositeShortcutCreator), "top"));
      palette.addEntry(makeEntry(false, "trace.png", 									"Trace",           		  new ArcCreateToolGem(new TraceCreatorGem().getArcCreateFacet()).getToolFacet(), "class"));
      
      tools.addCategory(palette);
    }

    { 
      {
        RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Interface", new String[]{COMPONENT_FOCUS, STATE_FOCUS, CLASS_FOCUS});
        palette.addEntry(makeEntry(true, "interface.png",    "Interface",            makeNodeCreateTool(interfaceCreator.getNodeCreateFacet()), "top"));
        palette.addEntry(makeEntry(true, "interface.png",    "Interface (small)",    makeNodeCreateTool(interfaceShortcutCreator.getNodeCreateFacet()), "top"));
        palette.addEntry(makeEntry(false, "provided.png",  "Provides",             new ArcCreateToolGem(retrieveArcRecreator(ImplementationCreatorGem.NAME)).getToolFacet(), "port", "class"));
        palette.addEntry(makeEntry(false, "required.png",   "Requires",             new ArcCreateToolGem(retrieveArcRecreator(DependencyCreatorGem.NAME)).getToolFacet(), "port", "class"));
	      tools.addCategory(palette);
      }

      {
        RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Component", new String[]{COMPONENT_FOCUS});
	      palette.addEntry(makeEntry(true, "component.png",    "Component",            makeNodeCreateTool(compositeCreator.getNodeCreateFacet()), "top"));
	      palette.addEntry(makeEntry(true, "component.png",    "Component (small)",    makeNodeCreateTool(compositeShortcutCreator), "top"));
	      NodeCreateFacet primitiveCreator = makePrimitiveCreator(false);
	      palette.addEntry(makeEntry(true, "class.png",        "Primitive type",       makeNodeCreateTool(primitiveCreator), "top"));
	      palette.addEntry(makeEntry(true, "factory.png",      "Factory",              makeNodeCreateTool(factoryCreator), "top"));
	      palette.addEntry(makeEntry(true, "placeholder.png",  "Placeholder",          makeNodeCreateTool(placeholderCreator), "top"));
	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
	      	palette.addEntry(makeEntry(true, "port.png",         "Port",                 makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));
	      }

	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.HYPERPORT_START_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "hyperport-start.png", "Start hyperport", makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.HYPERPORT_END_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "hyperport-end.png",   "End hyperport",   makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.AUTOCONNECT_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "autoconnect.png",   "Autoconnect port",  makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
	      tools.addCategory(palette);
      }

      {
        RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "State", new String[]{STATE_FOCUS});
        NodeCreateFacet stateCreator = makeStateCreator(false);
        palette.addEntry(makeEntry(true, "state.png",           "State",            makeNodeCreateTool(stateCreator), "top"));
        ClassCreatorGem compositeStateCreator = new ClassCreatorGem();
        compositeStateCreator.setFillColorPrefence(BaseColors.STATE_COLOR);
        compositeStateCreator.setSuppressOperations(true);
        compositeStateCreator.setAutoSized(false);
        compositeStateCreator.setComponentKind(ComponentKindEnum.NORMAL);
        compositeStateCreator.setStereotype(CommonRepositoryFunctions.STATE);
        compositeStateCreator.setResemblance(CommonRepositoryFunctions.COMPOSITE_STATE_CLASS);
        palette.addEntry(makeEntry(true, "composite-state.png", "Composite state",  makeNodeCreateTool(compositeStateCreator.getNodeCreateFacet()), "top"));

        PartCreatorGem statePartCreator = new PartCreatorGem(true);
        statePartCreator.setFillColorPreference(BaseColors.STATE_PART_COLOR);
        palette.addEntry(makeEntry(true, "state-part.png", "State part",  makeNodeCreateTool(statePartCreator.getNodeCreateFacet()), "class"));
        
        ConnectorCreatorGem transitionCreator = new ConnectorCreatorGem();
        transitionCreator.setDirected(true);
        palette.addEntry(makeEntry(false, "transition.png", "Transition", new ArcCreateToolGem(transitionCreator.getArcCreateFacet()).getToolFacet(), "port"));
        tools.addCategory(palette);

	      NodeCreateFacet primitiveCreator = makePrimitiveCreator(false);
	      palette.addEntry(makeEntry(true, "class.png",        "Primitive type",       makeNodeCreateTool(primitiveCreator), "top"));

	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
	      	palette.addEntry(makeEntry(true, "port.png",       "Port",                 makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));
	      }

	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.HYPERPORT_START_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "hyperport-start.png", "Start hyperport", makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.HYPERPORT_END_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "hyperport-end.png",   "End hyperport",   makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
	      {
	      	PortCreatorGem creator = new PortCreatorGem();
	      	creator.setPortKind(PortKind.AUTOCONNECT_LITERAL);
	      	creator.setStereotype(CommonRepositoryFunctions.PORT);
		      palette.addEntry(makeEntry(true, "autoconnect.png",   "Autoconnect port",  makeNodeCreateTool(creator.getNodeCreateFacet()), "class", "port"));	      	
	      }
      }

      {
        RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Profile", new String[]{PROFILE_FOCUS});
        palette.addEntry(makeEntry(true, "profile.png",     "Profile",          makeNodeCreateTool(retrieveNodeRecreator(ProfileCreatorGem.NAME)), "top", null));
        palette.addEntry(makeEntry(true, "stereotype.png",  "Stereotype",       makeNodeCreateTool(retrieveNodeRecreator(StereotypeCreatorGem.NAME)), "top", null));
        tools.addCategory(palette);
      }
      
      {
        RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Part", new String[]{COMPONENT_FOCUS, STATE_FOCUS});
        PartCreatorGem partCreator = new PartCreatorGem(false);
        partCreator.setFillColorPreference(BaseColors.STATE_PART_COLOR);
        partCreator.setFillColorPreference(BaseColors.PART_COLOR);        
	      palette.addEntry(makeEntry(true,  "part.png",         "Part",                 makeNodeCreateTool(partCreator.getNodeCreateFacet()), "class", "part"));
	      palette.addEntry(makeEntry(false, "portlink.png",     "Port link",            new ArcCreateToolGem(portLinkCreator.getArcCreateFacet()).getToolFacet(), "port"));
	      palette.addEntry(makeEntry(false, "connector.png",    "Connector",            new ArcCreateToolGem(retrieveArcRecreator(ConnectorCreatorGem.NAME)).getToolFacet(), "port", null));
	      palette.addEntry(makeEntry(false, "dependency.png",   "Delegation connector", new ArcCreateToolGem(delegateConnectorCreator.getArcCreateFacet()).getToolFacet(), "port"));
	      tools.addCategory(palette);
      }
    }

    if (Evolve.ADVANCED)
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Class", new String[]{CLASS_FOCUS});
      ClassCreatorGem classCreator = new ClassCreatorGem();
      classCreator.setFillColorPrefence(BaseColors.CLASS_COLOR);
      palette.addEntry(makeEntry(true,  "class.png",       "Class",              makeNodeCreateTool(classCreator.getNodeCreateFacet()), "top", null));
      palette.addEntry(makeEntry(false, "inheritance.gif", "Inheritance",        new ArcCreateToolGem(retrieveArcRecreator(InheritanceCreatorGem.NAME)).getToolFacet(), "element", null));
      palette.addEntry(makeEntry(false, "interface.png",   "Interface",          makeNodeCreateTool(retrieveNodeRecreator(InterfaceCreatorGem.NAME)), "top", null));
      palette.addEntry(makeEntry(false, "realisation.gif", "Implementation",     new ArcCreateToolGem(retrieveArcRecreator(ImplementationCreatorGem.NAME)).getToolFacet(), "class", null));
      palette.addEntry(makeEntry(false, "dependency.png",  "Dependency",         new ArcCreateToolGem(retrieveArcRecreator(DependencyCreatorGem.NAME)).getToolFacet(), "element", null));
      palette.addEntry(makeEntry(false, "association.gif", "Association",        new ArcCreateToolGem(new AssociationCreatorGem().getArcCreateFacet()).getToolFacet(), "classifier", null));
      palette.addEntry(makeEntry(false, "aggregation.gif", "Aggregation",        new ArcCreateToolGem(new AssociationCreatorGem(AssociationArcAppearanceGem.AGGREGATION_TYPE).getArcCreateFacet()).getToolFacet(), "classifier", null));
      palette.addEntry(makeEntry(false, "composition.gif", "Composition",        new ArcCreateToolGem(new AssociationCreatorGem(AssociationArcAppearanceGem.COMPOSITION_TYPE).getArcCreateFacet()).getToolFacet(), "classifier", null));
      palette.addEntry(makeEntry(false, "containment.gif", "Containment",        new ArcCreateToolGem(retrieveArcRecreator(ContainmentCreatorGem.NAME)).getToolFacet(), "class,package", null));
      tools.addCategory(palette);
    }
    
    if (Evolve.ADVANCED)
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Sequence", new String[]{BEHAVIOUR_FOCUS});
      palette.addEntry(makeEntry(false, "call.gif",     "Call (synchronous)",  new ArcCreateToolGem(new MessageCreatorGem(MessageArcAppearanceGem.CALL_TYPE).getArcCreateFacet()).getToolFacet()));
      palette.addEntry(makeEntry(false, "return.gif",   "Return",              new ArcCreateToolGem(new MessageCreatorGem(MessageArcAppearanceGem.RETURN_TYPE).getArcCreateFacet()).getToolFacet()));
      palette.addEntry(makeEntry(false, "send.gif",     "Send (asynchronous)", new ArcCreateToolGem(new MessageCreatorGem(MessageArcAppearanceGem.SEND_TYPE).getArcCreateFacet()).getToolFacet()));
      palette.addEntry(makeEntry(false, "lifeline.gif", "Lifeline",            new ArcCreateToolGem(new LifelineCreatorGem().getArcCreateFacet()).getToolFacet()));
      palette.addEntry(makeEntry(true, "baseline.gif", "Baseline",            makeNodeCreateTool((retrieveNodeRecreator(BaselineCreatorGem.NAME)))));
      palette.addEntry(makeEntry(true, "section.gif",  "Section",             makeNodeCreateTool((retrieveNodeRecreator(SequenceSectionCreatorGem.NAME)))));
      palette.addEntry(makeEntry(true, "region.gif",   "Region",              makeNodeCreateTool((retrieveNodeRecreator(RegionCreatorGem.NAME)))));
      tools.addCategory(palette);
    }

    if (Evolve.ADVANCED)
    {
      RichPaletteCategory palette = new RichPaletteCategory(FOLDER, "Deployment", new String[]{MISCELLANEOUS_FOCUS});
      palette.addEntry(makeEntry(true, "model.png",       "Model",                makeNodeCreateTool(retrieveNodeRecreator(ModelCreatorGem.NAME)), "top"));
      palette.addEntry(makeEntry(true, "node.gif",        "Node",        makeNodeCreateTool(retrieveNodeRecreator(NodeCreatorGem.NAME))));
      SelectionToolGem layoutToolGem = new SelectionToolGem(true);
      layoutToolGem.connectGlobalPopupMenuFacet(popupFacet);
      layoutToolGem.connectToolClassificationFacet(toolClassifier);
      palette.addEntry(makeEntry(false, "layout.png", "Layout", layoutToolGem.getToolFacet(), true, "", ""));
      palette.addEntry(makeEntry(false, "magnifier.png", "Zoom", new ZoomToolGem().getToolFacet()));
      tools.addCategory(palette);
    }

    tools.setHideMinimized(true);
    return tools;
  }

	public static NodeCreateFacet makePrimitiveCreator(boolean icon)
	{
		ClassCreatorGem primitiveCreator = new ClassCreatorGem();
		primitiveCreator.setComponentKind(ComponentKindEnum.PRIMITIVE);
		primitiveCreator.setStereotype(CommonRepositoryFunctions.PRIMITIVE_TYPE);
		primitiveCreator.setFillColorPrefence(BaseColors.PRIMITIVE_COLOR);
		primitiveCreator.setDisplayOnlyIcon(icon);
		return primitiveCreator.getNodeCreateFacet();
	}

	public static NodeCreateFacet makeStateCreator(boolean icon)
	{
		ClassCreatorGem stateCreator = new ClassCreatorGem();
    stateCreator.setFillColorPrefence(BaseColors.STATE_COLOR);
		stateCreator.setSuppressOperations(true);
		stateCreator.setAutoSized(false);
		stateCreator.setComponentKind(ComponentKindEnum.NORMAL);
		stateCreator.setStereotype(CommonRepositoryFunctions.STATE);
		stateCreator.setResemblance(CommonRepositoryFunctions.STATE_CLASS);
		stateCreator.setDisplayOnlyIcon(icon);
		return stateCreator.getNodeCreateFacet();
	}

	public static NodeCreateFacet makeFactoryCreator(boolean icon)
	{
		ClassCreatorGem factoryCreator = new ClassCreatorGem();
    factoryCreator.setSuppressOperations(true);
    factoryCreator.setAutoSized(false);
    factoryCreator.setFactory(true);
    factoryCreator.setFillColorPrefence(BaseColors.FACTORY_COLOR);
    factoryCreator.setComponentKind(ComponentKindEnum.NORMAL);
    factoryCreator.setStereotype(CommonRepositoryFunctions.COMPONENT);
    factoryCreator.setResemblance(CommonRepositoryFunctions.FACTORY_BASE);
    factoryCreator.setDisplayOnlyIcon(icon);
		return factoryCreator.getNodeCreateFacet();
	}

	public static NodeCreateFacet makePlaceholderCreator(boolean icon)
	{
		ClassCreatorGem placeholderCreator = new ClassCreatorGem();
    placeholderCreator.setSuppressOperations(true);
    placeholderCreator.setAutoSized(false);
    placeholderCreator.setPlaceholder(true);
    placeholderCreator.setFillColorPrefence(BaseColors.PLACEHOLDER_COLOR);
    placeholderCreator.setComponentKind(ComponentKindEnum.NORMAL);
    placeholderCreator.setStereotype(CommonRepositoryFunctions.COMPONENT);
    placeholderCreator.setDisplayOnlyIcon(icon);
		return placeholderCreator.getNodeCreateFacet();
	}

	public static NodeCreateFacet makeCompositeShortcutCreator()
	{
		ClassCreatorGem compositeShortcutCreator = new ClassCreatorGem();
    compositeShortcutCreator.setSuppressOperations(true);
    compositeShortcutCreator.setSuppressAttributes(true);
    compositeShortcutCreator.setDisplayOnlyIcon(true);
    compositeShortcutCreator.setAutoSized(true);
    compositeShortcutCreator.setComponentKind(ComponentKindEnum.NORMAL);
    compositeShortcutCreator.setStereotype(CommonRepositoryFunctions.COMPONENT);
		return compositeShortcutCreator.getNodeCreateFacet();
	}

	public static NodeCreateFacet makeStrictStratumCreator()
	{
		{
		  PackageCreatorGem creator = new PackageCreatorGem(false);
		  creator.setFillColorPreference(BaseColors.STRATUM_COLOR);
		  creator.setStereotype(CommonRepositoryFunctions.STRATUM);
		  return creator.getNodeCreateFacet();
		}
	}

	public static NodeCreateFacet makeRelaxedStratumCreator()
	{
		{
		  PackageCreatorGem creator = new PackageCreatorGem(false);
		  creator.setFillColorPreference(BaseColors.STRATUM_COLOR);
		  creator.setStereotype(CommonRepositoryFunctions.STRATUM);
		  creator.setRelaxed(true);
		  return creator.getNodeCreateFacet();
		}
	}

  private IRichPaletteEntry makeEntry(boolean node, String icon, String name, final ToolFacet tool)
  {
    return makeEntry(node, icon, name, tool, false, "", null);
  }

  private IRichPaletteEntry makeEntry(boolean node, String icon, String name, final ToolFacet tool, String elements)
  {
    return makeEntry(node, icon, name, tool, false, elements, null);
  }

  private IRichPaletteEntry makeEntry(boolean node, String icon, String name, final ToolFacet tool, String elements, String hints)
  {
    return makeEntry(node, icon, name, tool, false, elements, hints);
  }

  private static final int ICON_HEIGHT = 16;
  private IRichPaletteEntry makeEntry(
  		boolean node,
  		String icon,
  		String name,
  		final ToolFacet tool,
  		final boolean selectType,
  		String elements,
  		String hints)
  {
    ImageIcon loadIcon = IconLoader.loadIcon(icon);
    ImageIcon scaledIcon = loadIcon;
    int height = loadIcon.getIconHeight();
    
    if (height > 16)
    {
      double scaler = ICON_HEIGHT / (double) height;
      int width = (int) Math.round(loadIcon.getIconWidth() * scaler);
      Image scaled = loadIcon.getImage().getScaledInstance(width, ICON_HEIGHT, Image.SCALE_SMOOTH);
      scaledIcon = new ImageIcon(scaled);
    }
    
    // scale the icon to fit 16 height
    final RichPaletteEntry entry = new RichPaletteEntry(tools, scaledIcon, name, elements, hints, node, tool);
    IRichPaletteEntryListener listener =
      new IRichPaletteEntryListener()
      {
        public void deselected()
        {
          // not currently used...
        }
  
        public void selected()
        {          
          attachedTo.setCurrentTool(tool, coordinator);
          if (selectType)
            lastSelect = entry;
          current = tool;
          setCurrentCursor(entry);
        }
      };
      
    // add the listener
    entry.setListener(listener);
      
    return entry;
  }

  private ToolFacet makeNodeCreateTool(NodeCreateFacet nodeCreateFacet)
  {
    NodeCreateToolGem gem = new NodeCreateToolGem(nodeCreateFacet);
    return gem.getToolFacet();
  }

  private NodeCreateFacet retrieveNodeRecreator(String name)
  {
    return (NodeCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(name);
  }

  private ArcCreateFacet retrieveArcRecreator(String name)
  {
    return (ArcCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(name);
  }

  public PaletteManagerFacet getPaletteManagerFacet()
  {
    return paletteManager;
  }
  
  public void connectToolCoordinatorFacet(ToolCoordinatorFacet coordinator)
  {
    this.coordinator = coordinator;
  }
  
  public void connectSmartMenuBarFacet(SmartMenuBarFacet smartMenuBar)
  {
    this.smartMenuBar = smartMenuBar;
  }
  
  private static final Cursor DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
  private static final Cursor TOOL = new Cursor(Cursor.CROSSHAIR_CURSOR);
  private void setCurrentCursor(IRichPaletteEntry entry)
  {
    // change the icon if this is not select
    if (entry == select)            
      attachedTo.getCanvas().setCursor(DEFAULT);
    else
      attachedTo.getCanvas().setCursor(TOOL);
  }
}
