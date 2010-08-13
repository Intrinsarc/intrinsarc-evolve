package com.intrinsarc.evolve.repositorybrowser;

import org.eclipse.uml2.Package;

import com.intrinsarc.gem.*;

public interface PackageDiagramOpenerFacet extends Facet
{
  public void openDiagramForPackage(Package pkg);
}
