package com.kandyvilla.kandyvillathefashionstore.admin.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.admin.models.UserDetailsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {
    Context context;
    List<UserDetailsModel> userDetailsModelList;

    public UserDetailsAdapter(Context context, List<UserDetailsModel> userDetailsModelList) {
        this.context = context;
        this.userDetailsModelList = userDetailsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDetailsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(userDetailsModelList.get(position).getProfileImg() == null)
        {
            holder.circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.user_admin));
        }
        else {
            Glide.with(context).load(userDetailsModelList.get(position).getProfileImg()).into(holder.circleImageView);
        }
        holder.name.setText("Name : " + userDetailsModelList.get(position).getName());
        holder.email.setText("Email : " + userDetailsModelList.get(position).getEmail());
        holder.mobileno.setText("Number : " + userDetailsModelList.get(position).getMobileno());
        holder.address.setText("Adress : " + userDetailsModelList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return userDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView name,email,mobileno,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.user_img);
            name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);
            mobileno = itemView.findViewById(R.id.user_number);
            address = itemView.findViewById(R.id.user_address);
        }
    }
}
