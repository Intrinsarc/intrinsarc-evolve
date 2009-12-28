package com.hopstepjump.jumble.repositorybrowser;

import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;

public interface PackageDiagramOpenerFacet extends Facet
{
  public void openDiagramForPackage(Package pkg);
}
