package com.neusoft.youthleague.wheelView;



public interface OnWheelChangedListener {

	/**
	 * Wheel changed listener interface.
	 * <p>The onChanged() method is called whenever current wheel positions is changed:
	 * <li> New Wheel position is set
	 * <li> Wheel view is scrolled
	 */
	void onChanged(WheelView wheel, int oldValue, int newValue);
	
}

