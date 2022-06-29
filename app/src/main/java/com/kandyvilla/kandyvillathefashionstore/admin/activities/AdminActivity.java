package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminActivity extends AppCompatActivity {
    CardView userdata,logout,popularproducts,category,allproducts,ordersreceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userdata = findViewById(R.id.userdata);
        logout = findViewById(R.id.logout);
        popularproducts = findViewById(R.id.popularproducts);
        category = findViewById(R.id.category);
        allproducts = findViewById(R.id.allproducts);
        ordersreceived = findViewById(R.id.ordersreceived);

        allproducts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AllProductsAdminActivity.class));

            }
        });
        ordersreceived.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,OrdersReceivedActivity.class));

            }
        });

        userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,UserDetailsActivity.class);
                startActivity(intent);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,CategoryAdminActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are You Sure ?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });

        popularproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,PopularProductActivity.class));
            }
        });


}
}