/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import java.awt.event.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;


public class GotoDiagramAction extends AbstractAction
{
	private DiagramFacet diagram;
	private Package pkg;
	private boolean addTab;
	private boolean gotoParent;
	private boolean addToStack;

	public GotoDiagramAction(DiagramFacet diagram, String description, boolean addTab, boolean gotoParent, boolean addToStack)
	{
		super(description);
		this.addTab = addTab;
		this.diagram = diagram;
		this.gotoParent = gotoParent;
		this.addToStack = addToStack;
	}

	public GotoDiagramAction(Package pkg, String description, boolean addTab, boolean gotoParent)
	{
		super(description);
		this.addTab = addTab;
		this.pkg = pkg;
		this.gotoParent = gotoParent;
	}

	public void actionPerformed(ActionEvent e)
	{
		// find the package corresponding to the package id
    Package gotoPkg = pkg != null ? pkg : (Package) diagram.getLinkedObject();
		if (gotoParent)
			gotoPkg = GotoDiagramAction.getParentDiagramPackage(gotoPkg);
		
		if (gotoPkg != null)
			GlobalPackageViewRegistry.activeRegistry.open(
					gotoPkg,
					true,
					addTab,
					null,
					GlobalPackageViewRegistry.activeRegistry.getFocussedView().getFixedPerspective(),
					addTab);
	}
	
	public static Package getParentDiagramPackage(Package pkg)
	{
	  SubjectRepositoryFacet repository =
	    GlobalSubjectRepository.repository;
	
	  // can't go any higher than the top level
	  Model topLevel = repository.getTopLevelModel();
	  
	  // iterate upwards, looking for the next package (or model) which will hold the diagram
	  Namespace parent = pkg.getNamespace();
	  while (parent != null && !(parent instanceof Package) && parent != topLevel)
	    parent = parent.getNamespace();
	  return (Package) parent;
	}
}