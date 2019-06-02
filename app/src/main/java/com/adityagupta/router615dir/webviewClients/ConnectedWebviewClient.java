package com.adityagupta.router615dir.webviewClients;

import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;

public class ConnectedWebviewClient extends WebViewClient {
    public static final String LOGIN_URL = "http://192.168.0.1/login.htm";
    public static final String INDEX_URL = "http://192.168.0.1/index.htm";
    public static final String CONNECTEDDEVICES_URL = "http://192.168.0.1/dhcptbl.htm";

    private BaseWebViewClient tester;

    private boolean complete;

    private TaskDone taskDone;

    public ConnectedWebviewClient(TaskDone taskDone) {
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
                            tester.getWebView().loadUrl(CONNECTEDDEVICES_URL);
                        }
                    }, 100);
                } else if (CONNECTEDDEVICES_URL.equals(url)) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tester.getWebView().evaluateJavascript(
                                    "(function() {\n" +
                                            "    var table = document.getElementsByClassName('formlisting')[1];\n" +
                                            "    var arr = [];\n" +
                                            "    for (var i = 1; i < table.rows.length; i++) {\n" +
                                            "    \tvar ob = {\n" +
                                            "    \t\tname:table.rows[i].children[0].children[0].innerHTML,\n" +
                                            "    \t\tip:table.rows[i].children[1].children[0].innerHTML,\n" +
                                            "    \t\tmac:table.rows[i].children[2].children[0].innerHTML\n" +
                                            "    \t}\n" +
                                            "    \tarr.push(ob);\n" +
                                            "    }\n" +
                                            "    return arr;\n" +
                                            "})()",
                                    new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String s) {
                                            Log.e("OUTPUT",s);
                                            try {
                                                JSONArray jsonArray = new JSONArray(s);
                                                taskDone.onTaskComplete(jsonArray);
                                            } catch (JSONException e) {
                                                taskDone.onTaskFailed(new Exception("JSONArray Parse Exception"));
                                                e.printStackTrace();
                                            }
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
        public void onTaskComplete(JSONArray response);
        public void onTaskFailed(Exception e);
    }
}
