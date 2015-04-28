package abcd3901.game.environement;

import abcd3901.graphics.sprite.Sprite;

/**
 * A simplistic representation of a chunk of terrain
 * @author Arnaud Paré-Vogt
 *
 */
public class Tile {
	
	public static final int TILE_SIZE = 16;
	
	public static Tile baseWater = new Tile(Sprite.base_water);
	public static Tile baseGround = new Tile(Sprite.base_island);
	
	Sprite sprite;
	
	public Tile(Sprite s){
		this.sprite=s;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Sprite getSprite(Tile[] adjacentTiles){
		return sprite;
	}
}
