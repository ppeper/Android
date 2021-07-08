package com.ppeper.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ppeper.lotto.LottoNumberMaker.getShuffleLottoNumbers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_name.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 랜덤으로 번호 생성 카드의 클릭 이벤트 리스너
        findViewById<View>(R.id.randomCard).setOnClickListener {
            // ResultActivity 를 시작하는 Intent 생성
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            // intent 의 결과 데이터를 전달한다.
            // int 의 리스트를 전달하므로 putIntegerArrayListExtra 를 사용한다.
            intent.putIntegerArrayListExtra("result",ArrayList(getShuffleLottoNumbers()))
            startActivity(intent)
        }
        // 별자리로 번호 생성 카드의 클릭 이벤트 리스너
        findViewById<View>(R.id.constellationCard).setOnClickListener {
            // ConstellationActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(Intent(this, ConstellationActivity::class.java))
        }
        // 이름으로 번호 생성 카드의 클릭 이벤트 리스너
        findViewById<View>(R.id.nameCard).setOnClickListener {
            // NameActivity 를 시작하는 Intent 를 만들고 startActivity 로 실행
            startActivity(Intent(this, NameActivity::class.java))
        }
    }
}