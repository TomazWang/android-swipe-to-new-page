package me.tom.swipe_listview;

import java.util.List;

import me.tom.swipe_listview.SwipeToPage.AfterSwipeToPage;
import me.tom.swipelistview.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ListAdapter extends ArrayAdapter<Item> {

	private int layoutId = R.layout.layout_list_item;
	private ListView listView;
	private SurfaceView coverView;
	private int screenSizeX;
	private AfterSwipeToPage delegate;

	public ListAdapter(Context context, int resource, List<Item> objects,
			ListView listView, SurfaceView coverView, int screenSizeX, AfterSwipeToPage delegate) {
		super(context, resource, objects);
		this.listView = listView;
		this.coverView = coverView;
		this.screenSizeX =screenSizeX;
		this.delegate = delegate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Context context = getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		IconItemViewHolder viewHolder;

		if (convertView == null) {
			// inflate layout first time
			convertView = inflater.inflate(layoutId, parent, false);
		}
		
		final IconItemViewHolder holder = getIconItemViewHolder(convertView);
		Item item = getItem(position);
		holder.imgView.setImageResource(item.getIconId());
		holder.rightView.setBackgroundResource(item.getItemColorCode());
		holder.leftView.setBackgroundResource(item.getItemColorCode());;
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.mainView.getLayoutParams();
		params.rightMargin = 0;
		params.leftMargin = 0;
		holder.mainView.setLayoutParams(params);
		
		TouchTrigger iconSD = new TouchTrigger();
		holder.imgView.setOnTouchListener(iconSD);
		
		SwipeToPage swipe = new SwipeToPage(listView, holder, position, coverView, screenSizeX,delegate);
		swipe.setTrigger(iconSD);
		convertView.setOnTouchListener(swipe);
		
		return convertView;
	}

	public void setListView(ListView view) {
		this.listView = view;
	}

	private IconItemViewHolder getIconItemViewHolder(View view) {
		Object tag = view.getTag();
		IconItemViewHolder holder;
		if (tag == null || !(tag instanceof IconItemViewHolder)) {
			holder = new IconItemViewHolder();
			holder.mainView = (LinearLayout) view.findViewById(R.id.swipe_main_view);
			holder.leftView = (RelativeLayout) view.findViewById(R.id.swipe_right_view);
			holder.rightView = (RelativeLayout) view.findViewById(R.id.swipe_right_view);
			holder.imgView = (ImageView) view.findViewById(R.id.swipe_img_icon);

			view.setTag(holder);
			
		}else{
			holder = (IconItemViewHolder)tag;
		}

		return holder;
	}

	public static class IconItemViewHolder {
		public ImageView imgView;
		public RelativeLayout leftView;
		public RelativeLayout rightView;
		public LinearLayout mainView;

	}

}
