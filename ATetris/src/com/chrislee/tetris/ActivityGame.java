package com.chrislee.tetris;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class ActivityGame extends Activity {

	private static final String TAG = "ActivityGame";
	TetrisView mTetrisView = null;
	
//	private static final int DIALOG_ID = 1;
	
	public void onCreate(Bundle saved)
	{
		super.onCreate(saved);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		init();
	}
	
	private void init()
	{
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
//			builder.setTitle("��ϲ����ǰ����");
//			return builder.create();
//		}
//		return null;
//	}

}
