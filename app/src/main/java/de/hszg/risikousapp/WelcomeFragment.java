package de.hszg.risikousapp;

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

public class WelcomeFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String TAG = WelcomeFragment.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a TabbedView (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new TabbedView();
            Bundle args = new Bundle();
            args.putInt(TabbedView.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

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
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class TabbedView extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

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

            // TODO ...hässlich gelöst, mit den sichbarkeiten von image- und textview, oder?!

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
