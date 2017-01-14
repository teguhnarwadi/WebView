package com.allsosmed.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.grabner.circleprogress.CircleProgressView;
import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private AdvancedWebView webView;
    private boolean exit = false;
    private CircleProgressView progressBar;
    private RelativeLayout rlWeb;
    private RelativeLayout rlMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        rlWeb = (RelativeLayout) findViewById(R.id.rl_webview);
        rlMessage = (RelativeLayout) findViewById(R.id.rl_reload);

        // get the Intent that started this Activity
        Intent in = getIntent();
        // get the Bundle that stores the data of this Activity
        Bundle b = in.getExtras();
        // getting data from bundle
        String url = b.getString("url");
        String code = b.getString("code");

        // initialize progress bar
        progressBar = (CircleProgressView) findViewById(R.id.progressBar1);

        // initialize web view with advance web view
        webView = (AdvancedWebView) findViewById(R.id.webView);
        webView.setListener(this, this);

        if (url != null) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://instagram.followergratis.co.id/masuk.php");
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        // this method to back to prev web view
        if (!webView.onBackPressed()) {
            return;
        }

        // this method to exit
        exitAppDouble();
    }

    private void exitAppDouble() {
        if (exit) {
            super.onBackPressed();
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        this.exit = true;
        Toast.makeText(this, "Press twice to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }

    private void exitApp() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        rlWeb.setVisibility(View.VISIBLE);
        rlMessage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.spin();
    }

    @Override
    public void onPageFinished(String url) {
        rlWeb.setVisibility(View.VISIBLE);
        rlMessage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.stopSpinning();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        rlWeb.setVisibility(View.INVISIBLE);
        rlMessage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.stopSpinning();

        ImageView ivReload = (ImageView) findViewById(R.id.iv_reload);
        TextView tvReload = (TextView) findViewById(R.id.tv_message);

        ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(webView.getUrl());
            }
        });

        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(webView.getUrl());
            }
        });

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    /**
     * This method for initialize menu in main activity
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method for listener on click, when item menu click
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_world) {
            webView.loadUrl(Helper.url_world);
            return true;
        }
        if (id == R.id.action_indo) {
            webView.loadUrl(Helper.url_indo);
            return true;
        }
        if (id == R.id.action_reload) {
            webView.loadUrl(webView.getUrl());
            return true;
        }
        if (id == R.id.action_exit) {
            // this method to exit
            exitApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
