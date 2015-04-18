package abcd3901.game.gamemode;

import abcd3901.graphics.Renderer;
import abcd3901.io.UserInput;

public abstract class GameMode {

	public abstract void render(Renderer ren);
	public abstract void update(UserInput in);
	
}
