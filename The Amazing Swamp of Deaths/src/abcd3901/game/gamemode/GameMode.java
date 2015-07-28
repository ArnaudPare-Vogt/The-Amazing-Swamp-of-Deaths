package abcd3901.game.gamemode;

import java.awt.Dimension;

import abcd3901.graphics.Renderer;
import abcd3901.io.UserInput;

public abstract class GameMode {

	public abstract void render(Renderer ren);
	public abstract void update(UserInput in);
	
	/**
	 * Abstract method called when the UI resizes.
	 * @param newSize The new size of the UI
	 */
	public abstract void resize(Dimension newSize);
	
}
