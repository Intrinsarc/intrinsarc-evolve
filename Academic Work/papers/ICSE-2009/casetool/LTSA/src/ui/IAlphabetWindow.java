package ui;

import javax.swing.JComponent;

import lts.CompositeState;
import lts.EventClient;
import lts.EventManager;
import lts.LTSEvent;
import lts.LTSOutput;

public interface IAlphabetWindow extends EventClient, LTSOutput
{
    public void setup(CompositeState cs,EventManager eman);
    public JComponent getComponent();
	public void ltsAction(LTSEvent e );
	public void out ( String str );
	public void outln ( String str );
	public void clearOutput ();
	public void setBigFont(boolean b);
	public void removeClient();
	public void copy();
    public void saveFile ();
}
