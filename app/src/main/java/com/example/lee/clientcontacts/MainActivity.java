package com.example.lee.clientcontacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> listItems = new ArrayList<>();

    ArrayAdapter<String> adapter;

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView listView = (ListView) findViewById(R.id.id_list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                //view.getContentDescription()
                String str = adapter.getItem(position);
                Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        HttpClientContacts httpClientContacts = new HttpClientContacts(this, SharedData.ipAddressWebService);
        httpClientContacts.get();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(settingsActivity);
                break;

            case R.id.action_reload:
                adapter.clear();
                HttpClientContacts httpClientContacts = new HttpClientContacts(this, SharedData.ipAddressWebService);
                httpClientContacts.get();
                break;
        }

        /*if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_reload) {
            adapter.clear();
            HttpClientContacts httpClientContacts = new HttpClientContacts(this);
            httpClientContacts.get();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
