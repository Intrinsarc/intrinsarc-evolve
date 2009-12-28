/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * A simple grid layout.  Lays out objects into equally sized cells.  Allows
 * for the specification of the number of rows or columns, the cell width
 * and height, the vertical and horizontal spacing, and the upper left grid
 * layout start point.  This layout preserves object aspect ratios.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Lance Good
 * @see     ZNode
 * @see     edu.umd.cs.jazz.util.ZLayout
 */
public class ZGridLayoutManager implements ZLayoutManager, ZSerializable {
    
    // This layout specifies the number of columns
    public static final int COLUMN = 1;

    // This layout specifies the number of rows
    public static final int ROW = 2;    

    // The default start point
    protected static final Point2D DEFAULT_START_POINT = new Point2D.Double(0,0);

    // The default cell width
    protected static final double DEFAULT_CELL_WIDTH = 50.0;

    // The default cell height
    protected static final double DEFAULT_CELL_HEIGHT = 50.0;

    // The default horizontal spacing
    protected static final double DEFAULT_HORIZONTAL_SPACING = 25.0;

    // The default vertical spacing
    protected static final double DEFAULT_VERTICAL_SPACING = 25.0;
        
    // Whether the layout specifies rows or columns
    int rowsOrColumns;

    // The number of rows or columns depending on the layout
    int numCells;

    // Should this layout manager layout its children in inverse order
    boolean invertChildren = false;
    
    // The point from which the layout should be started
    Point2D layoutStartPoint = (Point2D)DEFAULT_START_POINT.clone();

    // The cell width
    double cellWidth = DEFAULT_CELL_WIDTH;

    // The cell height
    double cellHeight = DEFAULT_CELL_HEIGHT;

    // The horizontal spacing in the table
    double horizontalSpacing = DEFAULT_HORIZONTAL_SPACING;

    // The vertical spacing in the table
    double verticalSpacing = DEFAULT_VERTICAL_SPACING;

    /**
     * The default no arg constructor
     */
    public ZGridLayoutManager() {
    }

    /**
     * The default constructor
     * @param rowsOrColumns Whether this manager should fix the number of rows or columns
     * @param numCells The number of rows or columns to which this layout manager is fixed
     */ 
    public ZGridLayoutManager(int rowsOrColumns, int numCells) {
	this.rowsOrColumns = rowsOrColumns;
	this.numCells = numCells;
    }

    /**
     * Constructor that allows cell dimensions to be specified
     * @param rowsOrColumns Whether this manager should fix the number of rows or columns
     * @param numCells The number of rows or columns to which this layout manager is fixed
     * @param width The row width
     * @param height The row height
     */ 
    public ZGridLayoutManager(int rowsOrColumns, int numCells, double width, double height) {
	this(rowsOrColumns,numCells);
	this.cellWidth = width;
	this.cellHeight = height;
    }

    /**
     * Constructor that allows cell dimensions to be specified
     * @param rowsOrColumns Whether this manager should fix the number of rows or columns
     * @param numCells The number of rows or columns to which this layout manager is fixed
     * @param width The row width
     * @param height The row height
     * @param horizontalSpacing The horizontal spacing between columns
     * @param verticalSpacing The vertical spacing between rows
     */ 
    public ZGridLayoutManager(int rowsOrColumns, int numCells, double width, double height, double horizontalSpacing, double verticalSpacing) {
	this(rowsOrColumns,numCells,width,height);
	this.horizontalSpacing = horizontalSpacing;
	this.verticalSpacing = verticalSpacing;
    }

    /**
     * Constructor that allows cell dimensions to be specified
     * @param rowsOrColumns Whether this manager should fix the number of rows or columns
     * @param numCells The number of rows or columns to which this layout manager is fixed
     * @param width The row width
     * @param height The row height
     * @param horizontalSpacing The horizontal spacing between columns
     * @param verticalSpacing The vertical spacing between rows
     * @param layoutStartX The layout start x coordinate (in the coordinate system of the parent node being laid out)
     * @param layoutStartX The layout start y coordinate (in the coordinate system of the parent node being laid out)
     */ 
    public ZGridLayoutManager(int rowsOrColumns, int numCells, double width, double height, double horizontalSpacing, double verticalSpacing, double layoutStartX, double layoutStartY) {
	this(rowsOrColumns,numCells,width,height,horizontalSpacing,verticalSpacing);
	layoutStartPoint.setLocation(layoutStartX,layoutStartY);
    }

