package com.zane.androidupnpdemo.uiinfraredcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zane.androidupnpdemo.R;

/**
 * @Time : 2020-11-05 no 15:41
 * @USER : vvguoliang
 * @File : ControlPanelActivity.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃   ☃   ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 * <p>
 * 控制面板 控制电视，空调，开关，红外线控制 NEC编码协议
 */
public class ControlPanelActivity extends Activity {//implements View.OnLongClickListener {

//    private final String TAG = ControlPanelActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
//        findViewById(R.id.offbutton).setOnLongClickListener(this);
//        findViewById(R.id.onbutton).setOnLongClickListener(this);
//        findViewById(R.id.topbutton).setOnLongClickListener(this);
//        findViewById(R.id.buttonleft).setOnLongClickListener(this);
//        findViewById(R.id.buttonright).setOnLongClickListener(this);
//        findViewById(R.id.buttonboomt).setOnLongClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 点击开机
     *
     * @param v
     */
    public void offonClick(View v) {
        if (offon) {
            offon = false;
            isOffon = 0;
        } else {
            offon = true;
            isOffon = 1;
        }
        mane = "开";
        mHandler.postDelayed(runnable, 1000L);
    }


    /**
     * 点击关机
     *
     * @param v
     */
    public void ononClick(View v) {
        if (offon) {
            offon = false;
            isOffon = 0;
        } else {
            offon = true;
            isOffon = 1;
        }
        mane = "关";
        mHandler.postDelayed(runnable, 1000L);
    }

    /**
     * 点击上
     *
     * @param v
     */
    public void onClicktop(View v) {
        if (offon) {
            offon = false;
            isOffon = 0;
        } else {
            offon = true;
            isOffon = 2;
        }
        mane = "上";
        mHandler.postDelayed(runnable, 1000L);
    }

    /**
     * 点击左
     *
     * @param v
     */
    public void onClickleft(View v) {
        if (offon) {
            offon = false;
            isOffon = 0;
        } else {
            offon = true;
            isOffon = 2;
        }
        mane = "左";
        mHandler.postDelayed(runnable, 1000L);
    }

    /**
     * 点击右
     *
     * @param v
     */
    public void onClickright(View v) {
        CIRdeal.sendIRCode(ControlPanelActivity.this, "右", CIRdeal.hz, CIRdeal.pattern1);
    }

    /**
     * 点击下
     *
     * @param v
     */
    public void onClickboomt(View v) {
        CIRdeal.sendIRCode(ControlPanelActivity.this, "下", CIRdeal.hz, CIRdeal.pattern1);
    }

//    @Override
//    public boolean onLongClick(View v) {
//        switch (v.getId()) {
//            case R.id.offbutton:
//            case R.id.onbutton:
//            case R.id.topbutton:
//            case R.id.buttonleft:
//            case R.id.buttonright:
//            case R.id.buttonboomt:
//                if (offon) {
//                    offon = false;
//                    isOffon = 0;
//                } else {
//                    offon = true;
//                    isOffon = 1;
//                }
//                mHandler.postDelayed(runnable, 1000L);
//                return true;
//        }
//        return false;
//    }

    private boolean offon = false;
    private int isOffon = 0;
    private String mane = "";
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (offon) {
                if (isOffon == 1) {
                    CIRdeal.transmitKey(ControlPanelActivity.this, "00001000", "11110111", mane, true);
                    mHandler.postDelayed(runnable, 2 * 1000L);
                } else if (isOffon == 2) {
                    CIRdeal.transmitKey(ControlPanelActivity.this, "00001000", "11110111", mane, false);
                    mHandler.postDelayed(runnable, 2 * 1000L);
                }
            }
        }
    };
}
