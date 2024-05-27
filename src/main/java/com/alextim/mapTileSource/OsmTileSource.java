package com.alextim.mapTileSource;

public class OsmTileSource extends AbstractTMSTileSource{

    public static final String URL = "https://%s.tile.openstreetmap.org:80";

    private static final String[] SERVER = {"a", "b", "c"};

    private int serverNum;

    public OsmTileSource() {
        super("Carto OSM", "Standard Carto OSM");
    }

    @Override
    public String getBaseUrl() {
        String url = String.format(URL, SERVER[serverNum]);
        serverNum = (serverNum + 1) % SERVER.length;
        return url;
    }
}
