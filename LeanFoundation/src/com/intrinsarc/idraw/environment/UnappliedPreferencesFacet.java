package com.intrinsarc.idraw.environment;

import com.intrinsarc.gem.*;

public interface UnappliedPreferencesFacet extends Facet
{
  public String getUnappliedPreference(Preference pref);
  public void storeUnappliedPreference(Preference pref, String value);
}
