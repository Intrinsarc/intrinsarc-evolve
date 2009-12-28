package com.hopstepjump.jumble.gui;

import java.awt.event.*;
import java.net.*;

import javax.help.*;
import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.swing.*;

public class HelpContentsAction extends AbstractAction
{
  public static final ImageIcon HELP_ICON = IconLoader.loadIcon("help.png");
  private HelpSet hs;
  private HelpBroker hb;
  private boolean setup;
  

  public HelpContentsAction(ToolCoordinatorFacet coordinator)
  {
    super("Contents");
  }

  public void actionPerformed(ActionEvent e)
  {
    setupHelpContents();    
    new CSH.DisplayHelpFromSource(hb).actionPerformed(e);
  }
  
  private void setupHelpContents()
  {
    if (setup)
      return;
    setup = true;

    // Find the HelpSet file and create the HelpSet object
    String helpHS = "jUMbLe.hs";
    ClassLoader cl = Evolve.class.getClassLoader();
    try
    {
      URL hsURL = HelpSet.findHelpSet(cl, helpHS);
      hs = new HelpSet(null, hsURL);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return;
    }
    
    // Create a HelpBroker object:
    hb = hs.createHelpBroker();
  }
}
