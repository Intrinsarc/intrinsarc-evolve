package org.freehep.graphicsio.cgm.test;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.freehep.graphicsio.cgm.*;
import org.freehep.graphicsio.cgm.Polygon;

public class PolygonTest
{

  public static void main(String[] args) throws IOException
  {

    Point2D p[] = {new Point2D.Double(4000, 5000), new Point2D.Double(16000, 17000), new Point2D.Double(8000, 3000)};

    Vector cgm = new Vector();
    cgm.add(new BeginMetafile("POLYGON"));
    cgm.add(new MetafileVersion(1));
    cgm.add(new MetafileDescription("FreeHEP/VG-CGM-1.0"));
    cgm.add(new MetafileElementList(MetafileElementList.DRAWING_PLUS_SET));
    cgm.add(new BeginPicture("POLYGON"));
    cgm.add(new ColorSelectionMode(ColorSelectionMode.DIRECT));
    cgm.add(new EdgeWidthSpecificationMode(EdgeWidthSpecificationMode.ABSOLUTE));
    cgm.add(new VDCExtent(new Point2D.Double(0, 20000), new Point2D.Double(20000, 0)));
    cgm.add(new BeginPictureBody());
    cgm.add(new EdgeVisibility(true));
    cgm.add(new EdgeColor(Color.red));
    cgm.add(new FillColor(Color.black));
    cgm.add(new EdgeType(EdgeType.SOLID));
    cgm.add(new EdgeWidth(60));
    cgm.add(new InteriorStyle(InteriorStyle.SOLID));
    cgm.add(new Polygon(p));
    cgm.add(new EndPicture());
    cgm.add(new EndMetafile());

    CGMOutputStream out = new CGMOutputStream(new FileOutputStream("PolygonTest.cgm"));
    for (int i = 0; i < cgm.size(); i++)
    {
      out.writeTag((CGMTag) cgm.get(i));
    }
    out.close();
  }
}