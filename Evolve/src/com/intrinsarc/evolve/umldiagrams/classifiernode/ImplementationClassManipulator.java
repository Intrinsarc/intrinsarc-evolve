package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

public class ImplementationClassManipulator extends FieldPopupManipulator
{
	private Popup pop;
	private JTextField field;
	private String implClass;

	public ImplementationClassManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure, String implClass, boolean bad)
	{
		super(coordinator, diagramView, figure, bad);
		this.implClass = implClass;
		this.bad |= implClass.length() > 0 && !ElementErrorChecker.isValidClassName(implClass);
	}

	public void setUpPopup()
	{
  	JPanel panel = new JPanel(new BorderLayout());
  	panel.setBorder(
  			BorderFactory.createCompoundBorder(
  					BorderFactory.createLineBorder(Color.GRAY),
  					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
  	JLabel label = new JLabel("Class:");
  	label.setBorder(new EmptyBorder(0, 0, 0, 5));
  	field = new JTextField();

  	panel.add(label, BorderLayout.WEST);
  	panel.add(field, BorderLayout.CENTER);
  	
		field.setText(implClass);
  	field.setEditable(false);
  	
  	field.setForeground(bad ? new Color(200, 100, 100) : Color.GRAY.darker());
  	ZSwing txt = new ZSwing(new ZCanvas(), new JButton("Aj"));
  	int height = (int) txt.getBounds().getHeight(); 
  	field.setPreferredSize(new Dimension(250, height));

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
					finishManipulator();		
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
		finishManipulator();
	}
}
