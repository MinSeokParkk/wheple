package com.minseok.wheple.near


import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.minseok.wheple.R
import com.minseok.wheple.place.PlaceActivity
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.dialog_filter_near.*
import kotlinx.android.synthetic.main.fragment_near.*
import kotlinx.android.synthetic.main.fragment_near.view.*


class NearFragment : androidx.fragment.app.Fragment(), NearContract.View, OnMapReadyCallback,
                     GoogleMap.OnMarkerClickListener{

   private lateinit var mPresenter : NearContract.Presenter
    private lateinit var mMap : GoogleMap
    private lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest

    var  currentPosition :LatLng? = null
    private var firstsetting = false

    private  var selectedMarker : Marker? = null

    private lateinit var mapView:View

    val  REQUIRED_PERMISSIONS  = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)



    override fun setPresenter(presenter: NearContract.Presenter) {
        this.mPresenter = presenter
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_near, container, false)

        NearPresenter(this)

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)
            .setFastestInterval(500)

        val builder : LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.context)


        val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.frag_near) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = mapFragment.view!!

        view.img_near_filter.setBackgroundResource(R.drawable.icon_filter_black)

        view.layout_near_search.setOnClickListener {
            layout_near_detail.visibility = View.GONE
            layout_near_search.visibility = View.GONE
            selectedMarker  = null
            mMap.clear()
            getCameraRange()

        }


        view.text_near_disappear.setOnClickListener {
            selectedMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            selectedMarker = null
            layout_near_detail.visibility = View.GONE

        }


        view.layout_near_filter.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this.context).inflate(R.layout.dialog_filter_near, null)
            val mBuilder = AlertDialog.Builder(this.context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            // 1. 원래 가지고 있던 필터 조건에 맞게 바꿈
           mPresenter.setfilter( mAlertDialog.Ncheck_soccer, mAlertDialog.Ncheck_futsal,
               mAlertDialog.Ncheck_baseball, mAlertDialog.Ncheck_basketball,
               mAlertDialog.Ncheck_badminton, mAlertDialog.Ncheck_tennis,
               mAlertDialog.Ncheck_pingpong, mAlertDialog.Ncheck_volleyball,

               mAlertDialog.Ncheck_parking, mAlertDialog.Ncheck_shower ,
               mAlertDialog.Ncheck_cooling,mAlertDialog.Ncheck_heating)


            mAlertDialog.dialog_near_apply.setOnClickListener {
                // 필터 데이터를 현재 조건으로 바꿈
                mPresenter.filter_change( mAlertDialog.Ncheck_soccer.isChecked,
                        mAlertDialog.Ncheck_futsal.isChecked,
                        mAlertDialog.Ncheck_baseball.isChecked,
                        mAlertDialog.Ncheck_basketball.isChecked,
                        mAlertDialog.Ncheck_badminton.isChecked,
                        mAlertDialog.Ncheck_tennis.isChecked,
                        mAlertDialog.Ncheck_pingpong.isChecked,
                        mAlertDialog.Ncheck_volleyball.isChecked,

                        mAlertDialog.Ncheck_parking.isChecked,
                        mAlertDialog.Ncheck_shower.isChecked ,
                        mAlertDialog.Ncheck_cooling.isChecked,
                        mAlertDialog.Ncheck_heating.isChecked)

                // 위치 다시 가져옴

                layout_near_detail.visibility = View.GONE
                layout_near_search.visibility = View.GONE
                selectedMarker  = null
                mMap.clear()
                getCameraRange()

                // 조건이 있을 경우 필터 아이콘 레이아웃 배경색 바꿈
                mPresenter.change_filter()

                mAlertDialog.dismiss()
            }

            mAlertDialog.dialog_near_reset.setOnClickListener {
                mAlertDialog.Ncheck_soccer.isChecked = false
                mAlertDialog.Ncheck_futsal.isChecked = false
                mAlertDialog.Ncheck_baseball.isChecked = false
                mAlertDialog.Ncheck_basketball.isChecked = false
                mAlertDialog.Ncheck_badminton.isChecked = false
                mAlertDialog.Ncheck_tennis.isChecked = false
                mAlertDialog.Ncheck_pingpong.isChecked = false
                mAlertDialog.Ncheck_volleyball.isChecked = false

                mAlertDialog.Ncheck_parking.isChecked = false
                mAlertDialog.Ncheck_shower.isChecked = false
                mAlertDialog.Ncheck_cooling.isChecked = false
                mAlertDialog.Ncheck_heating.isChecked = false
            }

        }

        view.text_near_permission.setOnClickListener {
            showPermissionDialog()
        }


        // Return the fragment view/layout
        return view

    }




    companion object {
        fun newInstance(): NearFragment = NearFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)


            locationResult?.let {
                for((i, location) in it.locations.withIndex()) {
//                    Log.d("near12", "#$i ${location.latitude} , ${location.longitude}")
                    currentPosition = LatLng(location.latitude, location.longitude)
                    if(!firstsetting && currentPosition!=null){
                        firstsetting = true
                       initialready()

                    }

                }
            }

        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        const_near_loading.visibility = View.VISIBLE
        val defaultlocation = LatLng(37.56668, 126.97843)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultlocation, 10f))


        //나침판 레이아웃 위치 수정(필터 레이아웃 자리 때문에)
        val compassButton:View = mapView.findViewWithTag("GoogleMapCompass")
        val rlp=compassButton.layoutParams as (RelativeLayout.LayoutParams)
        rlp.setMargins(30,110,0,0)



    }

    fun initialready(){

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 14f))

        getCameraRange()

        const_near_loading.visibility = View.GONE
        layout_near_filter.visibility = View.VISIBLE
        mMap.setOnCameraMoveStartedListener {
            layout_near_search.visibility = View.VISIBLE
        }
    }


    override fun  startLocationUpdates(){
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden){
            Log.d("near12","hidden")

            mFusedLocationClient.removeLocationUpdates(locationCallback)
        }else{
            Log.d("near12","shown")
            mPresenter.check_viewhasPer()

//            startLocationUpdates()
        }
    }

    override fun showToast(string: String) {
        Toast.makeText(this.context, string, Toast.LENGTH_SHORT).show()
    }

    override fun loc_setting(x:Double, y:Double, no:String){

       val location = LatLng(x, y)
        val makerOptions = MarkerOptions()
        makerOptions.position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .title(no)
            .snippet("설명 테스트~~~")

        mMap.addMarker(makerOptions)

        mMap.setOnMarkerClickListener(this)


    }

    override fun onMarkerClick(marker: Marker): Boolean {  // 마커 눌렀을 때 일어나는 이벤트
        val center : CameraUpdate  = CameraUpdateFactory.newLatLng(marker.position)
        mMap.animateCamera(center) //카메라 이동


//        showToast(marker.title + " & " + marker.position.latitude)
        mPresenter.getDetail(marker.title, marker, currentPosition!!)

        return true
    }


    override fun changeSelectedMarker(marker: Marker){
        if(selectedMarker!=null){ //기존 선택됐었던 마커 색 되돌리기
            selectedMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        }

        if(marker!=null){ //선택된 마커 색 바꾸기
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            selectedMarker = marker

            layout_near_detail.setOnClickListener {
                activity?.let {
                    val intent = Intent(it,  PlaceActivity::class.java)
                    intent.putExtra("no", marker.title)
                    it.startActivity(intent)
                }
            }

            layout_near_detail.visibility = View.VISIBLE
        }
    }

    private fun getCameraRange(){
        val lat_ne:Float =  mMap.projection.visibleRegion.latLngBounds.northeast.latitude.toFloat()
        val lng_ne:Float =  mMap.projection.visibleRegion.latLngBounds.northeast.longitude.toFloat()

        val lat_sw:Float =  mMap.projection.visibleRegion.latLngBounds.southwest.latitude.toFloat()
        val lng_sw:Float =  mMap.projection.visibleRegion.latLngBounds.southwest.longitude.toFloat()

        mPresenter.getPlace(lat_ne, lng_ne, lat_sw, lng_sw)
    }

    override fun setDetail(photo:String, name:String, rating:String, review:String, price:String,
                           marker: Marker, distance:String){
        Glide.with(this)
            .load(photo)
            .into(img_near_d)
        text_near_name.text = name
        text_near_rating.text = rating
        text_near_review.text = review
        text_near_price.text = price
        text_near_distance.text = distance

        changeSelectedMarker(marker)

    }

    override fun set_checkbox(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox,
                             c5: CheckBox, c6: CheckBox, c7: CheckBox, c8: CheckBox,
                             c9: CheckBox, c10: CheckBox, c11: CheckBox, c12: CheckBox,
                             soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                             badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean,
                             parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean){
        c1.isChecked = soccer
        c2.isChecked = futsal
        c3.isChecked = baseball
        c4.isChecked = basketball
        c5.isChecked = badminton
        c6.isChecked = tennis
        c7.isChecked = pingpong
        c8.isChecked = volley

        c9.isChecked = parking
        c10.isChecked = shower
        c11.isChecked = cooling
        c12.isChecked = heating
    }



    override fun change_filterlayout(change:Int){
        if(change!=0){
            layout_near_filter.setBackgroundResource(R.drawable.layout_place)
            img_near_filter.setBackgroundResource(R.drawable.icon_filter_white)
            layout_near_count.visibility = View.VISIBLE
            text_near_count.text=change.toString()
        }else{
            layout_near_count.visibility = View.GONE
            layout_near_filter.setBackgroundResource(R.drawable.layout_mylocation)
            img_near_filter.setBackgroundResource(R.drawable.icon_filter_black)
        }

    }

    override fun set_placeNumber(num: Int) {
        if(num>0){
            text_near_top.text = "내 주변 ("+ num.toString() + "곳)"
        }else{
            showToast("현재 조건에 맞는 장소가 없습니다.")
            text_near_top.text = "내 주변 (장소 없음)"
        }
    }

    override fun checkPermission(): Boolean {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
        if( hasFineLocationPermission == PackageManager.PERMISSION_DENIED ||
            hasCoarseLocationPermission == PackageManager.PERMISSION_DENIED ) return false

        return true

    }

    override fun showPermissionDialog() {
       this.requestPermissions(REQUIRED_PERMISSIONS, 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 // mPresenter.currentPosition()
                showPermissionView()

            }
        }
    }

    override fun showPermissionView() {
        const_near_noper.visibility = View.GONE
        const_near_okper.visibility = View.VISIBLE
        if(currentPosition==null){
            const_near_loading.visibility = View.VISIBLE
        }


        startLocationUpdates()
    }

    override fun showNoPermissionView() {
        const_near_noper.visibility = View.VISIBLE
        const_near_loading.visibility = View.GONE
        const_near_okper.visibility = View.GONE


    }


}