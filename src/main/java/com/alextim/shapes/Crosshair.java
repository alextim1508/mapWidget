package com.alextim.shapes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.awt.*;

public class Crosshair {

    private final Color color;
    private final int width;
    private final int lengthCrosshair;

    private Path pathX, pathY;

    public Crosshair() {
        this(Color.GRAY, 3, 10);
    }

    public Crosshair(Color color, int width, int lengthCrosshair) {
        this.color = color;
        this.width = width;
        this.lengthCrosshair = lengthCrosshair;
        createShape();
    }

    private void createShape() {
        pathX = new Path();
        pathX.setFill(null);
        pathX.setStrokeWidth(width);
        pathX.setStroke(color);
        pathX.getElements().add(new MoveTo());
        pathX.getElements().add(new LineTo());

        pathY = new Path();
        pathY.setFill(null);
        pathY.setStrokeWidth(width);
        pathY.setStroke(color);
        pathY.getElements().add(new MoveTo());
        pathY.getElements().add(new LineTo());
    }

    public void render(Group g, Point point) {
        pathX.getElements().set(0, new MoveTo(point.x-lengthCrosshair, point.y));
        pathX.getElements().set(1, new LineTo(point.x+lengthCrosshair, point.y));

        pathY.getElements().set(0, new MoveTo(point.x, point.y-lengthCrosshair));
        pathY.getElements().set(1, new LineTo(point.x, point.y+lengthCrosshair));

        g.getChildren().addAll(pathX, pathY);
    }
}
