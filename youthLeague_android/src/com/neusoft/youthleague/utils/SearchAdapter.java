package com.neusoft.youthleague.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.neusoft.youthleague.R;
import com.neusoft.youthleague.model.Staff;

public class SearchAdapter extends BaseAdapter {

	Context context ;
	List<Staff> itemList;
	LayoutInflater inflater;
	
	
	public SearchAdapter(Context context, List<Staff> data) {
		super();
		this.context = context;
		this.itemList = data;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return itemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		Holder holder;
		
//		if (position >= 0 && position < itemList.size()){
			
			if (convertView==null) {
				
				convertView = inflater.inflate(R.layout.search_list_item, parent, false);
				holder = new Holder();   
				holder.nameTextView = (TextView)convertView.findViewById(R.id.name_textview);
				holder.positionTextView = (TextView)convertView.findViewById(R.id.position_textview);
				holder.mobileTextView = (TextView)convertView.findViewById(R.id.mobile_textview);
				holder.phoneTextView = (TextView)convertView.findViewById(R.id.phone_textview);
				holder.selectCheckBox = (CheckBox) convertView.findViewById(R.id.chkbox);
				
				convertView.setTag(holder);
				holder.nameTextView.setTag(itemList.get(position));

			}
			else {
				holder = (Holder) convertView.getTag();
				holder.nameTextView.setTag(itemList.get(position));
			}
			
			holder.nameTextView.setText(itemList.get(position).getName());
			holder.positionTextView.setText(itemList.get(position).getPositionName());
			holder.mobileTextView.setText(itemList.get(position).getMobile());
			holder.phoneTextView.setText(itemList.get(position).getPhone());
			holder.selectCheckBox.setChecked(itemList.get(position).isSelected());
//			holder.selectCheckBox.setTag(position);
			
			holder.selectCheckBox.setVisibility(View.VISIBLE);
			holder.selectCheckBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					itemList.get(position).setSelected(((CheckBox)v).isChecked());
				}
			});

//		}
		
        return convertView;
        
	}

}
