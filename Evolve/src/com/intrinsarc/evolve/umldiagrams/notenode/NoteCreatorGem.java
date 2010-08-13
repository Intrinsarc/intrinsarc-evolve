package com.intrinsarc.evolve.umldiagrams.notenode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulation.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;


public final class NoteCreatorGem implements Gem
{
	public static final String NAME = "Note";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean autoSized = true;
  private boolean useHTML;
  private boolean hideNote;
  private boolean wordWrap = true;
	
  public NoteCreatorGem()
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
	    return NoteNodeGem.FIGURE_NAME;
	  }

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

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
      if (properties.retrieve(">useHTML", false).asBoolean())
        comment.setBody(TextManipulatorGem.HTML_START_TEXT);

      return comment;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);

	  	BasicNodeGem basicGem = new BasicNodeGem(
          getRecreatorName(),
          diagram,
          figureId,
          location,
          actualProperties.retrieve(">auto").asBoolean(),
          false);
	  	NoteNodeGem noteGem = new NoteNodeGem(
          (Comment) subject,
          diagram,
          figureId,
          actualProperties.retrieve(">useHTML").asBoolean(),
          actualProperties.retrieve(">hideNote").asBoolean(),
          actualProperties.retrieve(">wordWrap").asBoolean());
			noteGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeAppearanceFacet(noteGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(noteGem.getBasicNodeContainerFacet());
			
	    diagram.add(basicGem.getBasicNodeFigureFacet());
	  }
    
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
	  	NoteNodeGem noteGem = new NoteNodeGem(figure);
			noteGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeAppearanceFacet(noteGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(noteGem.getBasicNodeContainerFacet());

			return basicGem.getBasicNodeFigureFacet();
		}

		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty(">wordWrap", wordWrap, true));
      properties.addIfNotThere(new PersistentProperty(">auto", autoSized, true));
      properties.addIfNotThere(new PersistentProperty(">hideNote", hideNote, false));
      properties.addIfNotThere(new PersistentProperty(">useHTML", useHTML, false));
		}
	}

  public void setAutoSized(boolean auto)
  {
    this.autoSized = auto;
  }
  
  public void setHideNote(boolean hide)
  {
    hideNote = hide;
  }
  
  public void setUseHTML(boolean use)
  {
    useHTML = use;
  }

  public void setWordWrap(boolean wordWrap)
  {
    this.wordWrap = wordWrap;
  }
}