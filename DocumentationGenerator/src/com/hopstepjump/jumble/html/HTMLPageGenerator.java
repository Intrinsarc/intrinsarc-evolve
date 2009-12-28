package com.hopstepjump.jumble.html;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.imageio.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.component.*;

public class HTMLPageGenerator
{
	public static final String UPDATED_DATE_FORMAT = "EEE MMM d, yyyy";
	private ToolCoordinatorFacet coordinator;
	private DocumentationPackage top;
	private DocumentationPackage current;
	private String htmlTemplate;
	private File htmlGenerateTo;
	private String siteName;
	private String owner;
	private String mailTo;
	private String years;
	private int padding;
	private String pageTitlePrefix;
	private String prefix;

	public HTMLPageGenerator(ToolCoordinatorFacet coordinator, DocumentationPackage top, DocumentationPackage current,
			String htmlTemplate, File htmlGenerateTo, String prefix, String siteName, String owner, String mailTo,
			String years, int padding, String pageTitlePrefix)
	{
		this.coordinator = coordinator;
		this.top = top;
		this.current = current;
		this.htmlTemplate = htmlTemplate;
		this.htmlGenerateTo = htmlGenerateTo;
		this.siteName = siteName;
		this.owner = owner;
		this.mailTo = mailTo;
		this.years = years;
		this.prefix = prefix;
		this.padding = padding;
		this.pageTitlePrefix = pageTitlePrefix;
	}

	public void generate() throws IOException, HTMLGenerationException, PersistentFigureRecreatorNotFoundException,
			RepositoryPersistenceException
	{
		String replacePageTitle = replacePageTitle("$PAGETITLE", htmlTemplate);
		String replaceNavigation = replaceNavigation("$NAVIGATION", replacePageTitle);
		String replaceTitle = replaceTitle("$TITLE", replaceNavigation);
		String replaceUpdated = replaceUpdated("$UPDATED", replaceTitle);
		String replaceOwner = replaceUpdated.replace("$OWNER", owner);
		String replaceMailTo = replaceOwner.replace("$MAILTO", mailTo);
		String replaceYears = replaceMailTo.replace("$YEARS", years);
		String replaceContents = replaceContents("$CONTENTS", replaceYears);
		writeFile(htmlGenerateTo, current.getUniqueName(), replaceContents);
	}

	private String replaceUpdated(String variable, String contents)
	{
		// if this is a normal page, put in when the page was updated, but if it is
		// home do the entire site
		String updated;
		if (current.getLevel() == 0)
			updated = "Site updated on " + dateToString(current.getSiteUpdateTime());
		else
			updated = "Page updated on " + dateToString(current.getPageUpdateTime());
		updated += " by " + updaterToString(current.getPageUpdater());
		return contents.replace(variable, updated);
	}

	private String updaterToString(String pageUpdater)
	{
		if (pageUpdater == null)
			return "(unrecorded)";
		return pageUpdater;
	}

	private String dateToString(Date updateTime)
	{
		if (updateTime == null)
			return "(unrecorded)";
		SimpleDateFormat formatter = new SimpleDateFormat(UPDATED_DATE_FORMAT);
		return formatter.format(updateTime);
	}

	private String replacePageTitle(String variable, String contents)
	{
		if (current.getLevel() == 0)
			return contents.replace(variable, siteName);
		return contents.replace(variable, pageTitlePrefix + current.getName());
	}

	private String replaceTitle(String variable, String contents)
	{
		// walk from here to the top, making a breadcrumb
		List<DocumentationPackage> pkgs = current.makeBreadcrumbList();
		String crumb = "";
		for (DocumentationPackage pkg : pkgs)
		{
			if (pkg != top)
			{
				String fwd = "";
				if (crumb.length() != 0)
					fwd = " / ";
				crumb += "<a class=\"breadcrumb\" href=\"" + pkg.getUniqueName() + ".html\">" + fwd + pkg.getName() + "</a>";
			}
		}
		return contents.replace(variable, crumb);
	}

