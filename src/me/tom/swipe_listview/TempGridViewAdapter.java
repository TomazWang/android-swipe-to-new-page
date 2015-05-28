package me.tom.swipe_listview;

import java.util.List;

import me.tom.swipelistview.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TempGridViewAdapter extends ArrayAdapter<String> {
	
	private static final int layoutID = R.layout.layout_grid_item;

	public TempGridViewAdapter(Context context, List<String> objects) {
		super(context, layoutID, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Context context = getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		
		
		// should be using view hodler here!!
		convertView = inflater.inflate(layoutID, parent,false);
		convertView.setPadding(20, 20, 20, 0);
		return convertView;
	}
}
