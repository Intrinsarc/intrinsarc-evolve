package com.hopstepjump.jumble.repositorybrowser;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

public class UMLTreeMenuCreator
{
	public UMLTreeMenuCreator()
	{
	}
	
  public JPopupMenu createMenu(final ToolCoordinatorFacet coordinator, DefaultMutableTreeNode node)
  {
  	final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    JPopupMenu menu = new JPopupMenu();
    UMLTreeUserObject user = (UMLTreeUserObject) node.getUserObject();
    final Element element = user.getElement();
    
    // (1) add the creation options
    // look for all createOwned() things in the class definition
    JMenu createItem = new JMenu("Create");
    
    // add the menu elements for creation
    EClass ecls = element.eClass();
    List<EReference> allRefs = new ArrayList<EReference>();
    for (Object obj : ecls.getEAllContainments())
    {
      EReference ref = (EReference) obj;
      if (!ref.isDerived())
        allRefs.add((EReference) obj);
    }
    Collections.sort(allRefs, new Comparator<EReference>()
        {
          public int compare(EReference r1, EReference r2)
          {
            return r1.getName().compareTo(r2.getName());
          }
        });
    for (EReference ref : allRefs)
      makeCreateCommand(createItem, repository, coordinator, element, ref);
    MenuAccordion.makeMultiColumn(createItem, null, true);
    
    menu.add(createItem);
    menu.addSeparator();
    
    // (2) add the deletion option
    // add a delete action if this is not the root model
    AbstractAction delete =
      new AbstractAction("Delete")
      {
        public void actionPerformed(ActionEvent e)
        {
          String className = element.eClass().getName().toLowerCase();
          coordinator.executeCommandAndUpdateViews(new AbstractCommand("deleted " + className, "undeleted " + className)
              {
                public void execute(boolean isTop)
                {
                  repository.incrementPersistentDelete(element);
                }

                public void unExecute()
                {
                  repository.decrementPersistentDelete(element);
                }
              });
        }
      };          

    // grey out the deletion command if this is the top level
    if (element == repository.getTopLevelModel())
      delete.setEnabled(false);
    menu.add(delete);

    return menu;
  }

  private void makeCreateCommand(
      JMenuItem addTo,
      SubjectRepositoryFacet repository,
      ToolCoordinatorFacet coordinator,
      Element element,
      EReference reference)
  {
    // reject if not suitable
    String name = reference.getName();
    if (name.contains("_"))
      return;
    
    JMenu createMenu = new JMenu(reference.getName());
    addTo.add(createMenu);
    
    // find the classes we can legitimately add to this
    List<EClass> subclasses = new UMLSubclassFinder(reference.getEReferenceType()).findSubClasses();
    
    for (EClass subclass : subclasses)
      makeCreateCommand(createMenu, repository, coordinator, element, reference, subclass);
    MenuAccordion.makeMultiColumn(createMenu, null, true);
  }
  

  
  private void makeCreateCommand(
      JMenu addTo,
      final SubjectRepositoryFacet repository,
      final ToolCoordinatorFacet coordinator,
      final Element element,
      final EReference reference,
      final EClass subclass)
  {
    final String refName = reference.getName();
    final String clsName = element.eClass().getName();
    final String subName = subclass.getName();
    JMenuItem createItem = new JMenuItem(
        new AbstractAction(subName)
        {
          public void actionPerformed(ActionEvent e)
          {
            Command cmd;
            if (reference.getUpperBound() == 1)
            {
              cmd = new AbstractCommand(
                  "Set " + refName + DEObject.SEPARATOR + clsName + " to new " + subName,
                  "Deleted " + subName + " from " + refName + DEObject.SEPARATOR + clsName)
              {
                private Element created;
                
                public void execute(boolean isTop)
                {
                  if (created == null)
                    created = (Element) UML2Factory.eINSTANCE.create(subclass);
                  else
                    repository.decrementPersistentDelete(created);
                  element.eSet(reference, created);
                }

                public void unExecute()
                {
                  element.eSet(reference, null);
                  repository.incrementPersistentDelete(created);
                }
              };
            }
            else
            {
              cmd = new AbstractCommand(
                  "Added new " + subName + " to " + refName + DEObject.SEPARATOR + clsName,
                  "Deleted " + subName + " from " + refName + DEObject.SEPARATOR + clsName)
              {
                private Element created;
                
                public void execute(boolean isTop)
                {
                  if (created == null)
                    created = (Element) UML2Factory.eINSTANCE.create(subclass);
                  else
                    repository.decrementPersistentDelete(created);
                  EList list = (EList) element.eGet(reference);
                  list.add(created);
                }

                public void unExecute()
                {
                  List list = (List) element.eGet(reference);
                  list.remove(created);
                  repository.incrementPersistentDelete(created);
                }
              };
            }
            
            // execute the cmd
            coordinator.executeCommandAndUpdateViews(cmd);
          }
        });
    addTo.add(createItem);
  }

  private String firstAsLower(String actualName)
  {
    return Character.toLowerCase(actualName.charAt(0)) + actualName.substring(1);
  }
}
