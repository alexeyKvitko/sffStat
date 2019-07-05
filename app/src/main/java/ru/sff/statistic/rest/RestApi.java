package ru.sff.statistic.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.BootstrapModel;
import ru.sff.statistic.model.DrawInfo;
import ru.sff.statistic.model.LotoModel;

public interface RestApi {

    String APP_API = "api/";

    @GET(APP_API+"getBootstrapModel")
    Call< ApiResponse< BootstrapModel > > getBootstrapModel( @Header("Authorization") String authorization );

    @GET(APP_API+"getAllResults")
    Call< ApiResponse< BallsInfo > > fetchAllResults( @Header("Authorization") String authorization );

    @GET(APP_API+"getLotoDrawsByYear/{year}")
    Call< ApiResponse< List< LotoModel > > > getLotoDrawsByYear( @Header("Authorization") String authorization
            , @Path("year") Integer year);

    @GET(APP_API+"getDrawDetails/{draw}")
    Call< ApiResponse< DrawInfo > > getDrawDetails( @Header("Authorization") String authorization
            , @Path("draw") Integer draw);
}
