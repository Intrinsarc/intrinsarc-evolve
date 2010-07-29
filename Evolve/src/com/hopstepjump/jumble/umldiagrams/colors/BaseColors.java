package com.hopstepjump.jumble.umldiagrams.colors;

import java.awt.*;
import java.util.*;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public class BaseColors
{
  public static final Preference COMPONENT_COLOR = new Preference("Colors", "Default component color", new PersistentProperty(new Color(190, 190, 255)));
  public static final Preference PART_COLOR = new Preference("Colors", "Default part color", new PersistentProperty(new Color(190, 190, 255)));
  public static final Preference PRIMITIVE_COLOR = new Preference("Colors", "Default primitive color", new PersistentProperty(new Color(180, 180, 255)));
  public static final Preference CLASS_COLOR = new Preference("Colors", "Default class color", new PersistentProperty(Color.LIGHT_GRAY));
  public static final Preference FACTORY_COLOR = new Preference("Colors", "Default factory color", new PersistentProperty(Color.WHITE));
  public static final Preference PLACEHOLDER_COLOR = new Preference("Colors", "Default placeholder color", new PersistentProperty(Color.LIGHT_GRAY));
  public static final Preference STATE_COLOR = new Preference("Colors", "Default state color", new PersistentProperty(new Color(240, 200, 200)));
  public static final Preference STATE_PART_COLOR = new Preference("Colors", "Default state part color", new PersistentProperty(new Color(240, 200, 200)));
  public static final Preference REQUIREMENTS_FEATURE_COLOR = new Preference("Colors", "Default requirements feature color", new PersistentProperty(Color.LIGHT_GRAY));
  public static final Preference PACKAGE_COLOR = new Preference("Colors", "Default package color", new PersistentProperty(new Color(224, 224, 210)));
  public static final Preference STRATUM_COLOR = new Preference("Colors", "Default stratum color", new PersistentProperty(new Color(224, 207, 207)));
  public static final Preference MODEL_COLOR = new Preference("Colors", "Default model color", new PersistentProperty(new Color(255, 217, 102)));
  public static final Preference NOTE_COLOR = new Preference("Colors", "Default note color", new PersistentProperty(new Color(255, 255, 174)));
  public static final Preference PORT_COLOR = new Preference("Colors", "Default port color", new PersistentProperty(Color.LIGHT_GRAY));
  public static final Preference DEFAULT_COLOR = new Preference("Colors", "Default color", new PersistentProperty(Color.LIGHT_GRAY));

  private static Map<Preference, Color> cached = new HashMap<Preference, Color>();
   
  public static void clearCachedColors()
  {
  	cached.clear();
  }
  
  public static Color getColorPreference(Preference pref)
  {
  	Color col = cached.get(pref);
  	if (col != null)
  		return col;
  	PersistentProperty prop = GlobalPreferences.preferences.getRawPreference(pref);
  	if (prop == null)
  		return Color.LIGHT_GRAY;
  	col = prop.asColor();
  	cached.put(pref, col);
  	return col;
  }
  
	public static void registerPreferenceSlots()
	{
		PreferenceType colorType = new PreferenceTypeColor();
	  GlobalPreferences.preferences.addPreferenceSlot(COMPONENT_COLOR, colorType,
	    "The default color for components.");
	  GlobalPreferences.preferences.addPreferenceSlot(PART_COLOR, colorType,
    	"The default color for parts.");
	  GlobalPreferences.preferences.addPreferenceSlot(CLASS_COLOR, colorType,
  	"The default color for classes.");
	  GlobalPreferences.preferences.addPreferenceSlot(REQUIREMENTS_FEATURE_COLOR, colorType,
  	"The default color for requirements features.");
	  GlobalPreferences.preferences.addPreferenceSlot(PACKAGE_COLOR, colorType,
  	"The default color for packages.");
	  GlobalPreferences.preferences.addPreferenceSlot(STRATUM_COLOR, colorType,
  	"The default color for strata.");
	  GlobalPreferences.preferences.addPreferenceSlot(PRIMITIVE_COLOR, colorType,
  		"The default color for primitives.");
	  GlobalPreferences.preferences.addPreferenceSlot(FACTORY_COLOR, colorType,
  		"The default color for factories.");
	  GlobalPreferences.preferences.addPreferenceSlot(PLACEHOLDER_COLOR, colorType,
			"The default color for placeholders.");
	  GlobalPreferences.preferences.addPreferenceSlot(MODEL_COLOR, colorType,
	  	"The default color for models.");
	  GlobalPreferences.preferences.addPreferenceSlot(STATE_COLOR, colorType,
    	"The default color for states.");
	  GlobalPreferences.preferences.addPreferenceSlot(STATE_PART_COLOR, colorType,
    	"The default color for state parts.");
	  GlobalPreferences.preferences.addPreferenceSlot(NOTE_COLOR, colorType,
    	"The default color for notes.");
	  GlobalPreferences.preferences.addPreferenceSlot(PORT_COLOR, colorType,
	  	"The default color for ports.");
	  GlobalPreferences.preferences.addPreferenceSlot(DEFAULT_COLOR, colorType,
  		"The default color if persistent color information is found.");
	}
}
