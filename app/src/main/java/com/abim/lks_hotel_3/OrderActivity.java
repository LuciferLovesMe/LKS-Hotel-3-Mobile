package com.abim.lks_hotel_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    RequestQueue queue;
    Context ctx;
    Session s;
    List<FD> fds;
    List<String> fd_list;
    List<String> roomNumber;
    List<Integer> room_id;
    Spinner spin_room, spin_fd;
    Button btn_inc, btn_dec, btn_add;
    EditText et_qty;
    TextView tv_sub, tv_price;
    int reservation_id, qty, total, fdid, price;
    String fd_name;

    List<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ctx = this;
        spin_fd = findViewById(R.id.spin_fd);
        spin_room = findViewById(R.id.spin_room);
        btn_inc = findViewById(R.id.btn_inc);
        btn_dec = findViewById(R.id.btn_dec);
        btn_add = findViewById(R.id.btn_add);
        et_qty = findViewById(R.id.et_qty);
        tv_sub = findViewById(R.id.tv_sub);
        tv_price = findViewById(R.id.tv_price);

        queue = Volley.newRequestQueue(ctx);
        fds = new ArrayList<>();
        fds.clear();
        fd_list = new ArrayList<>();
        fd_list.clear();
        room_id = new ArrayList<>();
        room_id.clear();
        roomNumber = new ArrayList<>();
        roomNumber.clear();
        carts = new ArrayList<>();
        carts.clear();
        s = new Session(ctx);

        getFood();
        getRoom();

        qty = 0;

        et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                qty =Integer.valueOf(String.valueOf(s));
                total = Integer.valueOf(tv_price.getText().toString()) * qty;
                tv_sub.setText(String.valueOf(total));
            }
        });

        spin_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reservation_id = Integer.valueOf(room_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_fd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FD fd = fds.get(position);
                fd_name = fd.getName();
                fdid = fd.getId();
                tv_price.setText(String.valueOf(fd.getPrice()));
                price = fd.getPrice();
                total = Integer.valueOf(price * qty);
                tv_sub.setText(String.valueOf(Integer.valueOf(total)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.valueOf(et_qty.getText().toString());
                qty++;
                et_qty.setText(String.valueOf(qty));
                total = Integer.valueOf(price * qty);
                tv_sub.setText(String.valueOf(Integer.valueOf(total)));
            }
        });
        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(et_qty.getText().toString()) < 1){
                    qty = 0;
                }
                else if (Integer.valueOf(et_qty.getText().toString()) > 0){
                    qty = Integer.valueOf(et_qty.getText().toString());
                    qty--;
                }
                et_qty.setText(String.valueOf(qty));
                total = Integer.valueOf(price * qty);
                tv_sub.setText(String.valueOf(Integer.valueOf(total)));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_qty.getText().toString().equalsIgnoreCase("0") || et_qty.getText().toString().equalsIgnoreCase("0")){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setMessage("All fields must be filled");
                    dialog.setTitle("Error");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    add();
                }
            }
        });
    }

    void getFood(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getFdURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject object = response.getJSONObject(i);
                        fds.add(new FD(object.getInt("id"), object.getInt("price"), object.getString("name")));
                    }
                    for (FD fd :fds){
                        fd_list.add(fd.getName());
                    }

                    ArrayAdapter<String> adapter= new ArrayAdapter<String>(ctx, R.layout.support_simple_spinner_dropdown_item, fd_list);
                    spin_fd.setAdapter(adapter);
                }catch (Exception ex){
                    Toast.makeText(ctx, ""+ex, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error FD");
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setMessage(error.getMessage());
                dialog.show();
            }
        });

        queue.add(request);
    }
    void getRoom(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getRoomURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        room_id.add(obj.getInt("id"));
                        roomNumber.add(obj.getString("roomnumber"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.support_simple_spinner_dropdown_item, roomNumber);
                    spin_room.setAdapter(adapter);
                }
                catch (Exception ex){
                    Toast.makeText(ctx, ""+ex, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error FD");
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setMessage(error.getMessage());
                dialog.show();
            }
        });

        queue.add(request);
    }

    void add(){
        carts.add(new Cart(s.getId(), reservation_id, qty, total, fdid, fd_name));
        SharedPreferences.Editor editor =getSharedPreferences("my_key", MODE_PRIVATE).edit();
        editor.putInt("keysize", carts.size());
        editor.commit();

        for (int i = 0; i < carts.size(); i++){
            Cart cart = carts.get(i);
            SharedPreferences.Editor edit = getSharedPreferences("my_key", MODE_PRIVATE).edit();
            edit.putInt("fdId"+i, cart.getFdid());
            edit.putInt("employeeId"+i, cart.getEmp_id());
            edit.putInt("qty"+i, cart.getQty());
            edit.putInt("reservationId"+i, cart.getReservation_id());
            edit.putInt("total"+i, cart.getTotal());
            edit.putString("fdName"+i, cart.getFdName());

            edit.commit();
        }

        Toast.makeText(ctx, "Successfully add to cart", Toast.LENGTH_SHORT).show();
    }
}