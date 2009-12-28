// Copyright 2003, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import javax.xml.parsers.*;

import org.freehep.util.io.*;
import org.xml.sax.*;

/**
 * @author Mark Donszelmann
 * @version $Id: XMLSequenceTest.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class XMLSequenceTest
{

  public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
  {

    if (args.length != 1)
    {
      System.out.println("Usage: XMLSequenceInputStreamTest filename");
      System.exit(1);
    }
    File file = new File(args[0]);
    XMLSequence sequence = new XMLSequence(new BufferedInputStream(new FileInputStream(file)));
    if (sequence.markSupported())
    {
      sequence.mark((int) file.length());
    }

    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    XMLReader xmlReader = factory.newSAXParser().getXMLReader();

    int i = 0;
    while (sequence.hasNext())
    {
      InputStream input = sequence.next();
      InputSource source = new InputSource(input);
      xmlReader.parse(source);
      input.close();
      i++;
      System.out.println("Parsed XML section: " + i);
    }

    // try reset...
    if (sequence.markSupported())
    {
      System.out.println("Reset file");
      sequence.reset();
      i = 0;
      while (sequence.hasNext())
      {
        InputStream input = sequence.next();
        InputSource source = new InputSource(input);
        xmlReader.parse(source);
        input.close();
        i++;
        System.out.println("Parsed XML section: " + i);
      }
    }
    else
    {
      System.out.println("Reset not supported");
    }
    sequence.close();
  }
}
