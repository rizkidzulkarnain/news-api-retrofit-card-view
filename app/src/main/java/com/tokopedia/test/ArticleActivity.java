package com.tokopedia.test;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tokopedia.test.api.ApiGenerator;
import com.tokopedia.test.api.NewsClient;
import com.tokopedia.test.generalclass.config;
import com.tokopedia.test.generalclass.parameter;
import com.tokopedia.test.model.Article;
import com.tokopedia.test.model.Article2;
import com.tokopedia.test.model.ArticleResponse;
import com.tokopedia.test.model.SourceResponse;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dist-admin on 11/28/2017.
 */

public class ArticleActivity extends AppCompatActivity {
    String _Param;
    List<Article2> _Article;
    private ArticleAdapter mAdapter;

    NewsClient sourceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //activate back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sourceClient = ApiGenerator.createService(NewsClient.class);

        //get parameter from prev activity
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                _Param = null;
                config._param.set_asourceid(_Param);
            } else {
                _Param = extras.getString("param");
                config._param.set_asourceid(_Param);
                config._param.setAsourcename(extras.getString("sourcename"));
            }
        }
        listArticleAPI(config._param.get_asourceid());
        if(config._param.getAsourcename() == ""){
            config._param.setAsourcename("List of Articles");
        }
        getSupportActionBar().setTitle(config._param.getAsourcename());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    public void listArticleAPI(String isourceid) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Call<ArticleResponse> call = sourceClient.getArticles2(config._ApiKey, isourceid, "top");
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Response<ArticleResponse> response) {
                config._Articles = response.body();
                if (config._Articles != null) {
                    mAdapter = new ArticleAdapter(config._Articles.getArticles(), R.layout.article_adapter, getApplicationContext(), config._param.get_asourceid());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.main_error_text),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
