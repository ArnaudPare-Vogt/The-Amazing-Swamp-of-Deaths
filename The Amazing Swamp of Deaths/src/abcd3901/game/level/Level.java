package abcd3901.game.level;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.game.environement.Map;
import abcd3901.game.environement.TileType;
import abcd3901.game.environement.tiles.Tile;
import abcd3901.game.environement.tiles.WaterTile;
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
		pixelSize = new Dimension(tileSize.width*TileType.TILE_SIZE, tileSize.height*TileType.TILE_SIZE);
	}
	
	public void render(Renderer canvas, Point coordinates, Dimension viewport){
		map.render(canvas, coordinates, viewport);
		if(selectedTileCoords!=null){
			canvas.drawSprite((selectedTileCoords.x * TileType.TILE_SIZE) - coordinates.x, (selectedTileCoords.y * TileType.TILE_SIZE) - coordinates.y, Sprite.baseSelector);
			Tile t = map.getTile(selectedTileCoords);
			if(t!=null){
				canvas.drawString(t.getSpriteHandeler().getName(), 10, 50);
				if(t instanceof WaterTile){
					canvas.drawString("deapth : " + ((WaterTile)t).getDepth(), 10, 75);
				}
			}
		}
	}
	
	/**
	 * Gives the size of the level in pixels. The given value is pre-calculated, and is equal to tileSize * Tile.TILESIZE.
	 * @return the size of the level in pixels
	 */
	public Dimension getSizeInPixels(){
		return pixelSize;
	}
	
	/**
	 * Selects a tile at the corresponding pixel coordinates.
	 * @param pixelPosX the x position of the pixel
	 * @param pixelPosY the y position of the pixel
	 */
	public void selectTile(int pixelPosX, int pixelPosY){
		selectedTileCoords = new Point(pixelPosX/TileType.TILE_SIZE,pixelPosY/TileType.TILE_SIZE);
	}
	
	/**
	 * Deselects the tile
	 */
	public void delelectTile(){
		selectedTileCoords = null;
	}
}
