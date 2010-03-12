package com.hopstepjump.idraw.figurefacilities.textmanipulation;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.hexidec.ekit.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


/**
 * An editable text item.  This was very hard to code and potentially fragile (however, see note below) because:
 *
 * 1. picking over a ZText item, or a ZSwing with a text area seems to always return null
 *    (solution is to cover the item with a transparent rectangular box, and then direct mouse events from this manipulator to the swing item.  Keys seem to work ok).
 * 2. TextField doesn't work properly
 *    (solution is to use TextArea or TextPane instead)
 *
 * @author A.McVeigh 26/4/2002
 * 
 * Seems to have withstood the test of time, and many upgrades of swing and some of jazz... 6/11/07
 */

public final class TextManipulatorGem implements Gem
{
  public static final String HTML_START_TEXT = "<html><body><p></p></body></html>";
  
  public static final int TEXT_PANE_TYPE                                = 1;
  public static final int TEXT_PANE_CENTRED_TYPE                        = 2;
  public static final int TEXT_AREA_TYPE                                = 3;
  public static final int TEXT_AREA_ONE_LINE_TYPE                       = 4;
	public static final int TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING = 5;
  public static final int TEXT_HTML_TYPE                                = 6;

  private static final int DISPLAYING_STATE = 1;
  private static final int EDITING_STATE    = 2;

  private int state = DISPLAYING_STATE;

	private ToolCoordinatorFacet coordinator;
  private String textToStart;
  private String originalText;
  private String underlyingText;
  private Font font;
  private Color textColour;
  private Color backgroundColour;
  private ZCanvas canvas;
  private ZGroup diagramLayer;
  private ZGroup group;
  private ZTransformGroup swingTransform;
  private ZTransformGroup listTransform;
  private JComponent scroller;
  private boolean wordWrap;

  private JTextPane textPane;
  private JTextComponent currentText;
  private int type;
  private TextableFacet target;
  private KeyListener keyListener;
  private String lastText;
  private int added = 0;
	private int manipulatorType;  // text manipulation is generally be on button release (TYPE3), but doesn't have to be
  private ManipulatorListenerFacet listener;
  private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();

	private String cmdDescription;
	private String undoCmdDescription;



  public static EkitCore makeEkit(boolean useIcons, String text)
  {
    EkitCore core = new EkitCore(
        (String)null,
        (String)null,
        (String)null,
        (StyledDocument)null,
        (URL)null,
        true,
        false,
        true,
        useIcons,
        "en",
        "US",
        false,
        false,
        false,
        true,
        EkitCore.TOOLBAR_DEFAULT_MULTI);
    core.setDocumentText(text);
    return core;
  }
  
	public class ManipulatorFacetImpl implements ManipulatorFacet
	{
	  public int getType()
	  {
	    return manipulatorType;
	  }
	
