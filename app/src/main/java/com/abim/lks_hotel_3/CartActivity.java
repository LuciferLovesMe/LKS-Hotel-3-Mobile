package com.abim.lks_hotel_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Context ctx;
    RequestQueue queue;
    List<Cart> carts;
    RecyclerView rv;
    Button btn_checkout;
    int emp_id, reservation_id, qty, total, fdid;
    String fd_name;
    final String my_key = "my_key";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ctx = this;
        queue = Volley.newRequestQueue(ctx);
        carts = new ArrayList<>();
        carts.clear();
        rv = findViewById(R.id.rv);
        btn_checkout = findViewById(R.id.btn_checkout);

        getData();

        if (getSharedPreferences("my_key", MODE_PRIVATE).getInt("keysize", 0) < 1){
            btn_checkout.setVisibility(View.GONE);
        }
        else {
            btn_checkout.setVisibility(View.VISIBLE);
        }

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int size = getSharedPreferences("my_key", MODE_PRIVATE).getInt("keysize", 0);
                    for (int i = 0; i < size; i++){
                        JSONObject obj = new JSONObject();
                        obj.put("reservationRoomId", getSharedPreferences(my_key, MODE_PRIVATE).getInt("reservationId"+i, 0));
                        obj.put("fdid", getSharedPreferences(my_key, MODE_PRIVATE).getInt("fdId"+i, 0));
                        obj.put("qty", getSharedPreferences(my_key, MODE_PRIVATE).getInt("qty"+i, 0));
                        obj.put("totalPrice", getSharedPreferences(my_key, MODE_PRIVATE).getInt("total"+i, 0));
                        obj.put("employeeId", getSharedPreferences(my_key, MODE_PRIVATE).getInt("employeeId"+i, 0));

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyRequest.getCheckOutURL(), obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                dialog.setTitle("Success");
                                dialog.setMessage("Successfully checked out");
                                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();

                                SharedPreferences.Editor editor =getSharedPreferences("my_key", MODE_PRIVATE).edit();
                                editor.putInt("keysize", 0);
                                editor.commit();
                                getData();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                dialog.setTitle("Success");
                                dialog.setMessage("Successfully checked out");
                                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();

                                SharedPreferences.Editor editor =getSharedPreferences("my_key", MODE_PRIVATE).edit();
                                editor.putInt("keysize", 0);
                                editor.commit();
                                getData();
                            }
                        });

                        queue.add(request);
                    }
                }
                catch (Exception ex){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Error");
                    dialog.setMessage(""+ex);
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    void getData(){
        int size = getSharedPreferences("my_key", MODE_PRIVATE).getInt("keysize", 0);

        for (int i = 0; i < size; i++){
            fdid = getSharedPreferences(my_key, MODE_PRIVATE).getInt("fdid"+i, 0);
            total = getSharedPreferences(my_key, MODE_PRIVATE).getInt("total"+i, 0);
            qty = getSharedPreferences(my_key, MODE_PRIVATE).getInt("qty"+i, 0);
            reservation_id = getSharedPreferences(my_key, MODE_PRIVATE).getInt("reservationId"+i, 0);
            emp_id = getSharedPreferences(my_key, MODE_PRIVATE).getInt("employeeId"+i, 0);
            fd_name = getSharedPreferences(my_key, MODE_PRIVATE).getString("fdName"+i, "");
            carts.add(new Cart(emp_id, reservation_id, qty, total, fdid, fd_name));
        }

        rv.setLayoutManager(new LinearLayoutManager(ctx));
        adapter = new Adapter(carts, ctx);
        rv.setAdapter(adapter);
    }
}