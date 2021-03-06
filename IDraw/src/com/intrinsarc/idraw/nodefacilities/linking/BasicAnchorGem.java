package com.intrinsarc.idraw.nodefacilities.linking;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;

import edu.umd.cs.jazz.*;


public final class BasicAnchorGem
{
  private BasicNodeAppearanceFacet apprearanceFacet = new BasicNodeAppearanceFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {
		public boolean acceptsContainer(ContainerFacet container)
		{
			return false;
		}

		public ZNode formView()
		{
			return new ZGroup();
		}

		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}

		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return figureFacet.getFullBounds();
		}

		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator, boolean readOnly)
		{
			return null;
		}

		public UDimension getCreationExtent()
		{
			return new UDimension(0, 0);
		}

		public String getFigureName()
		{
			return "";
		}

		public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
			return null;
		}

		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, false);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			return basicGem.getPreviewFacet();
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return figureFacet.getFullBounds();
		}
		
		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			return figureFacet.getFullBounds();
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		}
	
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
		 */
		public void middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public Object getSubject()
		{
			return null;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
		 */
		public boolean hasSubjectBeenDeleted()
		{
			return false;
		}

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
    }

    public void performPostContainerDropTransaction()
    {
    }

		public boolean canMoveContainers()
		{
			return true;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return false;
    }

    public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      return null;
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return null;
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
		}
	}
	
	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
	{
		return apprearanceFacet;
	}
	
	public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
	}
}
