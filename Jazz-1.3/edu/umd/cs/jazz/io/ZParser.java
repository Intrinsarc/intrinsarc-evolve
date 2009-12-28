/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.io;

import java.lang.*;
import java.io.*;
import java.lang.reflect.Array;

/** 
 * <b>ZParser</b> is the top-level api to the Jazz io code.
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
public class ZParser implements Serializable {

    protected static ZInternalParser internalParser = null;
    private static final String[][] stringTranslations = {{"\\\\", "\\"},
							  {"\\n", "\n"},
							  {"\\\"", "\""},
							  {"\\r", "\n"},
							  {"\\t", "\t"}} ;
   
    

    /**
     * Takes a Java string and creates a string appropriate for Jazz to save (all
     * control characters are backslashed).
     * @param s The java string to be translated
     * @return the translated String
     */
    public static String translateJavaToJazz(String s) {
	String key;
	String val;
	int hit;
	int prevHit;
	
	for (int i=0; i<stringTranslations.length; i++) {
	    key = stringTranslations[i][1];
	    val = stringTranslations[i][0];
	    
	    prevHit = 0;
	    hit = 0;
	    while ((hit = s.indexOf(key, prevHit)) != -1) {
		s = s.substring(0, hit) + val + s.substring(hit+key.length());
		prevHit = hit + val.length();
	    }
	}
	return s;
    }

    /**
     * Takes a Jazz string  (all control characters are backslashed) and creates a
     * string Java String.
     * @param s The java string to be translated
     * @return the translated String
     */
    public static String translateJazzToJava(String s) {
	String key;
	String val;
	int hit;
	int prevHit;
	
	for (int i=0; i<(s.length() - 1); i++) {
	    if (s.charAt(i) == '\\') {
		for (int j=0; j<stringTranslations.length; j++) {
		    key = stringTranslations[j][0];
		    val = stringTranslations[j][1];
		    if (s.charAt(i+1) == key.charAt(1)) {
			s = s.substring(0, i) + val + s.substring(i+key.length());
			break;
		    }
		}			
	    }
	}

	return s;
    }
    
    public synchronized Object parse(InputStream stream) throws ParseException {
	ZExtendedInputStream dataStream = new ZExtendedInputStream(stream);
	ZStreamPreprocessor pp = new ZStreamPreprocessor(dataStream);
	InputStream parseStream = pp.preprocessStream();
	
	if (internalParser == null) {
	    internalParser = new ZInternalParser(parseStream);
	} else {
	    internalParser.ReInit(parseStream);
	}
	internalParser.createObjectTable();
	internalParser.setDataStream(dataStream);
	return internalParser.Scene(internalParser);
    }

    
    public static void main(String args[]) {

	
  	try {
  	    InputStream data = new java.io.FileInputStream("test6.jazz");
  	    ZParser parser = new ZParser();
  	    Object result = parser.parse(data);
  	    System.out.println("Jazz File ZParser:  Jazz file parsed successfully.");
  	    System.out.println(result);
		
  	} catch (Exception e) {
  	    System.out.println(e.getMessage());
  	    System.out.println("Jazz File ZParser:  Encountered errors during parse.");
  	}
    }
}

