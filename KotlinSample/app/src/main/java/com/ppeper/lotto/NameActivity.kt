package com.ppeper.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ppeper.lotto.LottoNumberMaker.getLottoNumbersFromHash
import kotlinx.android.synthetic.main.activity_name.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        // 뒤로가기 버튼의 클릭이벤트 리스너 설정
        findViewById<View>(R.id.backButton).setOnClickListener {
            // 액티비티 종료
            finish()
        }
        // kotlin extensions 사용
        goButton.setOnClickListener {
            if (TextUtils.isEmpty(editText.text.toString())) {
                Toast.makeText(applicationContext,"이름을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(getLottoNumbersFromHash(editText.text.toString())))
            intent.putExtra("name",editText.text.toString())
            startActivity(intent)
        }
    }
}