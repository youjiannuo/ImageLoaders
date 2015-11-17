package com.yjn.image;


import android.os.Environment;
import android.view.View;


/**
 * get image task
 *
 * @author youjiannuo
 *         set encoding = "UTF-8"
 */
public class NetworkPhotoTask {

    //文件名称
    public String fileName;

    public View v = null;
    /**
     * 加载照片的网地址获取本地图片地址
     */
    public String url = null;

    /**
     * 读取照片是从SDCard获取
     */
    public boolean isGetImageFromSdCard = false;

    /**
     * 加入到缓存里面的标示
     */
    public String key = "";

    /**
     * 是否将获取的照片设置为背景图片
     * 如果是false，需要将View转换为ImageView,
     * 如果是true,就直接将图片设置为控件的背景图片
     */
    public boolean isSetBitmapBackground = false;

    /**
     * 加载照片错误的时候，�?要显示的照片
     * <i>可以为资源文件， 也可以是bitmap</i>
     */
    public int errorDrawId = -1;
    /**
     * 加载照片�?始的时�?�，�?要显示文件的照片
     * <i>可以为资源文件， 也可以是bitmap</i>
     */
    public int startDrawId = -1;

    /**
     * 要加载照片的高，自动对照片进行缩�??
     */
    public int height = -1;
    /**
     * 要加载照片的高，自动对照片进行缩�??
     */
    public int width = -1;

    /**
     * 控件设置的最大宽度
     */
    public int viewMaxWidth = Integer.MAX_VALUE;

    /**
     * 控件设置的最大高度
     */
    public int viewMaxHeight = Integer.MAX_VALUE;

    /**
     * 照片的缩放比例，该设置为5 ，照片就会缩小为原来5
     */
    public int scalingSize = 0;

    /**
     * 是否需要把图片的方向移到正确位置
     */
    public boolean isRightDirection = false;

    /**
     * 如果把这个参数设置为false ,那么就是不把当前的照片加入到缓存里面，那么当我们退出当前的Activity的时候，
     * 调用recycleBitmap方法来释放Bitmap对象
     */
    public boolean isAddToCache = true;

    /**
     * 是否要从缓存里面获取照片
     */
    public boolean isGetImageFromCache = true;

    /**
     * 回调方法，该类是个接口，可以回调从网络上或本地获取照片成功，
     */
    public OnLoaderImageCallback onLoaderImageCallback = null;

    /**
     * 图片下载进度回调方法
     */
    public OnLoadImageProgress onLoadImageProgress = null;
    

    /**
     * 如果在设置圆形和圆角的时候，设置圆形的优先级更好
     */

    /**
     * 是否将图片设置为圆形
     * 如果设置为true就是要把图片设置为圆
     */
    public boolean isSetRounded = false;

    /**
     * 设置图片的圆角的大小
     */
    public int roundedCornersSize = -1;


    NetworkPhotoTask() {
    }

    NetworkPhotoTask(NetworkPhotoTask p) {
        // TODO Auto-generated constructor stub
        if (p == null) return;
        fileName = p.fileName;
        url = p.url;
        errorDrawId = p.errorDrawId;
        startDrawId = p.startDrawId;
        height = p.height;
        width = p.width;
        scalingSize = p.scalingSize;
        onLoaderImageCallback = p.onLoaderImageCallback;
        isAddToCache = p.isAddToCache;
        isGetImageFromCache = p.isGetImageFromCache;
        fileName = p.fileName;
    }

    /**
     * 实例�?? NetworkPhotoTask
     *
     * @param params
     * @return
     */
    public static NetworkPhotoTask build(NetworkPhotoTask params) {
        return new NetworkPhotoTask(params);
    }

    /**
     * 获取照片的名字，不是照片的URL地址
     *
     * @return
     */
    public String getPhotoName() {
        if (isGetImageFromSdCard) return fileName;
        fileName = fileName != null && fileName.trim().length() != 0 ?
                fileName : Util.getFromURLFileNameNotSuffix(url);
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/yjn/cache/image/" + fileName;
    }

    public String getPhotoKey() {
        if (key != null && key.length() != 0) return key;
        getPhotoName();
        return key = fileName + "w" + width + "h" + height + "border" + roundedCornersSize + "rounded" + isSetRounded + "wm" + viewMaxWidth + "wh" + viewMaxHeight;
    }


    /**
     * 实例�?? NetworkPhotoTask
     *
     * @param
     * @return
     */
    public static NetworkPhotoTask build() {
        return new NetworkPhotoTask();
    }


    @Override
    public boolean equals(Object object) {
        if (object instanceof NetworkPhotoTask) {
            NetworkPhotoTask task = (NetworkPhotoTask) object;
            final String url = task.url;
            final String key = task.getPhotoKey();
            if (url.equals(this.url) && getPhotoKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}

