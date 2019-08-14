package ru.sff.statistic.model;

import java.io.Serializable;

public class RequestByDraw implements Serializable {

    private RequestType requestDrawType;
    private int ball;
    private int startDraw;
    private int endDraw;

    public RequestType getRequestDrawType() {
        return requestDrawType;
    }

    public void setRequestDrawType( RequestType requestDrawType ) {
        this.requestDrawType = requestDrawType;
    }

    public int getBall() {
        return ball;
    }

    public void setBall( int ball ) {
        this.ball = ball;
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
