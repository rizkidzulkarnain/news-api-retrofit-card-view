package com.tokopedia.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by dist-admin on 11/28/2017.
 */

public class WebActivity extends AppCompatActivity {
    String _Url_Param;
    String _Article_Param;
    WebView view;

    boolean loadingFinished = true;
    boolean redirect = false;

    private ProgressBar aprogressbar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        //activate back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get parameter from prev activity
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                _Url_Param = null;
                _Article_Param = null;
            } else {
                _Url_Param = extras.getString("url_param");
                _Article_Param = extras.getString("article_param");
            }
        } else {
            _Url_Param = (String) savedInstanceState.getSerializable("url_param");
            _Article_Param = (String) savedInstanceState.getSerializable("article_param");
        }

        aprogressbar=(ProgressBar)findViewById(R.id.progressBar);
        view = (WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(_Url_Param);

        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                aprogressbar.setVisibility(View.VISIBLE);
                loadingFinished = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!redirect){
                    loadingFinished = true;
                }

                if(loadingFinished && !redirect){
                    aprogressbar.setVisibility(View.GONE);
                } else{
                    redirect = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent aintent = new Intent();
                aintent.putExtra("param", _Article_Param);
                setResult(RESULT_OK, aintent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent aintent = new Intent();
        aintent.putExtra("param", _Article_Param);
        setResult(RESULT_OK, aintent);
        finish();
    }
}
