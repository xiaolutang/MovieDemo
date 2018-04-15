package com.example.xiaolutang.moviedemo;

import android.support.v7.app.AppCompatActivity;

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：${xiaoluTang}
 * date：2018/4/14
 * description：
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     *    String：适用于少量的字符串操作的情况
     *　　StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
     *　　StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况
     * */
    protected final String TAG = getClass().getSimpleName();
}
