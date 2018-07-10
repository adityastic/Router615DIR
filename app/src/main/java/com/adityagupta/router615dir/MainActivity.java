package com.adityagupta.router615dir;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import static com.adityagupta.router615dir.TesterWebViewClient.INDEX_URL;
import static com.adityagupta.router615dir.TesterWebViewClient.LOGIN_URL;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    Button tv, gaming, clearall;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        tv = findViewById(R.id.tv_button);
        gaming = findViewById(R.id.gaming_button);
        output = findViewById(R.id.output);
        clearall = findViewById(R.id.clearall);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSaveFormData(false);

        tv.setSelected(true);
        tv.requestFocus();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Processing......");
                webView.stopLoading();
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new TesterWebViewClient(2, new TesterWebViewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("Lag Fixed");
                    }
                }));
                webView.loadUrl(LOGIN_URL);
            }
        });
        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Processing......");
                webView.stopLoading();
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new TesterWebViewClient(1, new TesterWebViewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("Lag Fixed");
                    }
                }));
                webView.loadUrl(LOGIN_URL);
            }
        });
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Processing......");
                webView.stopLoading();
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new TesterWebViewClient(3, new TesterWebViewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("ClearedAll");
                    }
                }));
                webView.loadUrl(LOGIN_URL);
            }
        });
    }
}
