package com.hopstepjump.deltaengine.base;

import java.util.*;

public abstract class DEStratum extends DEObject
{
	/** abstract functions that are repository specific */
  public abstract boolean isRelaxed();
  public abstract boolean isDestructive();
  public abstract boolean isReadOnly();
  public abstract boolean isCheckOnceIfReadOnly();
  /** get the raw dependency list */
  public abstract List<? extends DEStratum> getRawDependsOn();
  /** get any packages or strata that are nested directly in this */
  public abstract List<? extends DEStratum> getDirectlyNestedPackages();
	public abstract List<DEElement> getChildElements();

  /**
   * flattened is the full transformation down to a flat package structure where every package
   * is explicit about its full dependency set.  Used to check circularity.
   */
  private transient Set<DEStratum> transitive;

  /**
   * this is the same as transitive, but also includes this package
   */
  private transient Set<DEStratum> transitivePlusMe;

  /**
   * stratumVisibleFlattened is the full transformation down to a flat package structure, as above, but respecting the
   * strict/relaxed stratum rules. 
   */
  private transient Set<DEStratum> canSee;
  private transient Set<DEStratum> canSeePlusMe;

  /**
   * visibleDistinctStratumFlattened is the visible stratum from this one, removing any stratum of direct dependencies 
   */
  private transient Set<DEStratum> simpleDependsOn;

  /**
   * this is all the strata that a dependency to this stratum will expose the client to
   */
  private transient Set<DEStratum> exposesStrata;

  /**
   * need an engine to locate objects during the course of computation
   */
  public DEStratum()
  {
    super();
  }

  public DEStratum asStratum()
  {
    return this;
  }
  
  /**
   * (formal)
   * returns anything we directly depend on, taking away what any children depend on
   * NOTE: only valid for a stratum
   * @return
   */
  public Set<DEStratum> getSimpleDependsOn()
  {
    if (simpleDependsOn == null)
    {
      simpleDependsOn = new HashSet<DEStratum>(getTransitive());
      // take away the dependencies of any nested children, and other strata we depend on
      for (DEStratum child : getDirectlyNestedPackages())
        simpleDependsOn.removeAll(child.getTransitive());
      for (DEStratum dep : getRawDependsOn())
        simpleDependsOn.removeAll(dep.getTransitive());
    }
    return simpleDependsOn;
  }
  
  /**
   * (formal)
   * same as canSee, but add this also...
   * @return
   */
  public Set<? extends DEStratum> getCanSee()
  {
    if (canSee == null)
    {
      canSee = new LinkedHashSet<DEStratum>();
      collectTransitiveDependencies(canSee, false);
    }
    return canSee;
  }
  
  /**
   * (formal)
   * same as canSee, but add this also...
   * @return
   */
  public Set<? extends DEStratum> getCanSeePlusMe()
  {
    if (canSeePlusMe == null)
    {
      canSeePlusMe = new LinkedHashSet<DEStratum>(getCanSee());
      canSeePlusMe.add(this);
    }
    return canSeePlusMe;
  }
  
  /**
   * same as getVisibleFlattenedDependencies, but does not respect visibility.  i.e. use this for circularity checking
   * @return
   */
  public Set<DEStratum> getTransitive()
  {
    if (transitive == null)
    {
      transitive = new LinkedHashSet<DEStratum>();
      collectTransitiveDependencies(transitive, true);
    }
    return transitive;
  }
  
  /**
   * same as getTransitive((), but includes this also
   * @return
   */
  public Set<DEStratum> getTransitivePlusMe()
  {
    if (transitivePlusMe == null)
    {
      transitivePlusMe = new LinkedHashSet<DEStratum>(getTransitive());
      transitivePlusMe.add(this);
    }
    return transitivePlusMe;
  }
  
  /**
   * works out what strata will be exposed by this is another stratum depends on this.
   * not intended to be called for packages.
   * @return
   */
  public Collection<? extends DEStratum> getExposesStrata()
  {
    if (exposesStrata == null)
    {
      exposesStrata = new LinkedHashSet<DEStratum>();
      collectExposesDependencies(exposesStrata, false);
    }
    return exposesStrata;
  }  
  
