package ru.madest.statistic.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;
import ru.madest.statistic.utils.AppUtils;

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
        circle.setColor( SFFSApplication.getAppContext().getResources().getColor( R.color.repeatColor ) );
        circle.setStrokeWidth( ( int ) AppUtils.convertDpToPixel( 4 ) );
        circle.setStrokeCap( Paint.Cap.SQUARE );
        canvas.drawCircle( mCenter, mCenter, mRadius, circle );
    }


}
