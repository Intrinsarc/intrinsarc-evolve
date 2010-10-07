package com.hopstepjump.beanimporter;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.text.AttributedCharacterIterator.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.javapro.jicon.*;

interface BeanFieldListener
{
	public void action(BeanField field);
}

public class BeanImporter
{
	public static final ImageIcon BEAN_ADD_ICON = IconLoader.loadIcon("bean_add.png");
	public static final ImageIcon BEAN_ICON = IconLoader.loadIcon("bean.png");
	public static final ImageIcon BEAN_IMPORT_ICON = IconLoader.loadIcon("bean_import.png");
	public static final ImageIcon CLASSPATH_ICON = IconLoader.loadIcon("folder_brick.png");
	public static final ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");
	public static final ImageIcon ARROW_UP_ICON = IconLoader.loadIcon("arrow_up_slanted.png");
	public static final ImageIcon ARROW_DOWN_ICON = IconLoader.loadIcon("arrow_down_slanted.png");
	public static final ImageIcon PACKAGE_ICON = IconLoader.loadIcon("impl_package.png");
	public static final ImageIcon LEAF_ICON = IconLoader.loadIcon("leaf.png");
	public static final ImageIcon PRIMITIVE_ICON = IconLoader.loadIcon("class.png");
	public static final ImageIcon INTERACE_ICON = IconLoader.loadIcon("interface.png");
	public static final ImageIcon ATTRIBUTE_ICON = IconLoader.loadIcon("tree-public-attribute.png");
	public static final ImageIcon PORT_ICON = IconLoader.loadIcon("tree-port.png");
	public static final ImageIcon PROVIDED_ICON = IconLoader.loadIcon("provided.png");
	public static final ImageIcon REQUIRED_ICON = IconLoader.loadIcon("required.png");
	public static final ImageIcon BOX_ATTRIBUTE_ICON = IconLoader.loadIcon("box-attribute.png");
  public static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error-decorator.png");

	private ToolCoordinatorFacet coordinator;
	private Package current;
	private Package stratum;
	private Map<String, BeanPackage> pkgs;

	private Set<BeanClass> leaves = new HashSet<BeanClass>();
	private JTree pkgTree;
	private JTree beanTree;
	private JTree leafTree;
	private JTree featureTree;	
	private BeanFinder finder;
	private JLabel importLabel;
	private JLabel featureLabel;
	private JButton importLeaves;

	public BeanImporter(ToolCoordinatorFacet coordinator, Package current, Map<String, BeanPackage> pkgs, BeanFinder finder)
	{
		this.coordinator = coordinator;
		this.current = current;
		// get the stratum
		if (UMLTypes.isStratum(current)) 
			stratum = current;
		else
			stratum = GlobalSubjectRepository.repository.findOwningStratum(current);
		this.pkgs = pkgs;
		this.finder = finder;
	}

	public int importBeansAndLeaves()
	{
		int count[] = new int[]{0};
		do
		{
			count[0] = 0;
			for (BeanPackage p : pkgs.values())
				p.fixUpBeanTypes(finder, count);
		} while (count[0] != 0);
		fixUpPorts();
		
		String title = "stratum " + stratum.getName() + (current != stratum ? ", package " + current.getName() : "");
		final JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panel.setPreferredSize(new Dimension(700, 600));
		
		// the bean tree
		beanTree = makeTree(false);
		addTogglePrimitiveItem(beanTree);
		
		// the package tree
		pkgTree = makePackageTree();
		expandTopElement(pkgTree);
		pkgTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
    {
      public void valueChanged(TreeSelectionEvent e)
      {
      	refreshBeanTree();
      }
    });
		
		// the top half
		final JSplitPane insideTop = new JSplitPane();
		JPanel topLeft = new JPanel(new BorderLayout());
		JPanel topRight = new JPanel(new BorderLayout());
		JPanel insideTopLeft = new JPanel(new BorderLayout());
		insideTopLeft.add(new JLabel("Java Packages"), BorderLayout.NORTH);
		insideTopLeft.add(new JScrollPane(pkgTree), BorderLayout.CENTER);
		topLeft.add(insideTopLeft, BorderLayout.CENTER);
		
