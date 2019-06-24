package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BallsInfo implements Serializable {

    private int firstDraw;
    private int lastDraw;
    private Date firstDrawDate;
    private Date lastDrawDate;
    private Ball[] moreOften = new Ball[6];
    private Ball[] lessOfter= new Ball[6];;
    private Ball[] middleOften= new Ball[6];;
    private List<Ball> drawBalls;
    private EvenOdd evenOdd;


    public int getFirstDraw() {
        return firstDraw;
    }

    public void setFirstDraw(int firstDraw) {
        this.firstDraw = firstDraw;
    }

    public int getLastDraw() {
        return lastDraw;
    }

    public void setLastDraw(int lastDraw) {
        this.lastDraw = lastDraw;
    }

    public Date getFirstDrawDate() {
        return firstDrawDate;
    }

    public void setFirstDrawDate(Date firstDrawDate) {
        this.firstDrawDate = firstDrawDate;
    }

    public Date getLastDrawDate() {
        return lastDrawDate;
    }

    public void setLastDrawDate(Date lastDrawDate) {
        this.lastDrawDate = lastDrawDate;
    }

    public Ball[] getMoreOften() {
        return moreOften;
    }

    public void setMoreOften( Ball[] moreOften ) {
        this.moreOften = moreOften;
    }

    public Ball[] getLessOfter() {
        return lessOfter;
    }

    public void setLessOfter( Ball[] lessOfter ) {
        this.lessOfter = lessOfter;
    }

    public Ball[] getMiddleOften() {
        return middleOften;
    }

    public void setMiddleOften( Ball[] middleOften ) {
        this.middleOften = middleOften;
    }

    public List< Ball > getDrawBalls() {
        return drawBalls;
    }

    public void setDrawBalls( List< Ball > drawBalls ) {
        this.drawBalls = drawBalls;
    }

    public EvenOdd getEvenOdd() {
        return evenOdd;
    }

    public void setEvenOdd(EvenOdd evenOdd) {
        this.evenOdd = evenOdd;
    }
}
