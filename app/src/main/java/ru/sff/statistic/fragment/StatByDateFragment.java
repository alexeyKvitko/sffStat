package ru.sff.statistic.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Calendar;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.DiscretSlider;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestDateType;
import ru.sff.statistic.utils.AppUtils;


public class StatByDateFragment extends BaseFragment {


    private RequestByDate mRequestByDate;

    private DiscretSlider mDaySlider;
    private DiscretSlider mMonthSlider;
    private DiscretSlider mDayWeekSlider;

    private AppCompatEditText mDayValue;
    private AppCompatEditText mMonthValue;

    private ThreeCellStat mDayMonthRequest;

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
        mRequestByDate = new RequestByDate();
        mRequestByDate.setRequestDateType( RequestDateType.BY_DAY );
        mRequestByDate.setDay( Calendar.getInstance().get( Calendar.DAY_OF_MONTH ) );
        mRequestByDate.setMonth( Calendar.getInstance().get( Calendar.MONTH ) + 1 );
        mRequestByDate.setDayOfWeek( Calendar.getInstance().get( Calendar.DAY_OF_WEEK ) - 1 );
        initByDaySlider( mRequestByDate.getDay(),
                getActivity().getResources().getString( R.string.by_date_label ) );
        initByMonthSlider( mRequestByDate.getMonth(),
                getActivity().getResources().getString( R.string.by_month_label ) );
        initByDayWeekSlider( mRequestByDate.getDayOfWeek(),
                getActivity().getResources().getString( R.string.by_day_week_label ) );
        initByDayMonth();

        setThisOnClickListener( R.id.daySliderId, R.id.monthSliderId, R.id.dayWeekSliderId, R.id.statByDayMonthRequesId );
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

    private void initByDayMonth() {
        mDayMonthRequest = getView().findViewById( R.id.statByDayMonthLabelId );
        mDayMonthRequest.setLeftCell( mRequestByDate.getDay()+"","" );
        mDayMonthRequest.setMiddleCell( AppConstants.ALL_OF_MONTH.get( mRequestByDate.getMonth() ),"" );
        initTextView( R.id.statByDayMonthTitleId, AppConstants.ROTONDA_BOLD );
        mDayValue = initEditText( R.id.statByDayValueId, AppConstants.ROBOTO_CONDENCED );
        mMonthValue = initEditText( R.id.statByMonthValueId, AppConstants.ROBOTO_CONDENCED );
        setEnableDayMonthRequest( false );
    }

    private void setEnableDayMonthRequest( boolean enabled ) {
        if ( enabled ) {
            mDayMonthRequest.setBackground( getActivity().getResources().getDrawable( R.drawable.border_left_yellow ) );
            mDayMonthRequest.enableComponent();
            mDayValue.setText( mRequestByDate.getDay() + "" );
            mMonthValue.setText( mRequestByDate.getMonth() + "" );
            initTextView( R.id.statByDayMonthTitleId ).setTextColor( getActivity().getResources().getColor( R.color.grayTextColor ) );
        } else {
            AppUtils.hideKeyboardFrom( getActivity(), mDayMonthRequest );
            mDayMonthRequest.setBackground( null );
            mDayMonthRequest.disableComponent();
            mDayValue.setText( null );
            mDayValue.clearFocus();
            mMonthValue.setText( null );
            mMonthValue.clearFocus();
            initTextView( R.id.statByDayMonthTitleId ).setTextColor( getActivity().getResources().getColor( R.color.transpGrayTextColor ) );
        }
        mDayValue.setEnabled( enabled );
        mMonthValue.setEnabled( enabled );
    }

    private boolean setRequestType( RequestDateType type ){
        if( mRequestByDate.getRequestDateType().equals( type ) ){
            return false;
        }
        mDaySlider.setEnableSlider( false );
        mMonthSlider.setEnableSlider( false );
        mDayWeekSlider.setEnableSlider( false );
        setEnableDayMonthRequest( false );
        mRequestByDate.setRequestDateType( type );
        return true;
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.daySliderId:
                if ( setRequestType( RequestDateType.BY_DAY ) ) {
                    mDaySlider.setEnableSlider( true );
                }
                break;
            case R.id.monthSliderId:
                if ( setRequestType( RequestDateType.BY_MONTH ) ) {
                    mMonthSlider.setEnableSlider( true );
                }
                break;
            case R.id.dayWeekSliderId:
                if ( setRequestType( RequestDateType.BY_DAY_WEEK ) ) {
                    mDayWeekSlider.setEnableSlider( true );
                }
                break;
            case R.id.statByDayMonthRequesId:
                if ( setRequestType( RequestDateType.BY_DAY_MONTH ) ) {
                    setEnableDayMonthRequest( true );
                }
                break;
            default:
                break;
        }
    }
}
