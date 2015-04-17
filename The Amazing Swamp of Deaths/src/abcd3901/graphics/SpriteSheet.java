package abcd3901.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import abcd3901.utility.exception.ExceptionLog;

public class SpriteSheet {
	
	
	
	private int size;
	
	private BufferedImage img;
	
	/**
	 * Creates a spriteSheet with the defined size, at the path path
	 * @param path the path of the spriteSheet
	 * @param size the size of the spriteSheet
	 */
	public SpriteSheet(String path,int size){
		this.size=size;
		loadImage(path);
	}
	
	private void loadImage(String path){
		try{
			img = ImageIO.read(SpriteSheet.class.getClassLoader().getResource(path));
		}catch(IOException exe){
			ExceptionLog.log(exe);
		}
		if(img.getHeight()!=size || img.getWidth()!=size){
			ExceptionLog.log(new IOException("wrong file for "+path+" the size is not the one expected."));
		}
	}
	
}
