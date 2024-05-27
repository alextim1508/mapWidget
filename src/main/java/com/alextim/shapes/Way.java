package com.alextim.shapes;

import com.alextim.mapTileControl.MapUtil;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Way implements MapLine {

    private Path path;
    private int radiusCircle;
    private List<MapUtil.LatLonCoord> coords;

    private boolean isClosed;
    private Consumer<MapUtil.LatLonCoord> onSelected;

    public Way(List<MapUtil.LatLonCoord> coords, Consumer<MapUtil.LatLonCoord> onSelected) {
        this(coords, false, onSelected,  Color.BLUE);
    }

    public Way(List<MapUtil.LatLonCoord> coords, boolean isClosed, Consumer<MapUtil.LatLonCoord> onSelected) {
        this(coords, isClosed, onSelected,  Color.BLUE);
    }

    public Way(List<MapUtil.LatLonCoord> coords,  boolean isClosed, Consumer<MapUtil.LatLonCoord> onSelected, Color color) {
        this(coords, isClosed, onSelected, color, 3, 1);
    }

    public Way(List<MapUtil.LatLonCoord> coords,  boolean isClosed, Consumer<MapUtil.LatLonCoord> onSelected, Color color, int width, int radius) {
        this.coords = coords;
        this.onSelected = onSelected;
        this.radiusCircle = radius;
        this.isClosed = isClosed;
        createShape(color, width);
    }

    private void createShape(Color color, int width) {
        path = new Path();
        path.setStroke(color);
        path.setFill(null);
        path.setStrokeWidth(width);
    }

    public void setColor(Color color) {
        path.setStroke(color);
    }

    public void setWidth(int width) {
        path.setStrokeWidth(width);
    }

    @Override
    public List<MapUtil.LatLonCoord> getLatLonCoord() {
        return coords;
    }

    public void render(Group g, List<Point> points) {
        if(points.size() < 2)
            throw new RuntimeException("points size must be greater than 1");

        path.getElements().clear();
        path.getElements().add(new MoveTo(points.get(0).x, points.get(0).y));


        List<Circle> circles = new ArrayList<>(points.size());
        for (int i = 1; i < points.size(); i++) {
            path.getElements().add(new LineTo(points.get(i).x, points.get(i).y));
            Circle circle = new Circle(points.get(i).x, points.get(i).y, radiusCircle, Color.BLUE);

            MapUtil.LatLonCoord coord = coords.get(i);
            if(onSelected != null) {
                circle.setOnMouseClicked(event -> {
                    onSelected.accept(coord);
                });
            }
            circles.add(circle);

        }
        if(isClosed)
            path.getElements().add(new ClosePath());


        g.getChildren().add(path);
        g.getChildren().addAll(circles);
    }
}
