package abcd3901.game.gamemode;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;

import abcd3901.game.environement.Map;
import abcd3901.graphics.Renderer;
import abcd3901.io.UserInput;

public class PlayMode extends GameMode{

	private int x,y,width,height;
	
	private Map lvl01 = new Map(new Dimension(1000,1000));
	
	public PlayMode(int width,int height){
		this.height=height;
		this.width=width;
		x=0;
		y=0;
	}
	
	@Override
	public void render(Renderer ren) {
		lvl01.render(ren, new Point(x,y), new Dimension(width,height));
	}

	@Override
	public void update(UserInput in) {
		if(in.getKey(KeyEvent.VK_W))y--;
		if(in.getKey(KeyEvent.VK_S))y++;
		if(in.getKey(KeyEvent.VK_A))x--;
		if(in.getKey(KeyEvent.VK_D))x++;
		
		this.x += in.getMouseDeltaDragged().width;
		this.y += in.getMouseDeltaDragged().height;
	}

}
