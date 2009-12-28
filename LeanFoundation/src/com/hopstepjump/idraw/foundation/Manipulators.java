package com.hopstepjump.idraw.foundation;

import java.util.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;


public class Manipulators
{
  private ManipulatorFacet keyFocus;
  private List<ManipulatorFacet> others = new ArrayList<ManipulatorFacet>();

  public Manipulators(ManipulatorFacet keyFocus)
  {
    this.keyFocus = keyFocus;
  }

  public Manipulators(ManipulatorFacet keyFocus, ManipulatorFacet singleOther)
  {
    this.keyFocus = keyFocus;
	  addOther(singleOther);
  }

	public Manipulators(ManipulatorFacet keyFocus, ManipulatorFacet firstOther, ManipulatorFacet secondOther)
	{
		this.keyFocus = keyFocus;
		addOther(firstOther);
		addOther(secondOther);
	}

  public void addOther(ManipulatorFacet manipulator)
  {
    if (manipulator != null)
      others.add(manipulator);
  }

  public ManipulatorFacet getKeyFocus()
  {
    return keyFocus;
  }

  public Iterator getOthers()
  {
    return others.iterator();
  }

  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
  {
    if (keyFocus != null)
      keyFocus.addToView(diagramLayer, canvas);

    Iterator iter = others.iterator();
    while (iter.hasNext())
    {
      ManipulatorFacet manip = (ManipulatorFacet) iter.next();
      manip.addToView(diagramLayer, canvas);
    }
  }

  public void cleanUp()
  {
    if (keyFocus != null)
      keyFocus.cleanUp();

    Iterator iter = others.iterator();
    while (iter.hasNext())
    {
      ManipulatorFacet manip = (ManipulatorFacet) iter.next();
      manip.cleanUp();
    }
  }
}
