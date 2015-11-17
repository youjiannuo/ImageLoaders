package com.yjn.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.Message;


import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class AccessNetwork {

    private HttpExecute mHttpExecute;
    private NetworkPhotoTask mTask = null;
    private Asy mAsy = null;

    public AccessNetwork() {
        mHttpExecute = new HttpExecute();
        mAsy = new Asy();
    }


    /*
     * get bitmap from network
     */
    public String getBitmapFromNetwork(String filename, String url, AsyPublish asy, int count) {
        if (url == null || url.trim().length() == 0) {
            Util.println("Image file has been damaged");
            return null;
        }
        long total = 1;
        Util.println("start downloading images:" + url);
        InputStream in = null;
        FileOutputStream out = null;
        //is delete name
        int progress = 0;
        try {
            HttpExecute.NetworkTask task = new HttpExecute.NetworkTask();
            task.method = HttpExecute.METHOD_GET;
            task.url = url;
            HttpEntity httpEntity = mHttpExecute.getBitmapInputStream(task);
            if (httpEntity != null) {
                in = httpEntity.getContent();
                total = httpEntity.getContentLength();
            }
            //delete have image file
            Util.deleteFile(filename);
            Util.createFile(filename);

            out = new FileOutputStream(new File(filename));
            byte[] buf = new byte[8192];
            int seg = 0;
            long current = 0;

            while (in != null && (seg = in.read(buf)) != -1 && isNext(asy)) {
                out.write(buf, 0, seg);
                current += seg;
                progress = (int) ((float) current / (float) total * 100f);
                asy.publishProgressDevelopment(progress);
            }
            if (seg == -1){
               
                progress = 100;
            }

        } catch (Exception e) {
            e.printStackTrace();
            delete(filename);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        if (progress != 100 && !isNext(asy)) {
            delete(filename);
            Util.println("Successful mission cancel the download");
            return null;
        }
        if (progress == 100) {
            Util.println("image file download success");
        } else {
            delete(filename);
            Util.println("image file download fail");
            filename = null;
        }
        return filename;
    }


    private boolean isNext(AsyPublish asy) {
        return asy != null && !asy.isCancel();
    }

    private void delete(String fileName) {
        Util.println("delete image file:" + fileName);
        Util.deleteFile(fileName);
    }

    public void close() {
        if (mHttpExecute != null) {
            mHttpExecute.close();
        }
    }

    public void startLoadImage(NetworkPhotoTask task) {
        boolean is = BitmapCacheManager.BITMAP_CACHE_MANAGER.isFileExit(task);
        if (is){
            if (task.onLoaderImageCallback != null){
                task.onLoaderImageCallback.onCallback(task.getPhotoName());
            }
            return;
        }

        this.mTask = task;
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            final String fileName = getBitmapFromNetwork(mTask.getPhotoName(), mTask.url, mAsy, 0);
            if (mTask.onLoaderImageCallback != null) {
                new android.os.Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mTask.onLoaderImageCallback.onCallback(fileName);
                    }
                }.sendEmptyMessage(0);

            }
        }
    };

    public void stop() {
        if (mHttpExecute != null) {
            mHttpExecute.close();
        }
        if (mAsy != null)
            mAsy.stop = true;
    }

    public boolean isStop(){
        if (mAsy == null) return true;
        return  mAsy.stop ;
    }


    class Asy implements AsyPublish {
        public boolean stop = false;

        @Override
        public void publishProgressDevelopment(final Integer... progress) {
            if (progress != null && progress.length > 0) {
                new android.os.Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (mTask.onLoadImageProgress != null) {
                            mTask.onLoadImageProgress.onProgress(msg.what, null);
                        }
                    }
                }.sendEmptyMessage(progress[0]);
            }
        }

        @Override
        public boolean isCancel() {
            return stop;
        }
    }

}