	  /** add the view to the display */
	  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
	  {
	    added++;
	    TextManipulatorGem.this.canvas = canvas;
	    TextManipulatorGem.this.diagramLayer = diagramLayer;
	
	    // add the underlying text information to the display
	    UBounds bounds = target.getTextBounds(textToStart);
	    UBounds underlyingBounds = target.getTextBounds(underlyingText != null ? underlyingText : textToStart);
	    group = new ZGroup();
	
	    if (state == DISPLAYING_STATE)
	    {
	      // add an alpha transparent box to cover the text, so that picking will find this manipulator figure
	      // can't use a ZText, as a pick without manipulators will always find null rather than what is under this %-)
	      ZRectangle rect = new ZRectangle(bounds);
	      rect.setFillPaint(ScreenProperties.getTransparentColor());
	      rect.setPenPaint(null);
	      ZVisualLeaf transparent = new ZVisualLeaf(rect);
	      group.addChild(transparent);
	    }
	    else
	    {
	      // following is a hack because garbage gets left behind
	    	UBounds coverBounds = underlyingBounds;
	      if (type == TEXT_PANE_TYPE && type != TEXT_PANE_CENTRED_TYPE)
	      	coverBounds = underlyingBounds.addToExtent(new UDimension(0,1));
	      ZRectangle rect = new ZRectangle(coverBounds);
	      rect.setFillPaint(backgroundColour);
	      rect.setPenPaint(null);
	      ZVisualLeaf rectLeaf = new ZVisualLeaf(rect);
	      group.addChild(rectLeaf);
	    }
	
	    if (state == EDITING_STATE || type == TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING && state == DISPLAYING_STATE)
	    {
	      if (type == TEXT_AREA_TYPE ||
            type == TEXT_HTML_TYPE ||
            type == TEXT_AREA_ONE_LINE_TYPE ||
            type == TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING)
	      {
          if (type == TEXT_HTML_TYPE)
          {
            final EkitCore core = makeEkit(true, textToStart);
            core.setFrame(JOptionPane.getFrameForComponent(canvas));
            currentText = core.getTextPane();
            currentText.setMaximumSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
            currentText.setPreferredSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
            
            // add keystrokes
            Keymap parent = currentText.getKeymap();
            Keymap map = JTextComponent.addKeymap("FontStyleMap", parent);

            // make the popup menu by customising the ekit menu
            final JPopupMenu popup = new JPopupMenu();
            Vector<String> selected = new Vector<String>();
            selected.add("Edit");
            selected.add("Font");
            selected.add("Format");
            selected.add("Search");
            selected.add("Insert");
            selected.add("Table");
            JMenuBar bar = core.getCustomMenuBar(selected);
            java.util.List<JMenuItem> items = new ArrayList<JMenuItem>();
            for (int lp = 0; lp < bar.getComponentCount(); lp++)
            {
              JMenuItem item = (JMenuItem) bar.getComponent(lp);
              items.add((JMenuItem) bar.getComponent(lp));

              // register any key bindings of the children menu items
              if (item instanceof JMenu)
              {
                JMenu itemMenu = (JMenu) item;
                for (int lp2 = 0; lp2 < itemMenu.getItemCount(); lp2++)
                {
                  Component sub = itemMenu.getItem(lp2);
                  if (sub instanceof JMenuItem)
                  {
                    JMenuItem subItem = (JMenuItem) sub;
                    KeyStroke stroke = subItem.getAccelerator();
                    if (stroke != null && subItem.getAction() != null)
                    {
                      // register the accelerator
                      map.addActionForKeyStroke(stroke, subItem.getAction());
                    }
                  }
                }
              }
            }
            currentText.setKeymap(map);

            // add to the popup menu
            for (JMenuItem item : items)
              popup.add(item);

            // add a popup menu
            currentText.addMouseListener(new MouseAdapter()
                {
                  public void mouseReleased(MouseEvent e)
                  {
                    Point pt = e.getPoint();
                    if (e.getButton() == MouseEvent.BUTTON3)
                      popup.show(currentText, pt.x, pt.y);
                  }
                });
          }
          else
            currentText = new JTextArea(textToStart);
          
	        currentText.setBounds((int)bounds.getTopLeftPoint().getX(), (int)bounds.getTopLeftPoint().getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
          
	        if (type == TEXT_AREA_TYPE)
	        {
            JTextArea area = (JTextArea) currentText;
            currentText.setMaximumSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
            if (wordWrap)
            {
              area.setWrapStyleWord(true);
              area.setLineWrap(true);
            }
	        }
	
	        if (font != null)
            currentText.setFont(font);
          currentText.setForeground(textColour);
          currentText.setBackground(backgroundColour);
          currentText.setBorder(null);
	
	        if (type == TEXT_AREA_ONE_LINE_TYPE || type == TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING)
	        {
            currentText.addKeyListener(keyListener = new KeyListener()
	          {
	            public void keyPressed(KeyEvent e)
	            {   
	              lastText = currentText.getText();
	              char ch = e.getKeyChar();
	              if (!isControl(e))
	                resizeOnKey(e, currentText.getText() + ch);
	            }

	            public void keyReleased(KeyEvent e)
	            {
	              // suppress any enter key -- we don't want this to have multiple lines
	              if (e.getKeyCode() == KeyEvent.VK_ENTER)
	              {
                  currentText.setText(lastText);
	                finishManipulation(null);
	              }
	              else
	              {
	                resizeOnKey(e, currentText.getText());
	                handlePossibleSelectionList(e, true);
	              }
	            }
	            public void keyTyped(KeyEvent e)
	            {
	              handlePossibleSelectionList(e, false);
	            }

	            private void resizeOnKey(KeyEvent e, String text)
	            {
	              // recentre
	              UPoint newTranslationPoint = target.getTextBounds(text).getPoint();
	              swingTransform.setTranslation(newTranslationPoint.getX(), newTranslationPoint.getY());
	            }
	          });
	        }
	        else
	        {
            currentText.addKeyListener(keyListener = new KeyListener()
	          {
	            public void keyPressed(KeyEvent e)
	            {
	              lastText = currentText.getText();
	              if (!isControl(e))
	                resizeOnKey(e, currentText.getText() + e.getKeyChar());
	            }
	            public void keyReleased(KeyEvent e)
	            {
	              resizeOnKey(e, currentText.getText());
	            }
	            public void keyTyped(KeyEvent e)
	            {
	              // no tab completion is available for this text type
	            }
	            private void resizeOnKey(KeyEvent e, String text)
	            {
	              // recentre
	              UPoint newTranslationPoint = target.getTextBounds(text).getPoint();
	              swingTransform.setTranslation(newTranslationPoint.getX(), newTranslationPoint.getY());
	            }
	          });
	        }
	      }
	      else
	      {
	        currentText = textPane = new JTextPane();
	        if (font != null)
	          textPane.setFont(font);
	        textPane.setForeground(textColour);
	        textPane.setBackground(backgroundColour);
	        textPane.setBorder(null);
	
	        // try to centre the text
	        if (type == TEXT_PANE_CENTRED_TYPE)
	        {
	          StyledEditorKit.AlignmentAction alignment = new StyledEditorKit.AlignmentAction("centre", StyleConstants.ALIGN_CENTER);
	          ActionEvent action = new ActionEvent(textPane, 1, "centre");
	          alignment.actionPerformed(action);
	
	          textPane.addKeyListener(keyListener = new KeyListener()
	          {
	            public void keyPressed(KeyEvent e)
	            {
	              lastText = textPane.getText();
	              if (!isControl(e))
	                resizeOnKey(e, textPane.getText() + e.getKeyChar());
	            }
	            public void keyReleased(KeyEvent e)
	            {
	              resizeOnKey(e, textPane.getText());
                handlePossibleSelectionList(e, true);
	            }
	            public void keyTyped(KeyEvent e)
	            {
	              handlePossibleSelectionList(e, false);
	            }
	            private void resizeOnKey(KeyEvent e, String text)
	            {
	              // recentre
	              UPoint newTranslationPoint = target.getTextBounds(text).getPoint();
	              swingTransform.setTranslation(newTranslationPoint.getX(), newTranslationPoint.getY());
	            }
	          });
	        }
	        textPane.setText(textToStart);
	        textPane.setBounds((int)bounds.getTopLeftPoint().getX(), (int)bounds.getTopLeftPoint().getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
	        textPane.setMaximumSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
	
	        // if this is left justified, constrain to a box, using a crazy method.  Unable to figure out anything better.
	        if (type == TEXT_PANE_TYPE)
	        {
	          textPane.setPreferredSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
	          textPane.getDocument().addDocumentListener(new DocumentListener()
	          {
	            public void insertUpdate(DocumentEvent e)
	            {
	              int height = (int) textPane.getMinimumSize().getHeight();
	              textPane.setPreferredSize(new Dimension((int)textPane.getBounds().getWidth(), height));
	            }
	
	            public void removeUpdate(DocumentEvent e)
	            {
	              insertUpdate(e);
	            }
	
	            public void changedUpdate(DocumentEvent e)
	            {
	              insertUpdate(e);
	            }
	          });
	        }
	      }
	
	      ZSwing textSwing = new ZSwing(canvas, currentText);	      
	      ZVisualLeaf jazzSwing = new ZVisualLeaf(textSwing);
	      jazzSwing.putClientProperty("manipulator", this);
	      
	      swingTransform = new ZTransformGroup(jazzSwing);
	      swingTransform.translate(bounds.getTopLeftPoint().getX(), bounds.getTopLeftPoint().getY());
	      swingTransform.putClientProperty("manipulator", this);
	      swingTransform.setChildrenPickable(false);
	      swingTransform.setChildrenFindable(false);
	      group.addChild(swingTransform);

        // add a transparent box over this so we can pick up correct mouse events
	      ZRectangle rect = new ZRectangle(bounds);
	      rect.setFillPaint(ScreenProperties.getTransparentColor());
	      rect.setPenPaint(null);
	      ZVisualLeaf transparentCover = new ZVisualLeaf(rect);
	      group.addChild(transparentCover);
	    }
	
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("manipulator", this);
	    diagramLayer.addChild(group);
	  }

    private void handlePossibleSelectionList(KeyEvent e, boolean released)
    {
      char ch = e.getKeyChar();

      // disallow tabs
      boolean tab = ch == '\t';
      if (tab)
        currentText.setText(lastText);
      String current = currentText.getText();
      
      if (!released && tab)
      {
        if (listTransform == null)
          createSelectionList(current, new UBounds(swingTransform.getBounds()).getPoint().getX());
        else
          deleteSelectionList();
      }
      else
      if (released && listTransform != null)
      {
        if (current.length() == 0)
          deleteSelectionList();
        else
        {
          // recreate to match up with the lastest text
          double currentX = new UBounds(listTransform.getBounds()).getPoint().getX();
          deleteSelectionList();
          createSelectionList(current, currentX);
        }
      }
    }

	  private void deleteSelectionList()
    {
	    if (listTransform != null)
	    {
  	    group.removeChild(listTransform);
  	    listTransform = null;
	    }
    }

    private void createSelectionList(String textSoFar, double xPosition)
    {
      final JList list = target.formSelectionList(textSoFar);
      if (list == null)
        return;
      
      list.addListSelectionListener(new ListSelectionListener()
      {
        public void valueChanged(final ListSelectionEvent e)
        {
          new Thread(new Runnable()
          {
            public void run()
            {
              try
              {
                Thread.sleep(100);
                SwingUtilities.invokeAndWait(new Runnable()
                {
                  public void run()
                  {
                    finishManipulation(list.getSelectedValue());
                  }
                });
              }
              catch (Exception e)
              {
              }
            }
          }).start();
        }          
      });
      
      scroller = new JScrollPane(list);
      scroller.setPreferredSize(new UDimension(200, 80).getAsAWTDimension());
      scroller.setBorder(BorderFactory.createEtchedBorder());
      list.setBackground(new Color(250, 240, 230));
      ZSwing popup = new ZSwing(canvas, scroller);
      listTransform = new ZTransformGroup(new ZVisualLeaf(popup));
      UPoint offset = new UBounds(swingTransform.getBounds()).getBottomLeftPoint();
      listTransform.translate(xPosition, offset.getY());
      group.addChild(listTransform);
      
      // add a transparent rectangle to the 
      ZRectangle rect2 = new ZRectangle(listTransform.getBounds());
      rect2.setFillPaint(ScreenProperties.getTransparentColor());
      rect2.setPenPaint(null);
      ZVisualLeaf transparentCover2 = new ZVisualLeaf(rect2);
      group.addChild(transparentCover2);
    }
	
	  public void cleanUp()
	  {
	    added--;
	    if (group != null)
	    {
	      diagramLayer.removeChild(group);
	    }
	    group = null;
	
	    if (currentText != null && keyListener != null)
	      currentText.removeKeyListener(keyListener);
	    currentText = null;
	    keyListener = null;
	    textPane = null;
	    swingTransform = null;
	  }
	
		/** invoked to see if we want to enter on this key
		 * @param keyPressed the key that was pressed
		 */
		public boolean wantToEnterViaKeyPress(char keyPressed)
		{
			return keyPressed != KeyEvent.VK_DELETE;
		}

	  /**invoked on key entry*/
	  public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	  {
	    TextManipulatorGem.this.listener = listener;

      if (target.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
        finishManipulation(null);
      else
        enter_EDITING_STATE_keypress(keyPressed);
	  }
	  
	  /** for direct selections etc */
	  public void enterImmediately(ManipulatorListenerFacet listener)
	  {
	    TextManipulatorGem.this.listener = listener;

      if (target.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
        finishManipulation(null);
      else
        enter_EDITING_STATE_immediately();
	  }
	
	  /**invoked on button press entry*/
	  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	    // applicable to type 2 manipulators
	    TextManipulatorGem.this.listener = listener;
	    
	    // don't do this if the subject is read only
      if (target.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
        finishManipulation(null);
      else
        enter_EDITING_STATE_button_press(point);
	  }
	
	  /**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	    TextManipulatorGem.this.listener = listener;
	    
      // don't do this if the subject is read only
      if (target.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
        finishManipulation(null);
      else
        enter_EDITING_STATE_button_release(point);      
	  }
	
	  /**invoked when a key has been pressed*/
	  public void keyPressed(char keyPressed)
	  {
	  }
	
	  /**invoked when a mouse button has been dragged on this figure*/
	  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    if (state == EDITING_STATE)
	      sendMouseEventToText(point, MouseEvent.MOUSE_DRAGGED, event.getModifiers());
	  }
	
	  /**Invoked when a mouse button has been released on this figure*/
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
      if (state == EDITING_STATE)
      {
	      sendMouseEventToText(point, MouseEvent.MOUSE_RELEASED, event.getModifiers());
      }
	  }
	
	  private void finishManipulation(Object listSelection)
	  {
	    String current = currentText == null ? null : currentText.getText();
			
	    cleanUp();
	    addToView(diagramLayer, canvas);

	    if (current != null && (listSelection != null || !current.equals(originalText)))
	    {
	      // NOTE: we want the text *and* the selection in some cases, as this gives more info if the formSelectionList call only uses part of the text data
	    	coordinator.startTransaction(cmdDescription, undoCmdDescription);
			  SetTextTransaction.set(target.getFigureFacet(), current, listSelection, true);
			  coordinator.commitTransaction();
	    }
	
	    listener.haveFinished();	
	
	    // move back to a display state
	    state = DISPLAYING_STATE;
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure*/
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (swingTransform == null)
	  		return;
	  	boolean inText = swingTransform.getBounds().contains(point);
	    boolean inList = listTransform == null ? false : listTransform.getBounds().contains(point); 
	    
	    if (state == EDITING_STATE)
	    {
	      if (!inText && !inList)
	        finishManipulation(null);
	      else
	      {
	        sendMouseEventToText(point, MouseEvent.MOUSE_PRESSED, event.getModifiers());
	        sendMouseEventToText(point, MouseEvent.MOUSE_CLICKED, event.getModifiers());
	      }
	    }
	  }
	
	  /**invoked when a mouse button has been moved on this figure*/
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
      // NOTE: group is null if we delete via the browser sometimes when we are still editing...
	    if (state == EDITING_STATE && group != null)
	      sendMouseEventToText(point, MouseEvent.MOUSE_MOVED, MouseEvent.BUTTON1_MASK);
	  }

    public void setLayoutOnly()
    {
    }
	}

