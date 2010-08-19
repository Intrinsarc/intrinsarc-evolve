package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.repositorybrowser.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

/**
 * shows the resemblance of a given element from the perspective of a given stratum
 * @author andrew
 */
public class ResemblancePerspectiveTree
{
  private Set<DEElement> base;
  private DEStratum perspective;
  private UMLNodeRendererGem render = new UMLNodeRendererGem(null);
  
  public static JMenuItem makeMenuItem(
      final DiagramFacet diagram,
      final BasicNodeFigureFacet figure,
      final ToolCoordinatorFacet coordinator)
  {
    JMenuItem item = new JMenuItem("Resemblance perspective...");
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
        IDeltaEngine engine = GlobalDeltaEngine.engine;
        
        ContainerFacet container = figure.getContainedFacet().getContainer();
        Package perspective = repository.findVisuallyOwningStratum(diagram, container);
        DEStratum dePerspective =  engine.locateObject(perspective).asStratum();
        DEElement deElement = engine.locateObject(figure.getSubject()).asElement();
        
        String title =
          "<html>Resemblance of <b>" + deElement.getFullyQualifiedName() +
          "</b> from perspective <b>" + dePerspective.getFullyQualifiedName() + "</b>";
        
        // make the tree
        JPanel panel = new JPanel(new BorderLayout());        
        panel.add(new JLabel(title), BorderLayout.NORTH);
        JTree tree = new ResemblancePerspectiveTree(dePerspective, deElement).makeTree();
        JScrollPane scroll = new JScrollPane(tree);
        panel.add(scroll, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(400, 200));
        
        coordinator.invokeAsDialog(
        		null,
            "Filtered resemblance perspective",
            panel,
            null,
            0,
            null);
      }
    });
    
    return item; 
  }

  public ResemblancePerspectiveTree(DEStratum perspective, DEElement start)
  {
    this.perspective = perspective;
    this.base = start.getReplacesOrSelf();
  }
  
  public JTree makeTree()
  {    
  	// get all the entries to make up the full tree
  	Set<DEElement> all = new HashSet<DEElement>();
  	all.addAll(base);
  	int oldSize = 1;
  	do
  	{
  		oldSize = all.size();
  		Set<DEElement> newFinds = new HashSet<DEElement>();
  		for (DEElement element : all)
  		{
  			newFinds.addAll(element.getSubElementClosure(perspective, false));
  			newFinds.addAll(element.getSuperElementClosure(perspective, false));
  		}
  		all.addAll(newFinds);
  	}
  	while (oldSize != all.size());
  	
  	
  	Set<DEElement> refinedSubs = new HashSet<DEElement>(DEComponent.extractLowestCommonSubtypes(perspective, all, false));
  	if (refinedSubs.size() == 0)
  		refinedSubs.addAll(base);
  	
  	// sort the elements now, preferring those which include the base(s)
  	List<DEElement> roots = new ArrayList<DEElement>(refinedSubs);
  	Collections.sort(roots, new Comparator<DEElement>()
		{
			public int compare(DEElement a, DEElement b)
			{
				Set<DEElement> as = a.getSuperElementClosure(perspective, false);
				as.add(a);
				Set<DEElement> bs = b.getSuperElementClosure(perspective, false);
				bs.add(b);
				boolean aok = as.containsAll(base);
				boolean bok = bs.containsAll(base);

				// choose which one contains the base
				if (aok && !bok)
					return -1;
				if (bok && !aok)
					return 1;
				// if both contain the base, choose the deepest hierarcy
				boolean alower = as.contains(b);
				boolean blower = bs.contains(a);
				if (alower && !blower)
					return -1;
				if (!alower && blower)
					return 1;
				// otherwise, sort by name
				return a.getName().compareTo(b.getName());				
			}	
		});
  	
  	DefaultMutableTreeNode root = new DefaultMutableTreeNode();
  	boolean line = false;
  	boolean start = true;
  	for (DEElement r : roots)
  	{
  		if (!start && !line && !r.getSuperElementClosure(perspective, false).containsAll(base) && !base.contains(r))
  		{
  			root.add(new DefaultMutableTreeNode(""));  // a separator
  			line = true;
  		}
  		root.add(expandOut(root, base, r, 0, false, false));
  		start = false;
  	}
  	
    JTree tree = new JTree(root);
    tree.setRootVisible(false);
    
    TreeExpander.expandEntireTree(tree, true, null);
    tree.setCellRenderer(render.getTreeCellRenderer());
    
    return tree;
  }

  private DefaultMutableTreeNode expandOut(
      DefaultMutableTreeNode parent,
      Set<DEElement> base,
      DEElement element,
      int depth,
      boolean circular,
      boolean redundant)
  {
    // add the element and any children in the resemblance graph
    String annotation = "";
    if (element.isReplacement())
    {
      annotation = " (substitutes for ";
      List<DEElement> list = new ArrayList<DEElement>(element.getReplaces());
      int size = list.size();
      int lp = 0;
      for (DEElement r : list)
      {
        annotation += r.getFullyQualifiedName();
        lp++;
        if (lp != size)
          annotation += ", ";
      }
      annotation += ")";
    }
    
    // add further annotations
    if (depth >= 2)
    {
      if (circular)
        annotation += " (circular)";
      else
      if (redundant)
        annotation += " (redundant)";
    }
    
    boolean isBase = base.contains(element);
    boolean superType = element.getSubElementClosure(perspective, true).containsAll(base);
    boolean subType = element.getSuperElementClosure(perspective, true).containsAll(base);
    
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(
        new UMLTreeUserObject(
            (Element) element.getRepositoryObject(),
            ShortCutType.NONE,
            null,
            element.getFullyQualifiedName() + annotation,
            depth >= 2 && (circular || redundant),
            isBase ? 1 : (superType || subType ? 0 : 2),
            0));
    
    // don't go any further if there is a problem
    if (!circular && !redundant || depth < 2)
    {
      Set<DEElement> filteredResembles = element.getResembles(perspective, true, depth < 2);
      for (DEElement r : element.getResembles(perspective, false, false))
    		node.add(expandOut(node, base, r, depth + 1, r.hasDirectCircularResemblance(perspective), !filteredResembles.contains(r)));
    }
    
    return node;
  }
}
