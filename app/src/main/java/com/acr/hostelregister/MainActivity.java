
package com.acr.hostelregister;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {

    private WebView webView;

    private static final String WEBSITE_URL =
            "https://hostel2943-tech.github.io/ACR-email-update/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {

                return handleUrl(request.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {

                return handleUrl(Uri.parse(url));
            }

            private boolean handleUrl(Uri uri) {
                String host = uri.getHost();

                if (host != null &&
                    (host.equals("hostel2943-tech.github.io") ||
                     host.equals("supabase.co") ||
                     host.endsWith(".supabase.co"))) {
                    return false;
                }

                try {
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    // No compatible application available
                }

                return true;
            }
        });

        setContentView(webView);

        if (savedInstanceState == null) {
            webView.loadUrl(WEBSITE_URL);
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
