package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import printpreview.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.swing.*;

import edu.umd.cs.jazz.util.*;

public class PrintDiagramAction extends AbstractAction
{
	public static final ImageIcon PRINTER_ICON = IconLoader.loadIcon("printer.png");
	private ToolCoordinatorFacet coordinator;

	public PrintDiagramAction(ToolCoordinatorFacet coordinator)
	{
		super("Print current diagram", PRINTER_ICON);
		this.coordinator = coordinator;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int percentScale = 100;
		
		DiagramFacet diagram = GlobalPackageViewRegistry.activeRegistry.getFocussedView().getCurrentDiagramView().getDiagram(); 
		DiagramViewFacet view =
			new BasicDiagramViewGem(
					diagram,
					null,
					new ZCanvas(),
					new UDimension(1, 1),
					Color.WHITE,
					false).getDiagramViewFacet();

		UBounds bounds = view.getDrawnBounds();
		UPoint br = bounds.getBottomRightPoint();
		view.getCanvas().setBounds(new Rectangle(0, 0, (int) br.getX() + 1, (int) br.getY() + 1));
		
		final PrintPreviewPanel panel = new PrintPreviewPanel(view.getCanvas(), percentScale);
		ButtonGroup pf = new ButtonGroup();
		final JRadioButton pf1 = new JRadioButton("Portrait");
		pf1.setSelected(true);
		JRadioButton pf2 = new JRadioButton("Landscape");
		pf.add(pf1);
		pf.add(pf2);
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.setOrientation(e.getSource() == pf1);
			}
		};
		pf1.addActionListener(listener);
		pf2.addActionListener(listener);
		
		JLabel empty1 = new JLabel("    ");
		JLabel empty2 = new JLabel("    ");
		
		JLabel scaleLabel = new JLabel("%Scale");
		final SpinnerNumberModel model = new SpinnerNumberModel(percentScale, 10, 500.0, 10);
		JSpinner scale = new JSpinner(model);
		scale.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				panel.setScale((int) Math.round(((Double) model.getNumber())));
			}
		});
		
		JButton print = new JButton("Print...");
		JButton cancel = new JButton("Cancel");
		int button = coordinator.invokeAsDialog(PRINTER_ICON, "Print preview", panel, new JComponent[]{pf1, pf2, empty1, scaleLabel, scale, empty2, print, cancel}, 0, null);
		if (button == 0)
			panel.print();
	}
}
