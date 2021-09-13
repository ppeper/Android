package com.kyonggi.quizlocker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class MainActivity : AppCompatActivity() {
    val fragment = MyPreferenceFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // preferenceContent FrameLayout 영역을 PreferenceFragment 로 교체
        supportFragmentManager.beginTransaction().replace(R.id.preferenceContent, fragment).commit()
    }

    class MyPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref, rootKey)
            // 퀴즈 종류 요약정보에, 현재 선택된 항목을 보여주는 코드
            val categoryPref = findPreference<MultiSelectListPreference>("category")!!
            categoryPref.summary = categoryPref.values.joinToString(", ")
            // 환경설정 정보값이 변경될때에도 요약정보를 변경하도록 리스너 등록
            categoryPref.setOnPreferenceChangeListener { preference, newValue ->
                // newValue 파라미터가 HashSet 으로 캐스팅이 실패하면 리턴
                val newValueSet = newValue as? HashSet<*>
                    ?: return@setOnPreferenceChangeListener true
                // 선택된 퀴즈종류로 요약정보 보여줌
                 categoryPref.summary= newValue.joinToString (", ")
                true
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // 퀴즈 잠금화면 사용 스위치 객체 가져옴
            val useLockScreenPref = findPreference<SwitchPreference>("useLockScreen")
            useLockScreenPref?.setOnPreferenceClickListener {
                when {
                    // 퀴즈 잠금화면 사용이 체크된 경우 LockScreenService를 실행한다.
                    useLockScreenPref.isChecked -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            activity?.startForegroundService(
                                Intent(
                                    activity,
                                    LockScreenService::class.java
                                )
                            )
                        } else {
                            activity?.startService(Intent(activity, LockScreenService::class.java))
                        }
                    }
                    // 퀴즈 잠금화면 사용이 체크 해제된 경우 LockScreenService 중단
                    else -> activity?.stopService(Intent(activity, LockScreenService::class.java))
                }
                true
            }
            // 앱이 시작 되었을때 이미 퀴즈잠금화면 사용이 체크되어있으면 서비스 실행
            if (useLockScreenPref?.isChecked == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity?.startForegroundService(Intent(activity, LockScreenService::class.java))
                } else {
                    activity?.startService(Intent(activity, LockScreenService::class.java))
                }
            }
        }
    }
}