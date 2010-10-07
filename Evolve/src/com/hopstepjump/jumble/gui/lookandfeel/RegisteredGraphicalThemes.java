package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 * handles a set of themes, and can tell which one should be active according to
 * preferences
 * 
 * @author amcveigh
 */
public class RegisteredGraphicalThemes
{
  public static final Preference INITIAL_X_POS = new Preference("Appearance", "Initial application X position", new PersistentProperty(100));
  public static final Preference INITIAL_Y_POS = new Preference("Appearance", "Initial application Y position", new PersistentProperty(100));
  public static final Preference INITIAL_WIDTH = new Preference("Appearance", "Initial application width", 950);
  public static final Preference INITIAL_HEIGHT = new Preference("Appearance", "Initial application height", 830);
  public static final Preference INITIAL_BROWSER_X_POS = new Preference("Appearance", "Initial browser X position", new PersistentProperty(100));
  public static final Preference INITIAL_BROWSER_Y_POS = new Preference("Appearance", "Initial browser Y position", new PersistentProperty(100));
  public static final Preference INITIAL_BROWSER_WIDTH = new Preference("Appearance", "Initial browser width", new PersistentProperty(700));
  public static final Preference INITIAL_BROWSER_HEIGHT = new Preference("Appearance", "Initial browser height", new PersistentProperty(350));
  public static final Preference INITIAL_EDITOR_X_POS = new Preference("Appearance", "Initial editor X position", new PersistentProperty(100));
  public static final Preference INITIAL_EDITOR_Y_POS = new Preference("Appearance", "Initial editor Y position", new PersistentProperty(100));
  public static final Preference INITIAL_EDITOR_WIDTH = new Preference("Appearance", "Initial editor width", new PersistentProperty(600));
  public static final Preference INITIAL_EDITOR_HEIGHT = new Preference("Appearance", "Initial editor height", new PersistentProperty(600));
  public static final Preference INITIAL_RUNNER_X_POS = new Preference("Appearance", "Initial runner X position", new PersistentProperty(400));
  public static final Preference INITIAL_RUNNER_Y_POS = new Preference("Appearance", "Initial runner Y position", 400);
  public static final Preference INITIAL_RUNNER_WIDTH = new Preference("Appearance", "Initial runner width", 450);
  public static final Preference INITIAL_RUNNER_HEIGHT = new Preference("Appearance", "Initial runner height", 400);
  public static final Preference LNF_PREFERENCE = new Preference("Appearance", "Application look and feel", new PersistentProperty(LunaGraphicalTheme.THEME_NAME));
  public static final Preference LNF_SUBTHEME_PREFERENCE = new Preference("Appearance", "Application look and feel subtheme");
	public static final Preference INITIAL_PALETTE_WIDTH = new Preference("Appearance", "Palette width", 165);
  
  private static RegisteredGraphicalThemes instance = new RegisteredGraphicalThemes();
  private List<GraphicalTheme> themes = new ArrayList<GraphicalTheme>();
  private GraphicalTheme appliedTheme;
  private String appliedSubTheme;

  public synchronized static RegisteredGraphicalThemes getInstance()
  {
    return instance;
  }
  
  public void setLookAndFeel()
  {
    try
		{
    	GraphicalTheme newTheme = getStartupTheme();
    	String newSubTheme = RegisteredGraphicalThemes.getSubtheme();
    	if (newTheme != appliedTheme || appliedSubTheme != newSubTheme)
    	{
    		newTheme.change(newSubTheme);
    		RegisteredGraphicalThemes.determineNativeTitleBars();
    		appliedTheme = newTheme;
    		appliedSubTheme = newSubTheme;
    	}
		}
		catch (Exception e)
		{
			System.err.println("$$ problem changing look and feel");
			e.printStackTrace();
		}
  }

  private RegisteredGraphicalThemes()
  {
    themes.add(new LunaGraphicalTheme());
    themes.add(new NimbusGraphicalTheme());
    themes.add(new QuaquaGraphicalTheme());
    themes.add(new SmartGraphicalTheme());
    themes.add(new FastGraphicalTheme());
    themes.add(new AluminiumGraphicalTheme());
    themes.add(new AcrylGraphicalTheme());
    themes.add(new SystemGraphicalTheme());
    themes.add(new MetalGraphicalTheme());
  }

  public List<GraphicalTheme> getThemes()
  {
    return themes;
  }

  public GraphicalTheme getStartupTheme()
  {
    return getTheme(getStartupThemeName());
  }
  
  public GraphicalTheme getTheme(String name)
  {
    // look for the theme, if we can't find the name, default
    for (GraphicalTheme theme : themes)
    {
      if (theme.getName().equals(name))
        return theme;
    }
    // if we get here, we have a problem
    return new SmartGraphicalTheme();    
  }

  public String getStartupThemeName()
  {
    return GlobalPreferences.preferences.getRawPreference(LNF_PREFERENCE).asString();
  }
  
  public static String getSubtheme()
  {
  	String pref = GlobalPreferences.preferences.getRawPreference(LNF_SUBTHEME_PREFERENCE).asString();
    if (pref == null)
    	return "Default";
    return pref;
  }

