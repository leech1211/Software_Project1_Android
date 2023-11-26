package com.example.proj2_and_2019202085;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {
    private Context context;
    private JSONArray jsonData;
//    private ImageView imgView;
//
//    public ImageView getImgView() {
//        return imgView;
//    }
//
//    public void setImgView(ImageView imgView) {
//        this.imgView = imgView;
//    }

    public JSONAdapter(Context context) {
        this.context = context;
        jsonData = new JSONArray();
    }

    public void setJsonData(JSONArray jsonArray) {
        jsonData = jsonArray;
    }

    @Override
    public int getCount() {
        return jsonData.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return jsonData.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            viewHolder = new ViewHolder();
            //viewHolder.titleTextView = convertView.findViewById(R.id.titleTextView);
            //viewHolder.commentTextView = convertView.findViewById(R.id.commentTextView);
            viewHolder.imageButton = convertView.findViewById(R.id.imgView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // JSON 데이터에서 필요한 정보를 추출하여 텍스트뷰에 표시
        try {
            JSONObject item = jsonData.getJSONObject(position);
            String title = item.getString("title");
            String comment = item.getString("comment");
            String imgpath = item.getString("imagePath");
            viewHolder.titleText = title;
            viewHolder.commentText = comment;

            // 이미지 URL 생성
            String baseUrl = "http://10.0.2.2:8080/";
            String imageUrl = baseUrl + imgpath;

            // Picasso 라이브러리를 사용하여 이미지 로드
            Picasso.get()
                    .load(imageUrl)
                    .into(viewHolder.imageButton);

            // 패딩 설정
            int padding = context.getResources().getDimensionPixelSize(R.dimen.item_padding);
            //viewHolder.titleTextView.setPadding(padding, padding, padding, padding);
            //viewHolder.commentTextView.setPadding(padding, padding, padding, padding);
            viewHolder.imageButton.setPadding(padding, padding, padding, padding);
            viewHolder.clickListner();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }




}
