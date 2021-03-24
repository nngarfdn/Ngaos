package com.udindev.ngaos;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.agrawalsuneet.dotsloader.loaders.ZeeLoader;
import com.schibstedspain.leku.LocationPickerActivity;
import com.udindev.ngaos.api.ApiInterface;
import com.udindev.ngaos.api.ApiIslamicPrayerTimes;
import com.udindev.ngaos.model.ResponsePrayerTime;
import com.udindev.ngaos.model.Times;
import com.udindev.ngaos.utils.GlobalConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

public class WaktuSholatActivity extends AppCompatActivity {

    private TextView tv_location, tv_subuh, tv_dhuhur, tv_ashar, tv_maghrib, tv_isya;
    private String TAG = "Sholat Activity";
    private LocationManager locationManager;
    private String latitude, longitude, mlatitude, mlongitude;
    private Geocoder geocoder;
    private List<Address> addresses;
    private int MAP_BUTTON_REQUEST_CODE = 1;
    private ZeeLoader zeeLoader;
    private Toolbar toolbar;
    private Button btn_sholat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_sholat);
        tv_location = findViewById(R.id.tv_fr_Sholat_Location);
        tv_subuh = findViewById(R.id.tv_subuh);
        tv_dhuhur = findViewById(R.id.tv_dhuhur);
        tv_ashar = findViewById(R.id.tv_ashar);
        tv_maghrib = findViewById(R.id.tv_maghrib);
        tv_isya = findViewById(R.id.tv_isya);
        zeeLoader = findViewById(R.id.zeeLoader_sholat);
        toolbar = findViewById(R.id.toolbar_waktu_sholat);
        btn_sholat = findViewById(R.id.btn_waktuSholat);
        zeeLoader.setVisibility(View.VISIBLE);

        ActivityCompat.requestPermissions(WaktuSholatActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, GlobalConfig.REQUEST_CODE_LOCATION_PERMISSION);

        locationManager = (LocationManager) WaktuSholatActivity.this.getSystemService(Context.LOCATION_SERVICE);

        toolbar.setOnClickListener(view -> {
            Intent intent = new Intent(WaktuSholatActivity.this, MainActivity.class);
            startActivity(intent);
        });
        openMaps();

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            try {
                getLocation();
            } catch (IOException e) {
                Log.e(TAG, "Error Boss!!! " + e);
            }
        }
        getTimes();


    }

    public void openMaps() {
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new LocationPickerActivity.Builder()
                        .withUnnamedRoadHidden()
                        .withLegacyLayout()
                        .build(WaktuSholatActivity.this);
                intent.putExtra("test", "this is test");
                startActivityForResult(intent, MAP_BUTTON_REQUEST_CODE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);

                this.mlatitude = String.valueOf(latitude);
                this.mlongitude = String.valueOf(longitude);

                String alamat = data.getStringExtra(LOCATION_ADDRESS);
                tv_location.setText(alamat);
                getTimes();
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

    public void getTimes() {
        ApiInterface apiInterface = ApiIslamicPrayerTimes.getClient().create(ApiInterface.class);
        //get current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = formatter.format(calendar.getTime());
        Log.d("Coba", "latitude: " + mlatitude + " longtude : " + mlongitude + " date Now : " + dateNow);
        //get data
        Call<ResponsePrayerTime> responsePrayerTimeCall = apiInterface.getTimeFromFatimah(mlatitude, mlongitude, dateNow);
        responsePrayerTimeCall.enqueue(new Callback<ResponsePrayerTime>() {
            @Override
            public void onResponse(Call<ResponsePrayerTime> call, Response<ResponsePrayerTime> response) {

                Times times = response.body().getResults().getDatetime().get(0).getTimes();
                if (times == null) {
                    zeeLoader.setVisibility(View.VISIBLE);
                }
                zeeLoader.setVisibility(View.GONE);
                try {

                    time(times);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponsePrayerTime> call, Throwable t) {
                zeeLoader.setVisibility(View.GONE);
            }
        });
    }

    public void time(Times times) {
        zeeLoader.setVisibility(View.GONE);
        tv_subuh.setText(times.getFajr());
        tv_dhuhur.setText(times.getDhuhr());
        tv_ashar.setText(times.getAsr());
        tv_maghrib.setText(times.getMaghrib());
        tv_isya.setText(times.getIsha());
    }

    private void getLocation() throws IOException {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(WaktuSholatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WaktuSholatActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WaktuSholatActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, GlobalConfig.REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
            } else {
                Toast.makeText(WaktuSholatActivity.this.getApplicationContext(), "Tidak Mendapatkan Lokasi Anda", Toast.LENGTH_SHORT).show();
            }

            this.mlatitude = latitude;
            this.mlongitude = longitude;

            //getData
            try {
                double lat = Double.parseDouble(latitude);
                double lang = Double.parseDouble(longitude);

                geocoder = new Geocoder(WaktuSholatActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lang, 1);
                String address = addresses.get(0).getLocality();
                tv_location.setText(address);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(WaktuSholatActivity.this);
        builder.setMessage("Enable GPS")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, which) ->
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("NO", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}