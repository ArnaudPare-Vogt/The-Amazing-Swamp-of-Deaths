package abcd3901.game.environement;

import java.awt.Dimension;
import java.awt.Point;

import abcd3901.game.environement.generation.MapFactory;
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
		for (int i = 0; i < view.width / Tile.TILE_SIZE; i++) {
			for (int j = 0; j < view.height / Tile.TILE_SIZE; j++) {
				Point p = findTileOnPoint(new Point(i * Tile.TILE_SIZE
						+ coordinate.x, j * Tile.TILE_SIZE + coordinate.y));
				if (p != null) {
					ren.drawSprite(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE,
							terrainData[p.x + p.y * size.width]
									.getSprite(getAdjacentTiles(p)));
				}
			}
		}
	}

	private Tile[] getAdjacentTiles(Point p) {
		Tile[] tiles = new Tile[8];
		tiles[0] = getTile(new Point(p.x - 1, p.y - 1));
		tiles[1] = getTile(new Point(p.x, p.y - 1));
		tiles[2] = getTile(new Point(p.x + 1, p.y - 1));
		tiles[3] = getTile(new Point(p.x - 1, p.y));
		tiles[4] = getTile(new Point(p.x + 1, p.y));
		tiles[5] = getTile(new Point(p.x - 1, p.y + 1));
		tiles[6] = getTile(new Point(p.x, p.y + 1));
		tiles[7] = getTile(new Point(p.x + 1, p.y + 1));
		return tiles;
	}

	private Tile getTile(Point p) {
		if (p.x >= 0 && p.y >= 0 && p.x < size.width && p.y < size.height) {
			return terrainData[p.x + p.y * size.width];
		} else {
			return Tile.baseWater;
		}
	}

	/**
	 * Gives a point representing the x and y coordinates of the tile located under the given point.
	 * @param point the point where to look for for the tile
	 * @return the point representing the coordinates of the found tile
	 */
	public Point findTileOnPoint(Point point) {
		Point p = new Point(point.x / Tile.TILE_SIZE, point.y / Tile.TILE_SIZE);
		if (point.x >= 0 && point.y >= 0 && p.x < size.width && p.y < size.height) {
			return p;
		} else
			return null;
	}

}
