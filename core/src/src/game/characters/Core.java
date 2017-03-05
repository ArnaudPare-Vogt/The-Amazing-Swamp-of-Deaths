package src.game.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * The core aka the player
 * @author Arnaud paré-Vogt
 *
 */
public class Core extends Being{

	public Core(Vector3 position, TextureRegion image) {
		super(position, image);
	}
	
	@Override
	public void update() {
	}
}
