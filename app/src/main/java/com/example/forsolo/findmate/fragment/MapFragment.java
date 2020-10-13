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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map, container, false); //[1006]h0: fragment_map -> activity_map 으로 교체해봄
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

/*    //방법 3 실패 : 필요한 다른 클래스 구현시 onReadyMap 작동X
    //Json 파일을 읽어와 파일 내용을 String 변수에 담아 return 하는 getJsonString()함수
    private String getJsonString(){
        String json = "";
        try{
            InputStream is = getActivity().getAssets().open("document.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }catch (IOException ex){ex.printStackTrace();}

        return json;
    }

    public class Location{
        private String BIZPLC_NM;
        private String REFINE_LOTNO_ADDR;
        private String REFINE_WGS84_LAT ;
        private String REFINE_WGS84_LOGT;

        public String getBIZPLC_NM(){
            return BIZPLC_NM;
        }

        public String getREFINE_LOTNO_ADDR(){
            return REFINE_LOTNO_ADDR;
        }

        public String getREFINE_WGS84_LAT(){
            return REFINE_WGS84_LAT;
        }

        public String getREFINE_WGS84_LOGT(){
            return REFINE_WGS84_LOGT;
        }

        public void setBIZPLC_NM(String BIZPLC_NM) {
            this.BIZPLC_NM = BIZPLC_NM;
        }

        public void setREFINE_LOTNO_ADDR(String REFINE_LOTNO_ADDR) {
            this.REFINE_LOTNO_ADDR = REFINE_LOTNO_ADDR;
        }

        public void setREFINE_WGS84_LAT(String REFINE_WGS84_LAT) {
            this.REFINE_WGS84_LAT = REFINE_WGS84_LAT;
        }

        public void setREFINE_WGS84_LOGT(String REFINE_WGS84_LOGT) {
            this.REFINE_WGS84_LOGT = REFINE_WGS84_LOGT;
        }


    }
*/
/*
    public class LocationGson {
        List<Location_info> result;
    }

    public class Location_info {
        String BIZPLC_NM;
        String REFINE_LOTNO_ADDR;
        String REFINE_WGS84_LAT;
        String REFINE_WGS84_LOGT;
    }
*/
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in MJU and move the camera

/*
        // 방법 2-1 실패. Reader reader에서 null 포인터 오류
        //리소스를 받아온다
        AssetManager assetManager = getResources().getAssets();

        //inputStream 객체 생성
        InputStream source = null;

        try {
            source = assetManager.open("document.json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(source));
            Gson gson = new Gson();
            LocationGson info = gson.fromJson(reader, MapFragment.LocationGson.class);

            if (info.result != null && info.result.size() > 0) {
                for (int i = 0; i < info.result.size(); i++) {
                    if (info.result.get(i).REFINE_WGS84_LAT != null && info.result.get(i).REFINE_WGS84_LOGT != null) {
                        LatLng latLng = new LatLng(Double.parseDouble(info.result.get(i).REFINE_WGS84_LAT), Double.parseDouble(info.result.get(i).REFINE_WGS84_LOGT));
                        String makerSnippet = "도로명 주소" + String.valueOf(info.result.get(i).REFINE_LOTNO_ADDR) + "\n";
                        mMap.addMarker(new MarkerOptions().position(latLng).title(info.result.get(i).BIZPLC_NM));
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
*/
/*방법 1 _ assets 폴더에 있는 json 파일 활용



        //json 파일 읽어와서 분석하기
        //assets 폴더의 파일을 가져오기 위해
        //AssetsManager(창고 관리자) 얻어오기
        AssetManager assetManager = getActivity().getAssets();

        //assets파일의 Restaurant.json 파일을 읽기 위한 InputStream
        try{
            InputStream is = assetManager.open("jsons/Restaurant.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while ( line!= null){
                buffer.append(line+"\n");
                line = reader.readLine();
            }
            String jsonData = buffer.toString();

            //json 데이터가 []로 시작하는 배열이므로로
           JSONArray jsonArray = new JSONArray(jsonData);

            String s="";

            for(int i = 0; i<jsonArray.length();i++){
                //순서에 맞게 JSON Object를 불러들여온다
                JSONObject jo = jsonArray.getJSONObject(i);

                String BIZPLC_NM = jo.getString("BIZPLC_NM");
                String REFINE_LOTO_ADDR = jo.getString("REFINE_LOTNO_ADDR");
                Double REFINE_WGS84_LAT = jo.getDouble("REFINE_WGS84-LAT");
                Double REFINE_WGS84_LOGT = jo.getDouble("REFINE_WGS_84-LOGT");

                if (REFINE_WGS84_LAT != null && REFINE_WGS84_LOGT != null){
                    LatLng latLng = new LatLng(REFINE_WGS84_LAT, REFINE_WGS84_LOGT);
                    String makerSnippet = "도로명 주소" + String.valueOf("REFINE_LOTNO_ADDR")+"\n";
                    mMap.addMarker(new MarkerOptions().position(latLng).title(BIZPLC_NM));
                }
            }

        }catch(IOException e){e.printStackTrace();}catch(JSONException e){e.printStackTrace();}

*/





/*방법3
        AssetManager assetManager = getActivity().getAssets();
        InputStream info = null;
        byte buf[] = new byte[1024];
        String name = "";
        try

*/

    //방법 4
        AssetManager assetManager = getActivity().getAssets();


        try{
            InputStream is = assetManager.open("document.json");
           InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while(line!=null){
                buffer.append(line+"\n");
                line = reader.readLine();
            }

            String json = buffer.toString();

            JSONObject jsonObject = new JSONObject(json);
            String info = jsonObject.getString("info");
            JSONArray jsonArray = new JSONArray(info);
            for (int i = 0; i<info.length();i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String name = subJsonObject.getString("BIZPLC_NM");
                String addr = subJsonObject.getString("REFINE_LOTNO_ADDR");
                String lat = subJsonObject.getString("REFINE_WGS84_LAT");
                String log = subJsonObject.getString("REFINE_WGS84_LOGT");

                double LAT = Double.parseDouble(lat);
                double LOG = Double.parseDouble(log);

                LatLng latLng = new LatLng(LAT, LOG);
                String makerSnippet = "도로명 주소" + String.valueOf("REFINE_LOTNO_ADDR")+"\n";
                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
            }

        }catch (JSONException | IOException e){
            e.printStackTrace();
        }


        LatLng stand = new LatLng(37.229635, 127.187521);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stand, 15));


    }


}
