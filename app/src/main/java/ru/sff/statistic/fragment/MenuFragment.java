package ru.sff.statistic.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sff.statistic.R;


public class MenuFragment extends BaseFragment {

    private OnMenuOptionSelectListener mListener;

    public MenuFragment() {}

    public static MenuFragment newInstance( ) {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
          return inflater.inflate( R.layout.fragment_menu, container, false );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed( ) {
        if ( mListener != null ) {
            mListener.onMainStatisticSelect(  );
        }
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

    public interface OnMenuOptionSelectListener {
        // TODO: Update argument type and name
        void onMainStatisticSelect();
    }
}