		JPanel insideTopRight = new JPanel(new BorderLayout());
		insideTopRight.add(new JLabel("Beans"), BorderLayout.NORTH);
		insideTopRight.add(new JScrollPane(beanTree), BorderLayout.CENTER);
		topRight.add(insideTopRight, BorderLayout.CENTER);
		insideTop.setLeftComponent(topLeft);
		insideTop.setRightComponent(topRight);
		panel.setTopComponent(insideTop);
		
		// the buttons in the middle
		JPanel buttons = new JPanel();
		final JButton add = createAddToImportListButton();
		final JButton remove = createRemoveFromImportListButton();
		buttons.add(remove);
		buttons.add(add);
		beanTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				boolean enabled = false;
				if (beanTree.getSelectionPaths() != null)
					for (TreePath path : beanTree.getSelectionPaths())
					{
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
						TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
						if (!obj.isDisabled())
							enabled = true;
					}
				add.setEnabled(enabled);					
			}			
		});

		// the importing and removing buttons
		importLeaves = new JButton("Import all", BEAN_IMPORT_ICON);
		importLeaves.setEnabled(false);
		JButton cancel = new JButton("Cancel", CANCEL_ICON);

		// the lower half
		JPanel lower = new JPanel(new BorderLayout());
		lower.add(buttons, BorderLayout.NORTH);
		final JSplitPane insideBottom = new JSplitPane();
		JPanel bottomLeft = new JPanel(new BorderLayout());
		JPanel bottomRight = new JPanel(new BorderLayout());
		importLabel = new JLabel();
		leafTree = makeTree(false);
		addTogglePrimitiveItem(leafTree);
		refreshLeafTree();
    ToolTipManager.sharedInstance().registerComponent(leafTree);
		leafTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				remove.setEnabled(leafTree.getSelectionCount() != 0);		
				refreshFeatureTree();
			}			
		});		

		JPanel insideBottomLeft = new JPanel(new BorderLayout());
		insideBottomLeft.add(importLabel, BorderLayout.NORTH);
		insideBottomLeft.add(new JScrollPane(leafTree), BorderLayout.CENTER);
		bottomLeft.add(insideBottomLeft, BorderLayout.CENTER);
		
		JPanel insideBottomRight = new JPanel(new BorderLayout());
		featureLabel = new JLabel("Features");
		insideBottomRight.add(featureLabel, BorderLayout.NORTH);
		featureTree = makeTree(false);
    ToolTipManager.sharedInstance().registerComponent(featureTree);
		featureTree.setEnabled(false);
		addFeatureMenuItems();
		insideBottomRight.add(new JScrollPane(featureTree), BorderLayout.CENTER);		
		bottomRight.add(insideBottomRight, BorderLayout.CENTER);
		
		insideBottom.setLeftComponent(bottomLeft);
		insideBottom.setRightComponent(bottomRight);
		lower.add(insideBottom, BorderLayout.CENTER);		
		panel.setBottomComponent(lower);
				
		conformSize(new JButton[]{add, remove, importLeaves, cancel});
		int buttonNumber[] = new int[]{0};
		assignNumber(importLeaves, buttonNumber, 1);
		
		// set the sizes
		panel.setDividerLocation((int)(panel.getPreferredSize().height * 0.45));
		insideBottom.setDividerLocation(panel.getPreferredSize().width / 2 - 5);
		insideTop.setDividerLocation(panel.getPreferredSize().width / 2 - 5);

		coordinator.invokeAsDialog(
				BEAN_ADD_ICON,
				"Import into " + title,
				panel,
				new JButton[]{importLeaves, cancel},
				0,
				null);
		return buttonNumber[0];
	}

	private void assignNumber(JButton button, final int[] buttonNumber, final int number)
	{
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonNumber[0] = number;
			}
		});
	}

	private void fixUpPorts()
	{
		// get rid of any bad ports
		for (BeanPackage p : pkgs.values())
			p.fixUpUsingFinder(finder);
	}

	public static void conformSize(JButton[] buttons)
	{
		int maxMinWidth = 0;
		int maxMinHeight = 0;
		for (JButton b : buttons)
		{
			maxMinWidth = Math.max(maxMinWidth, b.getMinimumSize().width);
			maxMinHeight = Math.max(maxMinHeight, b.getMinimumSize().height);
		}
		for (JButton b : buttons)
			b.setPreferredSize(new Dimension(maxMinWidth, maxMinHeight));
	}

	private JButton createRemoveFromImportListButton()
	{
		final JButton remove = new JButton("Remove from import list", ARROW_UP_ICON);
		remove.setEnabled(false);
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove all the selected elemenets from the leaf list
				final Set<BeanClass> classes = new HashSet<BeanClass>();
		    for (TreePath path : leafTree.getSelectionModel().getSelectionPaths())
		    {
		    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		    	TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
		    	leaves.remove(obj.getBeanClass());
		    	classes.add(obj.getBeanClass());
		    }
		    refreshLeafTree();
		    refreshBeanTree();
		    
		    // select any elements in the leaf tree we have just removed
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) beanTree.getModel().getRoot();
				List<TreePath> paths = new ArrayList<TreePath>();				
				TreeExpander.expandTree(beanTree, new TreePath(root), true, new TreeSelector()
				{
					public boolean selectNode(TreeNode node)
					{
			    	DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
			    	if (n.getUserObject() instanceof TreeElementUserObject)
			    	{
			    		return classes.contains(((TreeElementUserObject) n.getUserObject()).getBeanClass());
			    	}
			    	return false;
					}					
				}, paths, 2);
	    	beanTree.setSelectionPaths(paths.toArray(new TreePath[0]));
			}			
		});
		return remove;
	}

	private JButton createAddToImportListButton()
	{
		final JButton add = new JButton("Add to import list", ARROW_DOWN_ICON);
		add.setEnabled(false);
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// add all the selected elements to the leaf list
				final Set<BeanClass> classes = new HashSet<BeanClass>();
		    for (TreePath path : beanTree.getSelectionPaths())
		    {
		    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		    	TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
		    	leaves.add(obj.getBeanClass());
		    	classes.add(obj.getBeanClass());
		    }
		    refreshLeafTree();
		    refreshBeanTree();
		    
		    // select any elements in the leaf tree we have just imported
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) leafTree.getModel().getRoot();
				List<TreePath> paths = new ArrayList<TreePath>();				
				TreeExpander.expandTree(leafTree, new TreePath(root), true, new TreeSelector()
				{
					public boolean selectNode(TreeNode node)
					{
			    	DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
			    	if (n.getUserObject() instanceof TreeElementUserObject)
			    	{
			    		return classes.contains(((TreeElementUserObject) n.getUserObject()).getBeanClass());
			    	}
			    	return false;
					}					
				}, paths, 2);
	    	leafTree.setSelectionPaths(paths.toArray(new TreePath[0]));
			}
		});
		return add;
	}

	private void addTogglePrimitiveItem(final JTree tree)
	{
    tree.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
        Point pt = e.getPoint();
        final TreePath path = tree.getPathForLocation(pt.x, pt.y);
        if (path != null)
	        if (e.getButton() == MouseEvent.BUTTON3 && path.getParentPath() != null)
	        {
	          JPopupMenu popup = new JPopupMenu();
	
	          // allow it to be suppressed
	          JMenuItem suppress = new JMenuItem("Toggle bean / primitive");
	          popup.add(suppress);
	          suppress.addActionListener(new ActionListener()
	          {
							public void actionPerformed(ActionEvent e)
							{
								TreePath paths[] = tree.getSelectionPaths();
								if (paths == null)
									paths = new TreePath[]{path};
							
								for (TreePath path : paths)
								{
									DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
									if (node.getUserObject() instanceof TreeElementUserObject)
									{
										TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
										if (obj.getBeanClass() != null)
										{
											obj.getBeanClass().toggleBeanOrPrimitive();
										}
									}
								}
								refreshAfterPrimitiveBeanToggle();
							}
	          });
	          popup.show(tree, pt.x, pt.y);
	        }
      }
    });
	}
	
	private void refreshAfterPrimitiveBeanToggle()
	{
		fixUpPorts();
		refreshBeanTree();
		refreshLeafTree();
		refreshFeatureTree();
	}
	
	private void addFeatureMenuItems()
	{
    featureTree.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
        Point pt = e.getPoint();
        final TreePath path = featureTree.getPathForLocation(pt.x, pt.y);
        featureTree.setSelectionPath(path);
        Object node = path.getLastPathComponent();
        if (((DefaultMutableTreeNode) node).getUserObject() instanceof String)
        	return;
        
        if (path != null)
	        if (e.getButton() == MouseEvent.BUTTON3 && path.getParentPath() != null)
	        {
	          JPopupMenu popup = new JPopupMenu();
	
	          JMenuItem prim = new JMenuItem("Toggle bean / primitive of field types");
	          for (BeanField field : saveSelectedBeanFields())
	          	if (!field.canToggleBeanOrPrimitiveOfTypes(finder))
	          		prim.setEnabled(false);
	          prim.addActionListener(new ActionListener()
	          {
							public void actionPerformed(ActionEvent e)
							{
								handleFeatures(path, true,
									new BeanFieldListener()
									{
										public void action(BeanField field)
										{
											field.toggleBeanOrPrimitiveOfTypes(finder);
										}
									});								
							}
	          });
	          popup.add(prim);
	          
	          JMenuItem port = new JMenuItem("Toggle port / attribute of field");
	          for (BeanField field : saveSelectedBeanFields())
	          	if (!field.canTogglePortOrAttribute(finder))
	          		port.setEnabled(false);
	          port.addActionListener(new ActionListener()
	          {
							public void actionPerformed(ActionEvent e)
							{
								handleFeatures(path, true,
									new BeanFieldListener()
									{
										public void action(BeanField field)
										{
											field.togglePortOrAttribute(finder);
										}
									});								
							}
	          });
	          popup.add(port);
	          
	          JMenuItem strikethrough = new JMenuItem("Toggle ignore");
	          strikethrough.addActionListener(new ActionListener()
	          {
	  					public void actionPerformed(ActionEvent e)
	  					{
	  						handleFeatures(path, true, 
	  							new BeanFieldListener()
		  						{
		  							public void action(BeanField field)
		  							{
		  								field.toggleIgnore();
		  							}
		  						});
	  					}
	          });
	          popup.add(strikethrough);

	          popup.show(featureTree, pt.x, pt.y);
	        }
      }
    });
	}

	private void handleFeatures(final TreePath path, boolean fullRefresh, BeanFieldListener listener)
	{
		TreePath paths[] = featureTree.getSelectionPaths();
		if (paths == null)
			paths = new TreePath[]{path};
	
		final List<BeanField> fields = new ArrayList<BeanField>();
		for (TreePath p : paths)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) p.getLastPathComponent();
			if (node.getUserObject() instanceof TreeElementUserObject)
			{
				TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
				if (obj.getBeanField() != null)
				{
					listener.action(obj.getBeanField());
					fields.add(obj.getBeanField());
				}
			}
		}
		
		// either refresh all trees, or just refresh the feature tree
		if (fullRefresh)
			refreshAfterPrimitiveBeanToggle();
		else
			refreshFeatureTree();
		
		List<TreePath> newPaths = new ArrayList<TreePath>();
		TreeExpander.expandTree(featureTree, null, true, new TreeSelector()
		{
			public boolean selectNode(TreeNode node)
			{
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
				if (n.getUserObject() instanceof TreeElementUserObject)
					return fields.contains(((TreeElementUserObject) n.getUserObject()).getBeanField());
				return false;
			}
			
		}, newPaths, 3);
		featureTree.setSelectionPaths(newPaths.toArray(new TreePath[0]));
	}
	
	private DefaultMutableTreeNode findSingleSelectedNode(JTree tree)
  {
    TreePath path = tree.getSelectionModel().getSelectionPath();
    if (path != null && tree.getSelectionCount() == 1)
      return (DefaultMutableTreeNode) path.getLastPathComponent();
    return null;
  }
  
	private JTree makeTree(boolean expandMenu)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		DefaultTreeModel model = new DefaultTreeModel(root);

		final JTree tree = new JTree(model);
		tree.setRootVisible(false);
  	tree.setBorder(new EmptyBorder(2, 2, 2, 2));
		tree.setCellRenderer(new DefaultTreeCellRenderer()
		{
			public Component getTreeCellRendererComponent(
					JTree tree,
					Object value,
					boolean selected,
					boolean expanded,
					boolean leaf,
					int row,
					boolean hasFocus)
			{
				// reset the font
				if (getFont() != null)
				{
					Map<Attribute, Object> nostrike = new HashMap<Attribute, Object>();
					nostrike.put(TextAttribute.STRIKETHROUGH, false);
					setFont(getFont().deriveFont(Font.PLAIN).deriveFont(nostrike));
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				
				if (node.getUserObject() instanceof String)
				{
					String name = (String) node.getUserObject();
					Icon icon = null;
					if (name.startsWith("Classpath"))
						icon = CLASSPATH_ICON;
					if (name.startsWith("Provided"))
						icon = PROVIDED_ICON;
					if (name.startsWith("Required"))
						icon = REQUIRED_ICON;
					if (name.contains("Attribute"))
						icon = BOX_ATTRIBUTE_ICON;
						
		      setLeafIcon(icon);
		      setOpenIcon(icon);
		      setClosedIcon(icon);
					
					return super.getTreeCellRendererComponent(tree, name, false, expanded, leaf, row, false);
				}
				
				
				TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
				
				// otherwise, this is a complex node
				Icon icon = null;
				String name = obj.getName();
				String refresh = " (refresh)";
				switch (obj.getType())
				{
					case PACKAGE:
						icon = PACKAGE_ICON;
						break;
					case BEAN:
						if (tree == beanTree)
							icon = BEAN_ICON;
						else
							icon = LEAF_ICON;
						if (obj.getBeanClass().isAbstract())
							setFont(getFont().deriveFont(Font.ITALIC));
						// is this a refresh?
						if (finder.isRefreshedClass(obj.getBeanClass()))
							name += refresh;
						break;
					case INTERFACE:
						icon = INTERACE_ICON;
						if (tree == beanTree)
							name = obj.getBeanClass().getOriginalName();
						// is this a refresh?
						if (finder.isRefreshedInterface(obj.getBeanClass()))
							name += refresh;
						break;
					case PRIMITIVE:
						icon = PRIMITIVE_ICON;
						// is this a refresh?
						if (finder.isRefreshedClass(obj.getBeanClass()))
							name += refresh;
						break;
					case ATTRIBUTE:
						{
							BeanField field = obj.getBeanField();
							icon = ATTRIBUTE_ICON;
							name = expandName(name)  + " : " + field.getTypesString(finder) + (field.isMany() ? " [0..*]" : "");
						}
						break;
					case PORT:
						{
							BeanField field = obj.getBeanField();
							icon = PORT_ICON;
							name = expandName(name)  + " : " + field.getTypesString(finder) + (field.isMany() ? " [0..*]" : "");
						}
						break;
				}
				
				// possibly add an error as a tooltip
				String msg = "";
				
				if (obj.getBeanClass() != null)
				{
					BeanClass bc = obj.getBeanClass();
					if (bc.isSynthetic())
					{
						if (bc.getWantsThis() == null)
							msg = "Required by none";  // should never happen
						else
						{
							msg = "Required by";
							for (BeanClass w : bc.getWantsThis())
								msg += " " + w.getName();
						}
					}
				}
				
				
				if (obj.getBeanClass() != null && obj.getBeanClass().isInError(finder) && tree == leafTree)
				{
					icon = new DecoratedIcon(icon, ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
					msg = obj.getBeanClass().getError(finder);
				}
				else
				if (obj.getBeanField() != null && obj.getBeanField().isInError() && tree == featureTree)
				{
					icon = new DecoratedIcon(icon, ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
					msg = obj.getBeanField().getError();
				}
				else
				if (obj.getBeanField() != null && obj.getBeanField().isIgnore() && tree == featureTree)
				{
					Map<Attribute, Object> strikethrough = new HashMap<Attribute, Object>();
					strikethrough.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
					setFont(getFont().deriveFont(strikethrough));
				}
				
				if (msg.length() != 0)
					setToolTipText(msg);
				else
					setToolTipText(null);
				
				// set the icon
	      setLeafIcon(icon);
	      setOpenIcon(icon);
	      setClosedIcon(icon);
	      
	      Component comp = super.getTreeCellRendererComponent(tree, name, selected, expanded, leaf, row, hasFocus);
	      if (obj.getBeanClass() != null && obj.getBeanClass().isSynthetic())
	      	comp.setForeground(Color.GRAY);
	      if (obj.isDisabled())
	      	comp.setEnabled(false);
	      return comp;
			}

			private String expandName(String name)
			{
				if (name.equals(""))
					return "contents";
				return name;
			}
		});
		
		if (expandMenu)
		{
			JMenuItem[] items = new JMenuItem[]{new JMenuItem(), new JMenuItem()};
			TreeExpander.addExpandAndCollapseItems(tree, items);
			TreeExpander.addPopupMenu(tree, items);
		}

		return tree;
	}
	
	private void expandTopElement(JTree tree)
  {
		// expand the first elements
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		TreeExpander.expandTree(tree, new TreePath(root), true, null, null, 2);
  }
  
  private void selectFirstRow(JTree tree)
  {
    tree.getSelectionModel().setSelectionPath(new TreePath(tree.getModel().getRoot()));
  }

	private JTree makePackageTree()
	{
		JTree tree = makeTree(true);
		for (BeanPackage root : pkgs.values())
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode("Classpath: " + root.getName());
			((DefaultMutableTreeNode) tree.getModel().getRoot()).add(node);
			addPackages(node, root);
		}
		return tree;
	}

	private void addPackages(DefaultMutableTreeNode node, BeanPackage start)
	{
		for (BeanPackage pkg : start.getChildren())
		{
			if (pkg.definitelyHasBeansSomewhere())
			{
				TreeElementUserObject obj = new TreeElementUserObject(pkg);
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(obj);
				node.add(childNode);
				addPackages(childNode, pkg);
			}
		}
	}

	private void refreshFeatureTree()
	{
		// save any currently selected fields
		Set<BeanField> remembered = saveSelectedBeanFields();
		
		// populate the feature tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Features");
		DefaultTreeModel model = new DefaultTreeModel(root);

  	// populate with possible classes
  	DefaultMutableTreeNode node = findSingleSelectedNode(leafTree);
  	DefaultMutableTreeNode requiredPorts = null;
  	
  	featureLabel.setText("Features");
  	if (node != null && node != leafTree.getModel().getRoot())
  	{
  		if (node.getUserObject() instanceof TreeElementUserObject)
  		{
	  		TreeElementUserObject user = (TreeElementUserObject) node.getUserObject();
	  		BeanClass cls = user.getBeanClass();
	  		featureLabel.setText("Features of " + cls.getNode().name);
	  		if (cls.isBean())
	  		{	  		
		  		// handle the different types of ports
		  		processFeatures(cls, false, "Provided Ports", true, false, null, root); 
		  		requiredPorts = processFeatures(cls, false, "Required Ports", true, true, null, root); 
		  		processFeatures(cls, false, "Required Ports", false, true, requiredPorts, root); 
		  		
		  		// handle the different types of attributes
		  		processFeatures(cls, true, "Read-only Attributes", true, false, null, root);
		  		processFeatures(cls, true, "Write-only Attributes", false, true, null, root);
		  		processFeatures(cls, true, "Attributes", true, true, null, root);
	  			featureTree.setEnabled(true);
	  		}
	  		else
	  			featureTree.setEnabled(false);
  		}
  	}
  	
  	featureTree.setModel(model);
  	expandTopElement(featureTree);
  	reselectBeanFields(remembered);
	}
	
	private DefaultMutableTreeNode processFeatures(BeanClass cls, boolean attributes, String name, boolean read, boolean write, DefaultMutableTreeNode parentNode, DefaultMutableTreeNode root)
	{
		for (BeanField attr : attributes ? cls.getAttributes() : cls.getPorts())
		{
			if (attr.isReadOnly() && read && !write ||
					attr.isWriteOnly() && !read && write ||
					!attr.isReadOnly() && !attr.isWriteOnly() && read && write)
			{
				if (parentNode == null)
				{
					parentNode = new DefaultMutableTreeNode(name);
					root.add(parentNode);
				}
				TreeElementUserObject cUser = new TreeElementUserObject(attr, attributes ? BeanTypeEnum.ATTRIBUTE : BeanTypeEnum.PORT);
				DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(cUser);
				parentNode.add(cNode);
			}
		}

		return parentNode;
	}

	private void refreshBeanTree()
	{
		// save the selection
		Set<BeanClass> classes = saveSelectedBeanClasses(beanTree);

		// populate the bean tree
  	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Beans");
  	DefaultTreeModel model = new DefaultTreeModel(root);
  	
  	// populate with possible classes
  	DefaultMutableTreeNode node = findSingleSelectedNode(pkgTree);
  	if (node != null && node != pkgTree.getModel().getRoot())
  	{
  		if (node.getUserObject() instanceof TreeElementUserObject)
  		{
	  		TreeElementUserObject user = (TreeElementUserObject) node.getUserObject();
	  		BeanPackage pkg = user.getBeanPackage();
	  		
	  		for (BeanClass cls : pkg.getBeanClasses(false))
	  		{
	  			if (cls.getType() != BeanTypeEnum.BAD)
	  			{
		  			cls.fixUpUsingFinder(finder);
		  			TreeElementUserObject cUser = new TreeElementUserObject(cls);
		  			if (isPresentAsLeaf(cls))
		  			{
		  				cUser.setDisabled(true);
		  			}
		  			else
			  			cls.setSynthetic(false);
	
		  			DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(cUser);
		  			root.add(cNode);
	  			}
	  		}
  		}
  	}
  	
  	beanTree.setModel(model);
  	
  	// reselect
  	reselectBeanClasses(beanTree, classes);
	}
	
	private boolean isPresentAsLeaf(BeanClass cls)
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) leafTree.getModel().getRoot();
		for (int lp = 0; lp < root.getChildCount(); lp++)
		{
			BeanClass leaf = ((TreeElementUserObject) ((DefaultMutableTreeNode) root.getChildAt(lp)).getUserObject()).getBeanClass();
			if (leaf == cls)
				return true;
		}
		return false;
	}

	private Set<BeanClass> saveSelectedBeanClasses(JTree tree)
	{
		Set<BeanClass> classes = new HashSet<BeanClass>();
		if (tree.getSelectionModel().getSelectionPaths() != null)
	    for (TreePath path : tree.getSelectionModel().getSelectionPaths())
	    {
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
	    	TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
	    	classes.add(obj.getBeanClass());
	    }
		return classes;
	}
	
	private void reselectBeanClasses(JTree tree, final Set<BeanClass> classes)
	{
  	List<TreePath> newPaths = new ArrayList<TreePath>();
		TreeExpander.expandTree(tree, null, true, new TreeSelector()
		{
			public boolean selectNode(TreeNode node)
			{
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
				if (n.getUserObject() instanceof TreeElementUserObject)
					return classes.contains(((TreeElementUserObject) n.getUserObject()).getBeanClass());
				return false;
			}			
		}, newPaths, 3);
		tree.setSelectionPaths(newPaths.toArray(new TreePath[0]));
	}
	
	private Set<BeanField> saveSelectedBeanFields()
	{
		Set<BeanField> fields = new HashSet<BeanField>();
		if (featureTree.getSelectionModel().getSelectionPaths() != null)
	    for (TreePath path : featureTree.getSelectionModel().getSelectionPaths())
	    {
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
	    	TreeElementUserObject obj = (TreeElementUserObject) node.getUserObject();
	    	fields.add(obj.getBeanField());
	    }
		return fields;
	}
	
	private void reselectBeanFields(final Set<BeanField> fields)
	{
  	List<TreePath> newPaths = new ArrayList<TreePath>();
		TreeExpander.expandTree(featureTree, null, true, new TreeSelector()
		{
			public boolean selectNode(TreeNode node)
			{
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;
				if (n.getUserObject() instanceof TreeElementUserObject)
					return fields.contains(((TreeElementUserObject) n.getUserObject()).getBeanField());
				return false;
			}			
		}, newPaths, 3);
		featureTree.setSelectionPaths(newPaths.toArray(new TreePath[0]));
	}
	
	private synchronized void refreshLeafTree()
	{
		// save the selection
		Set<BeanClass> classes = saveSelectedBeanClasses(leafTree);

		// populate the bean tree
  	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Import List");
  	DefaultTreeModel model = new DefaultTreeModel(root);
  	
  	int errors = 0;
  	List<BeanClass> all = new ArrayList<BeanClass>(leaves);
  	int oldSize;
  	do
  	{
  		oldSize = all.size();
  		
  		// clear out all "wanted"
	  	for (BeanClass one : new HashSet<BeanClass>(all))
	  		one.clearWantsThis();
  		
	  	for (BeanClass one : new HashSet<BeanClass>(all))
	  	{
	  		// get any dependents for the bean
	  		extractDependents(all, one);
	  	}
  	}
  	while (oldSize != all.size());
  	
  	List<BeanClass> sorted = new ArrayList<BeanClass>(all);
  	Collections.sort(sorted, new BeanClassComparator());
  	
  	for (BeanClass cls : sorted)
  	{
  		// see if any fields have errors
  		cls.markFieldErrors(finder);
  		
  		TreeElementUserObject obj = new TreeElementUserObject(cls, true);
  		
  		if (cls.isInError(finder))
  			errors++;
  		DefaultMutableTreeNode node = new DefaultMutableTreeNode(obj);
  		root.add(node);
  	}

  	if (errors == 0)
  		importLabel.setText("Import List");
  	else
  		importLabel.setText("<html><body text='red'><b>Import List: " + errors + " error" + (errors == 1 ? "" : "s"));
  	leafTree.setModel(model);
  	importLeaves.setEnabled(root.getChildCount() != 0 && errors == 0);
  	
  	// reselect
  	reselectBeanClasses(leafTree, classes);
	}

	private void extractDependents(List<BeanClass> all, BeanClass bean)
	{
		// primitives, interfaces, beans
		finder.constructPrimitives(all, bean);
		finder.constructInterfaces(all, bean);
		finder.constructLeaves(all, bean);
	}

	public List<BeanClass> getImportList()
	{
		// translate the import list into beanclasses
		List<BeanClass> ret = new ArrayList<BeanClass>();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) leafTree.getModel().getRoot();
		
		int size = root.getChildCount();
		for (int lp = 0; lp < size; lp++)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(lp);
			if (child.getUserObject() instanceof TreeElementUserObject)
			{
				TreeElementUserObject user = (TreeElementUserObject) child.getUserObject();
				if (user.getBeanClass() != null)
					ret.add(user.getBeanClass());
			}
		}
		return ret;
	}
}
