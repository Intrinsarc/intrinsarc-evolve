package com.intrinsarc.jdoschema;

import java.io.*;

public class JDOMetaDataGenerator
{
  public static void main(String args[]) throws IOException
  {
    PrintStream out = new PrintStream("package.jdo");

    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    out.println("<!DOCTYPE jdo SYSTEM \"http://java.sun.com/dtd/jdo_1_0.dtd\">");
    out.println();
    out.println("<jdo>");
    out.println("    <package name=\"\">");
    out.println();
    new Generator().generate(new JDOMetaDataSwitcher(), out);
    out.println();
    out.println("    </package>");
    out.println("</jdo>");    

    out.close();
  }
}
