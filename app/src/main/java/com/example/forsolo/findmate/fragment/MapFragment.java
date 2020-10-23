package com.example.forsolo.findmate.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.forsolo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap mMap;
    private MapView mapView = null;

    //지도생성
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        return view;

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();

    //방법 4
        AssetManager assetManager = getActivity().getAssets();


        try{
            //파일을 읽기 위한 inputStream생성
            InputStream is = assetManager.open("Restaurant.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while(line!=null){
                buffer.append(line+"\n");
                line = reader.readLine();
            }

            String json = buffer.toString();
            //JSONObject 생성
            JSONObject jsonObject = new JSONObject(json);
            String info = jsonObject.getString("info");
            //JSONArray 생성
            JSONArray jsonArray = new JSONArray(info);

            for (int i = 0; i<info.length();i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                //JSONObject로 부터 json 파일의 키에 맞는 값 가져오기
                String name = subJsonObject.getString("BIZPLC_NM");
                String addr = subJsonObject.getString("REFINE_ROADNM_ADDR");
                String lat = subJsonObject.getString("REFINE_WGS84_LAT");
                String log = subJsonObject.getString("REFINE_WGS84_LOGT");

                //String 타입의 위도, 경도를 double 타입으로 변환
                double LAT = Double.parseDouble(lat);
                double LOG = Double.parseDouble(log);


                LatLng latLng = new LatLng(LAT, LOG);

                //marker에 식당 이름, 주소를 포함한 마커 생성
                markerOptions.position(latLng);
                markerOptions.title(name);
                markerOptions.snippet(addr);
                mMap.addMarker(markerOptions);
            }

        }catch (JSONException | IOException e){
            e.printStackTrace();
        }

        //카메라를 해당 위치로 잡아둠
        LatLng stand = new LatLng(37.229635, 127.187521);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stand, 15));


    }
}
