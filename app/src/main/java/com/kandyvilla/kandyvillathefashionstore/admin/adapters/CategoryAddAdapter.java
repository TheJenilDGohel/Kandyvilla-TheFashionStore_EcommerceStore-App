package com.kandyvilla.kandyvillathefashionstore.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.viewallActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.CategoryAdminActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.models.CategoryAddModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CategoryAddAdapter extends RecyclerView.Adapter<CategoryAddAdapter.ViewHolder> {
    Context context;
    List<CategoryAddModel> categoryAddModelList;
    FirebaseFirestore firestore;

    public CategoryAddAdapter(Context context, List<CategoryAddModel> categoryAddModelList) {
        this.context = context;
        this.categoryAddModelList = categoryAddModelList;

        firestore = FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public CategoryAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAddAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_category_card,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAddAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(categoryAddModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(categoryAddModelList.get(position).getName());
        holder.desc.setText(categoryAddModelList.get(position).getDescription());
        holder.type.setText("Type : " + categoryAddModelList.get(position).getType());
        holder.category.setText("Category : " +categoryAddModelList.get(position).getCategory());
        String a = categoryAddModelList.get(holder.getAdapterPosition()).getType();

        holder.editcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dailouge_content_category))
                        .setExpanded(true,1000)
                        .create();

                View myView = dialogPlus.getHolderView();

                EditText categoryname = myView.findViewById(R.id.edit_categoryname);
                EditText categorydesc = myView.findViewById(R.id.edit_categorydesc);
                Spinner categoryntype = myView.findViewById(R.id.edit_categorytype);
                Spinner productcategory = myView.findViewById(R.id.edit_productcategory);
                Button button = myView.findViewById(R.id.edit_productsubmit);

                List<String> types = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, types);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoryntype.setAdapter(arrayAdapter);

                List<String> categories = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","kurti","partywear","bottomwear","onepiece","saree");
                ArrayAdapter arraycategoryAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categories);
                arraycategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productcategory.setAdapter(arrayAdapter);

                String name = categoryAddModelList.get(holder.getAdapterPosition()).getName();
                String desc = categoryAddModelList.get(holder.getAdapterPosition()).getDescription();

                categoryname.setText(name);
                categorydesc.setText(desc);

                dialogPlus.show();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String productaddtype = categoryntype.getSelectedItem().toString();
                        String productaddcategory = productcategory.getSelectedItem().toString();

                        Map<String, Object> map = new HashMap<>();
                        map.put("name",categoryname.getText().toString());
                        map.put("description", categorydesc.getText().toString());
                        map.put("type", productaddtype);
                        map.put("category", productaddcategory);
                        String a = categoryAddModelList.get(holder.getAdapterPosition()).getDocumentId();

                        FirebaseFirestore.getInstance().collection("NavCategory")
                                .document(a).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                        new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Product Updated Successfully")
                                                .show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Category Not Updated.")
                                                .show();
                                    }
                                });
                    }
                });

            }
        });


        holder.deletecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are You Sure ?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                firestore.collection("NavCategory")
                                        .document(categoryAddModelList.get(holder.getAdapterPosition()).getDocumentId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Product Deleted !")
                                                        .show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Falied :"+e, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryAddModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,desc,type,category;
        ImageButton editcategory,deletecategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.nav_addcategoryimg);
            name = itemView.findViewById(R.id.nav_addcategoryname);
            desc = itemView.findViewById(R.id.nav_addcategorydesc);
            type = itemView.findViewById(R.id.nav_addcategorytype);
            category = itemView.findViewById(R.id.nav_addcategorycat);
            editcategory = itemView.findViewById(R.id.edit_category);
            deletecategory = itemView.findViewById(R.id.delete_category);
        }
    }
}
