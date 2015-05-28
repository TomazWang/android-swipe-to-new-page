package me.tom.swipe_listview;

import java.util.ArrayList;
import java.util.HashMap;

import me.tom.swipe_listview.SwipeToPage.AfterSwipeToPage;
import me.tom.swipelistview.R;
import android.R.integer;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String tag = "MainActivty";

	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<String> gridData = new ArrayList<String>();
	
	private ListView listView;
	private SurfaceView coverView;
	private TextView txt_item_name;
	private ImageView img_icon;
	private LinearLayout title_view;
	private GridView main_grid_view;
	private RelativeLayout layout_title;
	private RelativeLayout layout_content;
	
	
	private RelativeLayout.LayoutParams titleParams;
	private RelativeLayout.LayoutParams contentParams;
	
	
	
	private ListAdapter adapter;
	private SwipeToPage swipeToPage = null;
	private boolean isInNewPage = false;
	
	private int width,height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		prepareData();
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		layout_content = (RelativeLayout)findViewById(R.id.layout_content);
		layout_title = (RelativeLayout)findViewById(R.id.layout_title);
		main_grid_view = (GridView)findViewById(R.id.main_grid_view);
		
		img_icon = (ImageView)findViewById(R.id.img_icon);
		txt_item_name = (TextView)findViewById(R.id.txt_item_Name);
		title_view = (LinearLayout)findViewById(R.id.title_view);
		
		layout_content.setVisibility(View.GONE);
		layout_title.setVisibility(View.GONE);
		
		
		main_grid_view.setAdapter(new TempGridViewAdapter(this, gridData));
		
		
		listView = (ListView)findViewById(R.id.list_view);
		
		SurfaceView coverView = (SurfaceView)findViewById(R.id.cover_view);
		coverView.setAlpha(0f);
		
		AfterSwipeToPage afterSwipeToPage = new AfterSwipeToPage() {
			
			@Override
			public void onNewPage(SwipeToPage swipeToPage) {
				isInNewPage = true;
				MainActivity.this.swipeToPage = swipeToPage;
				
				titleParams = (RelativeLayout.LayoutParams)layout_title.getLayoutParams();
				contentParams = (RelativeLayout.LayoutParams)layout_content.getLayoutParams();
				
				titleParams.topMargin = titleParams.height*-1;
				layout_title.setLayoutParams(titleParams);
				
				contentParams.bottomMargin = contentParams.height * -1;
				layout_content.setLayoutParams(contentParams);
				
				layout_content.setVisibility(View.VISIBLE);
				layout_title.setVisibility(View.VISIBLE);
				
				listView.setVisibility(View.GONE);

				Item item = itemList.get(swipeToPage.getPosition());

				img_icon.setImageResource(item.getIconId());
				txt_item_name.setText(item.getItemName());
				
				titleBarInAnimation(titleParams);
				gridViewInAnimation(contentParams);
				
			}

		};
		
		adapter = new ListAdapter(this, R.layout.layout_list_item, itemList,listView,coverView,width,afterSwipeToPage);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void prepareData() {

		String[] names = new String[] { "Bell", "Check", "Female", "Male",
				"Map", "Quiz", "Ans", "Star", "BlackStar", "Tag", "Locator",
				"LightHose","light" };

		int[] iconIds = new int[] { 
				R.drawable.bell16, R.drawable.check34,
				R.drawable.female128, R.drawable.male154, R.drawable.map31,
				R.drawable.speech65, R.drawable.speech66, R.drawable.star82,
				R.drawable.star83, R.drawable.tag33, R.drawable.locator7,
				R.drawable.lighthouse1,R.drawable.light56 };
		
		
		int[] colorIds = new int[]{
				R.color.yellow_bright,R.color.green_clover,
				R.color.pink,R.color.blue_dress,
				R.color.green_jade,
				R.color.blue_sky_deep,R.color.red,
				R.color.yellow_sun,R.color.black_oil,
				R.color.orange_Cantaloupe,R.color.purple_amethyst,
				R.color.gray_cloud,R.color.blue_ocean	
		};
		
		for(int i = 0,j = 0 ; j<20 ;j++){
			
			i = j;
			if(i >= names.length){
				i -= names.length;
			}
			String name = names[i];
			int iconId = iconIds[i];
			int colorId = colorIds[i];
			
			Log.v(tag,"create Item name: "+name);
			
			itemList.add(new Item(name, i, i, colorId, iconId));
			gridData.add(name);
		}
		
	}
	
	
	private void gridViewInAnimation(final RelativeLayout.LayoutParams contentParams) {
		ValueAnimator animator = ValueAnimator.ofInt(contentParams.bottomMargin,0);
		animator.setDuration(1000);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int m = ((Integer)animation.getAnimatedValue());
				contentParams.bottomMargin = m;
				layout_content.setLayoutParams(contentParams);
			}
		});
		animator.start();
		
	}

	private void titleBarInAnimation(final RelativeLayout.LayoutParams titleParams) {
		ValueAnimator animator = ValueAnimator.ofInt(titleParams.topMargin,0);
		animator.setDuration(1000);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int m = ((Integer)animation.getAnimatedValue());
				titleParams.topMargin = m;
				layout_content.setLayoutParams(titleParams);
			}
		});
		animator.start();
	}
	
	
	@Override
	public void onBackPressed() {
		
		if(isInNewPage && swipeToPage != null){
			backToList();
		}else{
			super.onBackPressed();
		}
		
	}

	private void backToList() {
		
		titleParams = (RelativeLayout.LayoutParams)layout_title.getLayoutParams();
		contentParams = (RelativeLayout.LayoutParams)layout_content.getLayoutParams();
		
		ValueAnimator tAnimator = ValueAnimator.ofInt(0,titleParams.topMargin);
		tAnimator.setDuration(300);
		tAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int m = ((Integer)animation.getAnimatedValue());
				titleParams.topMargin = m;
				layout_content.setLayoutParams(titleParams);
			}
		});
		tAnimator.start();
		
		
		ValueAnimator cAnimator = ValueAnimator.ofInt(0,contentParams.bottomMargin);
		cAnimator.setDuration(300);
		cAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int m = ((Integer)animation.getAnimatedValue());
				contentParams.bottomMargin = m;
				layout_content.setLayoutParams(contentParams);
			}
		});
		
		cAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				swipeToPage.onoBunceBackAction();
				layout_content.setVisibility(View.GONE);
				layout_title.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		cAnimator.start();
		
	}
	
	
	
	

}