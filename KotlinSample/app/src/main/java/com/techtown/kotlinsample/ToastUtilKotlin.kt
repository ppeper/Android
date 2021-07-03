@file:JvmName("ToastUtil")
package com.techtown.kotlinsample

import android.widget.Toast

fun toastShort(message: String) {
    Toast.makeText(MainApplication.getAppContext(), message, Toast.LENGTH_SHORT).show()
}

fun toastLong(message: String) {
    Toast.makeText(MainApplication.getAppContext(), message, Toast.LENGTH_LONG).show()
}