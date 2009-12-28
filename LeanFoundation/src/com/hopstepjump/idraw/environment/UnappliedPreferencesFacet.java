package com.hopstepjump.idraw.environment;

import com.hopstepjump.gem.*;

public interface UnappliedPreferencesFacet extends Facet
{
  public String getUnappliedPreference(Preference pref);
  public void storeUnappliedPreference(Preference pref, String value);
}
