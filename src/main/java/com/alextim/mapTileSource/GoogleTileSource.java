package com.alextim.mapTileSource;

public class GoogleTileSource extends AbstractTMSTileSource{

    public static final String URL = "https://mt1.google.com/vt/";

    private int serverNum;

    public GoogleTileSource() {
        super("Google", "Standard Google");
    }

    @Override
    public String getBaseUrl() {
        return URL;
    }

    @Override
    public String getTilePath(int zoom, int tileX, int tileY) {
        return String.format("&lyrs=m&x=%d&y=%d&z=%d", tileX, tileY, zoom);
    }
}
