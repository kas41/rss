package com.example.rss.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rss.R;

public class WebActivity extends AppCompatActivity{

    private final static String LINK = "link";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView) findViewById(R.id.activity_web_container);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        String url = getIntent().getStringExtra(LINK);
        mWebView.loadUrl(url);
    }

    public static Intent newInstance(Context context, String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(LINK, url);
        return intent;
    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
