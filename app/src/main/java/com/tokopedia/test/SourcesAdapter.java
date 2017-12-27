package com.tokopedia.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokopedia.test.model.Source;
import com.tokopedia.test.model.Source2;

import java.util.List;

//import com.blocksolid.retrofittutorial.retrofit.R;
//import com.blocksolid.retrofittutorial.model.NewsAnswer;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.NewsViewHolder> {

    private List<Source2> _Source;
    private int rowLayout;
    private Context context;


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout newsLayout;
        TextView anama_source;
        TextView adesc_source;
        TextView aurl_source;

        private final Context context;

        public NewsViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            newsLayout = (LinearLayout) v.findViewById(R.id.news_layout);
            anama_source = (TextView) v.findViewById(R.id.anamasource);
            adesc_source = (TextView) v.findViewById(R.id.desc_source);
            aurl_source = (TextView) v.findViewById(R.id.url_source);
        }
    }

    public SourcesAdapter(List<Source2> asource, int rowLayout, Context context) {
        this._Source = asource;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SourcesAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.anama_source.setText(_Source.get(position).getName());
        holder.adesc_source.setText(_Source.get(position).getDescription());
        holder.aurl_source.setText(_Source.get(position).getUrl());

        holder.newsLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent aintent = new Intent(v.getContext(), ArticleActivity.class);
                aintent.putExtra("param", _Source.get(position).getId());
                aintent.putExtra("sourcename", _Source.get(position).getName());
                v.getContext().startActivity(aintent);
            }
        });
   }

    @Override
    public int getItemCount() {
        return _Source.size();
    }
}
