package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.utils.CustomAnimation;

public class FieldOrientation extends BaseComponent implements View.OnClickListener {

    public static final Map<Integer,Integer> DISBALED_SPIRAL = new HashMap<Integer, Integer>(  ){{
        put( R.id.orientation12RightId, R.drawable.spiral_12_right_disabe );
        put( R.id.orientation3RightId, R.drawable.spiral_3_right_disabe );
        put( R.id.orientation6RightId, R.drawable.spiral_6_right_disabe );
        put( R.id.orientation9RightId, R.drawable.spiral_9_right_disabe );
        put( R.id.orientation12LeftId, R.drawable.spiral_12_left_disabe );
        put( R.id.orientation3LeftId, R.drawable.spiral_3_left_disabe );
        put( R.id.orientation6LeftId, R.drawable.spiral_6_left_disabe );
        put( R.id.orientation9LeftId, R.drawable.spiral_9_left_disabe );
    }};

    public static final Map<Integer,Integer> ENABLED_SPIRAL = new HashMap<Integer, Integer>(  ){{
        put( R.id.orientation12RightId, R.drawable.spiral_12_right );
        put( R.id.orientation3RightId, R.drawable.spiral_3_right );
        put( R.id.orientation6RightId, R.drawable.spiral_6_right );
        put( R.id.orientation9RightId, R.drawable.spiral_9_right );
        put( R.id.orientation12LeftId, R.drawable.spiral_12_left );
        put( R.id.orientation3LeftId, R.drawable.spiral_3_left );
        put( R.id.orientation6LeftId, R.drawable.spiral_6_left );
        put( R.id.orientation9LeftId, R.drawable.spiral_9_left );
    }};

    public static final Map<Integer,Integer[]> FIELD_ORIENTATION = new HashMap<Integer, Integer[]>(  ){{
        put( R.id.orientation12RightId, AppConstants.BALL_12_RIGHT );
        put( R.id.orientation3RightId, AppConstants.BALL_3_RIGHT );
        put( R.id.orientation6RightId, AppConstants.BALL_6_RIGHT );
        put( R.id.orientation9RightId, AppConstants.BALL_9_RIGHT );
        put( R.id.orientation12LeftId, AppConstants.BALL_12_LEFT );
        put( R.id.orientation3LeftId, AppConstants.BALL_3_LEFT );
        put( R.id.orientation6LeftId, AppConstants.BALL_6_LEFT );
        put( R.id.orientation9LeftId, AppConstants.BALL_9_LEFT );
    }};

    private OnFieldOrientationListener mListener;

    public FieldOrientation( Context context ) {
        super( context );
        inflate(context, R.layout.field_orientation, this);
        init();
    }

    public FieldOrientation( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate(context, R.layout.field_orientation, this);
        init();
    }

    private void init(){
        initBaseComponent( this );
        setThisOnClickListener( R.id.orientation12LeftId, R.id.orientation3LeftId,
                                    R.id.orientation6LeftId, R.id.orientation9LeftId
                                    , R.id.orientation12RightId, R.id.orientation3RightId,
                                        R.id.orientation6RightId, R.id.orientation9RightId);
    }

    @Override
    public void onClick( View view ) {
        if( view instanceof ImageView ){
            CustomAnimation.clickAnimation( view );
            for(Integer imageId: DISBALED_SPIRAL.keySet() ){
                updateImageView( imageId, DISBALED_SPIRAL.get( imageId ) );
            }
            updateImageView( view.getId(), ENABLED_SPIRAL.get( view.getId() ) );
            GlobalManager.setFieldOrintation( FIELD_ORIENTATION.get( view.getId() ) );
            if( mListener !=null ){
                mListener.OnFieldOrientationSelect();
            }
        }
    }

    public void setOnFieldOrientationListener( OnFieldOrientationListener listener ){
        mListener = listener;
    }

    public interface OnFieldOrientationListener {
        void OnFieldOrientationSelect();
    }
}
