package abcd3901.graphics;

public class Sprite {

	private int[] data;
	private int width,height;
	
	public Sprite(SpriteSheet ss, int xPos,int yPos,int xSize,int ySize){
		width = xSize;
		height = ySize;
		data = ss.cut(xPos, yPos, xSize, ySize);
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
}
