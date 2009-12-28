/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * This is a ZLoadable module that must be loaded into an application like
 * HiNote to run.
 *
 * @author Lance Good
 */
public class LayoutZLoadable implements Runnable, ZLoadable, ActionListener {

    // Constants to denote the type of layout
    final int LINE = 0;
    final int ELLIPSE = 1;
    final int RECT = 2;
    final int QUAD = 3;
    final int CUBIC = 4;
    final int ARC = 5;
    final int POLY = 6;
    final int GRID = 7;

    // Items from the ZLoadable interface
    JMenuBar menubar = null;
    ZCamera camera = null;
    ZDrawingSurface surface = null;
    ZLayerGroup layer = null;

    // The menu bar items
    JMenuItem setLayoutOptions = null;
    JMenuItem doLayout = null;

    // The main window
    JDialog optionsDialog;

    // The two main options for layout
    JRadioButton gridOption;
    JRadioButton pathOption;

    // The options within Path layout
    JRadioButton lineOption;
    JRadioButton ellipseOption;
    JRadioButton rectOption;
    JRadioButton quadOption;
    JRadioButton cubicOption;
    JRadioButton arcOption;
    JRadioButton polyOption;

    // The grid options
    JComboBox axisBox;
    JTextField cellsField;
    JTextField widthField;
    JTextField heightField;
    JTextField vertSpaceField;
    JTextField horizontalSpaceField;
    
    // The number formats used by the grid layout
    NumberFormat intFormat = NumberFormat.getNumberInstance();
    NumberFormat doubleFormat = NumberFormat.getNumberInstance();
    String[] axisOptions = {"Columns","Rows"};

    // Button used to dismiss the dialog
    JButton done;

    // The current layout manager
    ZLayoutManager layoutManager;

    // The current layout option within Path layout
    int currentLayout = LINE;

    // The general path used by the polyOption
    GeneralPath polyPath = null;

    // The button pressed to specify the polyPath
    JButton polyButton;

    // An ActionEvent pointer to reuse
    ActionEvent action;

    // The current combo box selection
    int rowsOrColumns;

    // The current number of cells
    int numCells = 2;

    // The current cell width
    double cellWidth = 50.0;

    // The current cell height
    double cellHeight = 50.0;

    // The current vertical spacing
    double vertSpacing = 25.0;

    // The current horizontal spacing
    double horizontalSpacing = 25.0;

