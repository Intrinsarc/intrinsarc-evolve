package com.hopstepjump.idraw.foundation;

/**
 *
 * (c) Andrew McVeigh 06-Sep-02
 *
 */
public final class GlobalDiagramRegistry
{
	public static DiagramRegistryFacet registry;
	
  public static void resyncViews()
  {
    // resync all diagrams just in case a change was made to something visual
    for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
      diagram.resyncViews();
  }
}
