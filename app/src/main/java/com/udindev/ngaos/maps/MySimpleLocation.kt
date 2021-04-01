package com.nanang.lokasi

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MySimpleLocation (val activity: Activity, mySimpleLocationCallBack: MySimpleLocationCallBack) :
    MySettingLocation.MySettingLocationCallback {

    private val TAG = "MYSIMPLELOCATION"
    private var mLocationCallback: LocationCallback? = null

    private var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    interface MySimpleLocationCallBack {
        fun getLocation(location: Location?)
    }


    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private val locationRequest: LocationRequest
        get() {
            val locationRequest = LocationRequest()
            locationRequest.interval = 5000
            locationRequest.fastestInterval = 1000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            return locationRequest
        }

    init {
        // Initialize the location callbacks.
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let {
                    Log.d(TAG, "onLocationResult : Get Last Location! $locationResult")
                    mySimpleLocationCallBack.getLocation(it)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation() {
        Log.d(TAG, "getMyLocation : Trying to get location")
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback!!,
            Looper.myLooper()
        )
    }

    fun checkLocationSetting(activity: Activity) {
        Log.d(TAG, "checkLocationSetting : Checking GPS or Highly Accurate is active or not")
        MySettingLocation.checkLocationSetting(locationRequest, activity, this)
    }

    fun stopGetLocation() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
            .addOnCompleteListener {
                Log.d(TAG, "Location Callback was removed $it")
            }
    }

    override fun settingLocationActive(isActive: Boolean) {
        if (isActive) {
            Log.d(TAG, "settingLocationActive : GPS is active")
            getMyLocation()
        }
    }
}