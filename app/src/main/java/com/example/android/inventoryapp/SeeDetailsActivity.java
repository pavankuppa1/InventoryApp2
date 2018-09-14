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


public class SeeDetailsActivity extends AppCompatActivity //implements
        //  LoaderManager.LoaderCallbacks<Cursor>
{

    private String name;
    private int price;
    private int quantity;
    private String suppliername;
    private int supplierphno;

    private TextView proname, proprice, proquantity, prosuppliername, prosupplierphno;

    private static final int EXISTING_PRODUCT_LOADER = 0;

    private Uri mCurrentProductUri;

    private boolean mProductHasChanged = false;

    private Button delete;

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
        mCurrentProductUri = intent.getData();


        proname = (TextView) findViewById(R.id.pro_name);
        proprice = (TextView) findViewById(R.id.pro_price);
        proquantity = (TextView) findViewById(R.id.edit_product_quantity);
        prosuppliername = (TextView) findViewById(R.id.supplier_name);
        prosupplierphno = (TextView) findViewById(R.id.supplier_phno);
        delete = (Button) findViewById(R.id.delete);

        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price", -1);
        quantity = intent.getIntExtra("quantity", -1);
        suppliername = intent.getStringExtra("suppliername");
        supplierphno = intent.getIntExtra("supplierphno", -1);

        proname.setText(name);
        proprice.setText(String.valueOf(price));
        proquantity.setText(String.valueOf(quantity));
        prosuppliername.setText(suppliername);
        prosupplierphno.setText(String.valueOf(supplierphno));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteConfirmationDialog();


            }
        });


    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this product?");
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
