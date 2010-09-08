package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

public class DefaultPackageManipulator extends FieldPopupManipulator
{
	private Popup pop;
	private JTextField field;

	public DefaultPackageManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure)
	{
		super(coordinator, diagramView, figure);
	}

	public void setUpPopup()
	{
  	JPanel panel = new JPanel(new BorderLayout());
  	panel.setBorder(
  			BorderFactory.createCompoundBorder(
  					BorderFactory.createLineBorder(Color.GRAY),
  					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
  	JLabel label = new JLabel("Package:");
  	label.setBorder(new EmptyBorder(0, 0, 0, 5));
  	panel.add(label, BorderLayout.WEST);
  	field = new JTextField();
  	panel.add(field, BorderLayout.CENTER);
  	JPanel button = new JPanel(new BorderLayout());
  	button.setBorder(new EmptyBorder(0, 5, 0, 0));
  	panel.add(button, BorderLayout.EAST);	  	

  	adjustUIState();

  	// set the field to be quite wide
  	ZSwing txt = new ZSwing(new ZCanvas(), new JButton("Aj"));
  	int height = (int) txt.getBounds().getHeight(); 
  	field.setPreferredSize(new Dimension(1230, height));
  	field.setEnabled(!readOnly);
  	
  	UPoint pt = findInsidePoint(diagramView, panel);
  	pop = PopupFactory.getSharedInstance().getPopup(
  			diagramView.getCanvas(),
  			panel, pt.getIntX(), pt.getIntY());
  	SwingUtilities.invokeLater(new Runnable()
  	{
  		public void run()
  		{
  			field.requestFocus();
  		}
  	});
  	
  	field.addKeyListener(new KeyListener()
		{				
			public void keyTyped(KeyEvent e)
			{
			}
			
			public void keyReleased(KeyEvent e)
			{
			}
			
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					setTextAndFinish();
			}
		});
  	
  	pop.show();		
	}
	
	public void removePopup()	
	{		
  	if (pop != null)
  		pop.hide();
	}
	
	public void setTextAndFinish()
	{		
		if (!field.getText().equals(getPackageName()))
		{			
			coordinator.startTransaction("Set default package", "Unset default package");
			StereotypeUtilities.setStringRawStereotypeAttribute(
					getElement(),
					CommonRepositoryFunctions.DEFAULT_PACKAGE,
					field.getText());
			coordinator.commitTransaction();						
		}
		finishManipulator();		
	}
	
  private String getPackageName()
  {
  	return StereotypeUtilities.extractStringProperty(getElement(), CommonRepositoryFunctions.DEFAULT_PACKAGE);
  }
	  
	private void adjustUIState()
	{
		field.setText(getPackageName());
	}	
}