	private String replaceContents(String variable, String contents) throws PersistentFigureRecreatorNotFoundException,
			IOException
	{
		// get a y-ordered list of comments
		List<FigureFacet> commentFigures = current.findYOrderedComments();
		StringBuilder body = new StringBuilder();
		for (FigureFacet figure : commentFigures)
		{
			Comment comment = (Comment) figure.getSubject();
			// only take comments which are at the top level
			if (figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
				continue;

			// check the stereotype for see-also and figure
			boolean isFigure = StereotypeUtilities.isRawStereotypeApplied(comment,
					CommonRepositoryFunctions.DOCUMENTATION_FIGURE);
			boolean isSeeAlso = StereotypeUtilities.isRawStereotypeApplied(comment,
					CommonRepositoryFunctions.DOCUMENTATION_SEE_ALSO);
			boolean isImageGallery = StereotypeUtilities.isRawStereotypeApplied(comment,
					CommonRepositoryFunctions.DOCUMENTATION_IMAGE_GALLERY);
			boolean isImage = comment.getBinaryFormat().startsWith("Image/");

			if (isFigure)
				handleFigure(figure, comment, body);
			else if (isSeeAlso)
				handleSeeAlso(figure, comment, body);
			else if (isImageGallery)
				handleImageGallery(figure, comment, body);
			else if (isImage)
				handleImage(figure, comment, body);
			else
				handleComment(figure, comment, body);
		}
		return contents.replace(variable, body.toString());
	}

	private void handleComment(FigureFacet figure, Comment comment, StringBuilder body)
	{
		String data = comment.getBody().trim();
		if (!data.startsWith("<html"))
			body.append(comment.getBody() + "\n");
		else
		{
			// strip off elements
			data = stripStart("<body>", stripStart("</head>", stripStart("<head>", stripStart("<html>", data))));
			data = stripEnd("</body>", stripEnd("</html>", data));

			body.append(data + "\n");
			body.append("\n");
		}
	}

	private String stripEnd(String token, String data)
	{
		String strip = data.trim();
		if (strip.endsWith(token))
			return strip.substring(0, strip.length() - token.length());
		return strip;
	}

	private String stripStart(String token, String data)
	{
		String strip = data.trim();
		if (strip.startsWith(token))
			return strip.substring(token.length());
		return strip;
	}

	private void handleImage(FigureFacet figure, Comment comment, StringBuilder body) throws IOException
	{
		String filename;

		// if we have a body, use this as the name
		if (comment.getBody().length() != 0)
			filename = comment.getBody();
		else
		{
			filename = "Image" + comment.getUuid();
			// add a suffix
			String suffix = comment.getBinaryFormat().substring("Image/".length());
			filename += "." + suffix;
		}

		// write out the file to the images directory
		new CommonRepositoryFunctions().writeFileAsBinary(new File(htmlGenerateTo, "images/" + filename), comment.getBinaryData());

		body.append("<div style=\"text-align:center\">\n");
		body.append("<img style=\"padding:10px\" alt=\"\" style=\"\" width=\"" + (int) figure.getFullBounds().getWidth()
				+ "px\" height=\"" + (int) figure.getFullBounds().getHeight() + "px\" src=\"images/" + filename + "\" />\n");
		body.append("</div>\n");
	}

	private void handleSeeAlso(FigureFacet figure, Comment comment, StringBuilder body)
	{
		// get any sorted child documentation packages
		if (figure.getContainerFacet() == null)
			return;
		ContainerFacet container = figure.getContainerFacet();
		List<FigureFacet> docFigures = new ArrayList<FigureFacet>();
		FigureFacet simpleContainer = container.getContents().next();
		for (Iterator<FigureFacet> iter = simpleContainer.getContainerFacet().getContents(); iter.hasNext();)
		{
			FigureFacet child = iter.next();
			if (DocumentationPackage.isDocumentationPackage(child.getSubject()))
				docFigures.add(child);
		}
		HTMLDocumentationGenerator.sortFiguresByVerticalPosition(docFigures);

		// add the body of the comment
		body.append("<p>" + comment.getBody() + "<br/>\n");
		for (FigureFacet see : docFigures)
		{
			DocumentationPackage doc = top.locateMatching((Package) see.getSubject());
			if (doc != null)
				body.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"" + doc.getUniqueName() + ".html\">" + doc.getName()
						+ "</a><br/>\n");
		}
		body.append("</p>\n");
	}

	private static final int IMAGES_ACROSS = 4;
	private static final int MAX_THUMBNAIL_WIDTH = 225;
	private static final int MAX_BIG_WIDTH = 700;

	private void handleImageGallery(FigureFacet figure, Comment comment, StringBuilder body) throws IOException
	{
		// get any sorted child images
		if (figure.getContainerFacet() == null)
			return;
		ContainerFacet container = figure.getContainerFacet();
		java.util.List<FigureFacet> docFigures = new ArrayList<FigureFacet>();
		FigureFacet simpleContainer = container.getContents().next();
		for (Iterator<FigureFacet> iter = simpleContainer.getContainerFacet().getContents(); iter.hasNext();)
		{
			FigureFacet child = iter.next();
			Element elem = (Element) child.getSubject();
			if (elem instanceof Comment && ((Comment) elem).getBinaryFormat().startsWith("Image/")) 
				docFigures.add(child);
		}
		HTMLDocumentationGenerator.sortFiguresByVerticalPosition(docFigures);

		// add the body of the comment
		int count = 0;
		body.append("<table cellpadding=5><tr>\n");
		for (FigureFacet see : docFigures)
		{
			if (count != 0 && count % IMAGES_ACROSS == 0)
				body.append("</tr><tr>");
			
			Comment c = (Comment) see.getSubject();
			// write the large image
			String filename = "Image" + c.getUuid();
			String suffix = c.getBinaryFormat().substring("Image/".length());
			filename += "." + suffix;

			// write out the file to the images directory
			String large = "images/large_" + filename;
			writeScaledImage(large, suffix, c, MAX_BIG_WIDTH, MAX_BIG_WIDTH);
			String thumbnail = "images/thumbnail_" + filename; 
			writeScaledImage(thumbnail, suffix, c, MAX_THUMBNAIL_WIDTH, MAX_THUMBNAIL_WIDTH);
			String s = getShortComment(c);
			if (s == null)
				s = "";
			body.append("<td><a href=\"" + large + "\" rel=\"lightbox\" title=\"" + getLongComment(c) + "\">");
			body.append("<table><tr><td><img align=center valign=middle border=0 src=\"" + thumbnail + "\"/></tr><tr><td>" + s + "</td></tr></table>");
			body.append("</a></td>");
			count++;
		}
		body.append("</table>\n");
	}

