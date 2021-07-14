package com.example.caredog;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//implements OnMapReadyCallback 구글맵 준비되면 호출
public class GooglemapActivity extends AppCompatActivity implements OnMapReadyCallback {

//    private FragmentManager fragmentManager;//전역변수 설정
//    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

//        fragmentManager = getFragmentManager();
//        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
//        mapFragment.getMapAsync(this);

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton map_back = (ImageButton) findViewById(R.id.map_back);
        map_back.setOnClickListener(view -> {
            finish();
        });
    }

    //구글맵 준비되면 호출
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(37.392103, 126.919674);//괄호안에 호출받은 위도경도값 (현재는 안양대 위치)
        MarkerOptions markerOptions = new MarkerOptions();//마커 생성
        markerOptions.title("현위치");//마커 이름
        markerOptions.snippet("세부내용");//세부내용 적을수 있음
        markerOptions.position(location);//위의 위도경도값 갖고옴
        googleMap.addMarker(markerOptions);//셋팅한 마커옵션 추가

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));//숫자 키울수록 확대해서 보임
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));//애니메이션효과

    }
}