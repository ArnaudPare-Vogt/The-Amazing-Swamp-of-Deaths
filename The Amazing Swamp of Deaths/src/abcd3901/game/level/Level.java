package abcd3901.game.level;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.game.environement.Map;
import abcd3901.game.environement.Tile;
import abcd3901.graphics.Renderer;
import abcd3901.graphics.sprite.Sprite;

/**
 * Contains information on the current level of the game, including the map, characters and other stuff.
 * @author Arnaud Paré-Vogt
 *
 */
public class Level {
	
	private Map map;	
	private Point selectedTileCoords;
	
	private Dimension tileSize;
	private Dimension pixelSize;
	
	public Level(Dimension size) {
		map = new Map(size);
		tileSize = size;
		pixelSize = new Dimension(tileSize.width*Tile.TILE_SIZE, tileSize.height*Tile.TILE_SIZE);
	}
	
	public void render(Renderer canvas, Point coordinates, Dimension viewport){
		map.render(canvas, coordinates, viewport);
		if(selectedTileCoords!=null){
			canvas.drawSprite((selectedTileCoords.x * Tile.TILE_SIZE) - coordinates.x, (selectedTileCoords.y * Tile.TILE_SIZE) - coordinates.y, Sprite.baseSelector);
		}
	}
	
	/**
	 * Gives the size of the level in pixels. The given value is pre-calculated, and is equal to tileSize * Tile.TILESIZE.
	 * @return the size of the level in pixels
	 */
	public Dimension getSizeInPixels(){
		return pixelSize;
	}
	
	
	public void selectTile(int pixelPosX, int pixelPosY){
		selectedTileCoords = new Point(pixelPosX/Tile.TILE_SIZE,pixelPosY/Tile.TILE_SIZE);
	}
}
