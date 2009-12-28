// Copyright 2000, CERN, Geneva, Switzerland and SLAC, Stanford, U.S.A.
package org.freehep.preview;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import org.freehep.graphics2d.*;

/**
 * @author Mark Donszelmann
 * @version $Id: LabelPlacer.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */

public class LabelPlacer extends Component
{

  Vector labels = new Vector(); // of TextLabel
  Random xRandom = new Random();
  Random yRandom = new Random();
  Random locRandom = new Random();
  int border = 50;

  static Label labelCount = new Label("# of Labels: 00");
  static Label conflictCount = new Label("# of Conflicts: 00");
  static Label temperature = new Label("T: 00");
  static Label stageCount = new Label("Stage: 50");

  public LabelPlacer()
  {
    setBackground(Color.black);
  }

  public Dimension getPreferredSize()
  {
    return new Dimension(800, 640);
  }

  public void paint(Graphics g)
  {
    VectorGraphics g1 = VectorGraphics.create(g);
    Rectangle d = getBounds();

    g1.setColor(getBackground());
    g1.fillRect(d.x, d.y, d.width, d.height);
    g1.setColor(getForeground());

    for (Enumeration e = labels.elements(); e.hasMoreElements();)
    {
      TextLabel label = (TextLabel) e.nextElement();

      int hor = label.getAlignmentX();
      int ver = label.getAlignmentY();
      int x = label.getX();
      int y = label.getY();

      if (label.getConflicts() > 0)
      {
        g1.setColor(Color.cyan);
      }
      else
      {
        g1.setColor(Color.white);
      }
      Rectangle r = label.getBounds();
      g1.drawRect(border + r.x, border + r.y, r.width, r.height);
      g1.drawString(label.getText(), border + x, border + y, hor, ver);
      g1.setColor(Color.red);
      g1.fillRect(border + x - 1, border + y - 1, 3, 3);
    }
  }

  private void addLabels(int n)
  {
    Dimension d = getSize();
    while (n > 0)
    {
      labels.addElement(new TextLabel("Point " + labels.size(), new Point(xRandom.nextInt(d.width - 2 * border),
          yRandom.nextInt(d.height - 2 * border)), locRandom.nextInt(8), this));
      n--;
    }
    setConflicts();
    keep();
    labelCount.setText("# of Labels: " + labels.size());
  }

