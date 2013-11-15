package com.neusoft.youthleague.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.HorizontalScrollView;

import com.neusoft.youthleague.exception.ConstantValues;


public class MyHorizontalScrollView extends HorizontalScrollView  {
	

	private int dragOffsetX;  //拖动横向距离
	private int dragOffsetY;  //拖动纵向距离
	
	private int dragPointX;
	private int dragPointY;
	
	private HorizontalScrollView scrollView;



	
	public MyHorizontalScrollView(Context context) {
		super(context);
		inital(context);
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inital(context);
	}

	private void inital(Context context) {
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return setonTouchEventListener(event);
		}
		return super.onInterceptTouchEvent(event);
	}
	
	public boolean setonTouchEventListener(MotionEvent event) {
	
	    this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				//进行处理
				
				return false;
			}
		});
	    
	    return super.onInterceptTouchEvent(event);
	    
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch (event.getAction()) {
				
		
			case MotionEvent.ACTION_DOWN:
				dragPointX = x;
				dragPointY = y;
				break;
			
			case MotionEvent.ACTION_UP:
				doDraging(x, y);
				break;
				
		}
		
		return false;
		
	} 

	private void doDraging(int x, int y) {
		
			
			int dragOffsetX = x - dragPointX;
			int dragOffsetY = y - dragPointY;
			
			scrollView.scrollTo(dragOffsetX, dragOffsetY); 
	}

}
