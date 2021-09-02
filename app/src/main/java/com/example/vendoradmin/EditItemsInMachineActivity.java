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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditItemsInMachineActivity extends AppCompatActivity {
    PrograssBar prograssBar;
    String response, key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items_in_machine);

        prograssBar = new PrograssBar(this);

        Intent intent = getIntent();
        response = intent.getStringExtra("machineId");
        key = intent.getStringExtra("key");
        prograssBar.startPrograssBar();
        String url = "https://obscure-cove-38079.herokuapp.com/showProduct/"+response;


        findViewById(R.id.addNewProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prograssBar.startPrograssBar();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArr = new JSONArray(response);
                            JSONObject jsonObj = jsonArr.getJSONObject(0);
                            prograssBar.dismissPrograssBar();
                            Intent intent1 = new Intent(EditItemsInMachineActivity.this, AddNewProductActivity.class);
                            intent1.putExtra("machineId", jsonObj.getString("machine"));
                            intent1.putExtra("key", jsonObj.getString("key_razorpay"));
                            startActivity(intent1);

                        } catch (JSONException e) {
                            prograssBar.dismissPrograssBar();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prograssBar.dismissPrograssBar();
                        Toast.makeText(EditItemsInMachineActivity.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            }
        });


        final RecyclerView machineList = findViewById(R.id.machineList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        machineList.setLayoutManager(layoutManager);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prograssBar.dismissPrograssBar();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Schema[] productSchemas = gson.fromJson(response, Schema[].class);
                        machineList.setAdapter(new EditMachineAdapter(getApplicationContext(), productSchemas));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prograssBar.dismissPrograssBar();
                Toast.makeText(EditItemsInMachineActivity.this, "Network error please try again", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}