package abcd3901.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
	
	private BufferedImage image;
	private int[] pixels;
	int width,height;
	
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	public void clearImage(){
		for (int i = 0; i < pixels.length; i++) {
			pixels[i]=0;
		}
	}
	
	public void drawPixel(int x, int y, int color){
		if(x>=0 && y>=0 && x<width && y<height){
			pixels[x+y*width] = GraphicUtility.alphaBlend(color,pixels[x+y*width]);
		}else{
			//System.out.println("Wrong pixels data! coords :"+x+","+y+"in a square of "+width+","+height);
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void drawSprite(int x,int y,Sprite s){
		for (int i = 0; i < s.getWidth(); i++) {
			for (int j = 0; j < s.getHeight(); j++) {
				drawPixel((i+x),(y+j),s.getPixel(i, j));
			}
		}
	}
	
}