  public TextManipulatorGem(ToolCoordinatorFacet coordinator, String cmdDescription, String undoCmdDescription, String textToStart, Font font, Color textColour, Color backgroundColour, int type)
  {
  	this(coordinator, cmdDescription, undoCmdDescription, textToStart, null, font, textColour, backgroundColour, type, ManipulatorFacet.TYPE3);
  }

  public TextManipulatorGem(ToolCoordinatorFacet coordinator, String cmdDescription, String undoCmdDescription, String textToStart, String underlyingText, Font font, Color textColour, Color backgroundColour, int type, int manipulatorType)
  {
  	this.coordinator = coordinator;
  	this.cmdDescription = cmdDescription;
  	this.undoCmdDescription = undoCmdDescription;
    this.textToStart = textToStart;
    this.originalText = textToStart;
    this.underlyingText = underlyingText;
    this.font = font;
    this.textColour = textColour;
    this.backgroundColour = backgroundColour;
    this.type = type;
    this.lastText = textToStart;
    this.manipulatorType = manipulatorType;
  }
  
  public void connectTextableFacet(TextableFacet textableFacet)
  {
  	this.target = textableFacet;
  }

  private void sendMouseClickToText(JComponent text, UPoint point, int buttonMask)
  {
    sendMouseEventToText(point, MouseEvent.MOUSE_PRESSED, buttonMask);
    sendMouseEventToText(point, MouseEvent.MOUSE_RELEASED, buttonMask);
    sendMouseEventToText(point, MouseEvent.MOUSE_CLICKED, buttonMask);
  }

