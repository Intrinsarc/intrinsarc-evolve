package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import org.eclipse.uml2.*;

import com.hopstepjump.backbone.nodes.simple.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.easydock.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.repositorybrowser.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.sun.org.apache.xpath.internal.axes.*;

public class FlattenedTreeMaker
{
	private static final ImageIcon FLAT_ICON = IconLoader.loadIcon("flat.png");

	private ToolCoordinatorFacet coordinator;
	private FigureFacet figureFacet;

	public FlattenedTreeMaker(ToolCoordinatorFacet coordinator, FigureFacet componentFigure)
	{
		this.coordinator = coordinator;
		this.figureFacet = componentFigure;
	}
	
	public ActionListener makeTree()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DEStratum perspective = GlobalDeltaEngine.engine.locateObject(
						GlobalSubjectRepository.repository.findVisuallyOwningStratum(
								figureFacet.getDiagram(), figureFacet.getContainerFacet())).asStratum();
				DEComponent flat = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asComponent();
				Set<DEComponent> above = new HashSet<DEComponent>();
				if (ComponentErrorChecker.isSelfComposed(perspective, above, flat))
				{
					coordinator.invokeErrorDialog(
							"Problem flattening component...",
							"Component directly or indirectly contains itself.  Run a full error check to diagnose");
					return;
				}
				
				final JPanel pane = new JPanel();
				pane.setLayout(new BorderLayout());
				pane.setPreferredSize(new Dimension(600, 600));
	
				IEasyDockable dockable[] = new IEasyDockable[1];
				String title = remakeTree(pane, dockable);
				int x = coordinator.getFrameXPreference(
						RegisteredGraphicalThemes.INITIAL_EDITOR_X_POS);
				int y = coordinator.getFrameYPreference(
						RegisteredGraphicalThemes.INITIAL_EDITOR_Y_POS);
				int width = coordinator.getIntegerPreference(
						RegisteredGraphicalThemes.INITIAL_EDITOR_WIDTH);
				int height = coordinator.getIntegerPreference(
						RegisteredGraphicalThemes.INITIAL_EDITOR_HEIGHT);
	
				dockable[0] = coordinator.getDock().createExternalPaletteDockable(title,
						FLAT_ICON, new Point(x, y), new Dimension(width, height), true, true, pane);
			}
	
			private String remakeTree(final JPanel pane, final IEasyDockable[] dockable)
			{
				DEStratum perspective = GlobalDeltaEngine.engine.locateObject(
						GlobalSubjectRepository.repository.findVisuallyOwningStratum(
								figureFacet.getDiagram(), figureFacet.getContainerFacet())).asStratum();
	
				DEComponent flat = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asComponent();
				BBSimpleElementRegistry registry = new BBSimpleElementRegistry(perspective, flat);
				BBSimpleComponent top = new BBSimpleComponent(registry, flat);
				top.flatten(registry);
				
				final JTree tree = makeTree(top);
				tree.setRootVisible(false);
				TreeExpander.expandTree(tree, null, true, null, null, 3);
				tree.setCellRenderer(new UMLNodeRendererGem(null).getStringTreeCellRenderer());
				JScrollPane scroller = new JScrollPane(tree);
				pane.removeAll();
				pane.add(scroller, BorderLayout.CENTER);
				String title = "Flattened "
						+ GlobalSubjectRepository.repository.getFullyQualifiedName(
								(Element) figureFacet.getSubject(), "::")
						+ " from perspective " + perspective.getFullyQualifiedName();
	
				// provide a refresh option
				final JPopupMenu menu = new JPopupMenu();
				JMenuItem item = new JMenuItem("Refresh");
				menu.add(item);
				item.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						remakeTree(pane, dockable);
					}
				});
				tree.addMouseListener(new MouseAdapter()
				{
					public void mouseReleased(MouseEvent e)
					{
						Point pt = e.getPoint();
						if (e.getButton() == MouseEvent.BUTTON3)
							menu.show(tree, pt.x, pt.y);
					}
				});
				if (dockable[0] != null)
					dockable[0].setTitleText(title);
	
				pane.revalidate();
				return title;
			}
		};
	}

	private JTree makeTree(BBSimpleComponent top)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		JTree tree = new JTree(root);		
		addNodes(root, top, 0);
		return tree;
	}

	private void addNodes(DefaultMutableTreeNode parent, BBSimpleObject object, int depth)
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(object.getTreeDescription());
		parent.add(node);
		Map<String, java.util.List<? extends BBSimpleObject>> map = object.getChildren(depth == 0);
		if (map != null)
			for (String head : map.keySet())
			{
				java.util.List<? extends BBSimpleObject> ls = map.get(head);
				if (!isReallyEmpty(ls))
				{
					DefaultMutableTreeNode header = new DefaultMutableTreeNode(head);
					node.add(header);
					
					for (BBSimpleObject obj : ls)
						if (obj != null)
							addNodes(header, obj, depth + 1);
				}
			}
	}
	
	private boolean isReallyEmpty(java.util.List<? extends BBSimpleObject> ls)
	{
		if (ls == null)
			return true;
		for (BBSimpleObject obj : ls)
			if (obj != null)
				return false;
		return true;
	}
}
