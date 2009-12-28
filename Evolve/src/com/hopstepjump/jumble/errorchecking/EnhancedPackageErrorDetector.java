package com.hopstepjump.jumble.errorchecking;

import java.util.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.foundation.*;

public class EnhancedPackageErrorDetector extends StratumErrorDetector
{
	public EnhancedPackageErrorDetector(ErrorRegister errors)
	{
		super(errors);
	}


	/** this method uses UML2 references so, the delta engine can be cleared in the listener
	 *  adding to scalability...
	 * @param toCheck
	 * @param startFrom
	 * @param includeDiagrams 
	 * @param listener
	 */
	public void checkAllInOrderAllowingDeltaEngineClearing(List<DEStratum> deToCheck, int startFrom, boolean deepCheck, boolean includeDiagrams, IDetectorListener listener)
  {
		List<Package> toCheck = convertToUML2References(deToCheck);
		Set<Package> diagramsChecked = new HashSet<Package>();
		
  	int size = toCheck.size();
  	int count[] = {0};
  	for (int lp = (startFrom == -1 ? size - 1 : startFrom); lp < size; lp++)
  	{
  		// treat the current package as the perspective
  		Package perspective = toCheck.get(lp);
  		
  		// if this is destructive, check all that came before the topmost
  		DEStratum dePerspective = GlobalDeltaEngine.engine.locateObject(perspective).asStratum();
  		if (deepCheck || dePerspective.isDestructive() || StratumIterator.joinsDestructive(dePerspective))
  		{
	  		for (int before = 0; before <= lp; before++)
	  			checkOne(includeDiagrams, listener, diagramsChecked, perspective, toCheck.get(before), count);
  		}
  		else
  			checkOne(includeDiagrams, listener, diagramsChecked, perspective, perspective, count);
  	}
  }


	private void checkOne(boolean includeDiagrams, IDetectorListener listener,
			Set<Package> diagramsChecked, Package perspective, Package current, int[] count)
	{
		// translate into delta engine objects 
		DEStratum dePerspective = GlobalDeltaEngine.engine.locateObject(perspective).asStratum();
		DEStratum deCurrent = GlobalDeltaEngine.engine.locateObject(current).asStratum();
		
		if (dePerspective.getTransitivePlusMe().contains(deCurrent))
		{
	  	// if this is check once, and it has been checked already, don't bother
	  	if (!CheckOnceStrata.isOmitCheck(deCurrent))
	  	{
	  		int atStart = errors.getAllErrors().size();
				findErrors(dePerspective, deCurrent);
				count[0]++;
				
				// possibly check the current diagram
				if (includeDiagrams && !diagramsChecked.contains(current))
				{
					diagramsChecked.add(current);
					DiagramReference reference = new DiagramReference(current.getUuid());
					DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(reference, true);
					new DiagramErrorDetector(diagram, errors).determineErrors();
					
		  		int atEnd = errors.getAllErrors().size();
		  		
		  		// if we have had no further errors, and it is check once, then we have now checked correctly
		  		if (atEnd == atStart)
		  			CheckOnceStrata.possiblySetOmitCheck(deCurrent);
				}
	  	}
			
			// tell the listener, which is allowed to clear out the delta engine
			listener.haveChecked(dePerspective, deCurrent);
		}
	}
	
	private List<Package> convertToUML2References(List<DEStratum> toCheck)
	{
		List<Package> translated = new ArrayList<Package>();
		for (DEStratum pkg : toCheck)
			translated.add((Package) pkg.getRepositoryObject());
		return translated;
	}
}
