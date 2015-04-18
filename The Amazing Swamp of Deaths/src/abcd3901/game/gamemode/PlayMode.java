package abcd3901.game.gamemode;

import java.awt.event.KeyEvent;

import abcd3901.graphics.Renderer;
import abcd3901.graphics.Sprite;
import abcd3901.graphics.SpriteSheet;
import abcd3901.io.UserInput;

public class PlayMode extends GameMode{

	private int x,y,width,height;
	
	String path1 = "/graphics/spriteSheet/SpriteSheet1.png";
	SpriteSheet ss = new SpriteSheet(path1, 128);
	Sprite s = new Sprite(ss,0,0,16,16);
	
	public PlayMode(int width,int height){
		this.height=height;
		this.width=width;
		x=0;
		y=0;
	}
	
	@Override
	public void render(Renderer ren) {
		ren.drawSprite(x, y, s);
	}

	@Override
	public void update(UserInput in) {
		if(in.getKey(KeyEvent.VK_W))y--;
		if(in.getKey(KeyEvent.VK_S))y++;
		if(in.getKey(KeyEvent.VK_A))x--;
		if(in.getKey(KeyEvent.VK_D))x++;
	}

}
