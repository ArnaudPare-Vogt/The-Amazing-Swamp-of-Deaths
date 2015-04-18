package abcd3901.main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import abcd3901.game.gamemode.GameMode;
import abcd3901.game.gamemode.PlayMode;
import abcd3901.graphics.Renderer;
import abcd3901.io.UserInput;
import abcd3901.utility.exception.ExceptionLog;

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
	
	//control stuff
	UserInput in;
	
	//tepm stuff
	GameMode m = new PlayMode(800, 600);
	
	public Component(JFrame parent){
		this.setPreferredSize(size);
		initGraphics();
		initThread();
		initInput(parent);
	}
	
	private void initGraphics(){
		renderer = new Renderer(size.width,size.height);
	}
	
	private void initThread(){
		gameLoop = new Thread(this,"TASOD_mainGameLoop");
	}
	
	private void initInput(JFrame parent){
		in = new UserInput();
		parent.addKeyListener(in);
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
				update();
				
				//render
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void update(){
		m.update(in);
	}
	
	@Override
	public void paintComponent(Graphics g){
		renderer.clearImage();
		m.render(renderer);
		g.drawImage(renderer.getImage(), 0, 0, size.width*pixelSize,size.height*pixelSize,null);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Component component = new Component(frame);
		
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
