package ru.sff.statistic.model;

import android.util.Log;

import java.util.Comparator;

public class StoredBallSet implements Comparable {

    private Ball[] ballSets;
    private String storedDate;
    private BallSetType ballSetType;
    private String ballSetName;
    private String drawCount;

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

    public String getDrawCount() {
        return drawCount;
    }

    public void setDrawCount( String drawCount ) {
        this.drawCount = drawCount;
    }



    @Override
    public int compareTo( Object setTwo ) {
        String drawOne = this.drawCount;
        String drawTwo = (( StoredBallSet ) setTwo).drawCount;
        int countOne = 0;
        int countTwo = 0;
        Log.i("COMPARE: ","DRAWS: "+drawOne+" with "+drawTwo);
        try {
            countOne = Integer.valueOf( drawOne.substring( 0, drawOne.indexOf( " " ) ).trim() );
        } catch ( Exception e ){}
        try{
            countTwo = Integer.valueOf( drawTwo.substring( 0, drawTwo.indexOf( " " ) ).trim() );
        } catch ( Exception e ){}
        Log.i("COMPARE: ",countOne+" with "+countTwo);
        return countTwo-countOne;
    }
}
