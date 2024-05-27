package com.alextim.mapTileSource;

public class GoogleSatTileSource extends AbstractTMSTileSource{

    public static final String URL = "https://mt1.google.com/vt/";

    private int serverNum;

    public GoogleSatTileSource() {
        super("Google Sat", "Google satellite");
    }

    @Override
    public String getBaseUrl() {
        return URL;
    }

    @Override
    public String getTilePath(int zoom, int tileX, int tileY) {
        return String.format("&lyrs=s&x=%d&y=%d&z=%d", tileX, tileY, zoom);
    }
}
