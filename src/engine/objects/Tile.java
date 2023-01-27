package engine.objects;

import engine.maths.Vector2;
import engine.maths.Vector4;

/**
 * The preset for a tile.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public class Tile extends GameObject {
    /**
     * The constructor for the Tile game object.
     * @param cellPosition The cell that tile will be in.
     * @param scale The size of the tile. This is derived from the TileMap class that calls this constructor.
     * @param layer The layer that the tile will be on.
     * @param sprite The sprite of the tile. This is derived from the TileMap class that calls this constructor.
     */
    protected Tile(Vector2 cellPosition, Vector2 scale, int layer, Sprite sprite) {
        super(cellPosition._times(scale), scale, new Vector4(1, 1, 1, 1), layer, "block");
        this.spriteRenderer.setSprite(sprite);
    }
    
    public void Start() {}
    public void Update() {}
}
