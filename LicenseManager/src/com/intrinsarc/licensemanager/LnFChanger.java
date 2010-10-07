package com.intrinsarc.licensemanager;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.luna.*;

public class LnFChanger
{
  public static final String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";
  public static final Font DEFAULT_FONT = new Font("Arial", 12, Font.PLAIN);

	public static void changeLookAndFeel() throws Exception
	{
		UIManager.setLookAndFeel(LunaLookAndFeel.class.getName());
		// change the look and feel
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
    Properties props = new Properties();
    props.put("logoString", "");
    props.put("licenseKey", LICENSE_KEY);
   
    // change the fonts
    Object fs = fontString(DEFAULT_FONT, 0);
    props.put("controlTextFont", fs);
    props.put("labelTextFont", fs);
    props.put("systemTextFont", fs);
    props.put("userTextFont", fs);
    props.put("menuTextFont", fs);
    props.put("windowTitleFont", fontString(DEFAULT_FONT.deriveFont(Font.BOLD), 0));
    props.put("subTextFont", fontString(DEFAULT_FONT, 2));
    BaseTheme.setProperties(props);
	}

  private static Object fontString(Font font, int subtractSize)
  {
    return
      font.getName() + " " +
      (font.isBold() ? "bold " : "") + (font.isItalic() ? "italic " : "") + (font.getSize() - subtractSize);
  }
}
