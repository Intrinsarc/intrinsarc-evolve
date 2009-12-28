package com.hopstepjump.idraw.utility;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;

import com.hopstepjump.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public final class CrossHair
{
	private static final int LENGTH = 400;
  private UPoint centre;

  public CrossHair(UPoint centre)
  {
    this.centre = centre;
  }

  public void setCentre(UPoint centre)
  {
    this.centre = centre;
  }

  public UPoint getCentre()
  {
    return centre;
  }

  public ZNode formView()
  {
    ZGroup group = new ZGroup();

    // add cross hairs
    Color veryLight = ScreenProperties.getLightCrossHairColor();
    ZLine lineX = new ZLine(centre.add(new UDimension(-LENGTH, 0)), centre.add(new UDimension(LENGTH, 0)));
    lineX.setPenPaint(veryLight);
    group.addChild(new ZVisualLeaf(lineX));

    ZLine lineY = new ZLine(centre.add(new UDimension(0, -LENGTH)), centre.add(new UDimension(0, LENGTH)));
    lineY.setPenPaint(veryLight);
    group.addChild(new ZVisualLeaf(lineY));

    return group;
  }
}