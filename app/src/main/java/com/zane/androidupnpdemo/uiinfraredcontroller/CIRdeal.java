package com.zane.androidupnpdemo.uiinfraredcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020-11-09 no 16:28
 * @USER : vvguoliang
 * @File : CIRdeal.java
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
 * 200700480
 * Model:PS-YK12
 * 433.92MHz
 */
@SuppressLint("WrongConstant")
public class CIRdeal {

    // Android 4.4之后 红外遥控ConsumerIrManager
    private static ConsumerIrManager mCIR;
    //引导码
    private static int startH = 9000;
    private static int startL = 4500;

    private static int LOOP_H = 9000;
    private static int LOOP_L = 4500;

    private static int high8 = 560;
    //0：1125
    private static int low0 = 565;
    //1：2250
    private static int low1 = 1690;

    private static int GAP = 2300;

    //用户编码高八位80
    private static String userH = "00000001";
    //用户编码低八位18
    private static String userL = "10001000";

    //433.92MHz
//    private static int carrierFrequency = 56000;
    //38kHz
    private static int carrierFrequency = 38000;
    private static int[] pattern;

    private static List<Integer> list = new ArrayList<>();

    /**
     * 正常发码：引导码（9ms+4.5ms）+用户编码（低八位）+用户编码（高八位）+键数据码+键数据反码
     */
    public static void transmitKey(Context context, String key, String key2, String key3, boolean longPress) {
        if (!checkCIR(context, key3)) {
            return;
        }
        if (!mCIR.hasIrEmitter()) {
            Toast.makeText(context, "未找到红外发射器！", Toast.LENGTH_LONG).show();
        } else {
            list.clear();
            //引导码
            list.add(startH);
            list.add(startL);
            //用户编码
            change(userH);
            change(userL);
            //键数据码
            change(key);
            //键数据反码
            change(key2);
            //发射时数据少一位？？？
            change("1");

            //延时码
            list.add(600);
            list.add(GAP);

            list.add(LOOP_H);
            list.add(LOOP_L);

            //如果长按则添加重复码
            if (longPress) {
                for (int i = 0; i < 4; i++) {
                    list.add(650);
                    list.add(96000);
                    list.add(9600);
                    list.add(2250);
                }
            }


            int size = list.size();
            pattern = new int[size];
            for (int i = 0; i < size; i++) {
                pattern[i] = list.get(i);
            }
            mCIR.transmit(carrierFrequency, pattern);
        }
    }

    /**
     * 二进制转成电平
     *
     * @param code
     */
    public static void change(String code) {
        int len = code.length();
        String part;
        for (int i = 0; i < len; i++) {
            list.add(high8);
            part = code.substring(i, i + 1);
            if (part.equals("0"))
                list.add(low0);
            else
                list.add(low1);
        }
    }

    /**
     * 检测手机是否有红外功能
     */
    @SuppressLint("DefaultLocale")
    private static boolean checkCIR(Context context, String key) {
        if (null == mCIR) {
            // 获取系统的红外遥控服务
            mCIR = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        }

        if (!mCIR.hasIrEmitter()) {
            Toast.makeText(context, "未找到红外发射器！:=" + key, Toast.LENGTH_LONG).show();
            return false;
        } else {
            StringBuilder b = new StringBuilder();
            // 获得可用的载波频率范围
            ConsumerIrManager.CarrierFrequencyRange[] freqs = mCIR.getCarrierFrequencies();
            b.append("IR Carrier Frequencies:\n");// 红外载波频率
            // 边里获取频率段
            for (ConsumerIrManager.CarrierFrequencyRange range : freqs) {
                b.append(String.format("  %d - %d\n",
                        range.getMinFrequency(), range.getMaxFrequency()));
            }
            Toast.makeText(context, b.toString() + ":存在红外发射器:=" + key, Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    private static Method transmit = null;
    private static Object ConsumerIrManager = null;
    public static int hz = 38000;
    private static final int CODE1 = 13;
    private static final int CODE2 = 32;
    private static final int CODE3 = 72;
    private static final int CODE4 = 1800;
    private static final int CODE5 = 1650;
    public static int[] pattern1 = {CODE1, CODE3, CODE1, CODE2, CODE1, CODE2,
            CODE1, CODE2, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2, CODE1,
            CODE3, CODE1, CODE2, CODE1, CODE3, CODE1, CODE2, CODE1, CODE2,
            CODE1, CODE2, CODE1, CODE3, CODE1, CODE2, CODE1, CODE4, CODE1,
            CODE3, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2,
            CODE1, CODE3, CODE1, CODE3, CODE1, CODE2, CODE1, CODE3, CODE1,
            CODE2, CODE1, CODE3, CODE1, CODE3, CODE1, CODE3, CODE1, CODE2,
            CODE1, CODE3, CODE1, CODE5, CODE1, CODE3, CODE1, CODE2, CODE1,
            CODE2, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2,
            CODE1, CODE3, CODE1, CODE2, CODE1, CODE3, CODE1, CODE2, CODE1,
            CODE2, CODE1, CODE2, CODE1, CODE3, CODE1, CODE2, CODE1, CODE4,
            CODE1, CODE3, CODE1, CODE2, CODE1, CODE2, CODE1, CODE2, CODE1,
            CODE2, CODE1, CODE3, CODE1, CODE3, CODE1, CODE2, CODE1, CODE3,
            CODE1, CODE2, CODE1, CODE3, CODE1, CODE3, CODE1, CODE3, CODE1,
            CODE2, CODE1, CODE3, CODE1};

    public static void sendIRCode(Context context, String key, int paramInt, int[] paramArrayOfInt) {
        checkCIR(context, key);
        try {
            if (transmit == null) {
                ConsumerIrManager = context.getSystemService("consumer_ir");
                Class<?> clazz = ConsumerIrManager.getClass();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().contains("transmit")) {
                        transmit = method;
                        break;
                    }
                }
            }
            transmit.invoke(ConsumerIrManager, paramInt, paramArrayOfInt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
