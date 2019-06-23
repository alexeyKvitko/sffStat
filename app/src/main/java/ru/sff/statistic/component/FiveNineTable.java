package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

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
        inflate( context, R.layout.five_nine_table, this );
        mContext = context;
    }

    public FiveNineTable( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.five_nine_table, this );
        mContext = context;
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
        ballField.setBall( ball );
        return ballField;
    }

    public void fillFiveNineTable( List<Ball> balls){
        for( int rowId = 0; rowId < 8; rowId++ ){
            LinearLayout row = findViewById( ROW_IDS[ rowId ] );
            for( int cell = 0; cell < 6; cell++ ){
                int idx = 6*rowId+cell;
                if ( idx < 45 ){
                    row.addView( createBallView( balls.get( idx ) ) );
                }
            }
        }
    }

    public void setBallsInfo( BallsInfo ballsInfo ) {
        this.mBallsInfo = ballsInfo;
        fillFiveNineTable( mBallsInfo.getDrawBalls() );
    }
}
