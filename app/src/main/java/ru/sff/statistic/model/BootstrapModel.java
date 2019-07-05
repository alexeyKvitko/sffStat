package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

public class BootstrapModel implements Serializable {

    private Integer playedDraws;
    private List<Ball> playedBalls;

    public Integer getPlayedDraws() {
        return playedDraws;
    }

    public void setPlayedDraws( Integer playedDraws ) {
        this.playedDraws = playedDraws;
    }

    public List< Ball > getPlayedBalls() {
        return playedBalls;
    }

    public void setPlayedBalls( List< Ball > playedBalls ) {
        this.playedBalls = playedBalls;
    }

}
