package abcd3901.game.gamemode;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import abcd3901.game.level.Level;
import abcd3901.graphics.Renderer;
import abcd3901.graphics.sprite.Sprite;
import abcd3901.io.UserInput;

public class PlayMode extends GameMode{

	private int x,y,width,height;
	
	private Level lvl01 = new Level(new Dimension(200,200));
	private Point cursor;
	
	public PlayMode(int width,int height){
		this.height=height;
		this.width=width;
		x=0;
		y=0;
	}
	
	@Override
	public void render(Renderer ren) {
		lvl01.render(ren, new Point(x,y), new Dimension(width,height));
		ren.drawString("test", 10, 20);
		if(cursor!=null){
			ren.drawSprite(cursor.x, cursor.y, Sprite.baseSelector);
		}
	}

	@Override
	public void update(UserInput in) {
		if(in.getKey(KeyEvent.VK_W))y--;
		if(in.getKey(KeyEvent.VK_S))y++;
		if(in.getKey(KeyEvent.VK_A))x--;
		if(in.getKey(KeyEvent.VK_D))x++;
		
		//if we clicked, try to select the clicked tile
		if(in.clickedThisFrame()){
			MouseEvent info = in.getLastClickInformation();
			if(info.getButton() == MouseEvent.BUTTON1){
				lvl01.selectTile(info.getX()+x, info.getY()+y);
			}else if(info.getButton() == MouseEvent.BUTTON3){
				lvl01.delelectTile();
			}
		}
		//
		
		//try to move, and check to see if we are out of bounds
		this.x -= in.getMouseDeltaDragged().width;
		this.y -= in.getMouseDeltaDragged().height;
		
		if(x<0)x=0;
		if(y<0)y=0;
		if(x>(lvl01.getSizeInPixels().width-width))x=(lvl01.getSizeInPixels().width-width);
		if(y>(lvl01.getSizeInPixels().height-height))y=(lvl01.getSizeInPixels().height-height);
		//
		
	}

	@Override
	public void resize(Dimension newSize) {
		this.width = newSize.width;
		this.height = newSize.height;
	}

}