  private void positionLabels(int algorithm)
  {
    switch (algorithm)
    {
      case 1 : { // Random
        int oldConflicts = countConflicts();
        for (int i = 0; i < labels.size(); i++)
        {
          TextLabel ref = (TextLabel) labels.elementAt(i);
          ref.tmpPosition = locRandom.nextInt(8);
        }
        if (setConflicts() < oldConflicts)
        {
          keep();
        }
        break;
      }
      case 2 : { // Greedy: Place labels randomly, then for each conflicting
                  // label, try the next position
        // FIXME: we should start from what is visible on screen
        int oldConflicts = countConflicts();
        for (int i = 0; i < labels.size(); i++)
        {
          TextLabel ref = (TextLabel) labels.elementAt(i);
          if (ref.tmpConflicts > 0)
          {
            int oldPosition = ref.tmpPosition;
            int[] pos = generateSequence(oldPosition);
            int posIndex = 0;
            while ((posIndex < 7) && (ref.tmpConflicts > 0))
            {
              int newPosition = pos[posIndex];
              posIndex++;
              moveLabel(i, newPosition);
            }
          }
        }
        if (setConflicts() < oldConflicts)
        {
          keep();
        }
        break;
      }
      case 3 :
        Random labelPicker = new Random();
        Random undo = new Random();
        Random posPicker = new Random();
        int E = setConflicts();
        int lowestE = E;
        double T = -1.0 / Math.log(1.0 - (2.0 / 3.0));
        temperature.setText("T: " + (int) (T * 100));
        int moved = 0;
        int maxMoved = labels.size() * 20;
        int success = 0;
        int maxSuccess = labels.size() * 5;
        int stages = 50;
        stageCount.setText("Stage: " + stages);
        while ((E > 0) && (stages > 0))
        {
          if ((moved > maxMoved) || (success > maxSuccess))
          {
            if (success == 0)
            {
              System.out.println("No successes at all at tis temp");
              break;
            }
            moved = 0;
            success = 0;
            // decrease temperature
            T = T * 0.9;
            temperature.setText("T: " + (int) (T * 100));

            stages--;
            stageCount.setText("Stage: " + stages);
          }

          // pick a label and move it to a new position
          int index = labelPicker.nextInt(labels.size());
          TextLabel label = (TextLabel) labels.elementAt(index);
          int newPosition = posPicker.nextInt(8);
          if (newPosition >= label.tmpPosition)
          {
            newPosition = (newPosition + 1) % 8;
          }

          int dE = getDeltaE(index, newPosition);
          double P = 1.0;
          // undo if worse with probability 1.0-e^-dE/T
          if (dE > 0)
          {
            P = 1.0 - Math.exp(-dE / T);
            if (undo.nextDouble() > P)
            {
              // do a worse move
              moveLabel(index, newPosition);
              // increase energy
              E = E + dE;
            }
          }
          else
          {
            moveLabel(index, newPosition);
            success++;
            // decrease energy
            E = E + dE;
          }

          moved++;
          if (E < lowestE)
          {
            System.out.println("S=" + stages + ", T=" + T + ", E=" + E + ", dE=" + dE + ", P=" + P + ", m=" + moved
                + ", s=" + success);
            lowestE = E;
            System.out.println(keep());
            repaint();
            try
            {
              Thread.sleep(500);
            }
            catch (InterruptedException ie)
            {
            }
          }
        }
        break;
      default :
        System.out.println("Algorithm#: " + algorithm + " not implemented");
        break;
    }
  }

  private int setConflicts()
  {
    int conflicts = 0;
    for (int i = 0; i < labels.size(); i++)
    {
      TextLabel ref = (TextLabel) labels.elementAt(i);
      ref.tmpConflicts = 0;
      Rectangle refRect = ref.getBounds(ref.tmpPosition);
      for (int j = 0; j < labels.size(); j++)
      {
        if (i != j)
        {
          TextLabel label = (TextLabel) labels.elementAt(j);
          Rectangle labelRect = label.getBounds(label.tmpPosition);
          if (labelRect.intersects(refRect))
          {
            ref.tmpConflicts++;
            conflicts++;
          }
        }
      }
    }
    return conflicts;
  }

  private int countConflicts()
  {
    int conflicts = 0;
    for (int i = 0; i < labels.size(); i++)
    {
      TextLabel ref = (TextLabel) labels.elementAt(i);
      conflicts += ref.getConflicts();
    }
    return conflicts;
  }

  private int keep()
  {
    int conflicts = 0;
    for (int i = 0; i < labels.size(); i++)
    {
      TextLabel ref = (TextLabel) labels.elementAt(i);
      conflicts += ref.keep();
    }
    conflictCount.setText("# of Conflicts: " + conflicts / 2);
    return conflicts;
  }

  private int getDeltaE(int index, int newPosition)
  {
    int oldConflicts = 0;
    int newConflicts = 0;
    TextLabel ref = (TextLabel) labels.elementAt(index);
    Rectangle oldRect = ref.getBounds(ref.tmpPosition);
    Rectangle newRect = ref.getBounds(newPosition);
    for (int i = 0; i < labels.size(); i++)
    {
      if (i != index)
      {
        TextLabel label = (TextLabel) labels.elementAt(i);
        Rectangle labelRect = label.getBounds(label.tmpPosition);
        boolean oldIntersects = labelRect.intersects(oldRect);
        boolean newIntersects = labelRect.intersects(newRect);
        if (oldIntersects)
        {
          // we had a conflict
          oldConflicts += 2;
          if (newIntersects)
          {
            // we still have one
            newConflicts += 2;
          }
        }
        else
        {
          // we had no conflict
          if (newIntersects)
          {
            // but we created one
            newConflicts += 2;
          }
        }
      }
    }
    return newConflicts - oldConflicts;
  }


