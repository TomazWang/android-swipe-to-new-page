package me.tom.swipe_listview;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchTrigger implements OnTouchListener{

	private static final String tag = "TouchTrigger";
	public boolean isTouched;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		Log.d(tag,"onTouch, event.action = "+event.getAction());
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			isTouched = true;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			isTouched = false;
			break;
		}
		
		Log.v(tag,"isTouched = "+isTouched);
		
		return false;
	}
	
}
