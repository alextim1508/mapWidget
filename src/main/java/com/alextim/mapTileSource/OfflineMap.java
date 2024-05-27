package com.alextim.mapTileSource;

import java.io.File;

public class OfflineMap extends AbstractTMSTileSource{

    private String path;

    public OfflineMap(String path) {
        super("offline", "offline");
        this.path = path;
    }

    @Override
    public String getBaseUrl() {
        File file = new File(path);
        return file.toURI().toString();
    }
}
