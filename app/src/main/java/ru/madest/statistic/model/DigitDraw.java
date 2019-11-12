package ru.madest.statistic.model;

import java.io.Serializable;

public class DigitDraw implements Serializable {

    private int number;
    private int startDraw;
    private int endDraw;
    private String startDrawDate;
    private String endDrawDate;
    private int count;

    public int getNumber () {
        return number;
    }

    public void setNumber ( int number ) {
        this.number = number;
    }

    public int getStartDraw () {
        return startDraw;
    }

    public void setStartDraw ( int startDraw ) {
        this.startDraw = startDraw;
    }

    public int getEndDraw () {
        return endDraw;
    }

    public void setEndDraw ( int endDraw ) {
        this.endDraw = endDraw;
    }

    public String getStartDrawDate () {
        return startDrawDate;
    }

    public void setStartDrawDate ( String startDrawDate ) {
        this.startDrawDate = startDrawDate;
    }

    public String getEndDrawDate () {
        return endDrawDate;
    }

    public void setEndDrawDate ( String endDrawDate ) {
        this.endDrawDate = endDrawDate;
    }

    public int getCount () {
        return count;
    }

    public void setCount ( int count ) {
        this.count = count;
    }
}
