package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

import ru.sff.statistic.AppConstants;

public class CachedRequestByDraw implements Serializable {

    private int startDraw;
    private int endDraw;
    private boolean requestByCount;
    private BallsInfo ballsInfo;
    private List< LotoModel > lotoModelDraws;

    public CachedRequestByDraw() {
        startDraw = AppConstants.FAKE_ID;
        endDraw = AppConstants.FAKE_ID;
    }

    public int getStartDraw() {
        return startDraw;
    }

    public void setStartDraw( int startDraw ) {
        this.startDraw = startDraw;
    }

    public int getEndDraw() {
        return endDraw;
    }

    public void setEndDraw( int endDraw ) {
        this.endDraw = endDraw;
    }

    public boolean isRequestByCount() {
        return requestByCount;
    }

    public void setRequestByCount( boolean requestByCount ) {
        this.requestByCount = requestByCount;
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
}
