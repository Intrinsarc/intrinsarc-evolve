package com.intrinsarc.evolve.guibase;

import java.util.*;

import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.resize.*;

public class FixedPerspectiveDiagramPostProcessor implements DiagramPostProcessor
{
	private List<DiagramFacet> listeningTo = new ArrayList<DiagramFacet>();
	private DiagramListenerFacet listener = new DiagramListenerFacet()
	{
		public void haveModifications(DiagramChange[] changes)
		{
		}

		public void refreshViewAttributes()
		{
		}		
	};
	private String listenerId = listener.toString();
	
	public void postProcess(DiagramFacet diagram)
	{
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		DEStratum perspective = engine.locateObject(diagram.getPossiblePerspective()).asStratum();
		
		removeAllListeners();
		for (FigureFacet figure : diagram.getFigures())
		{
			Object subject = figure.getSubject();
			if (subject instanceof ClassImpl || subject instanceof InterfaceImpl)
			{
				if (containsSubfigures(figure))
				{
					// look for the highest redefinition
					DEElement element = engine.locateObject(subject).asElement();
					List<DEElement> topmost = element.getTopmost(perspective);
					if (!topmost.isEmpty())
					{
						DEElement top = topmost.get(0);
						if (top != element)
							fixFigureSizes(diagram, figure, element, top);
					}
				}
			}
		}
	}
	
	public void dispose()
	{
		removeAllListeners();
	}
	
	private void removeAllListeners()
	{
		for (DiagramFacet diagram : listeningTo)
			diagram.removeListener(listenerId);
	}
	
