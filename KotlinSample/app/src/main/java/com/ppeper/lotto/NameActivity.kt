package com.ppeper.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        findViewById<View>(R.id.goButton).setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }

        // 뒤로가기 버튼의 클릭이벤트 리스너 설정
        findViewById<View>(R.id.backButton).setOnClickListener {
            // 액티비티 종료
            finish()
        }
    }
}