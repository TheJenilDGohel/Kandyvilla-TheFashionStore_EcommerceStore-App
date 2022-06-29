package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.MyCartAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.CategoryAddAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.OrdersReceivedAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.UserDetailsAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.models.CategoryAddModel;
import com.kandyvilla.kandyvillathefashionstore.admin.models.OrdersReceivedModel;
import com.kandyvilla.kandyvillathefashionstore.admin.models.PopularProductModelAdmin;
import com.kandyvilla.kandyvillathefashionstore.admin.models.UserDetailsModel;
import com.kandyvilla.kandyvillathefashionstore.models.MyCartModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersReceivedActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<OrdersReceivedModel> ordersReceivedModelList;
    OrdersReceivedAdapter ordersReceivedAdapter;
    OrdersReceivedModel ordersReceivedModel;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_received);

        backbutton = findViewById(R.id.backbuttonordersreceived);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.order_data);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ordersReceivedModelList = new ArrayList<>();
        ordersReceivedAdapter = new OrdersReceivedAdapter(this, ordersReceivedModelList);
        recyclerView.setAdapter(ordersReceivedAdapter);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersReceivedActivity.this,AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}