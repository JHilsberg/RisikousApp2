package de.hszg.risikousapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by Hannes on 17.12.2014.
 */

public class PublicationDetailsFragment extends Fragment {

    SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = PublicationDetailsFragment.class.getSimpleName();
    ViewPager mViewPager;

    public static PublicationDetailsFragment newInstance() {
        return new PublicationDetailsFragment();
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new TabbedView();
            Bundle args = new Bundle();
            args.putInt(TabbedView.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section5).toUpperCase();
                case 1:
                    return getString(R.string.title_section6).toUpperCase();
            }
            return null;
        }
    }

    public static class TabbedView extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public TabbedView() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_details,
                    container, false);
            ScrollView detailView = (ScrollView) rootView.findViewById(R.id.detailView);
            RelativeLayout commentView = (RelativeLayout) rootView.findViewById(R.id.commentView);
            //detailView.setVisibility(View.INVISIBLE);
            commentView.setVisibility(View.INVISIBLE);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(sectionNumber) {
                /*case 1:
                    commentView.setVisibility(View.GONE);
                    detailView.setVisibility(View.VISIBLE);*/
                case 2:
                    detailView.setVisibility(View.GONE);
                    commentView.setVisibility(View.VISIBLE);
            }
            return rootView;

        }
    }
}
