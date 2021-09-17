package com.kyonggi.punchpower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyonggi.punchpower.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    // 펀치력을 저장하는 변수. 사용하는 시점에 초기화되도록 lazy
    // 전달받은 값에 100 을 곱하는 이유는 가속도 측정값이 소숫점 담위의 차이므로 눈에 띄지 않기 때문
    val power by lazy {
        intent.getDoubleExtra("power", 0.0) * 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.scoreLabel.text = "${String.format("%.0f", power)} 점"

        binding.restartButton.setOnClickListener {
            finish()
        }
    }
}