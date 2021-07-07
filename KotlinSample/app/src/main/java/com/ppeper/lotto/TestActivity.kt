package com.ppeper.lotto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.jar.Attributes

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // 코드에서 View에 이벤트 리스너를 설정하는 방법
        // 버튼과 같은 View 가 클릭되었을때 실행될 listener 를 등록하는 메소드가 setOnClickListener 이다
        findViewById<View>(R.id.button).setOnClickListener {
            // MainActivity 를 시작하는 Intent 를 생성한다.
            val intent = Intent(this@TestActivity, MainActivity::class.java)
            // intent 를 사용하여 Activity 를 시작한다
            startActivity(intent)
        }
        findViewById<View>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ConstellationActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button3).setOnClickListener {
            val intent = Intent(this, NameActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button4).setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     *  xml 에서 참조할수 있게 메소드를 정의한다.
     */
    fun goConstellation(view: View) {
        val intent = Intent(this@TestActivity, ConstellationActivity::class.java)
        startActivity(intent)
    }

    // 암시적 인텐트를 사용해 웹브라우저를 호출한다.
    fun callWeb(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://naver.com"))
        startActivity(intent)
    }
}