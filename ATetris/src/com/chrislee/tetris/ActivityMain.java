package com.chrislee.tetris;
// Author: ChrisLee
// 2010.3

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ActivityMain extends Activity {
	
	public static final int FLAG_NEW_GAME = 0;
	public static final int FLAG_CONTINUE_LAST_GAME = 1;
	
	public static final String FILENAME = "settingInfo";
	public static final String LEVEL = "level";
	public static final String VOICE = "voice";
	
	
	private int mLevel = 1;
	
	private Button btNewgame = null;
	private Button btContinue = null;
	private Button btHelp = null;
	private Button btRank = null;
	private Button btPre = null;
	private Button btNext = null;
	private Button btExit = null;
	
	private TextView tvLevel = null;
	private CheckBox cbVoice = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        btNewgame = (Button)findViewById(R.id.bt_new);
        btContinue = (Button)findViewById(R.id.bt_continue);
        btHelp = (Button)findViewById(R.id.bt_help);
        btRank = (Button)findViewById(R.id.bt_rank);
        btPre = (Button)findViewById(R.id.bt_pre);
        btNext = (Button)findViewById(R.id.bt_next);
        btExit = (Button)findViewById(R.id.bt_exit);
        
        tvLevel = (TextView)findViewById(R.id.tv_speed);
       
        cbVoice = (CheckBox)findViewById(R.id.cb_voice);
        
        btNewgame.setOnClickListener(buttonListener);
        btContinue.setOnClickListener(buttonListener);
        btHelp.setOnClickListener(buttonListener);
        btRank.setOnClickListener(buttonListener);
        btPre.setOnClickListener(buttonListener);
        btNext.setOnClickListener(buttonListener);
        btExit.setOnClickListener(buttonListener);
        restoreSettings();
    }
    
    private Button.OnClickListener buttonListener = new Button.OnClickListener()
    {

		@Override
		public void onClick(View v) {
			if(v == btNewgame)
			{
				Intent intent = new Intent(ActivityMain.this,ActivityGame.class);
				intent.setFlags(FLAG_NEW_GAME);
				intent.putExtra(VOICE,cbVoice.isChecked());
				intent.putExtra(LEVEL,mLevel);
				startActivity(intent);
				return;
			}
			if(v == btContinue)
			{
				Intent intent = new Intent(ActivityMain.this,ActivityGame.class);
				intent.setFlags(FLAG_CONTINUE_LAST_GAME);
				intent.putExtra(VOICE,cbVoice.isChecked());
				startActivity(intent);
				return;
			}
			if(v == btHelp)
			{
				Intent intent = new Intent(ActivityMain.this,ActivityHelp.class);
				startActivity(intent);
				return;
			}
			if(v == btRank)
			{
				Intent intent = new Intent(ActivityMain.this,ActivityRank.class);
				startActivity(intent);
				return;
			}
			if(v == btPre)
			{
				btPre.setBackgroundColor(0xffc0c0c0);
				String s = tvLevel.getText().toString();
				int level = Integer.parseInt(s);
				--level;
				level = (level-1+TetrisView.MAX_LEVEL) % TetrisView.MAX_LEVEL;
				++level;
				s = String.valueOf(level);
				tvLevel.setText(s);
				mLevel = level;
				btPre.setBackgroundColor(0x80cfcfcf);
				return;
			}
			if(v == btNext)
			{
				btNext.setBackgroundColor(0xffc0c0c0);
				String s = tvLevel.getText().toString();
				int level = Integer.parseInt(s);
				--level;
				level = (level+1) % TetrisView.MAX_LEVEL;
				++level;
				s = String.valueOf(level);
				tvLevel.setText(s);
				mLevel = level;
				btNext.setBackgroundColor(0x80cfcfcf);
				return;
			}
			if(v == btExit)
			{
				ActivityMain.this.finish();
			}
		}
    };
    
    private void saveSettings()
    {
    	SharedPreferences settings = getSharedPreferences(FILENAME,0);
    	settings.edit()
    	.putInt(LEVEL,mLevel)
    	.putBoolean(VOICE,cbVoice.isChecked())
    	.commit();
    }
    
    private void restoreSettings()
    {
    	SharedPreferences settings = getSharedPreferences(FILENAME,0);
    	mLevel = settings.getInt(LEVEL,1);
    	boolean hasVoice = settings.getBoolean(VOICE,true);
    	tvLevel.setText(String.valueOf(mLevel));
    	cbVoice.setChecked(hasVoice);
    }
    
    public void onStop()
    {
    	super.onStop();
    	saveSettings();
    }
}