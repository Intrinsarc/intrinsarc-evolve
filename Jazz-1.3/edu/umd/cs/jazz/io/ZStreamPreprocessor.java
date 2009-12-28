/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.io;

import java.io.*;

/** 
 * <b>ZStreamPreprocessor</b> preprocesses the java io stream to manage embedded binary data.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @author Britt McAlister
 */
public class ZStreamPreprocessor implements Serializable {
    protected ZExtendedInputStream data;
    protected StringBuffer out;
    protected int bufSize = 64;
    protected String binaryMarker = "BINARYDATAFOLLOWS";
    protected int bmLength = binaryMarker.length();
    protected byte[] bmBytes = ("\t" + binaryMarker + " ").getBytes();
    protected byte[] buffer = new byte[bufSize];

    
    public ZStreamPreprocessor(ZExtendedInputStream data) {
	this.data = data;
    }

    
    protected boolean isWhiteSpace(byte b) {
	if ((b == ' ')  ||
	    (b == '\t') ||
	    (b == '\f') ||
	    (b == '\r') ||
	    (b == '\n') ||
	    (b == '\t')) {
	    
	    return true;
	} else {
	    return false;
	}

    }
	
    protected boolean isEOL(byte b) {
	if ((b == '\r') ||
	    (b == '\n') ) {
	    
	    return true;
	} else {
	    return false;
	}
    }
    
    
    protected void handleBinaryData(OutputStream captureBuf, int bytesInBuffer, int markerPosInBuffer) throws IOException {
	byte[] tmp = new byte[2];
	StringBuffer byteCount = new StringBuffer();
	String offset;

	// Advance the stream cursor to right after the marker and
	// write out the marker
	if (bytesInBuffer < bufSize) {
            data.skip(-bytesInBuffer);
        } else {
            data.skip(-bufSize);
        }
	data.skip(markerPosInBuffer + bmLength);

	captureBuf.write(bmBytes);
	
	// Advance the stream cursor past any whitespace between the maker and the next token,
	// the fieldName, Preserving the whitespace in the output stream as we go.
	do {
	    data.read(tmp, 0, 1);
	    
	    if (isWhiteSpace(tmp[0])) {
		captureBuf.write(tmp[0]);
	    } else {
		break;
	    }
	} while (true);
	data.skip(-1);

	// Advance the stream cursor past the fieldname, writing the
	// the fieldName in  the output stream as we go. 
	do {
	    data.read(tmp, 0, 1);
	    if (!isWhiteSpace(tmp[0])) {
		captureBuf.write(tmp[0]);
	    } else {
		break;
	    }
	} while (true);
	data.skip(-1);

  	// Advance the stream cursor past any whitespace between the fieldName and the next token,
	// the byteCount, Preserving the whitespace in the output stream as we go.
	do {
	    data.read(tmp, 0, 1);
	    
	    if (isWhiteSpace(tmp[0])) {
		captureBuf.write(tmp[0]);
	    } else {
		break;
	    }
	} while (true);
	data.skip(-1);

	// Advance the stream cursor past the byteCount, capturing the
	// the byteCount in the byteCount buffer.
	do {
	    data.read(tmp, 0, 1);
	    if (!isWhiteSpace(tmp[0])) {
		byteCount.append((char)tmp[0]);
		captureBuf.write(tmp[0]);
	    } else {
		break;
	    }
	} while (true);

	offset = " " + data.getFilePosition();
	captureBuf.write(offset.getBytes());
	
	long skipAmmount = Long.parseLong(byteCount.toString());
	
	data.skip(skipAmmount);
    }

    public byte[] getBytes(int offset, int length) {
	byte[] result = new byte[length];

	try {
	    data.setFilePosition(offset);
	    data.read(result, 0, length);
	}
	catch (java.io.IOException e){
	    System.out.println("StreamPreprocessor.getBytes: Exception caught while trying to position data stream");
	    
	}
	return result;
    }

    
    public InputStream preprocessStream() {
	ByteArrayOutputStream captureBuf = new ByteArrayOutputStream();

	try {
	    int count;
	    int markerFound;

    	    do {
    		count = data.read(buffer, 0, bufSize);
		
    		if (count != -1) {
		    String str = new String(buffer, 0, count);
		    markerFound = str.indexOf(binaryMarker);
		    
		    if (markerFound != -1) {
			captureBuf.write(str.substring(0, markerFound).getBytes());
			handleBinaryData(captureBuf, count, markerFound);
		    } else {

			if (count <= bmLength) {   // last read...
			    captureBuf.write(str.substring(0, count).getBytes());
			} else {
			    captureBuf.write(str.substring(0, Math.max(count - bmLength, 0)).getBytes());

			    if (count > bmLength) {
				
				// this guarantees we won't miss our marker because it spans two reads
				data.skip(-bmLength);
			    }
			}
			
		    }
		}
		
		

    	    } while (count != -1);
	    //captureBuf.writeTo(System.out);
	}
	catch (Exception e) {
	    System.out.println("exception caught :" + e);
	}
	
	
	return new ByteArrayInputStream(captureBuf.toByteArray());
    }


    static public final void main(String[] args) {
	try {
	    InputStream in = new java.io.FileInputStream("test5.jazz");
	    ZExtendedInputStream extendedIn = new ZExtendedInputStream(in);
	    
	    ZStreamPreprocessor sp = new ZStreamPreprocessor(extendedIn);
	    sp.preprocessStream();
	}
	catch (Exception e) {
	    System.out.println(e);
	    
	}
	    
    }
}


