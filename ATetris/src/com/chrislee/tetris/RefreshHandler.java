package com.chrislee.tetris;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

class RefreshHandler extends Handler
{
	final static int MESSAGE_REFRESH = 0xeeeeeeee;
	
	final static int DELAY_MILLIS = 100;
	TetrisView mV = null;
	boolean mIsPaused = false;
	
	public RefreshHandler(TetrisView v)
	{
		super();
		mV = v;
	}
	
	public void handleMessage(Message ms)
	{
		if( !mIsPaused )
		{
			if(ms.what == MESSAGE_REFRESH)
			{
				mV.logic();
				mV.invalidate();
			}
		}
	}
		
	
	public void pause()
	{
		mIsPaused = true;
	}
	

	public void resume()
	{
		mIsPaused = false;
	}
	
}
