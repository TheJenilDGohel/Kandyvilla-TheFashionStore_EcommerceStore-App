package com.kandyvilla.kandyvillathefashionstore.admin.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.LoginActivity;
import com.kandyvilla.kandyvillathefashionstore.adapters.popularproductsAdapter;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.AdminActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.AllProductsAddActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.AllProductsAdminActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.models.PopularProductModelAdmin;
import com.kandyvilla.kandyvillathefashionstore.models.popularproductsModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class PopularAdminAdapter extends RecyclerView.Adapter<PopularAdminAdapter.ViewHolder> {
    Context context;
    List<PopularProductModelAdmin> popularProductModelAdmins;
    FirebaseFirestore firestore;
    PopularProductModelAdmin popularProductModelAdmin;
    //List<popularproductsModel> popularProductModelAdmins;

    public PopularAdminAdapter(Context context, List<PopularProductModelAdmin> popularProductModelAdmins) {
        this.context = context;
        this.popularProductModelAdmins = popularProductModelAdmins;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_product_card_admin, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularProductModelAdmins.get(position).getImg_url()).into(holder.popularproductsImg);
        holder.price.setText(popularProductModelAdmins.get(position).getPrice()+" ₹");
        holder.name.setText(popularProductModelAdmins.get(position).getName());
        holder.rating.setText("Rating : " + popularProductModelAdmins.get(position).getRating());
        holder.type.setText("Type : " + popularProductModelAdmins.get(position).getType());
        holder.category.setText("Category : " + popularProductModelAdmins.get(position).getCategory());
        holder.description.setText(popularProductModelAdmins.get(position).getDescription());
        holder.discount.setText("Discount : " + popularProductModelAdmins.get(position).getDiscount());

        holder.productedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.popularproductsImg.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dailoge_content))
                        .setExpanded(true,1900)
                        .create();

                View myView = dialogPlus.getHolderView();
                EditText  productname = myView.findViewById(R.id.edit_productname);
                EditText price = myView.findViewById(R.id.edit_productprice);
                EditText rating = myView.findViewById(R.id.edit_productrating);
                Spinner type = myView.findViewById(R.id.edit_producttype);
                Spinner category = myView.findViewById(R.id.edit_productcategory);
                EditText description = myView.findViewById(R.id.edit_productdesc);
                EditText discount = myView.findViewById(R.id.edit_productdiscount);
                Button button = myView.findViewById(R.id.edit_productsubmit);


                List<String> types = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","saree");
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, types);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type.setAdapter(arrayAdapter);

                List<String> categories = Arrays.asList("IndoWestern", "WesternWear", "officewear","Salwar & Suits","Anarkali Suits","Lehnga","saree");
                ArrayAdapter arraycategoryAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categories);
                arraycategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(arrayAdapter);

                String product_type = popularProductModelAdmins.get(holder.getAdapterPosition()).getType();
                String product_category = popularProductModelAdmins.get(holder.getAdapterPosition()).getCategory();
                String product_name = popularProductModelAdmins.get(holder.getAdapterPosition()).getName();
                Integer product_price = popularProductModelAdmins.get(holder.getAdapterPosition()).getPrice();
                String product_description = popularProductModelAdmins.get(holder.getAdapterPosition()).getDescription();
                String product_rating = popularProductModelAdmins.get(holder.getAdapterPosition()).getRating();
                String product_discount = popularProductModelAdmins.get(holder.getAdapterPosition()).getDiscount();
                productname.setText(product_name);
                price.setText(String.valueOf(product_price));
                rating.setText(product_rating);
                description.setText(product_description);
                discount.setText(product_discount);

                dialogPlus.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String productaddtype = type.getSelectedItem().toString();
                            String productaddcategory = category.getSelectedItem().toString();
                            String product_price = price.getText().toString();

                            Map<String, Object> map = new HashMap<>();
                            map.put("name", productname.getText().toString());
                            map.put("description", description.getText().toString());
                            map.put("rating", rating.getText().toString());
                            map.put("price", Integer.parseInt(product_price));
                            map.put("type", productaddtype);
                            map.put("category", productaddcategory);
                            map.put("discount", discount.getText().toString());
                            String a = popularProductModelAdmins.get(holder.getAdapterPosition()).getDocumentId();

                            FirebaseFirestore.getInstance().collection("PopularProducts")
                                    .document(a).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialogPlus.dismiss();
                                            FirebaseFirestore.getInstance().collection("AllProducts")
                                                    .document(a).update(map);
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

        holder.productdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are You Sure ?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                firestore.collection("PopularProducts")
                                        .document(popularProductModelAdmins.get(holder.getAdapterPosition()).getDocumentId())
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

                                 }
                        }).show();
                }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductModelAdmins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popularproductsImg;
        ImageButton productdelete,productedit;
        TextView name,description,price,type,category,rating,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularproductsImg = itemView.findViewById(R.id.popularproductsimg);
            name = itemView.findViewById(R.id.popularproductsname);
            description = itemView.findViewById(R.id.popularproductsdesc);
            price = itemView.findViewById(R.id.productprice);
            type = itemView.findViewById(R.id.producttype);
            category = itemView.findViewById(R.id.productcategory);
            rating = itemView.findViewById(R.id.productrating);
            discount = itemView.findViewById(R.id.productdiscount);
            productdelete = itemView.findViewById(R.id.delete_product);
            productedit = itemView.findViewById(R.id.edit_product);
        }
    }
}
