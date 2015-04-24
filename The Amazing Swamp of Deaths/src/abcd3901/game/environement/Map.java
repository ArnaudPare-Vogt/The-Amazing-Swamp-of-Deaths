package abcd3901.game.environement;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.graphics.Renderer;

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
			terrainData[i] = Tile.baseWater;
		}
	}
	
	public void render(Renderer ren,Point coordinate,Dimension view){
		for (int i = 0; i < view.width/Tile.TILE_SIZE; i++) {
			for (int j = 0; j < view.height/Tile.TILE_SIZE; j++) {
				
				Point p = findTileOnPoint(new Point(i*Tile.TILE_SIZE + coordinate.x,j*Tile.TILE_SIZE+coordinate.y));
				if(p!=null)
				ren.drawSprite(i*Tile.TILE_SIZE, j*Tile.TILE_SIZE, terrainData[p.x + p.y*size.width].getSprite());
				
			}
		}
	}
	
	
	
	public Point findTileOnPoint(Point point){
		Point p = new Point(point.x/Tile.TILE_SIZE,point.y/Tile.TILE_SIZE);
		if(p.x>=0&&p.y>=0&&p.x<size.width&&p.y<size.height){
			return p;
		}
		else return null;
	}
	
}
