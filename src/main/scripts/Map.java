package main.scripts;

import engine.maths.Vector2;
import engine.objects.TileData;
import engine.objects.TileMap;

/**
 * My tile map.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Map extends TileMap {
    public Map() {
        super(1, 1, 1, "tileSet", 16, 16, 1, 1);

        addTiles(
                new TileData(new Vector2(-2), 0),
                new TileData(new Vector2(-1, -2), 1),
                new TileData(new Vector2(0, -2), 2),
                new TileData(new Vector2(1, -2), 1),
                new TileData(new Vector2(2, -2), 3),
                new TileData(new Vector2(3, -1), 4),
                new TileData(new Vector2(3, 0), 5)
        );
    }
}
