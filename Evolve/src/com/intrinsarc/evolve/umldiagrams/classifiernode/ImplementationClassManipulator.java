package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class ImplementationClassManipulator extends FieldPopupManipulator
{
	private Popup pop;
	private JTextField field;
	private JButton auto;
	private boolean forceReplace;
	private boolean composite;

	public ImplementationClassManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure, boolean composite)
	{
		super(coordinator, diagramView, figure, composite);
		this.composite = composite;
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
  	auto = new JButton();
  	field = new JTextField();
  	JPanel button = new JPanel(new BorderLayout());
  	button.setBorder(new EmptyBorder(0, 5, 0, 0));
  	button.add(auto, BorderLayout.EAST);
  	
  	panel.add(label, BorderLayout.WEST);
  	panel.add(field, BorderLayout.CENTER);
  	panel.add(button, BorderLayout.EAST);
  	
  	// possibly readonly
  	field.setEnabled(!readOnly);
  	auto.setEnabled(!readOnly);

  	adjustUIState();

  	// set the field to be quite wide
  	field.setPreferredSize(new Dimension(200, 0));
  	
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
  	
  	auto.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// handle composite
				if (composite)
				{
					if (!hasImplementationClassField())
						forceReplace = true;
					field.setText("");
					setTextAndFinish();
				}
				else
				{
					if (!hasImplementationClassField())
					{
						forceReplace = true;
						adjustUIState();
					}
					else
					{
						// set the implementation class according to the default package of the home + the name of the element
						String homePackage = getHomePackage();
						field.setText((homePackage != null && homePackage.length() > 0 ? homePackage + "." : "") + getElement().getName());
					}
					field.requestFocus();
				}
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
		if (forceReplace)
		{
			// replace the stereo
			Stereotype componentStereo = 
				GlobalSubjectRepository.repository.findStereotype(
						UML2Package.eINSTANCE.getClass_(),
						CommonRepositoryFunctions.COMPONENT);
	  	StereotypeUtilities.formAddRawStereotypeTransaction(
	  			coordinator,
	  			getElement(),
	  			componentStereo);
		}
		
		boolean hasStereo = hasImplementationClassField();
		if (hasStereo && !field.getText().equals(getImplementationClassName()))
		{			
			coordinator.startTransaction("Set implementation class", "Unset implementation class");
			StereotypeUtilities.setStringRawStereotypeAttribute(
					getElement(),
					CommonRepositoryFunctions.IMPLEMENTATION_CLASS,
					field.getText());
			coordinator.commitTransaction();						
		}
		finishManipulator();		
	}
	
  public static String getImplementationClassName(Element element)
  {
  	DEElement comp = GlobalDeltaEngine.engine.locateObject(element).asElement();
  	DEStratum home = comp.getHomeStratum();
  	return comp.getImplementationClass(home);	  	
  }

  private String getImplementationClassName()
  {
  	return getImplementationClassName(getElement());
  }
  
	private String getHomePackage()
	{
  	DEComponent comp = GlobalDeltaEngine.engine.locateObject(getElement()).asComponent();
  	DEStratum home = comp.getHomeStratum();
  	return StereotypeUtilities.extractStringProperty(
  			(Element) home.getRepositoryObject(),
  			CommonRepositoryFunctions.DEFAULT_PACKAGE);
	}
	
	private void adjustUIState()
	{
		// get the impl class name
  	boolean hasStereo = hasImplementationClassField() || forceReplace;
  	if (!forceReplace)
  		field.setText(getImplementationClassName());
  	String autoText = hasStereo ? "Auto" : "Replace";
  	if (composite)
  		autoText = "Remove";
  	auto.setText(autoText);
  	field.setEnabled(hasStereo && !readOnly);
	}
	
	private boolean hasImplementationClassField()
	{
  	Map<String, DeltaPair> properties = StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(getElement());
  	DeltaPair impl = properties.get(CommonRepositoryFunctions.IMPLEMENTATION_CLASS);
  	return impl != null;
	}
}
