package com.example.proj2_and_2019202085;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiService {
    @GET("grid-data")  // 스프링 서버의 "/grid-data" 엔드포인트에 GET 요청을 보냄
    Call<List<Gallery>> getGridData();
}
