package com.adityagupta.router615dir.webChromeClients;

import android.content.Context;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class BaseChromeClient extends WebChromeClient {

    Context context;

    public BaseChromeClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.e("StatusDeviceFragment", "onJsConfirm url=" + url + ";message=" + message);
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {
        Log.e("StatusDeviceFragment", "onJsAlert url=" + url + ";message=" + message);
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.e("StatusDeviceFragment", "onJSPrompt url=" + url + ";message=" + message);
        result.confirm();
        return true;
    }

}
