package com.example.anujpatel.fetchjson;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private static Button buttonSub;
    private static Button buttonRec;
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

        try {
            ConnectivityManager ckeck = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (ckeck.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    ckeck.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    ckeck.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    ckeck.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            } else if (ckeck.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                    ckeck.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();

            }
            Toast.makeText(this, " Not Connected..... ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.d("Json",e.toString());
        }
        buttonSub= (Button) findViewById(R.id.button);
        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent("com.example.anujpatel.fetchjson.first_activity");
                startActivity(intent);
            }
        });
        buttonRec= (Button) findViewById(R.id.button2);
        buttonRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,RecycleActivity.class);
                startActivity(intent);
            }
        });


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
