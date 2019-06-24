package ru.sff.statistic.rest;


import retrofit2.Call;
import retrofit2.http.GET;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.BallsInfo;

public interface RestApi {

    String APP_API = "api/";


    @GET(APP_API+"getAllResults")
    Call< ApiResponse< BallsInfo > > fetchAllResults();
}
