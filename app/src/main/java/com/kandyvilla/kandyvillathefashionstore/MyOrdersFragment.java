package com.kandyvilla.kandyvillathefashionstore;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.adapters.MyCartAdapter;
import com.kandyvilla.kandyvillathefashionstore.adapters.MyOrdersAdapter;
import com.kandyvilla.kandyvillathefashionstore.models.MyCartModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {
    RecyclerView recyclerView;
    MyOrdersAdapter myCartAdapter;
    List<MyCartModel> cartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Context context;
    RelativeLayout emptyorder;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        emptyorder = root.findViewById(R.id.emptyorder);
        recyclerView = root.findViewById(R.id.myorderData);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        cartModelList = new ArrayList<>();
        myCartAdapter = new MyOrdersAdapter(getActivity(),cartModelList);

        emptyorder.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(myCartAdapter);

        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            emptyorder.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        if(cartModelList.isEmpty())
                        {
                                emptyorder.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);

                        }}


                    }
                });

        return  root;
    }

}