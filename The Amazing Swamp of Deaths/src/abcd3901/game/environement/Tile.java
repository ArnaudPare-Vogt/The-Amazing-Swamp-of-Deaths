package abcd3901.game.environement;

import abcd3901.graphics.Sprite;

/**
 * A simplistic representation of a chunk of terrain
 * @author Arnaud Paré-Vogt
 *
 */
public class Tile {
	
	
	Sprite sprite;
	
	public Tile(Sprite s){
		this.sprite=s;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}
