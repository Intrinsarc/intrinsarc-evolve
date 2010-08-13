package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;

import java.awt.*;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

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
	
	public void middleButtonPressed(DiagramFacet diagram);
	public boolean isEmptyAndCanBeDeleted(Namespace subject);
	public ToolFigureClassification getToolClassification(BasicNamespaceSizes sizes, UPoint point);
}
