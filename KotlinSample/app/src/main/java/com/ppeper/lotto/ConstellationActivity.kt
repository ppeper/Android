package com.ppeper.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ConstellationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)

        findViewById<View>(R.id.goResultButton).setOnClickListener {
            // ResultActivity 를 시작하는 Intent 생성
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}