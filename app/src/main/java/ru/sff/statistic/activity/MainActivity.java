package ru.sff.statistic.activity;


        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.Map;

        import ru.sff.statistic.AppConstants;
        import ru.sff.statistic.R;
        import ru.sff.statistic.SFFSApplication;
        import ru.sff.statistic.component.AppHeader;
        import ru.sff.statistic.utils.CustomAnimation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mRequestAll;
    private LinearLayout mRequestByDraw;
    private LinearLayout mRequestByDate;
    private LinearLayout mRequestByAmount;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initialize();
    }


    private void initialize(){
        AppHeader mainHeader = findViewById( R.id.mainHeaderId );
        mainHeader.setHeader( R.drawable.emoji_think, R.string.loto_6_45 );
        ( (TextView) findViewById( R.id.queryAllDrawsId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByDrawsId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByDateId)).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByAmountId)).setTypeface(AppConstants.ROBOTO_CONDENCED );

        mRequestAll = findViewById( R.id.requestAllDrawId );
        mRequestByDraw = findViewById( R.id.requestByDrawId );
        mRequestByDate = findViewById( R.id.requestByDateId );
        mRequestByAmount = findViewById( R.id.requestByAmountId );

        mRequestAll.setOnClickListener( this );
        mRequestByDraw.setOnClickListener( this );
        mRequestByDate.setOnClickListener( this );
        mRequestByAmount.setOnClickListener( this );

    }


    protected void startNewActivity( Class< ? > newClass ) {
        startNewActivity( newClass, null );
    }

    protected void startNewActivity( Class< ? > newClass, Map< String, String > params ) {
        Intent intent = new Intent( SFFSApplication.getAppContext(), newClass );
        intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        if ( params != null ) {
            for ( String key : params.keySet() ) {
                intent.putExtra( key, params.get( key ) );
            }
        }
        startActivity( intent );
        overridePendingTransition( R.anim.act_fade_in, R.anim.act_fade_out );
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.bounceAnimation( view );
        switch ( view.getId() ){
            case R.id.requestAllDrawId:
                startNewActivity(  RouteActivity.class );
                break;
        }
    }
}
