package printpreview;

/* save and compile as PrintPreviewS2.java */
import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;

import javax.swing.*;

public class PrintPreviewPanel extends JPanel
{
	private Component targetComponent;
	private PageFormat pageFormat = new PageFormat();
	private double percentScale;
	private BufferedImage pcImage;
	private boolean portrait = true;
	private PreviewPage prp;
	private int pcw;
	private int pch;
	private double hw;

	public PrintPreviewPanel(Component pc, int percentScale)
	{
		targetComponent = pc;
		this.percentScale = percentScale;

		pcImage = new BufferedImage(pcw = targetComponent.getWidth(), pch = targetComponent.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = pcImage.createGraphics();
		targetComponent.paint(g);
		g.dispose();
		hw = (double) pch / (double) pcw;

		pageFormat.setOrientation(PageFormat.PORTRAIT);
		prp = new PreviewPage();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(460, 480));
		add(prp, BorderLayout.CENTER);
	}

	GridBagConstraints buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, double wx, double wy)
	{
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}

	public class PreviewPage extends JPanel
	{
		int x1, y1, l1, h1, x2, y2;
		Image image;

		public PreviewPage()
		{
			setPreferredSize(new Dimension(460, 460));
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			// PORTRAIT
			if (pageFormat.getOrientation() == PageFormat.PORTRAIT)
			{
				int xoff = (getWidth() - 330) / 2 - 10;
				int yoff = (getHeight() - 430) / 2 - 20;
				
				x1 = (int) Math.rint(((double) pageFormat.getImageableX() / 72) * 40);
				y1 = (int) Math.rint(((double) pageFormat.getImageableY() / 72) * 40);
				l1 = (int) Math.rint(((double) pageFormat.getImageableWidth() / 72) * 40);
				h1 = (int) Math.rint(((double) pageFormat.getImageableHeight() / 72) * 40);

				x2 = (int) Math.rint((double) l1 * percentScale / 100);
				y2 = (int) Math.rint(((double) l1 * hw) * percentScale / 100);
				image = pcImage.getScaledInstance(x2, y2, Image.SCALE_AREA_AVERAGING);
				
				g.setClip(9 + xoff, 9 + yoff, 342, 442);
				g.setColor(Color.WHITE);
				g.fillRect(10 + xoff, 10 + yoff, 340, 440);
				g.drawImage(image, x1 + 10 + xoff, y1 + 10 + yoff, this);
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(x1 + 10 + xoff, y1 + 10 + yoff, l1, h1);
				g.setColor(Color.black);
				g.drawRect(10 + xoff, 10 + yoff, 340, 440);
			}
			// LANDSCAPE
			else
			{
				int xoff = (getWidth() - 430) / 2 - 15;
				int yoff = (getHeight() - 330) / 2 - 20;

				x1 = (int) Math.rint(((double) pageFormat.getImageableX() / 72) * 40);
				y1 = (int) Math.rint(((double) pageFormat.getImageableY() / 72) * 40);
				l1 = (int) Math.rint(((double) pageFormat.getImageableWidth() / 72) * 40);
				h1 = (int) Math.rint(((double) pageFormat.getImageableHeight() / 72) * 40);

				x2 = (int) Math.rint((double) l1 * percentScale / 100);
				y2 = (int) Math.rint(((double) l1 * hw) * percentScale / 100);
				image = pcImage.getScaledInstance(x2, y2, Image.SCALE_AREA_AVERAGING);

				g.setClip(9 + xoff, 9 + yoff, 442, 342);
				g.setColor(Color.WHITE);
				g.fillRect(10 + xoff, 10 + yoff, 440, 340);
				g.drawImage(image, x1 + 10 + xoff, y1 + 10 + yoff, this);
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(x1 + 10 + xoff, y1 + 10 + yoff, l1, h1);
				g.setColor(Color.BLACK);
				g.drawRect(10 + xoff, 10 + yoff, 440, 340);
			}
		}
	}
	
	public void setProperties()
	{
		if (portrait)
			pageFormat.setOrientation(PageFormat.PORTRAIT);
		else
			pageFormat.setOrientation(PageFormat.LANDSCAPE);
		prp.repaint();
	}
	
	public void setOrientation(boolean portrait)
	{
		this.portrait = portrait;
		setProperties();
	}
	
	public void setScale(int percent)
	{
		this.percentScale = percent;
		setProperties();
	}

	public void print()
	{
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();
		book.append(new PrintPage(), pageFormat);
		printerJob.setPageable(book);
		
		boolean doPrint = printerJob.printDialog();
		if (doPrint)
		{
			try
			{
				printerJob.print();
			} catch (PrinterException exception)
			{
				System.err.println("Printing error: " + exception);
			}
		}
	}

	// public class PrintPage implements Printable{
	private class PrintPage implements Printable
	{

		public int print(Graphics g, PageFormat format, int pageIndex)
		{
			Graphics2D g2D = (Graphics2D) g;
			g2D.translate(format.getImageableX(), format.getImageableY());
			// disableDoubleBuffering(mp);
			// scale to fill the page
			double dw = format.getImageableWidth();
			double dh = format.getImageableHeight();
			double xScale = dw / (1028 * percentScale / 100);
			double yScale = dh / (768 * percentScale / 100);
			g2D.scale(xScale, yScale);
			targetComponent.paint(g);
			// enableDoubleBuffering(mp);
			return Printable.PAGE_EXISTS;
		}
	}
}
