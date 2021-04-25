package engine.objects;

import engine.maths.Vector;

import java.util.HashSet;
import java.util.Set;

/**
 * The component that represents a tile map.
 *
 * @author Bailey
 */

@SuppressWarnings("unused")
public abstract class TileMap {
    public Vector scale;
    public Set<Tile> tiles = new HashSet<>();
    private final int layer;
    private final SpriteSheet spriteSheet;

    /**
     * The constructor for a tile map.
     * @param width The horizontal that each cell with have when displayed on the window.
     * @param height The vertical that each cell with have when displayed on the window.
     * @param layer The layer to render the tiles on.
     * @param sheet The path to the image of the sprite sheet that the tile map uses.
     * @param cellw The pixel width of each sprite in the sprite sheet.
     * @param cellh The pixel height of each sprite in the sprite sheet.
     * @param marginw The width in pixels of the margin between each sprite in the sprite sheet.
     * @param marginh The height in pixels of the margin between each sprite in the sprite sheet.
     */
    public TileMap(int width, int height, int layer, String sheet, int cellw, int cellh, int marginw, int marginh) {
        this.scale = new Vector(width, height);
        this.layer = layer;
        spriteSheet = new SpriteSheet(sheet, cellw, cellh, marginw, marginh);
        spriteSheet.gen();
    }

    /**
     * Adds a single tile to the tile map.
     * @param cellPosition The cell that tile will be in.
     * @param id The identifier that the tile is sprite in the spriteSheet is based on. You can get this by counting left to right and top to bottom across the sprites in the spriteSheet.
     */
    public void addTile(Vector cellPosition, int id) {
        tiles.add(new Tile(cellPosition, scale, layer,spriteSheet.getSprite(id)));
    }

    /**
     * Adds a single tile to the tile map.
     * @param data The data of the tile you want to add.
     */
    public void addTile(TileData data) {
        tiles.add(new Tile(data.position, scale, layer, spriteSheet.getSprite(data.id)));
    }

    /**
     * Adds multiple or a single tile to the tile map.
     * @param tileData The list of data of the tiles that you want to add
     */
    public void addTiles(TileData... tileData) {
        for (TileData data : tileData) tiles.add(new Tile(data.position, scale, layer, spriteSheet.getSprite(data.id)));
    }
}
