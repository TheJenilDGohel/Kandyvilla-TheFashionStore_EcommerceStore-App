package com.kandyvilla.kandyvillathefashionstore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.activities.viewallActivity;
import com.kandyvilla.kandyvillathefashionstore.models.popularproductsModel;

import java.util.List;

public class popularproductsAdapter extends RecyclerView.Adapter<popularproductsAdapter.ViewHolder> {
    private Context context;
    private List<popularproductsModel> popularproductsModelList;

    public popularproductsAdapter(Context context, List<popularproductsModel> popularproductsModelList) {
        this.context = context;
        this.popularproductsModelList = popularproductsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularproductsModelList.get(position).getImg_url()).into(holder.popularimage);
        holder.name.setText(popularproductsModelList.get(position).getName());
        holder.rating_txt.setText(popularproductsModelList.get(position).getRating());
        holder.description.setText(popularproductsModelList.get(position).getDescription());
        holder.discount.setText(popularproductsModelList.get(position).getDiscount());
        holder.price.setText(popularproductsModelList.get(position).getPrice()+" ₹");
        String a = popularproductsModelList.get(holder.getAdapterPosition()).getType();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, viewallActivity.class);
                intent.putExtra("type", a);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularproductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popularimage;
        TextView name,description,rating_txt,discount,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularimage = itemView.findViewById(R.id.popularproductsimg);
            name = itemView.findViewById(R.id.popularproductsname);
            description = itemView.findViewById(R.id.popularproductsdesc);
            rating_txt = itemView.findViewById(R.id.popularproductsrating);
            discount = itemView.findViewById(R.id.popularproductsdiscount);
            price = itemView.findViewById(R.id.productprice);
        }
    }
}
