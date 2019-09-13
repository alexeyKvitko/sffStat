package ru.sff.statistic.component;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;

public class DonateWebView extends BaseComponent {


    public DonateWebView( Context context ) {
        super( context );
        inflate( context, R.layout.donate_web_view, this );
    }

    public DonateWebView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.donate_web_view, this );
    }

    public void initialize(final Activity activity, String payUrl){
        initBaseComponent( this );
        WebView webView = findViewById( R.id.webView );
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled( true );
        webView.setWebViewClient( new WebViewClient() );
        webView.loadUrl( payUrl );
        initTextView( R.id.webBtnBackId, AppConstants.ROBOTO_CONDENCED );
        findViewById( R.id.webViewBackBtnId ).setOnClickListener( ( View view )->{
            activity.onBackPressed();
        } );
    }


    @Override
    public void onClick(View view) {}
}
