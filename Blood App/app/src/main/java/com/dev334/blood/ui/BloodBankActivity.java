package com.dev334.blood.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.databinding.ActivityBloodBankBinding;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.BloodBank;
import com.dev334.blood.model.GovApiResponse;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;
import com.dev334.blood.util.retrofit.GovApiClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapMarkerCluster;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodBankActivity extends AppCompatActivity {

    private ActivityBloodBankBinding binding;
    private AppConfig appConfig;
    private final String TAG="BloodBankLog";
    private List<BloodBank> bloodBankList;
    private List<MapMarker> bankMarkers;
    private LocationRequest locationRequest;
    private Double longitude=77.199621, latitude=28.61436;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBloodBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mapView2.onCreate(savedInstanceState);

        appConfig=new AppConfig(this);
        bloodBankList=new ArrayList<>();

        binding.mapView2.setOnReadyListener(new MapView.OnReadyListener() {
            @Override
            public void onMapViewReady() {
                Log.i(TAG, "onMapViewReady: Ready to use map");
            }
        });
        loadMapScene();
        getBloodBanks();

    }

    private void getBloodBanks() {
        Call<GovApiResponse> call= GovApiClient.getApiClient(getApplicationContext()).create(ApiInterface.class).getBloodBank(appConfig.getUserLocation());
        call.enqueue(new Callback<GovApiResponse>() {
            @Override
            public void onResponse(Call<GovApiResponse> call, Response<GovApiResponse> response) {

                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body().getTotal()!=0){
                    Log.i(TAG, "onResponse: "+response.body().getTotal());
                    bloodBankList=response.body().getResponse();
                    convertToGeoCoordinates();
                }

            }

            @Override
            public void onFailure(Call<GovApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
    
    private void convertToGeoCoordinates(){
        MapImage mapImage = MapImageFactory.fromResource(getResources(), R.drawable.place_png);
        bankMarkers=new ArrayList<>();
        for(BloodBank bank: bloodBankList){
            longitude=bank.getLongitude();
            latitude=bank.getLatitude();
            GeoCoordinates geo=new GeoCoordinates(bank.getLatitude(), bank.getLongitude());
            bankMarkers.add(new MapMarker(geo, mapImage));
        }

        Log.i(TAG, "convertToGeoCoordinates: "+bankMarkers.size());

        setMarker();
    }

    private void loadMapScene() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        binding.mapView2.getMapScene().loadScene(MapScheme.NORMAL_NIGHT, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    MapImage mapImage = MapImageFactory.fromResource(getResources(), R.drawable.place_png);
                    GeoCoordinates geo=new GeoCoordinates(latitude,longitude);
                    double distanceInMeters = 1000 * 10;
                    binding.mapView2.getCamera().lookAt(
                            geo, distanceInMeters);
                    MapMarker mapMarker=new MapMarker(geo, mapImage);
                    Log.i(TAG, "setMapLocation: "+latitude+" "+longitude);
                    mapMarker.setCoordinates(geo);
                    binding.mapView2.getMapScene().addMapMarker(mapMarker);
                    binding.mapView2.getMapScene().removeMapMarker(mapMarker);
                } else {
                    Log.d(TAG, "Loading map failed: mapError: " + mapError.name());
                }
            }
        });
    }

    private void setMapLocation(){
        double distanceInMeters = 1000 * 10;
        GeoCoordinates geo;
        if(bloodBankList.get(0).getLatitude()==0) {
            geo = new GeoCoordinates(bloodBankList.get(1).getLatitude(), bloodBankList.get(1).getLongitude());
        }else if(bloodBankList.size()>1){
            geo = new GeoCoordinates(bloodBankList.get(0).getLatitude(), bloodBankList.get(0).getLongitude());
        }else{
            Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            return;
        }
        binding.mapView2.getCamera().lookAt(geo, distanceInMeters);
    }

    private void setMarker() {
        binding.mapView2.getMapScene().addMapMarkers(bankMarkers);
        setMapLocation();
    }
    
}