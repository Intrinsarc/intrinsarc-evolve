package com.intrinsarc.repository.modelmover;

import java.net.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repository.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class ModelExporter extends ImportExportBase
{
	private static final ImageIcon EXPORT_ICON = IconLoader.loadIcon("export.png");
  private JFrame frame;
	private Set<Package> selected;

  
  public ModelExporter(JFrame frame, Set<Package> selected)
  {
    this.frame = frame;
    this.selected = selected;
  }
  
	public String exportPackages(final LongRunningTaskProgressMonitorFacet monitor)
  {
		final String savedName[] = {null};
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			public void run()
			{
		    // initialise any fields
		    savedReferences = new ArrayList<TransientSavedReference>();
		    translate = new HashMap<String, Element>();
		    topLevelOfFrom = GlobalSubjectRepository.repository.getTopLevelModel();
		    
		    XMLSubjectRepositoryGem repositoryGem;
		    try
		    {
		      repositoryGem = XMLSubjectRepositoryGem.openFile(null, false, false);
		    }
		    catch (RepositoryOpeningException e)
		    {
		      // will not happen, as we are creating a new repository
		      return;
		    }
		    SubjectRepositoryFacet to = repositoryGem.getSubjectRepositoryFacet();
		    
		    try
		    {
			    // copy each top level element to the model in the new repository
			    Model top = to.getTopLevelModel();
			    
			    // set some info
			    try
					{
						top.setDocumentation("Exported from " + InetAddress.getLocalHost().getHostName() + " on " + new Date());
					}
			    catch (UnknownHostException e)
					{
						top.setDocumentation("Exported on " + new Date());
					}
			    
			    for (Package pkg : selected)
			    {
			      monitor.displayInterimPopup(EXPORT_ICON, "Exporting...", "Exporting package " + pkg.getName(), null, -1);
			      Package newPkg = (Package) copyElementAndContained(
			      		pkg,
			          top,
			          UML2Package.eINSTANCE.getPackage_ChildPackages(),
			          true);
			      // make this read-only
			      newPkg.setReadOnly(true);
			      lazyOn();
			      top.getChildPackages().add(newPkg);		      
			      lazyOff();
			    }
			
			    // fix up the references and save
			    lazyOn();
		      monitor.displayInterimPopup(EXPORT_ICON, "Exporting...", "Reestablishing references", null, -1);
			    for (TransientSavedReference ref : reestablishReferences())
			    	createSavedReference(top, ref);
		      monitor.displayInterimPopup(null, "", "", null, 0);
			    String fileName = to.saveTo(
			    		frame,
			    		XMLSubjectRepositoryGem.UML2_EXPORT_FILES, XMLSubjectRepositoryGem.UML2_EXPORT_NO_DOT,
			    		PreferenceTypeDirectory.recent.getLastVisitedDirectory());
			    lazyOff();
			    savedName[0] = fileName;
		    }
		    finally
		    {
		    	to.close();
		    }
			}
		});
		
		return savedName[0];
  }
  
  private void createSavedReference(Model top, TransientSavedReference reference)
	{
  	SavedReference saved = (SavedReference) top.createOwnedMember(UML2Package.eINSTANCE.getSavedReference());
  	saved.setFrom(reference.getFrom().getUuid());
  	saved.setToEClass(reference.getTo().eClass());
  	saved.setTo(reference.getTo().getUuid());
  	saved.setFeature(reference.getReferred());
  	saved.setDocumentation(name(reference.getReferred()) + " from " + name(reference.getFrom()) + " to " + name(reference.getTo()));
	}
  
	private String name(EReference ref)
	{
		return ref.getName();
	}

	private String name(Element elem)
	{
		return ((elem instanceof NamedElement) ? ((NamedElement) elem).getName() : "") + " (" + elem.eClass().getName() + ")";
	}
}
