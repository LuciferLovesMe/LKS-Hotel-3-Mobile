package com.abim.lks_hotel_3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Context ctx;
    Session s;
    RequestQueue queue;
    EditText et_username, et_pass;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btn = findViewById(R.id.btn_login);
        ctx = this;
        et_username = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_pass);
        queue = Volley.newRequestQueue(ctx);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_pass.getText().toString().equalsIgnoreCase("") || et_username.getText().toString().equalsIgnoreCase("")){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setMessage("All fields must be filled!");
                    dialog.setTitle("Error");
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    doLogin();
                }
            }
        });
    }

    void doLogin(){
        String username, pass;
        username = et_username.getText().toString();
        pass = et_pass.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, MyRequest.getLoginURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    s = new Session(ctx);
                    s.setUser(obj.getInt("id"), obj.getString("name"));

                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (Exception exc){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Error");
                    dialog.setMessage("Can't find user");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error volley");
                dialog.setMessage(""+error);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", pass);

                return params;
            }
        };

        queue.add(request);
    }
}