package ru.madest.statistic.model;


import java.io.Serializable;
import java.util.List;

public class ConsiderInfo implements Serializable {

    private Integer ballDigit;
    private LotoModel lastFall;
    private String lastFallDay;
    private int repeatCount;
    private List<TwoBallMagnet> twoBalls;
    private List<ThreeBallMagnet> threeBalls;
    private List<FourBallMagnet> fourBalls;

    public Integer getBallDigit() {
        return ballDigit;
    }

    public void setBallDigit(Integer ballDigit) {
        this.ballDigit = ballDigit;
    }

    public LotoModel getLastFall() {
        return lastFall;
    }

    public void setLastFall(LotoModel lastFall) {
        this.lastFall = lastFall;
    }

    public String getLastFallDay() {
        return lastFallDay;
    }

    public void setLastFallDay(String lastFallDay) {
        this.lastFallDay = lastFallDay;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount( int repeatCount ) {
        this.repeatCount = repeatCount;
    }

    public List<TwoBallMagnet> getTwoBalls() {
        return twoBalls;
    }

    public void setTwoBalls(List<TwoBallMagnet> twoBalls) {
        this.twoBalls = twoBalls;
    }

    public List<ThreeBallMagnet> getThreeBalls() {
        return threeBalls;
    }

    public void setThreeBalls(List<ThreeBallMagnet> threeBalls) {
        this.threeBalls = threeBalls;
    }

    public List<FourBallMagnet> getFourBalls() {
        return fourBalls;
    }

    public void setFourBalls(List<FourBallMagnet> fourBalls) {
        this.fourBalls = fourBalls;
    }

}
