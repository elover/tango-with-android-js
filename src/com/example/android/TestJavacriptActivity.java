package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestJavacriptActivity extends Activity {
    private Button bt;
    private EditText et;
    private WebView wv;
    private String data = "xilou";
    Handler handler = new Handler() {};
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et = (EditText) this.findViewById(R.id.et_content);
        tv = (TextView) this.findViewById(R.id.tv_content);
        wv = (WebView) this.findViewById(R.id.webView_shownews);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        //在页面中可以通过window.androidJs调用RunJavaScript实例中的方法和成员变量
        wv.addJavascriptInterface(new RunJavaScript(), "androidJs");
        wv.loadUrl("file:///android_asset/html5.html");
        wv.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //直接将java里的成员变量赋值给页面里的函数调用
                view.loadUrl("javascript:setInfoFromAndroid('" + TestJavacriptActivity.this.data + "')");
            }
        });
        wv.setWebChromeClient(new WebChromeClient());
        bt = (Button) this.findViewById(R.id.bt_send);


        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 此处调用了来自于页面中的方法（getInfoFromAndroid）获取信息
                wv.loadUrl("javascript:getInfoFromAndroid('" + et.getText().toString() + "')");
            }
        });
    }
    class RunJavaScript {
        public void runJs(final String str) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // 处理具体的业务逻辑，例如：跳转到新的activity中，或者启动拨号，发短信程序都可以
                    tv.setText("message from html5：" + str);
                }
            });
        }
    }
}