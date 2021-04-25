package engine.objects;

import engine.maths.Vector;

/**
 * A datatype that stores the data for a tile to be added to a tile map.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class TileData {
    public Vector position;
    public int id;

    /**
     * The constructor for tile data.
     * @param position The position/cell that tile will be in.
     * @param id The identifier that the tile is sprite in the spriteSheet is based on. You can get this by counting left to right and top to bottom across the sprites in the spriteSheet.
     */
    public TileData(Vector position, int id) {
        this.position = position;
        this.id = id;
    }
}
