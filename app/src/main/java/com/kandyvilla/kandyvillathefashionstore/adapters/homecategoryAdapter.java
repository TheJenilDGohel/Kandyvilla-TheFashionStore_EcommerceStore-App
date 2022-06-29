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
import com.kandyvilla.kandyvillathefashionstore.models.homecategoryModel;

import java.util.List;

public class homecategoryAdapter extends RecyclerView.Adapter<homecategoryAdapter.ViewHolder> {

    Context context;
    List<homecategoryModel> homecategoryModelList;

    public homecategoryAdapter(Context context, List<homecategoryModel> homecategoryModelList) {
        this.context = context;
        this.homecategoryModelList = homecategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.homecategory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(homecategoryModelList.get(position).getImg_url()).into(holder.homecategoryImg);
        holder.homecategoryname.setText(homecategoryModelList.get(position).getName());
        String a = homecategoryModelList.get(holder.getAdapterPosition()).getType();

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
        return homecategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homecategoryImg;
        TextView homecategoryname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homecategoryImg = itemView.findViewById(R.id.homecategoryimg);
            homecategoryname = itemView.findViewById(R.id.homecategoryname);
        }
    }
}
