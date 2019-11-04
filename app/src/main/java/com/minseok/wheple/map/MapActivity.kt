package com.minseok.wheple.map

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), MapContract.View, OnMapReadyCallback {

    private lateinit var mPresenter: MapContract.Presenter

    private lateinit var mMap :GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        MapPresenter(this)


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
    }


    override fun setPresenter(presenter: MapContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mPresenter.setAddr(intent.getStringExtra("address"), this)
    }

    override fun loc_setting(x:Double, y:Double){
        val location:LatLng = LatLng(x, y)
        val makerOptions = MarkerOptions()
        makerOptions.position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        mMap.addMarker(makerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.toFloat()))
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }



}