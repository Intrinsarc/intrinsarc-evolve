package com.hopstepjump.jumble.deltaview;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.errorchecking.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

interface Tester
{
  public boolean test(Element element);
}

public class DeltaAdornerGem
{
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

  private DeltaAdornerFacet adornerFacet = new DeltaAdornerFacetImpl();
  private ToolCoordinatorFacet coordinator;
  private boolean enabled;
  
  public DeltaAdornerGem(ToolCoordinatorFacet coordinator)
  {
    this.coordinator = coordinator;
  }
  
  public DeltaAdornerFacet getDeltaAdornerFacet()
  {
    return adornerFacet;
  }
  
  private class DeltaAdornerFacetImpl implements DeltaAdornerFacet
  {
    public DeltaAdornerFacetImpl()
    {
    }
    
    public ZNode adornFigure(final FigureFacet figure, int style)
    {
      DeltaTypeEnum display = DeltaTypeEnum.values()[style];
      return getNode(figure, display);
    }
    
    ZNode getNode(final FigureFacet figure, final DeltaTypeEnum deltaType)
    {
      ZTransformGroup group = new ZTransformGroup();
      
      double width = 20;
      double height = 10;
      double half = height / 2;
      
      // set the middle of the circle, and the colour
      ZGroup middle = new ZGroup();
      Color col = null;
      switch (deltaType)
      {
        case DELTA_ADD:
          {
            col = Color.GREEN.darker();
            ZRectangle rect = new ZRectangle(4, 4, height - 8, height - 8);
            rect.setPenPaint(col);
            rect.setFillPaint(col);
            rect.setPenWidth(1.5);
            middle.addChild(new ZVisualLeaf(rect));
          }
          break;
        case DELTA_REPLACE:
          {
            col = new Color(235, 160, 0);
            ZLine line = new ZLine(new UPoint(height - 3, 3), new UPoint(3, height - 3));
            line.setPenPaint(col);
            line.setPenWidth(2);
            middle.addChild(new ZVisualLeaf(line));
          }        
          break;
        case DELTA_DELETE:
        default:
          {
            col = Color.RED.darker();
            ZPolygon poly = new ZPolygon();
            poly.add(new UPoint(3, 3));
            poly.add(new UPoint(height - 1.5, 3));
            poly.add(new UPoint(3, height - 2));
            poly.setPenPaint(null);
            poly.setFillPaint(col);
            middle.addChild(new ZVisualLeaf(poly));
          }        
          break;
      }
      
      ZRectangle rect = new ZRectangle(height, 2, width - height - half, height - 4);
      rect.setFillPaint(col);
      rect.setPenPaint(col);
      
      ZPolygon tri = new ZPolygon();
      tri.setPenPaint(col);
      tri.setFillPaint(col);
      tri.add(new UPoint(width - half, 0));
      tri.add(new UPoint(width, half));
      tri.add(new UPoint(width - half, height));
      
      ZRectangle in = new ZRectangle(0, 0, height, height);
      in.setPenPaint(col);
      in.setPenWidth(2);
      in.setFillPaint(Color.WHITE);      

      group.addChild(new ZVisualLeaf(rect));
      group.addChild(new ZVisualLeaf(tri));
      group.addChild(new ZVisualLeaf(in));
      group.addChild(middle);
      
      UPoint moveTo;
      UBounds full = figure.getFullBounds();
      
      if (figure.getLinkingFacet() != null)
        moveTo = figure.getLinkingFacet().getMajorPoint(CalculatedArcPoints.MAJOR_POINT_MIDDLE).subtract(new UDimension(width, height / 2));
      else
        moveTo = full.getTopLeftPoint().subtract(new UDimension(width + 1, -2));
      
      group.setTranslation(moveTo.getX(), moveTo.getY());
      
      AdornerPopup mouse = new AdornerPopup(coordinator)
      {
        @Override
        public void addText(JPanel panel)
        {          
          // work out the text to go here
          switch (deltaType)
          {
            case DELTA_ADD:
              // don't display anything
              break;
            case DELTA_DELETE:
              // report on the elements which where deleted
              // this only occurs on a class or interface
              Object subject = figure.getSubject();
              if (subject instanceof Class)
              {
                Class cls = (Class) subject;
                reportDeletes(panel, cls.undeleted_getDeltaDeletedAttributes(), "attributes",
                  new Tester()
                  {
                    public boolean test(Element element)
                    {
                      return UMLTypes.extractInstanceOfPart(element) == null;
                    }
                  });
                reportDeletes(panel, cls.undeleted_getDeltaDeletedOperations(), "operations", null);
                reportDeletes(panel, cls.undeleted_getDeltaDeletedPorts(), "ports", null);
                reportDeletes(panel, cls.undeleted_getDeltaDeletedConnectors(), "connectors",
	                  new Tester()
		                {
		                  public boolean test(Element element)
		                  {
		                    return !((Connector) element).getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
		                  }
		                });
                reportDeletes(panel, cls.undeleted_getDeltaDeletedConnectors(), "port-links",
	                  new Tester()
		                {
		                  public boolean test(Element element)
		                  {
		                    return ((Connector) element).getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
		                  }
		                });
                reportDeletes(panel, cls.undeleted_getDeltaDeletedAttributes(), "parts",
                    new Tester()
                    {
                      public boolean test(Element element)
                      {
                        return UMLTypes.extractInstanceOfPart(element) != null;
                      }
                    });
                reportDeletes(panel, cls.undeleted_getDeltaDeletedTraces(), "traces", null);
              }
              if (subject instanceof Interface)
              {
                Interface iface = (Interface) subject;
                reportDeletes(panel, iface.undeleted_getDeltaDeletedAttributes(), "attributes",
                    new Tester()
                    {
                      public boolean test(Element element)
                      {
                        return UMLTypes.extractInstanceOfPart(element) == null;
                      }
                    });
                  reportDeletes(panel, iface.undeleted_getDeltaDeletedOperations(), "operations", null);                
              }
              if (subject instanceof RequirementsFeature)
              {
                RequirementsFeature req = (RequirementsFeature) subject;
                reportDeletes(panel, req.undeleted_getDeltaDeletedSubfeatures(), "subfeatures", null);
              }
              break;
            case DELTA_REPLACE:
              // report on the original which was replaced
              Element feature = (Element) figure.getSubject();
              Element replaced = ((DeltaReplacedConstituent) feature.getOwner()).getReplaced();

              String display =
                UMLNodeText.getNodeText(replaced) +
                " (from " +
                UMLNodeText.removeFirst(GlobalSubjectRepository.repository.getFullyQualifiedName(replaced.getOwner(), "::")) + ")";
              panel.add(new JLabel("<html><b>Replaces</b> " + display, DELTA_ICON, JLabel.LEFT));
              break;
          }
        }

        private void reportDeletes(JPanel panel, ArrayList deletes, String name, Tester tester)
        {
          List<Element> actual = new ArrayList<Element>();
          for (Object obj : deletes)
          {
            DeltaDeletedConstituent deleted = (DeltaDeletedConstituent) obj;
            if (tester == null || tester.test(deleted.getDeleted()))
              actual.add(deleted.getDeleted());
          }
          if (!actual.isEmpty())
          {
            panel.add(new JLabel("<html><b>Deleted " + name, DELTA_ICON, JLabel.LEFT));
            for (Element deleted : actual)
              panel.add(
                  new JLabel(
                      "     " +
                      UMLNodeText.getNodeText(deleted) +
                      " (from " +
                      UMLNodeText.removeFirst(
                          GlobalSubjectRepository.repository.getFullyQualifiedName(
                              deleted.getOwner(), "::")) + ")"));              
          }
        } 
      };
      group.addMouseListener(mouse);
      group.addMouseMotionListener(mouse);
      
      return group;
    }
    
