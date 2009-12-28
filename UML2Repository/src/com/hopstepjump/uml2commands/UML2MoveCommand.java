package com.hopstepjump.uml2commands;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

public class UML2MoveCommand extends AbstractCommand
{
  private Namespace oldParent;
  private Element child;
  private Namespace newParent;
  
  public UML2MoveCommand(Namespace oldParent, Element child, Namespace newParent, String execute, String unExecute)
  {
    super(execute, unExecute);
    this.oldParent = oldParent;
    this.child = child;
    this.newParent = newParent;
  }  
  
  public void execute(boolean isTop)
  {
    // remove from the old parent, and add to the new
    oldParent.getMembers().remove(child);
    newParent.getMembers().add(child);
  }

  public void unExecute()
  {
    // remove from the new parent, and add back to the old
    newParent.getMembers().remove(child);
    oldParent.getMembers().add(child);
  }
}