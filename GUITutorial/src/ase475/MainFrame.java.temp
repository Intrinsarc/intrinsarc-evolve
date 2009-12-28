package ase475;

import java.awt.*;

import javax.swing.*;

import com.hopstepjump.backbone.runtime.api.*;

public class MainFrame
{
//start generated code
//attributes
	private Attribute<java.lang.String> title;
	private Attribute<Integer> width = new Attribute<Integer>(500);
	private Attribute<Integer> height = new Attribute<Integer>(300);
//required ports
	private javax.swing.JPanel panel;
//provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
//setters and getters
	public Attribute<java.lang.String> getTitle() { return title; }
	public void setTitle(Attribute<java.lang.String> title) { this.title = title;}
	public void setWidth(Attribute<Integer> width) { this.width = width;}
	public void setHeight(Attribute<Integer> height) { this.height = height;}
	public void setRawTitle(java.lang.String title) { this.title.set(title);}
	public void setPanel_JPanel(javax.swing.JPanel panel) { this.panel = panel; }
	public com.hopstepjump.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
//end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			JFrame f = new JFrame(title.get());
			JPanel p = new JPanel(new BorderLayout());
			p.add(panel, BorderLayout.NORTH);
			f.add(p);
			f.setPreferredSize(new Dimension(width.get(), height.get()));
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			return 0;
		}
	}	
}
