package com.yjn.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache extends LruCache<String, Bitmap> implements Cache<Bitmap> {


    public ImageCache() {
        super((int) (Runtime.getRuntime().maxMemory() / 8));
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("NewApi")
    @Override
    protected int sizeOf(String key, Bitmap value) {
        // TODO Auto-generated method stub
        return value.getByteCount();
    }

    @Override
    public void addCacheItem(String key, Bitmap item) {
        // TODO Auto-generated method stub
        if (key == null || key.length() == 0 || item == null) return;
        put(key, item);
    }

    @Override
    public Bitmap getCacheItem(String key) {
        // TODO Auto-generated method stub
        return get(key);
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        if (size() > 0) {
            evictAll();
        }
    }


    @Override
    public void Recycling() {
        // TODO Auto-generated method stub

    }

    @Override
    public void removes(String key) {
        // TODO Auto-generated method stub
        Bitmap bm = super.remove(key);
        if (bm != null)
            bm.recycle();
    }

    public static ImageCache build() {
        return new ImageCache();
    }


}
