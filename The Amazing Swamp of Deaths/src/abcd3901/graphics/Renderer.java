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
	
	public static Font boldLargeFont = new Font("Courier New", Font.BOLD, 20);
	
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
	
	/**
	 * Draws a rectangle on the renderer on the givan color.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawRectangle(int x, int y, int width, int height, int color){
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				drawPixel(x+i, y+j, color);
			}
		}
	}
	
	public void drawString(String str,int x,int y){
		roughGraphics.drawString(str, x, y + roughGraphics.getFontMetrics().getAscent());
	}
	
	public void drawString(String str,int x,int y, Font font){
		Font f = roughGraphics.getFont();
		roughGraphics.setFont(font);
		drawString(str, x, y);
		roughGraphics.setFont(f);
	}
	
	public Font getFont(){
		return roughGraphics.getFont();
	}
	
	public Graphics2D getRoughGraphics() {
		return roughGraphics;
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
