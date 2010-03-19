package com.hopstepjump.test;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class SimpleFigure implements FigureFacet
{
	private DiagramFacet diagram;
  private UPoint pt;
  private UDimension extent;
  private boolean rect;
  private Color color;
  private String reference = UUID.randomUUID().toString();
  
	public SimpleFigure(DiagramFacet diagram, UPoint pt, UDimension extent, boolean rect, Color color)
	{
		this.diagram = diagram;
		this.pt = pt;
		this.extent = extent;
		this.rect = rect;
		this.color = color;
	}

	public SimpleFigure(DiagramFacet diagram, PersistentFigure p)
	{
		this.diagram = diagram;
		this.reference = p.getId();
		readPersistentProperties(p);
	}

	private void readPersistentProperties(PersistentFigure p)
	{
		PersistentProperties properties = p.getProperties();
		pt = properties.retrieve("pt").asUPoint();
		extent = properties.retrieve("dim", new UDimension(0, 0)).asUDimension();
		color = properties.retrieve("color", Color.WHITE).asColor();
		rect = properties.retrieve("rect", false).asBoolean();
	}
	
	public PersistentFigure makePersistentFigure()
	{
		PersistentFigure p = new PersistentFigure(reference, SimpleFigureRecreator.NAME);
		PersistentProperties properties = p.getProperties();
		properties.add(new PersistentProperty("pt", pt));
		properties.add(new PersistentProperty("dim", extent, new UDimension(0,0)));
		properties.add(new PersistentProperty("color", color, Color.WHITE));
		properties.add(new PersistentProperty("rect", rect, false));		
		return p;
	}

	public void adjusted()
	{
		diagram.adjusted(this);
	}

	public void randomChange()
	{
		diagram.aboutToAdjust(this);
		double w = DiagramTest.rand(50, 200);
		pt = new UPoint(DiagramTest.rand(20, 300), DiagramTest.rand(20, 300));
		extent = new UDimension(w, w);
		rect = DiagramTest.rand(0, 100) > 50;
		color = DiagramTest.randomColor();
	}

	public ZNode formView()
	{
		if (!rect)
		{
			ZEllipse ell = new ZEllipse(pt.getX(), pt.getY(), extent.getWidth(), extent.getHeight());
			ell.setFillPaint(color);
			return new ZVisualLeaf(ell);
		}
		else
		{
			ZRectangle rect = new ZRectangle(pt.getX(), pt.getY(), extent.getWidth(), extent.getHeight());
			rect.setFillPaint(color);
			return new ZVisualLeaf(rect);
		}			
	}

	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	
	public Command formDeleteCommand()
	{
		return null;
	}

	public void addPreviewToCache(DiagramFacet diagram,
			PreviewCacheFacet figuresToPreview, UPoint start, boolean addMyself)
	{
	}

	public Command updateViewAfterSubjectChanged(boolean isTop,
			ViewUpdatePassEnum pass)
	{
		return null;
	}

	public FigureFacet getActualFigureForSelection()
	{
		return null;
	}

	public AnchorFacet getAnchorFacet()
	{
		return null;
	}

	public ClipboardCommandsFacet getClipboardCommandsFacet()
	{
		return null;
	}

	public ClipboardFacet getClipboardFacet()
	{
		return null;
	}

	public ContainedFacet getContainedFacet()
	{
		return new ContainedFacet()
		{
			public void setContainer(ContainerFacet container)
			{
			}
			
			public void persistence_setContainer(ContainerFacet container)
			{
			}
			
			public String persistence_getContainedName()
			{
				return null;
			}
			
			public void performPostContainerDropTransaction()
			{
			}
			
			public FigureFacet getFigureFacet()
			{
				return null;
			}
			
			public ContainerFacet getContainer()
			{
				return null;
			}
			
			public boolean canMoveContainers()
			{
				return false;
			}
			
			public boolean acceptsContainer(ContainerFacet container)
			{
				return false;
			}
		};
	}

	public ContainerFacet getContainerFacet()
	{
		return null;
	}

	public DiagramFacet getDiagram()
	{
		return diagram;
	}

	public String getFigureName()
	{
		return "simple figure";
	}

	public FigureReference getFigureReference()
	{
		return new FigureReference(diagram, reference);
	}

	public UBounds getFullBounds()
	{
		return new UBounds(pt, extent);
	}

	public UBounds getFullBoundsForContainment()
	{
		return new UBounds(pt, extent);
	}

	public String getId()
	{
		return reference;
	}

	public LinkingFacet getLinkingFacet()
	{
		return null;
	}

	public UBounds getRecalculatedFullBoundsForDiagramResize(boolean diagramResize)
	{
		return null;
	}

	public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator,
			DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	{
		return null;
	}

	public PreviewFacet getSinglePreview(DiagramFacet diagram)
	{
		return null;
	}

	public Object getSubject()
	{
		return null;
	}

	public ToolFigureClassification getToolClassification(UPoint point,
			DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
	{
		return null;
	}

	public int getType()
	{
		return 0;
	}

	public boolean hasSubjectBeenDeleted()
	{
		return false;
	}

	public boolean isShowing()
	{
		return true;
	}

	public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
	{
		return false;
	}

	public JPopupMenu makeContextMenu(DiagramViewFacet diagramView,
			ToolCoordinatorFacet coordinator)
	{
		return null;
	}

	public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
	{
		return null;
	}

	public void produceEffect(ToolCoordinatorFacet coordinator, String effect,
			Object[] parameters)
	{
	}

	public void setShowing(boolean showing)
	{
	}

	public boolean useGlobalLayer()
	{
		return false;
	}

	public Facet getDynamicFacet(Class facetClass)
	{
		return null;
	}

	public boolean hasDynamicFacet(Class facetClass)
	{
		return false;
	}

	public void registerDynamicFacet(Facet facet, Class facetInterface)
	{
	}

	public void cleanUp()
	{
	}

	public void aboutToAdjust()
	{
		diagram.aboutToAdjust(this);
	}

	public void acceptPersistentFigure(PersistentFigure pfig)
	{
		readPersistentProperties(pfig);
	}
}
