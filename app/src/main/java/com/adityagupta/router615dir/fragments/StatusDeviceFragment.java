package com.adityagupta.router615dir.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.adityagupta.router615dir.webChromeClients.BaseChromeClient;
import com.adityagupta.router615dir.R;
import com.adityagupta.router615dir.views.SquareImageView;
import com.adityagupta.router615dir.webviewClients.StatusWebviewClient;

import static com.adityagupta.router615dir.webviewClients.StatusWebviewClient.LOGIN_URL;

public class StatusDeviceFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefresh;
    private TextView title, subtitle;
    private SquareImageView image;

    private ChangeDrawerIcon changeDrawerIcon;

    private WebView webView;

    public StatusDeviceFragment() {
    }

    @SuppressLint("ValidFragment")
    private StatusDeviceFragment(ChangeDrawerIcon changeDrawerIcon) {
        this.changeDrawerIcon = changeDrawerIcon;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefresh = view.findViewById(R.id.swipeRefresh);
        webView = view.findViewById(R.id.webView);
        image = view.findViewById(R.id.noInternetSource);
        title = view.findViewById(R.id.noInternetTitle);
        subtitle = view.findViewById(R.id.noInternetSubTitle);

        // WEBVIEW CONFIGURATION
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSaveFormData(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        }, 300);
        refreshLayout();

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void noInternet() {
        Log.e("NoInternet","Called");
        image.setImageDrawable(getResources().getDrawable(R.drawable.emoji_status_dead));
        title.setText("Internet Not Available!!");
        subtitle.setText("There is some issue with your Internet Connection...");
    }

    @SuppressLint("SetTextI18n")
    private void gotInternet() {
        Log.e("GotInternet","Called");
        image.setImageDrawable(getResources().getDrawable(R.drawable.emoji_status_connected));
        title.setText("Internet Available!!");
        subtitle.setText("Internet Connection is Working....");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void refreshLayout() {

        image.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        subtitle.setVisibility(View.GONE);

        webView.stopLoading();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new StatusWebviewClient(new StatusWebviewClient.TaskDone() {
            @Override
            public void onTaskComplete(String ip) {
                Log.e("IP",ip);
                if (ip.equals("\"0.0.0.0\"") || ip.contains("192.168.")) {
                    noInternet();
                    changeDrawerIcon.changeIcon(false);
                } else {
                    gotInternet();
                    changeDrawerIcon.changeIcon(true);
                }
                mSwipeRefresh.setRefreshing(false);
                image.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                subtitle.setVisibility(View.VISIBLE);
            }
        }));
        webView.setWebChromeClient(new BaseChromeClient(getContext()));
        webView.loadUrl(LOGIN_URL);

    }

    public static Fragment newInstance(ChangeDrawerIcon changeDrawerIcon) {
        return new StatusDeviceFragment(changeDrawerIcon);

    }

    public interface ChangeDrawerIcon{
        void changeIcon(boolean yesorno);
    }
}