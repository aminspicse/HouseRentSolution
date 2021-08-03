package com.myspeec.firebaseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {


    private static final String Tag = "RecyclerView";
    Context context;
    List<ViewPostModel> viewPostModelList;

    public MyPostAdapter(Context context, List<ViewPostModel> viewPostModelList) {
        this.context = context;
        this.viewPostModelList = viewPostModelList;
    }

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypost_view_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.ViewHolder holder, int position) {

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

