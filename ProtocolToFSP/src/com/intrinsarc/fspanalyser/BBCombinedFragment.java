package com.intrinsarc.fspanalyser;

import java.io.*;

public class BBCombinedFragment extends AbstractBBCombinedFragment
{
  private String[] parameters;

  public BBCombinedFragment()
  {
  }
  
  public void setParameters(String[] parameters)
  {
    this.parameters = parameters;
  }
  
  public String[] getParameters()
  {
    return parameters;
  }

  public void produceFSP(BBProtocolContext p, PrintStream before, PrintStream during, PrintStream after, String compositionId)
  {    
    // switch based on the operator
    ByteArrayOutputStream newBeforeStream = new ByteArrayOutputStream();
    PrintStream newBefore = new PrintStream(newBeforeStream);
    ByteArrayOutputStream newDuringStream = new ByteArrayOutputStream();
    PrintStream newDuring = new PrintStream(newDuringStream);
    ByteArrayOutputStream newAfterStream = new ByteArrayOutputStream();
    PrintStream newAfter = new PrintStream(newAfterStream);

    if (operator.equals("seq"))
      produceInternalSeqFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter, compositionId);
    else
    if (operator.equals("loop") && parameters.length != 0)
      produceInternalFiniteLoopFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter);
    else
    if (operator.equals("loop"))
      produceInternalInfiniteLoopFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter);
    else
    if (operator.equals("alt"))
      produceInternalAltFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter);
    else
    if (operator.equals("par"))
      produceInternalParFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter);
    else
      if (operator.equals("opt") && parameters.length == 0)
        produceInternalOptFSP(p,
            before, during, after,
            newBefore, newDuring, newAfter);
    else
    if (operator.equals("opt"))
      produceInternalPortBoundOptFSP(p,
          before, during, after,
          newBefore, newDuring, newAfter,
          parameters[0]);
    
    // add the process definition to the outer stream
    before.println(newBeforeStream.toString() + newDuringStream.toString() + newAfterStream.toString());
  }

  private void produceInternalSeqFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter,
      String compositionId)
  {
    // this will have one operand, so just ask each element in the main operand to output
    produceFSPForConstituents(p, getOperands().get(0), before, during, after, compositionId);
  }

  private void produceInternalInfiniteLoopFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter)
  {
    // this will have one operand, so just ask each element in the main operand to output
    String id = p.makeNewId();
    String mainProcess = p.registerTopLevelProcess("Iloop" + id);
    String startAction = p.makeNewStartAction();
    String endAction = p.makeNewEndAction();
    String loopProcess = mainProcess + "_loop";
    
    newDuring.println(mainProcess + " = (");
    newDuring.println("  " + startAction + " -> " + loopProcess + "),");
    newDuring.println(loopProcess + " = (");
    produceFSPForConstituents(p, operands.get(0), newBefore, newDuring, newAfter, id);
    newDuring.println("  " + loopProcess + ") + {" + endAction + "}.");
    
    // add the synchronisation points in the process we want to add to
    during.print("  " + startAction + " -> " + endAction + " -> ");
  }

  private void produceInternalFiniteLoopFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter)
  {
    // this will have one operand, so just ask each element in the main operand to output
    String id = p.makeNewId();
    String mainProcess = p.registerTopLevelProcess("Floop" + id);
    String startAction = p.makeNewStartAction();
    String endAction = p.makeNewEndAction();
    String loopProcess = mainProcess + "_loop";
    String exitProcess = mainProcess + "_exit";
    String tauAction = p.makeTauAction();
    
    newDuring.println(mainProcess + " = (");
    newDuring.println("  " + startAction + " -> " + loopProcess + "),");
    newDuring.println(loopProcess + " = (");
    produceFSPForConstituents(p, operands.get(0), newBefore, newDuring, newAfter, id);
    newDuring.println("  " + exitProcess + "),");
    newDuring.println(exitProcess + " = (");
    newDuring.println("  " + tauAction + " -> " + loopProcess);    
    newDuring.println(" |" + endAction + " -> " + mainProcess + ").");
    
    // add the synchronisation points in the process we want to add to
    during.print("  " + startAction + " -> " + endAction + " -> ");
  }

  private void produceInternalAltFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter)
  {
    // produce a new process that alternates for each of the operands
    String id = p.makeNewId();
    String mainProcess = p.registerTopLevelProcess("Alt" + id);
    String startAction = p.makeNewStartAction();
    String endAction = p.makeNewEndAction();
    
    newDuring.println(mainProcess + " = (");
    boolean first = true;
    for (BBOperand bbOperand : operands)
    {
      if (first)
        first = false;
      else
        newDuring.println(" |");
      
      newDuring.println("  " + startAction + " -> ");
      produceFSPForConstituents(p, bbOperand, newBefore, newDuring, newAfter, id);
      newDuring.println("   " + endAction + " -> " + mainProcess);
    }
    newDuring.println(").");

    // add the synchronisation points in the process we want to add to
    during.print("  " + startAction + " -> " + endAction + " -> ");
  }

  private void produceInternalParFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter)
  {
    // produce a new process that alternates for each of the operands
    String startAction = p.makeNewStartAction();
    String endAction = p.makeNewEndAction();
    
    for (BBOperand bbOperand : operands)
    {
      String id = p.makeNewId();
      String mainProcess = p.registerTopLevelProcess("Par" + id);
      newDuring.println(mainProcess + " = (");
      newDuring.println("  " + startAction + " -> ");
      produceFSPForConstituents(p, bbOperand, newBefore, newDuring, newAfter, id);
      newDuring.println("   " + endAction + " -> " + mainProcess);
      newDuring.println(").");
    }

    // add the synchronisation points in the process we want to add to
    during.print("  " + startAction + " -> " + endAction + " -> ");
  }

  private void produceInternalOptFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter)
  {
    // produce a new process that alternates for each of the operands
    String id = p.makeNewId();
    String mainProcess = p.registerTopLevelProcess("Opt" + id);
    String startAction = p.makeNewStartAction();
    String endAction = p.makeNewEndAction();
    
    newDuring.println(mainProcess + " = (");
    BBOperand bbOperand = operands.get(0);
    newDuring.println("  " + startAction + " -> " + endAction + " -> " + mainProcess);
    newDuring.println(" |" + startAction + " -> ");
    produceFSPForConstituents(p, bbOperand, newBefore, newDuring, newAfter, id);
    newDuring.println("   " + endAction + " -> " + mainProcess);
    newDuring.println(").");

    // add the synchronisation points in the process we want to add to
    during.print("  " + startAction + " -> " + endAction + " -> ");
  }

  private void produceInternalPortBoundOptFSP(
      BBProtocolContext p,
      PrintStream before, PrintStream during, PrintStream after,
      PrintStream newBefore, PrintStream newDuring, PrintStream newAfter,
      String boundPortName)
  {
    // if the port is bound, then delegate to internal opt producer
    // otherwise suppress...
    if (p.isPortBound(boundPortName))
      this.produceInternalOptFSP(p, before, during, after, newBefore, newDuring, newAfter);
  }

  private void produceFSPForConstituents(
      BBProtocolContext p,
      BBOperand bbOperand,
      PrintStream before,
      PrintStream during,
      PrintStream after,
      String compositionId)
  {
    for (BBInteractionFragment bbFragment : bbOperand.getConstituents())
    {
      bbFragment.produceFSP(p, before, during, after, compositionId);
    }
  }

  public boolean containsInfiniteLoop()
  {
    // if we are an infinite loop, then this is fine
    if (operator.equals("loop") && parameters.length == 0)
      return true;
    
    // ask each operand in turn
    for (BBOperand bbOperand : operands)
    {
      if (bbOperand.containsInfiniteLoop())
        return true;
    }
    return false;
  }
  
}
