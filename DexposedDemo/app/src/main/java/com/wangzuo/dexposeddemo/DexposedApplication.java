package com.wangzuo.dexposeddemo;

import android.app.Application;

import com.taobao.android.dexposed.DexposedBridge;

/**
 * Created by hasee on 2016/9/23.
 */

public class DexposedApplication extends Application{
    @Override
    public void onCreate() {

        super.onCreate();

        init();

    }

    /**
     * 初始化
     */
    private void init() {
        DexposedBridge.canDexposed(this);

        try {
            if (android.os.Build.VERSION.SDK_INT == 22){
                System.loadLibrary("dexposed_l51");
            } else if (android.os.Build.VERSION.SDK_INT > 19 && android.os.Build.VERSION.SDK_INT <= 21){
                System.loadLibrary("dexposed_l");
            } else if (android.os.Build.VERSION.SDK_INT > 14){
                System.loadLibrary("dexposed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
