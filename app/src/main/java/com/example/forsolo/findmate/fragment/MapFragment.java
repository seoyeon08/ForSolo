package com.example.forsolo.findmate.fragment;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.forsolo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;


public class MapFragment<Maker, LocationRequest, FusedLocationProviderClient> extends Fragment implements OnMapReadyCallback, PlacesListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private View view;
    private GoogleMap mMap;
    private MapView mapView = null;
    private Maker currentMaker=null;
    List<Marker> previous_maker = null;

    private static final String TAG = "googleMap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;

    //onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requsetPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Location mCurrentLocation;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    private View mLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*추가
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);*/

        //기존
        view = inflater.inflate(R.layout.activity_map, container, false); //[1006]h0: fragment_map -> activity_map 으로 교체해봄
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        /*previous_maker = new ArrayList<Marker>();

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCRACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);*/



        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }



    @Override
    public void onMapReady(final GoogleMap googleMap){
        mMap = googleMap;

        LatLng MJU = new LatLng(37.221870, 127.186669);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(MJU);
        markerOptions.title("명지대");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MJU, 15));
    }

    public void showPlaceInformation(LatLng location){
        mMap.clear();

        if(previous_maker != null){
            previous_maker.clear();
        }
        new NRPlaces.Builder()
                .listener(MapFragment.this)
                .key("AIzaSyAji9AERbjXT56oh5qgn7Ikp3XjW7Le7ps")
                .latlng(location.latitude, location.longitude)
                .radius(500)
                .type(PlaceType.RESTAURANT)
                .build()
                .execute();
    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(noman.googleplaces.Place place : places){
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());

                    //String markerSnippet = getCurrentAddress(latLng);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    //markerOptions.snippet(markerSnippet);
                    mMap.addMarker(markerOptions);
                }
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_maker);
                previous_maker.clear();
                previous_maker.addAll(hashSet);
            }
        });




    }

    @Override
    public void onPlacesFinished() {

    }
}