package abcd3901.game.environement.generation;

import java.util.Random;

import abcd3901.game.environement.Tile;

public class MapFactory {
	
	public static final Tile[] baseTiles = {Tile.baseWater,Tile.baseGround};
	
	private Random rng;
	
	public MapFactory(long seed){
		rng = new Random(seed);	
	}
	
	public Tile[][] getTerrain(int width,int lenght){
		Tile[][] data = new Tile[width][lenght];
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				int num = rng.nextInt(baseTiles.length);
				data[i][j] = baseTiles[num];
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
