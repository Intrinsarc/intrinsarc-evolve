package com.hopstepjump.jumble.repositorybrowser;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

public class StereotypeChanger
{
	public StereotypeChanger()
	{
	}
	
	public void createMenu(JPopupMenu menu, final ToolCoordinatorFacet coordinator, final Element element, boolean readonly)
	{
    Utilities.addSeparator(menu);

    boolean isEmpty = StereotypeUtilities.findAllStereotypes(element).isEmpty();
    boolean shallowEmpty = element.undeleted_getAppliedBasicStereotypes().isEmpty();
    boolean isClassifier = element instanceof Classifier;
    DEStratum home = getHome(element);

    // populate the add menu
		JMenu addMenu = new JMenu("Add \u00AB\u00BB");
    menu.add(addMenu);
		if (isClassifier && !isEmpty)
		  addMenu.setEnabled(false);
		else
		{
  		boolean addEnabled = false;
  		for (final Stereotype toAdd : getApplicableButUnappliedStereotypes(element))
  		{
  			JMenuItem add = new JMenuItem(new AbstractAction(getStereoName(home, toAdd))
				{
					public void actionPerformed(ActionEvent e)
					{
            StereotypeUtilities.formAddRawStereotypeTransaction(coordinator, element, toAdd);
					}
				});
  			addMenu.add(add);
  			addEnabled = true;
  		}
  		MenuAccordion.makeMultiColumn(addMenu, null, true);
  		addMenu.setEnabled(addEnabled);
		}
		if (readonly)
			addMenu.setEnabled(false);

		// populate the replace menu
    JMenu replaceMenu = new JMenu("Replace with \u00AB\u00BB");
    menu.add(replaceMenu);
    if (!isClassifier || isClassifier && !shallowEmpty)
      replaceMenu.setEnabled(false);
    else
    {
      boolean replaceEnabled = false;
      for (final Stereotype toReplaceWith : getApplicableStereotypes(element))
      {
        JMenuItem add = new JMenuItem(new AbstractAction(toReplaceWith.getName())
            {
              public void actionPerformed(ActionEvent e)
              {
              	StereotypeUtilities.formAddRawStereotypeTransaction(coordinator, element, toReplaceWith);
              }
            });
        replaceMenu.add(add);
        replaceEnabled = true;
      }
      replaceMenu.setEnabled(replaceEnabled);
    }
    if (readonly)
    	replaceMenu.setEnabled(false);
		
		// populate the delete menu
		JMenu deleteMenu = new JMenu("Remove \u00AB\u00BB");
    menu.add(deleteMenu);
    if (shallowEmpty)
		  deleteMenu.setEnabled(false);
		else
		{
  		boolean deleteEnabled = false;
  		for (final Stereotype toRemove : getAppliedStereotypes(element))
  		{
  			JMenuItem add = new JMenuItem(new AbstractAction(toRemove.getName())
  					{
  						public void actionPerformed(ActionEvent e)
  						{
                StereotypeUtilities.formRemoveRawStereotypeTransaction(coordinator, element, toRemove);
  						}
  					});
  			deleteMenu.add(add);
  			deleteEnabled = true;
  		}
      deleteMenu.setEnabled(deleteEnabled);
		}
    if (readonly)
    	deleteMenu.setEnabled(false);
	}

	
  private String getStereoName(DEStratum home, Stereotype stereo)
  {
    DEComponent comp = GlobalDeltaEngine.engine.locateObject(stereo).asComponent();
    return comp.getName(home);
  }

  private List<Stereotype> getApplicableStereotypes(Element element)
  {
    // add the possible stereos that we can ...add
    Set<Stereotype> allApplicable = GlobalSubjectRepository.repository.findApplicableStereotypes(element.eClass());
    List<Stereotype> applicable = new ArrayList<Stereotype>(allApplicable);
    
    // put the lists in alpha order
    sort(applicable);
    return applicable;
  }
  
  private List<Stereotype> getApplicableButUnappliedStereotypes(Element element)
  {
    // add the possible stereos that we can add
    Set<Stereotype> allApplicable = GlobalSubjectRepository.repository.findApplicableStereotypes(element.eClass());
    List<Stereotype> applicable = new ArrayList<Stereotype>(allApplicable);
    applicable.removeAll(getAppliedStereotypes(element));
    
    // put the lists in alpha order
    sort(applicable);
    return applicable;
  }
  
  private List<Stereotype> getAppliedStereotypes(Element element)
  {
    // what is currently applied?
    List<DEComponent> all = StereotypeUtilities.findAllStereotypes(element);
    List<Stereotype> applied = new ArrayList<Stereotype>();
    for (DEComponent a : all)
      applied.add((Stereotype) a.getRepositoryObject());
    // put the lists in alpha order
    sort(applied);    
    return applied;
  }

  private void sort(List<Stereotype> stereos)
  {
    Collections.sort(stereos,
        new Comparator<Stereotype>()
        {
          public int compare(Stereotype o1, Stereotype o2)
          {
            return o1.getName().compareTo(o2.getName());
          }
        });
  }
  
  private DEStratum getHome(Element element)
  {
    return GlobalDeltaEngine.engine.locateObject(
        element instanceof Package ?
            element :
            GlobalSubjectRepository.repository.findOwningPackage(element)).asStratum();
  }
}
