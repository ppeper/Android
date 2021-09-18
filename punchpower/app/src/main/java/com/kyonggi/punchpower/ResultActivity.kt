package com.kyonggi.punchpower

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.Games
import com.kyonggi.punchpower.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    val RC_SIGN_IN = 9000
    val RC_LEADERBOARD_UI = 9004
    val signingClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
    }
    val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    // 펀치력을 저장하는 변수. 사용하는 시점에 초기화되도록 lazy
    // 전달받은 값에 100 을 곱하는 이유는 가속도 측정값이 소숫점 담위의 차이므로 눈에 띄지 않기 때문
    val power by lazy {
        intent.getDoubleExtra("power", 0.0) * 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            scoreLabel.text = "${String.format("%.0f", power)} 점"

            restartButton.setOnClickListener {
                finish()
            }

            rankingButton.setOnClickListener {
                // 이전에 구글 인증을 받은적이 있는지 체크. 널이면 아직 인증받은적 없음
                if (GoogleSignIn.getLastSignedInAccount(this@ResultActivity) == null) {
                    // sign in 필요
                    startActivityForResult(signingClient.signInIntent, RC_SIGN_IN)
                } else {
                    uploadScore()
                }
            }
        }
    }

    private fun uploadScore() {
        // 인증된 유저 객체 가져옴
        var user = GoogleSignIn.getLastSignedInAccount(this)
        user?.let {
            val leaderboard = Games.getLeaderboardsClient(this, user)
            // 리더보드 객체에 점수를 즉시 올림. 성공시 컬백에서 리더보드 화면으로 이동
            leaderboard.submitScoreImmediate(getString(R.string.leaderboard_id), power.toLong())
                .addOnSuccessListener {
                    leaderboard.getLeaderboardIntent(getString(R.string.leaderboard_id))
                        .addOnSuccessListener { intent ->
                            startActivityForResult(intent, RC_LEADERBOARD_UI)
                        }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            result?.let { result ->
                if (result.isSuccess) {
                    uploadScore()
                } else {
                    var message = result.status.statusMessage
                    if (message == null || message.isEmpty()) {
                        message = "로그인 오류!"
                    }
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}