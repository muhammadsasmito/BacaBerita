package com.project.sash.bacaberita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebBacaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_baca);

        Intent i = getIntent();
        FeedData feedValue = (FeedData) i.getSerializableExtra("data");

        TextView title = (TextView) findViewById(R.id.tvJudul);
        WebView webView = (WebView) findViewById(R.id.webView);

        title.setText(feedValue.feedTitle);
//        webView.loadData(feedValue.feedDesc,"text/html","UTF-8");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(feedValue.feedLink);
    }

}
