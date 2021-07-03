package com.techtown.kotlinsample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiJavaActivity extends AppCompatActivity {

    // 불필요한 findViewById 함수를 피하기 위해 UI 요소를 멤버 변수로 가지고 있는다.
    EditText tailField;
    EditText weightField;
    TextView resultLabel;
    Button bmiButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI로 사용할 레이아웃 XML 파일을 지정한다.
        setContentView(R.layout.layout_view_binding);

        // UI 요소 멤버변수를 XML 레이아웃에서 findViewID 함수를 이용해 바인딩한다.
        tailField = findViewById(R.id.tailField);
        weightField = findViewById(R.id.weightField);
        resultLabel = findViewById(R.id.resultLabel);
        bmiButton = findViewById(R.id.bmiButton);
        //bmibutton 이 클릭된 경우의 이벤트 리스너를 등록한다.
        findViewById(R.id.bmiButton).setOnClickListener(new View.OnClickListener(){

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //tailField 의 값을 읽어온다.
//                EditText tailField = findViewById(R.id.tailField);
                String tall = tailField.getText().toString();

                //weightField 의 값을 읽어온다.
//                EditText weightField = findViewById(R.id.weightField);
                String weight = weightField.getText().toString();

                //BMI 를 계산한다. 체중(kg) / 키(m) * 키(m) >> 키를 cm 로 입력받았으므로 100으로 나누어 제곱한다.
                //Math.pow() 는 넘겨받은 파라미터 값을 제곱해서 돌려준다.
                double bmi = Double.parseDouble(weight) / Math.pow(Double.parseDouble(tall) / 100.0, 2);
                // 결과 bmi 를 resultLabel 에 보여준다
//                TextView resultLabel = findViewById(R.id.resultLabel);
                resultLabel.setText("키: " + tall + ", 체중: " + weight + "-> BMI: " + bmi);
            }
        });
    }
}