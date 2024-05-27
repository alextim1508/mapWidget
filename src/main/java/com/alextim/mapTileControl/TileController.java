package com.alextim.mapTileControl;

import com.alextim.mapTileCache.MapTileCache;
import com.alextim.mapTileCache.MemoryMapTileCache;
import com.alextim.mapTileMask.TileMask;
import com.alextim.mapTileSource.TileSource;

public class TileController {

    protected MapTileCache cache;
    protected TileSource source;

    protected TileMask mask;

    public TileController(TileSource source) {
        this(source, new MemoryMapTileCache(), null);
    }

    public TileController(TileSource source, MapTileCache cache) {
        this(source, cache, null);
    }

    public TileController(TileSource source, MapTileCache cache, TileMask mask) {
        this.source = source;
        this.cache = cache;
        this.mask = mask;
    }

    public void setCache(MapTileCache cache) {
        this.cache = cache;
    }

    public void setSource(TileSource source) {
        this.source = source;
    }

    public void setMask(TileMask mask) {
        this.mask = mask;
    }

    protected MapTile getTile(int tileX, int tileY, int zoom) {
        int max = 1 << zoom;
        if (tileX < 0 || tileX >= max || tileY < 0 || tileY >= max)
            return null;

        MapTile tile = cache.getTile(source, tileX, tileY, zoom);
        if (tile == null) {
            tile = new MapTile(source, tileX, tileY, zoom, mask);
            cache.addTile(tile);
        }
        return tile;
    }
}
