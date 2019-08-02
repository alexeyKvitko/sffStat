package ru.sff.statistic.model;

import java.io.Serializable;

public class MagnetModel implements Serializable, Comparable {

    private Integer id;
    private Integer ballCount;
    private String combination;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getBallCount() {
        return ballCount;
    }

    public void setBallCount( Integer ballCount ) {
        this.ballCount = ballCount;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination( String combination ) {
        this.combination = combination;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount( Integer count ) {
        this.count = count;
    }

    @Override
    public int compareTo( Object o ) {
        int res = this.count - ( ( MagnetModel ) o ).getCount();
        return -res;
    }
}
