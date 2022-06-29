package com.kandyvilla.kandyvillathefashionstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.MyCartsFragment;
import com.kandyvilla.kandyvillathefashionstore.MyOrdersFragment;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.models.MyCartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<MyCartModel> cartModelList = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("cartList");

        if (cartModelList != null && cartModelList.size() > 0){
            for(MyCartModel model : cartModelList){
                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName",model.getProductName());
                cartMap.put("productImg",model.getProductImg());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("currentDate",model.getCurrentDate());
                cartMap.put("productQuantity",model.getProductQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());
                cartMap.put("productSize",model.getProductSize());


                firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PlacedOrderActivity.this,"Your Order Has Been Placed",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PlacedOrderActivity.this, MyOrdersFragment.class);
                                startActivity(intent);
                            }
                        });
            }

        }

    }
}