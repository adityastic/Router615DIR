package com.adityagupta.router615dir.fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.adityagupta.router615dir.webChromeClients.BaseChromeClient;
import com.adityagupta.router615dir.R;
import com.adityagupta.router615dir.adapter.ConnectedDevicesAdapter;
import com.adityagupta.router615dir.data.ConnectedDevicesData;
import com.adityagupta.router615dir.webviewClients.ConnectedWebviewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.adityagupta.router615dir.webviewClients.ConnectedWebviewClient.LOGIN_URL;

public class ConnectedDeviceFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefresh;
    RecyclerView mRecyclerView;
    ConnectedDevicesAdapter mAdapter;

    ArrayList<ConnectedDevicesData> list;

    WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefresh = view.findViewById(R.id.swipeRefresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        webView = view.findViewById(R.id.webView);

        // WEBVIEW CONFIGURATION
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSaveFormData(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

    private String getDevicesfromList(String present) {
        String output = null;
        try {
            JSONArray jsonArray = new JSONArray(getResources().getString(R.string.jsonOfMacs));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("mac").equals(present)) {
                    return jsonObject.getString("name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    private void refreshLayout() {
        list = new ArrayList<>();
        mAdapter = null;
        mRecyclerView.setAdapter(null);

        webView.stopLoading();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new ConnectedWebviewClient(new ConnectedWebviewClient.TaskDone() {
            @Override
            public void onTaskComplete(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);

                        String device = getDevicesfromList(object.getString("mac"));
                        if (device == null)
                            device = object.getString("name");

                        list.add(new ConnectedDevicesData(
                                device,
                                object.getString("mac"),
                                object.getString("ip")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new ConnectedDevicesAdapter(getContext(), list);
                mRecyclerView.setAdapter(mAdapter);
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onTaskFailed(Exception e) {
                mSwipeRefresh.setRefreshing(false);
            }
        }));
        webView.setWebChromeClient(new BaseChromeClient(getContext()));
        webView.loadUrl(LOGIN_URL);

    }

    public static Fragment newInstance() {
        return new ConnectedDeviceFragment();

    }
}