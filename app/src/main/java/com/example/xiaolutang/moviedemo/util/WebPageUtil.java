package com.example.xiaolutang.moviedemo.util;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：${xiaoluTang}
 * date：2018/4/14
 * description：解析电影天堂的html数据
 */
// FIXME: 2018/4/14 应该考虑和jsoup解耦，如果某一天要更换这个html解析框架该怎么处理呢
public class WebPageUtil {
    protected final String TAG = getClass().getSimpleName();


    protected Connection _mConnect;
    protected Document _mDocument;
    
    public void getTitle(){
        Log.i(TAG,"getTitle");
        Elements body =  _mDocument.getElementsByTag("body");
        Element head = body.get(0);
        Elements titles = head.getElementsByClass("contain");
        for (Element title: titles){
            Log.i(TAG,"getTitle "+title.wholeText());
        }
    }
    
    /**
     * @param url 网络请求地址
     * */
    public Connection getConnect(String url){
        Log.i(TAG,"getConnect url is "+url);
        try {
            _mConnect = Jsoup.connect(url);
            _mDocument = _mConnect.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _mConnect;
    }
}