  private void moveLabel(int index, int newPosition)
  {
    TextLabel ref = (TextLabel) labels.elementAt(index);
    Rectangle oldRect = ref.getBounds(ref.tmpPosition);
    ref.tmpPosition = newPosition;
    Rectangle newRect = ref.getBounds(ref.tmpPosition);
    for (int i = 0; i < labels.size(); i++)
    {
      if (i != index)
      {
        TextLabel label = (TextLabel) labels.elementAt(i);
        Rectangle labelRect = label.getBounds(label.tmpPosition);
        boolean oldIntersects = labelRect.intersects(oldRect);
        boolean newIntersects = labelRect.intersects(newRect);
        if (oldIntersects)
        {
          // we had a conflict
          if (!newIntersects)
          {
            // we got rid of it
            if (ref.tmpConflicts > 0)
            {
              ref.tmpConflicts--;
            }
            else
            {
              System.out.println("Old conflict was not counted in ref: " + index);
            }
            if (label.tmpConflicts > 0)
            {
              label.tmpConflicts--;
            }
            else
            {
              System.out.println("Old conflict was not counted in label: " + i);
            }
          }
        }
        else
        {
          // we had no conflict
          if (newIntersects)
          {
            // but we created one
            ref.tmpConflicts++;
            label.tmpConflicts++;
          }
        }
      }
    }
  }

  private static int[] generateSequence(int avoidPosition)
  {
    Random sequencer = new Random();
    boolean done[] = new boolean[8];
    done[avoidPosition] = true;
    int[] seq = new int[7];

    for (int i = 0; i < 7; i++)
    {
      int index = sequencer.nextInt(7 - i) + 1;
      // System.out.print("("+index+")");
      int r = 0;
      int j = -1;
      while ((j < 7) && (r < index))
      {
        j++;
        if (!done[j])
          r++;
      }
      seq[i] = j;
      // System.out.print(j+", ");
      done[seq[i]] = true;
    }
    // System.out.println("["+avoidPosition+"]");
    return seq;
  }

  public static void main(String[] args)
  {
    Frame frame = new Frame();
    Container content = frame;
    content.setLayout(new BorderLayout());
    final LabelPlacer lp;
    content.add(lp = new LabelPlacer(), "Center");

    Panel p = new Panel();
    content.add(p, "South");

    p.add(labelCount);
    p.add(conflictCount);

    Button b;
    p.add(b = new Button("Add 10 more labels"));
    b.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        lp.addLabels(10);
        lp.repaint();
      }
    });

    p.add(b = new Button("Randomize Positions"));
    b.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        lp.positionLabels(1);
        lp.repaint();
      }
    });

    p.add(b = new Button("Greedy Positions"));
    b.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        lp.positionLabels(2);
        lp.repaint();
      }
    });

    p.add(b = new Button("Simulated Annealing Positions"));
    b.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Thread t = new Thread()
        {
          public void run()
          {
            lp.positionLabels(3);
            lp.repaint();
          }
        };
        t.start();
      }
    });

    p.add(stageCount);
    p.add(temperature);

    frame.pack();
    frame.show();

    // update now that font is there.
    lp.addLabels(100);
    lp.repaint();
  }

}



class TextLabel
{

  // calculating state (visible only in package)
  int tmpPosition;
  int tmpConflicts;

  // best state
  private int position;
  private int conflicts;

  // general state
  private String text;
  private Point location;
  private Component component;

  public TextLabel(String text, Point location, int position, Component component)
  {
    this.text = text;
    this.location = location;
    this.tmpPosition = position;
    this.component = component;
    this.tmpConflicts = 0;
    keep();
  }

