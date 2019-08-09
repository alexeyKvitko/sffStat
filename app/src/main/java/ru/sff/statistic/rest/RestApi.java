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
import ru.sff.statistic.model.DigitInfo;
import ru.sff.statistic.model.DrawDetails;
import ru.sff.statistic.model.DrawInfo;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestByDraw;
import ru.sff.statistic.model.ResponseData;

public interface RestApi {

    String APP_API = "api/";

    @GET(APP_API+"getBootstrapModel")
    Call< ApiResponse< BootstrapModel > > getBootstrapModel( @Header("Authorization") String authorization );


    @GET(APP_API+"getResultsByDraw/{startDraw}/{endDraw}")
    Call< ApiResponse< BallsInfo > > fetchResultsByDraw( @Header("Authorization") String authorization,
                                                         @Path("startDraw") Integer startDraw, @Path("endDraw") Integer endDraw );

    @GET(APP_API+"getLotoDrawsByDrawsBetween/{startDraw}/{endDraw}")
    Call<ApiResponse< List< LotoModel > > > getLotoDrawsByDrawsBetween( @Header("Authorization") String authorization,
                                                         @Path("startDraw") Integer startDraw, @Path("endDraw") Integer endDraw );


    @GET(APP_API+"getLotoDrawsByMonthAndYear/{month}/{year}")
    Call< ApiResponse< List< LotoModel > > > getLotoDrawsByMonthAndYear( @Header("Authorization") String authorization
            , @Path("month") String month, @Path("year") Integer year);

    @GET(APP_API+"getDrawDetails/{draw}")
    Call< ApiResponse< DrawDetails > > getDrawDetails( @Header("Authorization") String authorization
            , @Path("draw") Integer draw);

    @GET(APP_API+"getDigitInfo/{digit}/{startDraw}/{endDraw}")
    Call< ApiResponse< DigitInfo > > getDigitInfo( @Header("Authorization") String authorization
            , @Path("digit") Integer digit, @Path("startDraw") Integer startDraw,
                                                                @Path("endDraw") Integer endDraw);

    @POST(APP_API+"getStatisticByDraw")
    Call< ApiResponse< ResponseData > > getStatisticByDraw( @Header("Authorization") String authorization
            , @Body RequestByDraw requestByDraw );

    @POST(APP_API+"getStatisticByDate")
    Call< ApiResponse< ResponseData > > getStatisticByDate( @Header("Authorization") String authorization
            , @Body RequestByDate requestByDate );
}
