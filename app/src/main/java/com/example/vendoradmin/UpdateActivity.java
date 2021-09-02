package com.example.vendoradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText priceEdit, quantityEdit;
    Button updateButton , deleteButton, updateImage;
    PrograssBar prograssBar;
    String machineId, _id;
    String price, quantity;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        prograssBar = new PrograssBar(this);

        Intent intent = getIntent();

        price = intent.getStringExtra("price");
        quantity = intent.getStringExtra("quantity");
        machineId = intent.getStringExtra("machineId");
        _id = intent.getStringExtra("_id");

        priceEdit = findViewById(R.id.getPrice);
        quantityEdit = findViewById(R.id.getQuantity);
        updateButton = findViewById(R.id.updateProduct);
        updateImage = findViewById(R.id.updateImage);
        deleteButton = findViewById(R.id.deleteProduct);

        priceEdit.setText(String.valueOf(price));
        quantityEdit.setText(String.valueOf(quantity));

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), UpdataImageActivity.class);
                intent1.putExtra("_id", _id);
                intent1.putExtra("machineId", machineId);
                startActivity(intent1);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pri = priceEdit.getText().toString();
                String quant = quantityEdit.getText().toString();

                if(pri.trim().length() == 0){
                    priceEdit.setError("Please fill");
                    priceEdit.setFocusable(true);
                }
                else if(quant.trim().length() == 0){
                    quantityEdit.setError("Please fill");
                    quantityEdit.setFocusable(true);
                }
                else{
                    prograssBar.startPrograssBar();
                    String url = "https://obscure-cove-38079.herokuapp.com/updateProduct";

                    JSONObject object1 = new JSONObject();
                    int price = Integer.parseInt(pri);
                    int quantity = Integer.parseInt(quant);

                    Log.e("priec", pri);
                    Log.e("quantity", quant);
                    Log.e("machineId", machineId);
                    Log.e("id", _id);

                    try {
                        object1.put("_id", _id);
                        object1.put("quantity", quantity);
                        object1.put("price", price);
                        object1.put("machineId", machineId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    queue = Volley.newRequestQueue(getApplicationContext());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object1, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            prograssBar.dismissPrograssBar();
                            Toast.makeText(UpdateActivity.this, "Updated successfully.", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(getApplicationContext(), EditMachineAdapter.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prograssBar.dismissPrograssBar();
                            Log.e("eror", String.valueOf(error));
                        }
                    });
                    queue.add(jsonObjectRequest);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prograssBar.startPrograssBar();
                String url = "https://obscure-cove-38079.herokuapp.com/deleteProduct";
                JSONObject object = new JSONObject();
                try {
                    object.put("_id", _id);
                    object.put("machineId", machineId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                queue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prograssBar.dismissPrograssBar();
                        Toast.makeText(UpdateActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Log.e("response ", String.valueOf(response));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prograssBar.dismissPrograssBar();
                        Log.e("eror", String.valueOf(error));
                    }
                });
                queue.add(jsonObjectRequest);

            }
        });

    }
}