    /**
     * Constructor to build GUI
     */
    public LayoutZLoadable() {
        optionsDialog = new JDialog();
        optionsDialog.setTitle("Layout Options");
        optionsDialog.setSize(400,350);

        // The top level - grid or path layout
        pathOption = new JRadioButton("Path Layout");
        gridOption = new JRadioButton("Grid Layout");

        pathOption.addActionListener(this);
        gridOption.addActionListener(this);

        ButtonGroup layoutOption = new ButtonGroup();
        layoutOption.add(pathOption);
        layoutOption.add(gridOption);
        pathOption.setSelected(true);


        // Within the path layout - line, ellipse, rect, quad, cubic, arc, & manual
        lineOption = new JRadioButton("Line");
        ellipseOption = new JRadioButton("Ellipse");
        rectOption = new JRadioButton("Rectangle");
        quadOption = new JRadioButton("Quadratic");
        cubicOption = new JRadioButton("Cubic");
        arcOption = new JRadioButton("Arc");
        polyOption = new JRadioButton("Selected Polyline");

        lineOption.addActionListener(this);
        ellipseOption.addActionListener(this);
        rectOption.addActionListener(this);
        quadOption.addActionListener(this);
        cubicOption.addActionListener(this);
        arcOption.addActionListener(this);
        polyOption.addActionListener(this);

        ButtonGroup layoutsGroup = new ButtonGroup();
        layoutsGroup.add(lineOption);
        layoutsGroup.add(ellipseOption);
        layoutsGroup.add(rectOption);
        layoutsGroup.add(quadOption);
        layoutsGroup.add(cubicOption);
        layoutsGroup.add(arcOption);
        layoutsGroup.add(polyOption);
        lineOption.setSelected(true);

        polyButton = new JButton("Set Polyline");
        polyButton.addActionListener(this);
        polyButton.setEnabled(false);

        JPanel linePanel = new JPanel();
        JPanel ellipsePanel = new JPanel();
        JPanel rectPanel = new JPanel();
        JPanel quadPanel = new JPanel();
        JPanel cubicPanel = new JPanel();
        JPanel arcPanel = new JPanel();
        JPanel polyPanel = new JPanel();

        linePanel.setLayout(new BorderLayout());
        ellipsePanel.setLayout(new BorderLayout());
        rectPanel.setLayout(new BorderLayout());
        quadPanel.setLayout(new BorderLayout());
        cubicPanel.setLayout(new BorderLayout());
        arcPanel.setLayout(new BorderLayout());
        polyPanel.setLayout(new GridLayout(2,1));

        linePanel.add(lineOption,BorderLayout.CENTER);
        ellipsePanel.add(ellipseOption,BorderLayout.CENTER);
        rectPanel.add(rectOption,BorderLayout.CENTER);
        quadPanel.add(quadOption,BorderLayout.CENTER);
        cubicPanel.add(cubicOption,BorderLayout.CENTER);
        arcPanel.add(arcOption,BorderLayout.CENTER);
        polyPanel.add(polyOption);
        polyPanel.add(polyButton);

        lineOption.setHorizontalAlignment(SwingConstants.LEFT);
        ellipseOption.setHorizontalAlignment(SwingConstants.LEFT);
        rectOption.setHorizontalAlignment(SwingConstants.LEFT);
        quadOption.setHorizontalAlignment(SwingConstants.LEFT);
        cubicOption.setHorizontalAlignment(SwingConstants.LEFT);
        arcOption.setHorizontalAlignment(SwingConstants.LEFT);
        polyOption.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(new EmptyBorder(0,20,0,0));
        optionsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        optionsPanel.add(linePanel,gbc);

        gbc.gridy = GridBagConstraints.RELATIVE;

        optionsPanel.add(ellipsePanel,gbc);
        optionsPanel.add(rectPanel,gbc);
        optionsPanel.add(quadPanel,gbc);
        optionsPanel.add(cubicPanel,gbc);
        optionsPanel.add(arcPanel,gbc);

        gbc.weighty = 0;

        optionsPanel.add(polyPanel,gbc);


	// The grid layout panel
	intFormat.setParseIntegerOnly(true);
	doubleFormat.setMaximumIntegerDigits(8);
	doubleFormat.setMaximumFractionDigits(8);

	JLabel axisLabel = new JLabel("Rows or Columns:");
	JLabel cellsLabel = new JLabel("Number of Cells:");
	JLabel widthLabel = new JLabel("Cell Width:");
	JLabel heightLabel = new JLabel("Cell Height:");
	JLabel vertSpaceLabel = new JLabel("Vertical Spacing");
	JLabel horizontalSpaceLabel = new JLabel("Horizontal Spacing");

	axisBox = new JComboBox(axisOptions);
	cellsField = new JTextField(Integer.toString(numCells));
	widthField = new JTextField(Double.toString(cellWidth));
	heightField = new JTextField(Double.toString(cellHeight));
	vertSpaceField = new JTextField(Double.toString(vertSpacing));
	horizontalSpaceField = new JTextField(Double.toString(horizontalSpacing));

	axisBox.addActionListener(this);
	cellsField.addActionListener(this);
	widthField.addActionListener(this);
	heightField.addActionListener(this);
	vertSpaceField.addActionListener(this);
	horizontalSpaceField.addActionListener(this);
	
	axisBox.setEnabled(false);
	cellsField.setEnabled(false);
	widthField.setEnabled(false);
	heightField.setEnabled(false);
	vertSpaceField.setEnabled(false);
	horizontalSpaceField.setEnabled(false);

	JPanel gridPanel = new JPanel();
	gridPanel.setLayout(new GridLayout(12,1));
	gridPanel.add(axisLabel);
	gridPanel.add(axisBox);
	gridPanel.add(cellsLabel);
	gridPanel.add(cellsField);
	gridPanel.add(widthLabel);
	gridPanel.add(widthField);
	gridPanel.add(heightLabel);
	gridPanel.add(heightField);
	gridPanel.add(vertSpaceLabel);
	gridPanel.add(vertSpaceField);
	gridPanel.add(horizontalSpaceLabel);
	gridPanel.add(horizontalSpaceField);
	gridPanel.setBorder(new EmptyBorder(0,20,0,0));

        // Add everything to the two main panels
        JPanel pathOptionPanel = new JPanel();
        JPanel gridOptionPanel = new JPanel();
        pathOptionPanel.setLayout(new BorderLayout());
        gridOptionPanel.setLayout(new BorderLayout());
        pathOptionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        gridOptionPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        pathOptionPanel.add(pathOption,BorderLayout.NORTH);
        pathOptionPanel.add(optionsPanel,BorderLayout.CENTER);
        gridOptionPanel.add(gridOption,BorderLayout.NORTH);
	gridOptionPanel.add(gridPanel,BorderLayout.CENTER);

        // Now create the main window
        JPanel holder = new JPanel();
        holder.setLayout(new GridLayout(1,2));
        holder.add(pathOptionPanel);
        holder.add(gridOptionPanel);

        done = new JButton("Done");
        done.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.setBorder(new EmptyBorder(5,50,5,50));
        buttons.add(done);

        optionsDialog.getContentPane().setLayout(new BorderLayout());
        optionsDialog.getContentPane().add(holder,BorderLayout.CENTER);
        optionsDialog.getContentPane().add(buttons,BorderLayout.SOUTH);
    }

