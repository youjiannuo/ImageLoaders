package com.yjn.imageloader;



import com.yjn.image.ImageViewNetwork;
import com.yjn.image.NetworkPhotoTask;
import com.yjn.image.TaskQueue;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TaskQueue.TASK_QUEUE.onCreate(this);
		WindowManager wm = this.getWindowManager();
	    int width = wm.getDefaultDisplay().getWidth();
	    int height = wm.getDefaultDisplay().getHeight();
		
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
	}

	
	
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
	
}
