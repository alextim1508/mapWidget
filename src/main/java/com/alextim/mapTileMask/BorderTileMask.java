package com.alextim.mapTileMask;

import javafx.scene.image.Image;

import java.util.Objects;

public class BorderTileMask extends TileMask {
    private static Image image;
    private static double maskR;
    private static double maskG;
    private static double maskB;
    {
        image = new Image(Objects.requireNonNull(getClass().getResource("/border.png")).toString());
        maskR = 0.5;
        maskG = 0.5;
        maskB = 0.5;
    }

    public BorderTileMask() {
        setImage(image);
        setMaskR(maskR);
        setMaskG(maskG);
        setMaskB(maskB);
    }
}
