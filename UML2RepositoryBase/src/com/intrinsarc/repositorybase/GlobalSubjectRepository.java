package com.intrinsarc.repositorybase;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.persistence.*;


/**
 *
 * (c) Andrew McVeigh 06-Sep-02
 *
 */
public class GlobalSubjectRepository
{
  public static final Preference USER_NAME =
  	new Preference("User", "Username", new PersistentProperty(System.getProperty("user.name", "unknown")));
	
	public static SubjectRepositoryFacet repository;
	public static boolean ignoreUpdates;
	
	public static void registerPreferences()
	{
    GlobalPreferences.preferences.addPreferenceSlot(
    		USER_NAME, new PreferenceTypeString(), "The username saved in the model");
	}
}
