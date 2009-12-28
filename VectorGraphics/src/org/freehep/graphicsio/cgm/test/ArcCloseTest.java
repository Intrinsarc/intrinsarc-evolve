package org.freehep.graphicsio.cgm.test;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.freehep.graphicsio.cgm.*;

public class ArcCloseTest
{

  public static void main(String[] args) throws IOException
  {

    Vector cgm = new Vector();
    cgm.add(new BeginMetafile("ARCCLOSE"));
    cgm.add(new MetafileVersion(1));
    cgm.add(new MetafileDescription("FreeHEP/VG-CGM-1.0"));
    cgm.add(new MetafileElementList(MetafileElementList.DRAWING_PLUS_SET));
    cgm.add(new BeginPicture("ARCCLOSE"));
    cgm.add(new ColorSelectionMode(ColorSelectionMode.DIRECT));
    cgm.add(new EdgeWidthSpecificationMode(EdgeWidthSpecificationMode.SCALED));
    cgm.add(new VDCExtent(new Point2D.Double(0, 3000), new Point2D.Double(3000, 0)));
    cgm.add(new BeginPictureBody());
    cgm.add(new InteriorStyle(InteriorStyle.SOLID));
    cgm.add(new FillColor(Color.red));
    cgm.add(new EdgeVisibility(true));
    cgm.add(new EdgeWidth(6));
    cgm.add(new EllipticalArcClose(new Point2D.Double(1000, 1000), new Point2D.Double(1000, 500), new Point2D.Double(
        2000, 1000), new Point2D.Double(1500, 5000), new Point2D.Double(500, 500), EllipticalArcClose.PIE));
    cgm.add(new EndPicture());
    cgm.add(new EndMetafile());

    CGMOutputStream out = new CGMOutputStream(new FileOutputStream("ArcCloseTest.cgm"));
    for (int i = 0; i < cgm.size(); i++)
    {
      out.writeTag((CGMTag) cgm.get(i));
    }
    out.close();
  }
}
