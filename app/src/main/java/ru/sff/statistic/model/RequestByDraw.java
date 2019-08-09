package ru.sff.statistic.model;

import java.io.Serializable;

public class RequestByDraw implements Serializable {

    private DrawRequestType drawRequestType;
    private int startDraw;
    private int endDraw;

    public DrawRequestType getDrawRequestType() {
        return drawRequestType;
    }

    public void setDrawRequestType(DrawRequestType drawRequestType) {
        this.drawRequestType = drawRequestType;
    }

    public int getStartDraw() {
        return startDraw;
    }

    public void setStartDraw(int startDraw) {
        this.startDraw = startDraw;
    }

    public int getEndDraw() {
        return endDraw;
    }

    public void setEndDraw(int endDraw) {
        this.endDraw = endDraw;
    }
}
