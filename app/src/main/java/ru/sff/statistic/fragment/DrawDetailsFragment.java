package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sff.statistic.R;


public class DrawDetailsFragment extends Fragment {

    private static final String DRAW_NUMBER = "draw_number";

    private Integer mDrawNumber;

    public DrawDetailsFragment() {}

    public static DrawDetailsFragment newInstance( int drawNumber ) {
        DrawDetailsFragment fragment = new DrawDetailsFragment();
        Bundle args = new Bundle();
        args.putInt( DRAW_NUMBER, drawNumber );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mDrawNumber = getArguments().getInt( DRAW_NUMBER );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_draw_details, container, false );
    }



}