  public void registerPreferenceSlots()
  {
    PreferenceType booleanType = new PreferenceTypeBoolean();
    PreferenceType integerType = new PreferenceTypeInteger();

    // register the themes
    PreferenceTypeEnumeration availableThemes = new PreferenceTypeEnumeration();
    for (GraphicalTheme theme : themes)
      availableThemes.addValue(theme.getName());

    GlobalPreferences.preferences.addPreferenceSlot(LNF_PREFERENCE, availableThemes,
        "The look and feel of the application.  Will only be appiled on restart.");
    
    // now do the subthemes
    final String theme = GlobalPreferences.preferences.getRawPreference(LNF_PREFERENCE).asString();
    List<String> subthemeValues = getTheme(theme).getSubthemes();
    PreferenceTypeEnumeration subthemes = new PreferenceTypeEnumeration(subthemeValues)
    {
      private String cachedTheme = theme;
      @Override
      public List<String> refreshValues(UnappliedPreferencesFacet unapplied)
      {
        String newTheme = unapplied.getUnappliedPreference(LNF_PREFERENCE);
        if (newTheme.equals(cachedTheme))
          return null;  // no change needed
        cachedTheme = newTheme;        
        return getTheme(newTheme).getSubthemes();
      }
    };
    GlobalPreferences.preferences.addPreferenceSlot(LNF_SUBTHEME_PREFERENCE, subthemes,
        "The look and feel subtheme of the application.  Will only be appiled on restart.");
    
    GlobalPreferences.preferences.addPreferenceSlot(SmartGraphicalTheme.JTATTOO_LNF_FONT_PREFERENCE, new PreferenceTypeFont(),
        "The font to use for the look and feel");

    // register the interface display preference
    PreferenceTypeEnumeration interfaceDisplayType = new PreferenceTypeEnumeration(new String[]{"UML2", "Darwin",
        "hybrid UML2/Darwin"});
    GlobalPreferences.preferences.addPreferenceSlot(new Preference("Appearance", "Interface display type"), interfaceDisplayType,
        "Controls whether UML2, Darwin or a hybrid approach is used for interface depiction.");

    // register the antialiasing preference
    PreferenceTypeEnumeration antiAliasingOptions = new PreferenceTypeEnumeration(new String[]{"off", "on", "gasp",
        "lcd", "lcd_hbgr", "lcd_vrgb", "lcd_vbgr"});
    GlobalPreferences.preferences.addPreferenceSlot(new Preference("Appearance", "Font antialiasing"), antiAliasingOptions,
        "Controls whether font anti-aliasing is enabled or not.  Will only be appiled on restart.");

    // register the opengl preference
    GlobalPreferences.preferences.addPreferenceSlot(new Preference("Appearance", "Application uses OpenGL for Java2D"), new PreferenceTypeBoolean(),
        "Should OpenGL be used to accelerate Java2D?  Will only be appiled on restart.");

    // add the effect slots
    PreferenceTypeEnumeration transitionOptions = new PreferenceTypeEnumeration(new String[]{"none", "zoom"});
    GlobalPreferences.preferences.addPreferenceSlot(new Preference("Appearance", "Diagram transition effect"), transitionOptions,
        "Type of transitions when moving from one diagram to another.");

    GlobalPreferences.preferences.addPreferenceSlot(new Preference("Advanced", "Show all previews", new PersistentProperty(false)), booleanType,
        "Show all previews for debugging?");

    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_X_POS, integerType,
        "The initial x position of the window on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_Y_POS, integerType,
        "The initial y position of the window on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_WIDTH, integerType,
        "The initial width of the window on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_HEIGHT, integerType,
        "The initial height of the window on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_BROWSER_X_POS, integerType,
        "The initial x position of the browser on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_BROWSER_Y_POS, integerType,
        "The initial y position of the browser on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_BROWSER_WIDTH, integerType,
        "The initial width of the browser on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_BROWSER_HEIGHT, integerType,
        "The initial height of the browser on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_EDITOR_X_POS, integerType,
        "The initial x position of the editor on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_EDITOR_Y_POS, integerType,
        "The initial y position of the editor on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_EDITOR_WIDTH, integerType,
        "The initial width of the editor on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_EDITOR_HEIGHT, integerType,
        "The initial height of the editor on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_RUNNER_X_POS, integerType,
        "The initial x position of the runner on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_RUNNER_Y_POS, integerType,
        "The initial y position of the runner on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_RUNNER_WIDTH, integerType,
        "The initial width of the runner on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_RUNNER_HEIGHT, integerType,
        "The initial height of the runner on the screen.");
    GlobalPreferences.preferences.addPreferenceSlot(INITIAL_PALETTE_WIDTH, integerType,
        "The width of the palette.  Limited to between 50 and 500 pixels.");
  }

  public void interpretPreferences()
  {
    // do we want anti-aliasing?
    RegisteredGraphicalThemes.getInstance().registerPreferenceSlots();
    String antiAliasing = GlobalPreferences.preferences.getRawPreference(new Preference("Appearance", "Font antialiasing")).asString();
    if (antiAliasing != null)
      System.setProperty("awt.useSystemAAFontSettings", antiAliasing);

    // should we use opengl?
    System.setProperty("sun.java2d.opengl", GlobalPreferences.preferences.getRawPreference(
        new Preference("Appearance", "Application uses OpenGL for Java2D")).asBoolean() ? "true" : "false");
  }
  
  public static void determineNativeTitleBars()
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JDialog.setDefaultLookAndFeelDecorated(false);
  }
}
