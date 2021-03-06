/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.idraw.figurefacilities.selectionbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

/**
 * @author Andrew
 */
public class LocateElementsAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	private ToolCoordinatorFacet coordinator;
	
	public LocateElementsAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
	{
		super("Locate elements here");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
	}

	public void actionPerformed(ActionEvent e)
	{	
    if (diagramView.getDiagram().isReadOnly())
      return;
    if (!allFiguresWritable(diagramView.getSelection().getSelectedFigures()))
      return;

    // count first
    int number = 0;
    for (FigureFacet figure : diagramView.getSelection().getSelectedFigures())
			if (figure.hasDynamicFacet(LocationFacet.class))
				number++;
    if (number == 0)
    	return;

		// location transaction
		if (number == 1)
			coordinator.startTransaction("located element in place", "unlocated element");
		else
			coordinator.startTransaction("located elements in their places", "unlocated elements");
    for (FigureFacet figure : diagramView.getSelection().getSelectedFigures())
    {
			if (figure.hasDynamicFacet(LocationFacet.class) && !GlobalSubjectRepository.repository.isReadOnly((Element) figure.getSubject()))
				SetLocationTransaction.locate(figure);
		}
    coordinator.commitTransaction();
    
		// place a small delay to make it look like we are doing something :-)
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException ex)
		{
		}
	}

	 public static boolean allFiguresWritable(Set<FigureFacet> selectedFigures)
	  {
	    for (FigureFacet figure : selectedFigures)
	    {
	      // check that the place it is going is ok
	  	if (figure.getSubject() != null)
	  	{
	      Element owner = ((Element) figure.getSubject()).getOwner();
	      
	      if (GlobalSubjectRepository.repository.isReadOnly(owner))
	        return false;
	      
	      // must check that the visual container is writable also
	      // but look out for arcs, which cannot be contained
	      ContainerFacet container = figure.getContainedFacet() != null ? figure.getContainedFacet().getContainer() : null;
	      
	      if (container == null &&
	          GlobalSubjectRepository.repository.isReadOnly((Element) figure.getDiagram().getLinkedObject()))
	        return false;
	      
	      if (container != null &&
	          GlobalSubjectRepository.repository.isContainerContextReadOnly(container.getFigureFacet()))
	        return false;
	  	}
	  }
	  return true;
	}
}
