package me.tom.swipe_listview;

import me.tom.swipe_listview.ListAdapter.IconItemViewHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ListView;

public class SwipeToPage extends BasicSwipeDetector {

	private SurfaceView coverView;
	private int screenSizeX = 0;
	private AfterSwipeToPage delegate;
	
	private static final String tag = "SwipeToPage";
	
	public SwipeToPage(ListView listView, IconItemViewHolder holder,
			int position,SurfaceView coverView,int screenSizeX ,AfterSwipeToPage delegate) {
		super(listView, holder, position);
		this.coverView = coverView;
		this.coverView.setAlpha(0.0f);
		this.screenSizeX = screenSizeX;
		this.delegate = delegate;
		setBounceBackDistance((int)(screenSizeX*0.4));
	}
	
	@Override
	protected void onDown(MotionEvent event) {
		this.coverView.setBackground(getHolder().rightView.getBackground());
		super.onDown(event);
	}
	
	
	@Override
	protected void moveToRight(float deltaX) {
		super.moveToRight(deltaX);
		Log.i(tag,"setting cover alpha");
		coverView.setAlpha(deltaX/(screenSizeX*0.8f));
	}
	
	
	@Override
	protected void onRightAction(float moveX) {
		
		ValueAnimator swipeAnimator = ValueAnimator.ofInt((int)moveX,screenSizeX);
		swipeAnimator.setDuration(100);
		swipeAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int d = ((Integer)(animation.getAnimatedValue()));
				swipe(d);
			}
		});
		
		swipeAnimator.start();
		
		float startAlpha  = coverView.getAlpha();
		
		AlphaAnimation alphaAnimation = new AlphaAnimation(startAlpha,100f);
		alphaAnimation.setDuration(500);
		alphaAnimation.setStartOffset(0);
		alphaAnimation.setFillAfter(true);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.i(tag,"onFull");
				coverView.setAlpha(100.0f);
				swipe(0);
				delegate.onNewPage(SwipeToPage.this);
			}
		});
		coverView.startAnimation(alphaAnimation);
		
		
		

	}
	
	
	@Override
	protected void onoBunceBackAction() {
		super.onoBunceBackAction();
		coverView.setAlpha(0.0f);
	}
	
	
	public interface AfterSwipeToPage{
		
		public void onNewPage(SwipeToPage swipeToPage);
	}
	
}