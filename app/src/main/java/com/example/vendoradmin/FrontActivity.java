package com.example.vendoradmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

public class FrontActivity extends AppCompatActivity {
    String response;
    PrograssBar prograssBar;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        prograssBar = new PrograssBar(this);

        Intent intent = getIntent();
        response = intent.getStringExtra("response");

        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FrontActivity.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
            }
        });

        prograssBar.startPrograssBar();


        final RecyclerView machineList = findViewById(R.id.productList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        machineList.setLayoutManager(layoutManager);

        String url = "https://authenticate-my-app.herokuapp.com/find_machine";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prograssBar.dismissPrograssBar();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Schema[] productSchemas = gson.fromJson(response, Schema[].class);
                        machineList.setAdapter(new MachineAdapter(getApplicationContext(), productSchemas));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prograssBar.dismissPrograssBar();
                Toast.makeText(FrontActivity.this, "Network error please try again", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("machine", response);
                return MyData;
            }
        };;
        queue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        if(count < 1){
            Toast.makeText(this, "Press once again.", Toast.LENGTH_SHORT).show();
        }else{
            finishAffinity();
        }
        count++;
    }
}