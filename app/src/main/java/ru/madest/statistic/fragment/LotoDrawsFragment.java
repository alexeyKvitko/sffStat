package ru.madest.statistic.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.component.ArrowSelector;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.modal.ModalMessage;
import ru.madest.statistic.model.ApiResponse;
import ru.madest.statistic.model.LotoModel;
import ru.madest.statistic.model.RequestType;
import ru.madest.statistic.recycleview.LotoDrawAdapter;
import ru.madest.statistic.recycleview.StickyRecyclerView;
import ru.madest.statistic.rest.RestController;
import ru.madest.statistic.utils.AppPreferences;
import ru.madest.statistic.utils.AppUtils;


public class LotoDrawsFragment extends BaseFragment implements LotoDrawAdapter.
        LotoDrawDataObjectHolder.LotoDrawDetailsListener,
        StickyRecyclerView.OnActionHeaderListener {

    private static final String TAG = "LotoDrawsFragment";

    private static final String HEADER_ANIMATED = "header_animated";

    private static final int REC_VIEW_CARD_HEIGHT = 24;
    private static final int HEADER_EXPAND_MARGIN_TOP = 94;
    private static final int HEADER_COLLAPSE_MARGIN_TOP = 16;
    private static final int HEADER_COLLAPSE_ANIMATION_MARGIN = 94;
    private static final int HEADER_ANIMATION_DURATION = 600;

    public OnDrawDetailsClickListener mListener;

    private StickyRecyclerView mLotoDrawRecView;
    private LotoDrawAdapter mLotoDrawAdapter;
    private ArrowSelector mMonthArrowSelector;
    private ArrowSelector mYearArrowSelector;
    private SwipeRefreshLayout mRefresh;
    private LinearLayout mHeaderContainer;

    private List< LotoModel > mLotoModelDraws;

    private String mSelectedMonth;
    private Integer mSelectedtYear;

    private Integer mMonthIndex = 0;
    private Integer mYearIndex = 0;

    private boolean mHeaderAnimated;


    public LotoDrawsFragment() {
    }

    public static LotoDrawsFragment newInstance( boolean headerAnimated ) {
        LotoDrawsFragment fragment = new LotoDrawsFragment();
        Bundle args = new Bundle();
        args.putBoolean( HEADER_ANIMATED, headerAnimated );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mHeaderAnimated = getArguments().getBoolean( HEADER_ANIMATED );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        mIsReady = true;
        return inflater.inflate( R.layout.fragment_loto_draws, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        initialize();
        populateLotoDraws();
    }

    private void initialize() {
        initTextView( R.id.periodLabelId, AppConstants.ROTONDA_BOLD );

        mHeaderContainer = getView().findViewById( R.id.headerSelectPeriodId );
        mMonthArrowSelector = getView().findViewById( R.id.monthArrowSelectorId );
        mYearArrowSelector = getView().findViewById( R.id.yearArrowSelectorId );
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get( Calendar.YEAR );
        mSelectedtYear = currentYear;
        mSelectedMonth = getActivity().getResources().getStringArray( R.array.monthes )[0];
        String[] years = new String[ ( currentYear - 2008 ) + 1 ];
        int idx = 0;
        for ( int year = currentYear; year >= 2008; year-- ) {
            years[ idx ] = year + "";
            idx++;
        }
        mMonthArrowSelector.setValues( getActivity().getResources().getStringArray( R.array.monthes ), mMonthIndex );
        mYearArrowSelector.setValues( years, mYearIndex );
        mRefresh = getView().findViewById( R.id.swipeRefreshLayoutId );
        if ( mHeaderAnimated ) {
            mHeaderContainer.setVisibility( View.VISIBLE );
        }
        if ( RequestType.ALL_DRAW.equals( GlobalManager.getCachedResponseData().getLastRequest() ) ) {
            mRefresh.setOnRefreshListener( () -> {
                if ( !mSelectedMonth.equals( mMonthArrowSelector.getValue() ) ||
                        !mSelectedtYear.equals( Integer.valueOf( mYearArrowSelector.getValue() ) )  ) {
                    mRefresh.setRefreshing( true );
                    new GetLotoDraws().execute();
                } else {
                    mRefresh.setRefreshing( false );
                }
            } );
        } else {
            mRefresh.setOnRefreshListener( () -> {
                mRefresh.setRefreshing( false );
            } );
        }


    }


    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnDrawDetailsClickListener ) {
            mListener = ( OnDrawDetailsClickListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnDrawDetailsClickListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecView() {
        if ( mLotoDrawRecView == null ) {
            mLotoDrawRecView = getView().findViewById( R.id.lotoDrawRVId );
            mLotoDrawRecView.setOnActionHeaderListener( this );
            mLotoDrawRecView.initialize( mLotoDrawAdapter, mRefresh, REC_VIEW_CARD_HEIGHT
                    , HEADER_EXPAND_MARGIN_TOP, HEADER_COLLAPSE_MARGIN_TOP );
        }
        mLotoDrawRecView.setHeaderGone( !mHeaderAnimated );
        mLotoDrawRecView.getAdapter().notifyDataSetChanged();
    }

    private void initAdapter() {
        fillLotoDrawAdapter( mLotoModelDraws );
        mLotoDrawAdapter.setLotoDrawDetailsListener( this );
        mLotoDrawAdapter.notifyDataSetChanged();
    }

    private void fillLotoDrawAdapter( List< LotoModel > entities ) {
        if ( mLotoDrawAdapter == null ) {
            mLotoDrawAdapter = new LotoDrawAdapter( new LinkedList<>() );
        } else {
            mLotoDrawAdapter.deleteAllItem();
        }
        int el = 0;
        for ( LotoModel lotoModel : entities ) {
            mLotoDrawAdapter.addItem( lotoModel, el );
            el++;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( mLotoDrawRecView != null ) {
            mLotoDrawRecView.setAdapter( null );
            mLotoDrawRecView = null;
        }
        mLotoDrawAdapter = null;
    }

    @Override
    public void onClick( View view ) {
    }

    public void populateLotoDraws() {
        if ( mIsReady ) {
            mLotoModelDraws = GlobalManager.getCachedResponseData().getLotoModelDraws();
            initAdapter();
            initRecView();
        }
    }

    @Override
    public void onLotoDrawDetailsClick( String drawStr ) {
        if ( mListener != null ) {
            Integer draw = Integer.valueOf( drawStr );
            for ( LotoModel lotoModel : mLotoModelDraws ) {
                if ( lotoModel.getDraw() != null && lotoModel.getDraw().equals( draw ) ) {
                    mListener.onDrawDetailsClick( lotoModel.getDraw() );
                    break;
                }
            }
        }
    }

    @Override
    public void onRemoveHeaderAction() {
        if ( !mHeaderAnimated ) {
            return;
        }
        TranslateAnimation slideUp = new TranslateAnimation( 0, 0, 0,
                -AppUtils.convertDpToPixel( HEADER_COLLAPSE_ANIMATION_MARGIN ) );
        slideUp.setDuration( HEADER_ANIMATION_DURATION );
        slideUp.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart( Animation animation ) {
                mLotoDrawRecView.setAnimateHeader( true );
            }

            @Override
            public void onAnimationEnd( Animation animation ) {
                mHeaderContainer.setVisibility( View.GONE );
                mLotoDrawRecView.setAnimateHeader( false );
            }

            @Override
            public void onAnimationRepeat( Animation animation ) {
            }
        } );
        mHeaderContainer.startAnimation( slideUp );
    }

    @Override
    public void onRestoreHeaderAction() {
        if ( !mHeaderAnimated ) {
            return;
        }
        TranslateAnimation slideDown = new TranslateAnimation( 0, 0,
                -AppUtils.convertDpToPixel( HEADER_COLLAPSE_ANIMATION_MARGIN ), 0 );
        slideDown.setDuration( HEADER_ANIMATION_DURATION );
        slideDown.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart( Animation animation ) {
                mHeaderContainer.setVisibility( View.VISIBLE );
                mLotoDrawRecView.setAnimateHeader( true );
            }

            @Override
            public void onAnimationEnd( Animation animation ) {
                mLotoDrawRecView.setAnimateHeader( false );
            }

            @Override
            public void onAnimationRepeat( Animation animation ) {
            }
        } );
        mHeaderContainer.startAnimation( slideDown );

    }

    public interface OnDrawDetailsClickListener {
        void onDrawDetailsClick( Integer draw );
    }

    private class GetLotoDraws extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            GlobalManager.setBackendBusy( true );
            try {
                mSelectedMonth = mMonthArrowSelector.getValue();
                mSelectedtYear = Integer.valueOf( mYearArrowSelector.getValue() );
                mMonthIndex = mMonthArrowSelector.getCurrentIndex();
                mYearIndex = mYearArrowSelector.getCurrentIndex();
                Call< ApiResponse< List< LotoModel > > > resultCall = null;
                resultCall = RestController.getApi().getLotoDrawsByMonthAndYear( AppPreferences.getUniqueId(),
                        mSelectedMonth, mSelectedtYear );
                Response< ApiResponse< List< LotoModel > > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mLotoModelDraws = resultResponse.body().getResult();
                        GlobalManager.getCachedResponseData().setLotoModelDraws( mLotoModelDraws );
                    } else {
                        result = resultResponse.body().getMessage();
                    }
                } else {
                    result = getResources().getString( R.string.internal_error );
                }
            } catch ( Exception e ) {
                result = getResources().getString( R.string.internal_error );
                Log.i( TAG, e.getMessage() );
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );
            GlobalManager.setBackendBusy( false );
            mRefresh.setRefreshing( false );
            if ( result != null ) {
                ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    getActivity().onBackPressed();
                }, 2000 );
                return;
            }
            populateLotoDraws();
        }
    }

}
