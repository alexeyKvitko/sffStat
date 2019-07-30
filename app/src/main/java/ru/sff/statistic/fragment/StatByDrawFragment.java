package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;


public class StatByDrawFragment extends TabbedFragment {


    public StatByDrawFragment() {}

    public static StatByDrawFragment newInstance(  ) {
        return new StatByDrawFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_stat_by_draw, container, false );
    }


    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        initTextView( R.id.statByDrawRequestLabelId, AppConstants.ROBOTO_CONDENCED );
    }

    @Override
    public void onClick( View view ) {

    }
}
