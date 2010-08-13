package com.intrinsarc.idraw.diagramsupport.moveandresize;

import java.util.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public interface MovingFiguresFacet extends PreviewCacheFacet
{
  public boolean indicateMovingFigures(Collection<FigureFacet> movingFigures);
  public ContainerFacet getContainerAllAreMovingFrom();
  public Iterator getSelectedPreviewFigures();
  public Iterator getTopLevelFigures();
  public void move(UPoint current);
  public void start(FigureFacet dragging);
  public boolean contentsCanMoveContainers();
}
