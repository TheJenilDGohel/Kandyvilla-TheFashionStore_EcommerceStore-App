package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.popularproductsAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.AllProductsAdminAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.CategoryAddAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.adapters.PopularAdminAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.models.CategoryAddModel;
import com.kandyvilla.kandyvillathefashionstore.admin.models.PopularProductModelAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllProductsAdminActivity extends AppCompatActivity {
    RecyclerView allproductsview;
    FirebaseFirestore database;
    AllProductsAdminAdapter popularproductsAdapter;
    FloatingActionButton floatingActionButton;
    List<PopularProductModelAdmin> popularProductModelAdminList;
    ProgressBar progressBar;
    SearchView searchView;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_admin);

        database = FirebaseFirestore.getInstance();
        allproductsview = findViewById(R.id.allproductsdata);
        floatingActionButton = findViewById(R.id.floatingActionButtoncategory);
        progressBar = findViewById(R.id.progressbar_category);
        progressBar.setVisibility(View.VISIBLE);
        allproductsview.setVisibility(View.GONE);
        backbutton = findViewById(R.id.backbuttonadminallproducts);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllProductsAdminActivity.this, AllProductsAddActivity.class);
                startActivity(intent);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllProductsAdminActivity.this,AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        allproductsview.setLayoutManager(new GridLayoutManager(this,2));
        popularProductModelAdminList = new ArrayList<>();
        popularproductsAdapter = new AllProductsAdminAdapter(this,popularProductModelAdminList);
        allproductsview.setAdapter(popularproductsAdapter);

        database.collection("AllProducts")
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

                                progressBar.setVisibility(View.GONE);
                                allproductsview.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(AllProductsAdminActivity.this,"Error"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        searchView = findViewById(R.id.searchview);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<PopularProductModelAdmin> filteredList = new ArrayList<>();

        for (PopularProductModelAdmin popularProductModelAdmin : popularProductModelAdminList)
        {
            if(popularProductModelAdmin.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(popularProductModelAdmin);
            }
        }

        if(filteredList.isEmpty())
        {
            Toast.makeText(this, "No Data Found !", Toast.LENGTH_SHORT).show();
        }
        else{
            popularproductsAdapter.setFilteredList(filteredList);
        }
    }
}