  private void sendMousePressToText(UPoint point, int buttonMask)
  {
  	sendMouseEventToText(point, MouseEvent.MOUSE_PRESSED, buttonMask);
  }
  
  private void sendMouseEventToText(UPoint point, int event, int buttonMask)
  {
    // to the text or the popup list?
    boolean inText = swingTransform == null ?
        false : swingTransform.getBounds().contains(point) || listTransform == null;
    JComponent component = inText ? currentText : scroller;
    
    // transform the point, as ZSwing components think they start at the origin
    ZNode node = inText ? group : listTransform;
    if (node == null)  // used to cause an exception
    	return;
    point = makeLocalPoint(node, point);

    // if we have a composite, look for the child that is best positioned
    Component target = SwingUtilities.getDeepestComponentAt(
        component,
        point.getIntX(),
        point.getIntY());
    
    if (target != null)
    {
      // convert the point to a local coordinate
      point = new UPoint(
          SwingUtilities.convertPoint(
              component,
              new Point(point.getIntX(), point.getIntY()),
              target));
      target.dispatchEvent(new MouseEvent(
          target,
          event,
          0,
          buttonMask,
          point.getIntX(),
          point.getIntY(),
          1,
          false));
    }
  }

  private void enter_EDITING_STATE_button_press(UPoint point)
  {
    state = EDITING_STATE;
    manipulatorFacet.cleanUp();
    manipulatorFacet.addToView(diagramLayer, canvas);
    currentText.requestFocus();
    sendMousePressToText(point, MouseEvent.BUTTON1_MASK);
  }

