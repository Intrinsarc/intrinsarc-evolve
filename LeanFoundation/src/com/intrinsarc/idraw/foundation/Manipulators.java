package com.intrinsarc.idraw.foundation;

import java.util.*;

import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


public class Manipulators implements ManipulatorFacet
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

    // draw in the reverse order so that earlier manipulators have visual precendence
    List<ManipulatorFacet> manips = new ArrayList<ManipulatorFacet>(others);
    Collections.reverse(manips);
    for (ManipulatorFacet manip : manips)
      manip.addToView(diagramLayer, canvas);
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

	public int getType()
	{
		if (keyFocus == null)
			return ManipulatorFacet.TYPE0;
		return keyFocus.getType();
	}

	public void setLayoutOnly()
	{
		if (keyFocus != null)
			keyFocus.setLayoutOnly();
	}

	public boolean wantToEnterViaKeyPress(char keyPressed)
	{
		if (keyFocus == null)
			return false;
		return keyFocus.wantToEnterViaKeyPress(keyPressed);
	}

	public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	{
		if (keyFocus != null)
			keyFocus.enterViaKeyPress(listener, keyPressed);
	}

	public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	{
		if (keyFocus != null)
			keyFocus.enterViaButtonPress(listener, source, point, modifiers);
	}

	public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	{
		if (keyFocus != null)
			keyFocus.enterViaButtonRelease(listener, source, point, modifiers);
	}

	public void enterImmediately(ManipulatorListenerFacet listener)
	{
		if (keyFocus != null)
			keyFocus.enterImmediately(listener);
	}

	public void keyPressed(char keyPressed)
	{
		if (keyFocus != null)
			keyFocus.keyPressed(keyPressed);
	}

	public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	{
		if (keyFocus != null)
			keyFocus.mousePressed(over, point, event);
	}

	public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	{
		if (keyFocus != null)
			keyFocus.mouseDragged(over, point, event);
	}

	public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	{
		if (keyFocus != null)
			keyFocus.mouseMoved(over, point, event);
	}

	public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	{
		if (keyFocus != null)
			keyFocus.mouseReleased(over, point, event);
	}
}
