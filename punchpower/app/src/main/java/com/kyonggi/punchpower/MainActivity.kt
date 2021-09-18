package com.kyonggi.punchpower

import android.animation.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kyonggi.punchpower.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // 측정된 최대 펀치력
    var maxPower = 0.0
    // 펀치력 측정이 시작되었는지 나타내는 변수
    var isStart = false
    // 펀치력 측정이 시작된 시간
    var startTime = 0L
    // Sensor 관리자 객체. lazy 로 실제 사용될때 초기화 한다.
    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    // 센서 이벤트를 처리하는 리스너
    val eventListener: SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                // 측정된 센서 값이 선령 가속도 타입이 아니면 바로 리턴
                if (event.sensor.type != Sensor.TYPE_LINEAR_ACCELERATION) return@let
                // 각 죄쵸값을 제곱하여 음수값을 없애고, 값의 차이를 극대화
                val power =
                    event.values[0].toDouble().pow(2.0) + event.values[1].toDouble().pow(2.0) + event.values[2].toDouble().pow(2.0)
                // 측정된 펀치력이 20을 넘고 아직 측정이 시작되지 않은 경우
                if (power > 20 && !isStart) {
                    // 측정시작
                    startTime = System.currentTimeMillis()
                    isStart = true
                }
                // 측정이 시작된 경우
                if (isStart) {
                    // 애니메이션 제거
                        binding.imageView.clearAnimation()
                    // 5초간 최대값을 측정. 현재측정된 값이 지금까지 측정된 최대 값보다 크면 현재 값으로 변경
                    if (maxPower < power) {
                        maxPower = power
                    }
                    // 최초 측정후 3초가 지났으면 측정을 끝낸다.
                    if (System.currentTimeMillis() - startTime > 3000) {
                        isStart = false
                        punchPowerTestComplete(maxPower)
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }

    // 화면 최초 생성될때 호출되면 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initGame()
    }

    override fun onStop() {
        super.onStop()
        try {
            sensorManager.unregisterListener(eventListener)
        } catch (e: Exception){}
    }

    // 게임초기화
    private fun initGame() {
        maxPower = 0.0
        isStart = false
        startTime = 0L
        binding.stateLabel.text = "핸드폰을 손에쥐고 주먹을 내지르세요"
        // 센서의 변화 값을 처리할 리스너를 등록한다.
        sensorManager.registerListener(
            eventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        // 에니메이션 추가
//        binding.imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tran))
//        with(binding) {
//            val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.alpha_scale)
//            imageView.startAnimation(animation)
//
//            animation.setAnimationListener(object: Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {
//                    // 애니메이션이 시작될때의 코드를 작성
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    // 애니메이션이 종료될때의 코드를 작성
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {
//                    // 애니메이션이 반복될때의 처리 코드를 작성
//                }
//            })
//        }

        AnimatorInflater.loadAnimator(this, R.animator.property_animation).apply {
            addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    start()
                }
            })
            setTarget(binding.imageView)
            start()
        }

        AnimatorInflater.loadAnimator(this, R.animator.color_anim).apply {
            val colorAnimator = this@apply as? ObjectAnimator
            colorAnimator?.apply {
                setEvaluator(ArgbEvaluator())
                target = window.decorView.findViewById(android.R.id.content)
                start()
            }
        }

    }

    // 펀치력 측정이 완료된 경우 처리 함수
    private fun punchPowerTestComplete(power: Double) {
        Log.d("MainActivity", "측정완료: power: ${String.format("%.5f", power)}")
        sensorManager.unregisterListener(eventListener)
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("power", power)
        startActivity(intent)
    }
}