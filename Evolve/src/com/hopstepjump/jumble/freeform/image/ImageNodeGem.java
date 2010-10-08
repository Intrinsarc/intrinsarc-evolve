package com.hopstepjump.jumble.freeform.image;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.figurefacilities.selectionbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class ImageNodeGem implements Gem
{
  static final String FIGURE_NAME = "image";
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private ResizeVetterFacet resizeVetterFacet = new ResizeVetterFacetImpl();
  private LocationFacet locationFacet = new LocationFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private Comment subject;
  private ZImage cachedImage;
  private int oldCount = 0;

  public ImageNodeGem(Object subject)
  {
    this.subject = (Comment) subject;
  }
  
  public ImageNodeGem(PersistentFigure pfig)
  {
    subject = (Comment) pfig.getSubject();
  }
  
  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
    return appearanceFacet;
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
    figureFacet.registerDynamicFacet(locationFacet, LocationFacet.class);
  }

  private class ResizeVetterFacetImpl implements ResizeVetterFacet
  {
    /**
     * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#startResizeVet()
     */
    public void startResizeVet()
    {
    }
  
    /**
     * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedBounds(DiagramView, int, UBounds, boolean)
     */
    public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
    {
      return bounds;
    }
  
    /**
     * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedExtent(UBounds)
     */
    public UDimension vetResizedExtent(UBounds bounds)
    {
      if (figureFacet.isAutoSized())
      {
        if (cachedImage == null)
          return appearanceFacet.getCreationExtent();
        return new UDimension(cachedImage.getBounds().getWidth(), cachedImage.getBounds().getHeight());
      }
      
      // make sure that the bounds have the same ratio, using X as the master
      UDimension dim = bounds.getDimension();
      double ratio = 1;
      if (cachedImage != null)
        ratio = cachedImage.getBounds().getHeight() / cachedImage.getBounds().getWidth();
      return new UDimension(dim.getWidth(), dim.getWidth() * ratio);
    }
  }
  
  private class LocationFacetImpl implements LocationFacet
  {
    /**
     * @see com.hopstepjump.idraw.figurefacilities.selectionbase.LocationFacet#setLocation(MPackage)
     */
    public void setLocation()
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      
      // locate to the diagram, or a possible nesting package
      // look upwards, until we find one that has a PackageFacet registered
      Namespace space = (Package) figureFacet.getDiagram().getLinkedObject();
      Namespace currentSpace = (Package) subject.getOwner();
      Namespace containerSpace = repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
      if (containerSpace != null)
        space = containerSpace;
      
      // make sure that the package is not set to be owned by itself somehow
      for (Element owner = space; owner != null; owner = owner.getOwner())
        if (owner == subject)
          return;
      
      currentSpace.getOwnedComments().remove(subject);
      space.getOwnedComments().add(subject);
    }
  }
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  { 
    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
     */
    public UBounds getAutoSizedBounds(boolean autoSized)
    {
      UBounds fullBounds = figureFacet.getFullBounds();
      UDimension newExtent = fullBounds.getDimension();
      if (cachedImage != null && autoSized)
        newExtent = new UDimension(cachedImage.getBounds().getWidth(), cachedImage.getBounds().getHeight());
      return new UBounds(figureFacet.getFullBounds().getPoint(), newExtent);
    }
    
    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}	

    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.Figure#formView()
     */
    public ZNode formView()
    {
    	// possibly remake the cached image
    	makeCachedImage();
    	
      UBounds bounds = figureFacet.getFullBounds();
      ZGroup group = new ZGroup();
      
      if (cachedImage != null)
      {
        // do we need to scale?
        ZTransformGroup scale = new ZTransformGroup(new ZVisualLeaf(cachedImage));
        UBounds imageBounds = new UBounds(cachedImage.getBounds());
        scale.setTransform(
            AffineTransform.getScaleInstance(
                bounds.getWidth() / imageBounds.getWidth(),
                bounds.getHeight() / imageBounds.getHeight()));
        ZTransformGroup translate = new ZTransformGroup(scale);
        translate.setTranslateX(bounds.getX());
        translate.setTranslateY(bounds.getY());
        group.addChild(translate);
        
        ZRectangle rect = new ZRectangle(bounds);
        rect.setPenPaint(null);
        rect.setFillPaint(new Color(255, 255, 255, 0));
        group.addChild(new ZVisualLeaf(rect));
      }
      else
      {
        ZRectangle rect = new ZRectangle(bounds);
        rect.setPenWidth(1);
        rect.setFillPaint(new Color(200, 200, 200, 100));
        group.addChild(new ZVisualLeaf(rect));
      }
          
      // indicate that these visual items are linked to this figure
      group.setChildrenPickable(false);
      group.setChildrenFindable(false);
      group.putClientProperty("figure", figureFacet);
      
      return group;
    }
    
    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.Figure#getFigureName()
     */
    public String getFigureName()
    {
      return FIGURE_NAME;
    }
  
    /**
     * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
     */
    public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
    {
      return new Manipulators(
          null,
          new ResizingManipulatorGem(
          		coordinator,
              figureFacet,
              diagramView,
              figureFacet.getFullBounds(),
              resizeVetterFacet,
              firstSelected).getManipulatorFacet());
    }

    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
     */
    public FigureFacet getActualFigureForSelection()
    {
      return figureFacet;
    }
  
    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getContextMenu(IDiagramView, IToolCoordinator)
     */
    public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      if (figureFacet.isSubjectReadOnlyInDiagramContext(false))
        return null;
      
      JPopupMenu popup = new JPopupMenu();
      popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
      
      // for adding operations
      JMenuItem chooseImageItem = new JMenuItem("Choose image");

      chooseImageItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          File recent = PreferenceTypeDirectory.recent.getLastVisitedDirectory();
          JFileChooser chooser = new CustomisedFileChooser(recent);
          if (chooser.showOpenDialog(diagramView.getCanvas()) != JFileChooser.APPROVE_OPTION)
            return;

          try
          {
            File selected = chooser.getSelectedFile();
            PreferenceTypeDirectory.recent.setLastVisitedDirectory(selected);
            
            String selectedName = selected.getAbsolutePath();
            byte[] bytes = FileUtilities.loadFileAsBinary(selected);
            String suffix = "gif";
            int suffixIndex = selectedName.lastIndexOf('.'); 
            if (suffixIndex != -1 && selectedName.length() > suffixIndex)
              suffix = selectedName.substring(suffixIndex + 1).toLowerCase();

            coordinator.startTransaction("loaded new image", "unloaded image");
            subject.setBinaryFormat("Image/" + suffix);
            subject.setBinaryData(bytes);
            subject.setBinaryCount(subject.getBinaryCount() + 1);
            coordinator.commitTransaction();
          }
          catch (Throwable ex)
          {
            // complain about the format etc...
            coordinator.invokeErrorDialog(
                "Error loading image",
                "Image file cannot be loaded.  Message is " + ex.getMessage() + ".");
          }
        }
      });
      popup.add(chooseImageItem);

      return popup;
    }
  
    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
     */
    public UDimension getCreationExtent()
    {
      return new UDimension(100, 100);
    }
  
    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#makeNodePreviewFigure(PreviewCacheFacet, IDiagram, UPoint, boolean)
     */
    public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
    {
      BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
      basicGem.connectPreviewCacheFacet(previews);
      basicGem.connectFigureFacet(figureFacet);
      return basicGem.getPreviewFacet();
    }

    /**
     * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
     */
    public boolean acceptsContainer(ContainerFacet container)
    {
      return true;
    }
    
    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
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
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
     */
    public void addToPersistentProperties(PersistentProperties properties)
    {
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
     */
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      int newCount = subject.getBinaryCount();
      if (newCount == oldCount || pass != ViewUpdatePassEnum.LAST)
        return;
      
      // get the new width and height
      makeCachedImage();          
      figureFacet.performResizingTransaction(
          appearanceFacet.getAutoSizedBounds(figureFacet.isAutoSized()));
    }

    private void makeCachedImage()
    {
      int newCount = subject.getBinaryCount();
      if (oldCount != newCount)
      {
	      // load the binary data into the ZImage
	      byte[] data = subject.getBinaryData();
	      if (data == null)
	        cachedImage = null;
	      else
	        cachedImage = new ZImage(data);
	      oldCount = newCount;
      }
    }
  
    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
     */
    public void middleButtonPressed(ToolCoordinatorFacet coordinator)
    {
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
     */
    public Object getSubject()
    {
      return subject;
    }

    /**
     * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
     */
    public boolean hasSubjectBeenDeleted()
    {
      // cannot delete something with no subject
      if (subject == null)
        return false;
      
      return subject.isThisDeleted();
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
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
		}
  }
}
