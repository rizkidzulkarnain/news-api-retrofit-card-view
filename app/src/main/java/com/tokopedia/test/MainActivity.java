package com.tokopedia.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.tokopedia.test.api.ApiGenerator;
import com.tokopedia.test.api.ServiceGenerator;
import com.tokopedia.test.generalclass.config;
import com.tokopedia.test.generalclass.parameter;
import com.tokopedia.test.model.Article;
import com.tokopedia.test.model.News;
import com.tokopedia.test.api.NewsClient;
import com.tokopedia.test.model.SourceResponse;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    NewsClient newsClient;
    NewsClient sourceClient;
    List<String> _listSourceAll;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config._param = new parameter();
        sourceClient = ApiGenerator.createService(NewsClient.class);

        listNewsAPI();

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    public void listNewsAPI() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            c.setTime(dateFormat.parse(config._DateNow));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, 0); //minus 3 days
        String fromDate = dateFormat.format(c.getTime());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.news_rv);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Call<SourceResponse> call = sourceClient.getSource("en");
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Response<SourceResponse> response) {
                config._Sources = response.body();
                if (config._Sources != null) {
                    recyclerView.setAdapter(new SourcesAdapter(config._Sources.getSources(), R.layout.news_adapter, getApplicationContext()));
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

    public Set<String> getSources(List<Article> iArticles) {
        _listSourceAll = new ArrayList<String>();
        for (Article aArticle : iArticles) {
            _listSourceAll.add(aArticle.getSource().getName());
        }

        //select distinct
        Set<String> hashsetSource = new HashSet<String>(_listSourceAll);

        return hashsetSource;
    }
}
