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
	
	/**
	 * Used by the constructor to load an image
	 * @param path the path of the image
	 */
	private void loadImage(String path){
		try{
			img = ImageIO.read(SpriteSheet.class.getResource(path));
		}catch(IOException exe){
			ExceptionLog.log(exe);
		}
		if(img.getHeight()!=size || img.getWidth()!=size){
			ExceptionLog.log(new IOException("wrong file for "+path+" the size is not the one expected."));
		}
	}
	
	/**
	 * Returns an array of ARGB integer pixels representing the bounds given
	 * @param x the upper left corner x coordinate
	 * @param y the upper left corner y coordinate
	 * @param width the width of the zone
	 * @param height the height of the zone
	 * @return an array of integers ARGB pixels representing the current zone. The pixels are ordered by columns (the first pixel id (0,0), the .
	 */
	public int[] cut(int x,int y, int width,int height){
		if(x<0||y<0||x+width>=size||y+height>=size)
			throw new IllegalArgumentException("The given bounds have to be within the spriteSheet");
		int[] data = new int[width*height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				data[i+j*width] = img.getRGB(x+i, y+j);
			}
		}
		return data;
	}
	
	
	
}
