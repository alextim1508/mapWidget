package com.alextim.mapTileMask;

import javafx.scene.image.Image;


public class TileMask {
    public Image image;
    public double maskR;
    public double maskG;
    public double maskB;

    public void setImage(Image image) {
        this.image = image;
    }

    public void setMaskR(double maskR) {
        this.maskR = maskR;
    }

    public void setMaskG(double maskG) {
        this.maskG = maskG;
    }

    public void setMaskB(double maskB) {
        this.maskB = maskB;
    }
}
