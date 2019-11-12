package ru.madest.statistic.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.SFFSApplication;

public abstract class BaseComponent extends LinearLayout implements View.OnClickListener {

    public BaseComponent( Context context ) {
        super( context );
    }

    public BaseComponent( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    protected View mView;

    protected void initBaseComponent( View view ){
        mView = view;
    }

    //TEXT VIEW
    protected TextView initTextView( int textId, Typeface typeface, Integer style, int stringId ){
        TextView textView = mView.findViewById( textId );
        String text = AppConstants.FAKE_ID != stringId
                    ? SFFSApplication.getAppContext().getResources().getString( stringId ) : null;
        if( typeface != null ){
            if( style != null ){
                textView.setTypeface( typeface, style );
            } else {
                textView.setTypeface( typeface );
            }
        }
        if ( text != null ){
            textView.setText( text );
        }
        return textView;
    }

    protected TextView initTextView( int textId, Typeface typeface,  int stringId ){
        return initTextView( textId, typeface, null, stringId );
    }

    protected TextView initTextView( int textId,  int stringId ){
        return initTextView( textId, null, stringId );
    }

    protected TextView initTextView( int textId,  Typeface typeface ){
        return initTextView( textId, typeface, AppConstants.FAKE_ID );
    }

    protected TextView initTextView( int textId  ){
        return initTextView( textId, null, AppConstants.FAKE_ID );
    }

    //EDIT TEXT

    protected AppCompatEditText initEditText( int editId, Typeface typeface, Integer style ){
        AppCompatEditText editText = mView.findViewById( editId );
        if( typeface != null ){
            if( style != null ){
                editText.setTypeface( typeface, style );
            } else {
                editText.setTypeface( typeface );
            }
        }
        return editText;
    }

    protected AppCompatEditText initEditText( int editId, Typeface typeface ){
        return initEditText( editId, typeface, null  );
    }

    protected AppCompatEditText initEditText( int editId  ){
        return initEditText( editId, null, null );
    }

    //TEXT LAYOUT
    protected TextInputLayout initTextInputLayout( int textLayoutId, Typeface typeface ){
        TextInputLayout textLayout = mView.findViewById( textLayoutId );
        if( typeface != null ){
            textLayout.setTypeface( typeface );
        }
        return textLayout;
    }

    protected TextInputLayout initTextInputLayout( int textLayoutId ){
        return initTextInputLayout( textLayoutId , null );
    }

    //BUTTON
    protected Button initButton( int buttonId, Typeface typeface ){
        Button button = mView.findViewById( buttonId );
        if( typeface != null ){
            button.setTypeface( typeface );
        }
        return button;
    }

    //IMAGE VIEW
    protected ImageView initImageView( int imageId, Integer drawableId ){
        ImageView imageView = mView.findViewById( imageId );
        if( drawableId != null ){
            imageView.setImageDrawable( SFFSApplication.getAppContext().getResources().getDrawable( drawableId ) );
        }
        return imageView;
    }

    protected ImageView updateImageView( int imageId, Integer drawableId ){
        return initImageView( imageId, drawableId );
    }

    protected void setThisOnClickListener( int ... ids ){
        for( int id : ids ){
            mView.findViewById( id ).setOnClickListener( ( View.OnClickListener ) this );
        }
    }
}
