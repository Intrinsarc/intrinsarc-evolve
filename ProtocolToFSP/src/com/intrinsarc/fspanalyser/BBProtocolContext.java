package com.intrinsarc.fspanalyser;

import java.util.*;

public class BBProtocolContext
{
  private BBGlobalProtocolContext global;
  private int dummyCount = 0;
  private int endCount = 0;
  private int endActionCount = 0;
  private int startCount = 0;
  private int endLoopCount = 0;
  private Set<String> compositionIds = new HashSet<String>();
  private List<String> topLevelProcessesToCompose = new ArrayList<String>();
  
  public BBProtocolContext(BBGlobalProtocolContext global)
  {
    this.global = global;
  }
  
  public String registerTopLevelProcess(String processName)
  {
    topLevelProcessesToCompose.add(processName);
    return processName;
  }
  
  public boolean isPortBound(String portName)
  {
    return true;
  }
  
  public List<String> getTopLevelProcessesToCompose()
  {
    return topLevelProcessesToCompose;
  }
  
  public Set<String> getCompositionIds()
  {
    return compositionIds;
  }
  
  public String makeAction(String Id, String portName, String interfaceName, boolean callNotReturn, boolean firstIsSelf, String action)
  {
    boolean tx = callNotReturn && firstIsSelf || !callNotReturn && firstIsSelf;
    compositionIds.add("comp_" + Id);
    return "comp_" + Id + "." + firstLower(portName) + "." + firstLower(interfaceName) + "." + (callNotReturn ? "call" : "ret") + "." + (callNotReturn ^ tx ? "rx" : "tx") + "." + firstLower(action);
  }

	public static String firstLower(String name)
	{
		if (name.length() == 0)
			return "";
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}

  public String makeNewId()
  {
    return global.makeNewId();
  }

  public String makeNewStartAction()
  {
    return "_.start." + makeAsciiString(startCount++);
  }
  
  public String makeNewEndAction()
  {
    return "_.end." + makeAsciiString(endCount++);
  }

  public String makeTauAction()
  {
    return "_.mytau." + makeAsciiString(dummyCount++);
  }
  
  public static String makeAsciiString(int count)
  {
    String ascii = "";
    for (;count >= 0; count -= 26)
    {
      char ch = (char) ('a' + (count % 26));
      ascii += "" + ch;
    }
    return ascii;
  }

  public String makeNewEndLoopAction()
  {
    return global.makeNewEndLoopAction();
  }
}
