package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.enhanced.*;

/**
 * allow the set of stereotypes etc to be visually edited etc.
 * @author amcveigh
 *
 */

class StereotypeViewerType implements Comparable<StereotypeViewerType>
{
  private DeltaPair pair;
  private Type type;
  private String typeName = "(unknown)";
  
  public StereotypeViewerType(DeltaPair pair)
  {
    this.pair = pair;
    this.type = getConstituentProperty().getType();
    if (type != null)
      typeName = type.getName();
  }

  public Property getConstituentProperty()
  {
    return (Property) pair.getConstituent().getRepositoryObject();
  }

  public Property getOriginalProperty()
  {
    return (Property) pair.getOriginal().getRepositoryObject();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof StereotypeViewerType))
      return false;
    StereotypeViewerType other = (StereotypeViewerType) obj;
    return getConstituentProperty() == other.getConstituentProperty() && type == other.type && typeName.equals(other.typeName);
  }

  @Override
  public int hashCode()
  {
    return getConstituentProperty().hashCode() ^ (type == null ? 0 : type.hashCode()) ^ typeName.hashCode();
  }

  public int compareTo(StereotypeViewerType other)
  {
    return getConstituentProperty().getName().compareTo(other.getConstituentProperty().getName());
  }

  public Type getType()
  {
    return type;
  }
  
  public String getTypeName()
  {
    return typeName;
  }
  
  public String getPropertyName()
  {
    return getConstituentProperty().getName() == null ? "(anon)" : getConstituentProperty().getName();
  }
}


public class StereotypeEditor
{
	private Element element;
	private ToolCoordinatorFacet coordinator;
	private JPanel panel;
  private JPanel insetPanel;
  private Map<StereotypeViewerType, UMLAttributeViewer> viewers =
    new HashMap<StereotypeViewerType, UMLAttributeViewer>();
  private UMLAttributeModificationListener modificationListener;
  private int hash;
  private boolean enabled = true;
	private JButton okButton;

	public StereotypeEditor(Element element, ToolCoordinatorFacet coordinator, UMLAttributeModificationListener modificationListener, JButton okButton)
	{
		this.element = element;
		this.coordinator = coordinator;
		this.panel = new JPanel(new BorderLayout());
		this.modificationListener = modificationListener;
		this.okButton = okButton;
	}
	
	public JComponent makeVisualComponent()
	{
		panel.removeAll();
		
		// save what we drew with to look for changes later
		hash = makeStereotypeHash();

		// take a look at the current set of applied stereotypes so we can investigate changes later
		JPanel current = new JPanel(new BorderLayout());
		JPanel name = new JPanel();
		JLabel existing = new JLabel(getNamesOfAppliedStereotypes());
		name.add(existing);
		current.add(name, BorderLayout.NORTH);

    // now add current to the top
		panel.add(current, BorderLayout.NORTH);

    insetPanel = new JPanel(new GridBagLayout());
    JPanel pushUpPanel = new JPanel(new BorderLayout());
    pushUpPanel.add(insetPanel, BorderLayout.NORTH);
    JScrollPane scroller = new JScrollPane(pushUpPanel);
    scroller.setBorder(null);
    panel.add(scroller, BorderLayout.CENTER);
    updateViewers(true);
		panel.revalidate();
		Utilities.setEnabledRecursively(insetPanel, enabled);
		
		return panel;
	}

	private int makeStereotypeHash()
	{
		return StereotypeUtilities.calculateStereotypeHash(null, element);
	}
	
