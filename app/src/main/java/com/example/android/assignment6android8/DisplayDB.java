package com.example.android.assignment6android8;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;
import static org.apache.http.protocol.HTTP.PLAIN_TEXT_TYPE;

public class DisplayDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //instead of array and preferences have the DB stuff here

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        });

        PersonDbHelper dbHelper = new PersonDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //
        //out of dataset which columns to use projection

        String[] projection = {
                PersonContract.PersonEntry.COLUMN_NAME_FIRST,
                PersonContract.PersonEntry.COLUMN_NAME_LAST,
                PersonContract.PersonEntry.COLUMN_PHONE,
                PersonContract.PersonEntry.COLUMN_EMAIL
        };

        String[] bind = {
                PersonContract.PersonEntry._ID,
                PersonContract.PersonEntry.COLUMN_NAME_FIRST,
                PersonContract.PersonEntry.COLUMN_NAME_LAST,
                PersonContract.PersonEntry.COLUMN_PHONE,
                PersonContract.PersonEntry.COLUMN_EMAIL
        };

        //now going to call method to return cursor

        Cursor cursor = db.query(PersonContract.PersonEntry.TABLE_NAME, //table to query
                bind,
                null, //columns for where, Null will return all rows
                null, //values for where
                null, //Group By, null is no group by
                null, //Having, null says return all rows
                PersonContract.PersonEntry.COLUMN_NAME_LAST + " ASC" //names in alpabetical order
        );


        //the list items from the layout, will find these in the row_item,
        //these are the 4 fields being displayed
        int[] to = new int[]{
                R.id.first,  R.id.last, R.id.phone, R.id.email
        };

        //create the adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.row_item, cursor, projection, to, 0);

        //set the adapter to the list
        final ListView listView = (ListView) findViewById(list);
        listView.setAdapter(adapter);

        //set up for the empty non data messaged
        TextView emptyView = (TextView) findViewById(android.R.id.empty);
        listView.setEmptyView(emptyView);

        //need to set the On Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Handle the on-click and display a toast, will do more work here later
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            //this is returning a cursor this time, so need to get the string out of the cursor
            String[] selectedEmail = {(String) cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_EMAIL))};
            String selectedLastName = (String) cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME_LAST));
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // The intent does not have a URI, so declare the "text/plain" MIME type
            emailIntent.setType(PLAIN_TEXT_TYPE);
            //put out data in the intent
            emailIntent.putExtra(Intent.EXTRA_EMAIL, selectedEmail); // recipients
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, selectedLastName);
            // Verify it resolves
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                startActivity(emailIntent);
        }

            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_db, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.enterValues) {
            Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
