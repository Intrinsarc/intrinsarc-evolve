package com.hopstepjump.jumble.umldiagrams.messagearc;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class MessageArcAppearanceGem implements Gem
{
  public static final int CALL_TYPE   = 1;
  public static final int RETURN_TYPE = 2;
  public static final int SEND_TYPE   = 3;

  public static String getRecreatorName(int type)
  {
    switch (type)
    {
      case CALL_TYPE:
        return "call (synchronous message)";
      case RETURN_TYPE:
        return "return";
      default:
        return "send (asynchronous message)";
    }
  }
  
  /** the type of message */
  private int type;
  private BasicArcAppearanceFacet appearanceFacet = new BasicArcAppearanceFacetImpl();
  private FigureFacet figureFacet;
  
  
  public MessageArcAppearanceGem(PersistentProperties properties)
  {
    type = properties.retrieve("type", CALL_TYPE).asInteger();
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
      properties.add(new PersistentProperty("type", type, CALL_TYPE));
    }

    public ZNode formAppearance(ZShape mainArc, UPoint start, UPoint second, UPoint secondLast, UPoint last, CalculatedArcPoints calculated, boolean curved)
    {
      // make the thin and thick lines
      ZGroup group = new ZGroup();
  
      mainArc.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      
      // make the arrowhead
      ZPolygon poly = new ZPolygon(last);
      UPoint first = last.add(new UDimension(-6, -12)); 
      poly.add(first);
      poly.add(last);
      poly.add(last.add(new UDimension(5, -12)));
      poly.setPenPaint(Color.BLACK);
      poly.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      poly.setClosed(false);
      poly.setFillPaint(null);
      if (type == CALL_TYPE)
      {
        poly.add(first);
        poly.setClosed(true);
        poly.setFillPaint(Color.BLACK);
      }
      
      UDimension dimension = secondLast.subtract(last);
      ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
      arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());
  
      // make the line dotted if this is a return
      if (type == RETURN_TYPE)
        mainArc.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));

      // add the thin line
      group.addChild(new ZVisualLeaf(mainArc));
      
      // add the arrow
      arrowGroup.setChildrenFindable(false);
      arrowGroup.setChildrenPickable(false);
      group.addChild(arrowGroup);
  
      group.setChildrenFindable(false);
      group.setChildrenPickable(true);
      return group;
    }

    public String getFigureName()
    {
      return MessageCreatorGem.getActualFigureName(type);
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
    }
    
		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
			return true;
		}

		public Command formViewUpdateCommandAfterSubjectChanged(boolean isTop, ViewUpdatePassEnum pass)
		{
			return null;
		}

		public Object getSubject()
		{
			return null;
		}

		public boolean hasSubjectBeenDeleted()
		{
			return false;
		}

		public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
		{
			return null;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return false;
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
      return null;
    }
  }
}
