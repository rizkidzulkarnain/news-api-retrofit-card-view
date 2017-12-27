package com.tokopedia.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tokopedia.test.model.Article;
import com.tokopedia.test.model.Source;

import java.util.ArrayList;
import java.util.List;

//import com.blocksolid.retrofittutorial.retrofit.R;
//import com.blocksolid.retrofittutorial.model.NewsAnswer;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<String> _Source;
    private int rowLayout;
    private Context context;


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout newsLayout;
        TextView anama_source;
        private final Context context;

        public NewsViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            newsLayout = (LinearLayout) v.findViewById(R.id.news_layout);
            anama_source = (TextView) v.findViewById(R.id.anamasource);
        }
    }

    public NewsAdapter(List<String> asource, int rowLayout, Context context) {
        this._Source = asource;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.anama_source.setText(_Source.get(position));
        holder.newsLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent aintent = new Intent(v.getContext(), ArticleActivity.class);
                aintent.putExtra("param", _Source.get(position));
                v.getContext().startActivity(aintent);
            }
        });
   }

    @Override
    public int getItemCount() {
        return _Source.size();
    }
}
