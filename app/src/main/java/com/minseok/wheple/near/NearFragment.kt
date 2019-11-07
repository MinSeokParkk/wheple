package com.minseok.wheple.near


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        view.layout_near_search.setOnClickListener {
            layout_near_detail.visibility = View.GONE
            selectedMarker  = null
            mMap.clear()
            getCameraRange()

        }


        view.text_near_disappear.setOnClickListener {
            selectedMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            selectedMarker = null
            layout_near_detail.visibility = View.GONE
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


    }

    fun initialready(){

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 14f))

        getCameraRange()

        const_near_loading.visibility = View.GONE
    }


   fun  startLocationUpdates(){
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden){
            mFusedLocationClient.removeLocationUpdates(locationCallback)
        }else{
            startLocationUpdates()
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

    override fun onMarkerClick(marker: Marker): Boolean {
        val center : CameraUpdate  = CameraUpdateFactory.newLatLng(marker.position)
        mMap.animateCamera(center)


//        showToast(marker.title + " & " + marker.position.latitude)
        mPresenter.getDetail(marker.title, marker)

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

    override fun setDetail(photo:String, name:String, rating:String, review:String, price:String, marker: Marker){
        Glide.with(this)
            .load(photo)
            .into(img_near_d)
        text_near_name.text = name
        text_near_rating.text = rating
        text_near_review.text = review
        text_near_price.text = price

        changeSelectedMarker(marker)


    }


}