package org.freehep.graphicsio.cgm.test;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.freehep.graphicsio.cgm.*;

public class PolylineTest
{

  public static void main(String[] args) throws IOException
  {

    Point2D p[] = {new Point2D.Double(2000, 2000), new Point2D.Double(4000, 6000), new Point2D.Double(3000, 5000),
        new Point2D.Double(0000, 6000)};

    Vector cgm = new Vector();
    cgm.add(new BeginMetafile("POLYLINE"));
    cgm.add(new MetafileVersion(1));
    cgm.add(new MetafileDescription("FreeHEP/VG-CGM-1.0"));
    cgm.add(new MetafileElementList(MetafileElementList.DRAWING_PLUS_SET));
    cgm.add(new BeginPicture("POLYLINE"));
    cgm.add(new ColorSelectionMode(ColorSelectionMode.DIRECT));
    cgm.add(new LineWidthSpecificationMode(LineWidthSpecificationMode.ABSOLUTE));
    cgm.add(new VDCExtent(new Point2D.Double(0, 8000), new Point2D.Double(8000, 0)));
    cgm.add(new BeginPictureBody());
    cgm.add(new LineColor(Color.black));
    cgm.add(new LineType(LineType.DASH));
    cgm.add(new LineWidth(60));
    cgm.add(new Polyline(p));
    cgm.add(new EndPicture());
    cgm.add(new EndMetafile());

    CGMOutputStream out = new CGMOutputStream(new FileOutputStream("PolylineTest.cgm"));
    for (int i = 0; i < cgm.size(); i++)
    {
      out.writeTag((CGMTag) cgm.get(i));
    }
    out.close();
  }
}