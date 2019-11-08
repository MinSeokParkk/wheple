package com.minseok.wheple.map

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), MapContract.View, OnMapReadyCallback {

    private lateinit var mPresenter: MapContract.Presenter

    private lateinit var mMap :GoogleMap
    private lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
   override var  currentPosition :LatLng? = null
    private lateinit var  location :LatLng

    val  REQUIRED_PERMISSIONS  = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private  var clickC = false

    private var TAG = "mpa12"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        MapPresenter(this)

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)
            .setFastestInterval(500)

        val builder : LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.frag_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        text_map_name.text = intent.getStringExtra("name")
        text_map_address.text = intent.getStringExtra("address")

        img_map_back.setOnClickListener {
            onBackPressed()
        }

        text_map_copy.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", intent.getStringExtra("address"))
            clipboard.primaryClip = clip
            showToast("주소가 복사되었습니다.")
        }



        layout_placepin.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(location))
        }

        layout_mylocation.setOnClickListener {
            clickC = true
            mPresenter.currentPosition()
        }

    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()


    }

    override fun onPause() {
        super.onPause()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun setPresenter(presenter: MapContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mPresenter.setAddr(intent.getStringExtra("address"), this)
    }

    override fun loc_setting(x:Double, y:Double){
        Log.d(TAG, "x : " + x.toString() + " & y : "+y.toString())
        location = LatLng(x, y)
        val makerOptions = MarkerOptions()
        makerOptions.position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))

        mMap.addMarker(makerOptions)


    }



    val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)


            locationResult?.let {
                for((i, location) in it.locations.withIndex()) {
                    Log.d(TAG, "#$i ${location.latitude} , ${location.longitude}")
                    currentPosition = LatLng(location.latitude, location.longitude)
                    if(clickC && currentPosition!=null){
                        clickC = false
                        showCurrentP()
                        moveTocurrentP()
                        layout_map_loading.visibility = View.GONE

                    }

                }
            }

        }
    }


    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun  startLocationUpdates(){
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    override fun moveTocurrentP(){

        if(currentPosition!=null){

            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentPosition))
        }else{
            showToast("현재 위치를 가지고 오는 중입니다. 잠시 후 다시 시도해주세요.")
        }
    }

    override fun checkPermission(): Boolean {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if( hasFineLocationPermission == PackageManager.PERMISSION_DENIED ||
            hasCoarseLocationPermission == PackageManager.PERMISSION_DENIED ) return false

        return true

    }

    override fun showPermissionDialog() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              mPresenter.currentPosition()
            }
        }
    }

    override fun showCurrentP(){
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
    }

    override fun showProgress(){
        layout_map_loading.visibility = View.VISIBLE

    }








}