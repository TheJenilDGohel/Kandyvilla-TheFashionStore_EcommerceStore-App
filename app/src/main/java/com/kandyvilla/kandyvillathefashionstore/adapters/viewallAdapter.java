package com.kandyvilla.kandyvillathefashionstore.adapters;

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
import com.kandyvilla.kandyvillathefashionstore.activities.DetailedActivity;
import com.kandyvilla.kandyvillathefashionstore.models.viewallModel;

import java.util.List;

public class viewallAdapter extends RecyclerView.Adapter<viewallAdapter.ViewHolder> {
    Context context;
    List<viewallModel> viewallModelList;

    public viewallAdapter(Context context, List<viewallModel> viewallModelList) {
        this.context = context;
        this.viewallModelList = viewallModelList;
    }

    public void setfilteredList(List<viewallModel> filteredList){
        this.viewallModelList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewall_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(viewallModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(viewallModelList.get(position).getName());
        holder.description.setText(viewallModelList.get(position).getDescription());
        holder.discount.setText(viewallModelList.get(position).getDiscount());
        holder.price.setText(viewallModelList.get(position).getPrice()+"₹");
        holder.rating.setText(viewallModelList.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("details",viewallModelList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return viewallModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,description,discount,price,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.viewallproductsimg);
            name = itemView.findViewById(R.id.viewallproductsname);
            description = itemView.findViewById(R.id.viewallproductsdesc);
            discount = itemView.findViewById(R.id.viewallproductsdiscount);
            price = itemView.findViewById(R.id.viewallproductsprice);
            rating = itemView.findViewById(R.id.viewallproductsrating);

        }
    }
}
