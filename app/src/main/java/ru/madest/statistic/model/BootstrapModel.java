package ru.madest.statistic.model;

import java.io.Serializable;
import java.util.List;

public class BootstrapModel implements Serializable {

    private Integer playedDraws;
    private LotoModel lastFall;
    private String lastFallDay;
    private List<Ball> playedBalls;
    private boolean showDonationsMsg;
    private boolean userBlocked;
    private int level;
    private Ball[] sixWinOften = new Ball[6];
    private Ball[] fiveWinOften = new Ball[6];
    private Ball[] fourWinOften = new Ball[6];
    private Integer sixBallWinDraws;
    private Integer fiveBallWinDraws;
    private Integer fourBallWinDraws;

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

    public boolean isShowDonationsMsg () {
        return showDonationsMsg;
    }

    public void setShowDonationsMsg ( boolean showDonationsMsg ) {
        this.showDonationsMsg = showDonationsMsg;
    }

    public boolean isUserBlocked () {
        return userBlocked;
    }

    public void setUserBlocked ( boolean userBlocked ) {
        this.userBlocked = userBlocked;
    }

    public Ball[] getSixWinOften() {
        return sixWinOften;
    }

    public void setSixWinOften(Ball[] sixWinOften) {
        this.sixWinOften = sixWinOften;
    }

    public Ball[] getFiveWinOften() {
        return fiveWinOften;
    }

    public void setFiveWinOften(Ball[] fiveWinOften) {
        this.fiveWinOften = fiveWinOften;
    }

    public Ball[] getFourWinOften() {
        return fourWinOften;
    }

    public void setFourWinOften(Ball[] fourWinOften) {
        this.fourWinOften = fourWinOften;
    }

    public Integer getSixBallWinDraws() {
        return sixBallWinDraws;
    }

    public void setSixBallWinDraws(Integer sixBallWinDraws) {
        this.sixBallWinDraws = sixBallWinDraws;
    }

    public Integer getFiveBallWinDraws() {
        return fiveBallWinDraws;
    }

    public void setFiveBallWinDraws(Integer fiveBallWinDraws) {
        this.fiveBallWinDraws = fiveBallWinDraws;
    }

    public Integer getFourBallWinDraws() {
        return fourBallWinDraws;
    }

    public void setFourBallWinDraws(Integer fourBallWinDraws) {
        this.fourBallWinDraws = fourBallWinDraws;
    }

    public int getLevel () {
        return level;
    }

    public void setLevel ( int level ) {
        this.level = level;
    }
}
