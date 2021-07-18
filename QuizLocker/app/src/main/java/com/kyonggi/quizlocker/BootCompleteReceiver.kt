package com.kyonggi.quizlocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

// BroadcastReceiver 를 상속 받는다.
class BootCompleteReceiver : BroadcastReceiver() {
    // 브로드캐스트 메서지 수신시 불리는 컬백 함수
    override fun onReceive(context: Context?, intent: Intent?) {
        // 부팅이 완료될 때의 메세지인지 확인
        when {
            intent?.action == Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("quizlocker", "부팅이 완료됨")
                Toast.makeText(context, "퀴즈 잠금화면: 부팅이 완료됨", Toast.LENGTH_LONG).show()
            }
        }
    }

}