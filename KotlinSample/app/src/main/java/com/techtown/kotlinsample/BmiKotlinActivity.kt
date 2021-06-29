package com.techtown.kotlinsample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_view_binding.*
import kotlin.math.pow

class BmiKotlinActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // UI로 사용할 레이아웃 XML 파일을 지정한다.
        setContentView(R.layout.layout_view_binding)

        // bmi 버튼이 클릭된 경우 동작하는 코드를 작성한다.
        bmiButton.setOnClickListener {
            //tailField 의 값을 읽어온다.
            val tall = tailField.text.toString().toDouble()

            // weightField 의 값을 읽어온다.
            val weight = weightField.text.toString().toDouble()

            //BMI 를 계산한다. 체중(kg) / 키(m) * 키(m) >> 키를 cm 로 입력받았으므로 100으로 나누어 제곱한다.
            //Math.pow() 는 넘겨받은 파라미터 값을 제곱해서 돌려준다.
            val bmi = weight / (tall / 100).pow(2.0)

            // 결과 BMI 를 resultLabel 에 보여준다.
            resultLabel.text = "키: ${tailField.text}, 체중: ${weightField.text}, BMI: $bmi"
        }
    }
}