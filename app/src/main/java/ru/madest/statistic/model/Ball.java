package ru.madest.statistic.model;

import java.io.Serializable;
import java.util.Set;

public class Ball implements Serializable, Comparable, Cloneable {


    private int ballNumber;
    private int ballRepeat;
    private int drawRange;
    private int avgRange;
    private BallSetType ballType;
    private Set<BallSetType> comby;

    public Ball( int ballNumber, int ballRepeat, BallSetType ballType ) {
        this.ballNumber = ballNumber;
        this.ballRepeat = ballRepeat;
        this.ballType = ballType;
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

    public int getDrawRange() {
        return drawRange;
    }

    public void setDrawRange( int drawRange ) {
        this.drawRange = drawRange;
    }

    public int getAvgRange() {
        return avgRange;
    }

    public void setAvgRange( int avgRange ) {
        this.avgRange = avgRange;
    }

    public BallSetType getBallType() {
        return ballType;
    }

    public void setBallType( BallSetType ballType ) {
        this.ballType = ballType;
    }

    public Set< BallSetType > getComby() {
        return comby;
    }

    public void setComby( Set< BallSetType > comby ) {
        this.comby = comby;
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