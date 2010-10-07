package printpreview;

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
	private double fWidth = pageFormat.getWidth();
	private double fHeight = pageFormat.getHeight();

	public PrintPreviewPanel(Component pc, int percentScale)
	{
		targetComponent = pc;
		this.percentScale = percentScale / 2;

		pcImage = new BufferedImage(targetComponent.getWidth(), targetComponent.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = pcImage.createGraphics();
		targetComponent.paint(g);
		g.dispose();

		pageFormat.setOrientation(PageFormat.PORTRAIT);
		prp = new PreviewPage();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(460, 480));
		add(prp, BorderLayout.CENTER);
	}

	public class PreviewPage extends JPanel
	{
		int x1, y1, l1, h1;
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
				double multiplier = multiplier();
				int fw = getFWidth();
				int fh = getFHeight();
				int xoff = (getWidth() - fw) / 2 - 10;
				int yoff = (getHeight() - fh) / 2 - 20;
				
				x1 = (int) Math.rint(pageFormat.getImageableX() * multiplier);
				y1 = (int) Math.rint(pageFormat.getImageableY() * multiplier);
				l1 = (int) Math.rint(pageFormat.getImageableWidth() * multiplier);
				h1 = (int) Math.rint(pageFormat.getImageableHeight() * multiplier);

				image = pcImage.getScaledInstance(
						(int) (pcImage.getWidth() * percentScale / 100 * multiplier),
						(int) (pcImage.getHeight() * percentScale / 100 * multiplier),
						Image.SCALE_AREA_AVERAGING);
				
				g.setClip(9 + xoff, 9 + yoff, fw + 2, fh + 2);
				g.setColor(Color.WHITE);
				g.fillRect(10 + xoff, 10 + yoff, fw, fh);
				g.drawImage(image, x1 + 10 + xoff, y1 + 10 + yoff, this);
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(x1 + 10 + xoff, y1 + 10 + yoff, l1, h1);
				g.setColor(Color.black);
				g.drawRect(10 + xoff, 10 + yoff, fw, fh);
			}
			// LANDSCAPE
			else
			{
				double multiplier = multiplier();
				int fw = getFHeight();
				int fh = getFWidth();
				int xoff = (getWidth() - fw) / 2 - 10;
				int yoff = (getHeight() - fh) / 2 - 20;

				x1 = (int) Math.rint(pageFormat.getImageableX() * multiplier);
				y1 = (int) Math.rint(pageFormat.getImageableY() * multiplier);
				l1 = (int) Math.rint(pageFormat.getImageableWidth() * multiplier);
				h1 = (int) Math.rint(pageFormat.getImageableHeight() * multiplier);

				image = pcImage.getScaledInstance(
						(int) (pcImage.getWidth() * percentScale / 100 * multiplier),
						(int) (pcImage.getHeight() * percentScale / 100 * multiplier),
						Image.SCALE_AREA_AVERAGING);

				g.setClip(9 + xoff, 9 + yoff, fw + 2, fh + 2);
				g.setColor(Color.WHITE);
				g.fillRect(10 + xoff, 10 + yoff, fw, fh);
				g.drawImage(image, x1 + 10 + xoff, y1 + 10 + yoff, this);
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(x1 + 10 + xoff, y1 + 10 + yoff, l1, h1);
				g.setColor(Color.BLACK);
				g.drawRect(10 + xoff, 10 + yoff, fw, fh);
			}
		}

		private int getFWidth()
		{
			// scale according to the width and height
			return (int) (fWidth * multiplier());
		}

		private int getFHeight()
		{
			return (int) (fHeight * multiplier());
		}
		
		private double multiplier()
		{
			return Math.min((double) (getWidth() - 20) / fWidth, (double) (getHeight() - 30) / fHeight);
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
		this.percentScale = percent / 2;
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
			}
			catch (PrinterException exception)
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
			
			// scale to fill the page
			double scale = percentScale / 100;
			g2D.scale(scale, scale);
			
			targetComponent.paint(g);
			return Printable.PAGE_EXISTS;
		}
	}
}
