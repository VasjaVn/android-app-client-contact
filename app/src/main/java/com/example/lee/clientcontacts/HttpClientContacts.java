package com.example.lee.clientcontacts;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class HttpClientContacts {
    private static final String URL = "http://192.168.1.2/project/rest/contacts";

    private String url;
    private Context context;
    private AsyncHttpClient client = new AsyncHttpClient();
    private HttpResponseHandler handler = new HttpResponseHandler();

    public HttpClientContacts(Context context, String ipAddressWebService) {
        this.context = context;
        this.url = "http://" + ipAddressWebService + "/project/rest/contacts";
    }

   public void get() {
        client.get(context, url, handler);
    }

    private class HttpResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String json = new String(responseBody);
            try {
                JSONObject response = new JSONObject(json);
                if("success".equals(response.getString("status"))) {

                    JSONArray contacts = response.getJSONArray("contacts");

                    for ( int i = 0; i < contacts.length(); i++ ) {
                        JSONObject contact = contacts.getJSONObject(i);

                        StringBuilder item = new StringBuilder();

                        item.append( contact.getString("firstName") );
                        item.append( " " );
                        item.append( contact.getString("lastName") );
                        item.append( " - ");
                        item.append( contact.getString("birthDate") );

                        ((MainActivity)context).getAdapter().add( item.toString() );
                    }
                }
            } catch (JSONException e) {
                Toast toast = Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
