package com.hopstepjump.jumble.umldiagrams.associationarc;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class AssociationArcAppearanceGem implements Gem
{
  public static final int ASSOCIATION_TYPE = 1;
  public static final int AGGREGATION_TYPE = 2;
  public static final int COMPOSITION_TYPE = 3;

  public static String getRecreatorName(int type)
  {
    switch (type)
    {
      case AGGREGATION_TYPE:
        return "Aggregation";
      case COMPOSITION_TYPE:
        return "Composition";
      default:
        return "Association";
    }
  }
  
  /** the type of association */
  private int type;
  private boolean unidirectional;
  private BasicArcAppearanceFacet appearanceFacet = new BasicArcAppearanceFacetImpl();
  private FigureFacet figureFacet;
  
  public AssociationArcAppearanceGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
  	PersistentProperties properties = pfig.getProperties();
    type = properties.retrieve("type", ASSOCIATION_TYPE).asInteger();
    unidirectional = properties.retrieve("uni", false).asBoolean();
	}

	public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return appearanceFacet;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  
  private class BasicArcAppearanceFacetImpl implements BasicArcAppearanceFacet
  {

    public void addToPersistentProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("type", type, ASSOCIATION_TYPE));
      properties.add(new PersistentProperty("uni", unidirectional, false));
    }

    public ZNode formAppearance(ZShape mainArc, UPoint start, UPoint second, UPoint secondLast, UPoint last, CalculatedArcPoints calculated, boolean curved)
    {
      // make the thin and thick lines
      ZGroup group = new ZGroup();
  
      mainArc.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      
      // make the possible composite symbol
      ZPolygon diamond = new ZPolygon(start);
      diamond.add(start.add(new UDimension(-4, -8)));
      diamond.add(start.add(new UDimension(0, -16)));
      diamond.add(start.add(new UDimension(4, -8)));
      diamond.add(start);
      diamond.setPenPaint(Color.black);
      if (type == COMPOSITION_TYPE)
        diamond.setFillPaint(Color.black);
      else
        diamond.setFillPaint(Color.white);
      UDimension diamondDimension = second.subtract(start);
      ZTransformGroup diamondGroup = new ZTransformGroup(new ZVisualLeaf(diamond));
      diamondGroup.rotate(diamondDimension.getRadians() + Math.PI/2, start.getX(), start.getY());
  
      // make the arrowhead
      ZPolygon poly = new ZPolygon(last);
      poly.setClosed(false);
      poly.add(last.add(new UDimension(-5, -12)));
      poly.add(last);
      poly.add(last.add(new UDimension(5, -12)));
      poly.setPenPaint(Color.black);
      poly.setFillPaint(null);
      poly.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      UDimension dimension = secondLast.subtract(last);
      ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
      arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());
  
      // add the thin line
      group.addChild(new ZVisualLeaf(mainArc));
      
      // add the arrow
      if (unidirectional)
      {
        arrowGroup.setChildrenFindable(false);
        arrowGroup.setChildrenPickable(false);
        group.addChild(arrowGroup);
      }
  
      // add the diamond (possibly!)
      if (type != ASSOCIATION_TYPE)
      {
        diamondGroup.setChildrenFindable(false);
        diamondGroup.setChildrenPickable(false);
        group.addChild(diamondGroup);
      }
  
      group.setChildrenFindable(false);
      group.setChildrenPickable(true);
      return group;
    }

    public String getFigureName()
    {
      return AssociationCreatorGem.getFigureName(type);
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
			boolean readOnly = figureFacet.isSubjectReadOnlyInDiagramContext(false);
			if (readOnly)
				return;
			
      Utilities.addSeparator(menu);
      JMenu typeMenu = new JMenu("Type");
      typeMenu.add(getChangeAssociationTypeMenuItem(coordinator, "composition", COMPOSITION_TYPE));
      typeMenu.add(getChangeAssociationTypeMenuItem(coordinator, "aggregation", AGGREGATION_TYPE));
      typeMenu.add(getChangeAssociationTypeMenuItem(coordinator, "association", ASSOCIATION_TYPE));
      menu.add(typeMenu);
      menu.add(getChangeUnidirectionalityMenuItem(coordinator));
    }
    
    private JCheckBoxMenuItem getChangeUnidirectionalityMenuItem(final ToolCoordinatorFacet coordinator)
    {
      JCheckBoxMenuItem item = new JCheckBoxMenuItem("Unidirectional");
      item.setState(unidirectional);
  
      item.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // adjust the visibility
          coordinator.startTransaction(
                "changed unidirectionality to " + !unidirectional,
                "restored unidirectionality to " + !unidirectional);
          unidirectional = !unidirectional;
          coordinator.commitTransaction();
        }
      });
      return item;
    }

    private JCheckBoxMenuItem getChangeAssociationTypeMenuItem(final ToolCoordinatorFacet coordinator, final String name, final int newType)
    {
      JCheckBoxMenuItem item = new JCheckBoxMenuItem(name);
      item.setState(type == newType);
  
      item.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // adjust the visibility
        	coordinator.startTransaction(
                "changed association type to " + name,
                "restored association type from " + name);
        	type = newType;
        	coordinator.commitTransaction();
        }
      });
      return item;
    }

		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
      return end != null && AssociationCreatorGem.acceptsOneOrBothAnchors(start, end);
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		}

		public Object getSubject()
		{
			return null;
		}

		public boolean hasSubjectBeenDeleted()
		{
			return false;
		}

		public void makeReanchorAction(AnchorFacet start, AnchorFacet end)
		{
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      Package visualStratum = repository.findVisuallyOwningStratum(
          figureFacet.getDiagram(),
          figureFacet.getContainedFacet().getContainer());
      Package owningStratum = repository.findOwningStratum((Element) figureFacet.getSubject());
      boolean locatedInCorrectView = visualStratum == owningStratum;

      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet) || !locatedInCorrectView;
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
      return new HashSet<String>();
    }

		public ToolFigureClassification getToolClassification(UPoint point)
		{
			return new ToolFigureClassification("association", null);
		}

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }
}
