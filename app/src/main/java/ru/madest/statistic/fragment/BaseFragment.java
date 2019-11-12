package ru.madest.statistic.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.activity.RouteActivity;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected IntentFilter mFloatMenuIntentFilter = new IntentFilter( AppConstants.FLOAT_MENU_MESSAGE );
    protected FloatMenuActionReceiver mFloatMenuReceiver = new FloatMenuActionReceiver();

    private Fragment mFragment;

    protected ImageView mBackButton;

    protected boolean mIsReady;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBackButton = ( (RouteActivity) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );
    }

    //TEXT VIEW
    protected TextView initTextView( int textId, Typeface typeface, Integer style, String text ) {
        TextView textView = getView().findViewById( textId );
        if ( typeface != null ) {
            if ( style != null ) {
                textView.setTypeface( typeface, style );
            } else {
                textView.setTypeface( typeface );
            }
        }
        if ( text != null ) {
            textView.setText( text );
        }
        return textView;
    }

    protected TextView initTextView( int textId, Typeface typeface, String text ) {
        return initTextView( textId, typeface, null, text );
    }

    protected TextView initTextView( int textId, String text ) {
        return initTextView( textId, null, text );
    }

    protected TextView initTextView( int textId, Typeface typeface ) {
        return initTextView( textId, typeface, null );
    }

    protected TextView initTextView( int textId ) {
        return initTextView( textId, null, null );
    }

    //EDIT TEXT

    protected AppCompatEditText initEditText( int editId, Typeface typeface, Integer style ) {
        AppCompatEditText editText = getView().findViewById( editId );
        if ( typeface != null ) {
            if ( style != null ) {
                editText.setTypeface( typeface, style );
            } else {
                editText.setTypeface( typeface );
            }
        }
        return editText;
    }

    protected AppCompatEditText initEditText( int editId, Typeface typeface ) {
        return initEditText( editId, typeface, null );
    }

    protected AppCompatEditText initEditText( int editId ) {
        return initEditText( editId, null, null );
    }

    //TEXT LAYOUT
    protected TextInputLayout initTextInputLayout( int textLayoutId, Typeface typeface ) {
        TextInputLayout textLayout = getView().findViewById( textLayoutId );
        if ( typeface != null ) {
            textLayout.setTypeface( typeface );
        }
        return textLayout;
    }

    protected TextInputLayout initTextInputLayout( int textLayoutId ) {
        return initTextInputLayout( textLayoutId, null );
    }

    //BUTTON
    protected Button initButton( int buttonId, Typeface typeface ) {
        Button button = getView().findViewById( buttonId );
        if ( typeface != null ) {
            button.setTypeface( typeface );
        }
        return button;
    }

    protected void setThisOnClickListener( int... ids ) {
        for ( int id : ids ) {
            getView().findViewById( id ).setOnClickListener( ( View.OnClickListener ) this );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragment = this;
        mIsReady = true;
        getActivity().registerReceiver( mFloatMenuReceiver, mFloatMenuIntentFilter );
    }

    @Override
    public void onPause() {
        mIsReady = false;
        getActivity().unregisterReceiver( mFloatMenuReceiver );
        super.onPause();
    }


    class FloatMenuActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive( Context context, Intent intent ) {
            String floatMenuAction = intent.getStringExtra( AppConstants.FLOAT_MENU_ACTION );
            switch ( floatMenuAction ) {
                case AppConstants.FLOAT_MENU_CHANGE_VIEW_TYPE:
                    if ( mFragment instanceof AllResultsFragment ) {
                        ( ( AllResultsFragment ) mFragment ).changeViewType();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
