package com.intrinsarc.evolve.umldiagrams.constituenthelpers;

import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class PerspectiveHelper
{
	public static DEStratum extractDEStratum(DiagramFacet diagram, ContainerFacet container)
	{
	  IDeltaEngine engine = GlobalDeltaEngine.engine;
	  SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	
	  // work out the perspective
	  Package stratum = null;
	  if (diagram.getPossiblePerspective() != null)
	  	stratum = (Package) diagram.getPossiblePerspective();
	  else
	  	stratum = repository.findVisuallyOwningStratum(diagram, container);
	  return engine.locateObject(stratum).asStratum();
	}

	public static Package extractStratum(DiagramFacet diagram, ContainerFacet container)
	{
	  // work out the perspective
	  if (diagram.getPossiblePerspective() != null)
	  	return (Package) diagram.getPossiblePerspective();
	  else
	  	return  GlobalSubjectRepository.repository.findVisuallyOwningStratum(diagram, container);
	}
}
