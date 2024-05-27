package com.alextim.shapes;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;


import java.awt.*;
import java.util.function.Consumer;

import static com.alextim.mapTileControl.MapUtil.LatLonCoord;


public class RadiationMarker implements MapMarker {

    private final String pathToFile = "/radiationIcon.png";
    private final int width = 25;
    private final int length = 50;

    private LatLonCoord coord;

    private Rectangle rectangle;
    private Text label;

    private Consumer<LatLonCoord> onSelected;

    private String tag;

    public RadiationMarker(LatLonCoord coord, String tag, Consumer<LatLonCoord> onSelected) {
        this.coord = coord;
        this.coord = coord;
        this.tag = tag;
        this.onSelected = onSelected;
        createShape();
    }

    private void createShape() {
        Image img = new Image(getClass().getResource(pathToFile).toString());
        rectangle = new Rectangle(width, length);
        rectangle.setFill(new ImagePattern(img));

        label = new Text(tag);
        label.setFill(Color.BLACK);
        label.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR,25));
        label.setFill(Color.BLUE);// setting colour of the text to blue
        label.setStroke(Color.BLACK); // setting the stroke for the text
        label.setStrokeWidth(1); // setting stroke width to 2
    }

    @Override
    public LatLonCoord getLatLonCoord() {
        return coord;
    }

    @Override
    public void render(Group g, Point point) {
        int x = point.x - width / 2;
        int y = point.y - length;
        rectangle.setX(x);
        rectangle.setY(y);
        if(onSelected != null) {
            rectangle.setOnMouseClicked(event -> {
                onSelected.accept(coord);
            });
        }

        label.setX(x);
        label.setY(y - 5);

        g.getChildren().addAll(rectangle, label);
    }
}
