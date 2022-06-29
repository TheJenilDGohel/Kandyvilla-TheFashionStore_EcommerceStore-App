package com.kandyvilla.kandyvillathefashionstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.activities.PlacedOrderActivity;
import com.kandyvilla.kandyvillathefashionstore.adapters.MyCartAdapter;
import com.kandyvilla.kandyvillathefashionstore.models.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> cartModelList;
    TextView totaltopamount;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Context context;

    ImageView emptycart;

    Button buyNowButton;
    TextView emptycarttxt,emptycarttxt2;


    public MyCartsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        emptycart=root.findViewById(R.id.emptycartimg);
        emptycarttxt=root.findViewById(R.id.emptycarttxt);
        emptycarttxt2=root.findViewById(R.id.emptycarttxt2);
        buyNowButton = root.findViewById(R.id.comfirm_order);
        buyNowButton.setVisibility(View.GONE);

        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are You Sure ?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                                intent.putExtra("cartList", (Serializable) cartModelList);
//                                deletecart();
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        totaltopamount = root.findViewById(R.id.cart_top_totalamount);

        recyclerView = root.findViewById(R.id.cart_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        cartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),cartModelList);

        recyclerView.setAdapter(myCartAdapter);

        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }

                            totalamountcalculation(cartModelList);
                        }
                    }
                });

        return root;
    }

    private void deletecart() {
    }


    private void totalamountcalculation(List<MyCartModel> cartModelList) {
        double totalamount = 0.0;


        for(MyCartModel myCartModel : cartModelList){
            totalamount+= myCartModel.getTotalPrice();
        }
        totaltopamount.setText("Total Amount :"+ String.valueOf(totalamount)+ " ₹");

        refresh(50);

        if (totalamount == 0.0)
        {
            emptycart.setVisibility(View.VISIBLE);
            emptycarttxt.setVisibility(View.VISIBLE);
            emptycarttxt2.setVisibility(View.VISIBLE);
            buyNowButton.setVisibility(View.GONE);
        }
        else
        {
            emptycart.setVisibility(View.GONE);
            emptycarttxt.setVisibility(View.GONE);
            emptycarttxt2.setVisibility(View.GONE);
            buyNowButton.setVisibility(View.VISIBLE);
        }

    }

    private void refresh(int i) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                totalamountcalculation(cartModelList);
            }
        };

        handler.postDelayed(runnable,i);
    }

}