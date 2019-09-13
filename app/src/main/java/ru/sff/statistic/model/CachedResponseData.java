package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

import ru.sff.statistic.AppConstants;

public class CachedResponseData implements Serializable {

    private RequestByDate requestByDate;
    private RequestByDraw requestByDraw;
    private RequestBySumOrBall requestBySOB;
    private RequestType lastRequest;
    private int totalDraw;
    private BallsInfo ballsInfo;
    private List< LotoModel > lotoModelDraws;

    public RequestByDate getRequestByDate() {
        return requestByDate;
    }

    public void setRequestByDate( RequestByDate requestByDate ) {
        this.requestByDate = requestByDate;
    }

    public RequestByDraw getRequestByDraw() {
        return requestByDraw;
    }

    public void setRequestByDraw( RequestByDraw requestByDraw ) {
        this.requestByDraw = requestByDraw;
    }

    public RequestBySumOrBall getRequestBySOB() {
        return requestBySOB;
    }

    public void setRequestBySOB( RequestBySumOrBall requestBySOB ) {
        this.requestBySOB = requestBySOB;
    }

    public RequestType getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest( RequestType lastRequest ) {
        this.lastRequest = lastRequest;
    }

    public BallsInfo getBallsInfo() {
        return ballsInfo;
    }

    public void setBallsInfo( BallsInfo ballsInfo ) {
        this.ballsInfo = ballsInfo;
    }

    public List< LotoModel > getLotoModelDraws() {
        return lotoModelDraws;
    }

    public void setLotoModelDraws( List< LotoModel > lotoModelDraws ) {
        this.lotoModelDraws = lotoModelDraws;
    }

    public int getTotalDraw() {
        return totalDraw;
    }

    public void setTotalDraw( int totalDraw ) {
        this.totalDraw = totalDraw;
    }

    public void clearAllRequests(){
        this.requestByDate = null;
        this.requestByDraw = null;
        this.requestBySOB = null;
    }
}
