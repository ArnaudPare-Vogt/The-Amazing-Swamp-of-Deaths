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
	}
	
	public void render(Renderer ren,Point coordinate,Dimension view){
		
	}
	
}
