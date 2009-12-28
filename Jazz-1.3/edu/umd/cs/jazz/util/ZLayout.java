/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import java.util.*;
import java.awt.geom.*;
import java.io.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZLayout</b> is a utility class that provides general-purpose layout mechanisms
 * to position nodes.
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Lance Good
 * @author Ben Bederson
 */
public class ZLayout implements Serializable {

    /**
     * Distributes a set of <code>nodes</codes> (those being ZNodes)
     * along the path specified by <code>coordinates</code>.  Assumes
     * exact spacing on a non-closed path with default tolerance.
     * @param nodes The nodes to be distributed.
     * @param coordinates The coordinates of the path.
     * @see #distribute(ZNode[], ArrayList, double, boolean)
     */
    static public void distribute(ZNode[] nodes, ArrayList coordinates) {
	distribute(nodes, coordinates, -1.0, false);
    }

    /**
     * Distributes a set of <code>nodes</code> (those being ZNodes) along
     * the (optionally closed) path specified by <code>coordinates</code>.
     * <code>exact</code> specifies, if false, that the algorithm should
     * run once using a first guess at spacing or, if true, that the
     * algorithm should attempt to evenly space the nodes using the entire
     * path.
     * @param nodes The nodes to be distributed.
     * @param coordinates The coordinates of the path.
     * @param exact Should the algorithm run once and stop or iterate to 
     *              get exact spacing.
     * @param closedPath Does the path represent a closed path?
     * @see #distribute(ZNode[], ArrayList, double, double, boolean, boolean)
     */
    static public void distribute(ZNode[] nodes, ArrayList coordinates, boolean exact, boolean closedPath) {
	// The number of nodes
	int numNodes;

	// The amount of space to start the algorithm
	double space;

	// The length of the path 
	double pathLength;

	// The total dimension of all the nodes
	double totalDim;

	// The current ZNode
	ZNode node;

	// The bounds for the current ZNode
	Rectangle2D bounds;
	
	if ((nodes.length == 0) || coordinates.isEmpty()) {
	    return;
	}

	pathLength = pathLength(coordinates);
	totalDim = 0.0;
	numNodes = 0;
	for(int i=0; i<nodes.length; i++) {
	    node = (ZNode)nodes[i].editor().getTop();
	    bounds = node.getBounds();
	    totalDim = totalDim + ((bounds.getWidth()+bounds.getHeight())/2.0);
	    numNodes++;
	}

	if (pathLength == 0.0) {
	    distribute(nodes, coordinates, 0.0, -1.0, false, closedPath);
	} else {
	    if (closedPath) {
		space = (pathLength - totalDim) / (numNodes);
	    }
	    else {
		space = (pathLength - totalDim) / (numNodes-1);
	    }
	    distribute(nodes, coordinates, space, -1.0, exact, closedPath);
	}
    }

    
    /**
     * Distributes a set of <code>nodes</code> (those being ZNodes) along
     * the (optionally closed) path specified by <code>coordinates</code>.
     * The algorithm will iterate until the nodes are placed along the
     * path within <code>tolerance</code> of the given path.
     * @param nodes The nodes to be distributed.
     * @param coordinates The coordinates of the path.
     * @param tolerance The error allowed in placing the nodes
     * @param closedPath Does the path represent a closed path?
     * @see #distribute(ZNode[], ArrayList, double, double, boolean, boolean)
     */
    static public void distribute(ZNode[] nodes, ArrayList coordinates, double tolerance, boolean closedPath) {
	// The number of nodes
	int numNodes;

	// The amount of space to start the algorithm
	double space;

	// The length of the path 
	double pathLength;

	// The total dimension of all the nodes
	double totalDim;

	// The current ZNode
	ZNode node;

	// The bounds for the current ZNode
	Rectangle2D bounds;
	
	if ((nodes.length == 0) || coordinates.isEmpty()) {
	    return;
	}

	pathLength = pathLength(coordinates);
	totalDim = 0.0;
	numNodes = 0;
	for(int i=0; i<nodes.length; i++) {
	    node = (ZNode)nodes[i].editor().getTop();
	    bounds = node.getBounds();
	    totalDim = totalDim + ((bounds.getWidth()+bounds.getHeight())/2.0);
	    numNodes++;
	}

	if (pathLength == 0.0) {
	    distribute(nodes, coordinates, 0.0, -1.0, false, closedPath);
	}
	else {
	    if (closedPath) {
		space = (pathLength - totalDim) / (numNodes);
	    }
	    else {
		space = (pathLength - totalDim) / (numNodes-1);
	    }
	    distribute(nodes, coordinates, space, tolerance, true, closedPath);
	}
    }

