package com.hopstepjump.idraw.utility;

import java.awt.*;

import com.hopstepjump.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class FancyPolygonMaker
{
	private UPoint line[];
	private Color primary;
	private boolean gradient;
	
	public FancyPolygonMaker(UPoint line[], Color primary, boolean gradient)
	{
		this.line = line;
		this.primary = primary;
		this.gradient = gradient;
	}
	
	public ZGroup make()
	{
		ZGroup rect = new ZGroup();
		{
			ZPolygon rc = makeQuadLine(0);
	    rc.setFillPaint(Color.WHITE);
	    rc.setPenPaint(null);
	    rect.addChild(new ZVisualLeaf(rc));
		}
	
		// ring around
		{
			ZPolygon rc = makeQuadLine(0);
	    rc.setFillPaint(null);
	    rc.setPenPaint(primary.darker());
	    rc.setPenWidth(3);
	    rect.addChild(new ZVisualLeaf(rc));
		}
	
		// gradient
		{
			ZPolygon rc = makeQuadLine(0);
			UBounds bounds = new UBounds(rc.getBounds());
	    if (gradient)
	    	rc.setFillPaint(new GradientPaint(bounds.getPoint(), primary, bounds.getBottomLeftPoint(), primary.brighter().brighter()));
	    else
	    	rc.setFillPaint(primary);
	    rc.setPenPaint(null);
	    rect.addChild(new ZVisualLeaf(rc));
		}
	
		return rect;
	}

	private ZPolygon makeQuadLine(int offset)
	{
		ZPolygon qpl = new ZPolygon(line[0]);
		for (int lp = 1; lp < line.length; lp++)
			qpl.add(line[lp].add(new UDimension(offset, offset)));
		return qpl;
	}
}
