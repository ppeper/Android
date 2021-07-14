package com.kyonggi.quizlocker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import com.kyonggi.quizlocker.databinding.ActivityPrefFragmentBinding

class PrefFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrefFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrefFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 액티비티의 컨텥트 뷰를 MyPrefFragment 로 교체한다
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPrefFragment()).commit()
    }

    // PreferenceFragment: XML 로 작성한 Preference 를 UI 로 보여주는 클래스
    class MyPrefFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // Preference 정보가 있는 XML 파일 지정
            addPreferencesFromResource(R.xml.ex_pref)
        }
    }
}