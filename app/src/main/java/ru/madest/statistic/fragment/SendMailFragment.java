package ru.madest.statistic.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Response;
import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.activity.RouteActivity;
import ru.madest.statistic.modal.ModalMessage;
import ru.madest.statistic.model.ApiResponse;
import ru.madest.statistic.rest.RestController;
import ru.madest.statistic.utils.AppPreferences;
import ru.madest.statistic.utils.AppUtils;
import ru.madest.statistic.utils.CustomAnimation;

public class SendMailFragment extends BaseFragment {

    private static final String TAG = "SendMailFragment";

    private AppCompatEditText mSupportMessage;

    private TextView mSupportError;

    public SendMailFragment () {
    }

    public static SendMailFragment newInstance () {
        SendMailFragment fragment = new SendMailFragment();
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_send_mail, container, false );
    }


    @Override
    public void onActivityCreated ( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        initTextView( R.id.supportTitleId, AppConstants.ROTONDA_BOLD );
        mSupportMessage = initEditText( R.id.supportMessagetValueId, AppConstants.ROBOTO_CONDENCED );
        mSupportError = initTextView( R.id.supportErrorFieldId, AppConstants.ROBOTO_CONDENCED );
        initButton( R.id.supportButtonId, AppConstants.ROBOTO_CONDENCED );
        setThisOnClickListener( R.id.supportButtonId );
    }

    private void sendMessageToSupport () {
        final String supportMessage = mSupportMessage.getText().toString();
        if ( AppUtils.nullOrEmpty( supportMessage ) ) {
            (new Handler()).postDelayed( () -> {
                mSupportError.setVisibility( View.GONE );
            }, 3000 );
            return;
        }
        AppUtils.hideKeyboardFrom( getActivity(), getView() );
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.supportContainerId ),
                getView().findViewById( R.id.pleaseWaitContainerId ) );
        new PostMessageToSupport().execute( supportMessage );
    }

    @Override
    public void onResume () {
        super.onResume();
        (( RouteActivity ) getActivity()).getFooter().setSupportDisable( true );
    }

    @Override
    public void onPause () {
        super.onPause();
        (( RouteActivity ) getActivity()).getFooter().setSupportDisable( false );
    }

    @Override
    public void onClick ( View view ) {
        CustomAnimation.clickAnimation( view );
        switch ( view.getId() ) {
            case R.id.supportButtonId:
                sendMessageToSupport();
                break;
        }
    }


    private class PostMessageToSupport extends AsyncTask< String, Void, String > {

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground ( String... messages ) {
            String result = null;
            try {
                Call< ApiResponse > supportCall = RestController.getApi()
                        .sendMessageToSupport( AppPreferences.getUniqueId(), messages[0] );
                Response< ApiResponse > responseFeedback = supportCall.execute();
                if ( responseFeedback.body() != null ) {
                    if ( responseFeedback.body().getStatus() == 200 ) {
                        result = "Спасибо за обращение. Нам очень важно Ваше мнение.";
                    } else {
                        result = responseFeedback.body().getMessage();
                    }
                } else {
                    result = getResources().getString( R.string.internal_error );
                }
            } catch ( Exception e ) {
                result = getResources().getString( R.string.internet_error );
                Log.i( TAG, e.getMessage() );
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute ( String result ) {
            super.onPostExecute( result );
            CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                    getView().findViewById( R.id.supportContainerId ) );
            ModalMessage.show( getActivity(), "Сообщение", new String[] {result} );
            (new Handler()).postDelayed( () -> {

                getActivity().onBackPressed();
            }, 2000 );

        }
    }
}
