package main.scripts;

import engine.maths.Vector;
import engine.objects.GameObject;
import engine.objects.Tile;
import engine.objects.TileData;
import engine.objects.TileMap;
import main.Main;

import java.util.Arrays;

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
                new TileData(new Vector(-2, -2), 0),
                new TileData(new Vector(-1, -2), 1),
                new TileData(new Vector(0, -2), 2),
                new TileData(new Vector(1, -2), 1),
                new TileData(new Vector(2, -2), 3),
                new TileData(new Vector(3, -1), 4),
                new TileData(new Vector(3, 0), 5)
        );
    }
}
