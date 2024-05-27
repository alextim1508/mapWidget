package com.alextim.shapes;

import com.alextim.mapTileControl.MapUtil;
import javafx.scene.Group;

import java.awt.*;

public interface MapMarker extends MapObject{

    MapUtil.LatLonCoord getLatLonCoord();
    void render(Group g, Point point);
}
