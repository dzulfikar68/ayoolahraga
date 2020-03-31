package com.digitcreativestudio.ayoolahraga.main.blog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.model.Blog;

public class DetailBlogActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT_BLOG";
    private Blog blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blog);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Blog View");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            blog = bundle.getParcelable(DetailBlogActivity.EXTRA_INTENT);
            loadWebView();
        }
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void loadWebView(){
        final ProgressBar pgBlogView = findViewById(R.id.pb_blog_view);
        pgBlogView.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(() -> pgBlogView.setVisibility(View.GONE), 3000);

        WebView webView = findViewById(R.id.wv_blog);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String request) {
//                    view.loadUrl(request);
//                    return super.shouldOverrideUrlLoading(view, request);
//                }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                pgBlogView.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(blog.getLink());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
