package com.yjn.image;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by youjiannuo on 15/9/2.
 */
public class Util {

    public static int getSampleSize(String fileName, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, opts);
        return getSampleSize(opts, width, height);
    }

    public static int getSampleSize(BitmapFactory.Options opts, int width, int height) {
        return getSampleSize(opts.outWidth, opts.outHeight, width, height);
    }

    public static int getSampleSize(int bitmapWidth, int bitmapHeight, int width, int height) {
        int sampleSize = 1;

        if (bitmapWidth > width || bitmapHeight > height) {
            final int wSize = Math.round((float) bitmapWidth / (float) width);
            final int hSize = Math.round((float) bitmapHeight / (float) height);
            sampleSize = wSize > hSize ? wSize : hSize;
        }
        return sampleSize < 1 ? 1 : sampleSize;
    }

    public static void deleteFile(String name){
        new File(name).delete();
    }

    public static void createFile(String url){

        File f = new File(url.substring(0, getFromUrlToFileNamePostion(url)));
        if(!f.exists()){
            f.mkdirs();
        }
        f = new File(url);
        if(!f.isFile()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings("deprecation")
    public static String getFromURLToFileName(String url) {

        int a = getFromUrlToFileNamePostion(url);
        if(a == -1)  return new Date().getSeconds() + "";

        return url.substring(a + 1, url.length());

    }

    private static int getFromUrlToFileNamePostion(String url){
        if (url == null || url.trim().length() == 0) return -1;

        int a = url.lastIndexOf('\\');
        int b = url.lastIndexOf('/');

        return a > b ? a : b;
    }

    //��ȡû�к�׺���?
    @SuppressWarnings("deprecation")
    private static String getFromURLFileNameNotSuffixs(String name){
        if(name == null || name.trim().length() == 0) return ""+new Date().getSeconds();
        int i = name.lastIndexOf('.');
        return i < 0 ? name : name.substring(0 , i);
    }



    public static String getFromURLFileNameNotSuffix(String url) {
        if (url == null || url.length() == 0) return "0";
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    
    /**
     * 检查SDCard是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void println(String msg) {
        Log.i("imageLoader", msg);
    }


}
