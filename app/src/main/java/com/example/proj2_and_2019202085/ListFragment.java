package com.example.proj2_and_2019202085;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    private View view;
    private LinearLayout linearLayout;
    //private ImageView imageView;

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        linearLayout = view.findViewById(R.id.linearLayout);
        //imageView = view.findViewById(R.id.ListHideImg);
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
                        for (Gallery gallery : myDataList) {
                            String title = gallery.getTitle();
                            String comment = gallery.getComment();
                            String imgpath =  gallery.getImagePath();

                            // 이미지 URL 생성
                            String baseUrl = "http://10.0.2.2:8080/";
                            String imageUrl = baseUrl + imgpath;

                            // Picasso 라이브러리를 사용하여 이미지 로드
                            ImageView imageView = new ImageView(requireContext());
                            Picasso.get()
                                    .load(imageUrl)
                                    .into(imageView);

                            addTextView(title,comment,imageView);
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

    private void addTextView(String title,String comment,ImageView imgview) {
        Button textButton = new Button(requireContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        int margin = 5;
        layoutParams.setMargins(0, margin, 0, margin);
        textButton.setLayoutParams(layoutParams);
        textButton.setText(title);
        textButton.setGravity(Gravity.LEFT);
        textButton.setPadding(16,30,16,30);
        textButton.setBackgroundColor(Color.WHITE);
        textButton.setHeight(210);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 해당 아이템의 데이터를 가져오기

                Drawable imageDrawable = imgview.getDrawable();

                // 데이터를 Intent로 전달하기
                Context context = imgview.getContext();
                Intent intent = new Intent(context, Article.class);
                intent.putExtra("title", title);
                intent.putExtra("comment", comment);

                // 이미지를 Bitmap으로 변환하여 전달
                if (imageDrawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) imageDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray);
                }

                // ArticleActivity 시작
                context.startActivity(intent);
            }
        });

        linearLayout.addView(textButton);

    }

}

