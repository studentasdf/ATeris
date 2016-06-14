package com.chrislee.tetris;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class ActivityGame extends Activity {

	private static final String TAG = "ActivityGame";
	private TetrisView mTetrisView = null;
	
//	private static final int DIALOG_ID = 1;
	
	public void onCreate(Bundle saved)
	{
		super.onCreate(saved);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

		init();
	}
	
	private void init()
	{
		WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay(); 
        Point outSize=new Point();
        display.getSize(outSize);
        TetrisView.SCREEN_WIDTH = outSize.x;
        TetrisView.SCREEN_HEIGHT = outSize.y; 
        
		mTetrisView = new TetrisView(this);
		Intent intent = getIntent();
		int level = intent.getIntExtra(ActivityMain.LEVEL,1);
		mTetrisView.setLevel(level);
		int flag = intent.getFlags();
		if(flag == ActivityMain.FLAG_CONTINUE_LAST_GAME)
		{
			mTetrisView.restoreGame();
		}
		// voice setting influence last game
		boolean isVoice = intent.getBooleanExtra(ActivityMain.VOICE,true);
		mTetrisView.setVoice(isVoice);
		setContentView(mTetrisView);
	}
	
	public void onPause()
	{
//		ranking();
		mTetrisView.onPause();
		super.onPause();
		
	}
	
	public void onResume()
	{
		super.onResume();
		mTetrisView.onResume();
		
		
	}
	
	public void onStop()
	{
		super.onStop();
		mTetrisView.saveGame();
		mTetrisView.freeResources();
		
	}
	
//	public void ranking()
//	{
//		showDialog(DIALOG_ID);
//		Log.i(TAG,"ranking now");
//	}
	
//	protected Dialog onCreateDialog(int id)
//	{
//		if(id == DIALOG_ID)
//		{
//			Builder builder = new AlertDialog.Builder(this);
//			builder.setIcon(R.drawable.icon);
//			builder.setTitle("恭喜进入前三名");
//			return builder.create();
//		}
//		return null;
//	}

}
