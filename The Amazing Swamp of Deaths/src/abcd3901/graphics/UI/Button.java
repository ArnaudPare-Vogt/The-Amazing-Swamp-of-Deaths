package abcd3901.graphics.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;

import abcd3901.graphics.Renderable;
import abcd3901.graphics.Renderer;

/**
 * A simplistic button
 * @author Arnaud Paré-Vogt
 *
 */
public class Button implements Renderable,Clickable{
	
	private Label text;
	private Color borderColor;
	
	private Color bgColor;
	private Color bgColorHover;
	
	private Point coordinates;
	private FontMetrics context;
	
	private Runnable action;
	
	public Button(Point coordinates, String text){
		this(coordinates, text, new Color(0,0,0,128), new Color(0,0,0,200));
	}
	
	public Button(Point coordinates, String text,Color backgroundColor, Color backgroundColorHover){
		this.coordinates = coordinates;
		this.text = new Label(coordinates, text);
		this.bgColor = backgroundColor;
		this.bgColorHover = backgroundColorHover;
	}

	@Override
	public void render(Renderer ren) {
		context = ren.getRoughGraphics().getFontMetrics();
		text.render(ren);
	}

	public boolean isOnBounds(Point pos) {
		if(context == null){
			return false;
		}
		Dimension size = text.getSize(context);
		int x = pos.x - coordinates.x;
		int y = pos.y - coordinates.y;
		return (x>0 && y > 0 && x < size.width && y < size.height);
	}



	@Override
	public boolean clicked(Point pos) {
		if(isOnBounds(pos)){
			if(action!=null){
				action.run();
			}
			return false;
		}
		return true;
	}
	
	public void setAction(Runnable action) {
		this.action = action;
	}

	@Override
	public void updateState(Point pos) {
		if(isOnBounds(pos)){
			text.setBackgroundColor(bgColorHover);
		}else{
			text.setBackgroundColor(bgColor);
		}
	}
	
}
