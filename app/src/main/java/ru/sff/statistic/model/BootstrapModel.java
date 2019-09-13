package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.List;

public class BootstrapModel implements Serializable {

    private Integer playedDraws;
    private LotoModel lastFall;
    private String lastFallDay;
    private List<Ball> playedBalls;
    private Boolean showDonationsMsg;

    public Integer getPlayedDraws() {
        return playedDraws;
    }

    public LotoModel getLastFall() {
        return lastFall;
    }

    public void setLastFall( LotoModel lastFall ) {
        this.lastFall = lastFall;
    }

    public String getLastFallDay() {
        return lastFallDay;
    }

    public void setLastFallDay( String lastFallDay ) {
        this.lastFallDay = lastFallDay;
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

    public Boolean getShowDonationsMsg() {
        return showDonationsMsg;
    }

    public void setShowDonationsMsg( Boolean showDonationsMsg ) {
        this.showDonationsMsg = showDonationsMsg;
    }
}
