package ru.sff.statistic.model;

import java.io.Serializable;

public class SimpleLotoModel implements Serializable {

    private Integer draw;
    private String drawDate;
    private Ball[] balls;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Ball[] getBalls() {
        return balls;
    }

    public void setBalls(Ball[] balls) {
        this.balls = balls;
    }

}