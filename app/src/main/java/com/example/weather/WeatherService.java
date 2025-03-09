package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//public interface JSONPlaceholder {
//    @GET("2.5/weather")
//    Call<List<Item_Temp>> getItem_Temp(@Query("q") String city);
//
//}

public interface WeatherService {

    @GET("2.5/weather")
    Call<Temp_ListCL> getWeather(@Query("q") String q,
                                 @Query("lang") String lang,
                                 @Query("appid") String appid);

}