  /**
   * is this circular?  i.e. is this contained in the full set of dependencies
   * @return
   */
  public boolean isCircular()
  {
    return getTransitive().contains(this);
  }
  
  ////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////// private methods ////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////
  
  /** collect transitive dependencies for this strata */
  private void collectTransitiveDependencies(Set<DEStratum> strata, boolean ignoreRelaxed)
  {
    // if we are already present, don't bother
    if (strata.contains(this))
      return;

    // we can directly see any children
    addExposesDependencies(strata, getDirectlyNestedPackages(), true, ignoreRelaxed);
    
    // we can see anything that we depend on
    addExposesDependencies(strata, getRawDependsOn(), false, ignoreRelaxed);
    
    // we can see anything that our parents directly depend on
    DEObject parent = getParent();
    while (parent != null)
    {
      DEStratum pkg = parent.asStratum();
      pkg.addExposesDependencies(strata, pkg.getRawDependsOn(), false, ignoreRelaxed);
      parent = parent.getParent();
    }
  }

  /** collect exposures for this strata */
  private void collectExposesDependencies(Set<DEStratum> strata, boolean ignoreRelaxed)
  {
    // if we are already present, don't bother
    if (strata.contains(this))
      return;

    // we expose ourself always...
    strata.add(this);
    
    // we expose any directly nested strata that we explicitly depend on
    
    // if we are ignoring relaxed/strict rules, then take anything nested and anything we depend on
    // otherwise, if we are relaxed expose only nested packages, nested stratum we depend on and external dependencies
    //            if we are strict, expose only nested packages and nested stratum we depend on
    for (DEStratum pkg: getDirectlyNestedPackages())
    	if (ignoreRelaxed)
    		pkg.collectExposesDependencies(strata, ignoreRelaxed);
    for (DEStratum pkg: getRawDependsOn())
    	if (ignoreRelaxed || isRelaxed() || isNested(pkg))
    		pkg.collectExposesDependencies(strata, ignoreRelaxed);
  }
  
  /** recursively follow any dependencies */
  private void addExposesDependencies(Set<DEStratum> strata, List<? extends DEStratum> dependsOn, boolean nested, boolean ignoreRelaxed)
  {
    for (DEStratum pkg: dependsOn)
    {
      if (isNested(pkg) == nested)
        pkg.collectExposesDependencies(strata, ignoreRelaxed);
    }
  }
  
  /** is the package one of our (possibly indirectly) nested children? */
  private boolean isNested(DEStratum pkg)
  {
    // can we travel up to meet ourselves?
    DEObject parent = pkg.getParent();
    while (parent != null)
    {
      if (parent == this)
        return true;
      parent = parent.getParent();
    }
    return false;
  }
  
  // utility functions
  public List<DEStratum> determineOrderedPackages(boolean onlyNested)
  {
  	List<DEStratum> starts = new ArrayList<DEStratum>();
  	
  	starts.add(this);
  	return determineOrderedPackages(starts, onlyNested);
  }
  
  public static List<DEStratum> determineOrderedPackages(List<DEStratum> start, boolean onlyNested)
  {
  	// sort the packages based on the transitive closure
  	if (onlyNested)
  		return new ArrayList<DEStratum>(expandNestedPackages(start));
  	else
  	{  		
  		Set<DEStratum> pkgs = new HashSet<DEStratum>();
  		for (DEStratum pkg : start)
  		  pkgs.addAll(pkg.getTransitivePlusMe());
  		
    	// create a graph and topologically sort
    	SimpleDirectedGraph<DEStratum> graph = new SimpleDirectedGraph<DEStratum>();
    	for (DEStratum p : pkgs)
    		graph.addNode(p);    	
    	for (DEStratum p : pkgs)
    		for (DEStratum dep : p.getTransitive())
    			graph.addEdge(p, dep);

    	List<DEStratum> sorted = graph.makeTopologicalSort();
    	return sorted;
  	}
  }
  
