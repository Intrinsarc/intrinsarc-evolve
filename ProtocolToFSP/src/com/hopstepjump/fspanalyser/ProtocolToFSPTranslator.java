package com.hopstepjump.fspanalyser;

import java.io.*;
import java.util.*;

import com.hopstepjump.deltaengine.base.*;

/**
 * 
 * 
 * @author Andrew
 */
public class ProtocolToFSPTranslator
{
	private DEStratum perspective;

	public ProtocolToFSPTranslator(DEStratum perspective)
	{
		this.perspective = perspective;
	}
	
	public String produceFSPForProtocol(BBGlobalProtocolContext global, String partName, DEComponent type) throws BackboneFSPTranslationException
	{    
	  List<BBProtocol> protocols = new ProtocolParser(perspective, type).makeLeafProtocol();
	  
	  // if this is a leaf use a protocol context to work out the FSP
	  if (type.isLeaf(perspective))
	  {
	    int size = protocols.size();
	    if (size != 1)
	      throw new BackboneFSPTranslationException(
	          "Must have just one protocol associated with " + partName + "::" +
	          type.getName() + ", but there are " + size);
	    
	    BBProtocol p = protocols.get(0);
	    return p.produceFSP(global);
	  }
	  else
	  {
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    PrintStream out = new PrintStream(outStream);
	
	    // ask each component in turn to make their protocols
	    for (DEPart bbPart : getParts(type))
	    {
	      out.println(produceFSPForProtocol(global, bbPart.getType().getName(), bbPart.getType())); 
	    }
	    
	    // turn each of the instances into properties
	    out.println();
	    for (DEPart bbPart : getParts(type))
	    {
	    	DEComponent partType = bbPart.getType();
	      List<BBProtocol> partProtocols = new ProtocolParser(perspective, partType).makeLeafProtocol();
	      boolean compositeProtocol = partProtocols.isEmpty();
	      String protocolName = compositeProtocol ? partType.getName() : partProtocols.get(0).getName();
	      
	      String subPartName = bbPart.getName();
	      String lowerSubPartName = BBProtocolContext.firstLower(subPartName);
	      String extraPrefix = compositeProtocol ? ".c" : "";
	      out.println("property ||P_" + subPartName + "_" + partType.getName() + " = (" + lowerSubPartName + extraPrefix + ":" + protocolName + ")" );
	      out.print("  @ {");
	      // expose only the possible input actions based on the ports
	      boolean first = true;
	      for (DEPort port : getPorts(partType))
	      {
	      	for (DEInterface iface : partType.getProvidedInterfaces(perspective, port))
	      	{
	      		for (DEOperation message : getMessages(iface))
	      		{
	        		if (!first)
	        			out.print(", ");
	        		first = false;
	        		out.print(lowerSubPartName + ".c." + BBProtocolContext.firstLower(partType.getName()) + "." +
	        				BBProtocolContext.firstLower(iface.getName()) + "." + "call.rx." + message.getName());
	      		}
	      	}
	      }
	      out.println("}.");
	    }
	    out.println();
	    
	    // compose them together, and omit any non-exposed ports
	    out.println("||" + partName + "_unhidden = (");
	    int size = getParts(type).size();
	    int lp = 0;
	    for (DEPart bbPart : getParts(type))
	    {
	      out.print("P_" + bbPart.getName() + "_" + bbPart.getType().getName());
	      if (lp != size - 1)
	        out.print(" || ");
	      lp++;
	    }
	    
	    // pair up connections by matching transmits and receives
	    out.println(") / {");
	    for (DEConnector bbConnector : getConnectors(type))
	    {
	      DEConnectorEnd end1 = bbConnector.makeConnectorEnd(perspective, type, 0);
	      DEConnectorEnd end2 = bbConnector.makeConnectorEnd(perspective, type, 1);
	      String port1Name = end1.getPort().getName();
	      String port2Name = end2.getPort().getName();
	
	      // make sure end1 matches to the outside, if possible
	      if (end2.getPart() == null)
	      {
	        DEConnectorEnd swap = end1;
	        end1 = end2;
	        end2 = swap;
	        String swapPort = port1Name;
	        port1Name = port2Name;
	        port2Name = swapPort;
	      }
	      
	      // if one end points to the outside, alias to the outside port
	      if (end1.getPart() == null && end2.getPart() != null)
	      {
	        String part2Name = BBProtocolContext.firstLower(end2.getPart().getName());
	        
	        // handle all interfaces
	        for (DEInterface bbProvided : getProvidedInterfaces(end2))
	        {
	          String iname = bbProvided.getName();
	          out.println("  " + action(port1Name, iname, "call.rx") + " / " + action(part2Name, port2Name, iname, "call.rx") + ",");
	          out.println("  " + action(port1Name, iname, "ret.tx") + " / " + action(part2Name, port2Name, iname, "ret.tx") + ",");            
	        }
	        for (DEInterface bbRequired : getRequiredInterfaces(end2))
	        {
	          String iname = bbRequired.getName();
	          out.println("  " + action(port1Name, iname, "call.tx") + " / " + action(part2Name, port2Name, iname, "call.tx") + ",");
	          out.println("  " + action(port1Name, iname, "ret.rx") + " / " + action(part2Name, port2Name, iname, "ret.rx") + ",");            
	        }
	      }
	      else
	      if (end1.getPart() != null && end2.getPart() != null)
	      {
	        String part1Name = BBProtocolContext.firstLower(end1.getPart().getName());
	        String part2Name = BBProtocolContext.firstLower(end2.getPart().getName());
	        
	        // handle all interfaces
	        for (DEInterface bbProvided : getProvidedInterfaces(end2))
	        {
	          String iname = bbProvided.getName();
	          out.println("  " + action(part2Name, port2Name, iname, "call.rx") + " / " + action(part1Name, port1Name, iname, "call.tx") + ",");
	          out.println("  " + action(part2Name, port2Name, iname, "ret.tx") + " / " + action(part1Name, port1Name, iname, "ret.rx") + ",");            
	        }
	        for (DEInterface bbRequired : getRequiredInterfaces(end2))
	        {
	          String iname = bbRequired.getName();
	          out.println("  " + action(part2Name, port2Name, iname, "call.tx") + " / " + action(part1Name, port1Name, iname, "call.rx") + ",");
	          out.println("  " + action(part2Name, port2Name, iname, "ret.rx") + " / " + action(part1Name, port1Name, iname, "ret.tx") + ",");            
	        }
	      }
	      else
	        throw new UnsupportedOperationException("Connector links from outside to outside");
	    }
	    out.println("  dummy / dummy}.");
	    
	    // hide everything but external ports
	    out.println();
	    out.println("||" + partName + " = " + partName + "_unhidden @ {");
	    size = getPorts(type).size();
	    lp = 0;
	    for (DEPort bbPort : getPorts(type))
	    {
	      out.print(bbPort.getName());
	      if (lp != size - 1)
	        out.print(", ");
	      lp++;
	    }
	    out.println("}.");
	    out.println();
	    
	    return outStream.toString();
	  }
	}
	
