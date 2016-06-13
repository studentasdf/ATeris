package com.chrislee.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


// tile是指如条形，方形，Z形的组件

public class TileView{
	int[][] mTile = new int[4][4];
	Random mRand = null;
	int mColor = 1;
	int mShape = 0;
	int mOffsetX = (Court.COURT_WIDTH-4)/2+1;
	int mOffsetY = 0;
	
	private Context mContext = null;
	private ResourceStore mRs = null;
	
	public TileView(Context context) {
		mContext = context;
		
		mRs = new ResourceStore(context);
		
		init();
		Log.i("tetris","init in TileView OK");
		// TODO Auto-generated constructor stub
	}
	
	private void init()
	{
		mRand = new Random();
		mShape = Math.abs(mRand.nextInt() % 28);
		
		mColor = Math.abs(mRand.nextInt() % 8) + 1;
		
		if(mTile == null)
		{
			return;
		}
		int i,j;
		for(i = 0;i<4;i++)
		{
			for(j = 0;j<4;j++)
			{
				mTile[i][j] = TileStore.store[mShape][i][j];
			}
		}	
	}
	

	public boolean rotateOnCourt(Court court) {
		int tempX = 0, tempY = 0;
		int tempShape;
		int[][] tempTile = new int[4][4];

		// 向右转
		tempShape = mShape;
		if (tempShape % 4 > 0) {
			tempShape--;
		} else {
			tempShape += 3;
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tempTile[i][j] = TileStore.store[tempShape][i][j];
			}
		}

		tempX = mOffsetX;
		tempY = mOffsetY;
		boolean canTurn = false;
		
		if( court.availableForTile(tempTile,tempX,tempY) )
		{
			canTurn = true;
		}	
		else if(court.availableForTile(tempTile,tempX-1,tempY) )
		{
			canTurn = true;
			tempX--;
		}
		else if(court.availableForTile(tempTile,tempX-2,tempY) )
		{
			canTurn =true;
			tempX -=2;
		}
		else if(court.availableForTile(tempTile,tempX+1,tempY) )
		{
			canTurn = true;
			tempX++;
		}
		else if(court.availableForTile(tempTile,tempX+2,tempY) )
		{
			canTurn = true;
			tempX += 2;
		}
		
		if (canTurn) {
			mShape = tempShape;
			mOffsetX = tempX;
			mOffsetY = tempY;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					mTile[i][j] = tempTile[i][j];
				}
			}
			return true;
		}
		return false;
	}

	
	public boolean moveRightOnCourt(Court court) {
		Log.i("tetris","here is moveRightOnCourt");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (mTile[i][j] != 0) {
					if ( !court.isSpace(mOffsetX + i + 1, mOffsetY + j) ) {
						return false;
					}
				}
			}
		}
		++mOffsetX;
		return true;
	}
	
	public boolean moveLeftOnCourt(Court court) {
		int i,j;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (mTile[i][j] != 0) {
					if (!court.isSpace(mOffsetX + i - 1, mOffsetY + j)) {
						return false;
					}
				}
			}
		}
		mOffsetX--;
		return true;
	}
	
	public boolean moveDownOnCourt(Court court) {
		int i,j;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (mTile[i][j] != 0) {
					if (!court.isSpace(mOffsetX + i, mOffsetY + j + 1)
							|| isUnderBaseline(mOffsetY+j+1) ) {
						return false;
					}
				}
			}
		}
		mOffsetY++;
		return true;
	}
	

	
	public boolean fastDropOnCourt(Court court) {
		int i,j,k;
		int step = Court.COURT_HEIGHT;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (mTile[i][j] != 0) {
					for (k = mOffsetY + j; k < Court.COURT_HEIGHT; k++) {
						if (!court.isSpace(mOffsetX + i, k + 1)
								|| isUnderBaseline(k+1) ) {
							if (step > k - mOffsetY - j) {
								step = k - mOffsetY - j;
							}
						}
					}
				}
			}
		}
		mOffsetY += step;
		if (step > 0)
			return true;
		return false;
	}
	
	private boolean isUnderBaseline(int posY )
	{
		if(posY >= Court.COURT_HEIGHT)
			return true;
		return false;
		
	}
	
	public int getOffsetX()
	{
		return mOffsetX;
	}
	
	public void setOffsetX(int offsetX)
	{
		mOffsetX = offsetX;
	}
	
	public int getOffsetY()
	{
		return mOffsetY;
	}
	
	public void setOffsetY(int offsetY)
	{
		mOffsetY = offsetY;
	}
	
	public int getColor()
	{
		return mColor;
	}

	public void paintTile(Canvas canvas)
	{
		ResourceStore rs = new ResourceStore(mContext);
		Paint paint = new Paint();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++) 
			{
				if (mTile[i][j] != 0) 
				{
					canvas.drawBitmap(mRs.getBlock(mColor -1), Court.BEGIN_DRAW_X
							+ (i + mOffsetX) * Court.BLOCK_WIDTH, Court.BEGIN_DRAW_Y
							+ (j + mOffsetY) * Court.BLOCK_WIDTH, paint);
				}
			}
		}
		
	}
	
	public int[][] getMatrix()
	{
		return mTile;
	}

	public int getShape() {
		// TODO Auto-generated method stub
		return mShape;
	}

	public void setColor(int color) 
	{
		mColor = color;
		
	}
	
	public void setShape(int shape)
	{
		mShape = shape;
	}
	
}
