package ru.sff.statistic.component;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import ru.sff.statistic.R;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallsInfo;

public class FiveNineTable extends LinearLayout {

    private static final int ROW_IDS[] = new int[]{ R.id.fiveNineRowOneId, R.id.fiveNineRowTwoId
                                            ,R.id.fiveNineRowThreeId, R.id.fiveNineRowFourId
                                            ,R.id.fiveNineRowFiveId, R.id.fiveNineRowSixId
                                            ,R.id.fiveNineRowSevenId, R.id.fiveNineRowEightId
                                            ,R.id.fiveNineRowNineId };

    private BallsInfo mBallsInfo;

    private Context mContext;

    public FiveNineTable( Context context ) {
        super( context );
        inflate( context, R.layout.ball_field, this );
        mContext = context;
        clearTable();
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
        BallField ballField = new BallField( mContext );
        ballField.setBallNumberValue( ball.getBallNumber() );
        ballField.setBallRepeatValue( ball.getBallRepeat() );
        return ballField;
    }

    private void fillFiveNineTable( List<Ball> balls){
        for( int rowId = 0; rowId < 9; rowId++ ){
            for( int cell = 1; cell < 6; cell++ ){
                LinearLayout row = findViewById( rowId );
                row.addView( createBallView( balls.get( 5*rowId+cell ) ) );
            }
        }
    }

    public void setBallsInfo( BallsInfo ballsInfo ) {
        this.mBallsInfo = ballsInfo;
        fillFiveNineTable( mBallsInfo.getDrawBalls() );
    }
}
