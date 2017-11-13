package com.example.android.assignment6android8;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    EditText firstName, lastName, email, phone;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //grab references to our input fields

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);

        // format the phone number for the user
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        save = (Button) findViewById(R.id.saveButton);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when the user clicks on save create instance of DbHelper
                PersonDbHelper myDbHelper = new PersonDbHelper(getApplicationContext());
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //put the values from the screen (not doing any editing here) into the object
                values.put(PersonContract.PersonEntry.COLUMN_NAME_FIRST, firstName.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_NAME_LAST, lastName.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_EMAIL, email.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_PHONE, PhoneNumberUtils.formatNumber(phone.getText().toString()));

                //insert the values into the database
                long newRowId = db.insert(
                        PersonContract.PersonEntry.TABLE_NAME,  //table name for insert
                        null,  //null is all columns
                        values);  //values for the insert

                //set up toast for saved data
                int duration = Toast.LENGTH_LONG;
                String result;

                //check if data was inserted put result into the toast
                if (newRowId != -1)
                {
                    result = "New Person Created!";
                }

                else
                {
                    result = "ERORR";
                }

                //show the toast
                Toast toast = Toast.makeText(getApplicationContext(), result, duration);
                toast.show();

                //clear the input fields
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                phone.setText("");
                firstName.requestFocus();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//Start display activity
        if (id == R.id.display) {
            Intent intent = new Intent(getApplicationContext(), DisplayDB.class);
            startActivity(intent);
            return true;
        }
        //menu option to clear the entire database, really helpful for testing, remove before going to production
        if (id == R.id.clearDatabase) {
            PersonDbHelper myDbHelper = new PersonDbHelper(getApplicationContext());
            SQLiteDatabase db = myDbHelper.getWritableDatabase();
            db.delete(PersonContract.PersonEntry.TABLE_NAME,"1",null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
