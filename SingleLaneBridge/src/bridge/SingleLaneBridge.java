package bridge;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;

public class SingleLaneBridge extends Panel {

    BridgeCanvas display;
    Button restart;
    Button freeze;
    int maxCar = 1;

    public BridgeCanvas get() {return display;}
    
    public void init(int max) {
        setLayout(new BorderLayout());
        display = new BridgeCanvas(max);
        add("Center",display);
        restart = new Button("Start");
        restart.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             display.thaw();
           }
        });
		
        freeze = new Button("Stop");
        freeze.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             display.freeze();
           }
        });
		
        		
        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());
        p1.add(restart);
        p1.add(freeze);
        add("South",p1);
        setBackground(Color.lightGray);
   }

    
 }
