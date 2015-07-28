package abcd3901.graphics.sprite;

import abcd3901.graphics.GraphicUtility;
import abcd3901.graphics.SpriteSheet;

public class Sprite {
	
	public static Sprite base_water = new Sprite(SpriteSheet.baseSheet,0,0,16,16);
	public static MetaSprite base_island = SpriteFactory.getConnectectedTextures(SpriteSheet.baseSheet, 16);
	
	public static final Sprite baseSelector = new Sprite(SpriteSheet.mainRandomSprites,32,0,16,16);
	
	private int[] data;
	private int width,height;
	
	/**
	 * Creates a new Sprite from the given SpriteSheet. The constructor
	 * makes use  of the method cut of SpriteSheet.
	 * 
	 * @see abcd3901.graphics.SpriteSheet#cut(int, int, int, int)
	 * 
	 * @param ss the SpriteSheet that contains the sprite
	 * @param xPos the x position of the sprite in the SpriteSheet (in pixels)
	 * @param yPos the y position of the sprite in the SpriteSheet (in pixels)
	 * @param xSize the width of the sprite (in pixels)
	 * @param ySize the height of the sprite (in pixels
	 */
	public Sprite(SpriteSheet ss, int xPos,int yPos,int xSize,int ySize){
		width = xSize;
		height = ySize;
		data = ss.cut(xPos, yPos, xSize, ySize);
		if(data==null){
			System.out.println("Whoooa!");
		}
	}
	
	protected Sprite(Sprite source){
		data = new int[source.data.length];
		for (int i = 0; i < data.length; i++) {
			this.data[i] = source.data[i];
		}
		this.width = source.width;
		this.height = source.height;
	}
	
	public int getPixel(int x,int y){
		if(x<0||y<0||x>=width||y>=height){
			throw new IllegalArgumentException("The pixel has to be between the boundries of the sprite");
		}
		return data[x+y*width];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Sprite getSprite(){
		return this;
	}
	
	public Sprite getSprite(int meta){
		return this;
	}
	
	public Sprite darken(double alphaPercentage){
		Sprite result = new Sprite(this);
		
		int[] pix = result.data;
		for (int i = 0; i < pix.length; i++) {
			pix[i] = GraphicUtility.alphaBlend(((int)alphaPercentage*0xff)<<24, pix[i]);
		}
		
		return result;
	}
	
	/**
	 * rotates this sprite 90 degrees counter-clockwise (PI/2 rad)
	 */
	public void rotateLeft(){
		int[] dataOld = new int[data.length];
		for (int i = 0; i < dataOld.length; i++) {
			dataOld[i] = data[i];
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rx = width-1-x;
				data[(y) + (rx) * width] = dataOld[x+y*width];
			}
		}
	}
	
	/**
	 * Flips this sprite on the x axis.
	 */
	public void flipHorizontally(){
		int[] dataOld = new int[data.length];
		for (int i = 0; i < dataOld.length; i++) {
			dataOld[i] = data[i];
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				data[x + (height-1 - y) * width] = dataOld[x+y*width];
			}
		}
	}

}
