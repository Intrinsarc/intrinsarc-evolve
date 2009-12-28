package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 22-Jan-03
 *
 */
public class CopyToDiagramCommand extends AbstractCommand
{
  private UDimension copyOffset;	  
	private DiagramReference destinationDiagram;
	private Collection<PersistentFigure> persistentFigures;

	public CopyToDiagramCommand(String executeDescription, String unExecuteDescription,
															UDimension copyOffset, DiagramReference destinationDiagram,
															Collection<PersistentFigure> persistentFigures)
	{
		super(executeDescription, unExecuteDescription);
		this.copyOffset = copyOffset;
		this.destinationDiagram = destinationDiagram;
		this.persistentFigures = persistentFigures;
	}
	
	/**
	 * @see com.hopstepjump.idraw.foundation.Command#execute(boolean)
	 */
	public void execute(boolean isTop)
	{
		DiagramFacet destination = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(destinationDiagram);
		
		// copy the elements to the destination
		destination.addPersistentFigures(persistentFigures, copyOffset);
	}


	/**
	 * @see com.hopstepjump.idraw.foundation.Command#unExecute()
	 */
	public void unExecute()
	{
		// NOTE: due to an earlier check, there will be no saved ids if the destination is the clipboard,
		// as copying to the clipboard shouldn't be undone
		DiagramFacet destination = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(destinationDiagram);
		
		// work out the top level figures
		for (Iterator iter = persistentFigures.iterator(); iter.hasNext();)
		{
			PersistentFigure pFig = (PersistentFigure) iter.next();
			FigureFacet figure = destination.possiblyRetrieveFigure(pFig.getId());
			if (figure != null)
				destination.remove(figure);
		}
	}	
}
