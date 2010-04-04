package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.swing.enhanced.*;

public class UMLElementViewer
{
  private Element element;
  private List<UMLAttributeViewer> viewers = new ArrayList<UMLAttributeViewer>();
  private Element subsidiary;
  private boolean enabled = true;
  private JPanel insetPanel;
  
  public UMLElementViewer(Element element, Element subsidiary, JPanel insetPanel)
  {
    this.element = element;
    this.subsidiary = subsidiary;
    this.insetPanel = insetPanel;
  }

  public void installAttributeEditors(GridBagConstraints gbcLeft, GridBagConstraints gbcRight, UMLAttributeModificationListener listener, boolean readonly, boolean attributes, boolean documentationOnly)
  {
    installAttributeEditors(element, gbcLeft, gbcRight, listener, readonly, attributes, documentationOnly);
    if (subsidiary != null)
      installAttributeEditors(subsidiary, gbcLeft, gbcRight, listener, true, attributes, documentationOnly);
  }

  private void installAttributeEditors(Element element, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, UMLAttributeModificationListener listener, boolean readonly, boolean attributes, boolean documentationOnly)
  {
    // look at each item in the inheritance hierarchy, looking for attributes
    for (EClass cls : new UMLHierarchyFinder(element).findSortedHierarchy())
    {
      // see if we can find any attributes here
      List<EAttribute> attrs = new ArrayList<EAttribute>();
      if (attributes)
        for (TreeIterator iter = cls.eAllContents(); iter.hasNext();)
        {
          Object obj = iter.next();
          if (obj instanceof EAttribute)
          {
            EAttribute attr = (EAttribute) obj;
            if (!rejectAttribute(attr, documentationOnly))
              attrs.add((EAttribute) obj);
          }
        }
      
      List<EReference> refs = new ArrayList<EReference>();
      if (!attributes)
        for (TreeIterator iter = cls.eAllContents(); iter.hasNext();)
        {
          Object obj = iter.next();
          if (obj instanceof EReference)
          {
            EReference ref = (EReference) obj;
            if (!rejectReference(ref))
              refs.add(ref);
          }
        }
      
      // only add a category if it has a valid attribute
      if (!attrs.isEmpty() || !refs.isEmpty())
      {
        String name = cls.getName();
        if (name.equals("J_DiagramHolder"))
          name = "Diagram information";

        if (!documentationOnly)
        {
          JLabel cat = new JLabel(name);
          cat.setFont(cat.getFont().deriveFont(Font.BOLD));
          gbcLeft.gridwidth = 2;
          insetPanel.add(cat, gbcLeft);
          gbcLeft.gridwidth = 1;
    
          // increment
          gbcLeft.gridy++;
          gbcRight.gridy++;
        }
          
        for (EAttribute attr : attrs)
        {
          UMLAttributeViewer viewer = makeAttributeViewer(element, attr, listener);
          viewers.add(viewer);
          viewer.installAttributeEditor("from " + cls.getName(), insetPanel, gbcLeft, gbcRight, !documentationOnly);
          if (readonly)
            viewer.getEditor().setEnabled(false);
        }
        for (EReference ref : refs)
        {
          UMLAttributeViewer viewer = makeAttributeViewer(element, ref, listener);
          viewers.add(viewer);
          viewer.installAttributeEditor("from " + cls.getName(), insetPanel, gbcLeft, gbcRight, !documentationOnly);
          if (readonly)
            viewer.getEditor().setEnabled(false);
        }
      }
    }
  }

  private UMLAttributeViewer makeAttributeViewer(Element element, EStructuralFeature feature, UMLAttributeModificationListener listener)
  {
    EClassifier cls = feature.getEType();
    
    // handle simple data types like string and boolean
    if (cls instanceof EDataType)
    {
      String name = cls.getName();
    	String fName = feature.getName();
      if (name.equals("String"))
      {
      	boolean readOnly = false;
      	if (fName.equals("uuid") && !GlobalPreferences.preferences.getRawPreference(RepositoryBrowserGem.EDITABLE_UUIDS).asBoolean())
      		readOnly = true;
      	return new UMLStringAttributeViewer(element, feature, listener, readOnly);
      }
      if (name.equals("Boolean"))
        return new UMLBooleanAttributeViewer(element, feature, listener, fName.equals("resemblance") || fName.equals("replacement"));
    }
    
    // handle enumerations
    if (cls instanceof EEnum)
      return new UMLEnumerationAttributeViewer(element, feature, listener);

    return new UMLUnknownAttributeViewer(element, feature);
  }

  private boolean rejectReference(EReference ref)
  {
    if (ref.isContainment() && ref.getUpperBound() != 1)
      return true;
    if (ref.isDerived())
      return true;
    if (ref.isUnsettable())
      return true;
    if (ref.getName().contains("_"))
      return true;
    if (!ref.isChangeable())
      return true;
    return false;
  }

  private boolean rejectAttribute(EAttribute attr, boolean documentationOnly)
  {
    String name = attr.getName();
    
    if (name.contains("_"))
      return true;
    if (attr.isDerived())
      return true;
    if (!attr.isChangeable())
      return true;
//    if (name.equals("uuid") || name.startsWith("binaryData"))
//    	return true;
    
    // handle documentation mode
    boolean docs = name.equals("documentation");
    if (documentationOnly && !docs)
      return true;
    if (!documentationOnly && docs)
      return true;    

    return false;
  }

  public void investigateChange()
  {
    for (UMLAttributeViewer viewer : viewers)
      viewer.investigateChange();
  }

  public void applyAction()
  {
    for (UMLAttributeViewer viewer : viewers)
      viewer.applyAction();
  }

  public boolean hasAnythingBeenModified()
  {
    for (UMLAttributeViewer viewer : viewers)
    {
      if (viewer.isModified())
        return true;
    }
    return false;
  }

  public void revert()
  {
    for (UMLAttributeViewer viewer : viewers)
      viewer.revert();
  }

  public void setEnabled(boolean enabled)
  {
    if (this.enabled == enabled)
      return;
    
    this.enabled = enabled;
    Utilities.setEnabledRecursively(insetPanel, enabled);
  }
}