  private void enter_EDITING_STATE_button_release(UPoint point)
  {
    state = EDITING_STATE;
    manipulatorFacet.cleanUp();
    manipulatorFacet.addToView(diagramLayer, canvas);
    currentText.requestFocus();
    sendMouseClickToText(currentText, point, MouseEvent.BUTTON1_MASK);
  }

  private void enter_EDITING_STATE_immediately()
  {
    state = EDITING_STATE;
    manipulatorFacet.cleanUp();
    manipulatorFacet.addToView(diagramLayer, canvas);
		
		currentText.selectAll();
    currentText.requestFocus();
  }

  private void enter_EDITING_STATE_keypress(char key)
  {
    state = EDITING_STATE;
    // enter with the key if this is not set yet
    if (type == TEXT_HTML_TYPE)
    {
      if (textToStart.equals(HTML_START_TEXT))
      {
        int index = textToStart.lastIndexOf("</p>");
        if (index != -1)
          textToStart = textToStart.substring(0, index) + key + textToStart.substring(index);
      }
    }
    else
    if (textToStart.length() == 0)
      textToStart = textToStart + key;

    manipulatorFacet.cleanUp();
    manipulatorFacet.addToView(diagramLayer, canvas);
    if (type == TEXT_HTML_TYPE)
      currentText.setCaretPosition(currentText.getCaretPosition());
    else
      currentText.setCaretPosition(currentText.getText().length());
    currentText.requestFocus();
  }
  
  private UPoint makeLocalPoint(ZNode node, UPoint point)
  {
    // the point is relative to the diagram rather than this manipulator
    // so make a local point relative to the text item
    UBounds bounds = new UBounds(node.getBounds());
    UDimension offsetFromText = point.subtract(bounds.getTopLeftPoint());
    return new UPoint(offsetFromText.getWidth(), offsetFromText.getHeight());
  }
  
  public ManipulatorFacet getManipulatorFacet()
  {
  	return manipulatorFacet;
  }

  public void setWordWrap(boolean wordWrap)
  {
    this.wordWrap = wordWrap;
  }
  
  /** is the keypress a control character?
   * @param e
   * @return
   */
  private boolean isControl(KeyEvent e)
  {
    return
      Character.isISOControl(e.getKeyChar()) ||
      e.getKeyCode() != KeyEvent.VK_LEFT ||
      e.getKeyCode() != KeyEvent.VK_RIGHT ||
      e.getKeyCode() != KeyEvent.VK_UP ||
      e.getKeyCode() != KeyEvent.VK_DOWN;
  }
}