package com.tokopedia.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokopedia.test.model.Article;
import com.squareup.picasso.Picasso;
import com.tokopedia.test.model.Article2;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> implements Filterable {

    private List<Article2> _Article;
    private List<Article2> _FilteredArticle;

    private String _Param_Article;
    private int rowLayout;
    private Context context;

    public ArticleAdapter(List<Article2> articles, int rowLayout, Context context, String iparam) {
        this._Param_Article = iparam;
        this._Article = articles;
        this._FilteredArticle = articles;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_adapter, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder holder, final int position) {
        holder.atitle.setText(_FilteredArticle.get(position).getTitle());
        holder.aauthor.setText(_FilteredArticle.get(position).getAuthor());
        holder.atitle.setText(_FilteredArticle.get(position).getTitle());
        String[] adatepost = _FilteredArticle.get(position).getPublishedAt().split("T");
        holder.adatepost.setText(adatepost[0] + " " + adatepost[1].replace("Z",""));
        Picasso.with(context).load(_FilteredArticle.get(position).getImageUrl()).resize(120, 80).into(holder.aimage);
        holder.acardview.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent aintent = new Intent(v.getContext(), WebActivity.class);
                Bundle extras = new Bundle();
                extras.putString("url_param",_Article.get(position).getUrl());
                extras.putString("article_param",_Param_Article);
                aintent.putExtras(extras);
                ((Activity)v.getContext()).startActivityForResult(aintent, 1);
//                v.getContext().startActivity(aintent);
            }
        });
   }

    @Override
    public int getItemCount() {
        return _FilteredArticle.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    _FilteredArticle = _Article;
                } else {
                    List<Article2> filteredList = new ArrayList<Article2>();
                    for (Article2 article : _Article) {
                        //search by title
                        if (article.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(article);
                        }
                    }
                    _FilteredArticle = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = _FilteredArticle;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _FilteredArticle = (List<Article2>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        LinearLayout articleLayout;
        TextView atitle;
        TextView aauthor;
        TextView adatepost;
        ImageView aimage;
        CardView acardview;

        public ArticleViewHolder(View v) {
            super(v);
            articleLayout = (LinearLayout) v.findViewById(R.id.article_layout);
            atitle = (TextView) v.findViewById(R.id.title);
            adatepost = (TextView) v.findViewById(R.id.datepost);
            aauthor = (TextView) v.findViewById(R.id.author);
            aimage = (ImageView) v.findViewById(R.id.img_android);
            acardview = (CardView) v.findViewById(R.id.article_cv);
        }
    }
}
