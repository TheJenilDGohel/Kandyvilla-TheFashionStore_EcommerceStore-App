package com.kandyvilla.kandyvillathefashionstore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.LoginActivity;
import com.kandyvilla.kandyvillathefashionstore.admin.activities.AdminActivity;
import com.kandyvilla.kandyvillathefashionstore.models.MyCartModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> cartModelList;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(cartModelList.get(position).getProductImg()).into(holder.productImg);
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.quantity.setText("Qty : "+ cartModelList.get(position).getProductQuantity() + "pcs.");
        holder.totalprice.setText("Total Price :" + String.valueOf(cartModelList.get(position).getTotalPrice())+" ₹");
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.size.setText("Size : " + cartModelList.get(position).getProductSize());
        holder.date.setText("Purchased On: " + cartModelList.get(position).getCurrentDate());

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to remove this item from your cart ?")
                                .setConfirmText("Yes")
                                        .setCancelText("No")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                                                                .collection("AddToCart")
                                                                .document(cartModelList.get(holder.getAdapterPosition()).getDocumentId())
                                                                .delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            cartModelList.remove(cartModelList.get(holder.getAdapterPosition()));
                                                                            notifyDataSetChanged();
                                                                            Toast.makeText(context, "Item Removed From Cart !!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        else
                                                                        {
                                                                            Toast.makeText(context, "Error :"+task.getException(), Toast.LENGTH_SHORT).show();
                                                                        }
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
        return cartModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,totalprice,date,quantity,size;
        ImageView productImg;
        ImageView deleteProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cart_product_name);
            productImg = itemView.findViewById(R.id.cart_product_img);
            price = itemView.findViewById(R.id.cart_product_price);
            totalprice = itemView.findViewById(R.id.cart_product_totalprice);
            date = itemView.findViewById(R.id.cart_product_date);
            quantity = itemView.findViewById(R.id.cart_product_qty);
            size = itemView.findViewById(R.id.cart_product_size);
            deleteProduct = itemView.findViewById(R.id.cart_product_delete);
        }
    }
}
