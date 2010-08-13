package com.intrinsarc.idraw.foundation;

import java.util.*;

import com.intrinsarc.gem.*;

/**
 *
 * (c) Andrew McVeigh 10-Jan-03
 *
 */
public interface ClipboardFacet extends Facet
{
	/** copying needs to alter the ids of the figures */
	public boolean addIncludedInCopy(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent);
	/** deletion identifies outgoing links as well */
	public boolean addIncludedInDelete(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent);
}
