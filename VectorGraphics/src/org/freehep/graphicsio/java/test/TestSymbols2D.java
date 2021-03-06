// AUTOMATICALLY GENERATED by FreeHEP JAVAGraphics2D

package org.freehep.graphicsio.java.test;

import java.awt.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.test.*;

public class TestSymbols2D extends TestingPanel
{

  public TestSymbols2D(String[] args) throws Exception
  {
    super(args);
    setName("TestSymbols2D");
  } // contructor

  public void paint(Graphics g)
  {
    vg[0] = VectorGraphics.create(g);
    vg[0].setCreator("FreeHEP JAVAGraphics2D");
    Paint0s0.paint(vg);
  } // paint

  private static class Paint0s0
  {
    public static void paint(VectorGraphics[] vg)
    {
      vg[0].setColor(new Color(0, 0, 0, 255));
      vg[0].setFont(new Font("Dialog", 0, 12));
      vg[1] = (VectorGraphics) vg[0].create();
      vg[1].setClip(0, 0, 600, 600);
      vg[1].setColor(new Color(255, 0, 0, 255));
      vg[1].fillRect(0, 0, 600, 600);
      vg[1].setColor(new Color(0, 0, 0, 255));
      vg[1].setLineWidth(1);
      vg[1].drawSymbol(60, 50, 66, 0);
      vg[1].drawSymbol(180, 50, 66, 1);
      vg[1].drawSymbol(300, 50, 66, 2);
      vg[1].drawSymbol(420, 50, 66, 3);
      vg[1].drawSymbol(540, 50, 66, 4);
      vg[1].setLineWidth(5.0);
      vg[1].drawSymbol(60, 150, 66, 0);
      vg[1].drawSymbol(180, 150, 66, 1);
      vg[1].drawSymbol(300, 150, 66, 2);
      vg[1].drawSymbol(420, 150, 66, 3);
      vg[1].drawSymbol(540, 150, 66, 4);
      vg[1].setLineWidth(1);
      vg[1].drawSymbol(60, 250, 66, 5);
      vg[1].drawSymbol(180, 250, 66, 6);
      vg[1].drawSymbol(300, 250, 66, 7);
      vg[1].drawSymbol(420, 250, 66, 8);
      vg[1].drawSymbol(540, 250, 66, 9);
      vg[1].setLineWidth(5.0);
      vg[1].drawSymbol(60, 350, 66, 5);
      vg[1].drawSymbol(180, 350, 66, 6);
      vg[1].drawSymbol(300, 350, 66, 7);
      vg[1].drawSymbol(420, 350, 66, 8);
      vg[1].drawSymbol(540, 350, 66, 9);
      vg[1].setLineWidth(1);
      vg[1].fillSymbol(60, 450, 66, 5);
      vg[1].fillSymbol(180, 450, 66, 6);
      vg[1].fillSymbol(300, 450, 66, 7);
      vg[1].fillSymbol(420, 450, 66, 8);
      vg[1].fillSymbol(540, 450, 66, 9);
      vg[1].setLineWidth(5.0);
      vg[1].fillSymbol(60, 550, 66, 5);
      vg[1].fillSymbol(180, 550, 66, 6);
      vg[1].fillSymbol(300, 550, 66, 7);
      vg[1].fillSymbol(420, 550, 66, 8);
      vg[1].fillSymbol(540, 550, 66, 9);
      vg[1].dispose();
    } // paint
  } // class Paint0s0

  private VectorGraphics vg[] = new VectorGraphics[2];

  public static void main(String[] args) throws Exception
  {
    new TestSymbols2D(args).runTest(600, 600);
  }
} // class
