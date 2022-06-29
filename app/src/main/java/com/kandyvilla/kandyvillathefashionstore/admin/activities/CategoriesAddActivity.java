package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.LoginActivity;
import com.kandyvilla.kandyvillathefashionstore.activities.RegistrationActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CategoriesAddActivity extends AppCompatActivity {
    EditText categoryname, categorydescription;
    Spinner categorytype, category;
    RoundedImageView categoryimg;
    Button addcategory;
    FirebaseStorage storage;
    String url,category_type,category_name;
    Button backbutton;
    ImageView backbuttontoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_add);

        storage = FirebaseStorage.getInstance();
        categorytype = (Spinner) findViewById(R.id.categorytype);
        category = (Spinner) findViewById(R.id.category);
        backbutton  = findViewById(R.id.backtohome);
        backbuttontoolbar = findViewById(R.id.backbuttoncategory);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoriesAddActivity.this, CategoryAdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        backbuttontoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoriesAddActivity.this, CategoryAdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        List<String> types = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorytype.setAdapter(arrayAdapter);

        List<String> categories = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
        ArrayAdapter categoryAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        categoryname = findViewById(R.id.categoryname);
        categorydescription = findViewById(R.id.categorydesc);
        addcategory = findViewById(R.id.categorybtn);
        categoryimg = findViewById(R.id.categoryimg);

        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_type = categorytype.getSelectedItem().toString();
                category_name = category.getSelectedItem().toString();
                categoryinsert();
            }
        });

        categoryimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            DocumentReference category_prod = database.collection("NavCategory").document();
            Uri productUri = data.getData();
            categoryimg.setImageURI(productUri);

            final StorageReference storageReference = storage.getReference().child("category")
                    .child(FirebaseAuth.getInstance().toString());
            storageReference.putFile(productUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CategoriesAddActivity.this, "Please Wait.....", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CategoriesAddActivity.this, "Image uploaded !!", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Toast.makeText(CategoriesAddActivity.this, "Category Image Uploaded Successfully !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void categoryinsert() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference category_prod = firestore.collection("NavCategory").document();

        Map<String, Object> map = new HashMap<>();
        map.put("name", categoryname.getText().toString());
        map.put("description", categorydescription.getText().toString());
        map.put("type", category_type);
        map.put("category",category_name);
        map.put("img_url", url);

        category_prod.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CategoriesAddActivity.this, "Product Successfully Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}