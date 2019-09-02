package ru.sff.statistic.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.utils.AppUtils;

public class Circle extends View {

    private int mRadius = ( int ) AppUtils.convertDpToPixel( 20 );
    private int mCenter = ( int ) AppUtils.convertDpToPixel( 45 ) / 2;

    public Circle( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public Circle( Context context, AttributeSet attrs ) {
        this( context, attrs, 0 );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
        Paint circle = new Paint( Paint.ANTI_ALIAS_FLAG );
        circle.setStyle( Paint.Style.STROKE );
        circle.setColor( SFFSApplication.getAppContext().getResources().getColor( R.color.ballPoppy ) );
        circle.setStrokeWidth( ( int ) AppUtils.convertDpToPixel( 3 ) );
        circle.setStrokeCap( Paint.Cap.SQUARE );
        canvas.drawCircle( mCenter, mCenter, mRadius, circle );
    }


}