    /**
     * Distributes the given <code>nodes</code> (those being ZNodes)
     * along the (optionally closed) path specified by
     * <code>coordinates</code>.  The algorithm initially distributes
     * the nodes with the specified <code>space</code> along the path.
     * If <code>exact</code> spacing is not requested, the algorithm
     * does not iterate.  Hence, the nodes will be spaced with the
     * intial value of <code>space</code> regardless of the nodes' positions.
     * If <code>exact</code> spacing is requested, the algorithm
     * iterates until the nodes and spacing are within
     * <code>tolerance</code> of the given path, where <code>tolerance</code>
     * is specified as a percentage of the total path length.  The algorithm
     * will terminate with suboptimal results if the path is too short for
     * the nodes or if an optimal result cannot be computed.
     * @param nodes The nodes to be distributed.
     * @param coordinates The coordinates of the path.
     * @param space The initial amount of spacing to leave between nodes.
     * @param tolerance The percent of the total path length to which
     *                   the algorithm should compute, a value from 0.0-100.0.
     * @param exact Should the algorithm iterate or make a single pass?
     * @param closedPath Does the path represent a closed path?
     */
    static public void distribute(ZNode[] nodes, ArrayList coordinates,
				  double space, double tolerance,
				  boolean exact, boolean closedPath) {
	
	// The default tolerance value
	final double DEFAULT_TOLERANCE = 0.1f;

	// The number of points on the path
	int numCoords;

	// The number of nodes 
	int numNodes;

	// The length of the path given by coordinates
	double pathLength;

	// The amount of path passing through the first node - for closed paths
	double closingLength;
	
	// Node index
	int index;
	
	// The current copy of coordinates
	ArrayList coords;
	
	// Are we finished?	 
	boolean done = false;
	
	// represents the current error in distributing along the path
	double currentError = -1.0;
	
	// Storage for a frequently used variable
	Point2D halfDim = new Point2D.Double();

	if ((nodes.length == 0) || coordinates.isEmpty()) {
	    return;
	}

	if (!(tolerance > 0.0 && tolerance <= 100.0)) {
	    tolerance = DEFAULT_TOLERANCE;
	}
	tolerance = tolerance/100.0;
	
	pathLength = pathLength(coordinates);
	numCoords = coordinates.size();
	numNodes = nodes.length;
	closingLength = 0.0;
	
	
	/* This code handles the special case when the path is closed.  Since
	   the center of the first node will be placed at the first
	   coordinate, we must calculate how much of the path, on closing
	   will intersect the node in order to add this to our
	   pathLengthInNodes */	   
	if (closedPath) {
	    Point2D dir = new Point2D.Double(1.0,1.0);
	    Point2D entranceP = (Point2D)((Point2D)coordinates.get(coordinates.size()-1)).clone();
	    ZNode firstNode = (ZNode)nodes[0].editor().getTop();
	    Rectangle2D bounds = firstNode.getBounds();
	    double halfWidth = (bounds.getWidth())/(2.0);
	    double halfHeight = (bounds.getHeight())/(2.0);
	    
	    double curX = entranceP.getX();
	    double curY = entranceP.getY();
	    normalizeList(dir);

	    Point2D curP = new Point2D.Double(curX, curY);
	    halfDim.setLocation(halfWidth, halfHeight);

	    closingLength = computeDimensionTranslation(halfDim,curP,new Point2D.Double(0.0,0.0),new Point2D.Double(0.0,0.0),dir,coordinates,false);   
	}


	/* ************** The meat of the algorithm *****************

	   If exact spacing is not specified, this loop is executed once.
	   Otherwise, we loop until we are within "tolerance" of the given
	   path.  The algorithm also exits this loop if it detects an	   
	   infinite loop or the path is too small for the nodes
	 */
	while (!done) {
	    double pathLengthInNodes = 0.0;
	    // Start off with the direction that doesn't favor either dimension
	    Point2D dir = new Point2D.Double(1.0,1.0);

	    index = 0;
	    coords = (ArrayList)coordinates.clone();

	    Point2D entranceP = (Point2D)((Point2D)coords.get(0)).clone();
	    double remainderX = 0.0;
	    double remainderY = 0.0;
	    double curX = 0.0;
	    double curY = 0.0;	    

	    boolean determined = false;
	    double centerX = 0.0;
	    double centerY = 0.0;

	    coords.remove(0);
	    normalizeList(dir);	    	    
	    
	    if (closedPath) {
		determined = true;
		centerX = entranceP.getX();
		centerY = entranceP.getY();
	    }

	    // Perform this loop once for each of the nodes
	    for (index=0; index < nodes.length; index++) {
		ZNode node = nodes[index].editor().getTop();
		ZBounds bounds = node.getBounds();
		Point2D curP;
		Point2D remainderP;
		
		double halfWidth = bounds.getWidth()/2.0;
		double halfHeight = bounds.getHeight()/2.0;
		double entranceX = entranceP.getX();
		double entranceY = entranceP.getY();

		curX = entranceX;
		curY = entranceY;


		/******************************************/
		/* Part 1 - find the new center of the    */
		/*   node                               */
		/******************************************/

		if (determined == false) {

		    /* halfDim is a Point2D instead of a Dimension2D because
		       the Java interface doesn't define any Dimension2D
		       classes with doubles */
		    Point2D centerP = new Point2D.Double(centerX, centerY);

		    halfDim.setLocation(halfWidth, halfHeight);
		    curP = new Point2D.Double(curX, curY);	    
		    remainderP = new Point2D.Double(remainderX, remainderY);
		    
		    pathLengthInNodes += computeDimensionTranslation(halfDim,curP,remainderP,centerP,dir,coords,true);

		    curX = curP.getX();
		    curY = curP.getY();

		    remainderX = remainderP.getX();
		    remainderY = remainderP.getY();

		    centerX = centerP.getX();
		    centerY = centerP.getY();
		}
		
		determined = false;

		/******************************************/		
		/* End Part 1                             */
		/******************************************/		

		curX = centerX;
		curY = centerY;

		/* Actually Move the node to this center point */

		ZTransformGroup transform = node.editor().getTransformGroup();
		Point2D newLocalCenter = new Point2D.Double(centerX, centerY);
		Point2D curLocalCenter = bounds.getCenter2D();
		AffineTransform tmpTransform = transform.getInverseTransform();
		tmpTransform.setTransform(tmpTransform.getScaleX(),tmpTransform.getShearX(),tmpTransform.getShearY(),tmpTransform.getScaleY(),0.0,0.0);
		tmpTransform.transform(newLocalCenter,newLocalCenter);
		tmpTransform.transform(curLocalCenter,curLocalCenter);

		Point2D translation = new Point2D.Double((newLocalCenter.getX()-curLocalCenter.getX()),(newLocalCenter.getY()-curLocalCenter.getY()));

		transform.translate(translation.getX(),translation.getY());
		

		/******************************************/
		/* Part 2 - Now that we have the center - */
		/*  find out how much more of the path is */
		/*   in the node's bounding box           */
		/* Plus we will find the point where the  */
		/*   path leaves the node's bounding box  */
		/******************************************/		

		double boundaryX = 0.0;
		double boundaryY = 0.0;
		
		/* This is a Point2D instead of a Dimension because
		   the very consistent Java interface doesn't define
		   any Dimension classes with doubles */
		Point2D boundaryP = new Point2D.Double(boundaryX, boundaryY);

		halfDim.setLocation(halfWidth, halfHeight);
		curP = new Point2D.Double(curX, curY);	    
		remainderP = new Point2D.Double(remainderX, remainderY);

		pathLengthInNodes += computeDimensionTranslation(halfDim,curP,remainderP,boundaryP,dir,coords,true);

		curX = curP.getX();
		curY = curP.getY();
		
		remainderX = remainderP.getX();
		remainderY = remainderP.getY();
		
		boundaryX = boundaryP.getX();
		boundaryY = boundaryP.getY();

		/******************************************/
		/* End Part 2                             */
		/******************************************/

		curX = boundaryX;
		curY = boundaryY;
		
		/******************************************/
		/* Part 3 - Now we add the appropriate    */
		/* amount of space based on our latest    */
		/* calculations for the amount of path    */
		/* in nodes the idea here is to         */
		/* eventually get all the nodes on the  */
		/* path with equal spacing between them   */
		/******************************************/

		boolean spaced = false;
		double spaceSoFar = 0.0;
		curP = new Point2D.Double(curX,curY);
		remainderP = new Point2D.Double(remainderX+curX,remainderY+curY);
		
		if ((remainderX != 0.0) || (remainderY != 0.0)) {

		    if (remainderP.distance(curP) > space) {
			entranceP.setLocation(curX + space*dir.getX(),curY + space*dir.getY());
			
			spaced = true;
			remainderX = remainderX - (entranceP.getX() - curX);
			remainderY = remainderY - (entranceP.getY() - curY);
		    }
		    else {
			spaceSoFar = remainderP.distance(curP);
			curP.setLocation(remainderP);
			remainderX = 0.0;
			remainderY = 0.0;
		    }
		}

		while (!spaced) {

		    if (!coords.isEmpty()) {
			Point2D newP = (Point2D)((Point2D)coords.get(0)).clone();
			double newX = newP.getX();
			double newY = newP.getY();

			coords.remove(0);			    
			dir.setLocation(newX-curP.getX(),newY-curP.getY());
			normalizeList(dir);

			if (newP.distance(curP)+spaceSoFar > space) {
			    entranceP.setLocation(curP.getX() + (space-spaceSoFar)*dir.getX(),curP.getY() + (space-spaceSoFar)*dir.getY());
			    
			    spaced = true;
			    remainderX = newP.getX() - entranceP.getX();
			    remainderY = newP.getY() - entranceP.getY();
			}
			else {
			    spaceSoFar = newP.distance(curP) + spaceSoFar;
			    curP.setLocation(newX, newY);			    
			}
		    }
		    else {
			entranceP.setLocation(curP.getX() + (space-spaceSoFar)*dir.getX(),curP.getY() + (space-spaceSoFar)*dir.getY());			
			spaced = true;						
		    }
		}

		/******************************************/
		/* End Part 3                             */
		/******************************************/
		
	    }

	    /******************************************/
	    /* Part 4 - Check to see if we're done,   */
	    /*   then update spacing                  */
	    /******************************************/

	    if (exact) {

		double length;

		/* Add in the closing path length for the case of a
		   closed path (it's 0 anyway if path isn't closed) */
		pathLengthInNodes = pathLengthInNodes + closingLength;
		
		if (closedPath) {
		    length = pathLengthInNodes + (numNodes)*space;
		}
		else {
		    if (numNodes != 1) {
			length = pathLengthInNodes + (numNodes-1)*space;
		    }
		    else {
			length = pathLength;
		    }
		}

		if (Math.abs((length-pathLength)/(pathLength)) < tolerance) {
		    done = true;
		}
		else {
		    
		    if (closedPath) {
			space = (pathLength - pathLengthInNodes)/(numNodes);
		    }
		    else {
			if (numNodes != 1) {
			    space = (pathLength - pathLengthInNodes)/(numNodes-1);
			}
			else {
			    space = pathLength - pathLengthInNodes;
			}
		    }
		    
		    if (space < 0) {
			done = true;
		    }

		    if (pathLength == 0) {
			done = true;
		    }
		}
		
		/* This case was added in the event that the error is not
		   decreasing - signaling a likely infinite loop */
		if (currentError != -1.0) {
		    if (currentError <= Math.abs(length-pathLength)) {
			done = true;
		    }
		}
		currentError = Math.abs(length-pathLength);
	    }
	    else {
		done = true;
	    }
	    
	    /******************************************/
	    /* End Part 4                             */
	    /******************************************/
	}	
    }
    

