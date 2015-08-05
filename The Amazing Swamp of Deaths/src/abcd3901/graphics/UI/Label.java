package abcd3901.graphics.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;

import abcd3901.graphics.Renderable;
import abcd3901.graphics.Renderer;
import abcd3901.utility.StringUtil;

/**
 * A label : a small text zone with a background
 * @author Arnaud Paré-Vogt
 *
 */
public class Label implements Renderable{
	
	private Point coordinates;
	
	private Font font;
	private Color color;
	private Color backgroundColor;
	
	private String content;
	private String[] lineContent;
	private int rows,maxRowLenght;
	
	private int padding = 10;
	
	public Label(Point coordinates, String content, Font font, Color color, Color backgroundColor) {
		this.coordinates = coordinates;
		setText(content);
		this.font = font;
		this.color = color;
		this.backgroundColor = backgroundColor;
	}
	
	public void setText(String text){
		this.content = text;
		rows = 1 + StringUtil.countOccurences(text, '\n');
		lineContent = content.split("\n");
		maxRowLenght = -1;
		
	}
	
	public Label(Point coordinates, String content){
		this(coordinates,content,Renderer.boldLargeFont,Color.WHITE, new Color(0,0,0,128));
	}

	@Override
	public void render(Renderer ren) {
		Dimension d = this.getSize(ren.getRoughGraphics().getFontMetrics(this.font));
		
		int height = ren.getRoughGraphics().getFontMetrics(this.font).getHeight();
		ren.drawRectangle(coordinates.x, coordinates.y, d.width, d.height, backgroundColor.getRGB());
		for (int i = 0; i < lineContent.length; i++) {
			ren.drawString(lineContent[i], coordinates.x + padding, coordinates.y + padding + i *  height, this.font);
		}
		
	}
	
	public Dimension getSize(FontMetrics metrics){
		if(maxRowLenght==-1){
			for (String string : lineContent) {
				int rl = metrics.stringWidth(string);
				if(rl > maxRowLenght)maxRowLenght = rl;
			}
		}
		int height = metrics.getHeight();
		return new Dimension( maxRowLenght + padding * 2, rows * metrics.getHeight() + padding * 2);
	}
	
	public String getText(){
		return content;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
