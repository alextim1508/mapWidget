package com.alextim.mapTileCache;

import com.alextim.mapTileControl.MapTile;
import com.alextim.mapTileSource.TileSource;

public interface MapTileCache {

	MapTile getTile(TileSource source, int x, int y, int z);

    void addTile(MapTile tile);

    int getTileCount();

    void clear();

    int getCacheSize();
}
