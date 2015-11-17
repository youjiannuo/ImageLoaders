package com.yjn.image;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.HashSet;

/**
 * Created by youjiannuo on 15/9/24.
 */
public enum TaskQueue {

    TASK_QUEUE;
    //队列
    private HashMapList<String, HashSet<ImageViewNetwork>> mTasks = new HashMapList<>();

    //当前的页面的标记
    private String mKey = "";


    public void addTask(ImageViewNetwork task) {
        checkKey();
        addTask(mKey, task);
    }

    //添加队列
    private void addTask(String key, ImageViewNetwork task) {
        HashSet<ImageViewNetwork> imageViewNetworks = getImageViewNetwork(key);
        imageViewNetworks.add(task);
    }

    public void removeTask(ImageViewNetwork task) {
        removeTask(mKey, task);
    }

    //移除队列
    private void removeTask(String key, ImageViewNetwork v) {
        HashSet<ImageViewNetwork> imageViewNetworks = getImageViewNetwork(key);
        imageViewNetworks.remove(v);
        System.out.println("剩余任务:key:" + imageViewNetworks.size());
    }

    private HashSet<ImageViewNetwork> getImageViewNetwork(String key) {
        HashSet<ImageViewNetwork> imageViewNetworks = mTasks.get(key);
        if (imageViewNetworks == null) {
            imageViewNetworks = new HashSet<>();
            mTasks.put(key, imageViewNetworks);
        }
        return imageViewNetworks;
    }

    public void pauseAllTask(Activity activity) {
        pauseAllTask(activity.toString());
    }

    public void onCreate(Activity activity) {
        mKey = activity.toString();
    }

    public void onCreate(Fragment fragment) {
        mKey = fragment.toString();
    }

    public void pauseAllTask(Fragment fragment) {
        pauseAllTask(fragment.toString());
    }

    //暂停当前任务
    public void pauseAllTask(String key) {
        Object objects[] = getImageViewNetwork(key).toArray();
        for (int i = 0; i < objects.length; i++) {
            //关闭任务
            ((ImageViewNetwork) objects[i]).stopTask();
        }
    }

    public void resumeAllTask(Activity activity) {
        resumeAllTask(activity.toString());
    }

    public void resumeAllTask(Fragment fragment) {
        resumeAllTask(fragment.toString());
    }

    //开始所有任务
    public void resumeAllTask(String key) {
        mKey = key;
        Object objects[] = getImageViewNetwork(key).toArray();
        for (int i = 0; i < objects.length; i++) {
            ((ImageViewNetwork) objects[i]).startTask();
        }
    }

    public void destroyAllTask(Activity activity) {
        destroyAllTask(activity.toString());
    }

    public void destroyAllTask(Fragment fragment) {
        destroyAllTask(fragment.toString());
    }

    //销毁当前activity所有的任务
    public void destroyAllTask(String key) {
        mTasks.remove(key);
    }


    private void checkKey() {
        if (mKey == null || mKey.length() == 0) {
            throw new RuntimeException("必须要把TaskQueue里面的方法onCreate放入到Activity或者Fragment的onCreate里面去");
        }
    }


}
