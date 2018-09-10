package com.example.android.inventoryapp;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;


public final class InventoryContract {


    private InventoryContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_PRODUCT = "product";


    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        public final static String TABLE_NAME = "product";


        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRODUCT_NAME ="name";


        public final static String COLUMN_PRICE = "price";

        public final static String COLUMN_QUANTITY="quantity";


        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";


        public final static String COLUMN_SUPPLIER_PHNO = "supplier_phno";


    }

}

