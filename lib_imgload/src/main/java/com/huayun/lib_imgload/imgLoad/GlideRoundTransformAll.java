package com.huayun.lib_imgload.imgLoad;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;


public class GlideRoundTransformAll extends BitmapTransformation {
    private static float radius_lt = 0f;
    private static float radius_rt = 0f;
    private static float radius_lb = 0f;
    private static float radius_rb = 0f;

    public GlideRoundTransformAll(int rlt, int rrt, int rlb, int rrb) {
        super();
        radius_lt = Resources.getSystem().getDisplayMetrics().density * rlt;
        radius_rt = Resources.getSystem().getDisplayMetrics().density * rrt;
        radius_lb = Resources.getSystem().getDisplayMetrics().density * rlb;
        radius_rb = Resources.getSystem().getDisplayMetrics().density * rrb;
    }


    @Override
    protected Bitmap transform( BitmapPool pool,  Bitmap toTransform, int outWidth, int outHeight) {
        //变换的时候裁切
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }


    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius_lt, radius_rt, paint);
        //只保留左上角、右上角圆角（注释掉以下两行，则四个角为圆角）
        RectF rectRound = new RectF(0f, 100f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectRound, radius_lb, radius_rb, paint);
//        canvas.drawRect(rectRound, paint);
        return result;
    }

}