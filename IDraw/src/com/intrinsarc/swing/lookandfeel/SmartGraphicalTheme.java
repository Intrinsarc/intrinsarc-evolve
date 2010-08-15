package com.intrinsarc.swing.lookandfeel;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.swing.*;
import com.jtattoo.plaf.*;
import com.jtattoo.plaf.smart.*;

public class SmartGraphicalTheme implements GraphicalTheme
{
  public static String THEME_NAME = "Smart";
  public static String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";
  public static Font DEFAULT_FONT = new Font("Arial", 12, Font.PLAIN);
  public static final Preference JTATTOO_LNF_FONT_PREFERENCE = new Preference("Appearance", "Font for JTattoo look and feel", new PersistentProperty(DEFAULT_FONT));

  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = setOptions(subtheme);
    changeWindowIcons();

    // set your theme
    SmartLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(SmartLookAndFeel.class.getName());
  }

  public static Properties setOptions(String subtheme)
  {
    Properties props = new Properties();
    props.put("logoString", "");
    props.put("licenseKey", SmartGraphicalTheme.LICENSE_KEY);
   
    if (subtheme != null && !subtheme.toLowerCase().contains("font"))
    {
      // see if we can get the font first
      Font font = GlobalPreferences.preferences.getRawPreference(
          SmartGraphicalTheme.JTATTOO_LNF_FONT_PREFERENCE).asFont();
      
      // change the fonts
      props.put("controlTextFont", fontString(font, 0));
      props.put("labelTextFont", fontString(font, 0));
      props.put("systemTextFont", fontString(font, 0));
      props.put("userTextFont", fontString(font, 0));
      props.put("menuTextFont", fontString(font, 0));
      props.put("windowTitleFont", fontString(font.deriveFont(Font.BOLD), 0));
      props.put("subTextFont", fontString(font, 2));
    }
    
    return props;
  }

  private static Object fontString(Font font, int subtractSize)
  {
    return
      font.getName() + " " +
      (font.isBold() ? "bold " : "") + (font.isItalic() ? "italic " : "") + (font.getSize() - subtractSize);
  }

  public static void changeWindowIcons()
  {
    UIManager.put("InternalFrame.iconifyIcon", loadIcon("jtb-minimize.png"));
    UIManager.put("InternalFrame.maximizeIcon", loadIcon("jtb-maximize.png"));
    UIManager.put("InternalFrame.minimizeIcon", loadIcon("jtb-restore.png"));
    UIManager.put("InternalFrame.closeIcon", loadIcon("jtb-close.png"));
  }

  private static ImageIcon loadIcon(String iconName)
  {
    return IconLoader.loadIcon(iconName);
  }

  public String getName()
  {
    return THEME_NAME;
  }

  public List<String> getSubthemes()
  {
    return SmartLookAndFeel.getThemes();
  }

  public void setProgressBarUI(JProgressBar progress)
  {
  	fixProgressBarUI(progress);
  }
  
	public static void fixProgressBarUI(JProgressBar progress)
	{
		progress.setUI(new BaseProgressBarUI()
		{
		  protected void paintDeterminate(Graphics g, JComponent jcomponent)
		  {
		  		// copied from JTatto, but overridden to never look disabled or inactive
		      if(!(g instanceof Graphics2D))
		      	return;
		      Graphics2D graphics2d = (Graphics2D)g;
		      Insets insets = progressBar.getInsets();
		      int i = progressBar.getWidth() - (insets.right + insets.left);
		      int j = progressBar.getHeight() - (insets.top + insets.bottom);
		      int k = getAmountFull(insets, i, j);
		      java.awt.Color acolor[] = null;
//		      if(!JTattooUtilities.isActive(jcomponent))
//		          acolor = AbstractLookAndFeel.getTheme().getInActiveColors();
//		      else
//		      if(jcomponent.isEnabled())
		          acolor = AbstractLookAndFeel.getTheme().getProgressBarColors();
//		      else
//		          acolor = AbstractLookAndFeel.getTheme().getDisabledColors();
		      java.awt.Color color = ColorHelper.darker(acolor[acolor.length - 1], 5D);
		      java.awt.Color color1 = ColorHelper.darker(acolor[acolor.length - 1], 10D);
		      if(progressBar.getOrientation() == 0)
		      {
		          if(JTattooUtilities.isLeftToRight(progressBar))
		          {
		              JTattooUtilities.draw3DBorder(g, color, color1, 2, 2, k - 2, j - 2);
		              JTattooUtilities.fillHorGradient(g, acolor, 3, 3, k - 4, j - 4);
		          } else
		          {
		              JTattooUtilities.draw3DBorder(g, color, color1, (i - k) + 2, 2, i - 2, j - 2);
		              JTattooUtilities.fillHorGradient(g, acolor, (i - k) + 3, 3, i - 4, j - 4);
		          }
		      } else
		      {
		          JTattooUtilities.draw3DBorder(g, color, color1, 2, 2, i - 2, k - 2);
		          JTattooUtilities.fillVerGradient(g, acolor, 3, 3, i - 4, k - 4);
		      }
		      if(progressBar.isStringPainted())
		      {
		          Object obj = null;
		          if(AbstractLookAndFeel.getTheme().isTextAntiAliasingOn())
		          {
		              obj = graphics2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
		              graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		          }
		          paintString(g, insets.left, insets.top, i, j, k, insets);
		          if(AbstractLookAndFeel.getTheme().isTextAntiAliasingOn())
		              graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, obj);
		      }
		  }
		});
	}

	public boolean drawsBoxAroundTextArea()
	{
		return false;
	}
}