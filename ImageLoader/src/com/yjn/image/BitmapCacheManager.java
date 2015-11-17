package com.yjn.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import java.io.File;

/**
 * Created by youjiannuo on 15/9/2.
 */
public enum BitmapCacheManager {

    BITMAP_CACHE_MANAGER;

    //缓存图片机制
    private ImageCache mImageCache = ImageCache.build();

    //只是从缓存里面获取照片
    public Bitmap getBitmapFromCacheAndNative(NetworkPhotoTask task) {
        Bitmap bitmap = getCacheBitmap(task.getPhotoKey());
        if (bitmap == null) {
            bitmap = getNativeBitmap(task);
        }
        return bitmap;
    }

    public Bitmap getCacheBitmap(String key) {
        return mImageCache.getCacheItem(key);
    }

    public void addCacheBitmap(String key, Bitmap bitmap) {
        mImageCache.addCacheItem(key, bitmap);
    }

    //从本地获取图片
    public Bitmap getNativeBitmap(NetworkPhotoTask task) {
        int sampleSize = getSampleSize(task);
        Util.println("缩放比例" + sampleSize);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(task.getPhotoName(), options);
        addCacheBitmap(task.getPhotoKey(), bitmap);
        return bitmap;
    }

    //判断本地是否有照片
    public boolean isFileExit(NetworkPhotoTask task) {
        String fileName = task.getPhotoName();
        boolean is = new File(fileName).exists();

        if (is) {
            //可能当前的照片是不完整的
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(task.getPhotoName(), options);
            is = options.outHeight != -1 && options.outWidth != -1;
        }
        return is;
    }

    private int getSampleSize(NetworkPhotoTask task) {
        final int height = task.height;
        final int width = task.width;
        ViewGroup.LayoutParams params = null;
        if (task.v != null) {
            params = task.v.getLayoutParams();
        }
        if (params == null) {
            params = new ViewGroup.LayoutParams(0, 0);
        }
        final int viewHeight = params.height;
        final int viewWidth = params.width;
        final int ViewMaxHeight = task.viewMaxHeight;
        final int ViewMaxWidth = task.viewMaxWidth;

        int h;
        int w;

        if (task.scalingSize > 0) {
            //缩放
            return task.scalingSize;
        } else if (height != -1 || width != -1) {
            w = width;
            h = height;
        } else if (ViewMaxWidth != Integer.MAX_VALUE || ViewMaxHeight != Integer.MAX_VALUE) {
            w = ViewMaxWidth;
            h = ViewMaxHeight;
        } else {
            w = viewWidth <= 0 ? 1 : viewWidth;
            h = viewHeight <= 0 ? 1 : viewHeight;
        }

        return Util.getSampleSize(task.getPhotoName(), w, h);
    }

    public void clear() {
        mImageCache.clear();
    }

    public ImageCache getmImageCache() {
        return mImageCache;
    }


}