	private static Set<DEStratum> expandNestedPackages(List<DEStratum> pkgs)
	{
  	Set<DEStratum> expanded = new HashSet<DEStratum>(pkgs);
  	int size;
  	do
  	{
  		size = expanded.size();
  		for (DEStratum pkg : new ArrayList<DEStratum>(expanded))
  			expanded.addAll(pkg.getDirectlyNestedPackages());
  	}
  	while (size != expanded.size());
  	
  	return expanded;
	}
	
	public static List<DEStratum> filterOutChildren(List<DEStratum> all)
	{
		List<DEStratum> filtered = new ArrayList<DEStratum>();
		for (DEStratum a : all)
		{
			boolean parent = false;
			for (DEStratum b : all)
				if (a.getParent() == b)
				{
					parent = true;
					break;
				}
			if (!parent)
				filtered.add(a);
		}
		return filtered;
	}
	
	public static Collection<? extends DEElement> getChildElementsInDependencyOrder(List<? extends DEElement> childElements)
	{
		SimpleDirectedGraph<DEElement> graph = new SimpleDirectedGraph<DEElement>();
		for (DEElement e : childElements)
			graph.addNode(e);
			
		for (DEElement e : childElements)
			if (e.asComponent() != null)
				addEdges(graph, e.asComponent());

		// handle resemblance
		for (DEElement e : childElements)
			for (DEElement res : e.getRawResembles())
			{
				// only include if in the same package
				if (res.getHomeStratum() == e.getHomeStratum())
					graph.addEdge(e, res);
			}
		
    // handle add any stereotypes
    for (DEElement e : childElements)
      for (DeltaPair pair : e.getDeltas(ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE).getReplaceObjects())
      {
        DEAppliedStereotype appl = pair.getConstituent().asAppliedStereotype();
        DEComponent stereo = appl.getStereotype();
        if (stereo.getHomeStratum() == e.getHomeStratum())
          graph.addEdge(e, stereo);
      }
		
		// handle substitution: make every non-substitution have a dependency on every substitution that doesn't depend on it
		for (DEElement e : childElements)
		{
			DEComponent ec = e.asComponent();
			if (ec != null && ec.getSubstitutes().isEmpty())
			{
				for (DEElement sub : childElements)
				{
					DEComponent se = sub.asComponent();
					if (se != null && !se.getSubstitutes().isEmpty() && !graph.pathExists(se, ec))
						graph.addEdge(ec, se);
				}
			}
		}

		return graph.makeTopologicalSort();
	}
	
	private static void addEdges(SimpleDirectedGraph<DEElement> graph, DEComponent c)
	{
		// handle the added and replaced attributes
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getAddObjects())
			addEdge(graph, c, pair.getConstituent().asAttribute().getType());
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getReplaceObjects())
			addEdge(graph, c, pair.getConstituent().asAttribute().getType());

		// handle the ports
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_PORT).getAddObjects())
		{
			for (DEInterface iface : pair.getConstituent().asPort().getSetRequiredInterfaces())
				addEdge(graph, c, iface);
			for (DEInterface iface : pair.getConstituent().asPort().getSetProvidedInterfaces())
				addEdge(graph, c, iface);
		}
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_PORT).getReplaceObjects())
		{
			for (DEInterface iface : pair.getConstituent().asPort().getSetRequiredInterfaces())
				addEdge(graph, c, iface);
			for (DEInterface iface : pair.getConstituent().asPort().getSetProvidedInterfaces())
				addEdge(graph, c, iface);
		}
		
		// handle the part types
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_PART).getAddObjects())
			addEdge(graph, c, pair.getConstituent().asPart().getType());
		for (DeltaPair pair : c.getDeltas(ConstituentTypeEnum.DELTA_PART).getReplaceObjects())
			addEdge(graph, c, pair.getConstituent().asPart().getType());
	}
	
	private static void addEdge(SimpleDirectedGraph<DEElement> graph, DEComponent c, DEElement dependent)
	{
		if (graph.containsNode(dependent))
			graph.addEdge(c, dependent);
	}
	
	public abstract void forceParent(DEStratum root);
}
