package abcd3901.game.environement;

import abcd3901.graphics.Sprite;

/**
 * A simplistic representation of a chunk of terrain
 * @author Arnaud Paré-Vogt
 *
 */
public class Tile {
	
	public static final int TILE_SIZE = 16;
	
	public static Tile baseTile = new Tile(Sprite.base_water);
	
	
	Sprite sprite;
	
	public Tile(Sprite s){
		this.sprite=s;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}
