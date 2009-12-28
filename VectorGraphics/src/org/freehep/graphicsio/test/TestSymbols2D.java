// University of California, Santa Cruz, USA and
// CERN, Geneva, Switzerland, Copyright (c) 2000
package org.freehep.graphicsio.test;

import java.awt.*;

import org.freehep.graphics2d.*;

/**
 * @author Charles Loomis
 * @author Mark Donszelmann
 * @version $Id: TestSymbols2D.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestSymbols2D extends TestingPanel implements VectorGraphicsConstants
{

  public TestSymbols2D(String[] args) throws Exception
  {
    super(args);
    setName("Symbols");
  }

  public void paintComponent(Graphics g)
  {

    if (g != null)
    {
      VectorGraphics vg = VectorGraphics.create(g);

      Dimension dim = getSize();
      Insets insets = getInsets();

      vg.setColor(Color.red);
      vg.fillRect(insets.left, insets.top, dim.width - insets.left - insets.right, dim.height - insets.top
          - insets.bottom);

      int dw = dim.width / 5;
      int dh = dim.height / 6;

      int size = Math.min(dw, dh) * 2 / 3;

      vg.setColor(Color.black);
      int symbol = 0;
      for (int y = 0; y < 6; y++)
      {
        symbol = (y / 2) * 5;
        if ((y % 2) == 0)
        {
          vg.setLineWidth(1);
        }
        else
        {
          vg.setLineWidth(5.0);
        }
        int iy = dh / 2 + dh * y;
        if (y >= 2)
          symbol = SYMBOL_CIRCLE;
        for (int x = 0; x < 5; x++)
        {
          int ix = dw / 2 + dw * x;
          if (y < 4)
          {
            vg.drawSymbol(ix, iy, size, symbol++);
          }
          else
          {
            vg.fillSymbol(ix, iy, size, symbol++);
          }
        }
      }
    }
  }

  public static void main(String[] args) throws Exception
  {
    new TestSymbols2D(args).runTest();
  }
}
