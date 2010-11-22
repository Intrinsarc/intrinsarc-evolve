package bridge;
import java.awt.*;
import java.awt.event.*;

class Viewer extends Frame {

    SingleLaneBridge applet = new SingleLaneBridge();
    
    SingleLaneBridge get() { return applet;}
    
    int maxCar =1;

    public Viewer(int max) {
        maxCar = max;
         create();
		addWindowListener(new WindowAdapter()  {
			public void windowClosing(WindowEvent e)  {
			  System.exit(0);
			}
		});
    }

    void create() {
        setSize(600,400);
        add("Center",applet);
        applet.init(maxCar);
        pack();
        setVisible(true);
    }

    
	


  public static void main (String args[]) {
    Frame frame = new Viewer(1);
  }

}

