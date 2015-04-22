package abcd3901.game.environement;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.graphics.Renderer;
import abcd3901.graphics.Sprite;

/**
 * A simple representation of the world, that contains all the data needed to play
 * @author Arnaud Paré-Vogt
 *
 */
public class Map {

	private Dimension size;
	
	private Tile[] terrainData;
	
	public Map(Dimension size){
		this.size = size;
		terrainData = new Tile[size.height*size.width];
		generateBaseTerrain();
	}
	
	private void generateBaseTerrain(){
		for (int i = 0; i < terrainData.length; i++) {
			terrainData[i] = Tile.baseTile;
		}
	}
	
	public void render(Renderer ren,Point coordinate,Dimension view){
		for (int i = 0; i < view.width; i+=16) {
			for (int j = 0; j < view.height; j+=16) {
				int tileX = (coordinate.x+view.width)/Tile.TILE_SIZE;
				int tileY = (coordinate.y+view.height)/Tile.TILE_SIZE;
				
				ren.drawSprite(i, j, terrainData[tileX + tileY*size.width].getSprite());
				
			}
		}
	}
	
}
