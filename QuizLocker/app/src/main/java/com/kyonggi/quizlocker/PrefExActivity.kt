package com.kyonggi.quizlocker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyonggi.quizlocker.databinding.ActivityPrefExBinding

class PrefExActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrefExBinding
    // nameField 의 데이터를 저장할 Key
    val nameFieldKey = "nameField"
    // pushCheckBox 의 데이터를 저장할 Key
    val pushCheckBoxKey = "pushCheckBox"
    // shared preference 객체. Activity 초기화 이후에 사용해야 하기 때문에 lazy 위임을 사용
    val preference by lazy { getSharedPreferences("PrefExActivity", Context.MODE_PRIVATE)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrefExBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            // 저장 버튼이 클릭된 경우
            saveButton.setOnClickListener {
                // SharedPreference 에서 nameFieldKey 키값으로 nameField 의 현재 텍스트를 저장한다.
                preference.edit().putString(nameFieldKey, nameField.text.toString()).apply()
                // SharedPreference 에서 pushCheckBoxKey 키값으로 체크 박스의 현재 체크 상태를 저장한다.
                preference.edit().putBoolean(pushCheckBoxKey, pushCheckBox.isChecked).apply()
            }
            // 불러오기 버튼이 클릭된 경우
            loadButton.setOnClickListener {
                // SharedPreference 에서 nameFieldKey 로 저장된 문자열을 불러와 nameField 의 텍스트로 설정
                nameField.setText(preference.getString(nameFieldKey, ""))
                // SharedPreference 에서 pushCheckBoxKey 키값으로 불린값을 불러와 pushCheckBox 의 체크상태를 설정
                pushCheckBox.isChecked = preference.getBoolean(pushCheckBoxKey, false)
            }
        }
    }
}