package com.yjn.imageloader;

import android.support.v7.app.ActionBarActivity;

import com.yjn.image.ImageViewNetwork;
import com.yjn.image.NetworkPhotoTask;
import com.yjn.image.TaskQueue;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
