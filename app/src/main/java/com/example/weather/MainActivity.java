package com.example.weather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<City_ListCL> list;
    ArrayList list1;
    List<Integer> list_id;
    List<String> list_city;
    String apiKEy = "949f9ab1c7be6cef75d007c1da6c9b5c";
    String base_url = "https://api.openweathermap.org/data/";
    String sehir = "Helmond";
    StringBuilder sonuc_string;
    String date;
    ArrayList<City_ListCL> city_listcl = new ArrayList<>();
    ArrayList<City_ListCL> city_listcl11 = new ArrayList<>();
    ArrayList<Temp_ListCL> item_l = new ArrayList<>();
    Context context = this;
    Button btn_quit;
    CardView btn_nl,btn_usa,btn_tr,btn_search;
    EditText edt_txt;
    TextView txt_city_data,txt_region_data,txt_popul_data,txt_temp,txt_cloud,txt_cty_count,txt_temp_fahr, txt_c, txt_f;
    ImageView img_cloud;
    AutoCompleteTextView ototext;

    SQLiteDatabase db;
    private Database database;
    RecyclerView recyclerView ;
    double c, cel_kel,fahr;
    InputMethodManager imm;
    String t, city_edt, ctry;
    int city_id;
    String country = "nl";
    String S_firslog;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    FirstLoginListClass firstLoginListClass;

    SQLiteDatabase dbsql;
    Database dbData;

    String error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Definitions();
        dbData = new Database(this);
        dbsql = dbData.getWritableDatabase();
        dbData.getReadableDatabase();
        edt_txt.clearFocus();
        imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_txt.getWindowToken(), 0); ////hide
        list_id = new ArrayList<Integer>();
        list_city = new ArrayList<String>();

        FirstData();





        ShowItemList(country);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        btn_nl.setCardBackgroundColor(getResources().getColor(R.color.r2));
        btn_usa.setCardBackgroundColor(getResources().getColor(R.color.r1));
        btn_tr.setCardBackgroundColor(getResources().getColor(R.color.r1));


        preferences = getSharedPreferences("b", MODE_PRIVATE);
        S_firslog = preferences.getString("x","");

        if(!(S_firslog.equalsIgnoreCase("f")))
        {
//            Intent i = new Intent(MainActivity.this,MainWelcomeActivity.class);
//            startActivity(i);


            preferences = getSharedPreferences("b", MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("x", "f");
            editor.apply();
            editor.commit();
        }
        Cursor cursor = dbsql.rawQuery("select city from city_table",null);
        while (cursor.moveToNext()) {
            list_city.add(cursor.getString(0));
        }

        ArrayAdapter<String
                > adaptor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list_city);
        ototext.setAdapter(adaptor);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ototext.getText().toString();
                if (s.equalsIgnoreCase("")){
                    error_msg = "Invalid Name";
                    ShowError(error_msg);
                    ototext.setText("");
                }
                else{
                    city_edt = s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();

                    if (list_city.contains(city_edt)) {
                        System.out.println("Aranan değer var");
                        Show_Weather(city_edt);
                    }
                    else {
                        error_msg = "Not Found";
                        ShowError(error_msg);
                        ShowItemList(country);

                    }
                    ototext.setText("");
//                    city_listcl.clear();
//                    ShowItemList(country);
                }



            }
        });




        btn_nl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "nl";

                btn_nl.setCardBackgroundColor(getResources().getColor(R.color.r2));
                btn_usa.setCardBackgroundColor(getResources().getColor(R.color.r1));
                btn_tr.setCardBackgroundColor(getResources().getColor(R.color.r1));
                city_listcl.clear();
                ShowItemList(country);
            }
        });

        btn_usa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "usa";
                btn_nl.setCardBackgroundColor(getResources().getColor(R.color.r1));
                btn_usa.setCardBackgroundColor(getResources().getColor(R.color.r2));
                btn_tr.setCardBackgroundColor(getResources().getColor(R.color.r1));

                city_listcl.clear();
                ShowItemList(country);
            }
        });

        btn_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "tr";
                btn_nl.setCardBackgroundColor(getResources().getColor(R.color.r1));
                btn_usa.setCardBackgroundColor(getResources().getColor(R.color.r1));
                btn_tr.setCardBackgroundColor(getResources().getColor(R.color.r2));

                city_listcl.clear();
                ShowItemList(country);
            }
        });
    }

    public void FirstData() {
        Cursor cursor = dbsql.rawQuery("select * from city_table where city = 'Amsterdam'",null);
        while (cursor.moveToNext()) {
            City_ListCL obj = new City_ListCL(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            city_listcl11.add(obj);
        }


        txt_city_data.setText("Amsterdam");
        txt_popul_data.setText(Integer.toString(city_listcl11.get(0).getPopulation()));
        txt_region_data.setText(city_listcl11.get(0).getRegion());
        ctry = city_listcl11.get(0).getCountry();
        txt_cty_count.setText("Amsterdam" +"/"+ city_listcl11.get(0).getCountry().toUpperCase());
        imm.hideSoftInputFromWindow(edt_txt.getWindowToken(), 0); ////hide
        Api_Call("Amsterdam",city_listcl11.get(0).getId());
        city_listcl11.clear();
    }


    public void ShowError(String error_msg){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(error_msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ototext.setText("");
                        dialog.dismiss();

                    }
                });
        alertDialog.show();

    }


    public void ShowItemList(String country) {
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor=new Database(this).read_data_country(country);


        while(cursor.moveToNext())
        {
            City_ListCL obj=new City_ListCL(cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            city_listcl.add(obj);
        }

        AdaptorCustom adapter=new AdaptorCustom(city_listcl, MainActivity.this, country);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        adapter.notifyDataSetChanged();
        // recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }
    public void Show_Weather(String city_edt){

        Cursor cursor=new Database(context).read_city_table(city_edt);

        while(cursor.moveToNext())
        {
            City_ListCL obj = new City_ListCL(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            city_listcl11.add(obj);
        }
        txt_city_data.setText(city_listcl11.get(0).getCity());
        txt_popul_data.setText(Integer.toString(city_listcl11.get(0).getPopulation()));
        txt_region_data.setText(city_listcl11.get(0).getRegion());
        ctry = city_listcl11.get(0).getCountry();
        txt_cty_count.setText(city_edt +"/"+ city_listcl11.get(0).getCountry().toUpperCase());
        imm.hideSoftInputFromWindow(edt_txt.getWindowToken(), 0); ////hide
        city_id = city_listcl11.get(0).getId();
        Api_Call(city_edt,city_id);
        city_listcl11.clear();


    }
    private void Api_Call(String edit, int city_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<Temp_ListCL> listCall = service.getWeather(edit,"en",apiKEy);
        listCall.getClass();
        listCall.enqueue(new Callback<Temp_ListCL>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Temp_ListCL> call, Response<Temp_ListCL> response) {

                if (response.isSuccessful()){

                    Temp_ListCL weatherList = response.body();
                    txt_c.setVisibility(View.VISIBLE);
                    txt_f.setVisibility(View.VISIBLE);
                    txt_temp.setVisibility(View.VISIBLE);
                    txt_temp_fahr.setVisibility(View.VISIBLE);
                    txt_cloud.setVisibility(View.VISIBLE);
                    txt_cloud.setTextColor(getResources().getColor(R.color.siyah));

                    txt_temp.setText(weatherList.getMain().getTemp().toString());
                    t = weatherList.getMain().getTemp().toString();
                    c = Double.parseDouble(t);
//                    cel_kel = c-273.15;
                    cel_kel = (Math.round((c-273.15)*10.0)/10.0);
                    fahr = (Math.round((((cel_kel) * 1.8) + 32)*10.0)/10.0);
                    txt_temp.setText(Double.toString(cel_kel));
                    txt_temp_fahr.setText(Double.toString(fahr));
                    txt_cloud.setText(weatherList.getWeather().get(0).getDescription());
                    Images(weatherList.getWeather().get(0).getIcon());
//                    sonuc_string = new StringBuilder();
//                    java.util.Calendar cal = java.util.Calendar.getInstance();
//                    int yil = cal.get(java.util.Calendar.YEAR);
//                    int ay = cal.get(java.util.Calendar.MONTH) + 1;
//                    int gun = cal.get(java.util.Calendar.DAY_OF_MONTH);
//                    sonuc_string.append(gun).append("/").append(ay).append("/").append(yil);
//
//                    date = sonuc_string.toString();
//
//                    String res = new Database(context).addrecord_we(city_id, weatherList.getMain().getTemp(), date,
//                            weatherList.getWeather().get(0).getIcon(), weatherList.getWeather().get(0).getDescription());
//
//
//
//                    db=dbData.getWritableDatabase();
//                    ContentValues cv = new ContentValues();
//
//                    cv.put("tempt",weatherList.getMain().getTemp());
//                    cv.put("date",date);
//                    cv.put("img_id",weatherList.getWeather().get(0).getIcon());
//                    cv.put("cloud",weatherList.getWeather().get(0).getDescription());
//
//
//                    Cursor cursor = db.rawQuery("select city_id from weather_table",null);
//                    while (cursor.moveToNext()) {
//                        list_id.add(cursor.getInt(0));
//                    }
//
//                    db.close();


                }

            }

            @Override
            public void onFailure(Call<Temp_ListCL> call, Throwable t) {
                Log.i("islem ","OLMADIIIIIIIIIIIIIIIII");
                txt_c.setVisibility(View.GONE);
                txt_f.setVisibility(View.GONE);
                txt_temp.setVisibility(View.GONE);
                txt_temp_fahr.setVisibility(View.GONE);
                txt_cloud.setVisibility(View.VISIBLE);
                txt_cloud.setText(" NO INTERNET CONNECTION ");
                txt_cloud.setTextColor(getResources().getColor(R.color.kirmizi));

            }
        });


    }

    public void Images(String img_id){
        switch (img_id){

            case "01d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d01));
                break;

            case "02d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d02));
                break;

            case "03d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d03));
                break;

            case "04d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d04));
                break;

            case "09d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d09));
                break;

            case "10d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d10));
                break;

            case "11d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d11));
                break;

            case "13d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d13));
                break;

            case "50d":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.d50));
                break;

            case "01n":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n01));
                break;

            case "02n":
                img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n02));
                break;

            case "03n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n03));
                break;

            case "04n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n04));
                break;

            case "09n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n09));
                break;

            case "10n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n10));
                break;

            case "11n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n11));
                break;

            case "13n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n13));
                break;

            case "50n":
            img_cloud.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.n50));
                break;

        }

    }


    private void Definitions() {
        recyclerView=findViewById(R.id.recycler_view);

        btn_nl = findViewById(R.id.btn_nl);
        btn_usa = findViewById(R.id.btn_usa);
        btn_tr = findViewById(R.id.btn_tr);
        btn_search = findViewById(R.id.btn_search);
        edt_txt = findViewById(R.id.edt_txt);
        ototext = findViewById(R.id.ototext);

        txt_city_data = findViewById(R.id.txt_city_data);
        txt_region_data = findViewById(R.id.txt_region_data);
        txt_popul_data = findViewById(R.id.txt_popul_data);
        txt_temp = findViewById(R.id.txt_temp);
        txt_temp_fahr = findViewById(R.id.txt_temp_fahr);
        txt_cloud = findViewById(R.id.txt_cloud);
        txt_cty_count = findViewById(R.id.txt_cty_count);
        img_cloud = findViewById(R.id.img_cloud);
        txt_c = findViewById(R.id.txt_c);
        txt_f = findViewById(R.id.txt_f);

        txt_c.setVisibility(View.GONE);
        txt_f.setVisibility(View.GONE);
        txt_temp.setVisibility(View.GONE);
        txt_temp_fahr.setVisibility(View.GONE);
        txt_cloud.setVisibility(View.GONE);


        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(180);

    }

}