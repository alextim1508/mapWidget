package com.alextim.mapTileSource;

public abstract class AbstractTMSTileSource implements TileSource {
    protected String id;
    protected String name;

    public AbstractTMSTileSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxZoom() {
        return 17;
    }

    @Override
    public int getMinZoom() {
        return 0;
    }

    public abstract String getBaseUrl();

    public String getExtension() {
        return "png";
    }

    public String getTilePath(int zoom, int tileX, int tileY) {
        return "/" + zoom + "/" + tileX + "/" + tileY + "." + getExtension();
    }

    @Override
    public String getTileUrl(int zoom, int tileX, int tileY) {
        return getBaseUrl() + getTilePath(zoom, tileX, tileY);
    }

    @Override
    public String toString() {
        return getName();
    }
}
