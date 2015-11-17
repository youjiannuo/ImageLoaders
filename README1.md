# ImageLoaders
这个图片库其实实现一些基本的功能，还另外实现如下功能：</br>
1、如果当前的Activity或者Fragment在加载大量的图片的时候，如果跳转到别的Activity，或者退出当前Activity，都会停止当前加载的图片，如果回到当前的Activity，就会重新加载当前图片。
2、添加了图片下载进度，只需要实现NetworkPhotoTask.OnLoadImageProgress接口。</br>
3、添加了下载图片成功，只需要实现NetworkPhotoTask.OnLoaderImageCallback接口。</br>
4、添加了一些对图片圆角和圆形处理操作图片是否添加添加到缓冲图片里面去。</br>
</br>
下面简单介绍如何使用：

##因为我是对ImageView继承编写，所有这里面需要使用ImageViewNetwork。</br>
    <com.yjn.image.ImageViewNetwork 
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

##现在就是使用代码来设置一些可以配置的参数，我们可以查阅NetworkPhotoTask里面的参数说明，需要达到什么效果只要需要配置这个类，代码简单操作：
    ImageViewNetwork imageView = (ImageViewNetwork) findViewById(R.id.imageView);
	NetworkPhotoTask task = NetworkPhotoTask.build();
	task.url = "http://www.sj88.com/attachments/201501/04/16/ph1zqr257.jpg";
	task.width = width;
	task.height = height;
	//开始加载图片显示的
	task.startDrawId = R.drawable.ic_launcher;
	//图片加载失败显示的照片
	task.errorDrawId = R.drawable.ic_launcher;
	imageView.setImageParams(task);
##在使用的时候特别要注意！！！</br>主要在Activity或者Fragment的方法onCreate 、onResume、onPause和onDestory这个几个方法里面添加如下代码

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TaskQueue.TASK_QUEUE.resumeAllTask(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		TaskQueue.TASK_QUEUE.pauseAllTask(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		TaskQueue.TASK_QUEUE.destroyAllTask(this);
	}
上述代码不添加，会抛出异常。
居然的属性个功能，可以查看类NetworkPhotoTask里面的参数来使用,里面的注释全都是中文，如果出现乱码，需要把代码转换成UTF-8。

</br>
如果发现程序有bug或者更好的建议,可以和我联系了哦，QQ:382034324
