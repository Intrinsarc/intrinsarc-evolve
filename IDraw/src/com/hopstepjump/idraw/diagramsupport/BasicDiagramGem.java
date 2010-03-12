package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public final class BasicDiagramGem implements Gem
{
	// an upper value for diagram chaining so when we add figures, they are in a different range
	// to any from those inherited from diagrams
	private static final int CHAINED_UPPER_FIGURE_ID = 1000000000;

  private Object linkedObject;
  private Map<String, FigureFacet> figures;
  private Map<String, DiagramListenerFacet> listeners = new HashMap<String, DiagramListenerFacet>();
  private DiagramReference reference;
  private DiagramFacetImpl diagramFacet = new DiagramFacetImpl();
  private List<DiagramChange> changes = new ArrayList<DiagramChange>();
  private int figureId = 0;
  private boolean modified = false;
  private PersistentDiagram persistentDiagram;
  private long lruTime = System.currentTimeMillis();
	private long openingTime;
	private boolean isClipboard;
	private PersistentProperties properties;
	private boolean generateChanges;
	private BasicDiagramReadOnlyFacet readOnlyFacet;
	private DiagramFacet source;
	private Object perspective;
	private DiagramPostProcessor postProcessor;
	private boolean temporary;

	/**
	 * the clipboard attribute has no bearing on the behaviour of this class, but is used by other logic to
	 * exclude clipboard diagrams from:
	 * 1) saving logic
	 * 2) being closed down in the scaleable diagram code
	 * 3) from being reverted in the undo logic (related to scaleable diagrams)
	 */
  public BasicDiagramGem(DiagramReference reference, boolean isClipboard, DiagramPostProcessor postProcessor, boolean temporary)
  {
    this.reference = reference;
    this.isClipboard = isClipboard;
    this.postProcessor = postProcessor;
    this.temporary = temporary;
    generateChanges = true;
	  properties = new PersistentProperties();
    persistentDiagram = new PersistentDiagram();
	  
	  clear();
    initialiseInternals();
  }
  
	/**
	 * the clipboard attribute has no bearing on the behaviour of this class, but is used by other logic to
	 * exclude clipboard diagrams from:
	 * 1) saving logic
	 * 2) being closed down in the scaleable diagram code
	 * 3) from being reverted in the undo logic (related to scaleable diagrams)
	 * @param postProcessor 
	 */
  public BasicDiagramGem(DiagramReference reference, final DiagramFacet source, Object perspective, DiagramPostProcessor postProcessor, boolean temporary)
  {
    this.reference = reference;
    this.source = source;
    this.perspective = perspective;
    this.postProcessor = postProcessor;
    this.temporary = temporary;
    
    // to stop the source diagram being disposed of by the diagram registry
    source.addListener(reference.getId(), new DiagramListenerFacet()
    {
			public void haveModifications(DiagramChange[] changes)
			{}
			public void refreshViewAttributes()
			{}
    });
    
    generateChanges = true;
	  properties = new PersistentProperties();
	  persistentDiagram = source.makePersistentDiagram();

	  clear();
    initialiseInternals();
  }
  
  public BasicDiagramGem(Object linkedObject, PersistentDiagram persistentDiagram, DiagramPostProcessor postProcessor, boolean temporary)
  {
    this.linkedObject = linkedObject;
    this.persistentDiagram = persistentDiagram;
    this.postProcessor = postProcessor;
    this.temporary = temporary;
    reference = new DiagramReference(persistentDiagram.getDiagramId());
    clear();
    initialiseInternals();
  }
  
  public void connectBasicDiagramReadOnlyFacet(BasicDiagramReadOnlyFacet readOnlyFacet)
  {
    this.readOnlyFacet = readOnlyFacet;
  }
  
  private void clear()
  {
    figures = new HashMap<String, FigureFacet>();
    changes.clear();
    openingTime = System.currentTimeMillis();  
  }
  
  private void initialiseInternals()
  {
  	figureId = source == null ? persistentDiagram.getLastFigureId() : CHAINED_UPPER_FIGURE_ID;
    diagramFacet.addPersistentFigures(persistentDiagram.getFigures(), null, false);
    // save all the properties
    properties = (PersistentProperties) persistentDiagram.getProperties().clone();    
  }
  
  /** this must be run after registration, otherwise the commands won't be able to find this diagram */
  public void completeInitialisationAfterRegistration()
  {    
    // resize the entire diagram, to avoid display glitches
    diagramFacet.resizeEntireDiagram();
    
    // reset the modification flag, as we have just recreated from a persistent store
    modified = false;
		generateChanges = true;
  }
  
  
	public DiagramFacet getDiagramFacet()
	{
		return diagramFacet;
	}

	private CompositeCommand concreteFormViewUpdateCommand(boolean isTop, ViewUpdatePassEnum pass, boolean initialRun)
	{
		// delete any figures whose subjects have been removed
		// using the "delete" technology developed for the cut/copy/paste/delete functions
		
		// 1) ask all figures if there subject has been deleted, and collate
		Set<String> deletionFigureIds = new HashSet<String>();
    if (isTop && pass == ViewUpdatePassEnum.START && !initialRun)
    {
			final Set<FigureFacet> figuresWithDeletedSubjects = new HashSet<FigureFacet>();
			for (FigureFacet figure : figures.values())
			{
				if (figure.hasSubjectBeenDeleted())
					figuresWithDeletedSubjects.add(figure);
			}

			// 2) form a complete set of all figures to delete, including children
			ChosenFiguresFacet chosenFigures = new ChosenFiguresFacet()
			{
				public boolean isChosen(FigureFacet figure)
				{
					return figuresWithDeletedSubjects.contains(figure);
				}
			};
			deletionFigureIds = DeleteFromDiagramHelper.getFigureIdsIncludedInDelete(figuresWithDeletedSubjects, chosenFigures, false);
			

			// 3) make a delete command to remove the views with deleted subjects
			DeleteFromDiagramHelper.makeDeleteCommand("formViewUpdateCommand deletion", "", "", diagramFacet, deletionFigureIds, false);
    }

    // ensure we process the figures in containment order
		for (FigureFacet figure : figures.values())
		{
			if (initialRun && includedInChanges(figure) || !initialRun)
			{
				// form a view update, but don't include any deleted figures
				if (!deletionFigureIds.contains(figure.getId()))
						figure.updateViewAfterSubjectChanged(isTop, pass);
			}
		}
		if (!initialRun)			
			refreshViewAttributes();

		return null;
	}

	private boolean includedInChanges(FigureFacet figure)
	{
		for (DiagramChange d : changes)
			if (d.getFigure() == figure)
				return true;
		return false;
	}

	private void refreshViewAttributes()
	{
		// tell all the diagram views to process the modification
		for (DiagramListenerFacet listener : listeners.values())
			listener.refreshViewAttributes();
	}

	private class DiagramFacetImpl implements DiagramFacet
	{
		private List<UndoRedoStates> stateStack = new ArrayList<UndoRedoStates>();
		private Map<FigureFacet, PersistentFigure> trans = new HashMap<FigureFacet, PersistentFigure>();
		private int pos = 0;
		private boolean inUndoRedo = false;
		private boolean insideTransaction = false;
		
		public void enforceTransactionDepth(int depth)
		{
		}
		
		public int getTransactionPosition()
		{
			return pos;
		}
		
		public int getTotalTransactions()
		{
			return stateStack.size();
		}

		private void addToCurrentTransaction(UndoRedoAction action, FigureFacet figure)
		{
			if (!inUndoRedo)
			{
				UndoRedoStates current = ensureCurrent();
				current.addState(new UndoRedoState(action, figure.makePersistentFigure()));
			}
		}

		public void primeForUpdateBeforeRevert()
		{
			// add in an undoredostates for an early change
			if (pos != 0)
				throw new IllegalStateException("Must be at index 0 for priming");
			if (insideTransaction)
				throw new IllegalStateException("Cannot undo while in diagram transaction");
			stateStack.add(0, new UndoRedoStates());
		}
		
		/**
		 * ensure we have a valid current undoredostates to add to.
		 * possibly truncate the stack and add a blank one if needed.
		 * @return
		 */
		private UndoRedoStates ensureCurrent()
		{
			int size = stateStack.size(); 
			if (size <= pos)
				stateStack.add(new UndoRedoStates());
			else
			{
				if (stateStack.get(pos).isSealed())
				{
					for (int lp = 0; lp < size - pos; lp++)
						stateStack.remove(pos);
					stateStack.add(new UndoRedoStates());
				}
			}
			insideTransaction = true;
			return stateStack.get(pos);
		}
		
		/** undo/redo support */
		public void checkpointCommitTransaction()
		{
			sendChangesToListeners();
		}
		
		public void clearTransactionHistory()
		{
			pos = 0;
			stateStack.clear();
		}
		
		public void commitTransaction()
		{
			ensureCurrent();
			UndoRedoStates states = stateStack.get(pos++);
			
			// add any modifications
			List<UndoRedoState> urs = states.getStates();
			for (FigureFacet f : trans.keySet())
			{
				UndoRedoState state = new UndoRedoState(UndoRedoAction.MODIFY, trans.get(f));
				state.setAfterPersistentFigure(f.makePersistentFigure());
				urs.add(state);
			}			
			states.setSealed(true);
			sendChangesToListeners();
			trans.clear();
			insideTransaction = false;
		}
		
		public void undoTransaction()
		{
			if (insideTransaction)
				throw new IllegalStateException("Cannot undo while in diagram transaction");

			if (pos > 0)
			{
				inUndoRedo = true;
				UndoRedoStates all = stateStack.get(--pos);
				List<UndoRedoState> states = all.getStates();
				List<PersistentFigure> readd = new ArrayList<PersistentFigure>();
				for (int lp = states.size() - 1; lp >= 0; lp--)
				{
					UndoRedoState s = states.get(lp);
					switch (s.getAction())
					{
						case REMOVE:
							lp = addPersistentFigures(states, lp, -1);
							break;
						case MODIFY:
							{
								FigureFacet f = figures.get(s.getPersistentFigure().getId());
								if (f != null)
									remove(f);
								readd.add(s.getPersistentFigure());
							}
							break;
					}
				}
				addPersistentFigures(readd, new UDimension(0, 0));
				for (int lp = states.size() - 1; lp >= 0; lp--)
				{
					UndoRedoState s = states.get(lp);
					switch (s.getAction())
					{
						case ADD:
							{
								FigureFacet f = figures.get(s.getPersistentFigure().getId());
								if (f != null)
									remove(f);
							}
							break;
					}
				}
				sendChangesToListeners();
				inUndoRedo = false;
				trans.clear();
			}
		}
		
		public void redoTransaction()
		{
			if (insideTransaction)
				throw new IllegalStateException("Cannot redo while in diagram transaction");
			
			if (pos < stateStack.size())
			{
				inUndoRedo = true;
				UndoRedoStates all = stateStack.get(pos++);
				List<UndoRedoState> states = all.getStates();
				int size = states.size();
				List<PersistentFigure> readd = new ArrayList<PersistentFigure>();
				for (int lp = 0; lp < size; lp++)
				{
					UndoRedoState s = states.get(lp); 
					switch (s.getAction())
					{
						case ADD:
							lp = addPersistentFigures(states, lp, 1);
							break;
						case MODIFY:
							{
								FigureFacet f = figures.get(s.getPersistentFigure().getId());
								if (f != null)
									remove(f);
								readd.add(s.getPersistentFigureAfter());
							}
							break;
					}
				}
				addPersistentFigures(readd, new UDimension(0, 0));
				for (int lp = 0; lp < size; lp++)
				{
					UndoRedoState s = states.get(lp); 
					switch (s.getAction())
					{
						case REMOVE:
						{
							FigureFacet f = figures.get(s.getPersistentFigure().getId());
							if (f != null)
								remove(f);
						}
						break;
					}
				}
				sendChangesToListeners();
				inUndoRedo = false;
				trans.clear();
			}
		}
		
		private void addPersistentFigure(PersistentFigure persistent)
		{
			List<PersistentFigure> p = new ArrayList<PersistentFigure>();
			p.add(persistent);
			addPersistentFigures(p, new UDimension(0, 0));
		}

		private int addPersistentFigures(List<UndoRedoState> states, int current, int direction)
		{
			List<PersistentFigure> pfigs = new ArrayList<PersistentFigure>();
			UndoRedoAction act = states.get(current).getAction();
			int size = states.size();
			while (current >= 0 && current < size)
			{
				UndoRedoState state = states.get(current);
				if (!state.getAction().equals(act))
				{
					current -= direction;
					break;
				}
				pfigs.add(state.getPersistentFigure());
				current += direction;
			}
			addPersistentFigures(pfigs, new UDimension(0, 0));
			return current;
		}

		public void aboutToAdjust(FigureFacet figure)
		{
			if (!inUndoRedo && !trans.containsKey(figure))
			{
				ensureCurrent();
				PersistentFigure p = figure.makePersistentFigure();
				trans.put(figure, p);
				internallyAdjusted(figure);
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////
		
		public void add(FigureFacet figure)
	  {
	  	Validator.validateFigure(figure);
	  	addToCurrentTransaction(UndoRedoAction.ADD, figure);
	  	
			setModified(true);
		  figures.put(figure.getId(), figure);
		  haveModification(figure, DiagramChange.MODIFICATIONTYPE_ADD);
	      
	    // if this is a container, add contained
	    ContainerFacet container = figure.getContainerFacet();
	    if (container != null)
	    {
	    	Iterator <FigureFacet> iter = container.getContents();
	    	while (iter.hasNext())
					add(iter.next());
	    }
	  }
	
	  public void remove(FigureFacet figure)
	  {
	  	addToCurrentTransaction(UndoRedoAction.REMOVE, figure);
      FigureFacet removed = figures.remove(figure.getId());
      if (removed != null)
      {
        removed.cleanUp();
  			setModified(true);
  	    haveModification(removed, DiagramChange.MODIFICATIONTYPE_REMOVE);
  	      
  	    // if this is a container, remove contained
  	    ContainerFacet container = removed.getContainerFacet();
  	    if (container != null)
  	    {
  	    	Iterator<FigureFacet> iter = container.getContents();
  	    	while (iter.hasNext())
  					remove(iter.next());
  	    }
      }
	  }
	
	  public void adjusted(FigureFacet figure)
	  {
	  }
	  
	  private void internallyAdjusted(FigureFacet figure)
	  {
			setModified(true);
			// make sure we don't generate adjustment changes before the element is on the diagram!!
			if (contains(figure))
		  	haveModification(figure, DiagramChange.MODIFICATIONTYPE_ADJUST);
	  }
	
	  public boolean contains(FigureFacet figure)
	  {
	    FigureFacet retrieved = figures.get(figure.getId());
	    return retrieved != null && figure.getDiagram().getDiagramReference().equals(getDiagramReference());
	  }
	
		private void setModified(boolean newModified)
		{
			if (modified != newModified)
			{
				modified = newModified;				
				refreshViewAttributes();
			}
		}
	
	  public List<FigureFacet> getFigures()
	  {
	    return new ArrayList<FigureFacet>(figures.values());
	  }
	
	  public DiagramReference getDiagramReference()
	  {
	    return reference;
	  }
	
	  public FigureReference makeNewFigureReference()
	  {
	    return new FigureReference(reference, "" + figureId++);
	  }
	
	  public FigureFacet retrieveFigure(String id)
	  {
	    FigureFacet figure = figures.get(id);
	    if (figure == null)
	      throw new FigureNotFoundException("Can't find figure with id: " + id + " on diagram: " + reference);
	    return figure;
	  }
	  
	  public FigureFacet possiblyRetrieveFigure(String id)
	  {
	    return figures.get(id);
	  }
	  
		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#addListener(String, DiagramListenerFacet)
		 */
		public void addListener(String id, DiagramListenerFacet listenerFacet)
		{
			listeners.put(id, listenerFacet);
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#removeListener(String)
		 */
		public void removeListener(String id)
		{
			listeners.remove(id);
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#hasListeners()
		 */
		public boolean hasListeners()
		{
			return !listeners.isEmpty();
		}


	  private synchronized void haveModification(FigureFacet figure, int modificationType)
	  {
	    // buffer the change, until processBufferedsendChangesToListeners() is called
	    if (generateChanges && source == null)
	    {
		    DiagramChange change = new DiagramChange(figure, modificationType);
		    changes.add(change);
	    }
	  }

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#sendChangesToListeners()
		 */
		public synchronized void sendChangesToListeners()
		{
      // tell all the diagram views to process the modification
	    DiagramChange[] changeArray = changes.toArray(new DiagramChange[0]);
      for (DiagramListenerFacet listener : listeners.values())
        listener.haveModifications(changeArray);
	    changes.clear();
	  }

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#getFigureId()
		 */
		public int getFigureId()
		{
			return figureId;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#formViewUpdateCommand(SubjectAlteration[])
		 */
		public Command formViewUpdateCommand(boolean isTop, ViewUpdatePassEnum pass, boolean initialRun)
		{
			// don't do this if we are chained -- wait for the update of the chained elements
			if (source != null)
			{
				if (pass == ViewUpdatePassEnum.LAST /*&& !initialRun*/)
				{
					clear();
			    initialiseInternals();
					diagramFacet.addPersistentFigures(source.makePersistentDiagram().getFigures(), new UDimension(0, 0));
					for (ViewUpdatePassEnum passx : ViewUpdatePassEnum.values())
					{
						concreteFormViewUpdateCommand(true, passx, false);
						concreteFormViewUpdateCommand(true, passx, true);
					}
	
					// possibly post-process the diagram
			    if (postProcessor != null)
			    	postProcessor.postProcess(diagramFacet);
					
	        changes.add(new DiagramChange(null, DiagramChange.MODIFICATIONTYPE_RESYNC));
				}
			}
			else
				concreteFormViewUpdateCommand(isTop, pass, initialRun);
			return null;
		}

    /**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#isModified()
		 */
		public boolean isModified()
		{
			return modified && source == null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#getLRUTime()
		 */
		public long getLRUTime()
		{
			return lruTime;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#setLRUTime(long)
		 */
		public void setLRUTime(long time)
		{
			lruTime = time;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#resetModified()
		 */
		public void resetModified()
		{
			setModified(false);
		}
		
		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#isEmpty()
		 */
		public boolean isEmpty()
		{
			return figures.isEmpty();
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#add(MFigure[])
		 */
		public void addPersistentFigures(Collection<PersistentFigure> persistentFigures, UDimension offset)
		{
			addPersistentFigures(persistentFigures, offset, true);
		}
		
		public void addPersistentFigures(Collection<PersistentFigure> persistentFigures, UDimension offset, final boolean generateFullAdjustments)
		{
			// NOTE: this must be able to add figures that refer to existing figures that are possibly already in the diagram
			setModified(true);

			final boolean needToOffset = offset != null && offset.distance() != 0;
			final List<FigureFacet> addedFigures = new ArrayList<FigureFacet>();

			DiagramRecreator.recreateFigures(this, persistentFigures, figures,
				new RecreatorListener()
				{
					public void addedFigure(FigureFacet figure)
					{
						haveModification(figure, DiagramChange.MODIFICATIONTYPE_ADD);
						if (needToOffset || generateFullAdjustments)
							addedFigures.add(figure);
					}
					public void addedLink(LinkingFacet link)
					{
						if (!generateFullAdjustments)
						{
							haveModification(link.getAnchor1().getFigureFacet(), DiagramChange.MODIFICATIONTYPE_ADJUST);
							haveModification(link.getAnchor2().getFigureFacet(), DiagramChange.MODIFICATIONTYPE_ADJUST);
						}
					}
					public void addedToContainer(ContainerFacet container)
					{
						if (!generateFullAdjustments)
							haveModification(container.getFigureFacet(), DiagramChange.MODIFICATIONTYPE_ADJUST);
					}
				});
				
			if (needToOffset)
			{
				// use the moving figures construct to offset the figures
				MovingFiguresGem movingGem = new MovingFiguresGem(diagramFacet, new UPoint(0,0));
				MovingFiguresFacet movingFacet = movingGem.getMovingFiguresFacet();
				movingFacet.indicateMovingFigures(addedFigures);
				movingFacet.move(new UPoint(offset));
				movingFacet.end();
			}

			// if we are generating full adjustments
			if (generateFullAdjustments)
			{
				for (FigureFacet figure : addedFigures)
					haveModification(figure, DiagramChange.MODIFICATIONTYPE_ADJUST);
			}
		}
		
		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#makePersistentDiagram()
		 */
		public PersistentDiagram makePersistentDiagram()
		{
			PersistentDiagram pDiagram = new PersistentDiagram(linkedObject, reference.getId(), null, figureId);
			pDiagram.setProperties((PersistentProperties) properties.clone());
			
			// make each figure, and add it
			for (FigureFacet figure : figures.values())
				pDiagram.addFigure(figure.makePersistentFigure());
			
			return pDiagram;
		}

		public Map<String, PersistentFigure> makePersistentFigures(String[] ids, boolean includeChildren)
		{
			Map<String, PersistentFigure> persistentFigures = new HashMap<String, PersistentFigure>();
			for (String id : ids)
			{
				FigureFacet figure = figures.get(id);
				addPersistentFiguresAndChildren(persistentFigures, figure, includeChildren);
			}
			return persistentFigures;
		}

		private void addPersistentFiguresAndChildren(Map<String, PersistentFigure> persistentFigures, FigureFacet figure, boolean includeChildren)
		{
			String id = figure.getId();
			persistentFigures.put(id, figure.makePersistentFigure());
			if (includeChildren && figure.getContainerFacet() != null)
			{
				ContainerFacet asContainer = figure.getContainerFacet();
				for (Iterator iter = asContainer.getContents(); iter.hasNext();)
				{
					FigureFacet child = (FigureFacet) iter.next();
					addPersistentFiguresAndChildren(persistentFigures, child, includeChildren);
				}
			}
		}

    public void resyncViews()
    { 
        changes.clear();
        changes.add(new DiagramChange(null, DiagramChange.MODIFICATIONTYPE_RESYNC));
        sendChangesToListeners();
    }
    

		public void revert()
		{
			// disallow changes to be generated, as we are synching up at the end
			generateChanges = false;

			clear();
			int newFigureId = figureId;
			initialiseInternals();
			figureId = source == null ? newFigureId : CHAINED_UPPER_FIGURE_ID;
			completeInitialisationAfterRegistration();
			// allow changes to be generated again
			generateChanges = true;
			
  		changes.clear();
  	  changes.add(new DiagramChange(null, DiagramChange.MODIFICATIONTYPE_RESYNC));
		}
    
    public void regenerate(PersistentDiagram refreshedPersistentDiagram)
    {
      // disallow changes to be generated, as we are synching up at the end
      generateChanges = false;
      figures = new HashMap<String, FigureFacet>();
      modified = true;

      persistentDiagram = refreshedPersistentDiagram;
      figureId = source == null ? persistentDiagram.getLastFigureId() : CHAINED_UPPER_FIGURE_ID;
      addPersistentFigures(persistentDiagram.getFigures(), null, false);
      
      // allow changes to be generated again
      generateChanges = true;
      
      changes.clear();
      changes.add(new DiagramChange(null, DiagramChange.MODIFICATIONTYPE_RESYNC));
    }

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#getOpeningTime()
		 */
		public long getOpeningTime()
		{
			return openingTime;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#isClipboard()
		 */
		public boolean isClipboard()
		{
			return isClipboard;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#addProperty(String, String)
		 */
		public void addProperty(String name, String value)
		{
			properties.add(new PersistentProperty(name, value));
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.DiagramFacet#getProperty(String)
		 */
		public String getProperty(String name)
		{
			return properties.retrieve(name).asString();
		}

    public void resizeEntireDiagram()
    {
      // we need to get all the elements in order from lowest leaf node, up to highest container
      List<FigureFacet> figuresFromTopToBottom = new ArrayList<FigureFacet>();
      for (FigureFacet figure : figures.values())
      {
        if (figure.getContainedFacet() == null || figure.getContainedFacet().getContainer() == null)
          addFigureAndChildren(figuresFromTopToBottom, figure);
      }
      
      // now, resize from the ground up
      Collections.reverse(figuresFromTopToBottom);
      for (FigureFacet figure : figuresFromTopToBottom)
      {
        UBounds recalculatedBounds = figure.getRecalculatedFullBoundsForDiagramResize(true);

        // execute the resizing command
        ResizingFiguresGem gem = new ResizingFiguresGem(null, diagramFacet);
        ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
        facet.markForResizingWithoutContainer(figure);
        facet.setFocusBounds(recalculatedBounds);
        facet.end();
      }
    }
    
    private void addFigureAndChildren(Collection<FigureFacet> figures, FigureFacet figure)
    {
      figures.add(figure);
      if (figure.getContainerFacet() != null)
      {
        for (Iterator iter = figure.getContainerFacet().getContents(); iter.hasNext();)
        {
          FigureFacet childFigure = (FigureFacet) iter.next();
          addFigureAndChildren(figures, childFigure);
        }
      }   
    }

    public Object getLinkedObject()
    {
      return linkedObject;
    }
    
    public void setLinkedObject(Object newLinkedObject)
    {
     linkedObject = newLinkedObject;
    }

    public boolean isReadOnly()
    {
    	if (source != null)
    		return true;
      return readOnlyFacet == null ? false : readOnlyFacet.isReadOnly();
    }

		public Object getPossiblePerspective()
		{
			return perspective;
		}

		public void dispose()
		{
			if (source != null)
			source.removeListener(reference.getId());
			if (postProcessor != null)
				postProcessor.dispose();
		}

		public DiagramFacet getSource()
		{
			return source;
		}

		public List<FigureFacet> locateLefts(double x, String figureName)
		{
			List<FigureFacet> rets = new ArrayList<FigureFacet>();
			UPoint pt = Grid.roundToGrid(new UPoint(x, 0));
			for (FigureFacet f : figures.values())
			{
				if (Grid.roundToGrid(f.getFullBounds().getTopLeftPoint()).getIntX() == pt.getIntX()
						&& (figureName == null || f.getFigureName().equals(figureName)))
					rets.add(f);
			}
			return rets;
		}

		public List<FigureFacet> locateTops(double y, String figureName)
		{
			List<FigureFacet> rets = new ArrayList<FigureFacet>();
			UPoint pt = Grid.roundToGrid(new UPoint(0, y));
			for (FigureFacet f : figures.values())
			{
				if (Grid.roundToGrid(f.getFullBounds().getTopLeftPoint()).getIntY() == pt.getIntY()
						&& (figureName == null || f.getFigureName().equals(figureName)))
				{
					rets.add(f);
				}
			}
			return rets;
		}

		public int getNumberOfChanges()
		{
			return changes.size();
		}
	}
}