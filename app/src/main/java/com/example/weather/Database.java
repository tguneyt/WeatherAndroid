package com.example.weather;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Database extends SQLiteOpenHelper {
    private Resources mResources;
    private static final String DATABASE_NAME = "Weather_DB";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;

    public Database (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mResources = context.getResources();
        db=this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "+DbContract.ItemEntry.TABLE_NAME + " ("+
                DbContract.ItemEntry.COLUMN_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.ItemEntry.COLUMN_CITY + " TEXT, " +
                DbContract.ItemEntry.COLUMN_POPULATION + " INTEGER, " +
                DbContract.ItemEntry.COLUMN_REGION + " TEXT, " +
                DbContract.ItemEntry.COLUMN_COUNTRY + " TEXT " + " );";
        db.execSQL(SQL_CREATE_TABLE);

//        create weather_table
        db.execSQL("create table weather_table (weather_id INTEGER PRIMARY KEY AUTOINCREMENT, city_id INTEGER, tempt REAL, date TEXT, img_id TEXT, cloud TEXT)");

        try {
            readDataToDb(db);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException{

        final String M_CITY = "city";
        final String M_POPULATION = "population";
        final String M_REGION = "region";
        final String M_COUNTRY = "country";


        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray MJsonArray = new JSONArray(jsonDataString);
            for (int i = 0; i<MJsonArray.length();++i){
                String city;
                String population;
                String region;
                String country;


                JSONObject JObject = MJsonArray.getJSONObject(i);
                city = JObject.getString(M_CITY);
                population = JObject.getString(M_POPULATION);
                region = JObject.getString(M_REGION);
                country = JObject.getString(M_COUNTRY);



                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContract.ItemEntry.COLUMN_CITY,city);
                contentValues.put(DbContract.ItemEntry.COLUMN_POPULATION,population);
                contentValues.put(DbContract.ItemEntry.COLUMN_REGION,region);
                contentValues.put(DbContract.ItemEntry.COLUMN_COUNTRY,country);



                db.insert(DbContract.ItemEntry.TABLE_NAME,null,contentValues);
            }
        }
        catch (JSONException e){

        }

    }

    private String readJsonDataFromFile() throws IOException{
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.data_tr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            while (((jsonDataString = bufferedReader.readLine()) != null)) {
                builder.append(jsonDataString);
            }
        }
        finally {

        }

        return new String(builder);
    }
//    TODO weather icin db guncelle
    public  String addrecord_we(int city_id, double tempt, String date, String img_id, String cloud)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("city_id",city_id);
        cv.put("tempt",tempt);
        cv.put("date",date);
        cv.put("img_id",img_id);
        cv.put("cloud",cloud);

        float res=db.insert("weather_table",null,cv);

        if(res==-1)
            return "Failed";
        else
            return  "Kaydedildi";

    }



    public  String addrecord_city(String cty, String pp, String r, String ct)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("city",cty);
        cv.put("population",pp);
        cv.put("region",r);
        cv.put("country",ct);

        float res=db.insert("city_table",null,cv);

        if(res==-1)
            return "Failed";
        else
            return  "Kaydedildi";

    }
    public Cursor read_city_table(String country)
    {


        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM city_table WHERE city = '"+country+"'",null);

        // SELECT * FROM Words_Table WHERE lang = 'lg' and level = 'lv'
        //TODO bu select değişecek



        return  cursor;
    }
    public Cursor read_data_country(String country)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM " + DbContract.ItemEntry.TABLE_NAME+" WHERE country = '"+country+"' order by population desc",null);

        // SELECT * FROM Words_Table WHERE lang = 'lg' and level = 'lv'
        //TODO bu select değişecek



        return  cursor;
    }

    public Cursor read_all_data()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor cursor=db.rawQuery("SELECT * FROM " + DbContract.ItemEntry.TABLE_NAME+" WHERE _id = '"+id+"'",null);
        Cursor cursor=db.rawQuery("SELECT * FROM " + DbContract.ItemEntry.TABLE_NAME,null);


        return  cursor;
    }

}

