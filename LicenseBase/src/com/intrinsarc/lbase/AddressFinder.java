package com.intrinsarc.lbase;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class AddressFinder
{
	// do we force using the shell command?
	private static final boolean FORCE_MACADDR_SHELL = System.getProperty("forceMACAddrShell") != null;
	
	enum Platform
	{
		Unknown(null),
		Windows("ipconfig /all"),
		Linux("/sbin/ifconfig"),
		MacOS("ifconfig"),
		Solaris("/usr/sbin/arp"),
		Bsd("ifconfig");

		String command;

		private Platform(String command)
		{
			this.command = command;
		}
	}

	static final Platform PLATFORM;
	static
	{
		String os = System.getProperty("os.name").trim().toLowerCase();
		if (os.indexOf("windows") >= 0)
		{
			PLATFORM = Platform.Windows;
		}
		else if (os.indexOf("linux") >= 0)
		{
			PLATFORM = Platform.Linux;
		}
		else if (os.indexOf("mac os x") >= 0 || os.indexOf("macosx") >= 0)
		{
			PLATFORM = Platform.MacOS;
		}
		else if (os.indexOf("solaris") >= 0 || os.indexOf("sunos") >= 0)
		{
			PLATFORM = Platform.Solaris; // untested
		}
		else if (os.indexOf("bsd") >= 0)
		{
			PLATFORM = Platform.Bsd; // untested
		}
		else
		{
			PLATFORM = Platform.Unknown;
		}
	}

	
	public static Set<String> getMACAddresses()
	{
		Set<String> addresses = new HashSet<String>();
		
		if (!FORCE_MACADDR_SHELL)
		{
		  try
		  {
		      Method method = NetworkInterface.class.getMethod("getHardwareAddress", (Class[]) null);
		      
		      Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
		      if (ifs != null) {
		          while (ifs.hasMoreElements()) {
		              NetworkInterface iface = ifs.nextElement();
		              byte[] hardware = (byte[]) method.invoke(iface);
		              if (hardware != null && hardware.length == 6 && hardware[1] != (byte) 0xff)
		              {
		              	String hex = "";
		              	for (byte b : hardware)
		              	{
		              		if (hex.length() != 0)
		              			hex += ":";
		              		hex += toHex(b);
		              	}
		              	addresses.add(hex);
		              }
		          }
		      }
		  }
		  catch (Exception ex)
		  {
		  	System.out.println(ex);
		  }
	  
		  // may not have found any
		  if (!addresses.isEmpty())
		  	return addresses;
		}
	  
	  // otherwise default to JDK1.5 way
	  return useShellToFindAddresses();
	}
	
	private static final String[] HEX_CHARS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	private static String toHex(byte b)
	{
		if (b < 0)
			b += 256;
		return HEX_CHARS[(b&255)/16] + HEX_CHARS[b&15];
	}

	private static Set<String> useShellToFindAddresses()
	{
		if (PLATFORM == Platform.Unknown)
		{
			return null;
		}
		BufferedReader reader = null;
		try
		{
			Process conf = Runtime.getRuntime().exec(PLATFORM.command);
			reader = new BufferedReader(new InputStreamReader(conf.getInputStream()));
			Set<String> macs = new LinkedHashSet<String>(4);
			StringBuilder regex = new StringBuilder("(?<!0)(?<!\\-)([0-9a-fA-F]{1,2}");
			for (int i = 0; i < 5; i++)
			{
				regex.append("[-:]");
				regex.append("[0-9a-fA-F]{2}");
			}
			regex.append(")\t*$");
			Pattern pattern = Pattern.compile(regex.toString());
			String line = reader.readLine();
			do
			{
				Matcher matcher = pattern.matcher(line);
				while (matcher.find())
				{
					String address = matcher.group(1);
					assert address.length() == 16 || address.length() == 17;
					if (address.length() == 16)
					{ // solaris omits first 0 (untested)
						address = "0" + address;
					}
				}
				line = reader.readLine();
			} while (line != null);
			return Collections.unmodifiableSet(macs);
		} catch (Throwable t)
		{
			return null;
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e)
				{
					// ignore
				}
			}
		}
	}
}
