package ru.sff.statistic.model;

import java.io.Serializable;

import ru.sff.statistic.AppConstants;

public class RequestBySumOrBall implements Serializable {

    private RequestType requestType;
    private int begin;
    private int end;

    public RequestBySumOrBall() {
        this.begin = AppConstants.MIN_SUM;
        this.end =AppConstants.MAX_SUM;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType( RequestType requestType ) {
        this.requestType = requestType;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin( int begin ) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd( int end ) {
        this.end = end;
    }
}
