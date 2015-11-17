package com.yjn.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youjiannuo on 15/9/1.
 */
public class HashMapList<K, V> {

    private List<K> mKeys = new ArrayList<>();
    private Map<K, V> mHashMap = new HashMap<>();

    public synchronized V get(K key) {
        if (key == null || key.equals("")) {
            throw new NullPointerException("key is null || key.lenght() == 0");
        }
        return mHashMap.get(key);
    }

    public synchronized V get(int index) {
        checkIndex(index);
        K key = mKeys.get(index);
        return mHashMap.get(key);
    }

    public synchronized K getKey(int index) {
        return mKeys.get(index);
    }

    public synchronized Object[] getKeyArray(){
        return  mKeys.toArray();
    }


    public synchronized V getLastItem() {
        return get(size() - 1);
    }

    public synchronized void put(K key, V value) {
        if (key == null || value == null) {
            throw new RuntimeException("key == null 0r value == null");
        }
        boolean is = true;
        for (int i = 0; i < mKeys.size(); i++) {
            if (mKeys.get(i).equals(key)) {
                is = false;
                break;
            }
        }
        if (is) {
            mKeys.add(key);
        }
        mHashMap.put(key, value);
    }

    public synchronized int size() {
        return mHashMap.size();
    }

    public synchronized void remove(int index) {
        checkIndex(index);
        remove(mKeys.remove(index));
    }

    public synchronized void remove(K key) {
        while (mKeys.remove(key)) ;
        mHashMap.remove(key);
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= mKeys.size()) {
            throw new IndexOutOfBoundsException("index must > 0 || < List.Size()");
        }
    }

    public Map<K, V> getMap() {
        return mHashMap;
    }


    public synchronized void clear() {
        mHashMap.clear();
        mKeys.clear();
    }

}
