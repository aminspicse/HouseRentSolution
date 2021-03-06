package com.myspeec.firebaseproject;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    Context context;
    List<ViewPostModel> viewPostModelList;

    public RecyclerAdapter(Context context, List<ViewPostModel> viewPostModelList) {
        this.context = context;
        this.viewPostModelList = viewPostModelList;
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ViewPostModel viewPostModel = viewPostModelList.get(position);
/*
        String posted, sub;
        Helper hp = new Helper();
        posted = viewPostModel.getEmail();
        sub = posted.substring(0, hp.subString(posted));

 */
        holder.title.setText(viewPostModel.getImageName()+" | "+viewPostModel.getLocation());
        holder.description.setText(viewPostModel.getDescription());
        holder.rentAmount.setText("Rent: "+viewPostModel.getRentAmount());
        //holder.location.setText("Location: "+viewPostModel.getLocation());
        holder.postby.setText("Post By: "+ viewPostModel.getEmail());
        holder.postTime.setText("Time: "+viewPostModel.getPostTime());
        holder.mobile.setText("Mobile: "+viewPostModel.getMobile());
        String imageUri= null;
        imageUri = viewPostModel.getImageUrl();
        Picasso.get().load(imageUri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return viewPostModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // hear declare design
        ImageView imageView;
        TextView title, imageurl, description, rentAmount,location, postby, postTime, mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);//just test
            rentAmount = itemView.findViewById(R.id.rentAmount);
//            location = itemView.findViewById(R.id.location);
            postby = itemView.findViewById(R.id.postedby);
            postTime = itemView.findViewById(R.id.postedTime);
            mobile = itemView.findViewById(R.id.mobile);

        }
    }
}
