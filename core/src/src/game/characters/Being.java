package src.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import src.game.Entity;
import src.game.graphics.Renderer;

/**
 * A simple character in the world of TASOD
 * @author Arnaud Paré-Vogt
 *
 */
public class Being extends Entity{
	
	private boolean isAlive = true;
	private Vector3 position;
	//TODO convert to drawable
	private TextureRegion image;
	
	public Being(TextureRegion image){
		this(Vector3.Zero, image);
	}
	
	public Being(Vector3 position, TextureRegion image){
		this.position = position;
		this.image = image;
	}
	
	@Override
	public void render(Renderer r) {
		r.spriteBatch.draw(image, position.x, position.y);
	}
	
	@Override
	public void update() {
		position.x += Gdx.graphics.getDeltaTime();
	}
	
	public Vector3 getPosition(){
		return position;
	}
	public boolean isAlive(){
		return isAlive;
	}
}
