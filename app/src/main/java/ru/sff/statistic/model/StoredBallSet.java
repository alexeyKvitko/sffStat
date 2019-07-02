package ru.sff.statistic.model;

public class StoredBallSet {

    private Ball[] ballSets;
    private String storedDate;
    private BallSetType ballSetType;
    private String ballSetName;

    public Ball[] getBallSets() {
        return ballSets;
    }

    public void setBallSets( Ball[] ballSets ) {
        this.ballSets = ballSets;
    }

    public String getStoredDate() {
        return storedDate;
    }

    public void setStoredDate( String storedDate ) {
        this.storedDate = storedDate;
    }

    public BallSetType getBallSetType() {
        return ballSetType;
    }

    public void setBallSetType( BallSetType ballSetType ) {
        this.ballSetType = ballSetType;
    }

    public String getBallSetName() {
        return ballSetName;
    }

    public void setBallSetName( String ballSetName ) {
        this.ballSetName = ballSetName;
    }
}
