/*
 * Created on Dec 28, 2003 by Andrew McVeigh
 */
package edu.umd.cs.jazz.component;

import java.awt.geom.*;

/**
 * @author Andrew
 */
public class ZQuadCoordListShape extends ZCoordListShape
{
	public PathIterator getPathIterator(AffineTransform at)
	{
		// Bug fix, an empty list that is closed throws exception.
		return new ZQuadCoordListIterator(this, at, isClosed && getNumberPoints() > 0);
	}

	/**
	 * This is the path iterator used to iterate over the ZCoordList. If the ZCoordList
	 * is marked as closed then this iterator will return a PathIterator.SEG_CLOSE as its
	 * last segment.
	 */
	protected static class ZQuadCoordListIterator implements PathIterator
	{

		private ZCoordListShape coordList;
		private AffineTransform transform;
		private int index = 0;
		private boolean isClosed;

		public ZQuadCoordListIterator(ZCoordListShape pl, AffineTransform at, boolean isClosed)
		{
			transform = at;
			index = 0;
			coordList = pl;
			this.isClosed = isClosed;
		}

		public int currentSegment(double coords[])
		{
			int result = -1;

			if (index >= coordList.getVertexCount())
			{
				return PathIterator.SEG_CLOSE;
			}
			else
			{
				coords[0] = coordList.getX(index);
				coords[1] = coordList.getY(index);
				if (index == 0)
				{
					result = PathIterator.SEG_MOVETO;
				}
				else
				{
					result = PathIterator.SEG_QUADTO;
					coords[2] = coordList.getX(index + 1);
					coords[3] = coordList.getY(index + 1);
				}

				if (transform != null)
				{
					if (result == PathIterator.SEG_MOVETO)
						transform.transform(coords, 0, coords, 0, 1);
					else
						transform.transform(coords, 0, coords, 0, 2);
				}
			}

			return result;
		}

		public int currentSegment(float coords[])
		{
			int result = -1;

			if (index >= coordList.getVertexCount())
			{
				return PathIterator.SEG_CLOSE;
			}
			else
			{
				coords[0] = (float) coordList.getX(index);
				coords[1] = (float) coordList.getY(index);
				if (index == 0)
				{
					result = PathIterator.SEG_MOVETO;
				}
				else
				{
					result = PathIterator.SEG_QUADTO;
					coords[2] = (float) coordList.getX(index + 1);
					coords[3] = (float) coordList.getY(index + 1);
				}

				if (transform != null)
				{
					if (result == PathIterator.SEG_MOVETO)
						transform.transform(coords, 0, coords, 0, 1);
					else
						transform.transform(coords, 0, coords, 0, 2);
				}
			}

			return result;
		}

		public int getWindingRule()
		{
			return coordList.getWindingRule();
		}

		public boolean isDone()
		{
			if (isClosed)
			{
				return index > coordList.getVertexCount();
			}
			else
			{
				return (index >= coordList.getVertexCount());
			}
		}

		public void next()
		{
			if (index == 0)
				index++;
			else
				index += 2; // advance past the control point and the end point
		}
	}
}
