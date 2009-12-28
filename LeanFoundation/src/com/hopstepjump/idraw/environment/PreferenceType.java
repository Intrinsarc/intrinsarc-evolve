package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

/**
 * NOTE: the type must be stateless, so we can make lots of editors from one type
 * @author amcveigh
 */

public interface PreferenceType
{
  public PreferenceSlotEditor makeEditor(
      UnappliedPreferencesFacet unapplied,
      PreferencesFacet preferences,
      PreferenceSlot slot,
      JPanel panel,
      GridBagConstraints left,
      GridBagConstraints right, 
      Preferences registry,
      Map<String, PreferenceSlotEditor> editors);
}
