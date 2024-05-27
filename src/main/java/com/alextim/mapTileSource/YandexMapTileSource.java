package com.alextim.mapTileSource;

public class YandexMapTileSource  extends AbstractTMSTileSource {

    private static final String URL = "https://map01.maps.yandex.net/";

    private static final String[] SERVER = {"a", "b", "c"};

    private int serverNum;

    public YandexMapTileSource() {
        super("Yandex", "Yandex");
    }

    @Override
    public String getBaseUrl() {
           return URL;
    }

    public String getTilePath(int zoom, int tileX, int tileY) {
        return String.format("tiles?l=sat&v=1.33.0&x=%d&y=%d&z=%d&lang=ru-RU", tileX, tileY, zoom);
    }

    @Override
    public int getMaxZoom() {
        return 18;
    }
}