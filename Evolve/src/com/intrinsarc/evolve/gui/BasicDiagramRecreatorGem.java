package com.intrinsarc.evolve.gui;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;

public class BasicDiagramRecreatorGem
{
  private BasicDiagramRecreatorFacet recreatorFacet = new BasicDiagramRecreatorFacetImpl();
  
  public BasicDiagramRecreatorGem()
  { 
  }
  
  public BasicDiagramRecreatorFacet getBasicDiagramRecreatorFacet()
  {
    return recreatorFacet;
  }
  
  private class BasicDiagramRecreatorFacetImpl implements BasicDiagramRecreatorFacet
  {
    public DiagramFacet recreateAndRegisterDiagram(
    		DiagramReference diagramReference,
        Map<DiagramReference, DiagramFacet> diagrams,
        DiagramPostProcessor postProcessor) throws DiagramRecreationException
   {    	
      Package pkg = locatePackage(diagramReference);
      PersistentDiagram persistentDiagram;
      try
      {
        persistentDiagram = GlobalSubjectRepository.repository.retrievePersistentDiagram(pkg);
      }
      catch (RepositoryPersistenceException ex)
      {
        throw new DiagramRecreationException(ex);
      }
      BasicDiagramGem gem = new BasicDiagramGem(pkg, persistentDiagram, postProcessor, false);

      final DiagramFacet diagram = gem.getDiagramFacet();
      setSavedDetails(diagram, pkg);
      diagrams.put(diagram.getDiagramReference(), diagram);
      gem.completeInitialisationAfterRegistration();      

      gem.connectBasicDiagramReadOnlyFacet(
          new BasicDiagramReadOnlyFacet()
          {
            public boolean isReadOnly()
            {
              return GlobalSubjectRepository.repository.isReadOnly(
                  (Package) diagram.getLinkedObject());
            } 
          });
      return diagram;
    }
    
		public DiagramFacet recreateAndRegisterDiagram(
				DiagramReference reference,
				Map<DiagramReference, DiagramFacet> diagrams,
				DiagramFacet chainedSource, Object perspective,
				DiagramPostProcessor postProcessor)
		{
      BasicDiagramGem gem = new BasicDiagramGem(reference, chainedSource, perspective, postProcessor, false);
      gem.getDiagramFacet().setLinkedObject(chainedSource.getLinkedObject());
      final DiagramFacet diagram = gem.getDiagramFacet();
      setSavedDetails(diagram, (Package) chainedSource.getLinkedObject());
      diagrams.put(diagram.getDiagramReference(), diagram);
      gem.completeInitialisationAfterRegistration();      

      gem.connectBasicDiagramReadOnlyFacet(
          new BasicDiagramReadOnlyFacet()
          {
            public boolean isReadOnly()
            {
            	return true;
            } 
          });
      return diagram;
		}
		
		private void setSavedDetails(DiagramFacet diagram, Package pkg)
		{
			J_DiagramHolder holder = pkg.getJ_diagramHolder();
			DiagramSaveDetails saveDetails = new DiagramSaveDetails(holder.getSavedBy(), holder.getSaveTime());
			diagram.setSaveDetails(saveDetails);
		}

    public PersistentDiagram retrievePersistentDiagram(DiagramReference diagramReference) throws DiagramRecreationException
    {
      Package pkg = locatePackage(diagramReference);
      try
      {
        return GlobalSubjectRepository.repository.retrievePersistentDiagram(pkg);
      }
      catch (RepositoryPersistenceException ex)
      {
        throw new DiagramRecreationException(ex);
      }
    }
    
    private Package locatePackage(DiagramReference diagramReference)
    {
      // retrieve the package
      Element element = (Element) diagramReference.getId();
      return element instanceof Package ?
      		(Package) element :
      		(Package) GlobalSubjectRepository.repository.findOwningElement(element, PackageImpl.class);
    }
  }
}
