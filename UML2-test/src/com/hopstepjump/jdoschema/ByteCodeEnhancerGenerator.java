package com.hopstepjump.jdoschema;

import java.io.*;

public class ByteCodeEnhancerGenerator
{
  public static void main(String args[]) throws IOException
  {
    PrintStream out = new PrintStream("enhanced.txt");
    
    new Generator().generate(new ByteCodeEnhancerSwitcher(), out);
    
    out.close();
  }
}


