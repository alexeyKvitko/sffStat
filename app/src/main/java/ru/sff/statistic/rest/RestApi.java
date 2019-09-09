package ru.sff.statistic.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.BootstrapModel;
import ru.sff.statistic.model.ConsiderRequest;
import ru.sff.statistic.model.ConsiderResponse;
import ru.sff.statistic.model.DigitInfo;
import ru.sff.statistic.model.DrawDetails;
import ru.sff.statistic.model.DrawInfo;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.model.LotoTurn;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestByDraw;
import ru.sff.statistic.model.RequestBySumOrBall;
import ru.sff.statistic.model.ResponseData;

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
}
