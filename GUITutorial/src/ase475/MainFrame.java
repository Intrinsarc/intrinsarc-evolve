package ase475;

import java.awt.*;

import javax.swing.*;

import com.intrinsarc.backbone.runtime.api.*;

public class MainFrame
// start generated code
	// main port
 implements IRun
{
	// attributes
	private String title;
	private int width = 500;
	private int height = 300;

	// attribute setters and getters
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title;}
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width;}
	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height;}

	// required ports
	private javax.swing.JPanel panel;

	// port setters and getters
	public void setPanel(javax.swing.JPanel panel) { this.panel = panel; }

// end generated code

	public boolean run(String[] args)
	{
		JFrame f = new JFrame(title);
		JPanel p = new JPanel(new BorderLayout());
		p.add(panel, BorderLayout.NORTH);
		f.add(p);
		f.setPreferredSize(new Dimension(width, height));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		return false;
	}
}
