package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulation.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public abstract class FieldPopupManipulator implements Gem
{
	private static final ImageIcon ICON = IconLoader.loadIcon("implementation.png");
	private static final ImageIcon ICON_BAD = IconLoader.loadIcon("implementation-bad.png");

	protected ToolCoordinatorFacet coordinator;
	protected DiagramViewFacet diagramView;
	protected FigureFacet figure;
	protected boolean readOnly;
	private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();
	private String labelText;
	private String startText;
	protected boolean bad;
	private ZCanvas canvas;
	
	private TextManipulatorGem text;
	
	public abstract void setTextAndFinish(String text);
	
	public FieldPopupManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure, String labelText, boolean bad, boolean forceReadOnly)
	{
		this.coordinator = coordinator;
		this.diagramView = diagramView;
		this.figure = figure;
		readOnly = forceReadOnly | GlobalSubjectRepository.repository.isReadOnly(getElement());
		this.labelText = labelText;
		this.bad = bad;
	}
	
	public void setStartText(String startText)
	{
		this.startText = startText;
	}

	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}

  protected NamedElement getElement()
  {
  	return (NamedElement) figure.getSubject();
  }
  
	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
		private ZGroup diagramLayer;
		private ZGroup group;
				
		/** the resizing manipulator for nodes is not direct.  The item must be selected first */
	  public int getType()
	  {
	    return TYPE2;
	  }
	
	  public void addToView(ZGroup diagramLayer, ZCanvas cvs)
	  {
	  	this.diagramLayer = diagramLayer;
	  	canvas = cvs;
	  	
	  	UBounds bounds = figure.getFullBounds();
	  	UPoint pt = bounds.getPoint().subtract(new UDimension(16, 16));
	  	group = new ZGroup();
	  	
	  	ZTransformGroup transform = new ZTransformGroup();
	  	ZImage img = new ZImage(bad ? ICON_BAD.getImage() : ICON.getImage());
	  	ZVisualLeaf ileaf = new ZVisualLeaf(img);
	  	transform.setTranslation(pt.getX(), pt.getY());
	  	transform.addChild(ileaf);
	  	group.addChild(transform);
	  	
  		// add a clear cover to pick up mouse clicks
  		ZRectangle rect = new ZRectangle(new UBounds(pt, new UDimension(16, 16)));
  		rect.setPenPaint(null);
  		rect.setFillPaint(new Color(0, 0, 0, 0));
	    ZVisualLeaf leaf = new ZVisualLeaf(rect);
	    leaf.putClientProperty("manipulator", this);
  		group.addChild(leaf);
	  	
	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	    
	  	diagramLayer.addChild(group);
	  }
	  
	  public void cleanUp()
	  {
	  	diagramLayer.removeChild(group);
	  }
	
		/** invoked to see if we want to enter on this key
		 * @param keyPressed the key that was pressed
		 */
		public boolean wantToEnterViaKeyPress(char keyPressed)
		{
			return false;
		}

	  /**invoked on key entry*/
	  public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	  {
	    // not applicable
	  }
	
	  /** invoked only on type 0 manipulators */
	  public void enterImmediately(ManipulatorListenerFacet listener)
	  {
	    // not applicable
	  }
	  
	  /**invoked on button press entry*/
	  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  	text = new TextManipulatorGem(
	  			coordinator,
	  			diagramView,
	  			"",
	  			"",
	  			startText,
	  			"",
	  			ScreenProperties.getPrimaryFont(),
	  			Color.BLACK,
	  			Color.WHITE,
	  			TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING,
          ManipulatorFacet.TYPE0);
	  	text.setForceReadOnly(readOnly);

	  	TextableFacet tf = new TextableFacet()
			{
				public UBounds vetTextResizedExtent(String text)
				{
					ZSwing label = makeLabel();
					ZSwing tLabel = new ZSwing(canvas, new JLabel(text));
					return
						new UBounds(
								figure.getFullBounds().getPoint().
									add(new UDimension(9, 5)).add(
										new UDimension(label.getBounds().width, 0)),
								new UDimension(tLabel.getBounds().getWidth() + 20, label.getBounds().getHeight()));
				}
				
				public void setText(String text, Object listSelection, boolean unsuppress)
				{
					setTextAndFinish(text);
				}
				
				public UBounds getTextBounds(String text)
				{
					return vetTextResizedExtent(text);
				}
				
				public FigureFacet getFigureFacet()
				{
					return figure;
				}
				
				public JList formSelectionList(String textSoFar)
				{
					return null;
				}
			};
			text.connectTextableFacet(tf);
			TextDecoratorFacet decorator = new TextDecoratorFacet()
			{
				public ZNode makeDecorator(UBounds bounds)
				{
					UBounds tbounds = 
						new UBounds(
								bounds.getPoint(),
								bounds.getDimension().maxOfEach(new UDimension(200, 0)));
					UBounds ibounds = 
								tbounds.addToPoint(new UDimension(-3, -3)).addToExtent(new UDimension(6, 6));
					
					ZSwing label = makeLabel();
					double textWidth = label.getBounds().width;
					ZRectangle inRect = new ZRectangle(ibounds.x, ibounds.y, ibounds.width, ibounds.height);
					inRect.setPenPaint(Color.LIGHT_GRAY);
					inRect.setFillPaint(Color.WHITE);
					
					UBounds obounds =
						tbounds.addToPoint(new UDimension(-9 - textWidth - 4, -9)).addToExtent(new UDimension(18 + textWidth + 4, 18));
								
					ZRectangle rect = new ZRectangle(obounds.x, obounds.y, obounds.width, obounds.height);
					rect.setPenPaint(Color.LIGHT_GRAY);
					rect.setFillPaint(new Color(243, 243, 243));

					ZTransformGroup transform = new ZTransformGroup();
					transform.addChild(new ZVisualLeaf(label));
					transform.translate(obounds.x + 8, obounds.y + 1 + 9);
					
					ZGroup group = new ZGroup();
					group.addChild(new ZVisualLeaf(rect));
					group.addChild(new ZVisualLeaf(inRect));
					group.addChild(transform);
					
					return group;
				}
			};
			text.connectTextDecoratorFacet(decorator);		
	  	
			ManipulatorFacet mf = text.getManipulatorFacet();
			mf.addToView(diagramLayer, canvas);
	  	mf.enterImmediately(listener);
	  }

	  private ZSwing makeLabel()
	  {
	  	JLabel label = new JLabel(labelText + " ");
	  	label.setEnabled(!readOnly);
	  	label.setBackground(null);
	  	ZSwing swing = new ZSwing(canvas, label);
	  	return swing;
	  }
	  
		/**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  }
	
	  /** invoked when a key has been pressed */
	  public void keyPressed(char keyPressed)
	  {
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure */
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (text != null)
	  		text.getManipulatorFacet().mousePressed(over, point, event);
	  }
	
	  /** invoked when a mouse button has been dragged on this figure */
	  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (text != null)
	  		text.getManipulatorFacet().mouseDragged(over, point, event);
	  }
	
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (text != null)
	  		text.getManipulatorFacet().mouseMoved(over, point, event);
	  }
	
	  /**Invoked when a mouse button has been released on this figure */
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (text != null)
	  		text.getManipulatorFacet().mouseReleased(over, point, event);
	  }

    public void setLayoutOnly()
    {
    }

		public boolean handlesReadonly()
		{
			return true;
		}
	}
}
