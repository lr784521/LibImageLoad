package com.huayun.lib_imgload.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

//下载网络图片转为Bitmap并加载
public class DownloadUrlBitmap extends AsyncTask<String, Void, Bitmap>{
    private ImageView imageView;
    private Bitmap bitmap;
    public DownloadUrlBitmap(ImageView imageView) {
        this.imageView=imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return loadImageFromNetwork(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.bitmap=bitmap;
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
    private Bitmap loadImageFromNetwork(String url) {
        //得到可用的图片
        Bitmap bitmap = simpleNetworkImage(url);
        if (bitmap == null) {
        }
        return bitmap;
    }


    public Bitmap simpleNetworkImage(String url) {
        Bitmap pngBM = null;
        try {
            URL picUrl = new URL(url);
            pngBM = BitmapFactory.decodeStream(picUrl.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pngBM;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
