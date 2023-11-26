package com.example.proj2_and_2019202085;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScrollActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private BottomNavigationView navigationView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_main);

        linearLayout = findViewById(R.id.scrollview1);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_1:
                        // TODO: 첫 번째 항목 클릭 시 전환할 프래그먼트 또는 뷰 처리
                        break;
                    case R.id.navigation_2:
                        // TODO: 두 번째 항목 클릭 시 전환할 프래그먼트 또는 뷰 처리
                        finish();
                        break;
                    case R.id.navigation_3:
                        // TODO: 세 번째 항목 클릭 시 전환할 프래그먼트 또는 뷰 처리
                        break;
                }
                return true;
            }
        });

        // Retrofit 인스턴스 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // 스프링 서버의 기본 URL을 입력해야 합니다.
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 스프링 서버에 API 요청하기 위한 인터페이스 생성
        MyApiService apiService = retrofit.create(MyApiService.class);

        // API 요청
        Call<List<Gallery>> call = apiService.getGridData();
        call.enqueue(new Callback<List<Gallery>>() {
            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                if (response.isSuccessful()) {
                    List<Gallery> myDataList = response.body();
                    if (myDataList != null) {
                        for (Gallery myData : myDataList) {
                            TextView textView = new TextView(ScrollActivity.this);
                            textView.setText(myData.getTitle());
                            linearLayout.addView(textView);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Gallery>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
