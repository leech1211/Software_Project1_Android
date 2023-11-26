package com.example.proj2_and_2019202085;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageFragment extends Fragment {

    private View view;
    private GridView gridView;
    private JSONAdapter jsonAdapter;

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);
        gridView = view.findViewById(R.id.gridView1);
        jsonAdapter = new JSONAdapter(requireContext());
        gridView.setAdapter(jsonAdapter);
        fetchDataFromServer();
        return view;
    }

    private void fetchDataFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiService apiService = retrofit.create(MyApiService.class);
        Call<List<Gallery>> call = apiService.getGridData();
        call.enqueue(new Callback<List<Gallery>>() {
            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                if (response.isSuccessful()) {
                    List<Gallery> myDataList = response.body();
                    if (myDataList != null) {
                        JSONArray jsonArray = new JSONArray();
                        try {
                            for (Gallery myData : myDataList) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("id", myData.getId());
                                jsonObject.put("title", myData.getTitle());
                                jsonObject.put("comment", myData.getComment());
                                jsonObject.put("imageName", myData.getImageName());
                                jsonObject.put("imagePath", myData.getImagePath());
                                jsonArray.put(jsonObject);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonAdapter.setJsonData(jsonArray);
                        jsonAdapter.notifyDataSetChanged();
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
