package com.hopstepjump.jumble.umldiagrams.substitutionarc;

import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;

/**
 * @author andrew
 */
public class SubstitutionUtilities
{
  public static DependencyCreatorGem makeSubstitutionCreator()
  {
    DependencyCreatorGem substitutionCreator = new DependencyCreatorGem();
    substitutionCreator.setSubstitution(true);
    return substitutionCreator;
  }

  public static DependencyCreatorGem makeIncrementalSubstitutionCreator()
  {
    DependencyCreatorGem incrementalCreator = new DependencyCreatorGem();
    incrementalCreator.setResembles(true);
    incrementalCreator.setSubstitution(true);
    return incrementalCreator;
  }
  
  public static DependencyCreatorGem makeResemblanceCreator()
  {
    DependencyCreatorGem resemblanceCreator = new DependencyCreatorGem();
    resemblanceCreator.setResembles(true);
    return resemblanceCreator;
  }

	public static DependencyCreatorGem makeReplacementCreator()
	{
    DependencyCreatorGem creator = new DependencyCreatorGem();
    creator.setSubstitution(true);
    return creator;
	}

	public static DependencyCreatorGem makeEvolutionCreator()
	{
    DependencyCreatorGem creator = new DependencyCreatorGem();
    creator.setResembles(true);
    creator.setSubstitution(true);
    return creator;
	}
}
