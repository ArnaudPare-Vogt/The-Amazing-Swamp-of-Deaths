package abcd3901.game.environement.tiles;

import abcd3901.game.environement.TileType;

public class WaterTile extends Tile{

	int depth;
	
	public WaterTile(TileType spriteHandeler, int depth) {
		super(spriteHandeler);
	}

	public int getDepth() {
		return depth;
	}
	
	public void fill(TileType groundType){
		
	}
	
}
