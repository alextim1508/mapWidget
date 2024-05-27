package com.alextim.shapes;

import com.alextim.mapTileControl.MapUtil;
import javafx.scene.Group;

import java.awt.*;
import java.util.List;

public interface MapLine extends MapObject{

    List<MapUtil.LatLonCoord> getLatLonCoord();
    void render(Group g, List<Point> points);
}
