package com.example.vendoradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText phoneNumber, password;
    TextView forgetPassword, signUp;
    PrograssBar prograssBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginBtn);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        forgetPassword = findViewById(R.id.forgetPassword);
        signUp = findViewById(R.id.signUp);

        prograssBar = new PrograssBar(MainActivity.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneNumber.getText() == null){
                    phoneNumber.setError("Please enter phone number");
                    phoneNumber.setFocusable(true);
                }
                else if(password.getText() == null){
                    password.setError("Please enter password");
                    password.setFocusable(true);
                }
                else{
                    prograssBar.startPrograssBar();
                    String number = String.valueOf(phoneNumber.getText());
                    String pass = String.valueOf(password.getText());
//====================================Make validation here==================================================
                    String urlForValidation ="https://authenticate-my-app.herokuapp.com/log_me_in";
//
                    RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlForValidation, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            response = response.replaceAll("\\[", "");
                            response = response.replaceAll("\\]", "");
                            response = response.replaceAll("\"", "");
                            response = response.replaceAll("\"", "");
                            Log.e("tagsss", response);

                            Intent intent = new Intent(getApplicationContext(), FrontActivity.class);
                            intent.putExtra("response", response);
                            prograssBar.dismissPrograssBar();
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prograssBar.dismissPrograssBar();
                            Log.e("error is", String.valueOf(error));
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();
                            MyData.put("phone", number);
                            MyData.put("pass", pass);
                            return MyData;
                        }
                    };
                    MyRequestQueue.add(MyStringRequest);
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}