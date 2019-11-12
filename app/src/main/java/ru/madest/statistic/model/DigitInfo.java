package ru.madest.statistic.model;

import java.util.List;
import java.util.Map;

public class DigitInfo {

    private Integer ballDigit;
    private LotoModel lastFall;
    private String lastFallDay;
    private String seriesCount;
    private Integer seriesDrawStart;
    private Integer seriesDrawEnd;
    private Integer drawFreqAvg;
    private Integer drawFreqMax;
    private Integer drawFreqMaxValue;
    private Integer fiveWinCount;
    private Map<Integer, DigitDraw> countTenHits;
    private List< MagnetModel > twoBalls;
    private List< MagnetModel > threeBalls;
    private List< MagnetModel > fourBalls;
    private List<SimpleLotoModel> hitToFive;
    private List<SimpleLotoModel> hitToSix;
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

    public Integer getDrawFreqAvg() {
        return drawFreqAvg;
    }

    public void setDrawFreqAvg( Integer drawFreqAvg ) {
        this.drawFreqAvg = drawFreqAvg;
    }

    public Integer getDrawFreqMax() {
        return drawFreqMax;
    }

    public void setDrawFreqMax( Integer drawFreqMax ) {
        this.drawFreqMax = drawFreqMax;
    }

    public Integer getDrawFreqMaxValue() {
        return drawFreqMaxValue;
    }

    public void setDrawFreqMaxValue( Integer drawFreqMaxValue ) {
        this.drawFreqMaxValue = drawFreqMaxValue;
    }

    public void setSeriesDrawEnd( Integer seriesDrawEnd ) {
        this.seriesDrawEnd = seriesDrawEnd;
    }

    public Integer getFiveWinCount() {
        return fiveWinCount;
    }

    public Map< Integer, DigitDraw > getCountTenHits () {
        return countTenHits;
    }

    public void setCountTenHits ( Map< Integer, DigitDraw > countTenHits ) {
        this.countTenHits = countTenHits;
    }

    public void setFiveWinCount( Integer fiveWinCount ) {
        this.fiveWinCount = fiveWinCount;
    }

    public List< MagnetModel > getTwoBalls() {
        return twoBalls;
    }

    public void setTwoBalls( List< MagnetModel > twoBalls ) {
        this.twoBalls = twoBalls;
    }

    public List< MagnetModel > getThreeBalls() {
        return threeBalls;
    }

    public void setThreeBalls( List< MagnetModel > threeBalls ) {
        this.threeBalls = threeBalls;
    }

    public List< MagnetModel > getFourBalls() {
        return fourBalls;
    }

    public void setFourBalls( List< MagnetModel > fourBalls ) {
        this.fourBalls = fourBalls;
    }

    public List< SimpleLotoModel > getHitToFive() {
        return hitToFive;
    }

    public void setHitToFive( List< SimpleLotoModel > hitToFive ) {
        this.hitToFive = hitToFive;
    }

    public List< SimpleLotoModel > getHitToSix() {
        return hitToSix;
    }

    public void setHitToSix( List< SimpleLotoModel > hitToSix ) {
        this.hitToSix = hitToSix;
    }

    public List< Integer > getDigitDraws() {
        return digitDraws;
    }

    public void setDigitDraws( List< Integer > digitDraws ) {
        this.digitDraws = digitDraws;
    }
}
