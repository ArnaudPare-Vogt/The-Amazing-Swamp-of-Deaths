package abcd3901.game.gamemode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import abcd3901.graphics.Renderable;
import abcd3901.graphics.Renderer;
import abcd3901.graphics.SpriteSheet;
import abcd3901.graphics.UI.Button;
import abcd3901.graphics.UI.Clickable;
import abcd3901.graphics.UI.Label;
import abcd3901.io.UserInput;

public class MenuMode extends GameMode{

	private Dimension currentSize;
	
	private GameModeContainer modeChanger;
	
	private List<Renderable> guiItems;
	private List<Clickable> guiInteractives;
	
	private BufferedImage bgImage;
	public static final String bgImagePath = "/graphics/images/titleScreenImage.png";
	
	public MenuMode(Dimension currentSize, GameModeContainer modeChanger){
		this.currentSize = currentSize;
		this.modeChanger = modeChanger;
		try{
			bgImage = ImageIO.read(SpriteSheet.class.getResource(bgImagePath));
		}catch(IOException|IllegalArgumentException exe){
			System.out.println("Image reading failed!");
		}
		initGUI();
	}
	
	private void initGUI(){
		guiItems = new ArrayList<Renderable>();
		guiInteractives = new ArrayList<Clickable>();
		
		Button tempbtn;
		Label tempLabel;
		tempbtn = new Button(new Point(100,200), "Play");
		tempbtn.setAction(()->{
			modeChanger.changeMode(1);
		});
		guiItems.add(tempbtn);
		guiInteractives.add(tempbtn);
		
		tempLabel = new Label(new Point(0,0),"The Amaizing Swamp Of Death",
				new Font("Courrier", Font.BOLD, 40),Color.WHITE,
				new Color(0,0,0,128));
		guiItems.add(tempLabel);
	}
	
	@Override
	public void render(Renderer ren) {
		if(bgImage!=null){
			ren.getRoughGraphics().drawImage(bgImage, 0, 0, currentSize.width, currentSize.height, null);
		}else{
			ren.drawRectangle(0, 0, currentSize.width, currentSize.height, 0xff00ffcc);
		}
		for (Renderable renderable : guiItems) {
			renderable.render(ren);
		}
	}

	@Override
	public void update(UserInput in) {
		Point pos = in.getMousePoint();
		for (Clickable clickable : guiInteractives) {
			clickable.updateState(pos);
		}
		if(in.clickedThisFrame()){
			for (Clickable clickable : guiInteractives) {
				if(clickable.clicked(pos))break;
			}
		}
		
	}

	@Override
	public void resize(Dimension newSize) {
		currentSize = newSize;
	}
	
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

}
