package com.abim.lks_hotel_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        ctx = this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.card_cart){
            Intent i = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(i);
        }
        else if (id == R.id.card_order){
            Intent i = new Intent(DashboardActivity.this, OrderActivity.class);
            startActivity(i);
        }
        else if (id == R.id.card_info){
            Intent i = new Intent(DashboardActivity.this, InfoActivity.class);
            startActivity(i);
        }
        else if (id == R.id.card_logout){
            AlertDialog dialog = new AlertDialog.Builder(ctx).create();
            dialog.setTitle("Confirmation");
            dialog.setMessage("Are you sure to logout?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}