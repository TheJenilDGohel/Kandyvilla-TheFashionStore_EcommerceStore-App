package com.kandyvilla.kandyvillathefashionstore.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AllProductsAddActivity extends AppCompatActivity {
    EditText productname, productdescription, productrating, productprice, productdiscount;
    Spinner producttype, productcategory;
    RoundedImageView productimg;
    Button addproduct;
    FirebaseStorage storage;
    String url, productaddtype, productaddcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_add);

        storage = FirebaseStorage.getInstance();
        producttype = (Spinner) findViewById(R.id.popularproductstype);
        productcategory = (Spinner) findViewById(R.id.popularproductscategory);

        List<String> types = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        producttype.setAdapter(arrayAdapter);

        List<String> category = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
        ArrayAdapter arraycategoryAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, category);
        arraycategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productcategory.setAdapter(arraycategoryAdapter);

        productname = findViewById(R.id.popularproductsaddname);
        productdiscount = findViewById(R.id.popularproductsdiscount);
        productdescription = findViewById(R.id.popularproductsdesc);
        productprice = findViewById(R.id.popularproductsprice);
        productrating = findViewById(R.id.popularproductsrating);
        addproduct = findViewById(R.id.popularproductsbtn);
        productimg = findViewById(R.id.popularproductsimg);

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productaddtype = producttype.getSelectedItem().toString();
                productaddcategory = productcategory.getSelectedItem().toString();
                productinsert();
            }
        });

        productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

    }

    private void productinsert() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference all_product = firestore.collection("AllProducts").document();
        String price = productprice.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("name", productname.getText().toString());
        map.put("description", productdescription.getText().toString());
        map.put("rating", productrating.getText().toString());
        map.put("price", Integer.parseInt(price));
        map.put("type", productaddtype);
        map.put("category", productaddcategory);
        map.put("discount", productdiscount.getText().toString() + "% Off");
        map.put("img_url", url);

        all_product
                .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(), "Product Added Successfully !!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AllProductsAddActivity.this,AllProductsAdminActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            DocumentReference pop_product = database.collection("PopularProducts").document();
            Uri productUri = data.getData();
            productimg.setImageURI(productUri);

            final StorageReference storageReference = storage.getReference().child("popular_products")
                    .child(FirebaseAuth.getInstance().toString());
            storageReference.putFile(productUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AllProductsAddActivity.this, "Image uploaded !", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Toast.makeText(AllProductsAddActivity.this, "Product Image Uploaded Successfully !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}