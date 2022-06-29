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
import com.kandyvilla.kandyvillathefashionstore.activities.viewallActivity;
import com.kandyvilla.kandyvillathefashionstore.models.navcategoryModel;

import java.util.List;

public class navcategoryAdapter extends RecyclerView.Adapter<navcategoryAdapter.ViewHolder> {

    Context context;
    List<navcategoryModel> nav_categoryModelList;

    public navcategoryAdapter(Context context, List<navcategoryModel> nav_categoryModelList) {
        this.context = context;
        this.nav_categoryModelList = nav_categoryModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(nav_categoryModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(nav_categoryModelList.get(position).getName());
        holder.desc.setText(nav_categoryModelList.get(position).getDescription());
        String a = nav_categoryModelList.get(holder.getAdapterPosition()).getType();

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
        return nav_categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.nav_categoryimg);
            name = itemView.findViewById(R.id.nav_categoryname);
            desc = itemView.findViewById(R.id.nav_categorydesc);
        }
    }
}
