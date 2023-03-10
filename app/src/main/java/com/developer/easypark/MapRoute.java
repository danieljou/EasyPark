package com.developer.easypark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MapRoute extends AppCompatActivity {
    WebView WebView;
    Button close_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);
        WebView = (WebView) findViewById(R.id.WebView);
        Bundle b = getIntent().getExtras();
        String url = b.getString("url");
        setTitle("Itinéraire");

        WebView.getSettings().setJavaScriptEnabled(true);
        WebView.getSettings().setUseWideViewPort(true);
        WebView.getSettings().setLoadWithOverviewMode(true);
        WebView.getSettings().setBuiltInZoomControls(true);
        WebView.getSettings().setSupportMultipleWindows(true);
        WebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WebView.setWebViewClient(new WebViewClient());

       /* WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                WebView.loadUrl(url);
                return true;
            }

            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });*/


        WebView.loadUrl(url);


    }

}