package abcd3901.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener{

	private static final int KEY_LIMIT = 1024;
	private boolean [] keys;
	
	
	public UserInput(){
		keys = new boolean[KEY_LIMIT];
	}
	
	/**
	 * Used to know if a key is pressed at the moment.
	 * @param keyIndex the index of the key (same as KeyEvent.VK_)
	 * @return if the key is pressed or not
	 */
	public boolean getKey(int keyIndex){
		return keys[keyIndex];
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

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
	
	

}
