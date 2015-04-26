package abcd3901.graphics;

public class Sprite {

	public static final String ERROR_MESSAGE_WRONG_SPRITESHEET_SIZE = "The sprite sheet has the size of %s, it should be ";
	
	public static Sprite base_water = new Sprite(SpriteSheet.baseSheet,0,0,16,16);
	public static Sprite base_island = new Sprite(SpriteSheet.baseSheet,16,0,16,16);
	
	
	
	private int[] data;
	private int width,height;
	
	public Sprite(SpriteSheet ss, int xPos,int yPos,int xSize,int ySize){
		width = xSize;
		height = ySize;
		data = ss.cut(xPos, yPos, xSize, ySize);
	}
	
	private Sprite(Sprite source){
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
	
	public Sprite darken(double alphaPercentage){
		Sprite result = new Sprite(this);
		
		int[] pix = result.data;
		for (int i = 0; i < pix.length; i++) {
			pix[i] = GraphicUtility.alphaBlend(((int)alphaPercentage*0xff)<<24, pix[i]);
		}
		
		return result;
	}
	
	public Sprite[] getConnectedTexturesBase(SpriteSheet ss,int res){
		if(ss.getSize() != 8*res){
			throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_SPRITESHEET_SIZE.replace("%s", ""+ss.getSize()) + 8*res);
		}
		
		Sprite[] baseTiles = new Sprite[13];
		for (int i = 0; i < baseTiles.length; i++) {
			int x = i%8;
			int y = i/8;
			baseTiles[i] = new Sprite(ss, x*res, y*res*4, res, res);
		}
		
		return baseTiles;
	}
	
}
