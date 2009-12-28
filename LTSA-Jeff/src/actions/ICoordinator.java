package actions;

import java.awt.*;

import lts.*;

public interface ICoordinator
{
	public void primeText();
	public void displayError(LTSException ex);
	public void clearAndShowOutput();
	public boolean needRecompile();
	public Point getLocationOnScreen();
}
