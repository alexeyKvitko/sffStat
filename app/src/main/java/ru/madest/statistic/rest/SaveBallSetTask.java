package ru.madest.statistic.rest;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;
import ru.madest.statistic.model.ApiResponse;
import ru.madest.statistic.model.StoredBallSet;
import ru.madest.statistic.utils.AppPreferences;

public class SaveBallSetTask extends AsyncTask< StoredBallSet, Void, String > {

    private static final String TAG = "SaveBallSetTask";

    @Override
    protected void onPreExecute () {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground ( StoredBallSet... args ) {
        String result = null;
        try {
            Call< ApiResponse > resultCall = RestController.getApi().saveStoredBallSet( AppPreferences.getUniqueId(), args[0] );
            Response< ApiResponse > resultResponse = resultCall.execute();
            if ( resultResponse.body() != null ) {
                if ( resultResponse.body().getStatus() == 200 ) {
                    Log.i ( TAG, "Saved successfully");
                } else {
                    result = resultResponse.body().getMessage();
                }
            } else {
                result = SFFSApplication.getAppContext().getResources().getString( R.string.internal_error );
            }
        } catch ( Exception e ) {
            result = SFFSApplication.getAppContext().getResources().getString( R.string.internet_error );
            Log.i( TAG, e.getMessage() );
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute ( String result ) {
        super.onPostExecute( result );
        if ( result != null ) {
            Log.e(TAG, result );
        }
   }

}
