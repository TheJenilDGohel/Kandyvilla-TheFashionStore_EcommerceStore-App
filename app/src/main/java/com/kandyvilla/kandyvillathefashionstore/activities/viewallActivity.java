package com.kandyvilla.kandyvillathefashionstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.adapters.viewallAdapter;
import com.kandyvilla.kandyvillathefashionstore.models.viewallModel;

import java.util.ArrayList;
import java.util.List;

public class viewallActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    viewallAdapter viewallAdapter;
    List<viewallModel> viewallModelList;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);

        imageView = findViewById(R.id.backbuttonallprofducts);
        textView = findViewById(R.id.txtviewall);
        firestore = FirebaseFirestore.getInstance();
        String type  = getIntent().getStringExtra("type");


        recyclerView = findViewById(R.id.viewallproducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        viewallModelList =new ArrayList<>();
        viewallAdapter = new viewallAdapter(this,viewallModelList);
        recyclerView.setAdapter(viewallAdapter);



        //All products of cards
        if(type!=null && type.equalsIgnoreCase("Salwar & Suits"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","Salwar & Suits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //All products of cards
        if(type!=null && type.equalsIgnoreCase("Anarkali Suits"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","Anarkali Suits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        //All products of cards
        if(type!=null && type.equalsIgnoreCase("Lehnga"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","Lehnga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        //All products products of home
        if(type!=null && type.equalsIgnoreCase("IndoWestern"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","IndoWestern").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //All category products of cards

        //All category products of cards
        if(type!=null && type.equalsIgnoreCase("officewear"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","officewear").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        //All category products of cards
        if(type!=null && type.equalsIgnoreCase("partywear"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","partywear").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //All category products of cards

        if(type!=null && type.equalsIgnoreCase("kurti"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","kurti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        if(type!=null && type.equalsIgnoreCase("WesternWear"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","WesternWear").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null && type.equalsIgnoreCase("onepiece"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","onepiece").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null && type.equalsIgnoreCase("saree"))
        {
            firestore.collection("AllProducts").whereEqualTo("type","saree").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                    {
                        viewallModel viewallModel = documentSnapshot.toObject(com.kandyvilla.kandyvillathefashionstore.models.viewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                    }
                }
            });
        }




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(viewallActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}