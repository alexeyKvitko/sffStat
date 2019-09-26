package ru.sff.statistic.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.DonateWebView;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppPreferences;
import ru.sff.statistic.utils.CustomAnimation;


public class DonateFragment extends BaseFragment {

    private static final String TAG = "DonateFragment";

    private static final Map<Integer, Integer> DONATE_IDS = new HashMap<Integer, Integer>(){{
        put( R.id.donateHundredId, 100 );
        put( R.id.donateTwoHundredId, 200 );
        put( R.id.donateThreeHundredId, 300 );
        put( R.id.donateFiveHundredId, 500 );
        put( R.id.donateSevenHundredId, 700 );
        put( R.id.donateThousandId, 1000 );
    }};

    private OnDonateChooseListener mListener;
    private int mDonateSum;
    private String mPaymentUrl;

    public DonateFragment() {}

    public static DonateFragment newInstance() {
        DonateFragment fragment = new DonateFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_donate, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mDonateSum = 100;
        initTextView( R.id.donateLabelId, AppConstants.ROTONDA_BOLD );

        initTextView( R.id.donateHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateTwoHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateThreeHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateFiveHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateSevenHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateThousandId, AppConstants.ROTONDA_BOLD );

        setThisOnClickListener( R.id.donateHundredId, R.id.donateTwoHundredId, R.id.donateThreeHundredId,
                R.id.donateFiveHundredId, R.id.donateSevenHundredId, R.id.donateThousandId );

        initButton( R.id.donatePostBtnId, AppConstants.ROBOTO_CONDENCED ).setOnClickListener( (View view ) ->{
            new GetPaymentUrl().execute();
        });
    }

    private void showWebDonate(){
        LinearLayout chooseContainer = getView().findViewById( R.id.donateChooseContainerId );
        chooseContainer.setVisibility( View.GONE );
        ((RouteActivity) getActivity()).getAppHeader().hideHeader();
        WebView webView = getView().findViewById( R.id.donateWebViewId );
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled( true );
        webView.setWebViewClient( new WebViewClient() );
        webView.loadUrl( mPaymentUrl );
        webView.setVisibility( View.VISIBLE );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnDonateChooseListener ) {
            mListener = ( OnDonateChooseListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnDonateChooseListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause () {
        super.onPause();
        ((RouteActivity) getActivity()).getAppHeader().showHeader();
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        for( int id: DONATE_IDS.keySet() ){
            if( view.getId() == id ){
                mDonateSum = DONATE_IDS.get( id );
            }
            (( TextView) getView().findViewById( id )).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_checkbox_blank_outline_black_18dp, 0, 0, 0 );
        }
        (( TextView) view ).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_outline_black_18dp, 0, 0, 0 );

    }

    private class GetPaymentUrl extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... amounts ) {
            String result = null;
            GlobalManager.setBackendBusy( true );
            mPaymentUrl = null;
            try {
                Call<ApiResponse< String >> resultCall =  RestController.getApi().getPaymentUrl( AppPreferences.getUniqueId(),
                            mDonateSum );
                Response< ApiResponse< String > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mPaymentUrl = resultResponse.body().getResult();
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
            if ( result != null ) {
                ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    getActivity().onBackPressed();
                }, 2000 );
                return;
            }
            showWebDonate();
        }
    }


    public interface OnDonateChooseListener {
        void onDonateAction( int amount );

    }
}
