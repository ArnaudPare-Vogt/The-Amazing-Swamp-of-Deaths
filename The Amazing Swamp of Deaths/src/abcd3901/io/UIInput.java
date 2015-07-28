package abcd3901.io;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * This is a class that contains a bunch of information concerning UI events
 * that may be useful to the application, such as resizing and minimizing.
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class UIInput implements ComponentListener{

	private boolean wasResizedThisFrame;
	private Dimension actualSize;
	
	private Component trackedComponent;
	
	
	public UIInput(Component trackedComponent) {
		this.trackedComponent = trackedComponent;
		wasResizedThisFrame = false;
	}
	
	public Dimension getActualSize() {
		return actualSize;
	}
	
	public boolean wasResizedThisFrame(){
		return wasResizedThisFrame;
	}
	
	/**
	 * Called once by frame inside The Loop to reset all events.
	 */
	public void reset(){
		wasResizedThisFrame = false;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		if(e.getComponent()==trackedComponent){
			actualSize = trackedComponent.getSize();
			wasResizedThisFrame = true;
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		//useless
	}

	@Override
	public void componentShown(ComponentEvent e) {
		//May be useful later
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		//May be useful later
	}
	
	
	
}
