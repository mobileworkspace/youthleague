package com.neusoft.youthleague.wheelView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neusoft.youthleague.R;


public class AdsWheelAdapter extends AbstractWheelTextAdapter {

	private Context mContext;
	private String[] ads;
	private int[] images;
	
    public AdsWheelAdapter(Context context) {
    	super(context, 1, NO_RESOURCE);   //R.layout.ads_wheel, NO_RESOURCE);
		// TODO Auto-generated constructor stub
		mContext = context;
/*		ads = mContext.getResources().getStringArray(R.array.ads_type);
		images = new int[]{R.drawable.house, R.drawable.job,R.drawable.service, R.drawable.food,
				           R.drawable.buy_sell, R.drawable.nightlife,R.drawable.city_special, R.drawable.church};
		
		setItemTextResource(R.id.ads_name);*/
	}

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
//        ImageView img = (ImageView) view.findViewById(R.id.flag);
//        img.setImageResource(images[index]);
        return view;
    }
    
    public int getItemsCount() {
        return ads.length;
    }
    
    @Override
    protected CharSequence getItemText(int index) {
        return ads[index];
    }
    
}
