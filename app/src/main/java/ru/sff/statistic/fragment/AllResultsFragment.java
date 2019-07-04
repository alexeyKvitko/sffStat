package ru.sff.statistic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.activity.RouteActivity;


public class AllResultsFragment extends BaseFragment {

    private static final String TAG = "AllResultsFragment";

    private OnMenuOptionSelectListener mListener;

    private ImageView mBackButton;
    private ViewPager mPager;
    private TabLayout mTabs;
    private ResultPagerAdapter mResultPagerAdapter;
    private DrawsPlaneFragment mDrawsPlaneFragment;
    private LotoDrawsFragment mLotoDrawsFragment;


    public AllResultsFragment() {
    }

    public static AllResultsFragment newInstance() {
        AllResultsFragment fragment = new AllResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_all_result, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mResultPagerAdapter = new ResultPagerAdapter( getChildFragmentManager() );
        mPager = getView().findViewById( R.id.pagerId );
        mPager.setAdapter( mResultPagerAdapter );

        mTabs = getView().findViewById( R.id.tabLayoutId );
        mTabs.setupWithViewPager( mPager );
        changeTabsFont();
        mBackButton = ( ( RouteActivity ) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) mTabs.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface( AppConstants.ROTONDA_BOLD  );
                }
            }
        }
    }

    public void changeViewType() {
        mDrawsPlaneFragment.changeViewType();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBackButton.setOnClickListener( null );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnMenuOptionSelectListener ) {
            mListener = ( OnMenuOptionSelectListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnMenuOptionSelectListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick( View view ) {

    }

    public void showBallSetBasket() {
        if ( mListener != null ) {
            mListener.onBallSetBasketShow();
        }
    }

    public interface OnMenuOptionSelectListener {
        // TODO: Update argument type and name
        void onBallSetBasketShow();

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
                    mLotoDrawsFragment = LotoDrawsFragment.newInstance();
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



}
