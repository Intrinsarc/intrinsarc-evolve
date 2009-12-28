package com.hopstepjump.jumble.repositorybrowser;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;

public interface RepositoryBrowserListenerFacet extends Facet
{
  void requestClose();
  void haveSetReusable(boolean reusable);
  void outerSelectionChanged(Element element);
}
