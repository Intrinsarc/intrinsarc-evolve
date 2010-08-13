package com.intrinsarc.evolve.repositorybrowser;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;

public interface RepositoryBrowserListenerFacet extends Facet
{
  void requestClose();
  void haveSetReusable(boolean reusable);
  void outerSelectionChanged(Element element);
}
