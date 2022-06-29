package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.popularproductsAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.PopularAdminAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.models.PopularProductModelAdmin;
import com.kandyvilla.kandyvillathefashionstore.models.popularproductsModel;

import java.util.ArrayList;
import java.util.List;

public class PopularProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore database;
    PopularAdminAdapter popularproductsAdapter;
    FloatingActionButton floatingActionButton;
    List<PopularProductModelAdmin> popularProductModelAdminList;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_product);

        recyclerView = findViewById(R.id.popularproductsdata);
        database = FirebaseFirestore.getInstance();
        floatingActionButton = findViewById(R.id.floatingActionButton);
        backbutton = findViewById(R.id.backbuttonadminpopularproducts);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularProductActivity.this,AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularProductActivity.this,PopularProductAddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        popularProductModelAdminList = new ArrayList<>();
        popularproductsAdapter = new PopularAdminAdapter(this,popularProductModelAdminList);
        recyclerView.setAdapter(popularproductsAdapter);


        database.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                String document_id = document.getId();
                                PopularProductModelAdmin popularProductModelAdmin = document.toObject(PopularProductModelAdmin.class);
                                popularProductModelAdmin.setDocumentId(document_id);
                                popularProductModelAdminList.add(popularProductModelAdmin);
                                popularproductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(PopularProductActivity.this,"Error"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}