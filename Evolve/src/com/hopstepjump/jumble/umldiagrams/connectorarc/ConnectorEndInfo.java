package com.hopstepjump.jumble.umldiagrams.connectorarc;

import org.eclipse.uml2.*;

public class ConnectorEndInfo
{
	private ConnectableElement role;
	private Property partWithPort;
	public Property getPartWithPort()
	{
		return partWithPort;
	}
	public void setPartWithPort(Property partWithPort)
	{
		this.partWithPort = partWithPort;
	}
	public ConnectableElement getRole()
	{
		return role;
	}
	public void setRole(ConnectableElement role)
	{
		this.role = role;
	}
	
}
