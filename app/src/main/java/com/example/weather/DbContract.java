package com.example.weather;

import android.provider.BaseColumns;

public class DbContract {

    public static final class ItemEntry implements BaseColumns {
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String TABLE_NAME = "city_table";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_POPULATION = "population";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_COUNTRY = "country";

    }

}
