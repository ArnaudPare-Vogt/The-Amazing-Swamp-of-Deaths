package src.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class used by entities to render temselves on the screen
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class Renderer {
	public SpriteBatch spriteBatch;
	public Renderer(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
}
