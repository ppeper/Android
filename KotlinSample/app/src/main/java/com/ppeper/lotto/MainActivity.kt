package com.ppeper.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
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
            intent.putIntegerArrayListExtra("result",ArrayList(getRandomLottoNumbers()))
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

    /**
     * 램덤으로 1~ 45 번호중 하나의 번호를 생성하는 함수
     */
    private fun getRandomLottoNumber(): Int {
        // Random.nextInt 는 0 ~ 전달받은 파라미터 값 미만의 번호를 생성
        // ex) Random().nextInt(10) 은 0 ~ 9 까지의 무작위 수를 반환
        return Random.nextInt(45) + 1
    }

    /**
     * 랜덤으로 추출하여 6개의 로또 번호를 만드는 함수
     */
    private fun getRandomLottoNumbers(): MutableList<Int> {
        val lottoNumbers = mutableListOf<Int>()

        // 6번 반복하는 for 문
        for (i in 1..6) {
            // 랜덤한 번호를 임시로 저장할 변수를 생성
            var number = 0
            do {
                // 랜덤한 번호를 추출해 number 변수에 저장
                number = getRandomLottoNumber()

                // lottoNumbers 에 number 변수의 값이 없을 때까지 반복
            } while (lottoNumbers.contains(number))

            // 이미 뽑은 리스트에 없는 번호가 나올때까지 반복했으므로 중복이 없는상태
            lottoNumbers.add(number)
        }
        return lottoNumbers
    }
}