	private void writeScaledImage(String filename, String format, Comment c, int maxWidth, int maxHeight) throws IOException
	{
		// by what factor do we need to scale
		double scale = 1;
		ZImage image = new ZImage(c.getBinaryData());
		Image actual = ImageConverter.toBufferedImage(image.getImage());
		double width = actual.getWidth(null);
		double height = actual.getHeight(null);
		if (width > maxWidth)
			scale = Math.min(scale, maxWidth / width);
		if (height > maxHeight)
			scale = Math.min(scale, maxHeight / height);

		File file = new File(htmlGenerateTo, filename);
    ImageIO.write(ImageConverter.toBufferedImage(actual.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_SMOOTH)), format, file);
	}

	private String getLongComment(Comment c)
	{
		String body = c.getBody();
		BufferedReader reader = new BufferedReader(new StringReader(body));
		try
		{
			reader.readLine();
			StringBuilder b = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				b.append(str + "\n");
			return b.toString();
		} catch (IOException e)
		{
			return null;
		}

	}

	private String getShortComment(Comment c)
	{
		String body = c.getBody();
		BufferedReader reader = new BufferedReader(new StringReader(body));
		try
		{
			return reader.readLine();
		} catch (IOException e)
		{
			return null;
		}
	}

	private void handleFigure(FigureFacet figure, Comment comment, StringBuilder body)
	{
		// turn the figure into an image, give it a unique name and place it in the
		// images directory
		// then add a link
		String filename = "Image" + comment.getUuid() + ".png";

		Object[] parameters =
			{ new File(htmlGenerateTo, "images"), filename };
		figure.produceEffect(coordinator, "exportPNGImage", parameters);

		// add in the image text and then the image
		body.append("<div style=\"text-align:center\">\n");
		body.append("<img style=\"padding:20px\" alt=\"\" style=\"\" src=\"images/" + filename + "\" />\n");
		if (comment.getBody().length() > 0)
			body.append("<br/>" + comment.getBody() + "<br/>\n");
		body.append("</div>\n");
	}

	private String replaceNavigation(String variable, String contents) throws HTMLGenerationException,
			PersistentFigureRecreatorNotFoundException, RepositoryPersistenceException
	{
		// form the navigation string
		final StringBuilder navigate = new StringBuilder();

		// get the 1st level parent
		final DocumentationPackage firstLevelParent = current.getFirstLevelParent();

		top.visit(new DocumentationPackageVisitor()
		{
			public void visit(DocumentationPackage visited)
			{
				int visitedLevel = visited.getLevel();
				int currentLevel = current.getLevel();
				if (visitedLevel <= 1 || // home and 1st level always visible
						visitedLevel >= 1 && visitedLevel <= currentLevel && visited.isParentedBy(firstLevelParent) || // show
						// hierarchically
						// upwards
						visited.getParent() == current) // expand children of parent
				{
					String uniq = visited.getUniqueName();
					String styleClass = "class = \"link-style" + visitedLevel + "\"";

					// make an arrow
					String arrow = "tree-nothing.gif";
					if (visited == current)
						arrow = "tree-current.gif";
					else if (visitedLevel > 0 && !visited.getChildren().isEmpty())
					{
						// add a - if this is open
						if (current.isParentedBy(visited) || visited == current)
							arrow = "tree-minus.gif";
						else
							arrow = "tree-plus.gif";
					}
					arrow = "<img alt=\"\" style=\"vertical-align:middle;border:0;\" src=\"images/" + arrow + "\" />";

					navigate.append("<tr><td " + styleClass + ">" + "<a " + styleClass + " href=\"" + uniq + ".html\">"
							+ indent(visitedLevel * 2) + arrow + "&nbsp;" + getPaddedName(visited) + "</a></td></tr>\n");
				}
			}

			private String getPaddedName(DocumentationPackage visited)
			{
				if (visited.getLevel() == 0)
					return visited.getName() + indent(padding);
				return visited.getName();
			}

			private String indent(int level)
			{
				String indent = "";
				for (int lp = 0; lp < level; lp++)
					indent += "&nbsp;";
				return indent;
			}
		});
		return contents.replace(variable, navigate.toString());
	}

	private void writeFile(File htmlGenerateTo, String uniqueName, String html) throws IOException
	{
		FileWriter writer = new FileWriter(new File(htmlGenerateTo, uniqueName + ".html"));
		BufferedWriter buffer = new BufferedWriter(writer);
		buffer.append(html);
		buffer.close();
		writer.close();
	}
}
