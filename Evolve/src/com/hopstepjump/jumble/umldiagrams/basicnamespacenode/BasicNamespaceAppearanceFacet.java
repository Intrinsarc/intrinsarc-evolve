package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import java.awt.*;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 29-Aug-02
 *
 */
public interface BasicNamespaceAppearanceFacet extends Facet
{
	public ZGroup formView(BasicNamespaceSizes sizes, boolean displayOnlyIcon, Color fillColor, Color lineColor, BasicNamespaceMiniAppearanceFacet miniAppearanceFacet);
	public BasicNamespaceSizes makeActualSizes(BasicNamespaceSizeInfo sizes, boolean moveTopLeftIn);
	public UBounds getInsideBoxForBoundaryCalculation(UBounds bounds, double insideTabHeight);

	public Shape formShapeForPreview(UBounds bounds, BasicNamespaceSizes sizes);
	public Shape formShapeForBoundaryCalculation(UBounds bounds, BasicNamespaceSizes sizes);
	public boolean useBoxForOutsideBoundaryCalculation();
	
	public Command middleButtonPressed(DiagramFacet diagram);
	public boolean isEmptyAndCanBeDeleted(Namespace subject);
	public ToolFigureClassification getToolClassification(BasicNamespaceSizes sizes, UPoint point);
}
