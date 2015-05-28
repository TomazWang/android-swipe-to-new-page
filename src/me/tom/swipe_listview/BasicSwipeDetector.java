package me.tom.swipe_listview;

import me.tom.swipe_listview.ListAdapter.IconItemViewHolder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class BasicSwipeDetector implements OnTouchListener {

	private final static String tag = "SwipeDetetor";

	private static final int MIN_DISTANCE = 300;
	private static final int MIN_LOCK_DISTANCE = 30; 
	private int bounceBackDistance = MIN_DISTANCE;
	private boolean motionInterceptDisallowed = false;
    private float downX, upX;
	

	private ListView listView;
	private IconItemViewHolder holder;
	private int position;
	private TouchTrigger trigger;

	public BasicSwipeDetector(ListView listView, IconItemViewHolder holder,
			int position) {
		this.listView = listView;
		this.holder = holder;
		this.setPosition(position);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		
//		Log.d(tag,"onTouch");
//		Log.v(tag,"isTouch? "+((trigger == null)?("null"):(trigger.isTouched)));
		if(trigger==null || !trigger.isTouched){
			Log.d(tag,"not touch on icon");
			return false;
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			onDown(event);
			break;
			
		case MotionEvent.ACTION_MOVE:
			// when moving
			onMove(event);
			break;

		case MotionEvent.ACTION_UP:
			onUp(event);
			if(trigger != null){
				trigger.isTouched = false;
			}
			break;
		}
		return true;
	}
	
	
	protected void reset() {
		
	}

	protected void onDown(MotionEvent event){
		downX = event.getX();
//		Log.i(tag,"onDown");
//		Log.v(tag,"downX recored: "+downX);
	}
	
	
	protected void onMove(MotionEvent event){
//		Log.i(tag,"onMove");
		upX = event.getX();
		float deltaX = upX - downX;

//		Log.v(tag,"upX ="+upX );
//		Log.v(tag,"deltaX(toRight) = "+deltaX);
		
		if(Math.abs(deltaX)>MIN_LOCK_DISTANCE
				&& listView != null
				&& !motionInterceptDisallowed){
			
			listView.requestDisallowInterceptTouchEvent(true);
			motionInterceptDisallowed = true;
		}
		
		if(deltaX < 0){
//			 swipe to left
//			moveToLeft();
		}else{
			moveToRight(deltaX);
		}
		
		
	}
	
	
	
	protected void moveToLeft(float deltaX){
//		Log.i(tag,"to left");
		holder.leftView.setVisibility(View.GONE);
//		swipe((int)deltaX);
	}
	
	
	protected void moveToRight(float deltaX) {
//		Log.i(tag,"to right");
		holder.leftView.setVisibility(View.VISIBLE);
		swipe((int)deltaX);
	}
	
	
	protected void onUp(MotionEvent event) {
		Log.i(tag,"onUp");
		upX = event.getX();
		float moveX = upX - downX;
		
		if(Math.abs(moveX)> bounceBackDistance){
			Log.d(tag,"moveX > min");
			
			if(moveX > 0){
				onRightAction(moveX);
			}else{
				onLeftAction(moveX);
			}
		}else{
			Log.d(tag,"moveX < min, should bounce back");
			onoBunceBackAction();
		}
		
		if(listView != null){
			listView.requestDisallowInterceptTouchEvent(false);
			motionInterceptDisallowed = false;
		}
		
		holder.rightView.setVisibility(View.VISIBLE);
	}
	
	
	
	protected void onLeftAction(float moveX) {
		onoBunceBackAction();
	}
	
	
	protected void onRightAction(float moveX) {
		onoBunceBackAction();
	}
	
	
	protected void onoBunceBackAction() {
		swipe(0);
	}
	
	
	protected void swipe(int distance){
		// Animations
		View view = holder.mainView;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
		params.rightMargin = -distance;
		params.leftMargin = distance;
		view.setLayoutParams(params);
	}

	public void setTrigger(TouchTrigger trigger) {
		this.trigger = trigger;
	}

	public float getDownX() {
		return downX;
	}

	public void setDownX(float downX) {
		this.downX = downX;
	}

	public float getUpX() {
		return upX;
	}

	public void setUpX(float upX) {
		this.upX = upX;
	}

	public IconItemViewHolder getHolder() {
		return holder;
	}

	public void setHolder(IconItemViewHolder holder) {
		this.holder = holder;
	}

	public int getBounceBackDistance() {
		return bounceBackDistance;
	}

	public void setBounceBackDistance(int bounceBackDistance) {
		this.bounceBackDistance = bounceBackDistance;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	

	
	
	
}
