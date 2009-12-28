package com.hopstepjump.wmf;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import com.hopstepjump.geometry.*;

/**
 * @author andrew
 */
class Segment
{
  /** one of the PathIterator.SEG_ types */
	private int type;
	/** maximum of 3 points */
	public UPoint points[] = new UPoint[3];
	/** the maximum distance for a flattened curve to deviate from the curve */
  private static final double flattenDistance = 0;
	
	public Segment(int type)
	{
		this.type = type;
	}

  /**
   * @return
   */
  public void expandInto(UPoint current, Collection<Segment> segments)
  {
    switch (type)
    {
			case PathIterator.SEG_QUADTO:
			  segments.addAll(expandQuadratic(current));
				break;

			case PathIterator.SEG_CUBICTO:
			  segments.addAll(expandCubic(current));
				break;
			
			// lines just expand into themselves
			default:
			  segments.add(this);
			  break;
    }
    
  }

  /**
   * @return
   */
  private Collection<Segment> expandCubic(UPoint current)
  {
    List<Segment> segments = new ArrayList<Segment>();

    // flatten and add the segments
    CubicCurve2D curve = new CubicCurve2D.Double(
        current.getX(), current.getY(),
        points[0].getX(), points[0].getY(),
        points[1].getX(), points[1].getY(),
        points[2].getX(), points[2].getY());
    flattenInto(segments, curve, flattenDistance);
    
    return segments;
  }

  /**
   * @return
   */
  private Collection<Segment> expandQuadratic(UPoint current)
  {
//	segment.points[0] = rotateAccordingToTransform(firstPoint, point);
//	segment.points[1] = rotateAccordingToTransform(firstPoint, new UPoint(coords[2], coords[3]));
    List<Segment> segments = new ArrayList<Segment>();

    // flatten and add the segments
    QuadCurve2D curve = new QuadCurve2D.Double(
        current.getX(), current.getY(),
        points[0].getX(), points[0].getY(),
        points[1].getX(), points[1].getY());
    flattenInto(segments, curve, flattenDistance);

    return segments;
  }

  /**
   * @param segments
   * @param curve
   * @param i
   */
  private void flattenInto(List<Segment> segments, Shape curve, double flatness)
  {
		for (PathIterator iter = curve.getPathIterator(null, flatness);
						!iter.isDone();
							iter.next())
		{
			float coords[] = new float[6];
			int type = iter.currentSegment(coords);
			
			if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO)
			{
				// make up the segment and add it to the list
				Segment segment = new Segment(type);
				UPoint point = new UPoint(coords[0], coords[1]);
				segment.points[0] = point;
				segments.add(segment);
			}
		}    
  }

  /**
   * @return
   */
  public UPoint getLastPoint()
  {
    // get the last point
    for (int lp = 0; lp < points.length; lp++)
      if (points[lp+1] == null)
        return points[lp];
    return null;
  }
  
  
/*
  private Collection<Segment> expandCubic()
  {
    List<Segment> segments = new ArrayList<Segment>();
    Segment segment1 = new Segment(PathIterator.SEG_MOVETO);
    segment1.points[0] = points[0];
    segments.add(segment1);
    Segment segment2 = new Segment(PathIterator.SEG_LINETO);
    segment2.points[0] = points[1];
    segments.add(segment2);
    Segment segment3 = new Segment(PathIterator.SEG_LINETO);
    segment3.points[0] = points[2];
    segments.add(segment3);
    
    return segments;
  }

  private Collection<Segment> expandQuadratic()
  {
    List<Segment> segments = new ArrayList<Segment>();

    Segment segment1 = new Segment(PathIterator.SEG_MOVETO);
    segment1.points[0] = points[0];
    segments.add(segment1);
    Segment segment2 = new Segment(PathIterator.SEG_LINETO);
    segment2.points[0] = points[1];
    segments.add(segment2);

    return segments;
  }
  */
}

