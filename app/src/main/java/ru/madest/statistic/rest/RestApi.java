package ru.madest.statistic.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.madest.statistic.model.ApiResponse;
import ru.madest.statistic.model.BallsInfo;
import ru.madest.statistic.model.BootstrapModel;
import ru.madest.statistic.model.ConsiderRequest;
import ru.madest.statistic.model.ConsiderResponse;
import ru.madest.statistic.model.DigitInfo;
import ru.madest.statistic.model.DrawDetails;
import ru.madest.statistic.model.LotoModel;
import ru.madest.statistic.model.LotoTurn;
import ru.madest.statistic.model.RequestByDate;
import ru.madest.statistic.model.RequestByDraw;
import ru.madest.statistic.model.RequestBySumOrBall;
import ru.madest.statistic.model.ResponseData;
import ru.madest.statistic.model.StoredBallSet;

public interface RestApi {

    String APP_API = "api/";

    @GET(APP_API+"getBootstrapModel")
    Call< ApiResponse< BootstrapModel > > getBootstrapModel( @Header("application-id") String applicationId );


    @GET(APP_API+"getResultsByDraw/{startDraw}/{endDraw}")
    Call< ApiResponse< BallsInfo > > fetchResultsByDraw( @Header("application-id") String applicationId,
                                                         @Path("startDraw") Integer startDraw, @Path("endDraw") Integer endDraw );

    @GET(APP_API+"getLotoDrawsByDrawsBetween/{startDraw}/{endDraw}")
    Call<ApiResponse< List< LotoModel > > > getLotoDrawsByDrawsBetween( @Header("application-id") String applicationId,
                                                         @Path("startDraw") Integer startDraw, @Path("endDraw") Integer endDraw );


    @GET(APP_API+"getLotoDrawsByMonthAndYear/{month}/{year}")
    Call< ApiResponse< List< LotoModel > > > getLotoDrawsByMonthAndYear( @Header("application-id") String applicationId
            , @Path("month") String month, @Path("year") Integer year);

    @GET(APP_API+"getDrawDetails/{draw}")
    Call< ApiResponse< DrawDetails > > getDrawDetails( @Header("application-id") String applicationId
            , @Path("draw") Integer draw);

    @POST(APP_API+"getDigitInfoByDraw")
    Call< ApiResponse< DigitInfo > > getDigitInfoByDraw( @Header("application-id") String applicationId
            , @Body RequestByDraw requestByDraw );

    @POST(APP_API+"getDigitInfoByDate")
    Call< ApiResponse< DigitInfo > > getDigitInfoByDate( @Header("application-id") String applicationId
            , @Body RequestByDate requestByDate );

    @POST(APP_API+"getDigitInfoBySOB")
    Call< ApiResponse< DigitInfo > > getDigitInfoBySOB( @Header("application-id") String applicationId
            , @Body RequestBySumOrBall requestBySOB );

    @POST(APP_API+"getStatisticByDraw")
    Call< ApiResponse< ResponseData > > getStatisticByDraw( @Header("application-id") String applicationId
            , @Body RequestByDraw requestByDraw );

    @POST(APP_API+"getStatisticByDate")
    Call< ApiResponse< ResponseData > > getStatisticByDate( @Header("application-id") String applicationId
            , @Body RequestByDate requestByDate );

    @POST(APP_API+"getStatisticBySOB")
    Call< ApiResponse< ResponseData > > getStatisticBySOB( @Header("application-id") String applicationId
            , @Body RequestBySumOrBall requestBySOB );

    @GET(APP_API+"getLotoTurns/{draw}")
    Call< ApiResponse< List< LotoTurn > > > getLotoTurns( @Header("application-id") String applicationId
            , @Path("draw") Integer draw);

    @POST(APP_API+"getConsiderInfo")
    Call< ApiResponse< ConsiderResponse > > getConsiderInfo( @Header("application-id") String applicationId
            , @Body ConsiderRequest considerRequest );

    @GET(APP_API+"getPaymentUrl/{amount}")
    Call< ApiResponse< String > > getPaymentUrl( @Header("application-id") String applicationId
            , @Path("amount") Integer amount);

    @POST(APP_API+"saveStoredBallSet")
    Call< ApiResponse > saveStoredBallSet( @Header("application-id") String applicationId
            , @Body StoredBallSet storedBallSet );

    @GET(APP_API+"sendEmailToUs/{message}")
    Call< ApiResponse> sendMessageToSupport( @Header("application-id") String applicationId,
                                             @Path("message") String message );
}
