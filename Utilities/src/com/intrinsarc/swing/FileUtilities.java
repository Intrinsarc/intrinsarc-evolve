package com.intrinsarc.swing;

import java.io.*;
import java.net.*;
import java.util.zip.*;

public class FileUtilities
{
  /**
   * read a file into a string
   * @param path
   * @param name
   * @return
   * @throws FileNotFoundException
   */
  public static String loadFileContents(File file) throws IOException
  {
    FileReader in = null;
    BufferedReader reader = null;
    StringBuilder out = new StringBuilder();
    try
    {
      in = new FileReader(file);
      reader = new BufferedReader(in);
      String line;
      do
      {
        line = reader.readLine();
        if (line != null)
        {
          out.append(line);
          out.append("\n");
        }
      } 
      while (line != null);
     }
    finally
    {
      if (reader != null)
        reader.close();
      if (in != null)
        in.close();
    }
    return out.toString();
  }
	
	public static final String loadFileContentsFromResource(String resourceFileName)
	{
		URL resource = ClassLoader.getSystemResource(resourceFileName);
		StringBuilder b = new StringBuilder();
		
		try
		{
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					resource.openStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
		}
	  catch (IOException e)
	  {
	  	return null; 
	  }

		return b.toString();
	}
	
  /**
   * Deletes all files and subdirectories under dir.
   * Returns true if all deletions were successful.
   * If a deletion fails, the method stops attempting to delete and returns false.
   * taken from the java almanac
   * @param dir
   * @return
   */
  public static boolean deleteDir(File dir)
  {
    if (dir.isDirectory())
    {
      String[] children = dir.list();
      for (int i=0; i<children.length; i++)
      {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success)
          return false;
      }
    }  
    // The directory is now empty so delete it
    return dir.delete();
  }
  
  /**
   * read a file as binary
   * @param path
   * @param name
   * @return
   * @throws FileNotFoundException
   */
  private static final int MAX_BINARY_SIZE = 5*1024*1024;  // 5mb
  public static byte[] loadFileAsBinary(File file) throws IOException
  {
    InputStream is = new FileInputStream(file);

    try
    {
      // Get the size of the file
      long length = file.length();
  
      if (length > MAX_BINARY_SIZE)
        throw new IOException("File " + file + " is too large to load into memory");
  
      // Create the byte array to hold the data
      byte[] bytes = new byte[(int)length];
  
      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length-offset)) >= 0)
        offset += numRead;
  
      // Ensure all the bytes have been read in
      if (offset < bytes.length)
          throw new IOException("Could not completely read file " + file.getName());
      return bytes;
    }
    finally
    {
      // Close the input stream and return bytes
      is.close();      
    }
  }

  /**
   * read a file as binary
   * @param path
   * @param name
   * @return
   * @throws FileNotFoundException
   */
  public static void writeFileAsBinary(File file, byte[] bytes) throws IOException
  {
    OutputStream is = new FileOutputStream(file);

    try
    {
      // Read in the bytes
      is.write(bytes);
    }
    finally
    {
      // Close the stream
      is.close();      
    }
  }

  /**
   * taken from http://java.sun.com/docs/books/performance/1st_edition/html/JPIOPerformance.fm.html
   * -- the sun java performance guide
   * @param from
   * @param to
   * @throws IOException
   */
  public static void copyFile(File from, File to, boolean expandUsingGZip) throws IOException
  {
    final int BUFF_SIZE = 100000;
    byte[] buffer = new byte[BUFF_SIZE];
    
    InputStream in = null;
    OutputStream out = null; 
    try
    {
    	if (expandUsingGZip)
    		in = new GZIPInputStream(new FileInputStream(from));
    	else
    		in = new FileInputStream(from);
      out = new FileOutputStream(to);
      while (true)
      {
          int amountRead = in.read(buffer);
          if (amountRead == -1)
            break;
          out.write(buffer, 0, amountRead); 
      } 
     }
    finally
    {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
     }
  }
  
  /**
   * Copies all files under srcDir to dstDir.
   * If dstDir does not exist, it will be created.
   * Taken from Java Almanac -- e1072
   */ 
  // 
  public static void copyDirectory(File srcDir, File dstDir) throws IOException
  {
    if (srcDir.isDirectory())
    {
      if (!dstDir.exists())
        dstDir.mkdir();
  
      String[] children = srcDir.list();
      for (int i=0; i<children.length; i++)
        copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
    }
    else
        copyFile(srcDir, dstDir, false);
  }

	public static void recursivelyRemoveFiles(File srcDir, String extensions[])
	{
    if (srcDir.isDirectory())
    {
      String[] children = srcDir.list();
      for (int i=0; i < children.length; i++)
      {
        File file = new File(srcDir, children[i]);
        if (file.isDirectory())
        	recursivelyRemoveFiles(file, extensions);
        else
        {
        	for (int lp = 0; lp < extensions.length; lp++)
        		if (children[i].endsWith(extensions[lp]))
        		{
        			file.delete();
        			break;
        		}
        }
      }
    }
	}
}
