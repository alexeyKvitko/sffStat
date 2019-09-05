package ru.sff.statistic.model;

import java.io.Serializable;

public class TwoBallMagnet implements Serializable, Comparable {

    private int oneBall;
    private int twoBall;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo( Object dest ) {
        return ((TwoBallMagnet) dest).count - this.count;
    }
}