  private Set<? extends DEInterface> getRequiredInterfaces(DEConnectorEnd end)
	{
  	return end.getPart().getType().getRequiredInterfaces(perspective, end.getPort());
	}

	private Set<? extends DEInterface> getProvidedInterfaces(DEConnectorEnd end)
	{
  	return end.getPart().getType().getProvidedInterfaces(perspective, end.getPort());  	
	}

	private List<DEOperation> getMessages(DEInterface iface)
	{
  	List<DEOperation> messages= new ArrayList<DEOperation>();
  	for (DeltaPair p : iface.getDeltas(ConstituentTypeEnum.DELTA_OPERATION).getConstituents(perspective))
  		messages.add(p.getConstituent().asOperation());
  	return messages;
	}

  private List<DEConnector> getConnectors(DEComponent type)
	{
  	List<DEConnector> connectors = new ArrayList<DEConnector>();
  	for (DeltaPair p : type.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR).getConstituents(perspective))
  		connectors.add(p.getConstituent().asConnector());
  	return connectors;
	}

	private List<DEPart> getParts(DEComponent type)
	{
  	List<DEPart> parts = new ArrayList<DEPart>();
  	for (DeltaPair p : type.getDeltas(ConstituentTypeEnum.DELTA_PART).getConstituents(perspective))
  		parts.add(p.getConstituent().asPart());
  	return parts;
	}

  private List<DEPort> getPorts(DEComponent type)
	{
  	List<DEPort> ports = new ArrayList<DEPort>();
  	for (DeltaPair p : type.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
  		ports.add(p.getConstituent().asPort());
  	return ports;
	}

	private static String action(String partName, String portName, String interfaceName, String suffix)
  {
    return
      partName + ".c." + action(portName, interfaceName, suffix);
  }

  private static String action(String portName, String interfaceName, String suffix)
  {
    return
      portName + "." + BBProtocolContext.firstLower(interfaceName) + "." + suffix;
  }
}
