package ru.sff.statistic.model;

import java.io.Serializable;

public class SimpleDraw implements Serializable {

    private Integer draw;
    private String drawDate;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw( Integer draw ) {
        this.draw = draw;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate( String drawDate ) {
        this.drawDate = drawDate;
    }
}
