package ru.sff.statistic.fragment;

import android.os.Bundle;
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

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.activity.RouteActivity;

public abstract class TabbedFragment extends BaseFragment{

    protected ImageView mBackButton;
    protected ViewPager mPager;
    protected TabLayout mTabs;
    protected ResultPagerAdapter mResultPagerAdapter;
    protected DrawsPlaneFragment mDrawsPlaneFragment;
    protected LotoDrawsFragment mLotoDrawsFragment;
    protected int mStartDraw;
    protected int mEndDraw;

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mBackButton = ( ( RouteActivity ) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );
    }

    protected void initTabs( int startDraw, int endDraw ){
        mStartDraw = startDraw;
        mEndDraw = endDraw;
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
                    mDrawsPlaneFragment = DrawsPlaneFragment.newInstance( mStartDraw, mEndDraw );
                    return mDrawsPlaneFragment;
                case 1:
                    mLotoDrawsFragment = LotoDrawsFragment.newInstance( mStartDraw, mEndDraw );
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
