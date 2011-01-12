package com.intrinsarc.repositorybase;

import java.util.*;

public class UserDetails
{
	private String serial;
	private String user;
	private long time;
	
	public UserDetails(String serial, String user, long time)
	{
		this.serial = serial;
		this.user = user;
		this.time = time;
	}

	@Override
	public int hashCode()
	{
		return serial.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof UserDetails))
			return false;
		UserDetails other = (UserDetails) obj;
		return eq(serial, other.serial) && eq(user, other.user) && time == other.time;
	}

	private boolean eq(String a, String b)
	{
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		return a.equals(b);
	}

	@Override
	public String toString()
	{
		return "UserDetails(name=" + user + ", serial=" + serial + ", time=" + new Date(time) + ")";
	}
}
