package org.freehep.graphicsio.cgm.test;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.freehep.graphicsio.cgm.*;

public class EllipseTest
{

  public static void main(String[] args) throws IOException
  {

    Vector cgm = new Vector();
    cgm.add(new BeginMetafile("ELLIPSE"));
    cgm.add(new MetafileVersion(1));
    cgm.add(new MetafileDescription("FreeHEP/VG-CGM-1.0"));
    cgm.add(new MetafileElementList(MetafileElementList.DRAWING_PLUS_SET));
    cgm.add(new BeginPicture("ELLIPSE"));
    cgm.add(new ColorSelectionMode(ColorSelectionMode.DIRECT));
    cgm.add(new EdgeWidthSpecificationMode(EdgeWidthSpecificationMode.ABSOLUTE));
    cgm.add(new VDCExtent(new Point2D.Double(0, 4000), new Point2D.Double(4000, 0)));
    cgm.add(new BeginPictureBody());
    cgm.add(new EdgeColor(Color.red));
    cgm.add(new EdgeVisibility(true));
    cgm.add(new EdgeWidth(4));
    cgm.add(new EdgeType(EdgeType.SOLID));
    cgm.add(new InteriorStyle(InteriorStyle.EMPTY));
    cgm
        .add(new Ellipse(new Point2D.Double(1000, 2000), new Point2D.Double(2000, 2000), new Point2D.Double(1000, 3000)));
    cgm.add(new EndPicture());
    cgm.add(new EndMetafile());

    CGMOutputStream out = new CGMOutputStream(new FileOutputStream("EllipseTest.cgm"));
    for (int i = 0; i < cgm.size(); i++)
    {
      out.writeTag((CGMTag) cgm.get(i));
    }
    out.close();
  }
}
