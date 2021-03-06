// AUTOMATICALLY GENERATED by FreeHEP JAVAGraphics2D

package org.freehep.graphicsio.java.test;

import java.awt.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.test.*;

public class TestLabels extends TestingPanel
{

  public TestLabels(String[] args) throws Exception
  {
    super(args);
    setName("TestLabels");
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
      vg[2] = (VectorGraphics) vg[1].create();
      vg[2].setColor(new Color(204, 204, 204, 255));
      vg[2].fillRect(0, 0, 600, 600);
      vg[2].dispose();
      vg[3] = (VectorGraphics) vg[1].create();
      vg[4] = (VectorGraphics) vg[3].create(496, 8, 73, 20);
      vg[4].setColor(new Color(0, 0, 0, 255));
      vg[4].setFont(new Font("Dialog", 1, 12));
      vg[4].setColor(new Color(0, 0, 0, 255));
      vg[4].setFont(new Font("Dialog", 1, 12));
      vg[5] = (VectorGraphics) vg[4].create();
      vg[6] = (VectorGraphics) vg[5].create();
      vg[6].setColor(new Color(204, 204, 204, 255));
      vg[6].fillRect(0, 0, 73, 20);
      vg[6].setFont(new Font("Dialog", 1, 12));
      vg[6].setColor(new Color(0, 0, 0, 255));
      vg[6].drawString("TestButton3", 2, 15);
      vg[6].dispose();
      vg[5].translate(0, 0);
      vg[5].setColor(new Color(142, 142, 142, 255));
      vg[5].drawLine(0, 0, 0, 19);
      vg[5].drawLine(1, 0, 72, 0);
      vg[5].setColor(new Color(99, 99, 99, 255));
      vg[5].drawLine(1, 1, 1, 18);
      vg[5].drawLine(2, 1, 71, 1);
      vg[5].setColor(new Color(255, 255, 255, 255));
      vg[5].drawLine(1, 19, 72, 19);
      vg[5].drawLine(72, 1, 72, 18);
      vg[5].setColor(new Color(255, 255, 255, 255));
      vg[5].drawLine(2, 18, 71, 18);
      vg[5].drawLine(71, 2, 71, 17);
      vg[5].translate(0, 0);
      vg[5].setColor(new Color(0, 0, 0, 255));
      vg[5].dispose();
      vg[4].dispose();
      vg[7] = (VectorGraphics) vg[3].create(418, 8, 73, 20);
      vg[7].setColor(new Color(0, 0, 0, 255));
      vg[7].setFont(new Font("Dialog", 1, 12));
      vg[7].setColor(new Color(0, 0, 0, 255));
      vg[7].setFont(new Font("Dialog", 1, 12));
      vg[8] = (VectorGraphics) vg[7].create();
      vg[9] = (VectorGraphics) vg[8].create();
      vg[9].setColor(new Color(204, 204, 204, 255));
      vg[9].fillRect(0, 0, 73, 20);
      vg[9].setFont(new Font("Dialog", 1, 12));
      vg[9].setColor(new Color(0, 0, 0, 255));
      vg[9].drawString("TestButton2", 2, 15);
      vg[9].dispose();
      vg[8].translate(0, 0);
      vg[8].setColor(new Color(142, 142, 142, 255));
      vg[8].drawRect(0, 0, 71, 18);
      vg[8].setColor(new Color(255, 255, 255, 255));
      vg[8].drawLine(1, 17, 1, 1);
      vg[8].drawLine(1, 1, 70, 1);
      vg[8].drawLine(0, 19, 72, 19);
      vg[8].drawLine(72, 19, 72, 0);
      vg[8].translate(0, 0);
      vg[8].dispose();
      vg[7].dispose();
      vg[10] = (VectorGraphics) vg[3].create(310, 5, 103, 26);
      vg[10].setColor(new Color(0, 0, 0, 255));
      vg[10].setFont(new Font("Dialog", 1, 12));
      vg[10].setColor(new Color(0, 0, 0, 255));
      vg[10].setFont(new Font("Dialog", 1, 12));
      vg[11] = (VectorGraphics) vg[10].create();
      vg[12] = (VectorGraphics) vg[11].create();
      vg[12].setColor(new Color(204, 204, 204, 255));
      vg[12].fillRect(0, 0, 103, 26);
      vg[12].setFont(new Font("Dialog", 1, 12));
      vg[12].setColor(new Color(0, 0, 0, 255));
      vg[12].drawString("TestButton1", 17, 18);
      vg[12].dispose();
      vg[11].translate(0, 0);
      vg[11].setColor(new Color(102, 102, 102, 255));
      vg[11].drawRect(0, 0, 101, 24);
      vg[11].setColor(new Color(255, 255, 255, 255));
      vg[11].drawRect(1, 1, 101, 24);
      vg[11].setColor(new Color(204, 204, 204, 255));
      vg[11].drawLine(0, 25, 1, 24);
      vg[11].drawLine(102, 0, 101, 1);
      vg[11].translate(0, 0);
      vg[11].dispose();
      vg[10].dispose();
      vg[13] = (VectorGraphics) vg[3].create(242, 10, 63, 16);
      vg[13].setColor(new Color(0, 0, 0, 255));
      vg[13].setFont(new Font("Dialog", 1, 12));
      vg[13].setColor(new Color(0, 0, 0, 255));
      vg[13].setFont(new Font("Dialog", 1, 12));
      vg[14] = (VectorGraphics) vg[13].create();
      vg[15] = (VectorGraphics) vg[14].create();
      vg[16] = (VectorGraphics) vg[15].create(0, 0, 63, 16);
      vg[16].setColor(new Color(0, 0, 0, 255));
      vg[16].setFont(new Font("Dialog", 1, 12));
      vg[16].setColor(new Color(0, 0, 0, 255));
      vg[16].setFont(new Font("Dialog", 1, 12));
      vg[17] = (VectorGraphics) vg[16].create();
      vg[18] = (VectorGraphics) vg[17].create();
      vg[18].setColor(new Color(0, 0, 0, 255));
      vg[18].drawString("TestLabel4", 0, 13);
      vg[18].dispose();
      vg[17].dispose();
      vg[16].dispose();
      vg[15].dispose();
      vg[14].dispose();
      vg[13].dispose();
      vg[19] = (VectorGraphics) vg[3].create(170, 8, 67, 20);
      vg[19].setColor(new Color(0, 0, 0, 255));
      vg[19].setFont(new Font("Dialog", 1, 12));
      vg[19].setColor(new Color(0, 0, 0, 255));
      vg[19].setFont(new Font("Dialog", 1, 12));
      vg[20] = (VectorGraphics) vg[19].create();
      vg[21] = (VectorGraphics) vg[20].create();
      vg[21].setColor(new Color(0, 0, 0, 255));
      vg[21].drawString("TestLabel3", 2, 15);
      vg[21].dispose();
      vg[20].translate(0, 0);
      vg[20].setColor(new Color(142, 142, 142, 255));
      vg[20].drawLine(0, 0, 0, 19);
      vg[20].drawLine(1, 0, 66, 0);
      vg[20].setColor(new Color(99, 99, 99, 255));
      vg[20].drawLine(1, 1, 1, 18);
      vg[20].drawLine(2, 1, 65, 1);
      vg[20].setColor(new Color(255, 255, 255, 255));
      vg[20].drawLine(1, 19, 66, 19);
      vg[20].drawLine(66, 1, 66, 18);
      vg[20].setColor(new Color(255, 255, 255, 255));
      vg[20].drawLine(2, 18, 65, 18);
      vg[20].drawLine(65, 2, 65, 17);
      vg[20].translate(0, 0);
      vg[20].setColor(new Color(0, 0, 0, 255));
      vg[20].dispose();
      vg[19].dispose();
      vg[22] = (VectorGraphics) vg[3].create(98, 8, 67, 20);
      vg[22].setColor(new Color(0, 0, 0, 255));
      vg[22].setFont(new Font("Dialog", 1, 12));
      vg[22].setColor(new Color(0, 0, 0, 255));
      vg[22].setFont(new Font("Dialog", 1, 12));
      vg[23] = (VectorGraphics) vg[22].create();
      vg[24] = (VectorGraphics) vg[23].create();
      vg[24].setColor(new Color(0, 0, 0, 255));
      vg[24].drawString("TestLabel2", 2, 15);
      vg[24].dispose();
      vg[23].translate(0, 0);
      vg[23].setColor(new Color(142, 142, 142, 255));
      vg[23].drawRect(0, 0, 65, 18);
      vg[23].setColor(new Color(255, 255, 255, 255));
      vg[23].drawLine(1, 17, 1, 1);
      vg[23].drawLine(1, 1, 64, 1);
      vg[23].drawLine(0, 19, 66, 19);
      vg[23].drawLine(66, 19, 66, 0);
      vg[23].translate(0, 0);
      vg[23].dispose();
      vg[22].dispose();
      vg[25] = (VectorGraphics) vg[3].create(30, 10, 63, 16);
      vg[25].setColor(new Color(0, 0, 0, 255));
      vg[25].setFont(new Font("Dialog", 1, 12));
      vg[25].setColor(new Color(0, 0, 0, 255));
      vg[25].setFont(new Font("Dialog", 1, 12));
      vg[26] = (VectorGraphics) vg[25].create();
      vg[27] = (VectorGraphics) vg[26].create();
      vg[27].setColor(new Color(0, 0, 0, 255));
      vg[27].drawString("TestLabel1", 0, 13);
      vg[27].dispose();
      vg[26].dispose();
      vg[25].dispose();
      vg[3].dispose();
      vg[1].dispose();
    } // paint
  } // class Paint0s0

  private VectorGraphics vg[] = new VectorGraphics[28];

  public static void main(String[] args) throws Exception
  {
    new TestLabels(args).runTest(600, 600);
  }
} // class