	private void updateViewers(boolean force)
  { 
    // if we have no change, don't touch the gui
    if (!force && hash == makeStereotypeHash())
      return;
    
    // otherwise, we need to regenerate, possibly reusing existing viewers
    insetPanel.removeAll();

    // assemble the attributes, then ask them for their visual components
    GridBagConstraints gbcLeft = new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 7, 1, 10), 0, 0);
    GridBagConstraints gbcRight = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);

    Map<StereotypeViewerType, UMLAttributeViewer> newViewers = handleNormalStereotypes(gbcLeft, gbcRight);
    
    insetPanel.revalidate();
    viewers = newViewers;
  }

	private Map<StereotypeViewerType, UMLAttributeViewer> handleNormalStereotypes(
			GridBagConstraints gbcLeft,
			GridBagConstraints gbcRight)
	{
	  boolean readOnly =
	    element instanceof Classifier &&
	    element.undeleted_getAppliedBasicStereotypes().isEmpty();
	  
	  // see if we have all the elements we require
    Map<String, DeltaPair> applicableProperties = StereotypeUtilities.findAllStereotypeProperties(element);
    Set<StereotypeViewerType> properties = new HashSet<StereotypeViewerType>();
    for (DeltaPair pair : applicableProperties.values())
      properties.add(new StereotypeViewerType(pair));

    List<StereotypeViewerType> sorted = new ArrayList<StereotypeViewerType>(properties);
    Map<StereotypeViewerType, UMLAttributeViewer> newViewers = new HashMap<StereotypeViewerType, UMLAttributeViewer>();

  	Collections.sort(sorted);
    for (StereotypeViewerType type : sorted)
    {
      // do we have an existing viewer?
      UMLAttributeViewer viewer = viewers.get(type);
      if (viewer == null)
        viewer = makeViewer(type);
      newViewers.put(type, viewer);
      
      viewer.installAttributeEditor(type.getConstituentProperty().getDocumentation(), insetPanel, gbcLeft, gbcRight, true, okButton);
      viewer.getEditor().setEnabled(!readOnly);
    }
    
    // handle the viewers for any left over properties that aren't associated with a stereotype
    Set<AppliedBasicStereotypeValue> leftOver = new HashSet<AppliedBasicStereotypeValue>();
    for (Object obj : element.undeleted_getAppliedBasicStereotypeValues())
    {
      AppliedBasicStereotypeValue value = (AppliedBasicStereotypeValue) obj;
      if (value.getProperty() == null || !applicableProperties.keySet().contains(value.getProperty().getUuid()))
          leftOver.add(value);
    }
    allowDeletionOfLeftOverValues(gbcLeft, gbcRight, leftOver);

    return newViewers;
	}

	private void allowDeletionOfLeftOverValues(
	    GridBagConstraints gbcLeft,
			GridBagConstraints gbcRight,
			Set<AppliedBasicStereotypeValue> leftOver)
	{
		if (!leftOver.isEmpty())
		{
		  insetPanel.add(new JLabel("<html><b>Old properties</b></html>"), gbcLeft);
		  gbcRight.gridy++;
		  gbcLeft.gridy++;
		  
		  for (final AppliedBasicStereotypeValue applied : leftOver)
		  {
		    // add this to the grid
		    insetPanel.add(
		    		new JLabel(
		    				(applied.getProperty() == null ? "<deleted>" : applied.getProperty().getName())
		    				+ " = " + applied.getValue().stringValue()),
		    		gbcLeft);
		    JButton delete = new JButton(new AbstractAction("Delete")
		        {
		          public void actionPerformed(ActionEvent e)
		          {
		            StereotypeUtilities.formDeleteAppliedRawStereotypeValueTransaction(
		            		coordinator,
                    element,
                    applied);
		          }
		        });
		    insetPanel.add(delete, gbcRight);
		    gbcRight.gridy++;
		    gbcLeft.gridy++;
		  }
		}
	}

	private UMLAttributeViewer makeViewer(StereotypeViewerType type)
  {
    String typeName = type.getTypeName();
    if (typeName.equals("String"))
      return new StereotypeStringAttributeViewer(element, type.getOriginalProperty(), type.getConstituentProperty(), modificationListener);
    if (typeName.equals("boolean"))
      return new StereotypeBooleanAttributeViewer(element, type.getOriginalProperty(), type.getConstituentProperty(), modificationListener);
    else
      return new StereotypeUnknownAttributeViewer(element, type.getOriginalProperty());
    
  }

  public void investigateChange()
	{
		if (hash != makeStereotypeHash())
			makeVisualComponent();
    else
    {
    	// should't really force this always, as it's slow but the hash above doesn't
    	// pick up when we replace the inherited "component" stereotype with another "component" stereotype
      updateViewers(true);
    }
    
    // ask the viewers to investigate the change
    for (UMLAttributeViewer viewer : viewers.values())
      viewer.investigateChange();
	}
	
	private String getNamesOfAppliedStereotypes()
	{
		String names = "<html><b>";
		List<DEComponent> stereos = StereotypeUtilities.findAllStereotypes(element);
		int size = stereos.size();
		DEStratum home = getHome();

		if (element instanceof Classifier && stereos.size() > 1)
		  names = "<html><body text='red'>" + names;
		for (DEComponent stereo : stereos)
		{
			size--;
			String name = stereo.getName(home);
			names += "\u00AB" + (name.length() == 0 ? "?" : name) + "\u00BB";
			if (size > 0)
				names += ", ";
		}
		return names;
	}

  private DEStratum getHome()
  {
    return GlobalDeltaEngine.engine.locateObject(
        element instanceof Package ?
            element :
            GlobalSubjectRepository.repository.findOwningPackage(element)).asStratum();
  }

  public void applyAction()
  {
    for (UMLAttributeViewer viewer : viewers.values())
      viewer.applyAction();
  }

  public boolean hasAnythingBeenModified()
  {
    for (UMLAttributeViewer viewer : viewers.values())
    {
      if (viewer.isModified())
        return true;
    }
    return false;
  }

  public void revert()
  {
    for (UMLAttributeViewer viewer : viewers.values())
      viewer.revert();
  }

  public void setEnabled(final boolean enabled)
  {
    if (this.enabled == enabled)
      return;
    
    this.enabled = enabled;
		Utilities.setEnabledRecursively(insetPanel, enabled);
  }
}
