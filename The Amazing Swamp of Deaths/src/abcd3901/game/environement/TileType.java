package abcd3901.game.environement;

import abcd3901.graphics.sprite.Sprite;

/**
 * A simplistic representation of a chunk of terrain
 * @author Arnaud Paré-Vogt
 *
 */
public class TileType {
	
	public static final int TILE_SIZE = 16;

	public static TileType baseWater = new TileType(Sprite.base_water, "water", false);
	public static TileType baseGround = new ConnectedTileType(Sprite.base_island,"earth", true);

	protected Sprite sprite;
	
	private String name;
	private boolean isGround;
	
	
	public TileType(Sprite s, String name, boolean isGround){
		this.sprite=s;
		this.name = name;
		this.isGround = isGround;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Sprite getSprite(TileType[] adjacentTiles){
		return sprite;
	}
	
	public boolean isGround(){
		return isGround;
	}
	
	public String getName() {
		return name;
	}
}
