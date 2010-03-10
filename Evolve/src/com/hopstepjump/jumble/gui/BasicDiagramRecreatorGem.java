package com.hopstepjump.jumble.gui;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;

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
      Element element = GlobalSubjectRepository.repository.findNamedElementByUUID(diagramReference.getId());
      return element instanceof Package ?
      		(Package) element :
      		(Package) GlobalSubjectRepository.repository.findOwningElement(element, PackageImpl.class);
    }
  }
}