	private DiagramFacet extractAndListenToDiagram(Package pkg)
	{
		// see if we can find the diagram in our current list
		for (DiagramFacet diagram : listeningTo)
			if (diagram.getLinkedObject() == pkg)
				return diagram;
		
		// otherwise, we need to retrieve it
		DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()));
		diagram.addListener(listenerId, listener);
		listeningTo.add(diagram);
		return diagram;
	}

	private void fixFigureSizes(DiagramFacet diagram, FigureFacet figure, DEElement element, DEElement top)
	{
		DiagramFacet extension = extractAndListenToDiagram((Package) top.getHomeStratum().getRepositoryObject());
		
		// find the likely match in the extension diagram
		FigureFacet match = null;
		for (FigureFacet fig : extension.getFigures())
		{
			if (fig.getSubject() == top.getRepositoryObject() && containsSubfigures(fig))
			{
				match = fig;
				break;
			}
		}
		if (match == null)
			return;
		
		// resize the containing figure
		UBounds bounds = figure.getFullBounds();
		UBounds matchBounds = match.getFullBounds();
		UBounds newBounds =
			ResizingManipulatorGem.formCentrePreservingBounds(
					bounds,
					matchBounds.getDimension());
		resize(figure, newBounds);
		
		// go through all containers of this figure first
		resizeContained(figure, match, matchBounds, newBounds);
	}

	private void resizeContained(FigureFacet figure, FigureFacet match, UBounds matchBounds, UBounds newBounds)
	{
		for (Iterator<FigureFacet> iter = figure.getContainerFacet().getContents(); iter.hasNext();)
		{
			FigureFacet fig = iter.next();
			if (fig.getContainerFacet() != null)
			{
				for (Iterator<FigureFacet> inside = fig.getContainerFacet().getContents(); inside.hasNext();)
				{
					FigureFacet constituentFigure = inside.next();
					FigureFacet relatedMatch = locateFigureWithSubject(match, constituentFigure.getSubject());
					// see if we can find any related links
					for (Iterator<LinkingFacet> linkIter = constituentFigure.getAnchorFacet().getLinks(); linkIter.hasNext();)
					{
						FigureFacet link = linkIter.next().getFigureFacet();
						// make sure we only do each link once
						if (link.getLinkingFacet().getAnchor1() == constituentFigure.getAnchorFacet())
						{
							FigureFacet related = locateLinkWithSubject(constituentFigure, link.getSubject());
							if (related != null)
								restructureLink(link, related, matchBounds.getPoint().subtract(newBounds.getPoint()));
						}
					}
					
					if (relatedMatch == null)
						continue;
					
					UBounds cBounds = relatedMatch.getFullBounds();
					if (cBounds != null)
					{
						UDimension offset = cBounds.getPoint().subtract(matchBounds.getPoint());
						resize(constituentFigure, new UBounds(newBounds.getPoint().add(offset), cBounds.getDimension()));
					}
					
					// handle any sub-figures of this one -- these translate to port instances
					if (constituentFigure.getContainerFacet() != null)
						resizeContained(constituentFigure, relatedMatch, matchBounds, newBounds);
				}
			}
		}
	}
	
	private void restructureLink(FigureFacet link, FigureFacet related, UDimension offset)
	{
//		DiagramFacet diagram = link.getDiagram();
//		
//		Collection<PersistentFigure> persistent = new ArrayList<PersistentFigure>();
//		PersistentFigure pers = addPersistent(persistent, related);
//		pers.setId(link.getId());
//		pers.setAnchor1Id(link.getLinkingFacet().getAnchor1().getFigureFacet().getId());
//		pers.setAnchor2Id(link.getLinkingFacet().getAnchor2().getFigureFacet().getId());
//		persistent.add(pers);		
//
//		diagram.remove(link);
//		diagram.addPersistentFigures(persistent, offset);
	}
	
	private PersistentFigure addPersistent(Collection<PersistentFigure> persistent, FigureFacet figure)
	{
		PersistentFigure pers = figure.makePersistentFigure();
		persistent.add(pers);
		if (figure.getContainerFacet() != null)
		{
			for (Iterator<FigureFacet> child = figure.getContainerFacet().getContents(); child.hasNext();)
				addPersistent(persistent, child.next());
		}
		return pers;
	}

	private FigureFacet locateLinkWithSubject(FigureFacet figure, Object subject)
	{
		for (Iterator<LinkingFacet> linkIter = figure.getAnchorFacet().getLinks(); linkIter.hasNext();)
		{
			FigureFacet link = linkIter.next().getFigureFacet();
			if (getActual(link.getSubject()) == getActual(subject))
				return link;
		}
		return null;
	}

	private Object getActual(Object subject)
	{
		if (subject instanceof DeltaReplacedConstituentImpl)
			return ((DeltaReplacedConstituentImpl) subject).getReplacement();
		return subject;
	}

	private FigureFacet locateFigureWithSubject(FigureFacet figure, Object subject)
	{
		if (getActual(figure.getSubject()) == getActual(subject))
			return figure;
		if (figure.getContainerFacet() != null)
			for (Iterator<FigureFacet> iter = figure.getContainerFacet().getContents(); iter.hasNext();)
			{
				FigureFacet sub = locateFigureWithSubject(iter.next(), subject);
				if (sub != null)
					return sub;
			}
		return null;
	}

	private void resize(FigureFacet figure, UBounds newBounds)
	{
		// this method should only be used inside a command's execution
		ResizingFiguresGem gem = new ResizingFiguresGem(null, figure.getDiagram());
		ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
		facet.markForResizing(figure);
		facet.setFocusBounds(newBounds);
		facet.end();
	}

	private boolean containsSubfigures(FigureFacet figure)
	{
		// do we have any subfigures in the top level?
		// NOTE: won't find connectors, but this isn't really a problem as we can't have connectors
		// without parts
		for (Iterator<FigureFacet> iter = figure.getContainerFacet().getContents(); iter.hasNext();)
		{
			FigureFacet fig = iter.next();
			if (fig.getContainerFacet() != null)
			{
				if (fig.getContainerFacet().getContents().hasNext())
					return true;
			}
		}
		return false;
	}
}
