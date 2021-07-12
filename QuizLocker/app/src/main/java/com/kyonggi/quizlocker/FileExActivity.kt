package com.kyonggi.quizlocker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.kyonggi.quizlocker.databinding.ActivityFileExBinding
import java.io.FileNotFoundException

class FileExActivity : AppCompatActivity() {
    // ViewBinding을 위한 클래스
    private lateinit var binding: ActivityFileExBinding
    // 데이터 저장에 사용할 파일이름
    val filename = "data.txt"
    // 권한이 있는지 저장하는 변수
    val granted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 객체화하여 내부 변수 사용가능
        binding = ActivityFileExBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 외부저장소의 권한을 동적으로 체크하는 함수를 호출
        checkPermission()
        with(binding) {
            // 저장 버튼이 클릭된 경우
            saveButton.setOnClickListener {
                // textField 의 현재 텍스트를 가져온다.
                val text = textField.text.toString()
                when {
                    // 텍스트가 비어있는 경우 오류 메세지를 보여준다.
                    TextUtils.isEmpty(text) -> {
                        Toast.makeText(applicationContext,"텍스트가 비어있습니다.", Toast.LENGTH_LONG).show()
                    }
                    !isExternalStorageWriteable() -> {
                        Toast.makeText(applicationContext,"외부 저장장치가 없습니다.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        // 내부 저장소 파일에 저장하는 함수 호출
                        saveToInnerStorage(text, filename)
                    }
                }
            }
            // 불러오기 버튼이 클릭된 경우
            loadButton.setOnClickListener {
                try {
//                    textField 의 텍스트를 불러온 텍스트로 설정한다.
//                    textField.setText(loadFromInnerStorage(filename))
//                    외부저장소 앱전용 디렉토리의 파일에서 읽어온 데이터로 textField 의 텍스트를 설정
//                    textField.setText(loadFromExternalStorage(filename))

                    // 외부저장소 "/sdcard/data.text" 에서 데이터를 불러온다
                    textField.setText(loadFromExternalCustomDirectory())

                } catch (e: FileNotFoundException) {
                    // 파일이 없는 경우 에러메세지 보여줌
                    Toast.makeText(applicationContext, "저장된 텍스트가 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }

        // 내부저장소 파일의 텍스트를 저장한다.
        fun saveToInnerStorage(text: String, filename: String) {
            // 내부 저장소의 전달된 파일이름의 파일 출력 스트림을 가져온다.
            val fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
            // 파일 출력 스트림에 text 를 바이트로 변환하여 write 한다
            fileOutputStream.write(text.toByteArray())
            // 파일 출력 스트림을 닫는다
            fileOutputStream.close()
        }

        // 내부저장소 파일의 텍스트를 불러온다
        fun loadFromInnerStorage(filename: String): String {
            // 배부저장소의 전달된 파일이름의 파일 입력 스트림을 가져온다
            val fileInputStream = openFileInput(filename)
            // 파일의 저장된 내용을 읽어 String 형태로 불러온다.
            return fileInputStream.reader().readText()
        }
    }

}