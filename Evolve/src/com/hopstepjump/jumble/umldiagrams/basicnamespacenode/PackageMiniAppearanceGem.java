package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jsyntaxpane.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.backbone.generator.*;
import com.hopstepjump.backbone.printers.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public class PackageMiniAppearanceGem implements Gem
{
	private FigureFacet figureFacet;
  private BasicNamespaceMiniAppearanceFacet miniAppearanceFacet = new BasicNamespaceMiniAppearanceFacetImpl();
  
  public PackageMiniAppearanceGem()
  {
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public BasicNamespaceMiniAppearanceFacet getBasicNamespaceMiniAppearanceFacet()
  {
    return miniAppearanceFacet;
  }
  
  private DEStratum getDEPackage()
  {
    return getDEPackage(figureFacet.getSubject());
  }  

  private boolean isStratum()
  {
  	Element elem = (Element) figureFacet.getSubject();
    return StereotypeUtilities.isStereotypeApplied(elem, CommonRepositoryFunctions.STRATUM);
  }
  
  private boolean isHTMLTop()
  {
    Element subject = (Element) figureFacet.getSubject();
    if (subject == null)
      return false;
    return
      StereotypeUtilities.isStereotypeApplied(subject, CommonRepositoryFunctions.DOCUMENTATION_TOP);
  }
  
  private boolean isHTMLIncluded()
  {
    Element subject = (Element) figureFacet.getSubject();
    if (subject == null)
      return false;
    return
      StereotypeUtilities.isStereotypeApplied(subject, CommonRepositoryFunctions.DOCUMENTATION_INCLUDED);
  }
  
  private class BasicNamespaceMiniAppearanceFacetImpl implements BasicNamespaceMiniAppearanceFacet
  { 
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean, UBounds)
     */
    public ZNode formView(boolean displayAsIcon, UBounds bounds)
    {
      boolean relaxed = isStratum() ?
          StereotypeUtilities.extractBooleanProperty((Package) figureFacet.getSubject(), CommonRepositoryFunctions.RELAXED) : false;
      
      // only form a view if this is a stratum
      if (isStratum())
      {
        // make a small layer picture
        ZRectangle rect1 = new ZRectangle(bounds);
        rect1.setPenPaint(Color.BLACK);
        rect1.setFillPaint(relaxed ? Color.LIGHT_GRAY : Color.BLACK);

        ZPolygon poly = new ZPolygon();
        UPoint middle = bounds.getMiddlePoint();
        UPoint topLeft = bounds.getTopLeftPoint();
        UPoint topRight = bounds.getTopRightPoint();
        UDimension yOffset = new UDimension(0, bounds.getHeight()/6);
        poly.add(topLeft);
        poly.add(topRight);
        poly.add(topRight.add(yOffset.multiply(2)));
        poly.add(middle.subtract(yOffset));
        poly.add(middle.add(yOffset));
        poly.add(topLeft.add(yOffset.multiply(4)));
        poly.setFillPaint(getDEPackage().isDestructive() ? Color.RED : Color.WHITE);
        poly.setPenPaint(Color.BLACK);
        
        ZGroup group = new ZGroup();
        group.addChild(new ZVisualLeaf(rect1));
        group.addChild(new ZVisualLeaf(poly));

        return group;
      }
      else
      if (isHTMLTop())
      {
        // make a small H in a circle
        ZGroup ell = new FancyRectangleMaker(bounds, 4, Color.ORANGE, true, 3, 1).make();
        UDimension offset = bounds.getDimension().multiply(0.3);
        UBounds inside = bounds.addToPoint(offset).addToExtent(offset.multiply(2).negative());
        ZPolyline line = new ZPolyline(inside.getTopLeftPoint());
        line.add(inside.getBottomLeftPoint());
        line.add(inside.getX(), inside.getCenterY());
        line.add(inside.getX() + inside.getWidth(), inside.getCenterY());
        line.add(inside.getTopRightPoint());
        line.add(inside.getBottomRightPoint());
        line.setPenPaint(Color.WHITE);
        line.setPenWidth(3);
        
        ZGroup group = new ZGroup();
        group.addChild(ell);
        group.addChild(new ZVisualLeaf(line));
        return group;
      }
      else
      if (isHTMLIncluded())
      {
        // make a small H in a circle
        ZGroup ell = new FancyRectangleMaker(bounds, 4, Color.ORANGE, true, 3, 1).make();
        UDimension offset = bounds.getDimension().multiply(0.3);
        UBounds inside = bounds.addToPoint(offset).addToExtent(offset.multiply(2).negative());
        ZPolyline line = new ZPolyline(inside.getX(), inside.getCenterY());
        line.add(inside.getX() + inside.getWidth(), inside.getCenterY());
        line.setPenPaint(Color.WHITE);
        line.setPenWidth(3);
        ZPolyline line2 = new ZPolyline(inside.getCenterX(), inside.getY());
        line2.add(inside.getCenterX(), inside.getY() + inside.getHeight());
        line2.setPenPaint(Color.WHITE);
        line2.setPenWidth(3);
        
        ZGroup group = new ZGroup();
        group.addChild(ell);
        group.addChild(new ZVisualLeaf(line));
        group.addChild(new ZVisualLeaf(line2));
        return group;
      }
      else
        return new ZGroup();
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
     */
    public boolean haveMiniAppearance()
    {
      if (figureFacet.getSubject() == null)
        return true;  // makes the preview work even when there is no subject
      
      return isStratum() || isHTMLTop() || isHTMLIncluded();
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
     */
    public Shape formShapeForPreview(UBounds bounds)
    {
      return new ZRectangle(bounds).getShape();
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
     */
    public UDimension getMinimumDisplayOnlyAsIconExtent()
    {
      return new UDimension(48, 48);
    }

    public void addToContextMenu(JPopupMenu menu, final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      Utilities.addSeparator(menu);
    	
      // if this is the topmost read-only element or is writeable, add a menu item to change it
      final Package pkg = (Package) figureFacet.getSubject();
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      
      // use the repository for the parent, as it may be null, or not a package
      boolean parentReadOnly = GlobalSubjectRepository.repository.isReadOnly(pkg.getOwner());
      JCheckBoxMenuItem read = new JCheckBoxMenuItem("Read only");
      // important to use the parent here, as we want to be able to toggle readonly
      // on "readonly" packages if the parent is writeable
      read.setEnabled(!parentReadOnly);
      read.setSelected(pkg.isReadOnly());

      Utilities.addSeparator(menu);
      menu.add(read);
      
      read.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // toggle the readonly flag
          coordinator.startTransaction("toggled readOnly", "set readOnly back");
          pkg.setReadOnly(!pkg.isReadOnly());
          coordinator.commitTransaction();
        }
      });
      
      DEStratum me = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asStratum();
      menu.add(PackageMiniAppearanceGem.makeShowBackboneCodeItem("Show Backbone code", coordinator, me, me, BackbonePrinterMode.REAL_NAMES));

      boolean readOnly = figureFacet.isSubjectReadOnlyInDiagramContext(false);
      if (isStratum())
      {
        Utilities.addSeparator(menu);

        final boolean isRelaxed = getDEPackage().isRelaxed();
        JCheckBoxMenuItem relaxed = new JCheckBoxMenuItem(
          new AbstractAction("Relaxed")
          {
            public void actionPerformed(ActionEvent e)
            {
              StereotypeUtilities.formSetBooleanRawStereotypeAttributeTransaction(
              		coordinator,
                  (Element) figureFacet.getSubject(),
                  CommonRepositoryFunctions.RELAXED,
                  !isRelaxed);
            }
          });
        relaxed.setSelected(isRelaxed);
        relaxed.setEnabled(!readOnly);
        menu.add(relaxed);

        final boolean isDestructive = getDEPackage().isDestructive();
        JCheckBoxMenuItem destructive = new JCheckBoxMenuItem(
          new AbstractAction("Destructive")
          {
            public void actionPerformed(ActionEvent e)
            {
              StereotypeUtilities.formSetBooleanRawStereotypeAttributeTransaction(
              		coordinator,
                  (Element) figureFacet.getSubject(),
                  CommonRepositoryFunctions.DESTRUCTIVE,
                  !isDestructive);
            }
          });
        destructive.setSelected(isDestructive);
        destructive.setEnabled(!readOnly);
        menu.add(destructive);
      }
      
      Utilities.addSeparator(menu);
      menu.add(new AbstractAction("Determine exposesStrata...")
      {
        public void actionPerformed(ActionEvent e)
        {
          Collection<? extends DEStratum> exposed = getDEPackage().getExposesStrata();
          
          JScrollPane scroll = makeTextPane(
              "<html>exposesStrata for " + getDEPackage().getFullyQualifiedName() + "<hr>", exposed);
          
          coordinator.invokeAsDialog(
          		null,
              "Stratum exposure",
              exposed.isEmpty() ? new JLabel("No strata are visible.") : scroll,
              null,
              0,
              null);
        }
      });

      // if this is a stratum, provide an entry to work out the exposure
      menu.add(new AbstractAction("Determine canSee...")
      {
        public void actionPerformed(ActionEvent e)
        {
          Collection<? extends DEStratum> visible = getDEPackage().getCanSee();
          
          String name = getDEPackage().getFullyQualifiedName();
          JScrollPane scroll = makeTextPane(
              "<html>canSee for " + name + "<hr>", visible);
          
          coordinator.invokeAsDialog(
          		null,
              "Stratum visibility",
              visible.isEmpty() ? new JLabel("No strata are visible.") : scroll,
              null,
              0,
              null);
        } 
      });
      
      menu.add(new AbstractAction("Determine simpleDependsOn...")
      {
        public void actionPerformed(ActionEvent e)
        {
          Collection<? extends DEStratum> simpleDependsOn = getDEPackage().getSimpleDependsOn();
          
          JScrollPane scroll = makeTextPane(
              "<html>simpleDependsOn for " + getDEPackage().getFullyQualifiedName() + "<hr>", simpleDependsOn);
          
          coordinator.invokeAsDialog(
          		null,
              "Stratum dependency",
              simpleDependsOn.isEmpty() ? new JLabel("No strata are depended upon.") : scroll,
              null,
              0,
              null);
        }
      });

      menu.add(new AbstractAction("Determine transitive closure...")
      {
        public void actionPerformed(ActionEvent e)
        {
          Collection<DEStratum> simpleDependsOn = getDEPackage().getTransitivePlusMe();

          JScrollPane scroll = makeTextPane(
              "<html>transitive closure for " + getDEPackage().getFullyQualifiedName() + "<hr>", simpleDependsOn);
          
          coordinator.invokeAsDialog(
          		null,
              "Stratum transitive closure",
              simpleDependsOn.isEmpty() ? new JLabel("No other strata can be reached.") : scroll,
              null,
              0,
              null);
        }
      });

      menu.add(new AbstractAction("Analyse independence...")
      {
        public void actionPerformed(ActionEvent e)
        {
          List<DEStratum> strata = new ArrayList<DEStratum>();
          for (FigureFacet figure : diagramView.getSelection().getSelectedFigures())
          {
            Object subject = figure.getSubject();
            DEStratum de = getDEPackage(subject);
            if (de != null)
              strata.add(de);
          }
          
          // if we don't have 2 strata, complain
          if (strata.size() != 2)
          {
            coordinator.invokeErrorDialog(
                "Independence analysis",
                "Exactly two strata must be selected.");
            return;
          }
          
          DEStratum s1 = strata.get(0);
          DEStratum s2 = strata.get(1);
          boolean s1Independent = !s1.getTransitivePlusMe().contains(s2);
          boolean s2Independent = !s2.getTransitivePlusMe().contains(s1);
          boolean mutual = s1Independent && s2Independent;
          boolean circular = !s1Independent && !s2Independent;
          Set<DEStratum> overlap = new HashSet<DEStratum>();
          if (mutual)
          {
            overlap.addAll(s1.getTransitive());
            overlap.retainAll(s2.getTransitive());
          }
          else
          if (s1Independent)
            overlap = s1.getTransitivePlusMe();
          else
          if (s2Independent)
            overlap = s2.getTransitivePlusMe();
          
          String report = "<html>";

          // summary
          report += "The strata are ";
          if (mutual)
            report += "mutually independent";
          else
          if (circular)
            report += "circularly dependent";
          else
            report += "dependent";
          
          if (!circular)
          {
            if (overlap.isEmpty())
              report += " with no overlap";
            else
            if (mutual)
              report += " but have overlap";
          }
          report += ".";

          report += "<ul><li>" + s1.getFullyQualifiedName() + (s1Independent ? " does not depend on " : " depends on ") + s2.getFullyQualifiedName() + "</li>";
          report += "<li>" + s2.getFullyQualifiedName() + (s2Independent ? " does not depend on " : " depends on ") + s1.getFullyQualifiedName() + "</li>";
          
          if (!overlap.isEmpty())
            report += "</ul><br>Overlap consists of the following strata<hr>";
          
          JScrollPane scroll = makeTextPane(report, overlap);
          
          coordinator.invokeAsDialog(
          		null,
              "Independence analysis",
              scroll,
              null,
              0,
              null);
        }
      });
    }

    private JScrollPane makeTextPane(String title, Collection<? extends DEStratum> strata)
    {
      JTextPane text = new JTextPane();
      StringBuilder b = new StringBuilder(title);
      
      // sort alphabetically
      List<DEStratum> sorted = new ArrayList<DEStratum>(strata);
      Collections.sort(sorted,
          new Comparator<DEStratum>()
          {
            public int compare(DEStratum p1, DEStratum p2)
            {
              return p1.getFullyQualifiedName().compareTo(p2.getFullyQualifiedName());
            }
          });
      
      b.append("<table><tr>");
      for (DEStratum st : sorted)
      {
        String name = st.getFullyQualifiedName();
        if (st.getName().length() == 0)
          name += "&lt;anonymous&gt;";
        b.append("&nbsp;&nbsp;<td>" + name + "</td><td>" + "(stratum) " + "</td><tr>");
      }
      text.setEditable(false);
      text.setContentType("text/html");
      text.setText(b.toString());
      text.setOpaque(false);
      text.setBorder(null);
      JScrollPane scroll = new JScrollPane(text);
      scroll.setPreferredSize(new Dimension(400, 200));
      scroll.setBorder(null);
      return scroll;
    }

    public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget)
    {
      return null;
    }

    public JList formSelectionList(String textSoFar)
    {
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(textSoFar, PackageImpl.class, false);
      Vector<ElementSelection> listElements = new Vector<ElementSelection>();
      for (NamedElement element : elements)
        if (element != figureFacet.getSubject())
          listElements.add(new ElementSelection(element));
      Collections.sort(listElements);
      
      return new JList(listElements);
    }

    public Object setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress)
    {
      return ComponentMiniAppearanceGem.setElementText(
          figureFacet,
          textable,
          text,
          listSelection,
          unsuppress);
    }
  }
  
  public static JMenuItem makeShowBackboneCodeItem(String menuText, final ToolCoordinatorFacet coordinator, final DEStratum perspective, final DEObject object, final BackbonePrinterMode mode)
  {
    JMenuItem code = new JMenuItem(menuText);
    code.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        int x = coordinator.getFrameXPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_X_POS);
        int y = coordinator.getFrameYPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_Y_POS);
        int width = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_WIDTH);
        int height = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_HEIGHT);

        coordinator.getDock().createExternalPaletteDockable(
            "Source",
            null,
            new Point(x, y), new Dimension(width, height),
            true,
            true,
            makeBackbone(perspective, object, new JPanel(), mode));
        }
    });
    return code;
  }
  
	private static JPanel makeBackbone(final DEStratum perspective, final DEObject object, final JPanel pane, final BackbonePrinterMode mode)
	{
		pane.setLayout(new BorderLayout());
		final JEditorPane editor = new JEditorPane();
		editor.setEditorKit(new SyntaxKit("tal"));
		JScrollPane scroller = new JScrollPane(editor);
    pane.removeAll();
    pane.add(scroller, BorderLayout.CENTER);

    // provide a refresh option
    final JPopupMenu menu = new JPopupMenu();
    JMenuItem item = new JMenuItem("Refresh");
    menu.add(item);
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      	makeBackbone(perspective, object, pane, mode);
      }
    });
    editor.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
          Point pt = e.getPoint();
          if (e.getButton() == MouseEvent.BUTTON3)
              menu.show(editor, pt.x, pt.y);
      }
    });
    
    String backbone = object.asStratum() != null ?
    		new StratumPrinter(perspective, object.asStratum(), mode).makePrintString("") :
    			new ElementPrinter(perspective, object.asElement(), mode).makePrintString("");
    editor.setText(backbone);
    editor.moveCaretPosition(0);
    
		pane.revalidate();
		return pane;
	}
	
  private static DEStratum getDEPackage(Object subject)
  {
    DEObject de = GlobalDeltaEngine.engine.locateObject(subject);
    if (de == null)
      return null;
    return de.asStratum();
  }
}
