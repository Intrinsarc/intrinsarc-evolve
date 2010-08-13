package com.intrinsarc.evolve.errorchecking;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.idraw.foundation.*;

public class DiagramErrorDetector
{
  private DiagramFacet diagram;
  private ErrorRegister errors;

  public DiagramErrorDetector(
      DiagramFacet diagram,
      ErrorRegister errors)
  {
    this.diagram = diagram;
    this.errors = errors;
  }
  
  public void determineErrors()
  {
    // get the visual stratum of the element
    Package diagramPerspective = (Package) diagram.getLinkedObject();
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    DEStratum perspective = engine.locateObject(diagramPerspective).asStratum();    
  	ErrorChecker checker = new ErrorChecker(errors);
  	
    for (FigureFacet figure : diagram.getFigures())
    {
      Element subject = (Element) figure.getSubject();
      if (subject != null)
      {
        DEObject element = engine.locateObject(subject);
        
        // don't bother with constituents
        if (element != null && element.asConstituent() == null)
          checker.performCheck(perspective, element, true);
      }
    }    
  }
}