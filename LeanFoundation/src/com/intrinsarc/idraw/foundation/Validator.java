package com.intrinsarc.idraw.foundation;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public class Validator
{
	public static void validateFigure(FigureFacet figure)
	{
		// there are currently 6 different permutations: 3 types x (anchor or not)
		switch (figure.getType())
		{
			case FigureFacet.NODE_TYPE:
				if (figure.getContainedFacet() == null)
					throw new IllegalFigureException("Node figure does not have a ContainedFacet: " + figure);
				if (figure.getContainerFacet() != null)
					throw new IllegalFigureException("Node figure has a ContainerFacet: " + figure);
				if (figure.getLinkingFacet() != null)
					throw new IllegalFigureException("Node figure has a LinkingFacet: " + figure);								
				break;
				
			case FigureFacet.CONTAINER_TYPE:
				if (figure.getContainedFacet() == null)
					throw new IllegalFigureException("Container figure does not have a ContainedFacet: " + figure);
				if (figure.getContainerFacet() == null)
					throw new IllegalFigureException("Container figure does not have a ContainerFacet: " + figure);
				if (figure.getLinkingFacet() != null)
					throw new IllegalFigureException("Container figure has a LinkingFacet: " + figure);								
				break;
				
			case FigureFacet.ARC_TYPE:
				if (figure.getContainedFacet() != null)
					throw new IllegalFigureException("Arc figure has a ContainedFacet: " + figure);
				if (figure.getLinkingFacet() == null)
					throw new IllegalFigureException("Arc figure does not have a LinkingFacet: " + figure);
				break;
		}
	}

	public static void validatePreview(PreviewFacet preview)
	{
		// there are currently 6 different permutations: 3 types x (anchor or not)
		FigureFacet underlying = preview.isPreviewFor();
		switch (underlying.getType())
		{
			case FigureFacet.NODE_TYPE:
				if (preview.getContainedPreviewFacet() == null)
					throw new IllegalFigureException("Node preview does not have a ContainedPreviewFacet: " + preview);
				if (preview.getContainerPreviewFacet() != null)
					throw new IllegalFigureException("Node preview has a ContainerPreviewFacet: " + preview);
				if (preview.getLinkingPreviewFacet() != null)
					throw new IllegalFigureException("Node preview has a LinkingPreviewFacet: " + preview);								
				// must have an anchor if the underlying has one
				if (underlying.getAnchorFacet() != null && preview.getAnchorPreviewFacet() == null)
					throw new IllegalFigureException("Node preview does not have an anchor facet, but underlying does");
				break;
				
			case FigureFacet.CONTAINER_TYPE:
				if (preview.getContainedPreviewFacet() == null)
					throw new IllegalFigureException("Container preview does not have a ContainedPreviewFacet: " + preview);
				if (preview.getContainerPreviewFacet() == null)
					throw new IllegalFigureException("Container preview does not have a ContainerPreviewFacet: " + preview);
				if (preview.getLinkingPreviewFacet() != null)
					throw new IllegalFigureException("Container preview has a LinkingPreviewFacet: " + preview);								
				// must have an anchor if the underlying has one
				if (underlying.getAnchorFacet() != null && preview.getAnchorPreviewFacet() == null)
					throw new IllegalFigureException("Container preview does not have an anchor facet, but underlying does");
				break;
				
			case FigureFacet.ARC_TYPE:
				if (preview.getContainedPreviewFacet() != null)
					throw new IllegalFigureException("Arc preview has a ContainedPreviewFacet: " + preview);
				if (preview.getLinkingPreviewFacet() == null)
					throw new IllegalFigureException("Arc preview does not have a LinkingPreviewFacet: " + preview);
				// must have an anchor if the underlying has one
				if (underlying.getAnchorFacet() != null && preview.getAnchorPreviewFacet() == null)
					throw new IllegalFigureException("Arc preview does not have an anchor facet, but underlying does");
				break;
		}
	}
}
