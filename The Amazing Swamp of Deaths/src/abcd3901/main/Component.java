package abcd3901.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import abcd3901.graphics.Renderer;
import abcd3901.graphics.Sprite;
import abcd3901.graphics.SpriteSheet;
import abcd3901.utility.exception.ExceptionLog;
import abcd3901.utility.math.PerlinNoise;

/**
 * The main class.
 * @author Arnaud Paré-Vogt
 *
 */
public class Component extends JPanel implements Runnable{
	public static final long serialVersionUID = 1l;

	public static final String TITLE = "The Amazing Swamp of Deaths";
	public static final String VERSION = "0.0.1-alpha";
	
	//thread stuff
	private boolean isRunning = false;
	private Thread gameLoop;
	private double fps = 60;
	
	//size stuff
	private Dimension size = new Dimension(800,600);
	private int pixelSize = 1;
	
	//graphics stuff
	private Renderer renderer;
	
	
	//tepm stuff
	PerlinNoise pn1 = new PerlinNoise(40,20, 100, new Random(),0.5,4);
	String path1 = "/graphics/spriteSheet/SpriteSheet1.png";
	SpriteSheet ss = new SpriteSheet(path1, 128);
	
	public Component(){
		this.setPreferredSize(size);
		initGraphics();
		initThread();
	}
	
	private void initGraphics(){
		renderer = new Renderer(size.width,size.height);
	}
	
	private void initThread(){
		gameLoop = new Thread(this,"TASOD_mainGameLoop");
	}
	
	public void start(){
		isRunning = true;
		gameLoop.start();
	}
	
	public void stop(){
		isRunning = false;
	}

	@Override
	public void run() {
		while(isRunning){
			try {
				Thread.sleep((int)(1000/fps));
				//update
				
				//render
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		renderer.clearImage();
		for (int i = 0; i < pn1.getWidth(); i++) {
			renderer.drawPixel(i, (int)pn1.getValue(i/(double)pn1.getWavelength())+50, 0x33ffff);
		}
		
		g.drawImage(renderer.getImage(), 0, 0, size.width*pixelSize,size.height*pixelSize,null);
	}
	
	public static void main(String[] args){
		Component component = new Component();
		JFrame frame = new JFrame();
		
		frame.add(component);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE+"   "+VERSION);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		component.start();
		
		ExceptionLog.showException(frame);
	}
}
