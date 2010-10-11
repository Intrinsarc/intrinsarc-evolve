package com.intrinsarc.lbase;

import java.security.*;
import java.security.spec.*;
import java.text.*;
import java.util.*;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.persistence.*;

public class LReal
{
  public static final Preference LICENSE_PREFERENCE = new Preference("Advanced", "License");
  private static boolean GPL_MODE;
  
	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC71YXsD+nU/J8bO6ECF1gSHh0mZcJM1T/dywHwzsEIEbdKzN429iOEtW0UYRhehMQYdXJCM1glSTJJIzAD9vFt9bJ1o2EqCCnskYVVCTXxzExu0ghYfNkCnB0rPpuZPSoS7YIKQl5abpgAzRQMWpYhOrDQxpn0BER7ta/o4bbr3wIDAQAB";
	private String user;
	private String email;
	private int number;
	private String expiry;
	private Date expiryDate;
	private String machineId;
	private Set<String> macs;
	
	/** register the license slot */
	public static void registerPreferenceSlots()
	{
	  GlobalPreferences.preferences.addPreferenceSlot(
	  	LICENSE_PREFERENCE,
	  	new PreferenceTypeString(),
    	"The encrypted license");
	}
	
	/** returns a valid license if possible, otherwise indicates the failure reason */
	public static LReal retrieveLicense(String[] errorReason)
	{
		GPL_MODE = true;
		PersistentProperty lic = GlobalPreferences.preferences.getRawPreference(LICENSE_PREFERENCE);
		if (lic == null)
		{
			errorReason[0] = "No license found";
			return null;
		}
		
		String licText = lic.asString();
		if (licText == null)
		{
			errorReason[0] = "No license found";
			return null;
		}
		
		try
		{
			LReal license = new LReal(licText);
			if (license.isExpired())
			{
				errorReason[0] = "License expired on " + license.getExpiry();
				return license;
			}
			if (!license.isCorrectMachineId())
			{
				errorReason[0] = "License is not valid - it is for a different machine";
				return license;
			}
			GPL_MODE = false;
			return license;
		}
		catch (Exception ex)
		{
			String msg = ex.getMessage();
			errorReason[0] = "License decryption failed: " + ("null".equals(msg) ? "empty license found" : msg);
			return null;
		}
	}
	
	public static String retrieveEncryptedLicense()
	{
		PersistentProperty lic = GlobalPreferences.preferences.getRawPreference(LICENSE_PREFERENCE);
		if (lic == null)
			return "";
		return lic.asString();
	}
	
	public static void storeLicense(String encrypted)
	{
		GlobalPreferences.preferences.getSlot(LICENSE_PREFERENCE, false).setStringValue(
				GlobalPreferences.preferences.getRegistry(), encrypted); 
	}
	
	public LReal(String encrypted) throws Exception
	{
		KeyFactory factory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicSpec = new X509EncodedKeySpec(Utils.decode(PUBLIC_KEY));
		PublicKey publicKey = factory.generatePublic(publicSpec);

		LDetails details = new LDetails(LWork.decrypt(encrypted, publicKey, null));
		user = details.get("user");
		email = details.get("email");
		number = Integer.parseInt(details.get("number"));
		expiry = details.get("expiry");
		machineId = details.get("machine-id");
		
		// parse some more details out
		if (!expiry.equals("never"))
		{
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("en", "US"));
			expiryDate = formatter.parse(expiry);
		}
		
		// get the macs
		macs = new HashSet<String>();
		String actual = Finder.toHex(Utils.decode(machineId));
		for (int lp = 0; lp < actual.length() / 12; lp++)
			macs.add(actual.substring(lp * 12, lp * 12 + 12));
	}
	
	public boolean isExpired()
	{
		if (expiryDate == null)
			return false;
		return new Date().after(expiryDate);
	}
	
	public boolean isCorrectMachineId()
	{
		// at most 1 mac address can differ
		int allowedFailures = macs.size() > 1 ? 1 : 0;

		int failed = 0;
		Set<String> current = Finder.findAll();
		for (String mac : macs)
			if (!current.contains(mac))
				failed++;

		// this is ok if the failed number is smaller than the allowed failures
		return failed <= allowedFailures;
	}
	
	public String getUser()
	{
		return user;
	}

	public String getEmail()
	{
		return email;
	}

	public int getNumber()
	{
		return number;
	}

	public String getExpiry()
	{
		return expiry;
	}

	public Date getExpiryDate()
	{
		return expiryDate;
	}

	public String getMachineId()
	{
		return machineId;
	}

	public Set<String> getMacs()
	{
		return macs;
	}
	
	public static boolean isGPLMode()
	{
		return GPL_MODE;
	}
}
