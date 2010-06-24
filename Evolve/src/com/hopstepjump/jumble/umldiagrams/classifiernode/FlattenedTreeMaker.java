package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.backbone.nodes.simple.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.easydock.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.repositorybrowser.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

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
		// TODO Auto-generated method stub
		return null;
	}
}