    /**
     * A helper function that computes the length of path in the given
     * dimension. Computes the destination location for the given
     * dimension based on the current point, current remainder, and
     * current direction.  The caller can specify whether the
     * coordinates should be viewed in ascending or descending
     * order.  If descending order is used, the coordinates are not
     * removed from the ArrayList.
     * @param dim The dimension of the bounds in which to perfrom the computations
     * @param currentP The current point on the path
     * @param remainderP The remainder of path from previous point
     * @param finishedP The storage location for the resultant destination point
     * @param dir The current direction of the path
     * @param coordinates The coordinates of the path
     * @param ascending Look at the points of the path in ascending or descending order?
     * @return A double equal to the length of the path in dim
     */
    static protected double computeDimensionTranslation(Point2D dim, Point2D currentP,
						       Point2D remainderP, Point2D finishedP,
						       Point2D dir, ArrayList coordinates,
						       boolean ascending) {		
	boolean finished = false;

	double width = dim.getX();
	double height = dim.getY();	
	
	double currentX = currentP.getX();
	double currentY = currentP.getY();

	double remainderX = remainderP.getX();
	double remainderY = remainderP.getY();
	
	double finishedX = finishedP.getX();
	double finishedY = finishedP.getY();	
	
	double pathSoFarX = 0.0;
	double pathSoFarY = 0.0;

	double pathLengthInNodes = 0.0;
	
	int ArrayListPosition;

	if (ascending) {
	    ArrayListPosition = coordinates.size();
	}
	else {
	    ArrayListPosition = coordinates.size()-2;
	}

	if (remainderX != 0.0 || remainderY != 0.0) {
	    
	    if ((Math.abs(remainderX) > width) || (Math.abs(remainderY) > height)) {
		/* This case indicates that the remainder extends past
		   the dimensions in one direction */

		/* Here we add NaN checks because 0.0/0.0 returns NaN
		   instead of infinity.  Thus, if not checked, we could
		   get incorrect results - don't know if 0 will be passed
		   as a dimension or not - better safe */
		if (Double.isNaN(width/Math.abs(dir.getX())) || Double.isNaN(height/Math.abs(dir.getY()))) {
		    if (Double.isNaN(width/Math.abs(dir.getX()))) {
			/* This case means the length of the path
			   along the current direction is
			   smaller in the y direction
			*/
			
			finishedX = currentX + (height)*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			finishedY = currentY + (height)*(dir.getY()/Math.abs(dir.getY()));
			
		    }
		    else {
			/* This case means the length of the path
			   along the current direction is smaller in the
			   x direction
			*/
			
			finishedX = currentX + (width)*(dir.getX()/Math.abs(dir.getX()));
			finishedY = currentY + (width)*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
		    }
		}
		else {
		    
		    if ((width/Math.abs(dir.getX())) < (height/Math.abs(dir.getY()))) {
			/* This case means the length of the path
			   along the current direction is smaller in the
			   x direction
			*/
			
			finishedX = currentX + (width)*(dir.getX()/Math.abs(dir.getX()));
			finishedY = currentY + (width)*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
		    }
		    else {
			/* This case means the length of the path
			   along the current direction is
			   smaller in the y direction
			*/
			
			finishedX = currentX + (height)*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			finishedY = currentY + (height)*(dir.getY()/Math.abs(dir.getY()));
		    }
		}		    
		finished = true;
		remainderX = remainderX - (finishedX - currentX);
		remainderY = remainderY - (finishedY - currentY);
		pathLengthInNodes = pathLengthInNodes + Point2D.distance(0.0,0.0,finishedX-currentX,finishedY-currentY);
	    }
	    else {
		/* In this case, the segment isn't long enough
		   to extend past the dimensions in either direction
		*/
		pathSoFarX = remainderX;
		pathSoFarY = remainderY;
		currentX = currentX + remainderX;
		currentY = currentY + remainderY;
		pathLengthInNodes = pathLengthInNodes + Point2D.distance(0.0,0.0,remainderX,remainderY);
		remainderX = 0.0;
		remainderY = 0.0;
	    }
	    
	}
	
	while(!finished) {
	    
	    if (!coordinates.isEmpty() && ArrayListPosition >=0) {
		Point2D newP = null;

		if (ascending) {
		    newP = (Point2D)((Point2D)coordinates.get(0)).clone();
		    coordinates.remove(0);			    
		}
		else {
		    newP = (Point2D)((Point2D)coordinates.get(ArrayListPosition)).clone();
		    ArrayListPosition--;
		}
		
		double newX = newP.getX();
		double newY = newP.getY();
		dir.setLocation(newX-currentX,newY-currentY);
		normalizeList(dir);
		
		double curDistX = newX - currentX;
		double curDistY = newY - currentY;
		if ((Math.abs(curDistX+pathSoFarX) > width) || (Math.abs(curDistY+pathSoFarY) > height)) {
		    /* This case means that the
		       current segment at least
		       extends past the dimensions
		       in one direction
		    */

		    
		    /* Generally double numbers in java are
		       fairly cooperative in divide by zero
		       instances, yielding "Double.POSITIVE_INFINITY"
		       or "Double.NEGATIVE_INFINITY". These
		       "INFINITY" values even work for comparisons.

		       However, if you perform operations on
		       "INFINITY" you get "Double.NaN".  This value does
		       not cooperate with comparison statements so we
		       have to check beforehand to see if
		       we got any NaN.  We shouldn't get division by
		       both components of "dir" yielding NaN
		       because the ArrayList is normalized

		       This applies to all the remaining operations as
		       well.
		    */

		    
		    if (Double.isNaN(width/Math.abs(dir.getX()) - pathSoFarX/dir.getX()) || Double.isNaN(height/Math.abs(dir.getY()) - pathSoFarY/dir.getY())) {
			if (Double.isNaN(width/Math.abs(dir.getX()) - pathSoFarX/dir.getX())) {
			    if (Math.abs(pathSoFarY/dir.getY()) == pathSoFarY/dir.getY()) {
				finishedX = currentX + (height - Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
				finishedY = currentY + (height - Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			    }
			    else {
				finishedX = currentX + (height + Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
				finishedY = currentY + (height + Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			    }			    
			}
			else {
			    if (Math.abs(pathSoFarX/dir.getX()) == pathSoFarX/dir.getX()) {
				finishedX = currentX + (width - Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
				finishedY = currentY + (width - Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			    }
			    else {
				finishedX = currentX + (width + Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
				finishedY = currentY + (width + Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			    }
			}
		    }
		    else {

			if ((width/Math.abs(dir.getX()) - pathSoFarX/dir.getX()) < (height/Math.abs(dir.getY()) - pathSoFarY/dir.getY())) {
			    
				/* Now we check whether the current
				   direction is the same as the
				   direction of the previous movements
				*/
			    if (Math.abs(pathSoFarX/dir.getX()) == pathSoFarX/dir.getX()) {
				finishedX = currentX + (width - Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
				finishedY = currentY + (width - Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			    }
			    else {
				finishedX = currentX + (width + Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
				finishedY = currentY + (width + Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			    }
			}
			else {
				
				/* Now we check whether the
				   current direction is the same
				   as the direction of the previous
				   movements
				*/	    
			    if (Math.abs(pathSoFarY/dir.getY()) == pathSoFarY/dir.getY()) {
				finishedX = currentX + (height - Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
				finishedY = currentY + (height - Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			    }
			    else {
				finishedX = currentX + (height + Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
				finishedY = currentY + (height + Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			    }
			}
						
		    }
		    
		    
		    finished = true;
		    remainderX = newX - finishedX;
		    remainderY = newY - finishedY;
		    pathLengthInNodes = pathLengthInNodes + Point2D.distance(0.0,0.0,finishedX-currentX,finishedY-currentY);
		}		
		else {
		    /* This case means that the
		       current segment doesn't
		       extends past the dimensions
		       in either direction
		    */

		    pathSoFarX = pathSoFarX + curDistX;
		    pathSoFarY = pathSoFarY + curDistY;
		    currentX = currentX + curDistX;
		    currentY = currentY + curDistY;
		    pathLengthInNodes = pathLengthInNodes + Point2D.distance(0.0,0.0,curDistX,curDistY);				
		}
	    }
	    else {
		if (Double.isNaN(width/Math.abs(dir.getX()) - pathSoFarX/dir.getX()) || Double.isNaN(height/Math.abs(dir.getY()) - pathSoFarY/dir.getY())) {
		    if (Double.isNaN(width/Math.abs(dir.getX()) - pathSoFarX/dir.getX())) {
			if (Math.abs(pathSoFarY/dir.getY()) == pathSoFarY/dir.getY()) {
			    finishedX = currentX + (height - Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			    finishedY = currentY + (height - Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			}
			else {
			    finishedX = currentX + (height + Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			    finishedY = currentY + (height + Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			}			
		    }
		    else {
			if (Math.abs(pathSoFarX/dir.getX()) == pathSoFarX/dir.getX()) {
			    finishedX = currentX + (width - Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
			    finishedY = currentY + (width - Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			}
			else {
			    finishedX = currentX + (width + Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
			    finishedY = currentY + (width + Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			}
		    }
		}
		else {
		    if ((width/Math.abs(dir.getX()) - pathSoFarX/dir.getX()) < (height/Math.abs(dir.getY()) - pathSoFarY/dir.getY())) {
			/* Now we check whether the current direction
			   is the same as the direction of the
			   previous movements
			*/
			if (Math.abs(pathSoFarX/dir.getX()) == pathSoFarX/dir.getX()) {
			    finishedX = currentX + (width - Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
			    finishedY = currentY + (width - Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			}
			else {
			    finishedX = currentX + (width + Math.abs(pathSoFarX))*(dir.getX()/Math.abs(dir.getX()));
			    finishedY = currentY + (width + Math.abs(pathSoFarX))*(dir.getY()/dir.getX())*(dir.getX()/Math.abs(dir.getX()));
			}
		    }
		    else {
			
			/* Now we check whether the current direction
			   is the same as the direction of the previous
			   movements
			*/
			
			if (Math.abs(pathSoFarY/dir.getY()) == pathSoFarY/dir.getY()) {
			    finishedX = currentX + (height - Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			    finishedY = currentY + (height - Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			}
			else {
			    finishedX = currentX + (height + Math.abs(pathSoFarY))*(dir.getX()/dir.getY())*(dir.getY()/Math.abs(dir.getY()));
			    finishedY = currentY + (height + Math.abs(pathSoFarY))*(dir.getY()/Math.abs(dir.getY()));
			}
		    }
		}
		finished = true;
		pathLengthInNodes = pathLengthInNodes + Point2D.distance(0.0,0.0,finishedX-currentX,finishedY-currentY);			
	    }
	    
	}

	currentP.setLocation(currentX, currentY);
	remainderP.setLocation(remainderX, remainderY);
	finishedP.setLocation(finishedX, finishedY);
	
	return pathLengthInNodes;
    }


    /**
     * Computes the length of the path for a given ArrayList of coordinates
     * @param coords The path for which to compute the length
     * @return The length of the given path 
     */
    static protected double pathLength(ArrayList coords) {
	double len = 0.0;
	if (coords.size() > 1) {
	    for(int i=0; i<(coords.size()-1); i++) {
		len = len + ((Point2D)coords.get(i)).distance((Point2D)coords.get(i+1));
	    }
	}
	return len;
    }

    /**
     * Normalizes the given ArrayList ie. maintains the ArrayLists direction
     * but gives it unit length
     * @param p ArrayList (ArrayList in the physics sense, actually a point) to be normalized
     */
    static protected void normalizeList(Point2D p) {
	double len = p.distance(0.0, 0.0);
	if (len != 0.0) {
	    p.setLocation(p.getX()/len,p.getY()/len);
	}
	else {
	    p.setLocation(1.0/Point2D.distance(0.0,0.0,1.0,1.0),1.0/Point2D.distance(0.0,0.0,1.0,1.0));
	}
    }
}

