// Copyright 2001-2003, FreeHEP
package org.freehep.graphicsio.pdf;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import org.freehep.graphicsio.*;

/**
 * Delay <tt>Image</tt> objects for writing XObjects to the pdf file when the
 * pageStream is complete.
 * 
 * @author Simon Fischer
 * @version $Id: PDFImageDelayQueue.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class PDFImageDelayQueue
{

  private int currentNumber = 0;

  private class Entry
  {
    private RenderedImage image;
    private String name, maskName;
    private Color bkg;
    private String writeAs;
    private boolean written;
    private Entry(RenderedImage image, Color bkg, String writeAs)
    {
      this.image = image;
      this.bkg = bkg;
      this.writeAs = writeAs;
      this.name = "Img" + (currentNumber++);
      if (image.getColorModel().hasAlpha() && (bkg == null))
      {
        maskName = name + "Mask";
      }
      else
      {
        maskName = null;
      }
      this.written = false;
    }
  }

  private List imageList;
  private PDFWriter pdf;

  public PDFImageDelayQueue(PDFWriter pdf)
  {
    this.pdf = pdf;
    this.imageList = new LinkedList();
  }

  public PDFName delayImage(RenderedImage image, Color bkg, String writeAs)
  {
    Entry e = new Entry(image, bkg, writeAs);
    imageList.add(e);
    return pdf.name(e.name);
  }

  /** Creates a stream for every delayed image that is not written yet. */
  public void processAll() throws IOException
  {
    ListIterator i = imageList.listIterator();
    while (i.hasNext())
    {
      Entry e = (Entry) i.next();

      if (!e.written)
      {
        e.written = true;

        String[] encode;
        if (e.writeAs.equals(ImageConstants.ZLIB) || (e.maskName != null))
        {
          encode = new String[]{"Flate", "ASCII85"};
        }
        else
          if (e.writeAs.equals(ImageConstants.JPG))
          {
            encode = new String[]{"DCT", "ASCII85"};
          }
          else
          {
            encode = new String[]{null, "ASCII85"};
          }

        PDFStream img = pdf.openStream(e.name);
        img.entry("Subtype", pdf.name("Image"));
        if (e.maskName != null)
          img.entry("SMask", pdf.ref(e.maskName));
        img.image(e.image, e.bkg, encode);
        pdf.close(img);

        if (e.maskName != null)
        {
          PDFStream mask = pdf.openStream(e.maskName);
          mask.entry("Subtype", pdf.name("Image"));
          mask.imageMask(e.image, encode);
          pdf.close(mask);
        }
      }
    }
  }

  /**
   * Adds all names to the dictionary which should be the value of the resources
   * dicionrary's /XObject entry.
   */
  public int addXObjects() throws IOException
  {
    if (imageList.size() > 0)
    {
      PDFDictionary xobj = pdf.openDictionary("XObjects");
      ListIterator i = imageList.listIterator();
      while (i.hasNext())
      {
        Entry e = (Entry) i.next();
        xobj.entry(e.name, pdf.ref(e.name));
        if (e.maskName != null)
          xobj.entry(e.maskName, pdf.ref(e.maskName));
      }
      pdf.close(xobj);
    }
    return imageList.size();
  }
}
