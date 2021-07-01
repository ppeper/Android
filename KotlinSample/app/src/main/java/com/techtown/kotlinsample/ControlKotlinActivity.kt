package com.techtown.kotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_control.*

class ControlKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // UI 로 사용할 레이아웃 XML 파일을 지정한다.
        setContentView(R.layout.layout_control)

        // 버튼이 클릭되었을때의 이벤트리스너 를 설정한다.
        button.setOnClickListener {
            // numberField 의 값을 읽어 int 형으로 반환한다.
            val number = numberField.text.toString().toInt()

            when {
                number % 2 == 0 -> {
                    Toast.makeText(applicationContext, "$number 는 2의 배수입니다.", Toast.LENGTH_SHORT).show()
                }
                number % 3 == 0 -> {
                    Toast.makeText(applicationContext, "$number 는 3의 배수입니다.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(applicationContext, "$number", Toast.LENGTH_SHORT).show()
                }
            }

            when (number) {
                4 -> button.text = "실행 - 4"
                9 -> button.text = "실행 - 9"
                else -> button.text = "실행"
            }
        }
    }
}