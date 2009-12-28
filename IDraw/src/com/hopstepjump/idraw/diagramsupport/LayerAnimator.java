package com.hopstepjump.idraw.diagramsupport;

import com.hopstepjump.geometry.*;

import edu.umd.cs.jazz.*;

/**
 * @author andrew
 */
public class LayerAnimator
{
  private static final double ALPHA_FLOOR = 0.2;
  private static final double ALPHA_CEILING = 0.8;
  private static final double ALPHA_RANGE = ALPHA_CEILING - ALPHA_FLOOR;

  private ZTransformGroup scaler;
  private ZFadeGroup fader;
  private ZGroup layer;
  private UBounds diagramSection;
  private UBounds startBounds;
  private UPoint actualStartTopLeftPoint;
  private UBounds endBounds;
  private boolean becomeLessVisible;
  private boolean nonLinear;

  /**
   * @param oldScaler
   * @param oldFader
   * @param openRegionHint
   * @param openRegionHint2
   * @param bounds
   */
  public LayerAnimator(
      ZGroup layer,
      UBounds diagramSection,
      UBounds startBounds,
      UBounds endBounds,
      boolean becomeLessVisible,
      boolean nonLinear)
  {
    fader = new ZFadeGroup(layer);
    scaler = new ZTransformGroup(fader);
    this.layer = layer;
    this.diagramSection = limit(diagramSection);
    this.startBounds = limit(startBounds);
    actualStartTopLeftPoint = startBounds.getTopLeftPoint();
    this.endBounds = limit(endBounds);
    this.becomeLessVisible = becomeLessVisible;
    this.nonLinear = nonLinear;
    
    // possibly make invisible now
    moveToCycle(0);
  }
  
  
  
  /**
   * @param diagramSection2
   * @return
   */
  private UBounds limit(UBounds bounds)
  {
    return new UBounds(bounds.getTopLeftPoint(), bounds.getDimension().maxOfEach(new UDimension(8, 8)));
  }



  public void moveToCycle(float fraction)
  {
    if (becomeLessVisible)
    {
      if (fraction > 0.7)
        fader.setAlpha(0);
      else
        fader.setAlpha(Math.min(1, (1 - fraction) * ALPHA_RANGE + ALPHA_FLOOR));
    }
    else
      fader.setAlpha(1);

    // set the scaling and the translation:
    // 1. work out how what the bounds should be
    UPoint startTopLeft = startBounds.getTopLeftPoint();
    UPoint startBottomRight = startBounds.getBottomRightPoint();
    UDimension topLeftOffset =
      startBounds.getTopLeftPoint().subtract(endBounds.getTopLeftPoint());
    UDimension bottomRightOffset =
      endBounds.getBottomRightPoint().subtract(startBounds.getBottomRightPoint());
    UPoint topLeft = startTopLeft.subtract(topLeftOffset.multiply(fraction));
    UPoint bottomRight = startBottomRight.add(bottomRightOffset.multiply(fraction));
    UBounds wantedBounds = new UBounds(topLeft, bottomRight);
    
    // 2. work out what the scale should be
    // NOTE: if the scale is greater for the vertical, use this instead
    double horizontalScale = wantedBounds.getWidth() / diagramSection.getWidth();
    double verticalScale = wantedBounds.getHeight() / diagramSection.getHeight();
    double scale = Math.min(horizontalScale, verticalScale);
    scaler.setScale(scale);

    // 3. work out what the translation should be
    UDimension offset = null;
    
    // if this is linear, we scale differently 
    if (!nonLinear)
    {
      offset = wantedBounds.getTopLeftPoint().
      	subtract(
      	    new UPoint(
      	        diagramSection.getTopLeftPoint().getX() * scale,
      	        diagramSection.getTopLeftPoint().getY() * scale));
    }
    else
    {
      offset = wantedBounds.getTopLeftPoint().
    	subtract(
    	    new UPoint(
    	        diagramSection.getTopLeftPoint().getX(),
    	        diagramSection.getTopLeftPoint().getY())).multiply(scale);      
    }
    
    scaler.setTranslation(offset.getWidth(), offset.getHeight());
  }

  /**
   * @param pickLayer
   */
  public void remove(ZLayerGroup pickLayer)
  {
    // remove from the pick layer
    pickLayer.removeChild(scaler);
  }

  /**
   * @param pickLayer
   */
  public void cement(ZLayerGroup pickLayer)
  {
    remove(pickLayer);
    pickLayer.addChild(layer);
    layer.lower();
  }

  /**
   * @param pickLayer
   */
  public void add(ZLayerGroup pickLayer)
  {
    pickLayer.addChild(scaler);
  }
}
