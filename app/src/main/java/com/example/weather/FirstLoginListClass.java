package com.example.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstLoginListClass {

    private Database mySql;
    SQLiteDatabase db;
    Context mycontext;
    ArrayList<City_ListCL> city_list_f = new ArrayList<>();
    ArrayList<City_ListCL> item_list_f1 = new ArrayList<>();
    ArrayList<Temp_ListCL> temp_l = new ArrayList<>();
    String apiKEy = "949f9ab1c7be6cef75d007c1da6c9b5c";
    String base_url = "https://api.openweathermap.org/data/";
    StringBuilder sonuc_string;
    String date;
    String city, img_id, cloud;
    int city_id;
    double tempt;

    public FirstLoginListClass(Context mycontext) {

        this.mycontext = mycontext;

    }

    public void firslogin() {
        mySql = new Database(mycontext);
        db = mySql.getWritableDatabase();


        Cursor cursor = new Database(mycontext).read_all_data();


        while (cursor.moveToNext()) {
            City_ListCL obj = new City_ListCL(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
            city_list_f.add(obj);
        }

        for (int i = 0; i < city_list_f.size(); i++) {
            city = city_list_f.get(i).getCity();
            city_id = city_list_f.get(i).getId();
            Api_Call(city, city_id);
        }
        db.close();

    }


    private void Api_Call(String city, int city_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<Temp_ListCL> listCall = service.getWeather(city, "en", apiKEy);
        listCall.getClass();
        listCall.enqueue(new Callback<Temp_ListCL>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Temp_ListCL> call, Response<Temp_ListCL> response) {

                if (response.isSuccessful()) {
                    Temp_ListCL weatherList = response.body();

//                    city_id INTEGER, tempt REAL, date TEXT, img_id TEXT, cloud TEXT
                    tempt = weatherList.getMain().getTemp();
                    img_id = weatherList.getWeather().get(0).getIcon();
                    cloud = weatherList.getWeather().get(0).getDescription();

                    addDB(city_id, tempt, img_id, cloud);
                    Log.i("islem ","basarili");

                }

            }

            @Override
            public void onFailure(Call<Temp_ListCL> call, Throwable t) {
                Log.i("islem ", "OLMADIIIIIIIIIIIIIIIII");
            }
        });


    }

    public void addDB(int city_id, double tempt, String img_id, String cloud) {

        sonuc_string = new StringBuilder();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int yil = cal.get(java.util.Calendar.YEAR);
        int ay = cal.get(java.util.Calendar.MONTH) + 1;
        int gun = cal.get(java.util.Calendar.DAY_OF_MONTH);
        sonuc_string.append(gun).append("/").append(ay).append("/").append(yil);

        date = sonuc_string.toString();

        String res = new Database(mycontext).addrecord_we(city_id, tempt, date, img_id, cloud);


    }

}