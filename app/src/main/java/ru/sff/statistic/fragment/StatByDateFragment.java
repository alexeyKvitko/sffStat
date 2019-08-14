package ru.sff.statistic.fragment;


import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.DiscretSlider;
import ru.sff.statistic.component.DoubleSlider;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.CachedResponseData;
import ru.sff.statistic.model.HeaderModel;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestType;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

import static ru.sff.statistic.manager.GlobalManager.getCachedResponseData;


public class StatByDateFragment extends TabbedFragment implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_CONTAINER_COLLAPSE_HEIGHT = -( int ) AppUtils.convertDpToPixel( 552 );

    private DiscretSlider mDaySlider;
    private DiscretSlider mMonthSlider;
    private DiscretSlider mDayWeekSlider;

    private DoubleSlider mDoubleSlider;

    private LinearLayout mStatByDateRequestContainer;

    private TextView mPeriodValue;

    private ImageView mShowRequestForm;
    private Button mRequestButton;
    private boolean mRequestContainerShown;

    public StatByDateFragment() {
    }

    public static StatByDateFragment newInstance() {
        StatByDateFragment fragment = new StatByDateFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_stat_by_date, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mRequestContainerShown = true;
        mStatByDateRequestContainer = getView().findViewById( R.id.statByDateRequestContainerId );
        mShowRequestForm = getView().findViewById( R.id.statByDateShowFormId );
        mShowRequestForm.setVisibility( View.GONE );
        mRequestButton = getView().findViewById( R.id.statByDateRequestFormId );
        mRequestButton.setVisibility( View.VISIBLE );
        RequestType defType = null;
        if ( getCachedResponseData() != null
                &&  !RequestType.DRAW_BETWEEN.equals( getCachedResponseData().getLastRequest() )
                &&  !RequestType.ALL_DRAW.equals( getCachedResponseData().getLastRequest() )){
            animateRequestContainer( 0, REQUEST_CONTAINER_COLLAPSE_HEIGHT );
            mRequestContainerShown = false;
            mRequestByDate = getCachedResponseData().getRequestByDate();
            defType = mRequestByDate.getRequestType();
            mRequestByDate.setRequestType( null );
            mFirstRequest = false;
            initTabs();
        } else {
            mRequestByDate = new RequestByDate();
            defType = RequestType.BY_DAY;
            mRequestByDate.setDay( Calendar.getInstance().get( Calendar.DAY_OF_MONTH ) );
            mRequestByDate.setMonth( Calendar.getInstance().get( Calendar.MONTH ) + 1 );
            mRequestByDate.setDayOfWeek( Calendar.getInstance().get( Calendar.DAY_OF_WEEK ) - 1 );
            mRequestByDate.setDayNumber( mRequestByDate.getDay() );
            mRequestByDate.setMonthNumber( mRequestByDate.getMonth() );
            mRequestByDate.setStartDay( AppUtils.formatDate( AppConstants.DATE_FORMAT, new Date() ) );
            mRequestByDate.setEndDay( AppUtils.formatDate( AppConstants.DATE_FORMAT, new Date() ) );
        }


        initByDaySlider( mRequestByDate.getDay(),
                getActivity().getResources().getString( R.string.by_date_label ) );
        initByMonthSlider( mRequestByDate.getMonth(),
                getActivity().getResources().getString( R.string.by_month_label ) );
        initByDayWeekSlider( mRequestByDate.getDayOfWeek(),
                getActivity().getResources().getString( R.string.by_day_week_label ) );
        initByDayMonthAndPeriod();

        setThisOnClickListener( R.id.daySliderId, R.id.monthSliderId, R.id.dayWeekSliderId, R.id.statByDateShowFormId,
                R.id.periodSliderId, R.id.statByDayPeriodRequesId, R.id.statByDateRequestFormId );
        setRequestType( defType );
    }

    private void initByDaySlider( int day, String title ) {
        mDaySlider = getView().findViewById( R.id.daySliderId );
        mDaySlider.initSlider( 1, 31, day, title, DiscretSlider.DISCRET_SLIDER_DAY );
    }

    private void initByMonthSlider( int month, String title ) {
        mMonthSlider = getView().findViewById( R.id.monthSliderId );
        mMonthSlider.initSlider( 1, 12, month, title, DiscretSlider.DISCRET_SLIDER_MONTH );
        mMonthSlider.setEnableSlider( false );
    }

    private void initByDayWeekSlider( int dayWeek, String title ) {
        mDayWeekSlider = getView().findViewById( R.id.dayWeekSliderId );
        mDayWeekSlider.initSlider( 1, 7, dayWeek, title, DiscretSlider.DISCRET_SLIDER_DAY_WEEK );
        mDayWeekSlider.setEnableSlider( false );
    }

    private void initByDayMonthAndPeriod() {
        mDoubleSlider = getView().findViewById( R.id.periodSliderId );
        mDoubleSlider.initDoubleSlider( 1, 31, mRequestByDate.getDayNumber(),
                1, 12, mRequestByDate.getMonthNumber(), getActivity().getResources().getString( R.string.by_day_month_label ) );
        mDoubleSlider.setEnableSlider( false );

        List< String > dataset = new LinkedList<>();
        for ( int i = 1; i < 32; i++ ) {
            dataset.add( i + "" );
        }
        initTextView( R.id.statByPeriodTitleId, AppConstants.ROTONDA_BOLD );
        mPeriodValue = initTextView( R.id.statPeriodValueId, AppConstants.ROTONDA_BOLD );
        setPeriodValue( false );
    }

    private void setPeriodValue( boolean enabled ) {
        if ( enabled ) {
            mPeriodValue.setTextColor( getActivity().getResources().getColor( R.color.shokoColor ) );
            initTextView( R.id.statByPeriodTitleId ).setTextColor( getActivity().getResources().getColor( R.color.grayTextColor ) );
            mPeriodValue.setBackground( getActivity().getResources().getDrawable( R.drawable.border_bottom ) );
            mPeriodValue.setOnClickListener( this );
        } else {
            int color = getActivity().getResources().getColor( R.color.transpGrayTextColor );
            mPeriodValue.setTextColor( color );
            initTextView( R.id.statByPeriodTitleId ).setTextColor( color );
            mPeriodValue.setOnClickListener( null );
            mPeriodValue.setBackground( null );
        }
        mPeriodValue.setText( "C " + mRequestByDate.getStartDay() + " по " + mRequestByDate.getEndDay() );
    }


    private void setRequestType( RequestType type ) {
        if ( type.equals( mRequestByDate.getRequestType() ) ) {
            return;
        }
        mDaySlider.setEnableSlider( false );
        mMonthSlider.setEnableSlider( false );
        mDayWeekSlider.setEnableSlider( false );
        mDoubleSlider.setEnableSlider( false );
        setPeriodValue( false );
        mRequestByDate.setRequestType( type );
        switch ( type ){
            case BY_DAY:
                mDaySlider.setEnableSlider( true );
                break;
            case BY_MONTH:
                mMonthSlider.setEnableSlider( true );
                break;
            case BY_DAY_WEEK:
                mDayWeekSlider.setEnableSlider( true );
                break;
            case BY_DAY_MONTH:
                mDoubleSlider.setEnableSlider( true );
                break;
            case BY_PERIOD:
                setPeriodValue( true );
                break;
        }
    }

    private void showPeriodPicker() {
        Calendar min = Calendar.getInstance();
        min.set( 2008, 10, 10 );
        Calendar start = Calendar.getInstance();
        start.setTime( AppUtils.parseDate( AppConstants.DATE_FORMAT, mRequestByDate.getStartDay() ) );
        Calendar end = Calendar.getInstance();
        end.setTime( AppUtils.parseDate( AppConstants.DATE_FORMAT, mRequestByDate.getEndDay() ) );
        DatePickerDialog periodDialog = DatePickerDialog.newInstance(
                this,
                start.get( Calendar.YEAR ),
                start.get( Calendar.MONTH ),
                start.get( Calendar.DAY_OF_MONTH ),
                end.get( Calendar.YEAR ),
                end.get( Calendar.MONTH ),
                end.get( Calendar.DAY_OF_MONTH )

        );
        periodDialog.setMinDate( min );
        periodDialog.setMaxDate( end );
        periodDialog.setAccentColor( getActivity().getResources().getColor( R.color.ballYellow ) );
        periodDialog.show( getActivity().getFragmentManager(), "Выбор периода" );
    }

    private void postStatByDateRequest() {
        mRequestByDraw = null;
        mRequestByDate.setDay( mDaySlider.getSliderValue() );
        mRequestByDate.setMonth( mMonthSlider.getSliderValue() );
        mRequestByDate.setDayOfWeek( mDayWeekSlider.getSliderValue() );
        mRequestByDate.setDayNumber( mDoubleSlider.getSliderOneValue() );
        mRequestByDate.setMonthNumber( mDoubleSlider.getSliderTwoValue() );

        if ( GlobalManager.getCachedResponseData() == null ) {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
        }
        GlobalManager.getCachedResponseData().setLastRequest( mRequestByDate.getRequestType() );
        GlobalManager.getCachedResponseData().setRequestByDate( mRequestByDate );
        GlobalManager.getCachedResponseData().setRequestByDraw( null );
        animateRequestContainer( 0, REQUEST_CONTAINER_COLLAPSE_HEIGHT );
        setAppHeader();
        mRequestContainerShown = false;
    }

    private void setAppHeader() {
        String header = "По Датам";
        switch ( mRequestByDate.getRequestType() ) {
            case BY_DAY:
                header = "за " + mRequestByDate.getDay() + " день месяца";
                break;
            case BY_MONTH:
                header = "за " + AppConstants.ALL_OF_MONTH.get( mRequestByDate.getMonth() ) + " месяц";
                break;
            case BY_DAY_WEEK:
                header = "по " + AppConstants.ALL_DAY_OF_WEEK_SFX.get( mRequestByDate.getDayOfWeek() );
                break;
            case BY_DAY_MONTH:
                header = "за " + mRequestByDate.getDayNumber() + " " + AppConstants.ALL_MONTH_SFX[ mRequestByDate.getMonthNumber() ];
                break;
            case BY_PERIOD:
                header = "c " + mRequestByDate.getStartDay() + " по " + mRequestByDate.getEndDay();
                break;
            default:
                break;

        }
        ( ( RouteActivity ) getActivity() ).getAppHeader().setHeader( new HeaderModel( R.drawable.emoji_look, header ) );
    }

    private void animateRequestContainer( int start, int end ) {
        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams ) mStatByDateRequestContainer.getLayoutParams();
        if ( start > end ) {
            CustomAnimation.transitionAnimation( mRequestButton, mShowRequestForm );
        } else {
            CustomAnimation.transitionAnimation( mShowRequestForm, mRequestButton );
        }
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.topMargin = val;
            mStatByDateRequestContainer.setLayoutParams( layoutParams );
            if ( REQUEST_CONTAINER_COLLAPSE_HEIGHT == end && val <= end ) {
                CustomAnimation.transitionAnimation( getView().findViewById( R.id.pagerId ), getView().findViewById( R.id.pleaseWaitContainerId ) );
                fetchDateData( mRequestByDate );
            }
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.daySliderId:
                setRequestType( RequestType.BY_DAY );
                break;
            case R.id.monthSliderId:
                setRequestType( RequestType.BY_MONTH );
                break;
            case R.id.dayWeekSliderId:
                setRequestType( RequestType.BY_DAY_WEEK );
                break;
            case R.id.periodSliderId:
                setRequestType( RequestType.BY_DAY_MONTH );
                break;
            case R.id.statByDayPeriodRequesId:
                setRequestType( RequestType.BY_PERIOD );
                break;
            case R.id.statPeriodValueId:
                showPeriodPicker();
                break;
            case R.id.statByDateRequestFormId:
                if ( mRequestContainerShown ) {
                    postStatByDateRequest();
                }
                break;
            case R.id.statByDateShowFormId:
                if ( !mRequestContainerShown ) {
                    animateRequestContainer( REQUEST_CONTAINER_COLLAPSE_HEIGHT, 0 );
                    mRequestContainerShown = true;
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onDateSet( DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd ) {
        Date startDate = new GregorianCalendar( year, monthOfYear, dayOfMonth ).getTime();
        Date endDate = new GregorianCalendar( yearEnd, monthOfYearEnd, dayOfMonthEnd ).getTime();
        if ( endDate.before( startDate ) ) {
            Date tempDate = startDate;
            startDate = endDate;
            endDate = tempDate;
        }
        mRequestByDate.setStartDay( AppUtils.formatDate( AppConstants.DATE_FORMAT, startDate ) );
        mRequestByDate.setEndDay( AppUtils.formatDate( AppConstants.DATE_FORMAT, endDate ) );
        setPeriodValue( true );
    }
}
