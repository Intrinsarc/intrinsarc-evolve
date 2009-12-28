// Copyright 2002, SLAC, Stanford University, U.S.A.
package org.freehep.graphicsio.test;

import javax.swing.*;
import javax.swing.text.html.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: TestHTML.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestHTML extends TestingPanel
{

  private String text;

  public TestHTML(String[] args) throws Exception
  {
    super(args);
    setName("HTML");
    text = "&lt;Vector<sup><b>Graphics</b></sup> &amp; Adapter<i><sub>Card</sub></i> "
        + "= e<sup>x<sup>2</sup>y<sup>3</sup></sup>&gt;";

    JEditorPane pane = new JEditorPane();
    pane.setContentType("text/html");
    pane.setEditorKit(new HTMLEditorKit());
    pane.setText(text);
    pane.setEditable(false);
    add(pane);
  }

  public static void main(String[] args) throws Exception
  {
    new TestHTML(args).runTest();
  }
}
