package abcd3901.graphics.sprite;

import abcd3901.graphics.GraphicUtility;
import abcd3901.graphics.SpriteSheet;

public class Sprite {
	
	public static Sprite base_water = new Sprite(SpriteSheet.baseSheet,0,0,16,16);
	public static Sprite base_island = new Sprite(SpriteSheet.baseSheet,16,0,16,16);
	
	
	
	private int[] data;
	private int width,height;
	
	public Sprite(SpriteSheet ss, int xPos,int yPos,int xSize,int ySize){
		width = xSize;
		height = ySize;
		data = ss.cut(xPos, yPos, xSize, ySize);
	}
	
	protected Sprite(Sprite source){
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
	
}
