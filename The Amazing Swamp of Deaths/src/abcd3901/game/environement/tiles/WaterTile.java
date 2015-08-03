package abcd3901.game.environement.tiles;

import abcd3901.game.environement.TileType;

public class WaterTile extends Tile{

	int depth;
	
	public WaterTile(TileType spriteHandeler, int depth) {
		super(spriteHandeler);
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}
	
	public void fill(TileType groundType){
		
	}
	
}
