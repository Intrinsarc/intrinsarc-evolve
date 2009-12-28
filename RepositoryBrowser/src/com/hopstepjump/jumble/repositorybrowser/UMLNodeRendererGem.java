package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.awt.Component;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.javapro.jicon.*;

/**
 * @author Andrew
 */

class UMLIconEntry
{
  private UMLElementIconReference reference;
  private Icon icon;

  public UMLIconEntry(UMLElementIconReference reference, Icon icon)
  {
    this.reference = reference;
    this.icon = icon;
  }

  public UMLElementIconReference getReference()
  {
    return reference;
  }

  public Icon getIcon()
  {
    return icon;
  }
}

public class UMLNodeRendererGem implements Gem
{
  private static final Icon DEFAULT_ICON = IconLoader.loadIcon("tree-default.gif");
  private static final Icon ERROR_ICON = IconLoader.loadIcon("error-decorator.png");
  private static final Icon INDIRECT_ERROR_ICON = IconLoader.loadIcon("warning-decorator.png");
  private static final Icon SHORTCUT_ICON = IconLoader.loadIcon("shortcut-decorator.png");

  private DefaultTreeCellRenderer treeCellRenderer = new TreeCellRendererImpl();
  private DefaultTreeCellRenderer stringTreeCellRenderer = new StringTreeCellRendererImpl();
  private List<UMLIconEntry> icons = new ArrayList<UMLIconEntry>();
  private ErrorRegister errors;

  public UMLNodeRendererGem(ErrorRegister errors)
  {
    this.errors = errors;
    setUpIcons();
  }

