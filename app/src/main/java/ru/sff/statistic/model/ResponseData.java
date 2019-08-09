package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable {

    private BallsInfo ballsInfo;
    private List< LotoModel > lotoDraws;

    public BallsInfo getBallsInfo() {
        return ballsInfo;
    }

    public void setBallsInfo(BallsInfo ballsInfo) {
        this.ballsInfo = ballsInfo;
    }

    public List<LotoModel> getLotoDraws() {
        return lotoDraws;
    }

    public void setLotoDraws(List<LotoModel> lotoDraws) {
        this.lotoDraws = lotoDraws;
    }
}
