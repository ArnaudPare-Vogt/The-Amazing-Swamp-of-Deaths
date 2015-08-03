package abcd3901.game.environement;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.game.environement.generation.MapFactory;
import abcd3901.game.environement.tiles.Tile;
import abcd3901.graphics.Renderer;

/**
 * A simple representation of the world, that contains all the data needed to
 * play
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class Map {

	private Dimension size;

	private Tile[] terrainData;

	public Map(Dimension size) {
		this.size = size;
		terrainData = new Tile[size.height * size.width];

		MapFactory mf = new MapFactory(System.nanoTime());
		terrainData = mf.getTerrainLinear(size.width, size.height);

	}

	public void render(Renderer ren, Point coordinate, Dimension view) {
		int xDecal = -(coordinate.x%TileType.TILE_SIZE);
		int yDecal = -(coordinate.y%TileType.TILE_SIZE);
		
		for (int i = 0; i < view.width / TileType.TILE_SIZE +1; i++) {
			for (int j = 0; j < view.height / TileType.TILE_SIZE +2; j++) {
				Point p = findTileOnPoint(new Point(i * TileType.TILE_SIZE
						+ coordinate.x, j * TileType.TILE_SIZE + coordinate.y));
				if (p != null) {
					ren.drawSprite(i * TileType.TILE_SIZE + xDecal, j * TileType.TILE_SIZE + yDecal,
							terrainData[p.x + p.y * size.width]
									.getSpriteHandeler().getSprite(getAdjacentTiles(p)));
				}
			}
		}
	}

	private TileType[] getAdjacentTiles(Point p) {
		TileType[] tiles = new TileType[8];
		tiles[0] = getTileHandeler(new Point(p.x - 1, p.y - 1));
		tiles[1] = getTileHandeler(new Point(p.x, p.y - 1));
		tiles[2] = getTileHandeler(new Point(p.x + 1, p.y - 1));
		tiles[3] = getTileHandeler(new Point(p.x - 1, p.y));
		tiles[4] = getTileHandeler(new Point(p.x + 1, p.y));
		tiles[5] = getTileHandeler(new Point(p.x - 1, p.y + 1));
		tiles[6] = getTileHandeler(new Point(p.x, p.y + 1));
		tiles[7] = getTileHandeler(new Point(p.x + 1, p.y + 1));
		return tiles;
	}

	public TileType getTileHandeler(Point p) {
		if (p.x >= 0 && p.y >= 0 && p.x < size.width && p.y < size.height) {
			return terrainData[p.x + p.y * size.width].getSpriteHandeler();
		} else {
			return TileType.baseWater;
		}
	}
	
	public Tile getTile(Point p){
		if (p.x >= 0 && p.y >= 0 && p.x < size.width && p.y < size.height) {
			return terrainData[p.x + p.y * size.width];
		} else {
			return null;
		}
	}

	/**
	 * Gives a point representing the x and y coordinates of the tile located under the given point.
	 * @param point the point where to look for for the tile (in pixels)
	 * @return the point representing the coordinates of the found tile
	 */
	public Point findTileOnPoint(Point point) {
		Point p = new Point(point.x / TileType.TILE_SIZE, point.y / TileType.TILE_SIZE);
		if (point.x >= 0 && point.y >= 0 && p.x < size.width && p.y < size.height) {
			return p;
		} else
			return null;
	}

}
