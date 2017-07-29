package com.pyar.newsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by RAVI on 30-Jun-17.
 */

public class DisplayActivity extends Activity {
    ProgressBar progressBar;
    WebView mWebView;
    Dialog dialog;
    Bundle bundle;
    String selected_item_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.webview1);
        bundle = getIntent().getExtras();
        selected_item_url = getIntent().getExtras().getString("Url", "");
        Log.d("inside_url", "" + selected_item_url);
        callWebView(selected_item_url);


    }

    private void callWebView(String url) {


        mWebView.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);


        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Log.d("onReceivedError", "--->");
                showDialog();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("onPageStarted", "--->");
                progressBar.setVisibility(View.VISIBLE);

            }


            @Override
            public void onPageFinished(WebView view, String url) {
                boolean status = checkInternetConnection();
                Log.d("onPageFinished", "--->" + status);
                progressBar.setVisibility(View.GONE);

            }

        });



        mWebView.loadUrl(url);

    }

    private void showDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Network error")
                .setMessage("you don't have internet connection")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean checkInternetConnection() {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                return true;
            } else {

                Toast.makeText(this, " Not Connected,Internet connection required ", Toast.LENGTH_LONG).show();

            }

        }
        // get Connectivity Manager object to check connection
      /*  ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Not Connected,Internet connection required ", Toast.LENGTH_LONG).show();
            return false;
        }*/
        return false;

    }
}
