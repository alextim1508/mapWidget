package com.alextim.mapTileSource;

public class BingAerialTileSource extends AbstractTMSTileSource {

    private static final String URL = "https://ecn.t2.tiles.virtualearth.net/tiles/";

    public BingAerialTileSource() {
        super("Bing Aerial Maps",  "BING");
    }

    @Override
    public int getMaxZoom() {
        return 22;
    }

    @Override
    public String getBaseUrl() {
        return URL;
    }

    @Override
    public String getExtension() {
        return ("jpeg");
    }

    @Override
    public String getTilePath(int zoom, int tileX, int tileY)  {
        return "/tiles/a" + computeQuadTree(zoom, tileX, tileY) + "." + getExtension() + "?g=587";
    }

    static String computeQuadTree(int zoom, int tileX, int tileY) {
        StringBuilder builder = new StringBuilder();
        for (int i = zoom; i > 0; i--) {
            char digit = 48;
            int mask = 1 << (i - 1);
            if ((tileX & mask) != 0) {
                digit += 1;
            }
            if ((tileY & mask) != 0) {
                digit += 2;
            }
            builder.append(digit);
        }
        return builder.toString();
    }
}