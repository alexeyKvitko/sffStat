package ru.madest.statistic.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestController {

    private static final String BASE_URL = "http://194.58.122.145:8080/";
    public static final String REST_BASE_URL = BASE_URL + "stoloto/";

    private static final RestController restController = new RestController();


    private RestController() {}

    public static RestController getInstance() {
        return restController;
    }

    public static RestController setContentType(String contentType){

        return restController;
    }

    public static RestApi getApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( REST_BASE_URL )
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        return retrofit.create( RestApi.class );
    }

}
