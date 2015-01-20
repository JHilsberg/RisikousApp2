package de.hszg.risikousapp.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hszg.risikousapp.R;

/**
 * Fragment that shows a welcome screen with info about the risikous system.
 */
public class WelcomeFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String TAG = WelcomeFragment.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    /**
     * Make an new instance of the fragment.
     * @return new instance of welcome fragment
     */
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates view for tab-view below the action bar.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view for tabs
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.title_strip, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return v;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the tabs.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page.
         * Return a TabbedView with the page number as its lone argument.
         * @param position
         * @return TabbedView
         */
        @Override
        public Fragment getItem(int position) {
            //
            Fragment fragment = new TabbedView();
            Bundle args = new Bundle();
            args.putInt(TabbedView.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * show 4 total pages
         * @return number of tabs
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * Sets the titles of the tabs.
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase();
                case 1:
                    return getString(R.string.title_section2).toUpperCase();
                case 2:
                    return getString(R.string.title_section3).toUpperCase();
                case 3:
                    return getString(R.string.title_section4).toUpperCase();
            }
            return null;
        }
    }

    /**
     * A fragment representing a tab of the app, that show different info about risikous.
     */
    public static class TabbedView extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Create views for all 4 tabs.
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return view of selected tab
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_welcome,
                    container, false);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            TextView textView = (TextView) rootView.findViewById(R.id.textView);
            TextView tv_welcome = (TextView) rootView.findViewById(R.id.tv_welcome);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);

            switch (sectionNumber) {
                case 1:
                    tv_welcome.setText(R.string.title_section1);
                    textView.setText(R.string.section1_text);
                    break;
                case 2:
                    textView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    tv_welcome.setText(R.string.title_section2);
                    imageView.setImageResource(R.drawable.cirs);
                    break;
                case 3:
                    tv_welcome.setText(R.string.title_section3);
                    textView.setText(R.string.section3_text);
                    break;
                case 4:
                    tv_welcome.setText(R.string.title_section4);
                    textView.setText(R.string.section4_text);
                    break;
            }

            return rootView;

        }
    }

}