    public Map<FigureFacet, Integer> determineAdornments(DiagramFacet diagram, Set<FigureFacet> figures)
    {
      if (!enabled)
        return new HashMap<FigureFacet, Integer>();
      Map<FigureFacet, Integer> displays = new HashMap<FigureFacet, Integer>();
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      
      for (FigureFacet figure : figures)
      {
        if (figure.getSubject() instanceof Classifier)
        {
          Classifier cls = (Classifier) figure.getSubject();
          
          // only adorn if this is at home
          if (repository.findVisuallyOwningStratum(figure.getDiagram(), figure.getContainedFacet().getContainer()) ==
              repository.findOwningStratum(cls))
          {
            // ask the class to adorn itself
            if (figure.hasDynamicFacet(DelegatedDeltaAdornerFacet.class))
            {
              DelegatedDeltaAdornerFacet delegated =
                (DelegatedDeltaAdornerFacet) figure.getDynamicFacet(DelegatedDeltaAdornerFacet.class);
              displays.putAll(delegated.getDeltaDisplaysAtHome());
            }
          }
        }

        if (figure.getSubject() instanceof RequirementsFeature)
        {
          RequirementsFeature req = (RequirementsFeature) figure.getSubject();
          
          // only adorn if this is at home
          if (repository.findVisuallyOwningStratum(figure.getDiagram(), figure.getContainedFacet().getContainer()) ==
              repository.findOwningStratum(req))
          {
            // ask the class to adorn itself
            if (figure.hasDynamicFacet(DelegatedDeltaAdornerFacet.class))
            {
              DelegatedDeltaAdornerFacet delegated =
                (DelegatedDeltaAdornerFacet) figure.getDynamicFacet(DelegatedDeltaAdornerFacet.class);
              displays.putAll(delegated.getDeltaDisplaysAtHome());              
            }
          }
        }
      }
      
      return displays;
    }

    public void setEnabled(boolean newEnabled)
    {
      enabled = newEnabled;
    }

    public void toggleEnabled()
    {
      enabled = !enabled;
    }

    public boolean isEnabled()
    {
      return enabled;
    }
  }
}