  private void setUpIcons()
  {
    // order is very important here so we must declare the more specific items first
    addIcon(PackageImpl.class, null, null, ShortCutType.UP, "tree-upshortcut.gif", null);
    addIcon(ModelImpl.class, null, null, ShortCutType.UP, "tree-upshortcut.gif", null);
    addIcon(ProfileImpl.class, null, null, ShortCutType.UP, "tree-upshortcut.gif", null);
    addIcon(PackageImpl.class, null, null, ShortCutType.NORMAL, "relaxed-stratum.png", 
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.STRATUM, CommonRepositoryFunctions.RELAXED, false));
    addIcon(PackageImpl.class, null, null, ShortCutType.NORMAL, "strict-stratum.png", 
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.STRATUM, CommonRepositoryFunctions.RELAXED, true));
    addIcon(PackageImpl.class, "Stratum", null, null, "relaxed-stratum.png",
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.STRATUM, CommonRepositoryFunctions.RELAXED, false));
    addIcon(PackageImpl.class, null, null, null, "strict-stratum.png",
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.STRATUM, CommonRepositoryFunctions.RELAXED, true));
    // this is for the flattened browser
    addIcon(PackageImpl.class, "Package", null, null, "relaxed-stratum.png",
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.STRATUM, CommonRepositoryFunctions.RELAXED, false));
    addIcon(PackageImpl.class, null, null, ShortCutType.NORMAL, "package.png", null);
    addIcon(PackageImpl.class, null, null, null, "package.png", null);
    addIcon(ModelImpl.class, null, null, ShortCutType.NORMAL, "model.png", null);
    addIcon(ModelImpl.class, null, null, null, "model.png", null);
    addIcon(ProfileImpl.class, null, null, ShortCutType.NORMAL, "profile.png", null);
    addIcon(ProfileImpl.class, null, null, null, "profile.png", null);
    addIcon(StereotypeImpl.class, null, null, null, "tree-stereotype.gif", null);
    
    // possible components
    addIcon(ClassImpl.class, null, null, null, "factory.png",
    		new UMLStereotypeDeterminer(CommonRepositoryFunctions.COMPONENT, CommonRepositoryFunctions.FACTORY, false));
    addIcon(ClassImpl.class, null, null, null, "placeholder.png",
  		new UMLStereotypeDeterminer(CommonRepositoryFunctions.COMPONENT, CommonRepositoryFunctions.PLACEHOLDER, false));
    addIcon(ClassImpl.class, null, null, null, "leaf.png", new UMLIconDeterminer()
    {
			public boolean isRelevant(Element element)
			{
				return
					StereotypeUtilities.isStereotypeApplied(element, CommonRepositoryFunctions.COMPONENT) &&
					!UMLTypes.containsParts((Class) element);
			}
    });
    addIcon(ClassImpl.class, null, null, null, "composite.png",    
        new UMLStereotypeDeterminer(CommonRepositoryFunctions.COMPONENT, null, false));
        
    // resemblance and substitution
    addIcon(DependencyImpl.class, null, null, null, "evolution.png",
        new UMLIconDeterminer()
        {
          public boolean isRelevant(Element element)
          {
            Dependency dep = (Dependency) element;
            return dep.isReplacement() && dep.isResemblance();
          }          
        });
    addIcon(DependencyImpl.class, "substitutes", null, null, "replacement.png",
        new UMLIconDeterminer()
        {
          public boolean isRelevant(Element element)
          {
            Dependency dep = (Dependency) element;
            return dep.isReplacement();
          }          
        });
    addIcon(DependencyImpl.class, "resembles", null, null, "resemblance.png",
        new UMLIconDeterminer()
        {
          public boolean isRelevant(Element element)
          {
            Dependency dep = (Dependency) element;
            return dep.isResemblance();
          }          
        });

    // standard elements
    addIcon(ClassImpl.class, null, null, null, "tree-class.png", null);
    addIcon(DependencyImpl.class, "requires", null, null, "required.png", new UMLIconDeterminer()
    {
			public boolean isRelevant(Element element)
			{
				Dependency impl = (Dependency) element;
				return impl.undeleted_getDependencyTarget() instanceof Interface && impl.getOwner() instanceof Class;
			}    	
    });
    addIcon(ImplementationImpl.class, "provides", null, null, "provided.png", new UMLIconDeterminer()
    {
			public boolean isRelevant(Element element)
			{
				Implementation impl = (Implementation) element;
				return impl.getOwner() instanceof Class;
			}    	
    });
    addIcon(DependencyImpl.class, "dependency", null, null, "dependency.png", null);
    addIcon(GeneralizationImpl.class, "inherits", null, null, "tree-inheritance.gif", null);
    addIcon(InterfaceImpl.class, "Interface", null, null, "tree-interface.png", null);
    addIcon(ComponentImpl.class, "Component", null, null, "composite.png", null);
    addIcon(NodeImpl.class, null, null, null, "tree-node.gif", null);
    addIcon(CommentImpl.class, null, null, null, "note.png", null);

    // parts are quite specific
    addIcon(PropertyImpl.class, "Part", null, null, "tree-part.png",
      new UMLIconDeterminer()
      {
        public boolean isRelevant(Element element)
        {
          return UMLTypes.extractInstanceOfPart(element) != null;
        }          
      });
    addIcon(DeltaDeletedAttributeImpl.class, null, null, null, "tree-part.png", "delta-deleted.png",
      new UMLIconDeterminer()
      {
        public boolean isRelevant(Element element)
        {
          return UMLTypes.extractInstanceOfPart(((DeltaDeletedAttribute) element).getDeleted()) != null;
        }          
      });
    addIcon(DeltaReplacedAttributeImpl.class, null, null, null, "tree-part.png", "delta-replaced.png",
      new UMLIconDeterminer()
      {
        public boolean isRelevant(Element element)
        {
          return UMLTypes.extractInstanceOfPart(((DeltaReplacedAttribute) element).getReplaced()) != null;
        }          
      });
    
    // add icons for ports, including deleted
    addIcon(PortImpl.class, "Port", null, null, "tree-port.png", null);
    addIcon(DeltaDeletedPortImpl.class, null, null, null, "tree-port.png", "delta-deleted.png", null);
    addIcon(DeltaReplacedPortImpl.class, null, null, null, "tree-port.png", "delta-replaced.png", null);

    // add icons for connectors, including deleted
    addIcon(ConnectorImpl.class, "Connector", null, null, "connector.png",
      new UMLIconDeterminer()
	    {
	      public boolean isRelevant(Element element)
	      {
	        return ((Connector) element).getKind().equals(ConnectorKind.ASSEMBLY_LITERAL);
	      }          
	    });
    addIcon(ConnectorImpl.class, "Connector", null, null, "dependency.png",
        new UMLIconDeterminer()
  	    {
  	      public boolean isRelevant(Element element)
  	      {
  	        return ((Connector) element).getKind().equals(ConnectorKind.DELEGATION_LITERAL);
  	      }          
  	    });
    addIcon(ConnectorImpl.class, "Connector", null, null, "portlink.png",
        new UMLIconDeterminer()
  	    {
  	      public boolean isRelevant(Element element)
  	      {
  	        return ((Connector) element).getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
  	      }          
  	    });
    addIcon(DeltaDeletedConnectorImpl.class, null, null, null, "connector.png", "delta-deleted.png", null);
    addIcon(DeltaReplacedConnectorImpl.class, null, null, null, "connector.png", "delta-replaced.png", null);

    // add icons for operations & attributes
    addIcon(OperationImpl.class, "Operation", VisibilityKind.PUBLIC_LITERAL, null, "tree-public-operation.png", null);
    addIcon(OperationImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-operation.png", null);
    addIcon(OperationImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-operation.png", null);
    addIcon(OperationImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-operation.png", null);
    addIcon(PropertyImpl.class, "Attribute", VisibilityKind.PUBLIC_LITERAL, null, "tree-public-attribute.png", null);
    addIcon(PropertyImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-attribute.png", null);
    addIcon(PropertyImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-attribute.png", null);
    addIcon(PropertyImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-attribute.png", null);

    // add icons for deleted operations & attributes
    addIcon(DeltaDeletedOperationImpl.class, null, VisibilityKind.PUBLIC_LITERAL, null, "tree-public-operation.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedOperationImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-operation.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedOperationImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-operation.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedOperationImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-operation.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedAttributeImpl.class, null, VisibilityKind.PUBLIC_LITERAL, null, "tree-public-attribute.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedAttributeImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-attribute.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedAttributeImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-attribute.png", "delta-deleted.png", null);
    addIcon(DeltaDeletedAttributeImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-attribute.png", "delta-deleted.png", null);

    // add icons for replaced operations & attributes
    addIcon(DeltaReplacedOperationImpl.class, null, VisibilityKind.PUBLIC_LITERAL, null, "tree-public-operation.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedOperationImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-operation.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedOperationImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-operation.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedOperationImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-operation.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedAttributeImpl.class, null, VisibilityKind.PUBLIC_LITERAL, null, "tree-public-attribute.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedAttributeImpl.class, null, VisibilityKind.PROTECTED_LITERAL, null, "tree-protected-attribute.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedAttributeImpl.class, null, VisibilityKind.PACKAGE_LITERAL, null, "tree-package-attribute.png", "delta-replaced.png", null);
    addIcon(DeltaReplacedAttributeImpl.class, null, VisibilityKind.PRIVATE_LITERAL, null, "tree-private-attribute.png", "delta-replaced.png", null);
    
    // handle parameters
    addIcon(ParameterImpl.class, null, null, null, "tree-parameter-in.png", determineDirection(ParameterDirectionKind.IN_LITERAL)); 
    addIcon(ParameterImpl.class, null, null, null, "tree-parameter-out.png", determineDirection(ParameterDirectionKind.OUT_LITERAL)); 
    addIcon(ParameterImpl.class, null, null, null, "tree-parameter-inout.png", determineDirection(ParameterDirectionKind.INOUT_LITERAL)); 
    addIcon(ParameterImpl.class, null, null, null, "tree-parameter-return.png", determineDirection(ParameterDirectionKind.RETURN_LITERAL));    

    // extras for the flattened backbone view
    addIcon(null, "SimpleFactory,", null, ShortCutType.NONE, "factory.png", null);
    // extras for the non-flattened backbone view
    addIcon(null, "AppliedStereotype", null, ShortCutType.NONE, "tree-stereotype.gif", null);
  }
  
  private UMLIconDeterminer determineDirection(final ParameterDirectionKind direction)
  {
    return new UMLIconDeterminer()
    {
      public boolean isRelevant(Element element)
      {
        Parameter param = (Parameter) element;
        return
          param.getDirection().equals(direction);
      }
    };
  }

  private void addIcon(
      java.lang.Class< ? > metaModelClass,
      String name,
      VisibilityKind visibility,
      ShortCutType shortCutType,
      String iconResource,
      UMLIconDeterminer determiner)
  {
    Icon icon = IconLoader.loadIcon(iconResource);
    icons.add(
        new UMLIconEntry(
            new UMLElementIconReference(metaModelClass, name, visibility, shortCutType, determiner),
            shortCutType == ShortCutType.NORMAL ?
            new CompoundIcon(SHORTCUT_ICON, icon, DecoratedIcon.HORIZONTAL, 0) : icon));
  }

  private void addIcon(
      java.lang.Class< ? > metaModelClass,
      String name,
      VisibilityKind visibility,
      ShortCutType shortCutType,
      String iconResource,
      String iconResource2,
      UMLIconDeterminer determiner)
  {
    Icon icon = IconLoader.loadIcon(iconResource);
    Icon icon2 = IconLoader.loadIcon(iconResource2);
    icons.add(
        new UMLIconEntry(
            new UMLElementIconReference(metaModelClass, name, visibility, shortCutType, determiner),
            shortCutType == ShortCutType.NORMAL ?
            new CompoundIcon(SHORTCUT_ICON, icon, DecoratedIcon.HORIZONTAL, 0) :
            new CompoundIcon(icon2, icon, DecoratedIcon.HORIZONTAL, 0)));
  }

  public Icon getUMLIcon(Element element, ShortCutType shortCutType)
  {
    // see if we can find the correct match
    for (UMLIconEntry entry : icons)
    {
      if (entry.getReference().matches(element, shortCutType))
        return entry.getIcon();
    }
    return null;
  }

  public Icon getUMLIcon(String name)
  {
    // see if we can find the correct match
    for (UMLIconEntry entry : icons)
    {
    	String iconName = entry.getReference().getName(); 
      if (iconName != null && iconName.equals(name))
        return entry.getIcon();
    }
    return null;
  }

  public DefaultTreeCellRenderer getTreeCellRenderer()
  {
    return treeCellRenderer;
  }
  
  public DefaultTreeCellRenderer getStringTreeCellRenderer()
  {
  	return stringTreeCellRenderer;
  }

  private class TreeCellRendererImpl extends DefaultTreeCellRenderer
  {
    public TreeCellRendererImpl()
    {
    }

    public java.awt.Component getTreeCellRendererComponent(
    		JTree tree,
    		Object value,
    		boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus)
    {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      if (node.getUserObject() instanceof String)
      	return makeLineRenderer(1000);
      
      UMLTreeUserObject object = (UMLTreeUserObject) node.getUserObject();
      Element element = object.getElement();

      Icon icon = null;
      if (element != null)
        icon = getUMLIcon(element, object.getShortCutType());

      // set the icon type
      boolean defaultIcon = icon == null && element != null;
      if (icon == null)
        icon = DEFAULT_ICON;

      // if we have an error, create a new icon
      icon = overlayError(icon, element);

      setLeafIcon(icon);
      setOpenIcon(icon);
      setClosedIcon(icon);

      // the text should not be set for up shortcuts
      String name = null;
      if (object.getShortCutType() != ShortCutType.UP)
      {
        String elementName = object.getText();
        name = elementName
            + (defaultIcon ? ((elementName.length() != 0 ? " " : "") + "(" + element.eClass().getName() + ")") : "");
      }

      // get the label so we can set the tooltip
      if (object.getShortCutType() == ShortCutType.NONE && object.getFeature() != null)
        setToolTipText(object.getFeature().getName());
      else
        setToolTipText(null);
      
      java.awt.Component c = super.getTreeCellRendererComponent(tree, name, selected, expanded, leaf, row, hasFocus);
      // for some reason, you shouldn't call setEnabled() if the tree is not enabled?!  AMcV 19/10/07
      if (tree.isEnabled())
        c.setEnabled(!object.isDisabled());
      if ((object.getType() & 1) != 0)
      	c.setFont(c.getFont().deriveFont(Font.BOLD));
      else
      	c.setFont(c.getFont().deriveFont(Font.PLAIN));
      if ((object.getType() & 2) != 0)
      	c.setForeground(Color.GRAY);
      else
      if ((object.getType() & 4) != 0)
      	c.setForeground(Color.RED);
      else
      if ((object.getType() & 8) != 0)
      	c.setForeground(Color.BLUE);
      else
      	c.setForeground(Color.BLACK);
      
      return c;
    }

    
    private JComponent line = new JComponent()
  	{
			@Override
			public void paint(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.LIGHT_GRAY);
		    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));
				g2d.drawLine(0, 2, getWidth(), 2);
			}
  	};

    private Component makeLineRenderer(int width)
		{
    	line.setPreferredSize(new Dimension(width, 6));    	
    	return line;
		}

		private Icon overlayError(Icon icon, Element element)
    {
      if (element == null)
        return icon;
      if (errors != null)
      {
        IDeltaEngine engine = GlobalDeltaEngine.engine;
        DEObject object = engine.locateObject(element);
        
        // can't do this with something that doesn't show up in the delta engine
        if (object != null)
        {
	        if (errors.isEnabled() && errors.matchesDirectly(object.getUuid()))
	          return new DecoratedIcon(icon, ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
	        if (errors.isEnabled() && errors.isInHierarchyOfErrors(object.getUuid()))
	          return new DecoratedIcon(icon, INDIRECT_ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
        }
      }
      return icon;
    }
  }
  
  /**
   * a simpler renderer that only takes string names into account when searching for icons
   * @author andrew
   *
   */
  private class StringTreeCellRendererImpl extends DefaultTreeCellRenderer
  {
    public StringTreeCellRendererImpl()
    {
    }

    public java.awt.Component getTreeCellRendererComponent(
    		JTree tree,
    		Object value,
    		boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus)
    {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      String name = (String) node.getUserObject();

      String firstPart = null;
      StringTokenizer match = new StringTokenizer(name == null ? "" : name);
      if (match.hasMoreTokens())
      	firstPart = match.nextToken();
      Icon icon = firstPart != null ? getUMLIcon(firstPart) : null;
      boolean defaultIcon = icon == null;
      if (!defaultIcon)
      	name = name.substring(firstPart.length()).trim();
      else
      	icon = DEFAULT_ICON;
      
      setLeafIcon(icon);
      setOpenIcon(icon);
      setClosedIcon(icon);

      Component c = super.getTreeCellRendererComponent(tree, name, selected, expanded, leaf, row, hasFocus);
      c.setForeground(defaultIcon ? Color.GRAY : Color.BLACK);
      return c;
    }

    
    private JComponent line = new JComponent()
  	{
			@Override
			public void paint(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.LIGHT_GRAY);
		    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));
				g2d.drawLine(0, 2, getWidth(), 2);
			}
  	};

    private Component makeLineRenderer(int width)
		{
    	line.setPreferredSize(new Dimension(width, 6));    	
    	return line;
		}

		private Icon overlayError(Icon icon, Element element)
    {
      if (element == null)
        return icon;
      if (errors != null)
      {
        IDeltaEngine engine = GlobalDeltaEngine.engine;
        DEObject object = engine.locateObject(element);
        
        // can't do this with something that doesn't show up in the delta engine
        if (object != null)
        {
	        if (errors.isEnabled() && errors.matchesDirectly(object.getUuid()))
	          return new DecoratedIcon(icon, ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
	        if (errors.isEnabled() && errors.isInHierarchyOfErrors(object.getUuid()))
	          return new DecoratedIcon(icon, INDIRECT_ERROR_ICON, DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
        }
      }
      return icon;
    }
  }
}
