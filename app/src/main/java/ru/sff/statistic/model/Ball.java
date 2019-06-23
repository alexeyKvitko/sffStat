package ru.sff.statistic.model;

import java.io.Serializable;

public class Ball implements Serializable {

    private String ballNumber;
    private String ballRepeat;
    private boolean isBigger;
    private boolean isLess;
    private boolean isMiddle;

    public Ball(String ballNumber, String ballRepeat) {
        this.ballNumber = ballNumber;
        this.ballRepeat = ballRepeat;
    }

    public String getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber( String ballNumber ) {
        this.ballNumber = ballNumber;
    }

    public String getBallRepeat() {
        return ballRepeat;
    }

    public void setBallRepeat( String ballRepeat ) {
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
}
