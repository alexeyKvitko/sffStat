package ru.sff.statistic.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.utils.CustomAnimation;

import static ru.sff.statistic.manager.GlobalManager.getFieldOrientation;

public class FiveNineTable extends LinearLayout {

    private static final int ROW_IDS[] = new int[]{ R.id.fiveNineRowOneId, R.id.fiveNineRowTwoId
                                            ,R.id.fiveNineRowThreeId, R.id.fiveNineRowFourId
                                            ,R.id.fiveNineRowFiveId, R.id.fiveNineRowSixId
                                            ,R.id.fiveNineRowSevenId, R.id.fiveNineRowEightId
                                            ,R.id.fiveNineRowNineId };

    private BallsInfo mBallsInfo;

    private Context mContext;

    private int mBallOrientation;

    public FiveNineTable( Context context ) {
        super( context );
        inflate( context, R.layout.five_nine_table, this );
        mContext = context;
        mBallOrientation = AppConstants.VERTICAL_ORIENTATION;
    }

    public FiveNineTable( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.five_nine_table, this );
        mContext = context;
        mBallOrientation = AppConstants.VERTICAL_ORIENTATION;
    }

    public void clearTable(){
        for( int rowId : ROW_IDS ){
            LinearLayout row = findViewById( rowId );
            if( row.getChildCount() > 0 ){
                row.removeAllViews();
            }
        }
    }

    private BallField createBallView( Ball ball ){
        BallField ballField = new BallField( mContext, mBallOrientation );
        ballField.setBall( ball );
        return ballField;
    }

    public void fillFiveNineTable( ){
        for( int rowId = 0; rowId < 7; rowId++ ){
            LinearLayout row = findViewById( ROW_IDS[ rowId ] );
            for( int cell = 0; cell < 7; cell++ ){
                int rowCel = 7*rowId+cell;
                int idx = getFieldOrientation()[ rowCel ];
                if ( idx < 45 ){
                    row.addView( createBallView( mBallsInfo.getDrawBalls().get( idx ) ) );
                } else {
                    row.addView( createBallView( null ) );
                }
            }
        }
    }

    public void setBallsInfo( BallsInfo ballsInfo ) {
        this.mBallsInfo = ballsInfo;
        fillFiveNineTable( );
    }

    public void redrawTable(){
        clearTable();
        fillFiveNineTable();
    }

    public void redrawTable( BallsInfo ballsInfo ){
        this.mBallsInfo = ballsInfo;
        redrawTable();
    }

    public void setBallOrientation( int ballOrientation ) {
        this.mBallOrientation = ballOrientation;
    }

    public List<Ball> getSelectedBall(){
         List<Ball> balls = new LinkedList<>(  );
        for( int rowId = 0; rowId < 7; rowId++ ){
            LinearLayout row = findViewById( ROW_IDS[ rowId ] );
            for( int i = 0; i < row.getChildCount(); i++ ){
                View child = row.getChildAt( i );
                if( child instanceof BallField &&
                        ((BallField) child).isCircleVisible() ) {
                    Ball selectedBall = ((BallField) child).getBall();
                    selectedBall.setBallType( BallSetType.CUSTOM );
                    balls.add( selectedBall );
                }
            }
        }
         return balls;
    }
}

    