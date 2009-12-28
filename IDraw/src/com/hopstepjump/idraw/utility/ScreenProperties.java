package com.hopstepjump.idraw.utility;

import java.awt.*;

import javax.swing.*;

/**
 *
 * (c) Andrew McVeigh 14-Oct-02
 *
 */
public class ScreenProperties
{
  private static final String FONT_NAME = "Helvetica";
	private static final Color TRANSPARENT_COLOR = new Color(0,0,0,0);
	private static final Color FIRST_SELECTED_HIGHLIGHT_COLOR = Color.ORANGE;
// private static final Color highlightColor = UIManager.getColor("textHighlight");
  private static final Color PREVIEW_COLOR = Color.BLACK;
	private static final Color LIGHT_CROSSHAIR_COLOR = new Color(150, 150, 150, 75);
	private static final Color LIGHT_PREVIEW_FILL_COLOR = new Color(230, 230, 230, 100);
	private static final Stroke DASHED_STROKE = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0);
	private static final Font PRIMARY_FONT = new Font(FONT_NAME, Font.PLAIN, 12);
  private static final Font TITLE_FONT = new Font(FONT_NAME, Font.PLAIN, 12);
  private static final Font BOLD_TITLE_FONT = new Font(FONT_NAME, Font.BOLD, 12);
  private static final Font SECONDARY_FONT = new Font(FONT_NAME, Font.PLAIN, 10);
  private static Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;

  public static Font getTitleFont()
  {
    return TITLE_FONT;
  }
  
  public static Font getPrimaryFont()
  {
    return PRIMARY_FONT;
  }
  
  public static Font getSecondaryFont()
  {
    return SECONDARY_FONT;
  }
  
	public static final Color getHighlightColor()
	{
		return HIGHLIGHT_COLOR;
	}

	public static final Color getFirstSelectedHighlightColor()
  {
    return FIRST_SELECTED_HIGHLIGHT_COLOR;
  }
	
	public static void setHighlightColor(Color color)
	{
		HIGHLIGHT_COLOR = color;
	}

	public static final Color getUndoPopupColor()
	{
		return UIManager.getColor("windowBorder");
	}
	
	public static final Color getCrossHairColor()
	{
		return Color.darkGray;
	}
	
	/**
	 * @return
	 */
	public static Color getTransparentColor()
	{
		return TRANSPARENT_COLOR;
	}
	
	public static Color getLightCrossHairColor()
	{
		return LIGHT_CROSSHAIR_COLOR;
	}
	
	/**
	 * @return
	 */
	public static Stroke getDashedStroke()
	{
		return DASHED_STROKE;
	}

  /**
   * @return
   */
  public static Paint getPreviewFillColor()
  {
    return LIGHT_PREVIEW_FILL_COLOR;
  }

  /**
   * @return
   */
  public static Color getPreviewColor()
  {
    return PREVIEW_COLOR;
  }

  public static Font getBoldTitleFont()
  {
    return BOLD_TITLE_FONT;
  }
}
