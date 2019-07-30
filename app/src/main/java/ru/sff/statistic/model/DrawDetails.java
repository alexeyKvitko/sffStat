package ru.sff.statistic.model;

import java.io.Serializable;

public class DrawDetails implements Serializable {

    private DrawInfo drawInfo;
    private LotoModel lotoDraw;

    public DrawInfo getDrawInfo() {
        return drawInfo;
    }

    public void setDrawInfo( DrawInfo drawInfo ) {
        this.drawInfo = drawInfo;
    }

    public LotoModel getLotoDraw() {
        return lotoDraw;
    }

    public void setLotoDraw( LotoModel lotoDraw ) {
        this.lotoDraw = lotoDraw;
    }
}
