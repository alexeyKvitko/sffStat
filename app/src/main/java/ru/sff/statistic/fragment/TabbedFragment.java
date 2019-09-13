package ru.sff.statistic.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestByDraw;
import ru.sff.statistic.model.RequestBySumOrBall;
import ru.sff.statistic.model.RequestType;
import ru.sff.statistic.model.ResponseData;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppPreferences;
import ru.sff.statistic.utils.CustomAnimation;

public abstract class TabbedFragment extends BaseFragment{

    private static final String TAG = "TabbedFragment";

    protected ViewPager mPager;
    protected TabLayout mTabs;
    protected ResultPagerAdapter mResultPagerAdapter;
    protected DrawsPlaneFragment mDrawsPlaneFragment;
    protected LotoDrawsFragment mLotoDrawsFragment;
    protected RequestByDraw mRequestByDraw;
    protected RequestByDate mRequestByDate;
    protected RequestBySumOrBall mRequestBySOB;

    protected boolean mFirstRequest;

    protected int mMenuHeight;

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        clearRequests();
        mFirstRequest = true;
    }

    private void clearRequests(){
        mRequestByDraw = null;
        mRequestByDate = null;
        mRequestBySOB = null;
    }

    protected void fetchDrawData( RequestByDraw requestByDraw ){
        clearRequests();
        mRequestByDraw = requestByDraw;
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pagerId ), getView().findViewById( R.id.pleaseWaitContainerId ) );
        new GetLotoDrawResults().execute();
    }

    protected void fetchDateData( RequestByDate requestByDate ){
        clearRequests();
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pagerId ), getView().findViewById( R.id.pleaseWaitContainerId ) );
        mRequestByDate = requestByDate;
        new GetLotoDrawResults().execute();
    }

    protected void fetchSOBData( RequestBySumOrBall requestBySOB ){
        clearRequests();
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pagerId ), getView().findViewById( R.id.pleaseWaitContainerId ) );
        mRequestBySOB = requestBySOB;
        new GetLotoDrawResults().execute();
    }

    protected void initTabs(  ){
//        if ( !mFirstRequest ){
//            mDrawsPlaneFragment.getDrawsPlaneInfo().populateDrawsPlane();
//            mLotoDrawsFragment.populateLotoDraws();
//            mFirstRequest = false;
//        }
        mResultPagerAdapter = new ResultPagerAdapter( getChildFragmentManager() );
        mPager = getView().findViewById( R.id.pagerId );
        mPager.setAdapter( mResultPagerAdapter );
        mTabs = getView().findViewById( R.id.tabLayoutId );
        mTabs.setupWithViewPager( mPager );
        changeTabsFont();
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) mTabs.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView ) {
                    ((TextView) tabViewChild).setTypeface( AppConstants.ROTONDA_BOLD  );
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mBackButton.setOnClickListener( null );
    }

    public class ResultPagerAdapter extends FragmentPagerAdapter {

        public ResultPagerAdapter( FragmentManager fm ) {
            super( fm );
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem( int position ) {
            switch ( position ) {
                case 0:
                    mDrawsPlaneFragment = DrawsPlaneFragment.newInstance();
                    return mDrawsPlaneFragment;
                case 1:
                    boolean animatedHeader = false;
                    if ( mRequestByDraw != null ){
                        animatedHeader = RequestType.ALL_DRAW.equals(  mRequestByDraw.getRequestDrawType() );
                    }
                        mLotoDrawsFragment = LotoDrawsFragment.newInstance( animatedHeader );
                    return mLotoDrawsFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle( int position ) {
            String title = position == 0 ? SFFSApplication.getAppContext().getResources().getString( R.string.nav_tab_plane )
                    : SFFSApplication.getAppContext().getResources().getString( R.string.nav_tab_grid );
            return title;
        }
    }

    private class GetLotoDrawResults extends AsyncTask< Void, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            try {
                Call< ApiResponse< ResponseData > > resultCall = null;
                if ( mRequestByDraw != null) {
                    resultCall = RestController.getApi().getStatisticByDraw( AppPreferences.getUniqueId(), mRequestByDraw );
                } else if( mRequestByDate != null ){
                    resultCall = RestController.getApi().getStatisticByDate( AppPreferences.getUniqueId(), mRequestByDate );
                } else if( mRequestBySOB != null ){
                    resultCall = RestController.getApi().getStatisticBySOB( AppPreferences.getUniqueId(), mRequestBySOB );
                }
                if ( resultCall == null ){
                    return null;
                }
                Response< ApiResponse< ResponseData > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        ResponseData responseData = resultResponse.body().getResult();
                        GlobalManager.getCachedResponseData().setBallsInfo( responseData.getBallsInfo() );
                        GlobalManager.getCachedResponseData().setLotoModelDraws( responseData.getLotoDraws() );
                        GlobalManager.getCachedResponseData().setTotalDraw( responseData.getTotalDraw() );
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
            if ( result != null ) {
                ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    getActivity().onBackPressed();
                }, 2000 );
                return;
            }
            CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                    getView().findViewById( R.id.pagerId ) );
            initTabs();
        }
    }
}
