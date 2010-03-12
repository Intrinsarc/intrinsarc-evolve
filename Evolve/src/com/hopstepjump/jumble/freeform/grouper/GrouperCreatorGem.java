/*
 * Created on Dec 31, 2003 by Andrew McVeigh
 */
package com.hopstepjump.jumble.freeform.grouper;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.repositorybase.*;

/**
 * @author Andrew
 */
public class GrouperCreatorGem
{
	public static final String NAME = "Grouper";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private String stereotype;
  private String text;
  private boolean textAtTop;
  private Color fillColor = GrouperNodeGem.INITIAL_FILL_COLOR;
	
  public GrouperCreatorGem()
	{
	}

	public NodeCreateFacet getNodeCreateFacet()
	{
		return nodeCreateFacet;
	}


	private class NodeCreateFacetImpl implements NodeCreateFacet
	{
		public String getFigureName()
		{
			return GrouperNodeGem.FIGURE_NAME;
		}
	
		public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
		{
      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);
		  
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, false);
			GrouperNodeGem nodeGem =
			  new GrouperNodeGem(
			      (Comment) subject,
			      diagram,
			      figureId,
			      actualProperties.retrieve(">textAtTop", false).asBoolean(),
	          actualProperties.retrieve(">fillColor").asColor());
			basicGem.connectBasicNodeAppearanceFacet(nodeGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(nodeGem.getBasicNodeContainerFacet());
			nodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
				
			diagram.add(basicGem.getBasicNodeFigureFacet());
		}
	
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			GrouperNodeGem grouperGem =
				new GrouperNodeGem(figure);
			basicGem.connectBasicNodeAppearanceFacet(grouperGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(grouperGem.getBasicNodeContainerFacet());
			grouperGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

			return basicGem.getBasicNodeFigureFacet();
		}

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // possibly resurrect
      if (previouslyCreated != null)
      {
        repository.decrementPersistentDelete((Element) previouslyCreated);
        return previouslyCreated;
      }

      // no nested classes currently supported
      Namespace owner = (Package) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      // get the package associated with this diagram, and add the new package to it
      Comment comment = owner.createOwnedComment();
      
      // should we set a stereotype?
      String stereoName = properties.retrieve(">stereotype", (String) null).asString();
      if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getComment(), stereoName);
        if (stereo != null)
          comment.getAppliedBasicStereotypes().add(stereo);
      }
      
      // should we set the text?
      String text = properties.retrieve(">text", (String) null).asString();
      if (text != null)
        comment.setBody(text);

      return comment;
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">text", text));
      properties.addIfNotThere(new PersistentProperty(">textAtTop", textAtTop, false));
      properties.addIfNotThere(new PersistentProperty(">fillColor", fillColor, GrouperNodeGem.INITIAL_FILL_COLOR));
    }
	}

  public void setStereotype(String stereotypeName)
  {
    this.stereotype = stereotypeName;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }

  public void setTextAtTop(boolean textAtTop)
  {
    this.textAtTop = textAtTop;
  }

  public void setFillColor(Color color)
  {
    this.fillColor = color;
  }
}
