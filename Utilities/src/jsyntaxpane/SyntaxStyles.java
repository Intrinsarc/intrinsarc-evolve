/*jadclipse*/// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
package jsyntaxpane;

import java.awt.*;
import java.util.*;

// Referenced classes of package jsyntaxpane:
//            SyntaxStyle, TokenType

public class SyntaxStyles
{

	private SyntaxStyles()
	{
	}

	private static SyntaxStyles createInstance()
	{
		SyntaxStyles s = new SyntaxStyles();
		s.add("OPER", new SyntaxStyle(Color.BLACK, false, false));
		s.add("KEYWORD", new SyntaxStyle(Color.BLUE, false, false));
		s.add("IDENT", new SyntaxStyle(Color.BLACK, false, false));
		s.add("REGEX", new SyntaxStyle(Color.ORANGE.darker(), false, false));

		s.add("STRING", new SyntaxStyle(/*Color.BLUE*/new Color(42, 43, 255), false, false));
		s.add("TYPE", new SyntaxStyle(Color.MAGENTA.darker(), false, false));
		s.add("NUMBER", new SyntaxStyle(new Color(10066227), true, false));
		s.add("COMMENT", new SyntaxStyle(/*Color.GREEN*/new Color(63, 127, 95), false, false));
		return s;
	}

	public static SyntaxStyles getInstance()
	{
		return instance;
	}

	public void add(String name, SyntaxStyle style)
	{
		if (styles == null)
			styles = new HashMap();
		styles.put(name, style);
	}

	public void setGraphicsStyle(Graphics g, TokenType type)
	{
		Graphics2D g2d = (Graphics2D) g;
		SyntaxStyle ss = (SyntaxStyle) styles.get(type.toString());
		if (ss != null)
		{
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(ss.getColor());
			if (ss.isItalic() && ss.isBold())
				g.setFont(g.getFont().deriveFont(Font.ITALIC | Font.BOLD));
			else
			if (ss.isItalic())
				g.setFont(g.getFont().deriveFont(Font.ITALIC));
			else
			if (ss.isBold())
				g.setFont(g.getFont().deriveFont(Font.BOLD));
			else
				g.setFont(g.getFont().deriveFont(Font.PLAIN));
		}
		else
		{
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
	}

	Map styles;
	private static SyntaxStyles instance = createInstance();

}