    /**
     * Action Listener interface for all the buttons on this components
     * dialog.
     * @param The ActionEvent from the appropriate button.
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == setLayoutOptions) {
            // Show the dialog
            optionsDialog.setVisible(true);
        }
        else if (ae.getSource() == doLayout) {
            // First set the layout manager
            Rectangle2D bounds = camera.getViewBounds();
            switch (currentLayout) {
            case LINE:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new Line2D.Double(bounds.getWidth()/10.0+bounds.getX(),bounds.getHeight()/10.0+bounds.getY(),bounds.getX()+bounds.getWidth()-bounds.getWidth()/10.0,bounds.getY()+bounds.getHeight()-bounds.getHeight()/10.0));
                ((ZPathLayoutManager)layoutManager).setClosed(false);
                break;
            case ELLIPSE:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new Ellipse2D.Double(bounds.getWidth()/10.0+bounds.getX(),bounds.getHeight()/10.0+bounds.getY(),(8.0/10.0)*bounds.getWidth(),(8.0/10.0)*bounds.getHeight()));
                ((ZPathLayoutManager)layoutManager).setClosed(true);
                break;
            case RECT:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new Rectangle2D.Double(bounds.getWidth()/10.0+bounds.getX(),bounds.getHeight()/10.0+bounds.getY(),(8.0/10.0)*bounds.getWidth(),(8.0/10.0)*bounds.getHeight()));
                ((ZPathLayoutManager)layoutManager).setClosed(true);
                break;
            case QUAD:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new QuadCurve2D.Double(bounds.getWidth()/10.0+bounds.getX(),(9.0/10.0)*bounds.getHeight()+bounds.getY(),bounds.getWidth()/2.0+bounds.getX(),bounds.getY()-bounds.getHeight()/1.5,(9.0/10.0)*bounds.getWidth()+bounds.getX(),(9.0/10.0)*bounds.getHeight()+bounds.getY()));
                ((ZPathLayoutManager)layoutManager).setClosed(false);
                break;
            case CUBIC:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new CubicCurve2D.Double(bounds.getWidth()/10.0+bounds.getX(),(1.0/2.0)*bounds.getHeight()+bounds.getY(),bounds.getWidth()/3.0+bounds.getX(),bounds.getY()-bounds.getHeight()/1.5,(2.0/3.0)*bounds.getWidth()+bounds.getX(),(2.5/1.5)*bounds.getHeight()+bounds.getY(),(9.0/10.0)*bounds.getWidth()+bounds.getX(),(1.0/2.0)*bounds.getHeight()+bounds.getY()));
                ((ZPathLayoutManager)layoutManager).setClosed(false);
                break;
            case ARC:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                ((ZPathLayoutManager)layoutManager).setShape(new Arc2D.Double(bounds.getWidth()/10.0+bounds.getX(),(1.0/4.0)*bounds.getHeight()+bounds.getY(),(8.0/10.0)*bounds.getWidth(),(8.0/10.0)*bounds.getHeight(),0.0,180.0,Arc2D.OPEN));
                ((ZPathLayoutManager)layoutManager).setClosed(false);
                break;
            case POLY:
		if (!(layoutManager instanceof ZPathLayoutManager)) {
		    layoutManager = new ZPathLayoutManager();
		}

                if (polyPath != null) {
                    ((ZPathLayoutManager)layoutManager).setShape(polyPath);
                    ((ZPathLayoutManager)layoutManager).setClosed(false);
                }
                break;
            case GRID:
                // Here for later Grid Options
		if (!(layoutManager instanceof ZGridLayoutManager)) {
		    layoutManager = new ZGridLayoutManager();
		}

		rowsOrColumns = axisBox.getSelectedIndex()+1;
		numCells = formatIntField(cellsField,numCells);
		cellWidth = formatDoubleField(widthField,cellWidth);
		cellHeight = formatDoubleField(heightField,cellHeight);
		vertSpacing = formatDoubleField(vertSpaceField,vertSpacing);
		horizontalSpacing = formatDoubleField(horizontalSpaceField,horizontalSpacing);

		((ZGridLayoutManager)layoutManager).setGridAxis(rowsOrColumns);
		((ZGridLayoutManager)layoutManager).setNumCells(numCells);
		((ZGridLayoutManager)layoutManager).setCellWidth(cellWidth);
		((ZGridLayoutManager)layoutManager).setCellHeight(cellHeight);
		((ZGridLayoutManager)layoutManager).setVerticalSpacing(vertSpacing);
		((ZGridLayoutManager)layoutManager).setHorizontalSpacing(horizontalSpacing);
		((ZGridLayoutManager)layoutManager).setLayoutStartPoint(bounds.getX()+bounds.getWidth()/10.0,
									bounds.getY()+bounds.getHeight()/10.0);

                break;
            }

            // Perform the layout on the currently selected nodes
            // First group them - then layout - then ungroup
            ArrayList nodes = ZSelectionManager.getSelectedNodes(layer);

            // Enough nodes to layout?
            if (nodes.size() <= 1) {
                return;
            }

            // create a new layout node, link it to the layer node
            ZLayoutGroup layoutGroup = new ZLayoutGroup();

            layer.addChild(layoutGroup);

            // move selected nodes to the layout group
            ZNode node;
            Vector topNodes = new Vector();
            for (Iterator i=nodes.iterator(); i.hasNext();) {
                node = (ZNode)i.next();
                node.editor().getTop().reparent(layoutGroup);
            }

            // Now we layout
            if ((currentLayout != POLY) || (polyPath != null)) {
                layoutGroup.setLayoutManager(layoutManager);
                layoutGroup.validate();       // Force immediate validation
            }

            // Now we remove the group
            layoutGroup.extract();
        }
        else if (ae.getSource() == done) {
            // Dismiss the dialog
            optionsDialog.setVisible(false);
        }
        else if (ae.getSource() == gridOption) {
            // Grid Layout was selected - disable path options
            lineOption.setEnabled(false);
            ellipseOption.setEnabled(false);
            rectOption.setEnabled(false);
            quadOption.setEnabled(false);
            cubicOption.setEnabled(false);
            arcOption.setEnabled(false);
            polyOption.setEnabled(false);
            polyButton.setEnabled(false);

	    axisBox.setEnabled(true);
	    cellsField.setEnabled(true);
	    widthField.setEnabled(true);
	    heightField.setEnabled(true);
	    vertSpaceField.setEnabled(true);
	    horizontalSpaceField.setEnabled(true);

            currentLayout = GRID;
	    layoutManager = new ZGridLayoutManager(0,0);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == pathOption) {
            // Path Layout was selected - disable Grid options
	    axisBox.setEnabled(false);
	    cellsField.setEnabled(false);
	    widthField.setEnabled(false);
	    heightField.setEnabled(false);
	    vertSpaceField.setEnabled(false);
	    horizontalSpaceField.setEnabled(false);

            lineOption.setEnabled(true);
            ellipseOption.setEnabled(true);
            rectOption.setEnabled(true);
            quadOption.setEnabled(true);
            cubicOption.setEnabled(true);
            arcOption.setEnabled(true);
            polyOption.setEnabled(true);
	    polyButton.setEnabled(false);

	    if (lineOption.isSelected()) {
		currentLayout = LINE;
	    }
	    else if (ellipseOption.isSelected()) {
		currentLayout = ELLIPSE;
	    }
	    else if (rectOption.isSelected()) {
		currentLayout = RECT;
	    }
	    else if (quadOption.isSelected()) {
		currentLayout = QUAD;
	    }
	    else if (cubicOption.isSelected()) {
		currentLayout = CUBIC;
	    }
	    else if (arcOption.isSelected()) {
		currentLayout = ARC;
	    }
	    else if (polyOption.isSelected()) {
		currentLayout = POLY;
		polyButton.setEnabled(true);
	    }

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == lineOption) {

            currentLayout = LINE;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == ellipseOption) {

            currentLayout = ELLIPSE;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == rectOption) {

            currentLayout = RECT;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == quadOption) {

            currentLayout = QUAD;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == cubicOption) {

            currentLayout = CUBIC;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == arcOption) {

            currentLayout = ARC;
            polyButton.setEnabled(false);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == polyOption) {

            currentLayout = POLY;
            polyButton.setEnabled(true);

            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
        }
        else if (ae.getSource() == polyButton) {

            // We need to see if a single ZPolyline is selected
            // - if so - set that as the path - otherwise
            // don't let this option be selected
            ArrayList nodes = ZSelectionManager.getSelectedNodes(layer);
            boolean reset = false;

            if (nodes.size() == 1) {
                ZNode node = (ZNode)nodes.get(0);
                if (node instanceof ZVisualLeaf) {
                    ZVisualLeaf leaf = (ZVisualLeaf)node;
                    ZVisualComponent vc = leaf.getFirstVisualComponent();
                    if (vc instanceof ZPolyline) {
                        polyPath = new GeneralPath(((ZPolyline)vc).getShape());
                        polyPath.transform(node.editor().getTransformGroup().getTransform());
                    }

                }
            }
        }
	else if (ae.getSource() == axisBox) {
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);	    
	}
	else if (ae.getSource() == cellsField) {
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
	}
	else if (ae.getSource() == widthField) {	
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
	}
	else if (ae.getSource() == heightField) {	
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
	}
	else if (ae.getSource() == vertSpaceField) {	
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);
	}
	else if (ae.getSource() == horizontalSpaceField) {
            action = new ActionEvent(doLayout,0,"Fake Event");
            actionPerformed(action);	    
	}
    }

    /**
     * Method that ZLoadable calls to finish initialization after
     * constructor and ZLoadable components set
     */
    public void run() {
        JMenu layoutMenu = new JMenu("Layout");
        setLayoutOptions = new JMenuItem("Set Layout Options");
        doLayout = new JMenuItem("Do Layout");

        setLayoutOptions.addActionListener(this);
        doLayout.addActionListener(this);

        layoutMenu.add(setLayoutOptions);
        layoutMenu.add(doLayout);
        menubar.add(layoutMenu);
        menubar.revalidate();

        optionsDialog.setLocationRelativeTo(menubar);
    }

