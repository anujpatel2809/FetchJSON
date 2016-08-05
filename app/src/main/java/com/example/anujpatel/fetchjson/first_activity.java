package com.example.anujpatel.fetchjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class first_activity extends AppCompatActivity {

    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    JSONArray peoples = null;
    ArrayList<HashMap<String,String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
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

        list= (ListView) findViewById(R.id.listView);
        personList=new ArrayList<HashMap<String, String>>();

        getData();
    }

    public void getData() {

        class GetDataJSON extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient=new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://192.168.137.1:80/test/getData.php");
                // Depends on your web service
                httppost.setHeader("Content‐type", "application/json");
                InputStream inputStream = null;
                String result = null;

                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF‐8 by default                     
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line =reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                }
                catch (Exception e) {
                    Log.d("Json", e.toString());
                }
                finally{
                    try{
                        if(inputStream != null)
                            inputStream.close();
                    }
                    catch (Exception s){
                        Log.d("Json",s.toString());
                    }
                }
                return result;

            }

            @Override
            protected void onPostExecute(String s) {
                myJSON=s;
                showList();
            }
        }

        GetDataJSON g=new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try{
            JSONObject jsonobj=new JSONObject(myJSON);
            peoples=jsonobj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c=peoples.getJSONObject(i);
                String id=c.getString(TAG_ID);
                String name=c.getString(TAG_NAME);
                String address=c.getString(TAG_ADD);

                HashMap<String,String> persons=new HashMap<String,String>();
                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);

                personList.add(persons);
            }

            ListAdapter adapter= new SimpleAdapter(
                    this,personList,R.layout.list_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD},
                    new int[]{R.id.id,R.id.name,R.id.address}
            );
            list.setAdapter(adapter);
        }catch (JSONException e){
            Log.d("Json",e.toString());
            e.printStackTrace();
        }
    }

}
