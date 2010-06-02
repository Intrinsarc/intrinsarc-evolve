package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.util.*;
import java.util.Map.*;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;


public class UMLDetailMediator
{
  private static final Icon ERROR_ICON = IconLoader.loadIcon("error.png");
  public static ImageIcon OK_ICON = IconLoader.loadIcon("tick.png");
  public static ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");
  public static ImageIcon APPLY_ICON = IconLoader.loadIcon("bullet_go.png");
  public static ImageIcon REVERT_ICON = IconLoader.loadIcon("arrow_undo.png");
  private Element element;
  /** the viewer for the detail */
  private UMLElementViewer attributeViewer;
  private UMLElementViewer referenceViewer;
  private UMLElementViewer documentationViewer;
  private JLabel nameAndTypeLabel;
  private ToolCoordinatorFacet coordinator;
  private boolean readonly;
  private RepositoryBrowserListenerFacet browserListener;
  private StereotypeEditor stereotypeEditor;
  private JPanel buttonPanel;
  private JButton applyButton;
  private JButton revertButton;
  private JTree outlineTree;
  private JTree inlineTree;
  private JTabbedPane tabs;
  private ErrorRegister errors;
  private JPanel innerErrors = new JPanel();
  
  private UMLAttributeModificationListener listener = new UMLAttributeModificationListener()
  {
    public void attributeModified()
    {
      // ask the buttons to recheck themselves
      checkButtons();
      
      // if this element is readonly, then disable
      refreshEnabled();
    }

  };

  public UMLDetailMediator(
      JTree outlineTree,
      JTree inlineTree,
      ToolCoordinatorFacet coordinator,
      Element element,
      boolean readonly,
      ErrorRegister errors)
  {
    this.coordinator = coordinator;
    this.element = element;
    this.readonly = readonly;
    this.outlineTree = outlineTree;
    this.inlineTree = inlineTree;
    this.errors = errors;
  }
  
  private void refreshEnabled()
  {
    // is the element in read-only space?
    boolean readOnly = GlobalSubjectRepository.repository.isReadOnly(element);
    
    attributeViewer.setEnabled(!readOnly);
    referenceViewer.setEnabled(!readOnly);
    documentationViewer.setEnabled(!readOnly);
    stereotypeEditor.setEnabled(!readOnly);
  } 
  
  public static void setEnabledRecursively(Component root, boolean enabled)
  {
    if (root.isEnabled() == enabled)
      return;
    root.setEnabled(enabled);
    
    if (root instanceof Container)
      for (Component c : ((Container) root).getComponents())
      {
        if (c instanceof Container)
          setEnabledRecursively(c, enabled);
        else
          c.setEnabled(enabled);
      }
  }

  public void connectRepositoryBrowserListenerFacet(RepositoryBrowserListenerFacet browserListener)
  {
    this.browserListener = browserListener;
  }
  
