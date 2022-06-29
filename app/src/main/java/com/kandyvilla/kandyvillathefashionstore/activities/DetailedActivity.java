package com.kandyvilla.kandyvillathefashionstore.activities;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.models.viewallModel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    TextView productQuantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    ImageView productImg;
    TextView productPrice,productRating,productDescription;
    Button addToCart;
    String productSize;
    ImageButton addProduct,removeProduct;
    ImageView backButton;
    viewallModel viewallModel = null;


    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        final Spinner spinner = findViewById(R.id.product_size);
        List<String> product_sizes = Arrays.asList("Free Size","S - Small","M- Medium","L - Large","XL - Extra Large");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,product_sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("details");

        if(object instanceof viewallModel){
            viewallModel = (com.kandyvilla.kandyvillathefashionstore.models.viewallModel) object;
        }



        productImg = findViewById(R.id.productimage);
        productPrice = findViewById(R.id.productprice);
        productRating = findViewById(R.id.productrating);
        productDescription = findViewById(R.id.productdesc);

        if(viewallModel != null){
            Glide.with(getApplicationContext()).load(viewallModel.getImg_url()).into(productImg);
            productRating.setText(viewallModel.getRating());
            productPrice.setText("Price : "+String.valueOf(viewallModel.getPrice())+" ₹");
            productDescription.setText(viewallModel.getDescription());
            productRating.setText(viewallModel.getRating());
        }

        addToCart = findViewById(R.id.add_to_cart);
        addProduct = findViewById(R.id.addproduct);
        removeProduct = findViewById(R.id.removeproduct);
        backButton = findViewById(R.id.backbuttonallprofducts);
        productQuantity = findViewById(R.id.productcount);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity <10){
                    totalQuantity++;

                    if(totalQuantity == 10) {
                        Toast toast = Toast.makeText(getApplicationContext(),"MAX 10 ITEMS ALLOWED",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    productQuantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewallModel.getPrice() * totalQuantity;
                }

            }
        });

        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity >1){
                    totalQuantity--;
                    productQuantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewallModel.getPrice() * totalQuantity;

                }

            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                productSize = spinner.getSelectedItem().toString();
                addToCart();
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addToCart() {
        if(totalQuantity == 1)
        {
            totalPrice = viewallModel.getPrice();
        }
        String currentDate,currentTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        currentTime = timeFormat.format(calendar.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",viewallModel.getName());
        cartMap.put("productImg",viewallModel.getImg_url());
        cartMap.put("productPrice",productPrice.getText().toString());
        cartMap.put("currentDate",currentDate);
        cartMap.put("currentTime",currentTime);
        cartMap.put("productQuantity",productQuantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        cartMap.put("productSize",productSize);

        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this,"Added To A Cart",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
}