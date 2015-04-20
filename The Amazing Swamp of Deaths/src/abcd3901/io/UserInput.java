package abcd3901.io;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UserInput implements KeyListener,MouseListener,MouseMotionListener{

	private static final int KEY_LIMIT = 1024;
	private boolean [] keys;
	private Point mousePoint;
	private Dimension deltaMouseDragged;
	
	
	public UserInput(){
		keys = new boolean[KEY_LIMIT];
		mousePoint = new Point();
		deltaMouseDragged = new Dimension();
		clear();
	}
	
	public final void clear(){
		deltaMouseDragged.width = 0;
		deltaMouseDragged.height = 0;
	}
	
	/**
	 * Used to know if a key is pressed at the moment.
	 * @param keyIndex the index of the key (same as KeyEvent.VK_)
	 * @return if the key is pressed or not
	 */
	public boolean getKey(int keyIndex){
		return keys[keyIndex];
	}
	
	/**
	 * returns the amount of pixels the mouse was dragged on the screen
	 * @return the amount of pixels the mouse was dragged on the screen since the last clear
	 */
	public Dimension getMouseDeltaDragged(){
		return deltaMouseDragged;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if(i<KEY_LIMIT){
			keys[i]=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if(i<KEY_LIMIT){
			keys[i]=false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		deltaMouseDragged.width += e.getX()-mousePoint.x;
		deltaMouseDragged.height += e.getY()-mousePoint.y;
		
		mousePoint = e.getPoint();
	}
	


	@Override
	public void mouseMoved(MouseEvent e) {
		mousePoint = e.getPoint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
