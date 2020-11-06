package com.zane.androidupnpdemo.uiInfraredcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
public class ControlPanelActivity extends Activity {

    private final String TAG = ControlPanelActivity.class.getSimpleName();

    //获取红外控制类
    private ConsumerIrManager IR;
    //判断是否有红外功能
    boolean IRBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        inItEvent();
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


    //初始化事务
    @SuppressLint("DefaultLocale")
    private void inItEvent() {
        //获取ConsumerIrManager实例
        IR = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

        //如果sdk版本大于4.4才进行是否有红外的功能（手机的android版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IRBack = IR.hasIrEmitter();
            if (!IRBack) {
                Toast.makeText(this, "对不起，该设备上没有红外功能!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "红外设备就绪", Toast.LENGTH_LONG).show();//可进行下一步操作
                StringBuilder b = new StringBuilder();
                // 获得可用的载波频率范围
                ConsumerIrManager.CarrierFrequencyRange[] freqs = IR.getCarrierFrequencies();
                b.append("IR Carrier Frequencies:\n");// 红外载波频率
                // 边里获取频率段
                for (ConsumerIrManager.CarrierFrequencyRange range : freqs) {
                    b.append(String.format("  %d - %d\n", range.getMinFrequency(), range.getMaxFrequency()));
                }
            }
        }
    }

    /**
     * 发射红外信号
     *
     * @param carrierFrequency 红外传输的频率，一般的遥控板都是38KHz
     * @param pattern          指以微秒为单位的红外开和关的交替时间
     */
    private void sendMsg(int carrierFrequency, int[] pattern) {
        IR.transmit(carrierFrequency, pattern);
    }

    /**
     * 点击开机
     *
     * @param v
     */
    public void offonClick(View v) {
        if (!IRBack) {
            return;
        }
        // 一种交替的载波序列模式，通过毫秒测量
        int[] pattern = {1901, 4453, 625, 1614, 625, 1588, 625, 1614, 625,
                442, 625, 442, 625, 468, 625, 442, 625, 494, 572, 1614,
                625, 1588, 625, 1614, 625, 494, 572, 442, 651, 442, 625,
                442, 625, 442, 625, 1614, 625, 1588, 651, 1588, 625, 442,
                625, 494, 598, 442, 625, 442, 625, 520, 572, 442, 625, 442,
                625, 442, 651, 1588, 625, 1614, 625, 1588, 625, 1614, 625,
                1588, 625, 48958};
        // 在38.4KHz条件下进行模式转换
        sendMsg(38000, pattern);
    }

    /**
     * 点击关机
     *
     * @param v
     */
    public void ononClick(View v) {

    }

    /**
     * 点击上
     *
     * @param v
     */
    public void onClicktop(View v) {

    }

    /**
     * 点击左
     *
     * @param v
     */
    public void onClickleft(View v) {

    }

    /**
     * 点击右
     *
     * @param v
     */
    public void onClickright(View v) {

    }

    /**
     * 点击下
     *
     * @param v
     */
    public void onClickboomt(View v) {

    }

}
