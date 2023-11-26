package com.example.proj2_and_2019202085;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Article extends AppCompatActivity {

    private ImageView articleImg;
    private TextView articleTitle;
    private TextView articleComment;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        backbtn = findViewById(R.id.button);

        // 인텐트에서 데이터 추출
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String comment = intent.getStringExtra("comment");
        byte[] imageBytes = intent.getByteArrayExtra("image");
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // 뷰 초기화
        articleImg = findViewById(R.id.articleImg);
        articleTitle = findViewById(R.id.articleTitle);
        articleComment = findViewById(R.id.articleComment);

        // 데이터 설정
        articleTitle.setText(title);
        articleComment.setText(comment);
        articleImg.setImageBitmap(imageBitmap);

        // backbtn 클릭 리스너 설정
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 현재 액티비티 종료
            }
        });
    }
    @Override
    public void onBackPressed() {
        // 뒤로가기 버튼 무시
    }

}
