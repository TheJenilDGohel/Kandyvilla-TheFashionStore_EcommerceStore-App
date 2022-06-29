package com.kandyvilla.kandyvillathefashionstore.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.admin.models.OrdersReceivedModel;

import java.util.List;

public class OrdersReceivedAdapter extends RecyclerView.Adapter<OrdersReceivedAdapter.ViewHolder>{
    Context context;
    List<OrdersReceivedModel> ordersReceivedModelList;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    public OrdersReceivedAdapter(Context context, List<OrdersReceivedModel> ordersReceivedModelList) {
        this.context = context;
        this.ordersReceivedModelList = ordersReceivedModelList;

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public OrdersReceivedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersReceivedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_received_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersReceivedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(ordersReceivedModelList.get(position).getProductImg()).into(holder.productImg);
        holder.name.setText(ordersReceivedModelList.get(position).getProductName());
        holder.quantity.setText("Qty : "+ ordersReceivedModelList.get(position).getProductQuantity() + "pcs.");
        holder.totalprice.setText("Total Price :" + String.valueOf(ordersReceivedModelList.get(position).getTotalPrice())+" ₹");
        holder.price.setText(ordersReceivedModelList.get(position).getProductPrice());
        holder.size.setText("Size : " + ordersReceivedModelList.get(position).getProductSize());
        holder.date.setText("Purchased On: " + ordersReceivedModelList.get(position).getCurrentDate());
    }

    @Override
    public int getItemCount() {
        return ordersReceivedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,totalprice,date,quantity,description,size;
        ImageView productImg;
        ImageView deleteProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.order_product_name);
            productImg = itemView.findViewById(R.id.order_product_img);
            price = itemView.findViewById(R.id.order_product_price);
            totalprice = itemView.findViewById(R.id.order_product_totalprice);
            date = itemView.findViewById(R.id.order_product_date);
            quantity = itemView.findViewById(R.id.order_product_qty);
            size = itemView.findViewById(R.id.order_product_size);
        }
    }
}