    /**
     * Method to set the ZCamera
     * Part of the ZLoadable interface.
     * @param The camera that this component should use.
     */
    public void setCamera(ZCamera aCamera) {
        camera = aCamera;
    }

    /**
     * Method to set the ZDrawingSurface
     * Part of the ZLoadable interface.
     * @param The surface that this component should use.
     */
    public void setDrawingSurface(ZDrawingSurface aSurface) {
        surface = aSurface;
    }

    /**
     * Method to set the drawing layer
     * Part of the ZLoadable interface.
     * @param The drawing layer that this component should use
     */
    public void setLayer(ZLayerGroup aLayer) {
        layer = aLayer;
    }

    /**
     * Method to set the Menubar from the appropriate window.
     * Part of the ZLoadable interface.
     * @param The menubar that this component should use.
     */
    public void setMenubar(JMenuBar aMenubar) {
        menubar = aMenubar;
    }

    /////////////////////////////////////////////////////////////////
    // 
    // Textfield formatting methods
    //
    /////////////////////////////////////////////////////////////////

    /**
     * Formats a double value in a text field
     */
    public double formatDoubleField(JTextField field, double prevValue) {
	try {
	    Number num = doubleFormat.parse(field.getText());
	    String newText = doubleFormat.format(num.doubleValue());
	    field.setText(newText);
	}
	catch (ParseException pe) {
	    String newText = doubleFormat.format(prevValue);
	    field.setText(newText);
	}

	try {
	    return doubleFormat.parse(field.getText()).doubleValue();
	}
	catch (ParseException pe) {
	    return prevValue;
	}
    }

    /**
     * Format a double value using the double formatter
     */
    public String formatDouble(double val) {
	return doubleFormat.format(val);
    }

    /**
     * Format a double object using the double formatter
     */
    public String formatDouble(Double obj) {
	return doubleFormat.format(obj.doubleValue());
    }
    
    /**
     * Formats an int value in a text field
     */
    public int formatIntField(JTextField field, int prevValue) {
	try {
	    Number num = intFormat.parse(field.getText());
	    String newText = intFormat.format(num.intValue());
	    field.setText(newText);
	}
	catch (ParseException pe) {
	    String newText = intFormat.format(prevValue);
	    field.setText(newText);
	}

	try {
	    return intFormat.parse(field.getText()).intValue();
	}
	catch (ParseException pe) {
	    return prevValue;
	}
    }

    /**
     * Format an int value using the int formatter
     */    
    public String formatInt(int val) {
	return intFormat.format(val);
    }

    /**
     * Format an int object using the int formatter
     */
    public String formatInt(Integer obj) {
	return intFormat.format(obj.intValue());
    }
    
}
