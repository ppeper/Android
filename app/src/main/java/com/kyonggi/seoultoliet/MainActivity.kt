package com.kyonggi.seoultoliet

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kyonggi.seoultoliet.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    // 퍼미션 승인 요청시 사용하는 요청 코드
    val REQUEST_PERMISSION_CODE = 1

    // 기본 맵 줌 레벨
    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 맵뷰에 onCreate 함수 호출
        binding.mapView.onCreate(savedInstanceState)

        // 앱이 실행될때 런타임에서 위치 서비스 관련 권한 체크
        if (hasPermission()) {
            initMap()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        binding.myLocationButton.setOnClickListener {
            onMyLocationButtonClick()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 맵 초기화
        initMap()
    }

    // 앱에서 사용하는 권한이 있는지 체크하는 함수
    fun hasPermission(): Boolean {
        // 퍼미션목록중 하나라도 권한이 없으면 false
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // 맵을 초기화하는 함수
    @SuppressLint("MissingPermission")
    fun initMap() {
        // 맵뷰에서 구글 맵을 불러오는 함수. 컬백함수에서 구글 맵 객체가 전달됨
        binding.mapView.getMapAsync {
            // 구글맵 멤버 변수에 구글맵 객체 저장
            googleMap = it
            // 현재위치로 이동 버튼 비활성화
            it.uiSettings.isMyLocationButtonEnabled = false
            // 위치 사용 권한이 있는 경우
            when {
                hasPermission() -> {
                    it.isMyLocationEnabled = true
                    // 현재 위치로 카메라 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    // 권한이 없으면 서울시청의 위치로 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {
        // 위치를 측정하는 포로바이더를 GPS 센서로 지정
        val locationProvider: String = LocationManager.GPS_PROVIDER
        // 위치 서비스 객체를 불러옴
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 마지막으로 업데이트된 위치를 가져옴
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        return if (lastKnownLocation != null) {
            // 위도 경도 객체로 반환
            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        } else {
            CITY_HALL
        }
    }

    // 현재 위치 버튼 클릭한 경우
    fun onMyLocationButtonClick() {
        when {
            hasPermission() -> {
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
            }
            else -> {
                Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    // 서울 열린 데이터 광장에서 받은 API 키
    val API_KEY = "6267454c7a776e733130327259665442"

    // 앱이 비활성화될때 백그라운드 작업도 취소하기위한 변수
    var task: ToiletReadTask? = null

    // 서울시 화장실 정보 집합을 저장할 Array 변수. 검색을 위해 저장
    var toilets = JSONArray()

    // 화장실 이미지로 사용할 Bitmap
    val bitmap by lazy {
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.restroom_sign, null) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
    }

    // JSONArray 를 병합하기 위해 확장함수 사용
    fun JSONArray.merge(anotherArray: JSONArray) {
        for (i in 0 until anotherArray.length()) {
            this.put(anotherArray[i])
        }
    }

    // 화장실 정보를 읽어와 JSONObject 로 반환하는 함수
    fun readData(startIndex: Int, lastIndex: Int): JSONObject {
        val url = URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/json/SearchPublicToiletPOIService/${startIndex}/${lastIndex}")
        val connection = url.openConnection()

        val data = connection.getInputStream().readBytes().toString(charset("UTF-8"))
        return JSONObject(data)
    }

    // 화장실 데이터를 읽어오는 AsyncTask
    inner class ToiletReadTask : AsyncTask<Void, JSONArray, String>() {

        override fun onPreExecute() {
            googleMap?.clear()
            toilets = JSONArray()
        }
        override fun doInBackground(vararg params: Void?): String {
            val step = 1000
            var startIndex = 1
            var lastIndex = step
            var totalCount = 0

            do {
                if (isCancelled) break

                if (totalCount != 0) {
                    startIndex += step
                    lastIndex += step
                }

                val jsonObject = readData(startIndex,lastIndex)

                totalCount = jsonObject.getJSONObject("SearchPublicToiletPOIService").getInt("list_total_count")

                val rows = jsonObject.getJSONObject("SearchPublicToiletPOIService").getJSONArray("row")

                toilets.merge(rows)

                publishProgress(rows)
            } while (lastIndex < totalCount)

            return "complete"
        }

        override fun onProgressUpdate(vararg values: JSONArray?) {
            val array = values[0]
            array?.let {
                for (i in 0 until array.length()) {
                    addMarkers(array.getJSONObject(i))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        task?.cancel(true)
        task = ToiletReadTask()
        task?.execute()
    }

    override fun onStop() {
        super.onStop()
        task?.cancel(true)
        task = null
    }

    fun addMarkers(toilet: JSONObject) {
        googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")))
                .title(toilet.getString("FNAME"))
                .snippet(toilet.getString("ANAME"))
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )
    }
}