  public JComponent makeVisualComponent(int selectedTabIndex)
  {
    tabs = new JTabbedPane();
    tabs.addTab("Attributes", makeDetailsVisualComponent(true, false));
    tabs.addTab("References", makeDetailsVisualComponent(false, false));
    stereotypeEditor = new StereotypeEditor(element, coordinator, listener);
    tabs.addTab("Stereotypes", stereotypeEditor.makeVisualComponent());
    tabs.addTab("Model", makeMetamodelVisualComponent());
    tabs.addTab("Doc", makeDetailsVisualComponent(true, true));
    tabs.addTab("Errors", makeErrorsVisualComponent());
    
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    JPanel centring = new JPanel();
    nameAndTypeLabel = new JLabel(getName(element) + DEObject.SEPARATOR + getType(element));
    nameAndTypeLabel.setFont(nameAndTypeLabel.getFont().deriveFont(Font.BOLD));
    centring.add(nameAndTypeLabel, BorderLayout.NORTH);
    panel.add(centring, BorderLayout.NORTH);
    panel.add(tabs);
    
    buttonPanel = new JPanel();
    JButton okButton = makeButton("OK", OK_ICON); 
    buttonPanel.add(okButton);
    okButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            executeViewerCommands();
            browserListener.requestClose();
          }
        });

    JButton cancelButton = makeButton("Cancel", CANCEL_ICON);
    cancelButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            browserListener.requestClose();
          }
        });

    buttonPanel.add(cancelButton);
    buttonPanel.add(new JLabel("   "));
    applyButton = makeButton("Apply", APPLY_ICON);
    buttonPanel.add(applyButton);
    applyButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      { executeViewerCommands(); }
    });

    revertButton = makeButton("Revert", REVERT_ICON);
    revertButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        attributeViewer.revert();
        referenceViewer.revert();
        documentationViewer.revert();
        stereotypeEditor.revert();
        checkButtons();
      }
    });
    buttonPanel.add(revertButton);
    
    panel.add(buttonPanel, BorderLayout.SOUTH);
    checkButtons();
    
    tabs.setSelectedIndex(selectedTabIndex);
    refreshEnabled();

    return panel;
  }
  
  
  private JComponent makeMetamodelVisualComponent()
  {
    String hierarchy = "<html><b>Metaclass hierarchy for " + element.eClass().getName() + "</b><hr>";
    UMLHierarchyFinder finder = new UMLHierarchyFinder(element);
    boolean start = true;
    for (EClass cls : finder.findSortedHierarchy())
    {
      hierarchy += (start ? "" : "<br>") + "&nbsp;&nbsp;" + cls.getName();
      start = false;
    }
    
   JLabel label = new JLabel(hierarchy);
   JPanel inner = new JPanel();
   inner.add(label, BorderLayout.NORTH);
   JScrollPane panel = new JScrollPane(inner);
   panel.setBorder(null);
   return panel;
  }

  private JComponent makeErrorsVisualComponent()
  {
    innerErrors.setLayout(new BoxLayout(innerErrors, BoxLayout.Y_AXIS));
    
    JPanel inside = new JPanel(new BorderLayout());
    inside.setBorder(new EmptyBorder(5, 5, 5, 5));
    inside.add(innerErrors, BorderLayout.WEST);
    JScrollPane panel = new JScrollPane(inside);
    panel.setBorder(null);
    refreshErrors(
    		errors,
    		GlobalDeltaEngine.engine.locateObject(element),
    		innerErrors,
    		errors.getErrors(element.getUuid()),
    		false,
    		null);
    return panel;
  }
  
  public static void refreshErrors(
  		ErrorRegister errors,
  		DEObject element,
  		JPanel innerErrors,
  		Map<ErrorLocation,
  		Set<ErrorDescription>> allErrors,
  		boolean reverse,
  		String extraMessage)
  {
  	// be a bit defensive, sometimes happens
  	if (element == null || allErrors == null)
  		return;
  	    
    innerErrors.removeAll();
    
    // sort into two sets; things that directly affect this, and things that are "rolled up" because the element
    // doesn't physically exist in this stratum
    Set<ErrorDescription> direct = filterDirectErrors(allErrors, element);
    String tab = "        ";
    
    if (reverse)
    {
    	recordIndirectErrors(element, innerErrors, allErrors, tab);
    	recordDirectErrors(innerErrors, direct, tab);
    }
    else
    {
    	recordDirectErrors(innerErrors, direct, tab);
    	recordIndirectErrors(element, innerErrors, allErrors, tab);
    }
    
    // add a possible extra message
    if (extraMessage != null)
    {
      JLabel title = new JLabel(tab + "    " + extraMessage);
      innerErrors.add(title);
    }
  }

	private static void recordIndirectErrors(DEObject element,
			JPanel innerErrors, Map<ErrorLocation, Set<ErrorDescription>> allErrors,
			String tab)
	{
		// now add it to the gui
    boolean added = false;
    for (Entry<ErrorLocation, Set<ErrorDescription>> entry : allErrors.entrySet())
    {
    	ErrorLocation location = entry.getKey();
      if (!element.getUuid().equals(entry.getKey().getFirstUuid()))
      {
      	Set<ErrorDescription> descriptions = entry.getValue();
      	if (!descriptions.isEmpty())
      	{
	        if (!added)
	        {
	          JLabel title = new JLabel("Indirect errors", ERROR_ICON, JLabel.HORIZONTAL);
	          title.setFont(title.getFont().deriveFont(Font.BOLD));
	          innerErrors.add(title);
	          added = true;
	        }
	        
	        JLabel locationLabel = new JLabel(tab + "> Location " + readableLocation(location));
	        locationLabel.setForeground(Color.BLUE);
	        innerErrors.add(locationLabel);
	        for (ErrorDescription desc : entry.getValue())
        		innerErrors.add(new JLabel(tab + "    " + desc.toString()));
      	}
      }
    }
	}

	private static void recordDirectErrors(
			JPanel innerErrors,
			Set<ErrorDescription> direct,
			String tab)
	{
		if (!direct.isEmpty())
    {
      // now add it to the gui
      JLabel title = new JLabel("Direct errors", ERROR_ICON, JLabel.HORIZONTAL);
      title.setFont(title.getFont().deriveFont(Font.BOLD));
      innerErrors.add(title);
      for (ErrorDescription desc : direct)
    		innerErrors.add(new JLabel(tab + desc.toString()));
    }
	}

  private static String readableLocation(ErrorLocation loc)
  {
    return loc.toString();
  } 
    
  private static Set<ErrorDescription> filterDirectErrors(
      Map<ErrorLocation, Set<ErrorDescription>> all, DEObject element)
  {
    Set<ErrorDescription> descriptions = new HashSet<ErrorDescription>();
    for (Entry<ErrorLocation, Set<ErrorDescription>> entry : all.entrySet())
    {
      if (entry.getKey().matchesFirst(element))
        descriptions.addAll(entry.getValue());
    }
    return descriptions;
  }

  private JComponent makeDetailsVisualComponent(boolean attributes, boolean documentationOnly)
  {
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel propertyPanel = new JPanel(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane(propertyPanel);
    GridBagLayout gridBag = new GridBagLayout();
    JPanel insetPanel = new JPanel(gridBag);

    // assemble the attributes, then ask them for their visual components
    GridBagConstraints gbcLeft = new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 7, 1, 10), 0, 0);
    GridBagConstraints gbcRight = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);

    final UMLElementViewer viewer ;
    if (element instanceof Package)
    {
      Package pkg = (Package) element;
      viewer = new UMLElementViewer(pkg, pkg.getJ_diagramHolder(), insetPanel);
    }
    else
      viewer = new UMLElementViewer(element, null, insetPanel);
    viewer.installAttributeEditors(gbcLeft, gbcRight, listener, readonly, attributes, documentationOnly);

    propertyPanel.add(insetPanel, BorderLayout.NORTH);
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    scrollPane.setBorder(null);    
    
    if(documentationOnly)
      documentationViewer = viewer;
    else
    if (attributes)
      attributeViewer = viewer;
    else
      referenceViewer = viewer;
    
    return mainPanel;
  }
  
  private void checkButtons()
  {
    // has anything been modified?
    boolean attrsModified = attributeViewer != null ? attributeViewer.hasAnythingBeenModified() : false;
    boolean refsModified = referenceViewer != null ? referenceViewer.hasAnythingBeenModified() : false;
    boolean docsModified = documentationViewer != null ? documentationViewer.hasAnythingBeenModified() : false;
    boolean stereoModified = stereotypeEditor != null ? stereotypeEditor.hasAnythingBeenModified() : false;
    boolean modified = attrsModified || refsModified || docsModified || stereoModified;
    
    // possibly disable the two trees
    outlineTree.setEnabled(!modified);
    inlineTree.setEnabled(!modified);
    revertButton.setEnabled(modified);
    applyButton.setEnabled(modified);
  }

  private JButton makeButton(String text, ImageIcon icon)
  {
    JButton button = new JButton(text, icon);
    button.setDisabledIcon(icon);
    button.setHorizontalAlignment(SwingConstants.LEFT);
    return button;
  }

  private String getName(Element element)
  {
    if (element instanceof NamedElement)
      return ((NamedElement) element).getName();
    return "";
  }
  
  private String getType(Element element)
  {
    return element.eClass().getName();
  }

  public void investigateChange()
  {
    nameAndTypeLabel.setText(getName(element) + DEObject.SEPARATOR + getType(element));
    attributeViewer.investigateChange();
    referenceViewer.investigateChange();
    documentationViewer.investigateChange();
    stereotypeEditor.investigateChange();
    refreshErrors(
    		errors,
    		GlobalDeltaEngine.engine.locateObject(element),
    		innerErrors,
    		errors.getErrors(element.getUuid()),
    		false,
    		null);
    checkButtons();
  }

  private void executeViewerCommands()
  {
    // form a command by asking each element in turn
    String name = element.eClass().getName().toLowerCase();
    coordinator.startTransaction("updated details of " + name, "restored details of " + name);
    attributeViewer.applyAction();
    referenceViewer.applyAction();
    documentationViewer.applyAction(); 
    stereotypeEditor.applyAction();
    coordinator.commitTransaction();
  }

  public int getSelectedTabIndex()
  {
    return tabs.getSelectedIndex();
  }
}
