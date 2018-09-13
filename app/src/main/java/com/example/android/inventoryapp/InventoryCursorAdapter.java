package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.InventoryContract.ProductEntry;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class InventoryCursorAdapter extends CursorAdapter {

    int mQuantity = 0;
    int fQuantity;


    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_id);
        Button saleButton = (Button) view.findViewById(R.id.sale);


        final int productColumnIndex = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
        int supplierColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
        int supplierphoneColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHNO);

        int currentId = cursor.getInt(cursor.getColumnIndex(InventoryContract.ProductEntry._ID));

        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        String productQuantity = cursor.getString(quantityColumnIndex);
        final int proQuantity = cursor.getInt(quantityColumnIndex);


        if (TextUtils.isEmpty(productPrice)) {
            productPrice = "unknown price";
        }

        nameTextView.setText(productName);
        priceTextView.setText("Price: " + productPrice);
        quantityTextView.setText("Quantity: " + productQuantity);

        final Uri contentUri = Uri.withAppendedPath(InventoryContract.ProductEntry.CONTENT_URI, Integer.toString(currentId));

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri productUri = ContentUris.withAppendedId(InventoryContract.ProductEntry.CONTENT_URI, productColumnIndex);
                decreaseProductQuantity(context, productUri, proQuantity);

            }

            private void decreaseProductQuantity(Context context, Uri productUri, int proQuantity) {

                int decreasedQuantity = (proQuantity >= 1) ? proQuantity - 1 : 0;

                if (proQuantity == 0) {
                    Toast.makeText(context.getApplicationContext(), "Out of Stock", Toast.LENGTH_LONG).show();
                }

                // Update table by using new value of quantity
                ContentValues contentValues = new ContentValues();
                contentValues.put(ProductEntry.COLUMN_QUANTITY, decreasedQuantity);
                int numRowsUpdated = context.getContentResolver().update(productUri, contentValues, null, null);
                if (numRowsUpdated > 0) {
                    // Show error message in Logs with info about pass update.
                    Log.i(TAG, "make one less");
                } else {
                    Toast.makeText(context.getApplicationContext(), "Negative quantity", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    

}
