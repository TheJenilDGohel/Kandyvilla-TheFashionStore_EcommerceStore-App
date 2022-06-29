package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.navcategoryAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.CategoryAddAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.models.CategoryAddModel;
import com.kandyvilla.kandyvillathefashionstore.models.navcategoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CategoryAdminActivity extends AppCompatActivity {
    FirebaseFirestore database;
    RecyclerView navcategoryview;
    List<CategoryAddModel> categoryAddModelList;
    CategoryAddAdapter categoryAddAdapter;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);
        database = FirebaseFirestore.getInstance();
        navcategoryview = findViewById(R.id.nav_categorydata);
        floatingActionButton = findViewById(R.id.floatingActionButtoncategory);
        progressBar = findViewById(R.id.progressbar_category);
        backbutton = findViewById(R.id.backbuttonadmincategories);

        progressBar.setVisibility(View.VISIBLE);
        navcategoryview.setVisibility(View.GONE);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, CategoriesAddActivity.class);
                startActivity(intent);
            }
        });
        //Home Category Icons

        navcategoryview.setLayoutManager(new GridLayoutManager(CategoryAdminActivity.this, 2));
        categoryAddModelList = new ArrayList<>();
        categoryAddAdapter = new CategoryAddAdapter(this, categoryAddModelList);
        navcategoryview.setAdapter(categoryAddAdapter);

        //read data firestore code firebase


        database.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String document_id = document.getId();
                                CategoryAddModel categoryAddModel = document.toObject(CategoryAddModel.class);
                                categoryAddModel.setDocumentId(document_id);
                                categoryAddModelList.add(categoryAddModel);
                                categoryAddAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                navcategoryview.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(CategoryAdminActivity.this, "Error" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}