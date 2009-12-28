/*
 * Created on Jan 1, 2004 by Andrew McVeigh
 */
package com.hopstepjump.layout;

import java.awt.*;
import java.util.*;
import java.util.List;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 */
public abstract class TextUtilities
{
	public static ZTransformGroup makeCentredZTexts(String text, Font font)
	{
		// make a ZText for each line
		ZText lines[] = makeLines(text, font);

		// work out the maximum width
		double maxWidth = 0;
		for (int lp = 0; lp < lines.length; lp++)
			maxWidth = Math.max(lines[lp].getBounds().getWidth(), maxWidth);

		// adjust the translation of each ztext to compensate
		ZTransformGroup transform = new ZTransformGroup();
		int height = 1;
		for (int lp = 0; lp < lines.length; lp++)
		{
			lines[lp].setTranslation((maxWidth - lines[lp].getBounds().getWidth()) / 2, height);
			transform.addChild(new ZVisualLeaf(lines[lp]));
			height += lines[lp].getBounds().getHeight() + 1;
		}

		return transform;
	}

	public static ZText[] makeLines(String text, Font font)
	{
		List<ZText> lines = new ArrayList<ZText>();
		try
		{
			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.StringReader(text));
			for (int lp = 0;; lp++)
			{
				String line = reader.readLine();
				if (line == null)
					break;
				ZText ztext = new ZText(line);
				if (font != null)
					ztext.setFont(font);
				lines.add(ztext);
			}
		}
		catch (java.io.IOException ex)
		{
			// should never happen with a string
		}
		return lines.toArray(new ZText[0]);
	}
}
