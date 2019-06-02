package com.adityagupta.router615dir.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.adityagupta.router615dir.webChromeClients.BaseChromeClient;
import com.adityagupta.router615dir.R;
import com.adityagupta.router615dir.webviewClients.QOSWebviewClient;

import static com.adityagupta.router615dir.webviewClients.QOSWebviewClient.LOGIN_URL;


public class QOSFragment extends Fragment {

    private WebView webView;

    Button tv, gaming, clearall;
    TextView output;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = (WebView) view.findViewById(R.id.webView);
        tv = view.findViewById(R.id.tv_button);
        gaming = view.findViewById(R.id.gaming_button);
        output = view.findViewById(R.id.output);
        clearall = view.findViewById(R.id.clearall);

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
                webView.setWebViewClient(new QOSWebviewClient(2, new QOSWebviewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("Lag Fixed");
                    }

                    @Override
                    public void onTaskFailed() {

                    }
                },getContext()));
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
                webView.setWebViewClient(new QOSWebviewClient(1, new QOSWebviewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("Lag Fixed");
                    }

                    @Override
                    public void onTaskFailed() {

                    }
                },getContext()));
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
                webView.setWebViewClient(new QOSWebviewClient(3, new QOSWebviewClient.TaskDone() {
                    @Override
                    public void onTaskComplete() {
                        output.setText("ClearedAll");
                    }

                    @Override
                    public void onTaskFailed() {

                    }
                },getContext()));
                webView.loadUrl(LOGIN_URL);
            }
        });
        webView.setWebChromeClient(new BaseChromeClient(getContext()));
    }

    public static Fragment newInstance() {
        return new QOSFragment();

    }
}