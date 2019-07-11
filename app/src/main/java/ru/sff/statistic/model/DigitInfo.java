package ru.sff.statistic.model;

import java.util.List;

public class DigitInfo {

    private Integer ballDigit;
    private LotoModel lastFall;
    private String lastFallDay;
    private String seriesCount;
    private Integer seriesDrawStart;
    private Integer seriesDrawEnd;
    private List<MagnetNumber> twoBalls;
    private List<MagnetNumber> threeBalls;
    private List<MagnetNumber> fourBalls;
    private List<Integer> hitToFive;
    private List<Integer> hitToSix;
    private List<Integer> digitDraws;

    public Integer getBallDigit() {
        return ballDigit;
    }

    public void setBallDigit( Integer ballDigit ) {
        this.ballDigit = ballDigit;
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

    public String getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount( String seriesCount ) {
        this.seriesCount = seriesCount;
    }

    public Integer getSeriesDrawStart() {
        return seriesDrawStart;
    }

    public void setSeriesDrawStart( Integer seriesDrawStart ) {
        this.seriesDrawStart = seriesDrawStart;
    }

    public Integer getSeriesDrawEnd() {
        return seriesDrawEnd;
    }

    public void setSeriesDrawEnd( Integer seriesDrawEnd ) {
        this.seriesDrawEnd = seriesDrawEnd;
    }

    public List< MagnetNumber > getTwoBalls() {
        return twoBalls;
    }

    public void setTwoBalls( List< MagnetNumber > twoBalls ) {
        this.twoBalls = twoBalls;
    }

    public List< MagnetNumber > getThreeBalls() {
        return threeBalls;
    }

    public void setThreeBalls( List< MagnetNumber > threeBalls ) {
        this.threeBalls = threeBalls;
    }

    public List< MagnetNumber > getFourBalls() {
        return fourBalls;
    }

    public void setFourBalls( List< MagnetNumber > fourBalls ) {
        this.fourBalls = fourBalls;
    }

    public List< Integer > getHitToFive() {
        return hitToFive;
    }

    public void setHitToFive( List< Integer > hitToFive ) {
        this.hitToFive = hitToFive;
    }

    public List< Integer > getHitToSix() {
        return hitToSix;
    }

    public void setHitToSix( List< Integer > hitToSix ) {
        this.hitToSix = hitToSix;
    }

    public List< Integer > getDigitDraws() {
        return digitDraws;
    }

    public void setDigitDraws( List< Integer > digitDraws ) {
        this.digitDraws = digitDraws;
    }
}
