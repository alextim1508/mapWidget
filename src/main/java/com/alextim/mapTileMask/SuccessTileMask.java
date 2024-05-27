package com.alextim.mapTileMask;

import javafx.scene.image.Image;

import java.util.Objects;

public class SuccessTileMask extends TileMask {

    private static Image image;
    private static double maskR;
    private static double maskG;
    private static double maskB;
    {
        image = new Image(Objects.requireNonNull(getClass().getResource("/ok.png")).toString());
        maskR = 0.3;
        maskG = 0.8;
        maskB = 0.3;
    }

    public SuccessTileMask() {
        setImage(image);
        setMaskR(maskR);
        setMaskG(maskG);
        setMaskB(maskB);
    }
}
