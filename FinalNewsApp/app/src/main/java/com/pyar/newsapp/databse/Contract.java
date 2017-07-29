package com.pyar.newsapp.databse;

import android.provider.BaseColumns;

/**
 * Created by RAVI on 7/24/2017.
 */

public class Contract {

    public static class TABLE_NEWSITEM implements BaseColumns {
        //Table for news
        public static final String TABLE_NAME = "news";


        //Columns to store news articles
        public static final String AUTHOR = "author";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";
        public static final String IMAGE_URL = "imageurl";
        public static final String PUBLISHED_AT = "publishedat";
    }

}
