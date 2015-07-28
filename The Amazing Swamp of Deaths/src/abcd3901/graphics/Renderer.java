package abcd3901.graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import abcd3901.graphics.sprite.Sprite;

public class Renderer {
	
	private BufferedImage image;
	private Graphics2D roughGraphics;
	private int[] pixels;
	int width,height;
	
	private static Font boldLargeFont = new Font("Monospace", Font.BOLD, 20);
	
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		roughGraphics = (Graphics2D)image.getGraphics();
		roughGraphics.setFont(boldLargeFont);
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
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void drawSprite(int x,int y,Sprite s){
		for (int i = 0; i < s.getWidth(); i++) {
			for (int j = 0; j < s.getHeight(); j++) {
				drawPixel((i+x),(y+j),s.getSprite().getPixel(i, j));
			}
		}
	}
	
	public void drawString(String str,int x,int y){
		roughGraphics.drawString(str, x, y);
	}
	
	/**
	 * Used to change the size of the renderer
	 */
	public void resize(Dimension newSize){
		width = newSize.width;
		height = newSize.height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		roughGraphics = (Graphics2D)image.getGraphics();
		roughGraphics.setFont(boldLargeFont);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
}
