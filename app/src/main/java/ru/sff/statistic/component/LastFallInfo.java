package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.LotoModel;

public class LastFallInfo extends BaseComponent {

    public LastFallInfo( Context context ) {
        super( context );
        inflate( context, R.layout.last_fall_info, this );
        initialize();
    }

    public LastFallInfo( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.last_fall_info, this );
        initialize();
    }

    private void initialize(){
        initBaseComponent( this );
        LotoModel lotoModel = GlobalManager.getBootstrapModel().getLastFall();

        initTextView( R.id.lastFallDrawLabelId ,
                                        AppConstants.ROBOTO_BLACK ).setText( "Предыдущий тираж № " +
                                                                    lotoModel.getDraw().toString() );
        initTextView( R.id.lastFallDateTimeValueId ,
                AppConstants.ROBOTO_BLACK ).setText( GlobalManager.getBootstrapModel()
                                                    .getLastFallDay()+","+lotoModel.getDrawDate() );
        initTextView( R.id.superPrizeLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.superPrizeValueId, AppConstants.ROTONDA_BOLD ).setText( lotoModel.getSuperPrize() );

        SixBallSet lastFallDraw = findViewById( R.id.lastFallDrawId );
        Ball[] balls = new Ball[6];
        balls[0] = GlobalManager.getBallByNumber( lotoModel.getSlotOne() );
        balls[1] = GlobalManager.getBallByNumber( lotoModel.getSlotTwo() );
        balls[2] = GlobalManager.getBallByNumber( lotoModel.getSlotThree() );
        balls[3] = GlobalManager.getBallByNumber( lotoModel.getSlotFour() );
        balls[4] = GlobalManager.getBallByNumber( lotoModel.getSlotFive() );
        balls[5] = GlobalManager.getBallByNumber( lotoModel.getSlotSix() );
        lastFallDraw.setBallSet( balls, BallSetType.BIGGER );
        lastFallDraw.hideRepeatCaption();
    }


    @Override
    public void onClick( View view ) {

    }
}
