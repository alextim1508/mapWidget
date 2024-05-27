package com.alextim.mapTileSource;


public class OsmHotMapTileSource extends AbstractTMSTileSource {

    private static final String URL = "https://%s.tile.openstreetmap.fr/hot";

    private static final String[] SERVER = {"a", "b", "c"};

    private int serverNum;

    public OsmHotMapTileSource() {
        super("Hot", "Humanitarian focused OSM");
    }

    @Override
    public String getBaseUrl() {
        String url = String.format(URL, SERVER[serverNum]);
        serverNum = (serverNum + 1) % SERVER.length;
        return url;
    }

    @Override
    public int getMaxZoom() {
        return 18;
    }
}