    /**
     * Gets whether this grid is specified by rows or columns
     * Possible values are:
     *   1 - COLUMN
     *   2 - ROW
     * @return Whether this rid is specified by rows or columns
     */
    public int getGridAxis() {
	return rowsOrColumns;
    }

    /**
     * Sets whether this grid is specified by rows or columns
     * Possible values are:
     *   1 - COLUMN
     *   2 - ROW
     * @param rowsOrColumns Whether this rid is specified by rows or columns
     */
    public void setGridAxis(int rowsOrColumns) {
	this.rowsOrColumns = rowsOrColumns;
    }

    /**
     * Sets whether this layout manager should invert its children before
     * laying them out.  That is, should it lay out the children from front
     * to back rather than back to front
     */
    public void setInvertChildren(boolean invertChildren) {
	this.invertChildren = invertChildren;
    }

    /**
     * Gets whether this layout manager inverts its children before
     * laying them out.  That is, does it lay out the children from front
     * to back rather than back to front     
     */
    public boolean getInvertChildren() {
	return invertChildren;
    }
    
    /**
     * Gets the number of cells on the grid axis
     * @return The number of cells on the grid axis
     */
    public int getNumCells() {
	return numCells;
    }

    /**
     * Sets the number of cells on the grid axis
     * @param numCells The number of cells on the grid axis
     */
    public void setNumCells(int numCells) {
	this.numCells = numCells;
    }

    /**
     * Gets the cell width
     * @return The cell width
     */
    public double getCellWidth() {
	return cellWidth;
    }

    /**
     * Sets the cell width
     * @param width The new cell width
     */
    public void setCellWidth(double width) {
	this.cellWidth = width;
    }

    /**
     * Gets the cell height
     * @return The cell height
     */
    public double getCellHeight() {
	return cellHeight;
    }

    /**
     * Sets the cell height
     * @param height The new cell height
     */
    public void setCellHeight(double height) {
	this.cellHeight = height;
    }

    /**
     * Gets the current vertical spacing
     * @return The current vertical spacing
     */
    public double getVerticalSpacing() {
	return verticalSpacing;
    }

    /**
     * Sets the current vertical spacing
     * @param verticalSpacing The current vertical spacing
     */
    public void setVerticalSpacing(double verticalSpacing) {
	this.verticalSpacing = verticalSpacing;
    }

    /**
     * Gets the current horizontal spacing
     * @return The current horizontal spacing
     */
    public double getHorizontalSpacing() {
	return horizontalSpacing;
    }

    /**
     * Sets the current horizontal spacing
     * @param horizontalSpacing The current horizontal spacing
     */
    public void setHorizontalSpacing(double horizontalSpacing) {
	this.horizontalSpacing = horizontalSpacing;
    }

    /**
     * Gets the layout start point
     * @return The layout start point
     */
    public Point2D getLayoutStartPoint() {
	return (Point2D)layoutStartPoint.clone();
    }

    /**
     * Sets the layout start point
     * @param startX The layout start point x coordinate
     * @param startY The layout start point y coordinate
     */
    public void setLayoutStartPoint(double x, double y) {
	layoutStartPoint.setLocation(x,y);
    }
    
    /**
     * Sets the layout start point
     * @param startPoint The new layout start point
     */
    public void setLayoutStartPoint(Point2D startPoint) {
	layoutStartPoint.setLocation(startPoint);
    }    

