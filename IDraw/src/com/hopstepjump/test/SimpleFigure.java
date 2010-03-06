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

	@Override
	public void addPreviewToCache(DiagramFacet diagram,
			PreviewCacheFacet figuresToPreview, UPoint start, boolean addMyself)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adjusted()
	{
		diagram.adjusted(this);
	}

	@Override
	public void cleanUp()
	{
		diagram.aboutToAdjust(this);
		double w = DiagramTest.rand(50, 200);
		pt = new UPoint(DiagramTest.rand(20, 300), DiagramTest.rand(20, 300));
		extent = new UDimension(w, w);
		rect = DiagramTest.rand(0, 100) > 50;
		color = DiagramTest.randomColor();
	}

	@Override
	public Command formDeleteCommand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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

	@Override
	public Command formViewUpdateCommandAfterSubjectChanged(boolean isTop,
			ViewUpdatePassEnum pass)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FigureFacet getActualFigureForSelection()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnchorFacet getAnchorFacet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClipboardCommandsFacet getClipboardCommandsFacet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClipboardFacet getClipboardFacet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
			
			public Command getPostContainerDropCommand()
			{
				return null;
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

	@Override
	public ContainerFacet getContainerFacet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiagramFacet getDiagram()
	{
		return diagram;
	}

	@Override
	public String getFigureName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FigureReference getFigureReference()
	{
		return new FigureReference(diagram, reference);
	}

	@Override
	public UBounds getFullBounds()
	{
		return new UBounds(pt, extent);
	}

	@Override
	public UBounds getFullBoundsForContainment()
	{
		return new UBounds(pt, extent);
	}

	@Override
	public String getId()
	{
		return reference;
	}

	@Override
	public LinkingFacet getLinkingFacet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UBounds getRecalculatedFullBoundsForDiagramResize(boolean diagramResize)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manipulators getSelectionManipulators(DiagramViewFacet diagramView,
			boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreviewFacet getSinglePreview(DiagramFacet diagram)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSubject()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToolFigureClassification getToolClassification(UPoint point,
			DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasSubjectBeenDeleted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShowing()
	{
		return true;
	}

	@Override
	public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JPopupMenu makeContextMenu(DiagramViewFacet diagramView,
			ToolCoordinatorFacet coordinator)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistentFigure makePersistentFigure()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void produceEffect(ToolCoordinatorFacet coordinator, String effect,
			Object[] parameters)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShowing(boolean showing)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean useGlobalLayer()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Facet getDynamicFacet(Class facetClass)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDynamicFacet(Class facetClass)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDynamicFacet(Facet facet, Class facetInterface)
	{
	}
}
