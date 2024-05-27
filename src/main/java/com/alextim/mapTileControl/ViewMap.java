package com.alextim.mapTileControl;

import com.alextim.mapTileControl.MapUtil.LatLonCoord;
import com.alextim.shapes.Crosshair;
import com.alextim.shapes.MapLine;
import com.alextim.shapes.MapMarker;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.alextim.mapTileControl.MapUtil.TILE_SIZE;

public class ViewMap {

    protected boolean mapMarkersVisible;
    protected List<MapMarker> mapMarkerList;
    protected List<MapLine> mapLineList;

    private LatLonCoord latLonFocus;
    private int zoom, maxZoom;
    private int dX, dY;

    private int halfHeight, halfWidth;

    private TileController tileController;
    private Group layer;

    private final Crosshair crosshair = new Crosshair();

    public ViewMap(TileController tileController, Group layer, MapParam mapParam) {
        init(tileController, layer,  mapParam);
        reDrawMap();
    }

    public void init(TileController tileController, Group layer, MapParam mapParam) {
        this.tileController = tileController;
        this.layer = layer;

        this.maxZoom = mapParam.maxZoom;
        this.zoom = mapParam.initZoom;
        this.latLonFocus = mapParam.focus;
        this.halfHeight = mapParam.height/2;
        this.halfWidth = mapParam.width/2;

        mapMarkerList = new ArrayList<>(mapParam.markerSize);
        mapLineList = new ArrayList<>(mapParam.lineSize);
    }

    public void resize(int width, int height) {
        this.halfHeight = height/2;
        this.halfWidth = width/2;

        reDrawMap();
    }

    public void resizeByWidth(int width) {
        this.halfWidth = width/2;
        reDrawMap();
    }

    public void resizeByHeight(int height) {
        this.halfHeight = height/2;
        reDrawMap();
    }

    public void setZoom(int zoom) {
        if (zoom<0)
            zoom = 0;
        if (zoom>maxZoom)
            zoom=maxZoom;
        this.zoom = zoom;

        reDrawMap();
    }

    public void setCenter(LatLonCoord latLonFocus) {
        this.latLonFocus = latLonFocus;

        reDrawMap();
    }

    public void move(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;

        reDrawMap();
    }

    public LatLonCoord getMapPositionByDisplay(Point point) {
        MapUtil.CartographicCoord cartographicCoord = MapUtil.latLonToCartographicCoord(latLonFocus, zoom);
        cartographicCoord.x += point.x - halfWidth;
        cartographicCoord.y += point.y - halfHeight;
        return MapUtil.cartographicToLatLonCoord(cartographicCoord, zoom);
    }

    public Point getDisplayPositionByMap(LatLonCoord latLonCoord) {
        MapUtil.CartographicCoord cartographicCoordFocus = MapUtil.latLonToCartographicCoord(latLonFocus, zoom);

        MapUtil.CartographicCoord cartographicCoordPosition = MapUtil.latLonToCartographicCoord(latLonCoord, zoom);

        cartographicCoordPosition.x -= cartographicCoordFocus.x - halfWidth;
        cartographicCoordPosition.y -= cartographicCoordFocus.y - halfHeight;

        return new Point(cartographicCoordPosition.x, cartographicCoordPosition.y);
    }

    public void setMapMarkersVisible(boolean mapMarkersVisible) {
        this.mapMarkersVisible = mapMarkersVisible;

        reDrawMap();
    }

    public void addMapLine(MapLine line) {
        mapLineList.add(line);
        reDrawMap();
    }

    public void addMapLine(List<MapLine> lines) {
        mapLineList.addAll(lines);
        reDrawMap();
    }

    public void removeMapLine(MapLine line) {
        mapLineList.remove(line);
        reDrawMap();
    }

    public void clearLines() {
        mapLineList.clear();
        reDrawMap();
    }

    public void addMapMarker(MapMarker marker) {
        mapMarkerList.add(marker);
        reDrawMap();
    }

    public void removeMapMarker(MapMarker marker) {
        mapMarkerList.remove(marker);
        reDrawMap();
    }

    public void clearMarkers() {
        mapMarkerList.clear();
        reDrawMap();
    }

    public void reDrawMap()  {
        MapUtil.CartographicCoord displayFocus = MapUtil.latLonToCartographicCoord(latLonFocus, zoom);
        displayFocus.x += dX;
        dX = 0;
        displayFocus.y += dY;
        dY = 0;

        latLonFocus = MapUtil.cartographicToLatLonCoord(displayFocus, zoom);

        int xLeftTileNumber = (displayFocus.x - halfWidth) / TILE_SIZE;
        int xRightTileNumber = (displayFocus.x + halfWidth) / TILE_SIZE;
        int yDownTileNumber = (displayFocus.y - halfHeight) / TILE_SIZE;
        int yUpTileNumber = (displayFocus.y + halfHeight) / TILE_SIZE;

        MapUtil.TileCoord tileFocus = MapUtil.cartographicToTileCoord(displayFocus);

        layer.getChildren().clear();

        for (int y = yDownTileNumber; y <= yUpTileNumber; y++) {
            int displayCoordY = (halfHeight - tileFocus.dY) + TILE_SIZE*(y - tileFocus.y);

            for (int x = xLeftTileNumber; x <= xRightTileNumber; x++) {
                int displayCoordX = (halfWidth - tileFocus.dX) + TILE_SIZE*(x - tileFocus.x);

                MapTile tile = tileController.getTile(x, y, zoom);
                if (tile != null) {
                    tile.imageView.setX(displayCoordX);
                    tile.imageView.setY(displayCoordY);
                    layer.getChildren().add(tile.imageView);
                }
            }
        }

        crosshair.render(layer, new Point(halfWidth, halfHeight));

        if (mapMarkersVisible) {
            mapMarkerList.forEach(marker ->
                    marker.render(layer, getDisplayPositionByMap(marker.getLatLonCoord())));

            mapLineList.forEach(line ->
                    line.render(layer, line.getLatLonCoord().stream().map(this::getDisplayPositionByMap).collect(Collectors.toList())));
        }
    }
}