  public String getText()
  {
    return text;
  }

  public int getConflicts()
  {
    return conflicts;
  }

  int keep()
  {
    position = tmpPosition;
    conflicts = tmpConflicts;
    return conflicts;
  }

  Rectangle getBounds(int position)
  {
    Font font = component.getFont();
    int width = 2;
    int height = 2;
    if (font != null)
    {
      FontMetrics fm = component.getFontMetrics(font);
      height = fm.getHeight();
      width = fm.stringWidth(text);
    }
    return new Rectangle(location.x + (int) (getTx(position) * width), location.y + (int) (getTy(position) * height),
        width, height);
  }

  public Rectangle getBounds()
  {
    return getBounds(position);
  }

  public int getX()
  {
    return location.x;
  }

  public int getY()
  {
    return location.y;
  }

  public int getAlignmentX()
  {
    return getHor(position);
  }

  private static int getHor(int position)
  {
    switch (position)
    {
      default :// should not occur, default to 0
        throw new RuntimeException("Location not allowed");
      case 0 : // top-right
        return VectorGraphicsConstants.TEXT_LEFT;
      case 1 : // top-left
        return VectorGraphicsConstants.TEXT_RIGHT;
      case 2 : // bottom-right
        return VectorGraphicsConstants.TEXT_LEFT;
      case 3 : // bottom-left
        return VectorGraphicsConstants.TEXT_RIGHT;
      case 4 : // center-right
        return VectorGraphicsConstants.TEXT_LEFT;
      case 5 : // top-center
        return VectorGraphicsConstants.TEXT_CENTER;
      case 6 : // center-left
        return VectorGraphicsConstants.TEXT_RIGHT;
      case 7 : // bottom-center
        return VectorGraphicsConstants.TEXT_CENTER;
    }
  }

  public int getAlignmentY()
  {
    return getVer(position);
  }

  private static int getVer(int position)
  {
    switch (position)
    {
      default :// should not occur, default to 0
        throw new RuntimeException("Location not allowed");
      case 0 : // top-right
        return VectorGraphicsConstants.TEXT_BOTTOM;
      case 1 : // top-left
        return VectorGraphicsConstants.TEXT_BOTTOM;
      case 2 : // bottom-right
        return VectorGraphicsConstants.TEXT_TOP;
      case 3 : // bottom-left
        return VectorGraphicsConstants.TEXT_TOP;
      case 4 : // center-right
        return VectorGraphicsConstants.TEXT_CENTER;
      case 5 : // top-center
        return VectorGraphicsConstants.TEXT_BOTTOM;
      case 6 : // center-left
        return VectorGraphicsConstants.TEXT_CENTER;
      case 7 : // bottom-center
        return VectorGraphicsConstants.TEXT_TOP;
    }
  }

  static double getTx(int position)
  {
    switch (position)
    {
      default :// should not occur, default to 0
        throw new RuntimeException("Location not allowed");
      case 0 : // top-right
        return 0;
      case 1 : // top-left
        return -1;
      case 2 : // bottom-right
        return 0;
      case 3 : // bottom-left
        return -1;
      case 4 : // center-right
        return 0;
      case 5 : // top-center
        return -0.5;
      case 6 : // center-left
        return -1;
      case 7 : // bottom-center
        return -0.5;
    }
  }

  static double getTy(int position)
  {
    switch (position)
    {
      default :// should not occur, default to 0
        throw new RuntimeException("Location not allowed");
      case 0 : // top-right
        return -1;
      case 1 : // top-left
        return -1;
      case 2 : // bottom-right
        return 0;
      case 3 : // bottom-left
        return 0;
      case 4 : // center-right
        return -0.5;
      case 5 : // top-center
        return -1;
      case 6 : // center-left
        return -0.5;
      case 7 : // bottom-center
        return 0;
    }

  }
}