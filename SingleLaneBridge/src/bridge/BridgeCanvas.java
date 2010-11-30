package bridge;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.applet.*;

class BridgeCanvas extends Canvas {


    Image  redCar;
    Image  blueCar;
    Image  bridge;


    int[] redX,redY,blueX,blueY;

    int maxCar = 2;

    final static int initredX  = 5;
    final static int initredY  = 55;
    final static int initblueX = 410;
    final static int initblueY = 130;
    final static int bridgeY   = 90;

    boolean frozen = false;
    int cycleTime = 20;
    
    AudioClip crashSound;

    BridgeCanvas(int max) {
        super();
        setLocation(100, 100);
        crashSound=java.applet.Applet.newAudioClip(this.getClass().getResource("sound/crash.au"));
        MediaTracker mt;
        mt = new MediaTracker(this);
        redCar = getImage(this.getClass().getResource("image/redcar.gif"));
        mt.addImage(redCar, 0);
        blueCar = getImage(this.getClass().getResource("image/bluecar.gif"));
        mt.addImage(blueCar, 1);
        bridge =  getImage(this.getClass().getResource("image/bridge.gif") );
        mt.addImage(bridge, 2);

        try {
            mt.waitForID(0);
            mt.waitForID(1);
            mt.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }
        setSize(bridge.getWidth(null),bridge.getHeight(null));
        init(max);
    }

    public void init(int ncars) { //set number of cars
        maxCar = ncars;
        frozen = false;
        redX  = new int[maxCar];
        redY  = new int[maxCar];
        blueX = new int[maxCar];
        blueY = new int[maxCar];

        for (int i = 0; i<maxCar ; i++) {
            redX[i] = initredX - i*85;
            redY[i] = initredY;
            blueX[i] =initblueX + i*85;
            blueY[i] =initblueY;
        }
        repaint();
        freeze();
    }

    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;

    public void backdrop() {
        Dimension d = getSize();
	    if ((offscreen == null) || (d.width != offscreensize.width)
	                            || (d.height != offscreensize.height)) {
	        offscreen = createImage(d.width, d.height);
	        offscreensize = d;
	        offgraphics = offscreen.getGraphics();
	        offgraphics.setFont(new Font("Helvetica",Font.BOLD,36));
	    }
        offgraphics.setColor(Color.lightGray);
        offgraphics.drawImage(bridge,0,0,this);
    }

    public void paint(Graphics g) {
         update(g);
    }

    public void update(Graphics g) {
        backdrop();
        for (int i=0; i<maxCar; i++) {
            offgraphics.drawImage(redCar,redX[i],redY[i],this);
            offgraphics.drawImage(blueCar,blueX[i],blueY[i],this);
        }
        if (blueY[0]==redY[0] && Math.abs(redX[0]+80 - blueX[0])<5) {
            offgraphics.setColor(Color.red);
            offgraphics.drawString("Crunch!",200,100);
            frozen=true;
            crashSound.play();
        }
        g.drawImage(offscreen, 0, 0, null);
    }

    //returns true for the period from just before until just after car on bridge
    public  boolean moveRed(int i) throws InterruptedException{
        int X = redX[i];
        int Y = redY[i];
        synchronized (this) {
            while (frozen ) wait();
            if (i==0 || Math.abs(redX[i-1] - X) > 120) {
                X += 2;
                if (X >=500) { X = -80; Y = initredY;}
                if (X >=60 && X < 290 && Y<bridgeY) ++Y;
                if (X >=290 && Y>initredY) --Y;
            }
            redX[i]=X;
            redY[i]=Y;
            repaint();
        }
        Thread.sleep(cycleTime);
        return (X>25 && X<400);
     }

    //returns true for the period from just before until just after car on bridge
    public  boolean moveBlue(int i) throws InterruptedException{
        int X = blueX[i];
        int Y = blueY[i];
        synchronized (this) {
            while (frozen ) wait();
            if (i==0 || Math.abs(blueX[i-1] - X) > 120) {
                X -= 2;
                if (X <=-80) { X = 500; Y = initblueY;}
                if (X <=370 && X > 130 && Y>bridgeY) --Y;
                if (X <=130 && Y<initblueY) ++Y;
                blueX[i]=X;
            }
            blueY[i]=Y;
            repaint();
        }
        Thread.sleep(cycleTime);
        repaint();
        return (X>25 && X<400);
    }


    public synchronized void freeze(){
        frozen = true;
    }

    public synchronized void thaw() {
        frozen = false;
        notifyAll();
    }
    
    Image getImage(URL url) {
      try {
        java.awt.image.ImageProducer prod = (java.awt.image.ImageProducer)url.getContent();
	    return Toolkit.getDefaultToolkit().createImage(prod);
	  } catch (java.io.IOException e) {
	    System.out.println("Image not found at:"+url);
	  }
	  return null;
    }


}
