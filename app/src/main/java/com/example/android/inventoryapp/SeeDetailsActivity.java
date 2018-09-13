package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.InventoryContract.ProductEntry;
import com.example.android.inventoryapp.EditorActivity;

import org.w3c.dom.Text;


public class SeeDetailsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private String name;
    private int price;
    private int quantity;
    private String suppliername;
    private int supplierphno;

    private TextView proname, proprice, proquantity, prosuppliername, prosupplierphno;

    private static final int EXISTING_PRODUCT_LOADER = 0;

    private Uri mCurrentProductUri;

    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_details);


        Intent intent = getIntent();
        Uri mCurrentProductUri = intent.getParcelableExtra("mCurrentProductUri");

        if (mCurrentProductUri == null) {
            invalidateOptionsMenu();
        } else {


            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        proname = (TextView) findViewById(R.id.pro_name);
        proprice = (TextView) findViewById(R.id.pro_price);
        proquantity = (TextView) findViewById(R.id.edit_product_quantity);
        prosuppliername = (TextView) findViewById(R.id.supplier_name);
        prosupplierphno = (TextView) findViewById(R.id.supplier_phno);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_PHNO};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentProductUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int suppliernameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
            int supplierphColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHNO);
            name = cursor.getString(nameColumnIndex);
            price = cursor.getInt(priceColumnIndex);
            quantity = cursor.getInt(quantityColumnIndex);
            suppliername = cursor.getString(suppliernameColumnIndex);
            supplierphno = cursor.getInt(supplierphColumnIndex);

            // Update the views on the screen with the values from the database
            proname.setText(name);
            proprice.setText(Integer.toString(price));
            proquantity.setText(suppliername);
            prosuppliername.setText(Integer.toString(supplierphno));
            prosupplierphno.setText(Integer.toString(quantity));


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        proname.setText("");
        proprice.setText("");
        proquantity.setText("");
        prosuppliername.setText("");
        prosupplierphno.setText("");
    }


    private void deleteProduct() {
        if (mCurrentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