    /**
     * Apply this manager's layout algorithm to the specified node's children.
     * @param node The node to apply this layout algorithm to.
     */
    public void doLayout(ZGroup node) {
	ZNode primary = node.editor().getNode();
	if (primary instanceof ZGroup) {

	    int numChildren = ((ZGroup)primary).getNumChildren();	    
	    ZNode[] children = ((ZGroup)primary).getChildrenReference();
	    if (invertChildren) {
		ZNode[] invertedChildren = new ZNode[numChildren];
		for(int i=0; i<numChildren; i++) {
		    invertedChildren[i] = children[numChildren-i-1];
		}
		children = invertedChildren;
	    }
	    
	    int numRows, numColumns = 0;
	    Point2D currentPt = (Point2D)layoutStartPoint.clone();

	    if (numChildren > 0) {

		// Determine if the layout prefers columns or rows
		if (rowsOrColumns == COLUMN) {
		    numColumns = numCells;
		    numRows = ((numCells % numChildren) == 0) ? (numCells / numChildren) : ((numCells / numChildren) + 1);

		    int curCount = 0;
		    while (curCount < numChildren) {

			// Layout a single row of children
			for(int i=0; (i<numColumns) && (curCount<numChildren); i++) {
			    ZSceneGraphEditor editor = children[curCount].editor();
			    ZNode child = editor.getNode();
			    ZBounds bounds = child.getBounds();

			    double scale = Math.min(cellWidth/bounds.getWidth(),cellHeight/bounds.getHeight());

			    ZTransformGroup transform = editor.getTransformGroup();			    
			    
			    // Create the new transform.
			    // This requires transforming the child's offset
			    // from the child's coordinate system to the
			    // parent's coordinate system
			    AffineTransform at = transform.getTransform();
			    at.setTransform(scale,
					    at.getShearX(),
					    at.getShearY(),
					    scale,
					    0,
					    0);
			    bounds.transform(at);
			    at.setTransform(at.getScaleX(),
					    at.getShearX(),
					    at.getShearY(),
					    at.getScaleY(),
					    currentPt.getX()-bounds.getX(),
					    currentPt.getY()-bounds.getY());
			    
			    // Position the child with the new transform
			    transform.setTransform(at);

			    // Update the current location
			    currentPt.setLocation(currentPt.getX()+cellWidth+horizontalSpacing,
						  currentPt.getY());
			    
			    // Increment the child count
			    curCount++;
			}

			// Increment the row position and reset the column position
			currentPt.setLocation(layoutStartPoint.getX(),
					      currentPt.getY()+cellHeight+verticalSpacing);
		    }
		}
		else if (rowsOrColumns == ROW) {		
		    numColumns = ((numCells % numChildren) == 0) ? (numCells / numChildren) : ((numCells / numChildren) + 1);
		    numRows = numCells;

		    int curCount = 0;
		    while (curCount < numChildren) {

			// Layout a single column of children
			for(int i=0; (i<numRows) && (curCount<numChildren); i++) {
			    ZSceneGraphEditor editor = children[curCount].editor();
			    ZNode child = editor.getNode();
			    ZBounds bounds = child.getBounds();

			    double scale = Math.min(cellWidth/bounds.getWidth(),cellHeight/bounds.getHeight());

			    ZTransformGroup transform = editor.getTransformGroup();			    
			    
			    // Create the new transform.
			    // This requires transforming the child's offset
			    // from the child's coordinate system to the
			    // parent's coordinate system
			    AffineTransform at = transform.getTransform();
			    at.setTransform(scale,
					    at.getShearX(),
					    at.getShearY(),
					    scale,
					    0,
					    0);
			    bounds.transform(at);
			    at.setTransform(at.getScaleX(),
					    at.getShearX(),
					    at.getShearY(),
					    at.getScaleY(),
					    currentPt.getX()-bounds.getX(),
					    currentPt.getY()-bounds.getY());
			    
			    // Position the child with the new transform
			    transform.setTransform(at);

			    // Update the current location
			    currentPt.setLocation(currentPt.getX(),
						  currentPt.getY()+cellHeight+verticalSpacing);

			    // Increment the child count
			    curCount++;
			}
		    
			// Increment the column postion and reset the row position
			currentPt.setLocation(currentPt.getX()+cellWidth+horizontalSpacing,
					      layoutStartPoint.getY());
		    }
		}

	    }
	}
    }

    /**
     * Apply this manager's layout algorithm to the specified node's children,
     * and animate the changes over time.
     * @param node The node to apply this layout algorithm to.
     * @param millis The number of milliseconds over which to animate layout changes.
     */
    public void doLayout(ZGroup node, int millis) {
    }

    /**
     * Notify the layout manager that a potentially recursive layout is starting.
     * This is called before any children are layed out.
     * @param node The node to apply this layout algorithm to.
     */
    public void preLayout(ZGroup node) {
    }

    /**
     * Notify the layout manager that the layout for this node has finished
     * This is called after all children and the node itself are layed out.
     * @param node The node to apply this layout algorithm to.
     */
    public void postLayout(ZGroup node) {
    }

    /**
     * Layout manager objects must provide a public clone method
     */
    public Object clone() {
	return null;
    }    

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
    }

    /**
     * Set some state of this object as it gets read back in.
     * After the object is created with its default no-arg constructor,
     * this method will be called on the object once for each bit of state
     * that was written out through calls to ZObjectOutputStream.writeState()
     * within the writeObject method.
     * @param fieldType The fully qualified type of the field
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     */
    public void setState(String fieldType, String fieldName, Object fieldValue){
    }
    
}
