package com.intrinsarc.lbase;

import java.io.*;
import java.security.*;
import java.util.*;

import javax.crypto.*;

public class LWork
{
	
	/**
	* Encrypt a text using a key, returning a set of hex bytes
	*/
	public static String encrypt(String text, PublicKey publicKey, PrivateKey privateKey) throws Exception
	{
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey != null ? publicKey : privateKey);

    // encrypt the plaintext using the public key
    byte[] bytes = text.getBytes();
    
    String out = "";
    int total = (int) Math.ceil(bytes.length / 100.0);
    for (int lp = 0; lp < total; lp++)
    {
    	if (out.length() > 0)
    		out += "-";
    	out += LUtils.encodeBytes(cipher.doFinal(bytes, lp*100, Math.min(100, bytes.length - lp*100)));
    }
    return "-" + out + "-";
	}
	
	/**
	 * descript an hex encoded, encryped text 
	 */
	
	public static String decrypt(String hex, PublicKey publicKey, PrivateKey privateKey) throws Exception
	{
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.DECRYPT_MODE, publicKey != null ? publicKey : privateKey);

    StringTokenizer tok = new StringTokenizer(hex, "-");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    while (tok.hasMoreTokens())
    {
    	String next = tok.nextToken();
    	byte[] bytes = LUtils.decode(next);
    	out.write(cipher.doFinal(bytes));
    }
    return new String(out.toByteArray());
	}
}
