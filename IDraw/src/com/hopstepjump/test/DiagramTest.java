package com.hopstepjump.test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;

import edu.umd.cs.jazz.util.*;

public class DiagramTest
{
	private JFrame frame;
	private DiagramFacet diagram;
	private JButton undo;
	private JButton redo;
	
	public static void main(String args[])
	{
		new DiagramTest().run();
	}
	
	private void run()
	{
		frame = new JFrame("Diagram Test");
		frame.setPreferredSize(new Dimension(600, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add the diagram and view
		final JPanel panel = new JPanel(new BorderLayout());
		BasicDiagramGem dgem = new BasicDiagramGem(new DiagramReference("1"), false, null, false);
		diagram = dgem.getDiagramFacet();
		ZCanvas canvas = new ZCanvas();
		panel.add(canvas, BorderLayout.CENTER);
		BasicDiagramViewGem vgem = new BasicDiagramViewGem(diagram, null, canvas, new UDimension(1, 1), Color.WHITE, false);
		diagram.addListener("d", vgem.getDiagramListenerFacet());
				
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		
		// buttons
		JPanel buttons = new JPanel();
		JButton circle = new JButton("Circle");
		circle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addFigure(diagram, false);
				diagram.commitTransaction();
			}
		});
		buttons.add(circle);
		JButton square = new JButton("Square");
		square.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addFigure(diagram, true);
				diagram.commitTransaction();
			}
		});
		buttons.add(square);
		JButton both = new JButton("Both");
		both.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addFigure(diagram, false);
				diagram.checkpointCommitTransaction();
				addFigure(diagram, true);
				diagram.commitTransaction();
			}
		});
		buttons.add(both);
		JButton del= new JButton("Delete");
		del.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (FigureFacet f : diagram.getFigures())
					if (rand(0, 10) > 5)
						diagram.remove(f);
				diagram.commitTransaction();
			}
		});
		buttons.add(del);
		JButton random = new JButton("Random");
		random.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (FigureFacet f : diagram.getFigures())
					((SimpleFigure) f).randomChange();  // mocked out to random change
				diagram.commitTransaction();
			}
		});
		buttons.add(random);
		undo = new JButton("<Undo>");
		undo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				diagram.undoTransaction();
			}
		});
		buttons.add(undo);
		redo = new JButton("<Redo>");
		redo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				diagram.redoTransaction();
			}
		});
		buttons.add(redo);
		panel.add(buttons, BorderLayout.NORTH);
		setUndoRedoStates();

		diagram.addListener("listener",
				new DiagramListenerFacet()
				{					
					public void refreshViewAttributes()
					{
					}
					public void haveModifications(DiagramChange[] changes)
					{
						setUndoRedoStates();
					}
				});
		
		// register the recreator
		PersistentFigureRecreatorRegistry.registry = new BasicPersistentFigureRecreatorRegistry();
		PersistentFigureRecreatorRegistry.registry.registerRecreator(new SimpleFigureRecreator());
		
		frame.pack();
		frame.setVisible(true);
	}

	private void setUndoRedoStates()
	{
		undo.setEnabled(diagram.getTransactionPosition() > 0);
		redo.setEnabled(diagram.getTransactionPosition() < diagram.getTotalTransactions());
	}
	
	private void addFigure(final DiagramFacet diagram, boolean rect)
	{
		double w = rand(50, 200);
		FigureFacet f = new SimpleFigure(
				diagram,
				new UPoint(rand(20, 300), rand(20, 300)),
				new UDimension(w, w),
				rect,
				randomColor());
		diagram.add(f);
	}

	public static double rand(int low, int high)
	{
		return Math.random() * (high - low) + low;
	}

	public static Color randomColor()
	{
		Color cols[] = {Color.BLUE, Color.CYAN, Color.RED, Color.WHITE, Color.GREEN, Color.LIGHT_GRAY, Color.YELLOW};
		return cols[(int) Math.round(rand(0, 6))];
	}
}
