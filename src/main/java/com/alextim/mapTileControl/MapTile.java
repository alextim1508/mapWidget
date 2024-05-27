package com.alextim.mapTileControl;

import com.alextim.mapTileMask.TileMask;
import com.alextim.mapTileSource.OsmHotMapTileSource;
import com.alextim.mapTileSource.TileSource;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapTile {

    public final ImageView imageView = new ImageView();

    private String key;
    protected TileSource source;
    protected int tileX;
    protected int tileY;
    protected int zoom;
    protected String tileUrl;

    private static Image delayedImg, errorImg;
    {
        delayedImg = new Image(Objects.requireNonNull(getClass().getResource("/watch.png")).toString());
        errorImg = new Image(Objects.requireNonNull(getClass().getResource("/error.png")).toString());
    }

    public MapTile(TileSource source, int tileX, int tileY, int zoom, TileMask mask) {
        this.source = source;
        this.tileX = tileX;
        this.tileY = tileY;
        this.zoom = zoom;

        tileUrl = source.getTileUrl(zoom, tileX, tileY);

        imageView.setImage(delayedImg);
        imageView.setFitWidth(256);
        imageView.setFitHeight(256);

        loadImage(tileUrl, mask);

        key = getTileKey(source, tileX, tileY, zoom);
    }

    public static String getTileKey(TileSource source, int tileX, int tileY, int zoom) {
        return zoom + "/" + tileX + "/" + tileY + "@" + source.getId();
    }

    public String getKey() {
        return key;
    }


    private void loadImage(String url, TileMask mask) {
        Image img = new Image(url, true);
        AtomicBoolean isError = new AtomicBoolean(false);

        img.progressProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            double percentLoaded = newVal.doubleValue();
            if (percentLoaded == 1.0 && !isError.get()) {
                setImage(img, mask);
            }
        });

        img.errorProperty().addListener((arg) -> {
            isError.set(true);
            setImage(errorImg, null);
        });
    }

    public void setImage(Image image, TileMask mask) {
        if (mask == null || image.getWidth() == 0) {
            imageView.setImage(image);
        } else {
            PixelReader sourceReader = image.getPixelReader();
            PixelReader maskReader = mask.image.getPixelReader();

            WritableImage canvas = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            PixelWriter writer = canvas.getPixelWriter();

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color sourceColor = sourceReader.getColor(x, y);
                    Color maskColor = maskReader.getColor(x, y);

                    Color color = new Color(
                            maskColor.getOpacity() == 0 ? sourceColor.getRed() : mask.maskR * sourceColor.getRed(),
                            maskColor.getOpacity() == 0 ? sourceColor.getGreen() : mask.maskG * sourceColor.getGreen(),
                            maskColor.getOpacity() == 0 ? sourceColor.getBlue() : mask.maskB * sourceColor.getBlue(),
                            sourceColor.getOpacity());

                    writer.setColor(x, y, color);
                }
            }
            imageView.setImage(canvas);
        }
    }
}
