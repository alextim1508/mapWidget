package com.alextim.mapTileControl;

import com.alextim.mapTileControl.MapUtil.LatLonCoord;

public class MapParam {

    public int initZoom, maxZoom;
    public int width, height;
    public LatLonCoord focus;
    public int markerSize, lineSize;

    public MapParam(int initZoom, int maxZoom, int width, int height, LatLonCoord focus, int markerSize, int lineSize) {
        this.initZoom = initZoom;
        this.maxZoom = maxZoom;
        this.width = width;
        this.height = height;
        this.focus = focus;
        this.markerSize = markerSize;
        this.lineSize = lineSize;
    }
}
