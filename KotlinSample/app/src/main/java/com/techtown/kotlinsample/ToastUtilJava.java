package com.techtown.kotlinsample;

import android.widget.Toast;

public class ToastUtilJava {
    // 짧은 토스트 메세지를 보여주는 함수
    public static void toastShort(String message){
        Toast.makeText(MainApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }
    // 긴 토스트 메세지를 보여주는 함수
    public static void toastLong(String message){
        Toast.makeText(MainApplication.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
