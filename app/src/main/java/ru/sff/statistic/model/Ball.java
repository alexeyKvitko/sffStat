package ru.sff.statistic.model;

import java.io.Serializable;

public class Ball implements Serializable, Comparable, Cloneable {

    private int ballNumber;
    private int ballRepeat;
    private boolean isBigger;
    private boolean isLess;
    private boolean isMiddle;

    public Ball( int ballNumber, int ballRepeat, boolean isBigger, boolean isLess, boolean isMiddle ) {
        this.ballNumber = ballNumber;
        this.ballRepeat = ballRepeat;
        this.isBigger = isBigger;
        this.isLess = isLess;
        this.isMiddle = isMiddle;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber( int ballNumber ) {
        this.ballNumber = ballNumber;
    }

    public int getBallRepeat() {
        return ballRepeat;
    }

    public void setBallRepeat( int ballRepeat ) {
        this.ballRepeat = ballRepeat;
    }

    public boolean isBigger() {
        return isBigger;
    }

    public void setBigger( boolean bigger ) {
        isBigger = bigger;
    }

    public boolean isLess() {
        return isLess;
    }

    public void setLess( boolean less ) {
        isLess = less;
    }

    public boolean isMiddle() {
        return isMiddle;
    }

    public void setMiddle( boolean middle ) {
        isMiddle = middle;
    }

    @Override
    public int compareTo( Object o ) {
        int res = this.ballRepeat - ( ( Ball ) o ).getBallRepeat();
        return -res;
    }

    public Ball clone() throws CloneNotSupportedException {
        return ( Ball ) super.clone();
    }

}