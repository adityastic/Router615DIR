package com.adityagupta.router615dir.webviewClients;

import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StatusWebviewClient extends WebViewClient {
    private static final String TAG = "StatusWebviewClient";

    public static final String LOGIN_URL = "http://192.168.0.1/login.htm";
    public static final String INDEX_URL = "http://192.168.0.1/index.htm";
    public static final String STATUS_URL = "http://192.168.0.1/status.htm";

    private BaseWebViewClient tester;

    private boolean complete;

    private TaskDone taskDone;

    public StatusWebviewClient(TaskDone taskDone) {
        tester = new BaseWebViewClient();
        this.complete = false;
        this.taskDone = taskDone;
    }

    @Override
    public void onPageFinished(final WebView view, String url) {
        tester.init(view);
        if (LOGIN_URL.equals(url)) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tester.clickOnItemWithId("loginBtn");
                }
            }, 100);
        } else {
            if (!complete) {
                if (INDEX_URL.equals(url)) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tester.getWebView().loadUrl(STATUS_URL);
                        }
                    }, 100);
                } else if (STATUS_URL.equals(url)) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tester.getWebView().evaluateJavascript(
                                    "(function() { return document.querySelector('#body_header > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td:nth-child(3) > b').innerText; })();",
                                    new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String s) {
                                            Log.e("OUTPUT", s);
                                            taskDone.onTaskComplete(s);
                                        }
                                    });
                        }
                    }, 100);
                    complete = true;
                }
            }
        }
    }

    public interface TaskDone {
        void onTaskComplete(String ip);
    }
}
