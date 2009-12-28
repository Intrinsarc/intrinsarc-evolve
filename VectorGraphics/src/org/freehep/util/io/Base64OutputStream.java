// Copyright 2003, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The Base64OutputStream encodes binary data according to RFC 2045.
 * 
 * @author Mark Donszelmann
 * @version $Id: Base64OutputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class Base64OutputStream extends FilterOutputStream implements FinishableOutputStream
{

  private int MAX_LINE_LENGTH = 74;
  private int position;
  private byte[] buffer;
  private int bufferLength;
  private int lineLength;
  private static final char intToBase64[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0 -
                                                                                      // 7
      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 8 - 15
      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 16 - 23
      'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 24 - 31
      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 32 - 39
      'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 40 - 47
      'w', 'x', 'y', 'z', '0', '1', '2', '3', // 48 - 55
      '4', '5', '6', '7', '8', '9', '+', '/' // 56 - 63
  };

  public Base64OutputStream(OutputStream out)
  {
    super(out);
    buffer = new byte[3];
    position = 0;
    lineLength = 0;
  }

  public void write(int a) throws IOException
  {
    buffer[position++] = (byte) a;
    if (position < buffer.length)
      return;

    // System.out.print(Integer.toHexString(buffer[0])+",
    // "+Integer.toHexString(buffer[1])+", "+Integer.toHexString(buffer[2])+"
    // ");
    writeTuple();
    // out.write('\n');

    lineLength += 4;
    if (lineLength >= MAX_LINE_LENGTH)
    {
      out.write('\n');
      lineLength = 0;
    }

    position = 0;
  }

  public void finish() throws IOException
  {
    writeTuple();
    flush();
    if (out instanceof FinishableOutputStream)
    {
      ((FinishableOutputStream) out).finish();
    }
  }

  public void close() throws IOException
  {
    finish();
    super.close();
  }

  private void writeTuple() throws IOException
  {
    int data = (position > 0 ? (buffer[0] << 16) & 0x00FF0000 : 0) | (position > 1 ? (buffer[1] << 8) & 0x0000FF00 : 0)
        | (position > 2 ? (buffer[2]) & 0x000000FF : 0);

    // System.out.println(Integer.toHexString(data));
    switch (position)
    {
      case 3 :
        // System.out.println("\n*** "+((data >> 18) & 0x3f) +", "+((data >> 12)
        // & 0x3f)+", "+
        // ((data >> 6) & 0x3f) +", "+((data ) & 0x3f));
        out.write(intToBase64[(data >> 18) & 0x3f]);
        out.write(intToBase64[(data >> 12) & 0x3f]);
        out.write(intToBase64[(data >> 6) & 0x3f]);
        out.write(intToBase64[(data) & 0x3f]);
        return;

      case 2 :
        out.write(intToBase64[(data >> 18) & 0x3f]);
        out.write(intToBase64[(data >> 12) & 0x3f]);
        out.write(intToBase64[(data >> 6) & 0x3f]);
        out.write('=');
        return;

      case 1 :
        out.write(intToBase64[(data >> 18) & 0x3f]);
        out.write(intToBase64[(data >> 12) & 0x3f]);
        out.write('=');
        out.write('=');
        return;

      default :
        return;
    }
  }

}
