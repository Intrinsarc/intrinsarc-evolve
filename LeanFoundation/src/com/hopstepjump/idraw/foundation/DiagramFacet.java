package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public interface DiagramFacet extends TransactionManagerFacet
{
	/** additional undo/redo support */
	public void checkpointCommitTransaction();
	public void completeUndoTransaction();
	public void completeRedoTransaction();
	public void forceAdjust(FigureFacet figure);

	/** for chained diagrams */
	public DiagramFacet getSource();
  public Object getLinkedObject();
  public void setLinkedObject(Object linkedObject);

  public int getFigureId();
  public boolean contains(FigureFacet figure );

	public void addProperty(String name, String value);
	/** throws a runtime exception if not found */
	public String getProperty(String name);
	
  public void addPersistentFigures(Collection<PersistentFigure> persistentFigures, UDimension offset);
  public PersistentDiagram makePersistentDiagram();
  /** make persistent figures of all the ids (no children or links followed) */
  public Map<String, PersistentFigure> makePersistentFigures(String[] ids, boolean includeChildren);

  public void resyncViews();
  // adjust the diagram, requiring undo/redo support
  public void revert();
  public void add(FigureFacet figure );
  public void remove(FigureFacet figure);
  //////////////////////////////////////////////////

  public List<FigureFacet> getFigures( );
  public boolean isEmpty();

  // identity management
  public DiagramReference getDiagramReference();
  public FigureReference makeNewFigureReference();
  public FigureFacet retrieveFigure(String id);
  public FigureFacet possiblyRetrieveFigure(String id);
  
  public void sendChangesToListeners();
  public void addListener(String id, DiagramListenerFacet listenerFacet);
  public void removeListener(String id);
  public boolean hasListeners();
  public boolean isModified();
  public void resetModified();
  public long getLRUTime();
  public void setLRUTime(long time);
	public boolean isClipboard();
  public void resizeEntireDiagram();
  
  /**
   * view update management -- due to subjects altering
   * @param pass TODO
   * @param initialRun TODO
   */
  public void formViewUpdate(ViewUpdatePassEnum pass, boolean initialRun);
  public void regenerate(PersistentDiagram diagram);
  public boolean isReadOnly();
  public Object getPossiblePerspective();
  // called when diagram is no longer required so that resources can be freed
	public void dispose();
	public List<FigureFacet> locateTops(double y, String figureName);
	public List<FigureFacet> locateLefts(double x, String figureName);
	public int getNumberOfChanges();
}
