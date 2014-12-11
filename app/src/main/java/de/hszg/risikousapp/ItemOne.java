package de.hszg.risikousapp;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.view.*;


public class ItemOne extends Fragment {

	public static final String TAG = ItemOne.class.getSimpleName();
	
	private ItemOnePagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
	
	public static ItemOne newInstance() {
		return new ItemOne();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_item_one, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		PagerTitleStrip tabs = (PagerTitleStrip) view.findViewById(R.id.pager_title_strip);
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
		pager.setAdapter(mPagerAdapter);

	}
	
	public class ItemOnePagerAdapter extends FragmentPagerAdapter {

		private final String[] TAB_TITLES = { "Today", "This Week", "This Month" };
		
		public ItemOnePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TAB_TITLES[position];
		}
		
		@Override
		public Fragment getItem(int position) {
			return ItemOneContentFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return 3;
			
		}
		
	}
}
