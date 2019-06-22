package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

public class BallsInfo implements Serializable {

    private Ball[] moreOften;
    private Ball[] lessOfter;
    private Ball[] middleOften;
    private List<Ball> drawBalls;

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
}
