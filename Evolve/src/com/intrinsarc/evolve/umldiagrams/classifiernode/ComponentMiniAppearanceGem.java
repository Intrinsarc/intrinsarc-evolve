package com.intrinsarc.evolve.umldiagrams.classifiernode;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jsyntaxpane.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.backbone.generator.*;
import com.intrinsarc.backbone.generator.hardcoded.common.*;
import com.intrinsarc.backbone.printers.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.enhanced.*;
import com.intrinsarc.swing.lookandfeel.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class ComponentMiniAppearanceGem implements Gem
{
	public static final ImageIcon EVOLVE_ICON = IconLoader.loadIcon("evolution.png");
	private static final ImageIcon FLAT_ICON = IconLoader.loadIcon("flat.png");
	private ClassifierMiniAppearanceFacet miniAppearanceFacet = new ClassifierMiniAppearanceFacetImpl();
	private FigureFacet figureFacet;

	public ComponentMiniAppearanceGem()
	{
	}

	public void connectFigureFacet(FigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
	}

	public ClassifierMiniAppearanceFacet getClassifierMiniAppearanceFacet()
	{
		return miniAppearanceFacet;
	}

	private class ClassifierMiniAppearanceFacetImpl implements ClassifierMiniAppearanceFacet
	{
		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean,
		 *      UBounds)
		 */
		public ZNode formView(boolean displayAsIcon, UBounds bounds)
		{
			// don't bother if the part has no type
			if (figureFacet.getSubject() == null || figureFacet.hasSubjectBeenDeleted())
				return new ZGroup();

			// black, grey or white box
			ElementProperties properties = new ElementProperties(figureFacet);

			// we must have a component stereotype to proceed
			if (properties.getComponentKind() == ComponentKindEnum.NONE || properties.getComponentKind() == ComponentKindEnum.PRIMITIVE) 
				return new ZGroup();

			if (properties.isFactory())
				return drawFactory(bounds, properties);
			else
			if (properties.isState())
				return drawState(bounds, properties);
			else
				return drawCompositeOrLeaf(bounds, properties);
		}

		private ZNode drawFactory(UBounds bounds, ElementProperties properties)
		{
			// draw this as either a filled or unfilled wheel with 8 spokes
			bounds = bounds.addToPoint(new UDimension(3, 2)).addToExtent(new UDimension(-4, -4));
			ZGroup group = new ZGroup();
			ZRectangle ell = new ZRectangle(bounds.getRectangle2D());
    	ell.setStroke(new BasicStroke((int) bounds.getWidth() / 4, BasicStroke.CAP_ROUND, 1));
			ell.setPenPaint(Color.BLACK);
			ell.setFillPaint(Color.WHITE);
			group.addChild(new ZVisualLeaf(ell));

			return group;
		}

		private ZNode drawState(UBounds bounds, ElementProperties properties)
		{
			ZGroup group = new ZGroup();
			if (properties.isStartState())
			{
				int offset = 2;
				double w = bounds.width;
				ZRoundedRectangle ell = new ZRoundedRectangle(bounds.x + offset, bounds.y + offset - 1, w - offset * 2, w - offset * 2 + 1, 10, 10);
				ell.setFillPaint(Color.BLACK);
				group.addChild(new ZVisualLeaf(ell));
			}
			else
			if (properties.isEndState())
			{
				int offset = 0;
				double w = bounds.width;
				ZRoundedRectangle ell = new ZRoundedRectangle(bounds.x + offset, bounds.y + offset - 1, w - offset * 2, w - offset * 2 + 1, 10, 10);
				ell.setPenPaint(Color.BLACK);
				ell.setPenWidth(1);
				offset = 3;
				ZRoundedRectangle ell2 = new ZRoundedRectangle(bounds.x + offset, bounds.y + offset - 1, w - offset * 2, w - offset * 2 + 1, 8, 8);
				ell2.setFillPaint(Color.BLACK);
				group.addChild(new ZVisualLeaf(ell));
				group.addChild(new ZVisualLeaf(ell2));
			}
			else
			if (properties.isComposite())
			{
				Color col = new Color(175, 139, 120);
				double w = bounds.width;
				int ellWidth = (int) (w / 2);
				int ellHeight = (int) (w / 2);
				ZShape ell1 = new ZRoundedRectangle((int) bounds.x, (int) bounds.y + ellWidth, ellWidth, ellHeight, 4, 4);
				ZShape ell2 = new ZRoundedRectangle((int) (bounds.x + bounds.width - ellWidth + 2), (int) (bounds.y + ellWidth), ellWidth, ellHeight, 4, 4);
				ZLine line = new ZLine(new UBounds(ell1.getBounds()).getMiddlePoint(), new UBounds(ell2.getBounds()).getMiddlePoint());
				ell1.setPenPaint(col);
				ell2.setPenPaint(col);
				line.setPenPaint(col);
				group.addChild(new ZVisualLeaf(line));
				group.addChild(new ZVisualLeaf(ell1));
				group.addChild(new ZVisualLeaf(ell2));
			}
			return group;
		}

		private ZNode drawCompositeOrLeaf(UBounds bounds, ElementProperties properties)
		{
			// set up the sizes
			double fullWidth = bounds.getWidth();
			double width = (int) (Math.min(fullWidth / 2, 32));
			double height = (int) (Math.min(bounds.getHeight() / 4, 15));

			// set up the colors
			Color fillColor = Color.WHITE;
			Color black = Color.BLACK;
			Color lineColor = black;
			if (properties.isBlackBox())
				fillColor = black;

			// make the 2 legs
			UDimension legExtent = new UDimension(width, height);
			UPoint middle = new UPoint(bounds.getPoint().getX(), bounds.getMiddlePoint().getY());
			UBounds leg1Bounds = new UBounds(middle.subtract(new UDimension(0, (int) (height * 1.5))), legExtent);
			ZRectangle leg1 = new ZRectangle(leg1Bounds);
			Color legFill = properties.isComposite() ? Color.WHITE : black;
			Color legPen = !properties.isComposite() && properties.isBlackBox() ? Color.LIGHT_GRAY
					: black;
			if (!properties.isComposite() && !properties.isBlackBox())
				legPen = null;
			leg1.setFillPaint(legFill);
			leg1.setPenPaint(legPen);
			UBounds leg2Bounds = new UBounds(middle.add(new UDimension(0,
					(int) (height * 0.5))), legExtent);
			ZRectangle leg2 = new ZRectangle(leg2Bounds);
			leg2.setFillPaint(legFill);
			leg2.setPenPaint(legPen);

			// make a box
			UBounds bodyBounds = bounds;
			// offset for the legs
			UDimension offset = new UDimension(width / 2, 0);
			bodyBounds = bodyBounds.addToPoint(offset).addToExtent(offset.negative());
			ZRectangle body = new ZRectangle(bodyBounds);
			body.setFillPaint(fillColor);
			body.setPenPaint(lineColor);

			// group them
			ZGroup group = new ZGroup();
			if (properties.isPlaceholder())
			{
				Area area = new Area(body.getShape());
				area.add(new Area(leg1.getShape()));
				area.add(new Area(leg2.getShape()));
				ZPath zpath = new ZPath(new GeneralPath(area));
				zpath.setPenPaint(Color.BLACK);
				zpath.setFillPaint(Color.WHITE);
				group.addChild(new ZVisualLeaf(zpath));
			}
			else
			{
				group.addChild(new ZVisualLeaf(body));
				group.addChild(new ZVisualLeaf(leg1));
				group.addChild(new ZVisualLeaf(leg2));
			}

			// add an arrow
			if (properties.isNavigable())
			{
				UDimension arrowHalf = new UDimension(width / 2, 0);
				UPoint end = middle.add(arrowHalf.multiply(5));
				ZPolygon arrow = constructArrow(end, width, -1);
				arrow.setPenPaint(Color.BLACK);
				arrow.setFillPaint(Color.WHITE);
				group.addChild(new ZVisualLeaf(arrow));
			}

			return group;
		}

		private ZPolygon constructArrow(UPoint start, double width, int direction)
		{
			ZPolygon arrow = new ZPolygon(start);
			double arrowOffset = width / 1.5;
			arrow.add(start.add(new UDimension(direction * arrowOffset * 1.2,
					arrowOffset)));
			arrow.add(start.add(new UDimension(direction * arrowOffset * 1.2,
					-arrowOffset)));
			arrow.add(start);
			return arrow;
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
		 */
		public boolean haveMiniAppearance()
		{
			if (figureFacet.getSubject() == null)
				return true;
			ComponentKindEnum kind = new ElementProperties(figureFacet).getComponentKind(); 
			return kind != ComponentKindEnum.NONE && kind != ComponentKindEnum.PRIMITIVE;
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
		 */
		public UDimension getMinimumDisplayOnlyAsIconExtent()
		{
			return new UDimension(24, 24);
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			double x = bounds.getX();
			double y = bounds.getY();
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			return new ZRectangle(x - 0.5, y - 0.5, width + 0.9, height + 0.9).getShape();
		}

		public void addToContextMenu(JPopupMenu menu,
				final DiagramViewFacet diagramView,
				final ToolCoordinatorFacet coordinator)
		{
			// add an evolution command to the menu
			JMenuItem evolve = makeEvolveCommand(coordinator, figureFacet, false, false); 
			menu.add(evolve);
			Utilities.addSeparator(menu);
			
			// allow viewing of the flattened structure
			JMenuItem code = new JMenuItem("Show flattened structure");
			code.setIcon(FLAT_ICON);
			menu.add(code);
			code.addActionListener(new FlattenedTreeMaker(coordinator, figureFacet).makeTree());
			
			// get the perspective
			Package pkg = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet());
			final DEStratum perspective = GlobalDeltaEngine.engine.locateObject(pkg).asStratum();					
			final DEComponent comp = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asComponent();

			// display the port details
			if (comp.isLeaf(perspective))
			{
				JMenuItem ports = new JMenuItem("Display bean port information");
				ports.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						List<DEPort> mains = comp.getBeanMainPorts(perspective);
						List<DEPort> nonames = comp.getBeanNoNamePorts(perspective);
						
						String p = "<html><b>Bean main-port</b>";
						for (DEPort port : mains)
							p += "<br>&nbsp;&nbsp;&nbsp;&nbsp;" + port.getName();
						p += "<br><br><hr><br><b>Bean no-name port</b>";
						for (DEPort port : nonames)
							p += "<br>&nbsp;&nbsp;&nbsp;&nbsp;" + port.getName();
						
						JTextPane area = new JTextPane();
						area.setContentType("text/html");
						area.setText(p);
						area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						JScrollPane pane = new JScrollPane(area);
						pane.setPreferredSize(new Dimension(400, 200));
						coordinator.invokeAsDialog(
								null,
								"Bean port information",
								pane,
								null,
								0,
								null);
					}
				});
				menu.add(ports);
			}

			JMenuItem javaCode = makeShowJavaCodeItem("Show Java code", coordinator, perspective, comp);			
			menu.add(javaCode);
      javaCode.setEnabled(comp.isLeaf(perspective));
      
      JMenuItem bbCode = PackageMiniAppearanceGem.makeShowBackboneCodeItem("Show Backbone code", coordinator, perspective, comp, BackbonePrinterMode.REAL_NAMES);
      menu.add(bbCode);

			Utilities.addSeparator(menu);
		}

		
	  public JMenuItem makeShowJavaCodeItem(String menuText, final ToolCoordinatorFacet coordinator, final DEStratum perspective, final DEComponent component)
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
	            "Java Source",
	            null,
	            new Point(x, y), new Dimension(width, height),
	            true,
	            true,
	            makeJavaEditor(new JPanel(), perspective, component));
	        }
	    });
	    return code;
	  }
	  
		private JPanel makeJavaEditor(final JPanel pane, final DEStratum perspective, final DEComponent component)
		{
			pane.setLayout(new BorderLayout());
			final JEditorPane editor = new JEditorPane();
			editor.setEditorKit(new SyntaxKit("java"));
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
	      	makeJavaEditor(pane, perspective, component);
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
	    
	    File realFile = null;
	    try
	    {
	  		PreferencesFacet prefs = GlobalPreferences.preferences;
	      boolean base = isInBackboneBase(perspective);
	      boolean suppress = extractSuppress(prefs, perspective, false);
	    	
	      if (base)
	      {
		    	editor.setText("/*\nSource not available -- class is in the Backbone base library\n*/");	      	
	      }
	      else
	      if (suppress)
	      {
		    	editor.setText("/*\nSource not available -- Java generation is suppressed for this class\n*/");	      	
	      }
	      else
	      {
		    	String name = WriterHelper.extractFolder(prefs, perspective, perspective, 1, null, true);
		    	File generationDir = new File(expandVariables(prefs, null, name));
		    	String implName = component.getImplementationClass(perspective);
		  		String directory = implName.replace('.', '/');
		  		realFile = new File(generationDir, directory + ".java");
		  		
		  		if (!realFile.exists())
			    	editor.setText("/*\nSource not available -- could be in jar file or not generated yet\n" +
			    			"Looked in " + realFile + "\n*/");
		  		else
		  			editor.setText(FileUtilities.loadFileContents(realFile));
	      }
	    }
	    catch (BackboneGenerationException ex)
	    {
	    	editor.setText("/*\nPreferences problem: " + ex.getMessage() + "\n*/");
	    }
	    catch (VariableNotFoundException ex)
			{
	    	editor.setText("/*\nCannot find variable: " + ex.getMessage() + "\n*/");
			}
	    catch (IOException ex)
	    {
	    	editor.setText("/*\nIO problem loading file: " + realFile + "\n*/");	
	    }
	    
	    editor.moveCaretPosition(0);
	    
			pane.revalidate();
			return pane;
		}
		
		public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon,
				boolean anchorIsTarget)
		{
			return null;
		}

		public JList formSelectionList(String textSoFar)
		{
			Collection<NamedElement> elements = GlobalSubjectRepository.repository
					.findElementsStartingWithName(textSoFar, ClassImpl.class, false);
			Vector<ElementSelection> listElements = new Vector<ElementSelection>();
			for (NamedElement element : elements)
				if (element != figureFacet.getSubject())
					listElements.add(new ElementSelection(element));
			Collections.sort(listElements);

			return new JList(listElements);
		}

		public Object setText(TextableFacet textable, String text,
				Object listSelection, boolean unsuppress)
		{
			return setElementText(figureFacet, textable, text, listSelection, unsuppress, true);
		}

		public ToolFigureClassification getToolClassification(
				ClassifierSizes sizes,
				boolean displayOnlyIcon,
				boolean suppressAttributes,
				boolean suppressOperations,
				boolean suppressContents,
				boolean havePorts,
				UPoint point)
		{
			return getTypedToolClassification(
					"class,classifier,element",
					sizes,
					displayOnlyIcon,
					suppressAttributes,
					suppressOperations,
					suppressContents,
					havePorts,
					point);
		}
	}

	public static Object setElementText(
			FigureFacet figure,
			TextableFacet textable,
			String text,
			Object listSelection,
			boolean unsuppress,
			boolean changeColor)
	{
		NamedElement subject = (NamedElement) figure.getSubject();
		String oldText = subject.getName();

		// 3 possibilities here
		// 1) we have changed from an empty name to a new name
		// 2) we have changed from a non-empty name to something that exists
		// -- point to the new subject, and don't delete the old one
		// 3) we have changed from an empty name to something that does exist
		// -- point to the new one, and delete the old one

		if (listSelection == null) // case (1)
		{
			// just change the name
			subject.setName(text);
			return subject;
		}

		ElementSelection sel = (ElementSelection) listSelection;
		Element selected = sel.getElement();
		GlobalSubjectRepository.repository.markLongRunningTransaction();
		if (oldText.length() == 0)
		{
			if (changeColor)
				changeColor(figure, subject, selected);
			GlobalSubjectRepository.repository.incrementPersistentDelete(subject);
			return selected;
		}
		else
		{
			// just link to the new object
			if (changeColor)
				changeColor(figure, subject, selected);
			return selected;
		}
	}

	private static void changeColor(FigureFacet figure, NamedElement subject, Element selected)
	{
		// find the old element
		Object obj[] = ClassifierConstituentHelper.findOwningDiagram(
				(Package) figure.getDiagram().getLinkedObject(),
				selected,
				false);
		FigureFacet original = ClassifierConstituentHelper.findFigureInDiagram((DiagramFacet) obj[0], obj[1]);
		
		if (original != null)
		{
			PersistentFigure ofig = original.makePersistentFigure();
			PersistentProperties op = ofig.getProperties();
			if (op.contains("fill"))
			{
				// set to the color of the old element
				PersistentFigure pfig = figure.makePersistentFigure();
				Color col = op.retrieve("fill").asColor();
				pfig.getProperties().add(new PersistentProperty("fill", col, col));
				figure.acceptPersistentFigure(pfig);
			}
		}
	}
	
	public static JMenuItem makeEvolveCommand(final ToolCoordinatorFacet coordinator, final FigureFacet figureFacet, final boolean iface, final boolean stereotype)
	{
		// if this is not at home, allow evolution
    JMenuItem evolve = new JMenuItem("Evolve", EVOLVE_ICON);
    final Package owner =
    	GlobalSubjectRepository.repository.findVisuallyOwningStratum(
    			figureFacet.getDiagram(),
    			figureFacet.getContainerFacet());
    final Classifier cls = (Classifier) figureFacet.getSubject();
    final DEStratum perspective =
    	GlobalDeltaEngine.engine.locateObject(owner).asStratum();
    DEElement me = GlobalDeltaEngine.engine.locateObject(cls).asElement();
    evolve.setEnabled(perspective.getTransitive().contains(me.getHomeStratum()));
    
    evolve.addActionListener(new ActionListener()
    {
			public void actionPerformed(ActionEvent event)
			{
				coordinator.startTransaction("Evolve component", "Remove evolution");
				ClassifierClipboardActionsGem.makeEvolveAction(owner, cls, figureFacet, iface, stereotype, false);
				coordinator.commitTransaction();
			}
    });
		return evolve;
	}

	public static ToolFigureClassification getTypedToolClassification(
			String type,
			ClassifierSizes sizes,
			boolean displayOnlyIcon,
			boolean suppressAttributes,
			boolean suppressOperations,
			boolean suppressContents,
			boolean havePorts,
			UPoint point)
	{
		UDimension pOffset = new UDimension(8, 8);
		if (havePorts)
			pOffset = new UDimension(16, 16);
		if (!displayOnlyIcon &&
				!sizes.getFull().addToPoint(pOffset).addToExtent(pOffset.multiply(2).negative()).contains(point))
			return new ToolFigureClassification(type, "port");
		if (sizes.getAttributes().contains(point))
			return new ToolFigureClassification("class", "attribute");
		if (sizes.getOperations().contains(point))
			return new ToolFigureClassification("class", "operation");
		UDimension offset = new UDimension(8, 8);
		if (!suppressContents && sizes.getContents().addToPoint(offset).addToExtent(offset.multiply(2).negative()).contains(point))
			return new ToolFigureClassification("class", "part");
		if (displayOnlyIcon)
		{
			if (!suppressAttributes)
				return new ToolFigureClassification(type, "attribute");
			if (!suppressOperations)
				return new ToolFigureClassification(type, "operation");
		}
		return null;
	}
}
