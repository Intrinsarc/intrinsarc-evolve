/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.hopstepjump.jumble.packageview.actions;

import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public class SelectAllAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	
	public SelectAllAction(DiagramViewFacet diagramView)
	{
		super("Select All");
		this.diagramView = diagramView;
	}
  		
	public void actionPerformed(ActionEvent e)
	{
		diagramView.selectAllFigures();
	}
}


