package ru.madest.statistic.model;

import java.io.Serializable;

public class ThreeBallMagnet implements Serializable, Comparable {

    private int oneBall;
    private int twoBall;
    private int threeBall;
    private int count;

    public int getOneBall() {
        return oneBall;
    }

    public void setOneBall(int oneBall) {
        this.oneBall = oneBall;
    }

    public int getTwoBall() {
        return twoBall;
    }

    public void setTwoBall(int twoBall) {
        this.twoBall = twoBall;
    }

    public int getThreeBall() {
        return threeBall;
    }

    public void setThreeBall(int threeBall) {
        this.threeBall = threeBall;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo( Object dest ) {
        return ((ThreeBallMagnet) dest).count - this.count;
    }
}
