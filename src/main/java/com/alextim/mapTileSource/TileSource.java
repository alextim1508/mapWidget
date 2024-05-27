package com.alextim.mapTileSource;

public interface TileSource {
    String getId();
    String getName();

    String getTileUrl(int zoom, int tileX, int tileY);

    int getMaxZoom();
    int getMinZoom();
}
