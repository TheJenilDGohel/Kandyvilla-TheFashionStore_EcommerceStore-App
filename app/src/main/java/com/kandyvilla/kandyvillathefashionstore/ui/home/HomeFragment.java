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

    //search box
//        SearchView searchbox;
    EditText searchbox;
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

            searchbox = root.findViewById(R.id.searchbox);
//            searchbox.clearFocus();
            recyclerView = root.findViewById(R.id.searchbox_items);

            viewallModellist = new ArrayList<>();
            viewallAdapter = new viewallAdapter(getContext(),viewallModellist);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            recyclerView.setAdapter(viewallAdapter);
            recyclerView.setHasFixedSize(true);

//            searchbox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
////                    filterList(newText);
//                    return true;
//                }
//            });


            searchbox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                        if(editable.toString().isEmpty()){
                            viewallModellist.clear();
                            viewallAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            searchProduct(editable.toString());
                        }
                }
            });


        return root;
    }

//    private void filterList(String text) {
//            List<viewallModel> filteredList = new ArrayList<>();
//
//            for(viewallModel item : viewallModellist)
//            {
//                if(item.getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
//                    filteredList.add(item);
//                }
//            }
//
//            if(filteredList.isEmpty())
//            {
//                Toast.makeText(getContext(), "Oops....", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                viewallAdapter.setfilteredList(filteredList);
//            }
//    }

    private void searchProduct(String name) {
            if(!name.toLowerCase(Locale.ROOT).isEmpty()){
                database.collection("AllProducts").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null)
                        {
                            viewallModellist.clear();
                            viewallAdapter.notifyDataSetChanged();

                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments())
                            {
                                viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                                viewallModellist.add(viewallModel);
                                viewallAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}