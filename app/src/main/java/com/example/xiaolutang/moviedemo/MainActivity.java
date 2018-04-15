package com.example.xiaolutang.moviedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.xiaolutang.moviedemo.util.WebPageUtil;

public class MainActivity extends BaseActivity {

    private WebView _mWebView;
    private WebViewClient _mWebClient;
    /**
     * 解析电影天堂页面
     * */
    WebPageUtil dyttPageUtil = new WebPageUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        initWebView();
        ladeMainPage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dyttPageUtil.getConnect("http://www.dytt8.net/");
                dyttPageUtil.getTitle();
            }
        }).start();
    }

    private void initWebView(){
        _mWebView = (WebView) findViewById(R.id.page_main_web_view);
        _mWebClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view,request);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }
        };
        _mWebView.setWebViewClient(_mWebClient);
    }
    private void ladeMainPage(){
        _mWebView.loadUrl("http://www.dytt8.net/");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i(TAG,"dispatchKeyEvent "+event);
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    if(_mWebView.canGoBack()){
                        _mWebView.goBack();
                    }
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
