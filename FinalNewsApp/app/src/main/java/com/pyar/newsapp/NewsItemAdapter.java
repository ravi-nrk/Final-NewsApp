package com.pyar.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RAVI on 30-Jun-17.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private static final int DATE_DIALOG_ID = 999;
    Context context;
    public View itemView;

    ArrayList<NewsItem> savedSearchTexts;
    ArrayList<String> checkedContacts = new ArrayList<String>();
    ArrayList<Integer> profileIcons;
    String Section;
    String viewTypes;
    ArrayList<String> profileEditSubTexts;
    String Mode;


    public NewsItemAdapter(Context context, ArrayList<NewsItem> saved_search_texts, String mode) {
        this.context = context;


        this.savedSearchTexts = saved_search_texts;
        // session = new SessionManager(context);

        this.Mode = mode;
        profileEditSubTexts = new ArrayList<>();

        //selections = new ArrayList<String>(MainActivity.hottestDealsArrayList.size());
    }
    //selections = new ArrayList<String>(MainActivity.hottestDealsArrayList.size());


    @Override
    public NewsItemAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_row_items, parent, false);
        return new NewsItemAdapter.NewsItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final NewsItemAdapter.NewsItemViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        String url=savedSearchTexts.get(position).getUrlToImage();

        //Loading images with PICASSO
        if (!TextUtils.isEmpty(url)){
            Picasso.with(context).load(url).into(holder.ivNewsImage);
        }
        holder.newsItemTitleTextView.setText(savedSearchTexts.get(position).getTitle());
        holder.newsItemDescription.setText(savedSearchTexts.get(position).getDescription());
        holder.newsItemPublishedAt.setText(savedSearchTexts.get(position).getPublishedAt());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context.getApplicationContext(), DisplayActivity.class);
                String url = savedSearchTexts.get(position).getUrl();
                Log.d("clicked_item_url",""+url);
                in.putExtra("Url",url);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });
    }


    @Override
    public int getItemCount() {
        return savedSearchTexts.size();
    }

    private String isCheckedOrNot(CheckBox checkbox) {
        if (checkbox.isChecked())

            return "is checked";

        else
            return "is not checked";
    }



    public static class NewsItemViewHolder extends RecyclerView.ViewHolder {
        public TextView newsItemTitleTextView,newsItemDescription,newsItemPublishedAt;
        public ImageView ivNewsImage;

        public NewsItemViewHolder(View v) {
            super(v);
            ivNewsImage= (ImageView) v.findViewById(R.id.image);
            newsItemTitleTextView=(TextView)v.findViewById(R.id.tv_news_item_title);
            newsItemDescription=(TextView)v.findViewById(R.id.tv_news_item_description);
            newsItemPublishedAt=(TextView)v.findViewById(R.id.tv_news_item_publish_time);
        }
    }
}