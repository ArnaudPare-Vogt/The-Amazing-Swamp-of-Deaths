package abcd3901.game.environement.generation;

import java.util.Random;

import abcd3901.game.environement.TileType;
import abcd3901.game.environement.tiles.Tile;
import abcd3901.game.environement.tiles.WaterTile;

public class MapFactory {
	
	public static final TileType[] baseTiles = {TileType.baseWater,TileType.baseGround};
	
	private Random rng;
	
	public MapFactory(long seed){
		rng = new Random(seed);	
	}
	
	public Tile[][] getTerrain(int width,int lenght){
		Tile[][] data = new Tile[width][lenght];
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				int num = rng.nextInt(baseTiles.length);
				TileType type = baseTiles[num];
				if(type.isGround()){
					data[i][j] = new Tile(baseTiles[num]);
				}else{
					data[i][j] = new WaterTile(baseTiles[num],rng.nextInt(9)+1);
				}
			}
		}
		
		return data;
	}

	public Tile[] getTerrainLinear(int width,int lenght){
		Tile[][] iiDData = getTerrain(width, lenght);
		Tile[] iDData = new Tile[width*lenght];
		for (int i = 0; i < iiDData.length; i++) {
			for (int j = 0; j < iiDData[i].length; j++) {
				iDData[i+j*width] = iiDData[i][j];
			}
		}
		return iDData;
	}
	
}
