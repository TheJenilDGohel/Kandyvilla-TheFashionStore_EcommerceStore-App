package com.kandyvilla.kandyvillathefashionstore.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.navcategoryAdapter;
import com.kandyvilla.kandyvillathefashionstore.models.navcategoryModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    FirebaseFirestore database;
    RecyclerView navcategoryview;
    List<navcategoryModel> categoryModelList;
    navcategoryAdapter nav_categoryAdapter;
    ProgressBar progressBar;
    ScrollView scrollView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category,container,false);

        database = FirebaseFirestore.getInstance();
        navcategoryview = root.findViewById(R.id.nav_categoryrec);
        progressBar = root.findViewById(R.id.progressbar_category);
        scrollView = root.findViewById(R.id.scrollview_category);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);


        //Home Category Icons

        navcategoryview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        categoryModelList = new ArrayList<>();
        nav_categoryAdapter = new navcategoryAdapter(getActivity(),categoryModelList);
        navcategoryview.setAdapter(nav_categoryAdapter);

        //read data firestore code firebase

        database.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                navcategoryModel navcategoryModel = document.toObject(com.kandyvilla.kandyvillathefashionstore.models.navcategoryModel.class);
                                categoryModelList.add(navcategoryModel);
                                nav_categoryAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}