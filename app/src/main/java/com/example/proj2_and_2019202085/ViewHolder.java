package com.example.proj2_and_2019202085;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

public class ViewHolder implements View.OnClickListener {
    public String titleText;
    public String commentText;
    public ImageButton imageButton;

    public void clickListner()
    {
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 해당 아이템의 데이터를 가져오기
        String title = titleText;
        String comment = commentText;
        Drawable imageDrawable = imageButton.getDrawable();

        // 데이터를 Intent로 전달하기
        Context context = imageButton.getContext();
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
}


