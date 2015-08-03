package abcd3901.game.environement.tiles;

import abcd3901.game.environement.TileType;

/**
 * A simplistic representation of a chunk of terrain.
 * @author Arnaud Paré-Vogt
 *
 */
public class Tile {

	private TileType spriteHandeler;
	
	public Tile(TileType spriteHandeler) {
		this.spriteHandeler = spriteHandeler;
	}
	
	public TileType getSpriteHandeler() {
		return spriteHandeler;
	}

}
