package com.kandyvilla.kandyvillathefashionstore.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.homecategoryAdapter;
import com.kandyvilla.kandyvillathefashionstore.adapters.popularproductsAdapter;
import com.kandyvilla.kandyvillathefashionstore.adapters.viewallAdapter;
import com.kandyvilla.kandyvillathefashionstore.models.homecategoryModel;
import com.kandyvilla.kandyvillathefashionstore.models.popularproductsModel;
import com.kandyvilla.kandyvillathefashionstore.models.viewallModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView popularproductsview,homecategoryview;
    FirebaseFirestore database;
    //popularitems

        List<popularproductsModel> popularproductsModelList;
        com.kandyvilla.kandyvillathefashionstore.adapters.popularproductsAdapter popularproductsAdapter;

    //Home Category Items
        List<homecategoryModel> homecategoryModelList;
        homecategoryAdapter homecategoryAdapter;

    SearchView searchbox;
    private List<viewallModel> viewallModellist;
    private RecyclerView recyclerView;
    private viewallAdapter viewallAdapter;

        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        database = FirebaseFirestore.getInstance();
        popularproductsview = root.findViewById(R.id.popularproducts);
        homecategoryview = root.findViewById(R.id.categories);
        scrollView = root.findViewById(R.id.scrollview_home);
        progressBar = root.findViewById(R.id.progressbar_home);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //popular products

        popularproductsview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularproductsModelList = new ArrayList<>();
        popularproductsAdapter = new popularproductsAdapter(getActivity(),popularproductsModelList);
        popularproductsview.setAdapter(popularproductsAdapter);

        //read data firestore code firebase

        database.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                popularproductsModel popularproductsModel = document.toObject(popularproductsModel.class);
                                popularproductsModelList.add(popularproductsModel);
                                popularproductsAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            //Home Category Icons

            homecategoryview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            homecategoryModelList = new ArrayList<>();
            homecategoryAdapter = new homecategoryAdapter(getActivity(),homecategoryModelList);
            homecategoryview.setAdapter(homecategoryAdapter);

            //read data firestore code firebase

            database.collection("HomeCategory")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    homecategoryModel homecategoryModel = document.toObject(com.kandyvilla.kandyvillathefashionstore.models.homecategoryModel.class);
                                    homecategoryModelList.add(homecategoryModel);
                                    homecategoryAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });



//search items

            viewallModellist = new ArrayList<>();
            viewallAdapter = new viewallAdapter(getContext(),viewallModellist);

            database.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            String document_id = documentSnapshot.getId();
                            viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                            viewallModel.setDocument_id(document_id);
                            viewallModellist.add(viewallModel);
                            viewallAdapter.notifyDataSetChanged();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Error :"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            searchbox = root.findViewById(R.id.searchbox);
            recyclerView = root.findViewById(R.id.searchbox_items);

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            searchbox.clearFocus();
            searchbox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String newtext) {
                    filteredList(newtext);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newtext) {
                    filteredList(newtext);
                    return true;
                }
            });

        return root;
    }

    private void filteredList(String newtext) {
            if (newtext.isEmpty()){
                recyclerView.setVisibility(View.GONE);
            }
            else{
                recyclerView.setVisibility(View.VISIBLE);
                List<viewallModel> filteredList = new ArrayList<>();

                for(viewallModel viewallModel:viewallModellist){
                    if (viewallModel.getName().toLowerCase().contains(newtext.toLowerCase())){
                        filteredList.add(viewallModel);
                    }
                    recyclerView.setAdapter(viewallAdapter);
                }
                if(filteredList.isEmpty()){
                    viewallModellist.clear();
                    Toast.makeText(getContext(), "No Data Found !", Toast.LENGTH_SHORT).show();
                }

                else{
                    viewallAdapter.setfilteredList(filteredList);
                }
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}