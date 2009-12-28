// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The ASCII85InputStream encodes binary data as ASCII base-85 encoding. The
 * exact definition of ASCII base-85 encoding can be found in the PostScript
 * Language Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 * @version $Id: ASCII85OutputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class ASCII85OutputStream extends FilterOutputStream implements ASCII85, FinishableOutputStream
{

  private boolean end;
  private int characters;
  private int b[] = new int[4];
  private int bIndex;
  private int c[] = new int[5];

  public ASCII85OutputStream(OutputStream out)
  {
    super(out);
    characters = MAX_CHARS_PER_LINE;
    end = false;
    bIndex = 0;
  }

  public void write(int a) throws IOException
  {
    b[bIndex] = a & 0x00FF;
    bIndex++;
    if (bIndex >= b.length)
    {
      writeTuple();
      bIndex = 0;
    }
  }

  public void finish() throws IOException
  {
    if (!end)
    {
      end = true;
      if (bIndex > 0)
      {
        writeTuple();
      }
      writeChar('~');
      writeChar('>');
      flush();
      if (out instanceof FinishableOutputStream)
      {
        ((FinishableOutputStream) out).finish();
      }
    }
  }

  public void close() throws IOException
  {
    finish();
    super.close();
  }

  private void writeTuple() throws IOException
  {
    // fill the rest
    for (int i = bIndex; i < b.length; i++)
    {
      b[i] = 0;
    }

    // convert
    long d = ((b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3]) & 0x00000000FFFFFFFFL;

    c[0] = (int) (d / a85p4 + '!');
    d = d % a85p4;
    c[1] = (int) (d / a85p3 + '!');
    d = d % a85p3;
    c[2] = (int) (d / a85p2 + '!');
    d = d % a85p2;
    c[3] = (int) (d / a85p1 + '!');
    c[4] = (int) (d % a85p1 + '!');

    // convert !!!!! to z
    if ((bIndex >= b.length) && (c[0] == '!') && (c[1] == '!') && (c[2] == '!') && (c[3] == '!') && (c[4] == '!'))
    {
      writeChar('z');
    }
    else
    {
      for (int i = 0; i < bIndex + 1; i++)
      {
        writeChar(c[i]);
      }
    }
  }

  private void writeChar(int b) throws IOException
  {
    if (characters == 0)
    {
      characters = MAX_CHARS_PER_LINE;
      super.write('\n');
    }
    characters--;
    super.write(b);
  }
}
