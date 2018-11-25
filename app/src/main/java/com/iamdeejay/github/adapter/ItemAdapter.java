package com.iamdeejay.github.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iamdeejay.github.R;
import com.iamdeejay.github.controller.ActivityDetail;
import com.iamdeejay.github.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_user,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(itemList.get(i).getLogin());
        viewHolder.githubLink.setText(itemList.get(i).getHtmlUrl());

        //I want to load image here but Im not getting it
        Picasso.get()
                .load(itemList.get(i).getAvatarUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView githubLink;
        ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            githubLink = itemView.findViewById(R.id.githubLink);
            avatar = itemView.findViewById(R.id.cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Item clickedDataItem = itemList.get(pos);
                        Intent intent = new Intent(context, ActivityDetail.class);
                        intent.putExtra("login", itemList.get(pos).getLogin());
                        intent.putExtra("html_url", itemList.get(pos).getHtmlUrl());
                        intent.putExtra("avatar_url", itemList.get(pos).getAvatarUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked" + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
