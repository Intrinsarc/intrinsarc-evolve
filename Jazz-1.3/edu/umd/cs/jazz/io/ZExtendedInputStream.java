/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.io;

import java.io.*;

/**
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 */

public class ZExtendedInputStream implements Serializable {
    protected InputStream stream; 
    protected long filePosition = 0;
    
    
    public ZExtendedInputStream(InputStream stream) {
	this.stream = stream;
    }

    public void setFilePosition(long n) throws IOException {
	long tmp = n - filePosition;
	stream.skip(tmp);
	filePosition += tmp;
    }

    public long  getFilePosition() {
	return filePosition;
    }

    public long skip(long n) throws IOException {
	long result = stream.skip(n);
	filePosition += n;

	return result;
    }

    public int read() throws IOException {
	int result = stream.read();
	filePosition ++;

	return result;
    }
    
    public int read(byte b[]) throws IOException {
	int result = stream.read(b);
	filePosition += result;

	return result;
    }
    
    public int read(byte b[], int off, int len) throws IOException {
    int result = stream.read(b, off, len);
	filePosition += result;

	return result;
    }
    
    public int available() throws IOException {
	return stream.available();
    }
    
    public void close() throws IOException {
	stream.close();
    }
    public synchronized void mark(int readlimit) {
	stream.mark(readlimit);
    }
    public synchronized void reset() throws IOException {
	stream.reset();
    }
    public boolean markSupported() {
	return stream.markSupported();
    }

    static public final void main(String[] args) {
	try {
	    java.io.FileInputStream f = new java.io.FileInputStream("test2.jazz");
	    ZExtendedInputStream s = new ZExtendedInputStream(f);
	    
	    s.setFilePosition(807);
	    byte[] buf = new byte[16];
	    s.read(buf, 0, 16);
	    System.out.println(new String(buf));
	    
	}
	catch (Exception e) {
	    System.out.println(e);
	    
	}
	    
    }
}

