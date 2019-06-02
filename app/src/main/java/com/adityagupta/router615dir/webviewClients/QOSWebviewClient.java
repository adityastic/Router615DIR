package com.adityagupta.router615dir.webviewClients;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QOSWebviewClient extends WebViewClient {

    public static final String LOGIN_URL = "http://192.168.0.1/login.htm";
    public static final String INDEX_URL = "http://192.168.0.1/index.htm";
//    public static final String WLAN_BASIC_URL = "http://192.168.0.1/wlan_basic.htm";
    public static final String TRAFFIC_CONTROL_URL = "http://192.168.0.1/ipqostc_gen_ap.htm";

    public static final String Predefined_FUNC = "(function() { " +
            "try {" +
            "%s" +
            "}catch(error)" +
            "{" +
            "return error.message;" +
            "}" +
            "return 'Success'})();";

    private static final int PLAYING_RULE = 1;
    private static final int TV_RULE = 2;
    private static final int CLEAR_ALL = 3;

    private int selected = 0;
    private boolean complete;
    private int loadtime;

    private TaskDone taskDone;

    private BaseWebViewClient tester;
    private Context context;

    public QOSWebviewClient(int selected, TaskDone taskDone,Context context) {
        tester = new BaseWebViewClient();
        this.selected = selected;
        this.complete = false;
        this.loadtime = 0;
        this.taskDone = taskDone;
        this.context = context;
    }
    SharedPreferences shared;
    @Override
    public void onPageFinished(final WebView view, String url) {
        tester.init(view);
        Log.e("URL", url);

        shared = PreferenceManager.getDefaultSharedPreferences(context);

        if (LOGIN_URL.equals(url)) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tester.clickOnItemWithId("loginBtn");
                }
            }, 100);
        } else {
            if(!complete)
            {
                switch (selected) {
                    case PLAYING_RULE:
                        if (INDEX_URL.equals(url)) {
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadtrafficpage();
                                }
                            }, 100);
                        } else if (TRAFFIC_CONTROL_URL.equals(url)) {
                            loadtime++;
                            Log.e("loadtime",loadtime+"");
                            if(loadtime==1)
                                checkifruleexists(view, new onCheckRuleExists() {
                                    @Override
                                    public void yes() {
                                        setTrafficRuleto64(view);
                                    }
                                });
                            else
                                setTrafficRuleto64(view);
                        }
                        break;
                    case TV_RULE:
                        if (INDEX_URL.equals(url)) {
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadtrafficpage();
                                }
                            }, 100);
                        } else if (TRAFFIC_CONTROL_URL.equals(url)) {
                            loadtime++;
                            Log.e("loadtime",loadtime+"");
                            if(loadtime == 1)
                                checkifruleexists(view, new onCheckRuleExists() {
                                    @Override
                                    public void yes() {
                                        setTrafficRuleto32(view);
                                    }
                                });
                            else
                                setTrafficRuleto32(view);
                        }
                        break;
                    case CLEAR_ALL:
                        if (INDEX_URL.equals(url)) {
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadtrafficpage();
                                }
                            }, 100);
                        } else if (TRAFFIC_CONTROL_URL.equals(url)) {
                            checkifruleexists(view);
                            complete = true;
                            taskDone.onTaskComplete();
                        }
                        break;
                }
            }
        }
    }

    private void loadtrafficpage() {
        tester.getWebView().loadUrl(TRAFFIC_CONTROL_URL);
    }

    private void setTrafficRuleto64(WebView view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tester.clickOnItemWithClassName("button");
            }
        }, 100);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                complete = true;
                tester.setValueForItemWithName("srcip", "192.168.0.0");
                tester.setValueForItemWithName("srcnetmask", "255.255.255.192");
//                tester.setValueForItemWithName("srcnetmask", "192.168.0.64");
                tester.setValueForItemWithName("dstip", "0.0.0.0");
                tester.setValueForItemWithName("dstnetmask", "0.0.0.0");
                tester.setValueForItemWithName("uprateFloor", "1");
                tester.setValueForItemWithName("uprateCeiling", shared.getString("mbup","0"));
                tester.setValueForItemWithName("downrateFloor", "1");
                tester.setValueForItemWithName("downrateCeiling", shared.getString("mbdown","0"));
                tester.clickOnItemWithName("addRule");
                taskDone.onTaskComplete();
            }
        }, 100);
    }

    private void setTrafficRuleto32(WebView view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tester.clickOnItemWithClassName("button");
            }
        }, 100);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                complete = true;
                tester.setValueForItemWithName("srcip", "192.168.0.0");
                tester.setValueForItemWithName("srcnetmask", "255.255.255.224");
                tester.setValueForItemWithName("dstip", "0.0.0.0");
                tester.setValueForItemWithName("dstnetmask", "0.0.0.0");
                tester.setValueForItemWithName("uprateFloor", "1");
                tester.setValueForItemWithName("uprateCeiling", shared.getString("tvup","0"));
                tester.setValueForItemWithName("downrateFloor", "1");
                tester.setValueForItemWithName("downrateCeiling", shared.getString("tvdown","0"));
                tester.clickOnItemWithName("addRule");
                taskDone.onTaskComplete();
            }
        }, 100);
    }

    private void loadJQuery(WebView view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tester.getWebView().evaluateJavascript("(function() { " +
                        "try {\n" +
                        "var body = document.getElementsByTagName(\"body\")[0];\n" +
                        "var script = document.createElement('script');\n" +
                        "script.type =\"text/javascript\";\n" +
                        "script.src = \"https://code.jquery.com/jquery-1.11.3.min.js\";\n" +
                        "return body.appendChild(script);\n" +
                        "}\n" +
                        "catch(error) {\n" +
                        "  return error.message;\n" +
                        "  // expected output: SyntaxError: unterminated string literal\n" +
                        "  // Note - error messages will vary depending on browser\n" +
                        "}" +
                        "return 'success'; })();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("JQuery Result", s);
                    }
                });
            }
        }, 700);
    }

    public void checkifruleexists(final WebView view, final onCheckRuleExists onCheckRuleExists) {
//        loadJQuery(view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tester.getWebView().evaluateJavascript(
                        String.format(Predefined_FUNC,
                                "return document.getElementById(\"qosTable\").rows.length"), new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("Lenght", s);
                                if (Integer.parseInt((s.equals("null")) ? "0" : s) > 2) {
                                    tester.checkItemWithName("removeQ", true);
                                    tester.clickOnItemWithName("save");
                                }else
                                    onCheckRuleExists.yes();
                            }
                        });
            }
        }, 100);
    }

    public void checkifruleexists(final WebView view) {
//        loadJQuery(view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tester.getWebView().evaluateJavascript(
                        String.format(Predefined_FUNC,
                                "return document.getElementById(\"qosTable\").rows.length"), new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("Lenght", s);
                                if (Integer.parseInt((s.equals("null")) ? "0" : s) > 2) {
                                    tester.checkItemWithName("removeQ", true);
                                    tester.clickOnItemWithName("save");
                                }
                            }
                        });
            }
        }, 100);
    }

    public interface TaskDone
    {
        void onTaskComplete();
        void onTaskFailed();
    }
    public interface onCheckRuleExists
    {
        void yes();
    }
}
